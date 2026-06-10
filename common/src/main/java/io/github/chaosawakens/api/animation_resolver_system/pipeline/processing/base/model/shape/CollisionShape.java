package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.shape;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.ModelCoordinateData;
import org.joml.Matrix4d;
import org.joml.Vector3d;

import java.util.List;

/**
 * Defines collision shape topology for use in conjunction with {@link ModelCoordinateData}.
 *
 * @apiNote All shapes are defined relative to their local origin (0, 0, 0).
 *
 * @implSpec Implementors should consider providing vertices in local space (i.e. untransformed), and that vertices form
 * a hull for SAT implementations to work as-expected. Half-extents should be as accurate as possible for broad-phase checks
 * (i.e. AABBs) to work as-expected, too.
 * <br></br>
 * If a shape is concave (or otherwise non-convex), it should provide helpers and decompose into convex components for
 * collision testing.
 * <br></br>
 * Shapes should be immutable or stateless. For stateful shape impls, bound information should NOT be stored, as it's
 * parameterized in the core methods provided by this {@code interface}. Composite shapes should prefer immutable builder
 * patterns, and data should be eagerly-computed where possible.
 *
 * @implNote Composite shape impls of this {@code interface} may not necessarily utilise the bound parameters each method
 * here specifies in favour of caching that information for children (or other encapsulated data), thus making them (the
 * method parameters) {@code null}-safe.
 *
 * @see ModelCoordinateData
 */
public interface CollisionShape {

    /**
     * Gets the {@link ShapeType} representing this shape.
     *
     * @return This shape's {@link ShapeType}.
     */
    ShapeType getShapeType();

    /**
     * Generates the vertices that define this shape in <b>LOCAL</b> (untransformed) space.
     *
     * @param minBounds The minimum bounds (corner) of the shape's bounding box.
     * @param maxBounds The maximum bounds (corner) of the shape's bounding box.
     *
     * @return An exhaustive {@link List} of the shape's vertices, in local space (mutable copy).
     */
    List<Vector3d> generateLocalVertices(Vector3d minBounds, Vector3d maxBounds);

    /**
     * Computes the half-extents of this shape's oriented bounding box (OBB).
     * <br></br>
     * Half-extents are the distances from the center to each face along the local axes.
     * For a box with dimensions (width = 4 [x], height = 6 [y], depth = 2 [z]), half-extents are (2, 3, 1).
     *
     * @param minBounds The minimum shape bounds.
     * @param maxBounds The maximum shape bounds.
     *
     * @return Half-extents, represented in a {@link Vector3d} (always positive).
     */
    Vector3d computeHalfExtents(Vector3d minBounds, Vector3d maxBounds);

    /**
     * Computes the geometric center of this shape instance.
     * <br></br>
     * For symmetric shapes, this is typically the midpoint of min/max bounds.
     * For asymmetric shapes (e.g., offset capsule), this may be different.
     *
     * @param minBounds The minimum shape bounds.
     * @param maxBounds The maximum shape bounds.
     *
     * @return The geometric center, represented in a {@link Vector3d} (in local space).
     */
    default Vector3d computeCenter(Vector3d minBounds, Vector3d maxBounds) {
        return new Vector3d(
                (minBounds.x + maxBounds.x) / 2.0D,
                (minBounds.y + maxBounds.y) / 2.0D,
                (minBounds.z + maxBounds.z) / 2.0D
        );
    }

    /**
     * Returns the unique face normals of this shape for Separating AlignmentAxis Theorem (SAT) collision detection purposes.
     * <br></br>
     * SAT requires testing against all <u>unique</u> face normals of both shapes. For a box, this returns 3 normals
     * (X, Y, Z axes), whereas for more complex shapes, it would return all "unique" face normals.
     *
     * @return A {@link List} of all face normals, represented in {@link Vector3d} objects (unit vectors).
     */
    List<Vector3d> getFaceNormals();

    /**
     * Returns the unique edges of this shape for Separating AlignmentAxis Theorem (SAT) collision detection purposes.
     *
     * @return A {@link List} of <u>unique</u> edge direction vectors, represented in {@link Vector3d} objects.
     *
     * @implNote This method (in its impls) would usually compute edge directions, not positions. For edge-edge
     * collision tests in SAT, we need cross products of edges from both shapes.
     */
    List<Vector3d> getEdgeDirections();

    /**
     * Tests if a provided {@linkplain Vector3d point} is inside this shape (in local space).
     *
     * @param point The point to test against (should be in local space).
     * @param minBounds The shape's minimum bounds.
     * @param maxBounds The shape's maximum bounds.
     *
     * @return {@code true} if the provided {@code point} is inside or on the surface of this shape, otherwise
     * {@code false}.
     *
     * @implNote Shapes that aggregate other shapes should delegate to their constituent shapes' implementations for
     * accurate results.
     *
     * @see CompoundUnionShape
     * @see CSGUnionShape
     */
    boolean containsPoint(Vector3d point, Vector3d minBounds, Vector3d maxBounds);

    /**
     * Computes the closest point on this shape's surface to a given point (in local space).
     *
     * @param point The point to test against (should be in local space).
     * @param minBounds The shape's minimum bounds.
     * @param maxBounds The shape's maximum bounds.
     *
     * @return The closest point on the shape's surface, represented as a {@link Vector3d}.
     */
    Vector3d closestPoint(Vector3d point, Vector3d minBounds, Vector3d maxBounds);

    /**
     * Returns the number of vertices this shape generates. Primarily useful for stuff like pre-allocating lists.
     *
     * @return This shape's expected vertex count. Usually a sum of all vertices if this is an aggregate shape.
     */
    int getVertexCount();

    /**
     * Returns an approximate volume of this shape.
     * <br></br>
     * Not really used at the moment, but if we ever get around to implementing more sophisticated IK, this could be
     * used for physics calculations (mass, inertia) to make things look natural, among other things.
     * <br></br>
     * Defaults to a box volume calculation.
     *
     * @param minBounds The shape's minimum bounds.
     * @param maxBounds The shape's maximum bounds.
     *
     * @return Approximate volume in cubic units (as provided).
     */
    default double computeVolume(Vector3d minBounds, Vector3d maxBounds) {
        Vector3d size = new Vector3d(maxBounds).sub(minBounds);

        return size.x * size.y * size.z;
    }

    /**
     * Returns the inertia tensor for this shape (assuming uniform density).
     * <br></br>
     * Like the method right above it ({@link #computeVolume(Vector3d, Vector3d)}), this isn't really used, but it still
     * kinda exists for the same purposes as that method. Implementations are currently not fully complete, either.
     * <br></br>
     * Defaults to a box's inertia tensor.
     *
     * @param minBounds The shape's minimum bounds.
     * @param maxBounds The shape's maximum bounds.
     * @param mass The "mass" of the object (it should probably be how "weighty" the object should be in this case).
     *
     * @return A 3x3 inertia tensor, represented as a {@link Matrix4d} (diagonal for symmetric shapes).
     */
    default Matrix4d computeInertiaTensor(Vector3d minBounds, Vector3d maxBounds, double mass) {
        Vector3d size = new Vector3d(maxBounds).sub(minBounds);

        double w2 = size.x * size.x;
        double h2 = size.y * size.y;
        double d2 = size.z * size.z;
        double factor = mass / 12.0D; // TODO Presumptuous af

        return new Matrix4d().identity()
                .m00(factor * (h2 + d2))
                .m11(factor * (w2 + d2))
                .m22(factor * (w2 + h2));
    }

    enum ShapeType {
        BOX, // Rectangular prism/OBB
        CAPSULE, // Cylinder with hemispherical caps
        SPHERE, // Icosphere
        CONVEX_HULL, // Arbitrary convex polyhedron (fancy speak for: non-standard/non-primitive convex shape)
        CSG_UNION, // Constructive Solid Geometry union of multiple OBBs (non-convex)
        COMPOUND_UNION, // Aggregate of multiple shapes
        CUSTOM // Other
    }
}