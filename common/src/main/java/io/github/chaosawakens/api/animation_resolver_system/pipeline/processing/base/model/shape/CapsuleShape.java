package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.shape;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import org.joml.Vector3d;

import java.util.List;

/**
 * Capsule {@link CollisionShape} implementation, represented as a cylinder with hemispherical caps.
 * <br></br>
 * Vertices are sampled around the cylinder and hemisphere. "Height" refers to the distance from the center of the
 * bottom hemisphere to the center of the top hemisphere.
 * <br></br>
 * The vertex-based approach here is for SAT compatibility with other shapes (also too lazy to consider how to implement
 * "perfect" curves in general, especially considering how spheres are supposed to have infinite edges and vertices and
 * all, which is unnatural for Minecraft, but then again, any non-{@link BoxShape} is kinda pushing it already).
 */
public class CapsuleShape implements CollisionShape {
    public static final CapsuleShape DEFAULT = new CapsuleShape(AlignmentAxis.Y, 8, 4);
    public static final double EPSILON = 1.0E-10D;
    private final AlignmentAxis alignmentAxis;
    private final int ringSegments;
    private final int capSegments;
    private final List<Vector3d> cachedFaceNormals;

    public CapsuleShape(AlignmentAxis alignmentAxis, int ringSegments, int capSegments) {
        this.alignmentAxis = alignmentAxis;
        this.ringSegments = Math.max(4, ringSegments);
        this.capSegments = Math.max(2, capSegments);
        this.cachedFaceNormals = computeFaceNormals(alignmentAxis, ringSegments);
    }

    private static List<Vector3d> computeFaceNormals(AlignmentAxis alignmentAxis, int ringSegments) { // For SAT with capsules, we use the alignmentAxis direction plus radial normals to approximate (actual collision detection for real capsules use some insane specialized algorithms that aren't worth implementing here)
        ObjectArrayList<Vector3d> normals = new ObjectArrayList<>(ringSegments + 1);

        normals.add(switch (alignmentAxis) { // alignmentAxis normal
            case X -> new Vector3d(1.0D, 0.0D, 0.0D);
            case Z -> new Vector3d(0.0D, 0.0D, 1.0D);
            default -> new Vector3d(0.0D, 1.0D, 0.0D);
        });

        // Radial normals (around the cylinder)
        for (int segment = 0; segment < ringSegments; segment++) {
            double angle = (2.0D * Math.PI * segment) / ringSegments;
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);

            Vector3d normal = switch (alignmentAxis) {
                case X -> new Vector3d(0.0D, cos, sin);
                case Z -> new Vector3d(cos, sin, 0.0D);
                default -> new Vector3d(cos, 0.0D, sin);
            };

            normals.add(normal);
        }

        return ObjectLists.unmodifiable(normals);
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.CAPSULE;
    }

    @Override
    public List<Vector3d> generateLocalVertices(Vector3d minBounds, Vector3d maxBounds) {
        List<Vector3d> vertices = new ObjectArrayList<>(getVertexCount());

        Vector3d center = computeCenter(minBounds, maxBounds);
        Vector3d size = new Vector3d(maxBounds).sub(minBounds);

        // Calculate radius and half-height based on alignmentAxis
        double radius, halfHeight;
        switch (alignmentAxis) {
            case X -> {
                radius = Math.min(size.y, size.z) / 2.0D;
                halfHeight = Math.max(0.0D, size.x / 2.0D - radius);
            }
            case Z -> {
                radius = Math.min(size.x, size.y) / 2.0D;
                halfHeight = Math.max(0.0D, size.z / 2.0D - radius);
            }
            default -> { // Y alignmentAxis (default)
                radius = Math.min(size.x, size.z) / 2.0D;
                halfHeight = Math.max(0.0D, size.y / 2.0D - radius);
            }
        }

        // Generate cylinder rings (just the top and the bottom)
        for (int ring = 0; ring <= 1; ring++) {
            double axisOffset = (ring == 0) ? -halfHeight : halfHeight;

            generateRingSegments(vertices, center, radius, axisOffset);
        }

        // Generate hemisphere caps (0 = bottom, 1 = top)
        for (int cap = 0; cap < 2; cap++) {
            double capSign = (cap == 0) ? -1.0D : 1.0D;
            double capCenter = capSign * halfHeight;

            for (int latRing = 1; latRing <= capSegments; latRing++) {
                double phi = (Math.PI / 2.0D) * latRing / capSegments; // 0 - 90 deg
                double ringRadius = radius * Math.cos(phi);
                double axisOffset = capCenter + capSign * radius * Math.sin(phi);

                generateRingSegments(vertices, center, ringRadius, axisOffset);
            }

            // Cap pole vertex
            Vector3d pole = switch (alignmentAxis) {
                case X -> new Vector3d(center.x + capCenter + capSign * radius, center.y, center.z);
                case Z -> new Vector3d(center.x, center.y, center.z + capCenter + capSign * radius);
                default -> new Vector3d(center.x, center.y + capCenter + capSign * radius, center.z);
            };

            vertices.add(pole);
        }

        return vertices;
    }

    @Override
    public Vector3d computeHalfExtents(Vector3d minBounds, Vector3d maxBounds) {
        return new Vector3d( // Same as BoxShape
                (maxBounds.x - minBounds.x) / 2.0D,
                (maxBounds.y - minBounds.y) / 2.0D,
                (maxBounds.z - minBounds.z) / 2.0D
        );
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

        double radius, halfHeight;
        double axisCoord, radialDistSq;

        switch (alignmentAxis) {
            case X -> {
                radius = Math.min(size.y, size.z) / 2.0D;
                halfHeight = Math.max(0, size.x / 2.0D - radius);
                axisCoord = point.x - center.x;
                radialDistSq = (point.y - center.y) * (point.y - center.y) + (point.z - center.z) * (point.z - center.z);
            }
            case Z -> {
                radius = Math.min(size.x, size.y) / 2.0D;
                halfHeight = Math.max(0, size.z / 2.0D - radius);
                axisCoord = point.z - center.z;
                radialDistSq = (point.x - center.x) * (point.x - center.x) +
                        (point.y - center.y) * (point.y - center.y);
            }
            default -> {
                radius = Math.min(size.x, size.z) / 2.0D;
                halfHeight = Math.max(0, size.y / 2.0D - radius);
                axisCoord = point.y - center.y;
                radialDistSq = (point.x - center.x) * (point.x - center.x) + (point.z - center.z) * (point.z - center.z);
            }
        }

        double radiusSq = radius * radius;

        // Cylinder portion
        if (Math.abs(axisCoord) <= halfHeight) return radialDistSq <= radiusSq;

        // Check dist to hemisphere portions
        double hemisphereCenter = (axisCoord > 0.0D) ? halfHeight : -halfHeight;
        double axisDistToCenter = axisCoord - hemisphereCenter;
        double totalDistSq = radialDistSq + axisDistToCenter * axisDistToCenter;

        return totalDistSq <= radiusSq;
    }

    @Override
    public Vector3d closestPoint(Vector3d point, Vector3d minBounds, Vector3d maxBounds) {
        Vector3d center = computeCenter(minBounds, maxBounds);
        Vector3d size = new Vector3d(maxBounds).sub(minBounds);

        double radius, halfHeight;
        switch (alignmentAxis) {
            case X -> {
                radius = Math.min(size.y, size.z) / 2.0D;
                halfHeight = Math.max(0, size.x / 2.0D - radius);
            }
            case Z -> {
                radius = Math.min(size.x, size.y) / 2.0D;
                halfHeight = Math.max(0, size.z / 2.0D - radius);
            }
            default -> {
                radius = Math.min(size.x, size.z) / 2.0D;
                halfHeight = Math.max(0, size.y / 2.0D - radius);
            }
        }

        // Find whichever closest point on the capsule's line segment
        Vector3d lineStart, lineEnd;

        switch (alignmentAxis) {
            case X -> {
                lineStart = new Vector3d(center.x - halfHeight, center.y, center.z);
                lineEnd = new Vector3d(center.x + halfHeight, center.y, center.z);
            }
            case Z -> {
                lineStart = new Vector3d(center.x, center.y, center.z - halfHeight);
                lineEnd = new Vector3d(center.x, center.y, center.z + halfHeight);
            }
            default -> {
                lineStart = new Vector3d(center.x, center.y - halfHeight, center.z);
                lineEnd = new Vector3d(center.x, center.y + halfHeight, center.z);
            }
        }

        // Project point onto line segment
        Vector3d lineDir = new Vector3d(lineEnd).sub(lineStart);
        double lineLength = lineDir.length();

        if (lineLength < EPSILON) lineDir.set(0.0D, 0.0D, 0.0D);
        else lineDir.div(lineLength);

        Vector3d toPoint = new Vector3d(point).sub(lineStart);
        double projFactor = Math.max(0, Math.min(lineLength, toPoint.dot(lineDir)));

        Vector3d closestOnLine = new Vector3d(lineStart).add(new Vector3d(lineDir).mul(projFactor));

        // Extend from line to surface
        Vector3d toSurface = new Vector3d(point).sub(closestOnLine);
        double dist = toSurface.length();

        if (dist < EPSILON) { // Pick arbitrary perpendicular direction, cuz we're already on alignmentAxis
            toSurface = switch (alignmentAxis) {
                case X -> new Vector3d(0.0D, 1.0D, 0.0D);
                case Z -> new Vector3d(1.0D, 0.0D, 0.0D);
                default -> new Vector3d(1.0D, 0.0D, 0.0D);
            };
        } else toSurface.div(dist);

        return closestOnLine.add(toSurface.mul(radius));
    }

    @Override
    public int getVertexCount() { // 2 cylinder rings + 2 hemispheres (each with capSegments rings + 1 pole)
        int cylinderVertices = 2 * ringSegments;
        int hemisphereVertices = 2 * (capSegments * ringSegments + 1);

        return cylinderVertices + hemisphereVertices;
    }

    @Override
    public double computeVolume(Vector3d minBounds, Vector3d maxBounds) {
        Vector3d size = new Vector3d(maxBounds).sub(minBounds);
        double radius, height;

        switch (alignmentAxis) {
            case X -> {
                radius = Math.min(size.y, size.z) / 2.0D;
                height = Math.max(0, size.x - 2 * radius);
            }
            case Z -> {
                radius = Math.min(size.x, size.y) / 2.0D;
                height = Math.max(0.0D, size.z - 2.0D * radius);
            }
            default -> {
                radius = Math.min(size.x, size.z) / 2.0D;
                height = Math.max(0.0D, size.y - 2.0D * radius);
            }
        }

        double cylinderVolume = Math.PI * radius * radius * height; // Capsule Volume = Cylinder + Sphere
        double sphereVolume = (4.0 / 3.0) * Math.PI * radius * radius * radius;

        return cylinderVolume + sphereVolume;
    }

    public AlignmentAxis getAlignmentAxis() {
        return alignmentAxis;
    }

    public int getRingSegments() {
        return ringSegments;
    }

    public int getCapSegments() {
        return capSegments;
    }

    private void generateRingSegments(List<Vector3d> vertices, Vector3d center, double radius, double axisOffset) {
        for (int i = 0; i < ringSegments; i++) {
            double angle = (2.0D * Math.PI * i) / ringSegments;
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);

            Vector3d vertex = switch (alignmentAxis) {
                case X -> new Vector3d(center.x + axisOffset, center.y + radius * cos, center.z + radius * sin);
                case Z -> new Vector3d(center.x + radius * cos, center.y + radius * sin, center.z + axisOffset);
                default -> new Vector3d(center.x + radius * cos, center.y + axisOffset, center.z + radius * sin);
            };

            vertices.add(vertex);
        }
    }

    public enum AlignmentAxis { // Alignment axis to describe which axis the capsule is mutated relative-to
        X,
        Y,
        Z
    }
}