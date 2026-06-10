package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.shape;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Vector3d;

import java.util.List;

/**
 * Aggregate/compound union {@link CollisionShape} implementation.
 * <br></br>
 * {@code CompoundUnionShape} is the lightweight "keep the children around" counterpart to {@link CSGUnionShape}.
 * Instead of doing polygon clipping or flattening the entire thing into a single mesh, this shape stores an ordered
 * collection of {@link ChildShape}s, each with its own bounds and optional local transform. This is useful when a
 * compound hitbox should behave like one logical shape, but still retain the underlying child shapes for accurate-ish
 * containment, closest-point, SAT metadata, and broad-phase bounds.
 * <br></br>
 * Queries are resolved using straightforward union semantics:
 * <ul>
 *     <li>{@link #containsPoint(Vector3d, Vector3d, Vector3d)} returns {@code true} if any child contains the point.</li>
 *     <li>{@link #closestPoint(Vector3d, Vector3d, Vector3d)} picks the nearest child-surface result.</li>
 *     <li>Face normals and edge directions are aggregated from children for SAT-style consumers.</li>
 *     <li>{@link #computeCombinedAABB(Matrix4d)} transforms/merges child vertices for a practical broad-phase box.</li>
 * </ul>
 * In other words, this class is more of a semantic union than a geometric boolean union. Internal overlaps are left
 * intact, child boundaries remain meaningful, and no attempt is made to cull hidden/intersecting faces (cuz that's what
 * {@link CSGUnionShape} is for).
 * <br></br>
 * <b>Important:</b> Child transforms are primarily respected by combined-AABB generation; the core
 * {@link CollisionShape} callbacks delegate to the child shapes using their stored local bounds. Use this when you want
 * modular compound hitboxes with preserved child behavior, {@link CSGUnionShape} when you need exterior-ish union
 * geometry, and {@link MergedShape} when you just want one cheap flattened approximation.
 */
public class CompoundUnionShape implements CollisionShape {
    private final List<ChildShape> children;
    private final List<Vector3d> cachedFaceNormals;
    private final List<Vector3d> cachedEdgeDirections;

    public CompoundUnionShape(List<ChildShape> children) {
        this.children = new ObjectArrayList<>(children);

        ObjectArrayList<Vector3d> normals = new ObjectArrayList<>();
        ObjectArrayList<Vector3d> edges = new ObjectArrayList<>();

        for (ChildShape child : this.children) {
            normals.addAll(child.getShape().getFaceNormals());
            edges.addAll(child.getShape().getEdgeDirections());
        }

        this.cachedFaceNormals = ObjectLists.unmodifiable(normals);
        this.cachedEdgeDirections = ObjectLists.unmodifiable(edges);
    }

    public CompoundUnionShape(ChildShape... children) {
        this(ObjectArrayList.of(children));
    }

    public List<ChildShape> getChildren() {
        return children;
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.COMPOUND_UNION;
    }

    @Override
    public List<Vector3d> generateLocalVertices(Vector3d minBounds, Vector3d maxBounds) {
        ObjectArrayList<Vector3d> allVertices = new ObjectArrayList<>();

        for (ChildShape child : children) {
            allVertices.addAll(child.getShape().generateLocalVertices(child.getMinBounds(), child.getMaxBounds()));
        }

        return allVertices;
    }

    @Override
    public Vector3d computeHalfExtents(Vector3d minBounds, Vector3d maxBounds) {
        if (children.isEmpty()) return new Vector3d();

        // Compute half-extents from the combined bounds of all children
        Vector3d combinedMin = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        Vector3d combinedMax = new Vector3d(-Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE);

        for (ChildShape child : children) {
            Vector3d childCenter = child.getShape().computeCenter(child.getMinBounds(), child.getMaxBounds());
            Vector3d childHalf = child.getShape().computeHalfExtents(child.getMinBounds(), child.getMaxBounds());

            Vector3d childMin = new Vector3d(childCenter).sub(childHalf); // Generated at child's position (world bounds)
            Vector3d childMax = new Vector3d(childCenter).add(childHalf);

            combinedMin.x = Math.min(combinedMin.x, childMin.x);
            combinedMin.y = Math.min(combinedMin.y, childMin.y);
            combinedMin.z = Math.min(combinedMin.z, childMin.z);
            combinedMax.x = Math.max(combinedMax.x, childMax.x);
            combinedMax.y = Math.max(combinedMax.y, childMax.y);
            combinedMax.z = Math.max(combinedMax.z, childMax.z);
        }

        return new Vector3d(
                (combinedMax.x - combinedMin.x) / 2.0D,
                (combinedMax.y - combinedMin.y) / 2.0D,
                (combinedMax.z - combinedMin.z) / 2.0D
        );
    }

    @Override
    public Vector3d computeCenter(Vector3d minBounds, Vector3d maxBounds) {
        if (children.isEmpty()) return new Vector3d();

        // Compute center from the combined bounds of all children
        Vector3d combinedMin = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        Vector3d combinedMax = new Vector3d(-Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE);

        for (ChildShape child : children) {
            Vector3d childCenter = child.getShape().computeCenter(child.getMinBounds(), child.getMaxBounds());
            Vector3d childHalf = child.getShape().computeHalfExtents(child.getMinBounds(), child.getMaxBounds());

            Vector3d childMin = new Vector3d(childCenter).sub(childHalf); // Generated at child's position (world bounds)
            Vector3d childMax = new Vector3d(childCenter).add(childHalf);

            combinedMin.x = Math.min(combinedMin.x, childMin.x);
            combinedMin.y = Math.min(combinedMin.y, childMin.y);
            combinedMin.z = Math.min(combinedMin.z, childMin.z);
            combinedMax.x = Math.max(combinedMax.x, childMax.x);
            combinedMax.y = Math.max(combinedMax.y, childMax.y);
            combinedMax.z = Math.max(combinedMax.z, childMax.z);
        }

        return new Vector3d(
                (combinedMin.x + combinedMax.x) / 2.0D,
                (combinedMin.y + combinedMax.y) / 2.0D,
                (combinedMin.z + combinedMax.z) / 2.0D
        );
    }

    @Override
    public List<Vector3d> getFaceNormals() {
        return cachedFaceNormals;
    }

    @Override
    public List<Vector3d> getEdgeDirections() {
        return cachedEdgeDirections;
    }

    @Override
    public boolean containsPoint(Vector3d point, Vector3d minBounds, Vector3d maxBounds) { // CUS: It contains a point if any child happens to contain said point
        for (ChildShape child : children) { // Shapes with explicit model-space vertices get model-space points/bounds passed in; otherwise, we use local-space instead
            ShapeType type = child.getShape().getShapeType();
            boolean hasExplicitVertices = (type == ShapeType.CSG_UNION || type == ShapeType.CONVEX_HULL);

            Vector3d testPoint;

            if (hasExplicitVertices) testPoint = point; // Already in model space
            else { // Transform to child's local space
                Vector3d childCenter = child.getShape().computeCenter(child.getMinBounds(), child.getMaxBounds());
                testPoint = new Vector3d(point).sub(childCenter);
            }

            if (child.getShape().containsPoint(testPoint, child.getMinBounds(), child.getMaxBounds())) return true;
        }

        return false;
    }

    @Override
    public Vector3d closestPoint(Vector3d point, Vector3d minBounds, Vector3d maxBounds) { // CUS: Closest distance to any child
        if (children.isEmpty()) return new Vector3d(point);

        Vector3d closest = null;
        double bestDistSq = Double.MAX_VALUE;

        for (ChildShape child : children) {
            ShapeType type = child.getShape().getShapeType();
            boolean hasExplicitVertices = type == ShapeType.CSG_UNION || type == ShapeType.CONVEX_HULL;

            Vector3d testPoint;
            Vector3d childClosest;

            if (hasExplicitVertices) { // See above
                testPoint = point;
                childClosest = child.getShape().closestPoint(testPoint, child.getMinBounds(), child.getMaxBounds());
            } else {
                Vector3d childCenter = child.getShape().computeCenter(child.getMinBounds(), child.getMaxBounds());

                testPoint = new Vector3d(point).sub(childCenter);
                childClosest = child.getShape().closestPoint(testPoint, child.getMinBounds(), child.getMaxBounds());

                childClosest.add(childCenter); // Transform back to model space
            }

            double distSq = childClosest.distanceSquared(point);

            if (distSq < bestDistSq) {
                bestDistSq = distSq;
                closest = childClosest;
            }
        }

        return closest != null ? closest : new Vector3d(point);
    }

    @Override
    public int getVertexCount() {
        int count = 0;

        for (ChildShape child : children) {
            count += child.getShape().getVertexCount();
        }

        return count;
    }

    @Override
    public double computeVolume(Vector3d minBounds, Vector3d maxBounds) { // Conservative estimate, since union sum isn't the sum of all volumes (since shapes can overlap)
        Vector3d halfExtents = computeHalfExtents(minBounds, maxBounds);

        return halfExtents.x * halfExtents.y * halfExtents.z * 8.0D;
    }

    @Override
    public String toString() {
        return String.format("CompoundUnionShape{children=%d}", children.size());
    }

    /**
     * Computes the world-space {@link AABB} representing this CompoundUnionShape.
     *
     * @param worldTransform Additional transformations to apply via a {@link Matrix4d}, such as entity position.
     *
     * @return An {@link AABB} wrapping around this CUS instance.
     */
    public AABB computeCombinedAABB(Matrix4d worldTransform) {
        if (children.isEmpty()) return new AABB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, minZ = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE, maxY = -Double.MAX_VALUE, maxZ = -Double.MAX_VALUE;

        for (ChildShape child : children) {
            Vector3d childCenter = child.getShape().computeCenter(child.getMinBounds(), child.getMaxBounds());
            Vector3d childHalf = child.getShape().computeHalfExtents(child.getMinBounds(), child.getMaxBounds());

            Matrix4d childTransform = new Matrix4d(worldTransform);

            if (child.hasTransform()) childTransform.mul(child.getTransform());
            else childTransform.translate(childCenter.x, childCenter.y, childCenter.z);

            double cx = childCenter.x;
            double cy = childCenter.y;
            double cz = childCenter.z;

            double hx = childHalf.x;
            double hy = childHalf.y;
            double hz = childHalf.z;

            Vector3d[] corners = new Vector3d[]{
                    new Vector3d(cx - hx, cy - hy, cz - hz),
                    new Vector3d(cx + hx, cy - hy, cz - hz),
                    new Vector3d(cx - hx, cy + hy, cz - hz),
                    new Vector3d(cx + hx, cy + hy, cz - hz),
                    new Vector3d(cx - hx, cy - hy, cz + hz),
                    new Vector3d(cx + hx, cy - hy, cz + hz),
                    new Vector3d(cx - hx, cy + hy, cz + hz),
                    new Vector3d(cx + hx, cy + hy, cz + hz)
            };

            for (Vector3d corner : corners) {
                Vector3d worldCorner = corner.mulPosition(childTransform);

                minX = Math.min(minX, worldCorner.x);
                minY = Math.min(minY, worldCorner.y);
                minZ = Math.min(minZ, worldCorner.z);
                maxX = Math.max(maxX, worldCorner.x);
                maxY = Math.max(maxY, worldCorner.y);
                maxZ = Math.max(maxZ, worldCorner.z);
            }
        }

        return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Represents an immutable child shape with its local bounds and optional transform. Each child maintains its own
     * coordinate space and bounds.
     */
    public static final class ChildShape {
        private final CollisionShape shape;
        private final Vector3d minBounds;
        private final Vector3d maxBounds;
        private final Matrix4d transform; // Optional transform (e.g. bone matrix)

        public ChildShape(CollisionShape shape, Vector3d minBounds, Vector3d maxBounds, Matrix4dc transform) {
            this.shape = shape;
            this.minBounds = new Vector3d(minBounds);
            this.maxBounds = new Vector3d(maxBounds);
            this.transform = transform != null ? new Matrix4d(transform) : null;
        }

        public ChildShape(CollisionShape shape, Vector3d minBounds, Vector3d maxBounds) {
            this(shape, minBounds, maxBounds, null);
        }

        public ChildShape(CollisionShape shape, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
            this(shape, new Vector3d(minX, minY, minZ), new Vector3d(maxX, maxY, maxZ), null);
        }

        public ChildShape(CollisionShape shape, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Matrix4dc transform) {
            this(shape, new Vector3d(minX, minY, minZ), new Vector3d(maxX, maxY, maxZ), transform);
        }

        public Matrix4d getTransform() {
            return transform != null ? transform : new Matrix4d().identity();
        }

        public boolean hasTransform() {
            return transform != null;
        }

        public CollisionShape getShape() {
            return shape;
        }

        public Vector3d getMinBounds() {
            return new Vector3d(minBounds);
        }

        public Vector3d getMaxBounds() {
            return new Vector3d(maxBounds);
        }
    }

    public static final class Builder {
        private final List<ChildShape> children = new ObjectArrayList<>();

        private Builder() {

        }

        public Builder addChild(ChildShape child) {
            children.add(child);
            return this;
        }

        public Builder addChild(CollisionShape shape, Vector3d minBounds, Vector3d maxBounds, Matrix4dc transform) {
            return addChild(new ChildShape(shape, minBounds, maxBounds, transform));
        }

        public CompoundUnionShape build() {
            return new CompoundUnionShape(children);
        }
    }
}
