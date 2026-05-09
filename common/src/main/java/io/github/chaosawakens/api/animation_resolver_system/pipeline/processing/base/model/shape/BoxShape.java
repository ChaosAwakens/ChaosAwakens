package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.shape;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.joml.Vector3d;

import java.util.List;

/**
 * Box/rectangular prism {@link CollisionShape} implementation.
 * <br></br>
 * Vertices are generated in the following order (x, y, z):
 * <ol>
 *     <li>{@code min, min, min}</li>
 *     <li>{@code max, min, min}</li>
 *     <li>{@code min, min, max}</li>
 *     <li>{@code max, min, max}</li>
 *     <li>{@code min, max, min}</li>
 *     <li>{@code max, max, min}</li>
 *     <li>{@code min, max, max}</li>
 *     <li>{@code max, max, max}</li>
 * </ol>
 * Box shapes are optimally tested for collision using SAT, not GJK/EPA, cuz each box will always have 8 vertices, meaning
 * we don't really have to worry or care about the ballooning time complexity for higher edge/vertex counts (+ EPA would
 * add significant overhead, whereas SAT can just give us everything GJK/EPA provides, except for contact normal).
 * <br></br>
 * Of all current {@link CollisionShape} implementations, {@code BoxShape} stands as the only stateless impl thanks to
 * the triviality of computing its data parametrically on the fly. As such, only one such object exists in memory
 * (singleton).
 */
public class BoxShape implements CollisionShape {
    public static final BoxShape INSTANCE = new BoxShape();
    public static final List<Vector3d> FACE_NORMALS = ImmutableList.of(
            new Vector3d(1.0D, 0.0D, 0.0D), // +X face
            new Vector3d(0.0D, 1.0D, 0.0D), // +Y face
            new Vector3d(0.0D, 0.0D, 1.0D)  // +Z face
    );
    public static final List<Vector3d> EDGE_DIRECTIONS = ImmutableList.copyOf(FACE_NORMALS); // Same as face normals because edges are aligned with axes (the shape itself is axis-aligned, whereas MCD provides OBB capabilities and whatnot)

    private BoxShape() { // Private cuz boxes are stateless, so we just use the provided singleton instance
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.BOX;
    }

    @Override
    public List<Vector3d> generateLocalVertices(Vector3d minBounds, Vector3d maxBounds) {
        List<Vector3d> vertices = new ObjectArrayList<>(getVertexCount());

        double[] xVals = {minBounds.x, maxBounds.x};
        double[] yVals = {minBounds.y, maxBounds.y};
        double[] zVals = {minBounds.z, maxBounds.z};

        for (double z : zVals) {
            for (double y : yVals) {
                for (double x : xVals) {
                    vertices.add(new Vector3d(x, y, z));
                }
            }
        }

        return vertices;
    }

    @Override
    public Vector3d computeHalfExtents(Vector3d minBounds, Vector3d maxBounds) {
        return new Vector3d(
                (maxBounds.x - minBounds.x) / 2.0D,
                (maxBounds.y - minBounds.y) / 2.0D,
                (maxBounds.z - minBounds.z) / 2.0D
        );
    }

    @Override
    public List<Vector3d> getFaceNormals() {
        return FACE_NORMALS;
    }

    @Override
    public List<Vector3d> getEdgeDirections() {
        return EDGE_DIRECTIONS;
    }

    @Override
    public boolean containsPoint(Vector3d point, Vector3d minBounds, Vector3d maxBounds) {
        return point.x >= minBounds.x && point.x <= maxBounds.x &&
                point.y >= minBounds.y && point.y <= maxBounds.y &&
                point.z >= minBounds.z && point.z <= maxBounds.z;
    }

    @Override
    public Vector3d closestPoint(Vector3d point, Vector3d minBounds, Vector3d maxBounds) {
        return new Vector3d(
                Math.max(minBounds.x, Math.min(point.x, maxBounds.x)),
                Math.max(minBounds.y, Math.min(point.y, maxBounds.y)),
                Math.max(minBounds.z, Math.min(point.z, maxBounds.z))
        );
    }

    @Override
    public int getVertexCount() {
        return 8;
    }
}