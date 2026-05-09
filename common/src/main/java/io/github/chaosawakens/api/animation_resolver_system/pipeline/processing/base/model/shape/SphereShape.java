package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.shape;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import org.joml.Matrix4d;
import org.joml.Vector3d;

import java.util.List;

/**
 * Sphere {@link CollisionShape} implementation, represented as an icosphere for vertex-based collision.
 * <br></br>
 * The sphere is defined by the inscribed sphere of the bounding box. Its radius is determined by the smallest
 * half extent of its containing bounding box's bounds, which ensures that the sphere itself fits entirely within
 * said bounds.
 * <br></br>
 * Regarding the vertices, a UV sphere layout is used with configurable resolution in terms of latitude rings (excluding
 * poles) and longitude segments. The sphere will always have exactly 2 pole vertices.
 * <br></br>
 * The vertex-based approach here is for SAT compatibility with other shapes.
 */
public class SphereShape implements CollisionShape {
    public static final SphereShape DEFAULT = new SphereShape(8, 6);
    public static final double EPSILON = 1.0E-10D;
    private final int segments;
    private final int rings;
    private final List<Vector3d> cachedFaceNormals;

    public SphereShape(int segments, int rings) {
        this.segments = Math.max(4, segments);
        this.rings = Math.max(2, rings);
        this.cachedFaceNormals = computeFaceNormals(segments);
    }

    private static List<Vector3d> computeFaceNormals(int segments) { // Since a sphere has infinite face normals (geometrically/mathematically-speaking), we instead use a set of evenly-distributed normals matching the vertex layout for SAT approx.
        ObjectArrayList<Vector3d> normals = new ObjectArrayList<>(segments + 1);

        // Cardinal axes
        normals.add(new Vector3d(1, 0, 0));
        normals.add(new Vector3d(0, 1, 0));
        normals.add(new Vector3d(0, 0, 1));

        // Equatorial normals
        for (int seg = 0; seg < segments; seg++) {
            double theta = (2.0D * Math.PI * seg) / segments;

            normals.add(new Vector3d(Math.cos(theta), 0, Math.sin(theta)).normalize());
        }

        return ObjectLists.unmodifiable(normals);
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.SPHERE;
    }

    @Override
    public List<Vector3d> generateLocalVertices(Vector3d minBounds, Vector3d maxBounds) {
        List<Vector3d> vertices = new ObjectArrayList<>(getVertexCount());

        Vector3d center = computeCenter(minBounds, maxBounds);
        Vector3d size = new Vector3d(maxBounds).sub(minBounds);

        // Radius is the smallest half-extent, so that the sphere fits within bounds
        double radius = Math.min(size.x, Math.min(size.y, size.z)) / 2.0D;

        vertices.add(new Vector3d(center.x, center.y + radius, center.z)); // Top pole

        for (int ring = 1; ring <= rings; ring++) { // Latitude rings (excluding poles)
            double phi = (Math.PI * ring) / (rings + 1); // 0 = top, PI = bottom
            double y = center.y + radius * Math.cos(phi);
            double ringRadius = radius * Math.sin(phi);

            for (int seg = 0; seg < segments; seg++) {
                double theta = (2.0D * Math.PI * seg) / segments;
                double x = center.x + ringRadius * Math.cos(theta);
                double z = center.z + ringRadius * Math.sin(theta);

                vertices.add(new Vector3d(x, y, z));
            }
        }

        vertices.add(new Vector3d(center.x, center.y - radius, center.z)); // Bottom pole

        return vertices;
    }

    @Override
    public Vector3d computeHalfExtents(Vector3d minBounds, Vector3d maxBounds) {
        Vector3d size = new Vector3d(maxBounds).sub(minBounds);
        double radius = Math.min(size.x, Math.min(size.y, size.z)) / 2.0D;

        return new Vector3d(radius, radius, radius);
    }

    @Override
    public List<Vector3d> getFaceNormals() {
        return cachedFaceNormals;
    }

    @Override
    public List<Vector3d> getEdgeDirections() {
        return getFaceNormals();
    }

    @Override
    public boolean containsPoint(Vector3d point, Vector3d minBounds, Vector3d maxBounds) {
        Vector3d center = computeCenter(minBounds, maxBounds);
        Vector3d size = new Vector3d(maxBounds).sub(minBounds);
        double radius = Math.min(size.x, Math.min(size.y, size.z)) / 2.0D;

        double dx = point.x - center.x;
        double dy = point.y - center.y;
        double dz = point.z - center.z;

        return (dx * dx + dy * dy + dz * dz) <= (radius * radius);
    }

    @Override
    public Vector3d closestPoint(Vector3d point, Vector3d minBounds, Vector3d maxBounds) {
        Vector3d center = computeCenter(minBounds, maxBounds);
        Vector3d size = new Vector3d(maxBounds).sub(minBounds);
        double radius = Math.min(size.x, Math.min(size.y, size.z)) / 2.0D;

        Vector3d direction = new Vector3d(point).sub(center);
        double dist = direction.length();

        if (dist < EPSILON)
            return new Vector3d(center.x + radius, center.y, center.z); // Point is at center, so just return some arbitrary point on the surface of the shape

        direction.div(dist);

        return new Vector3d(center).add(direction.mul(radius));
    }

    @Override
    public int getVertexCount() { // 2 poles
        return 2 + rings * segments;
    }

    @Override
    public double computeVolume(Vector3d minBounds, Vector3d maxBounds) {
        Vector3d size = new Vector3d(maxBounds).sub(minBounds);
        double radius = Math.min(size.x, Math.min(size.y, size.z)) / 2.0D;

        return (4.0D / 3.0D) * Math.PI * radius * radius * radius; // Volume of a Sphere V = (4/3) * PI * r^3
    }

    @Override
    public Matrix4d computeInertiaTensor(Vector3d minBounds, Vector3d maxBounds, double mass) {
        Vector3d size = new Vector3d(maxBounds).sub(minBounds);
        double radius = Math.min(size.x, Math.min(size.y, size.z)) / 2.0D;

        double inertia = (2.0D / 5.0D) * mass * radius * radius; // Solid Sphere Inertia I = (2/5) * m * r^2, for all axes

        return new Matrix4d().identity()
                .m00(inertia)
                .m11(inertia)
                .m22(inertia);
    }

    public int getSegments() {
        return segments;
    }

    public int getRings() {
        return rings;
    }
}
