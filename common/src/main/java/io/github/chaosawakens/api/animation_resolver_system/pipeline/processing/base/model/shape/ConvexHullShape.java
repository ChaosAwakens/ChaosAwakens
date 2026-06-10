package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.shape;

import io.github.chaosawakens.util.MathUtil;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntLists;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import org.joml.Vector3d;

import java.util.List;

/**
 * Convex hull {@link CollisionShape} implementation, represented as an arbitrary convex polyhedron collision shape
 * defined by a set of vertices with explicit face topology for accurate SAT, GJK, containment, and rendering.
 * <br></br>
 * Unlike the primitive shapes (e.g. {@link BoxShape} and {@link CapsuleShape}) which generate vertices from bounds, a
 * {@code ConvexHullShape} (CHS) is defined by an explicit set of provided vertices that form a convex hull. Min/max bounds
 * passed into methods are used as a fallback if no explicit vertices are provided (effectively treating this shape like
 * a {@link BoxShape}.
 * <br></br>
 * Faces are stored as triangle indices in {@link #faceIndices}, where each group of 3 consecutive ints form one triangle
 * face. Face normals and edge directions are derived from this topology (as opposed to, say, being brute-forced via
 * vertex enumeration or smth else).
 * <br></br>
 * Should provide sufficient data for both GJK and SAT (EPA by extension of GJK).
 *
 * @see <a href="https://cp-algorithms.com/geometry/convex-hull.html">Algorithms for Competitive Programming - Convex Hull construction</a>
 * @see <a href="https://en.wikipedia.org/wiki/Convex_hull">Wikipedia - Convex hull</a>
 * @see <a href="https://usaco.guide/plat/convex-hull">USACO Guide - Convex Hull</a>
 * @see <a href="https://www.geeksforgeeks.org/dsa/convex-hull-algorithm/">GeeksforGeeks - Convex Hull algorithm</a>
 */
public class ConvexHullShape implements CollisionShape { // TODO This is a primary culprit, but shapes in general need to be rigorously optimized
    public static final double EPSILON = 1.0E-10D;
    public static final double DEDUP = 0.999D; // Edge deduplication threshold; cos(~2.6 deg)
    private final List<Vector3d> hullVertices;
    private final IntList faceIndices; // Triangle indices (every 3 consecutive ints represent a single face)
    private final List<Vector3d> cachedFaceNormals;
    private final List<Vector3d> cachedEdgeDirections;
    private final Vector3d cachedCenter;

    public ConvexHullShape(List<Vector3d> vertices, IntList faceIndices) {
        if (faceIndices.size() % 3 != 0)
            throw new IllegalArgumentException(String.format("Attempted to construct a ConvexHullShape with an invalid number of face indices: %d (must be a multiple of 3)", faceIndices.size()));

        this.faceIndices = IntLists.unmodifiable(new IntArrayList(faceIndices));

        ObjectArrayList<Vector3d> tmp = new ObjectArrayList<>(vertices.size());

        for (Vector3d vertex : vertices) {
            tmp.add(new Vector3d(vertex)); // We wanna prevent any potential mutation of the original vertices
        }

        this.hullVertices = ObjectLists.unmodifiable(tmp);
        this.cachedFaceNormals = computeFaceNormals(faceIndices, hullVertices);
        this.cachedEdgeDirections = computeEdgeDirections(faceIndices, hullVertices);
        this.cachedCenter = computeCachedCenter(hullVertices);
    }

    public ConvexHullShape(List<Vector3d> vertices) {
        this(vertices, new IntArrayList());
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.CONVEX_HULL;
    }

    @Override
    public List<Vector3d> generateLocalVertices(Vector3d minBounds, Vector3d maxBounds) {
        if (!hullVertices.isEmpty()) {
            List<Vector3d> result = new ObjectArrayList<>(hullVertices.size());

            for (Vector3d vertex : hullVertices) result.add(new Vector3d(vertex));

            return result;
        } else return BoxShape.INSTANCE.generateLocalVertices(minBounds, maxBounds);
    }

    @Override
    public Vector3d computeHalfExtents(Vector3d minBounds, Vector3d maxBounds) {
        if (hullVertices.isEmpty()) return BoxShape.INSTANCE.computeHalfExtents(minBounds, maxBounds);

        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, minZ = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE, maxY = -Double.MAX_VALUE, maxZ = -Double.MAX_VALUE;

        for (Vector3d vertex : hullVertices) {
            minX = Math.min(minX, vertex.x);
            minY = Math.min(minY, vertex.y);
            minZ = Math.min(minZ, vertex.z);
            maxX = Math.max(maxX, vertex.x);
            maxY = Math.max(maxY, vertex.y);
            maxZ = Math.max(maxZ, vertex.z);
        }

        return new Vector3d((maxX - minX) / 2.0D, (maxY - minY) / 2.0D, (maxZ - minZ) / 2.0D);
    }

    @Override
    public Vector3d computeCenter(Vector3d minBounds, Vector3d maxBounds) {
        return hullVertices.isEmpty()
                ? CollisionShape.super.computeCenter(minBounds, maxBounds)
                : cachedCenter;
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
    public boolean containsPoint(Vector3d point, Vector3d minBounds, Vector3d maxBounds) {
        if (faceIndices.isEmpty() || hullVertices.size() < 4)
            return BoxShape.INSTANCE.containsPoint(point, minBounds, maxBounds);

        for (int faceIdx = 0; faceIdx < faceIndices.size(); faceIdx += 3) { // A point is considered to be inside a convex hull if it happens to be on the inward side of every plane
            Vector3d a = hullVertices.get(faceIndices.getInt(faceIdx));
            Vector3d b = hullVertices.get(faceIndices.getInt(faceIdx + 1));
            Vector3d c = hullVertices.get(faceIndices.getInt(faceIdx + 2));

            Vector3d normal = new Vector3d(b).sub(a).cross(new Vector3d(c).sub(a));
            double len = normal.length();

            if (len < EPSILON) continue;

            normal.div(len);

            double d = new Vector3d(point).sub(a).dot(normal); // If point is on the positive (outward) side of any face, it's outside

            if (d > EPSILON) return false;
        }

        return true;
    }

    @Override
    public Vector3d closestPoint(Vector3d point, Vector3d minBounds, Vector3d maxBounds) {
        if (hullVertices.isEmpty()) return BoxShape.INSTANCE.closestPoint(point, minBounds, maxBounds);
        if (containsPoint(point, minBounds, maxBounds)) return new Vector3d(point);

        // Project onto each face triangle and find the closest point
        Vector3d closestPoint = null;
        double minDistSq = Double.MAX_VALUE;

        if (!faceIndices.isEmpty()) {
            for (int faceIdx = 0; faceIdx < faceIndices.size(); faceIdx += 3) {
                Vector3d a = hullVertices.get(faceIndices.getInt(faceIdx));
                Vector3d b = hullVertices.get(faceIndices.getInt(faceIdx + 1));
                Vector3d c = hullVertices.get(faceIndices.getInt(faceIdx + 2));

                Vector3d closestTrianglePoint = MathUtil.closestPointOnTriangle(point, a, b, c);
                double dSq = closestTrianglePoint.distanceSquared(point);

                if (dSq < minDistSq) {
                    minDistSq = dSq;
                    closestPoint = closestTrianglePoint;
                }
            }
        } else { // Fallback to closest vertex
            for (Vector3d vertex : hullVertices) {
                double dSq = vertex.distanceSquared(point);

                if (dSq < minDistSq) {
                    minDistSq = dSq;
                    closestPoint = new Vector3d(vertex);
                }
            }
        }

        return closestPoint != null ? closestPoint : new Vector3d(point);
    }

    @Override
    public int getVertexCount() {
        return Math.max(hullVertices.size(), 8);
    }

    @Override
    public double computeVolume(Vector3d minBounds, Vector3d maxBounds) {
        if (faceIndices.isEmpty() || hullVertices.size() < 4)
            return CollisionShape.super.computeVolume(minBounds, maxBounds);

        double volume = 0.0D;

        for (int faceIdx = 0; faceIdx < faceIndices.size(); faceIdx += 3) {
            Vector3d a = hullVertices.get(faceIndices.getInt(faceIdx));
            Vector3d b = hullVertices.get(faceIndices.getInt(faceIdx + 1));
            Vector3d c = hullVertices.get(faceIndices.getInt(faceIdx + 2));

            volume += a.dot(new Vector3d(b).cross(c));
        }

        return Math.abs(volume) / 6.0D; // Signed tetrahedra from origin V = (1/6) * |sum(a dot (b x c) )|
    }

    public List<Vector3d> getHullVertices() {
        return hullVertices;
    }

    public Vector3d getCachedCenter() {
        return new Vector3d(cachedCenter);
    }

    public IntList getFaceIndices() {
        return faceIndices;
    }

    public int getFaceCount() {
        return faceIndices.size() / 3;
    }

    public static ConvexHullShape fromPointCloud(List<Vector3d> points) {
        return points.size() < 4
                ? new ConvexHullShape(points) // Approximate faces
                : buildQuickhull(points);
    }

    private static List<Vector3d> computeFaceNormals(IntList faceIndices, List<Vector3d> hullVertices) {
        ObjectArrayList<Vector3d> normals = new ObjectArrayList<>();

        if (faceIndices.isEmpty()) { // Js use cardinal axes atp
            normals.add(new Vector3d(1.0D, 0.0D, 0.0D));
            normals.add(new Vector3d(0.0D, 1.0D, 0.0D));
            normals.add(new Vector3d(0.0D, 0.0D, 1.0D));

            return normals;
        }

        for (int faceIdx = 0; faceIdx < faceIndices.size(); faceIdx += 3) {
            Vector3d a = hullVertices.get(faceIndices.getInt(faceIdx));
            Vector3d b = hullVertices.get(faceIndices.getInt(faceIdx + 1));
            Vector3d c = hullVertices.get(faceIndices.getInt(faceIdx + 2));

            Vector3d normal = new Vector3d(b).sub(a).cross(new Vector3d(c).sub(a));
            double len = normal.length();

            if (len < EPSILON) continue;

            normal.div(len);

            boolean duplicate = false;

            for (Vector3d existing : normals) {
                if (Math.abs(existing.dot(normal)) > DEDUP) {
                    duplicate = true;
                    break;
                }
            }

            if (!duplicate) normals.add(normal);
        }

        if (normals.isEmpty()) { // Cardinal axes (again)
            normals.add(new Vector3d(1.0D, 0.0D, 0.0D));
            normals.add(new Vector3d(0.0D, 1.0D, 0.0D));
            normals.add(new Vector3d(0.0D, 0.0D, 1.0D));
        }

        return ObjectLists.unmodifiable(normals);
    }

    private static List<Vector3d> computeEdgeDirections(IntList faceIndices, List<Vector3d> hullVertices) {
        ObjectArrayList<Vector3d> edges = new ObjectArrayList<>();

        if (faceIndices.isEmpty()) { // Should be obv atp (cardinal axes)
            edges.add(new Vector3d(1.0D, 0.0D, 0.0D));
            edges.add(new Vector3d(0.0D, 1.0D, 0.0D));
            edges.add(new Vector3d(0.0D, 0.0D, 1.0D));

            return edges;
        }

        for (int faceIdx = 0; faceIdx < faceIndices.size(); faceIdx += 3) { // Extract unique edge directions from face triangle edges only
            int a = faceIndices.getInt(faceIdx);
            int b = faceIndices.getInt(faceIdx + 1);
            int c = faceIndices.getInt(faceIdx + 2);

            addEdgeIfUnique(edges, hullVertices.get(a), hullVertices.get(b), DEDUP);
            addEdgeIfUnique(edges, hullVertices.get(b), hullVertices.get(c), DEDUP);
            addEdgeIfUnique(edges, hullVertices.get(c), hullVertices.get(a), DEDUP);
        }

        if (edges.isEmpty()) { // Cardinal axes moment
            edges.add(new Vector3d(1.0D, 0.0D, 0.0D));
            edges.add(new Vector3d(0.0D, 1.0D, 0.0D));
            edges.add(new Vector3d(0.0D, 0.0D, 1.0D));
        }

        return ObjectLists.unmodifiable(edges);
    }

    private static void addEdgeIfUnique(List<Vector3d> edges, Vector3d a, Vector3d b, double dedup) {
        Vector3d dir = new Vector3d(b).sub(a);
        double len = dir.length();

        if (len < EPSILON) return;

        dir.div(len);

        for (Vector3d existing : edges) {
            if (Math.abs(existing.dot(dir)) > dedup) return;
        }

        edges.add(dir);
    }

    private static ConvexHullShape buildQuickhull(List<Vector3d> points) { // TODO Potentially optimize, this is expensive af
        int[] extrema = findExtremePoints(points); // Find initial tetrahedron from 4 non-coplanar extreme points
        int ia = extrema[0], ib = extrema[1], ic = -1, id = -1;

        // Find point most distant from line ab
        Vector3d a = points.get(ia), b = points.get(ib);
        Vector3d ab = new Vector3d(b).sub(a);
        double maxDist = -1.0D;

        for (int pointIdx = 0; pointIdx < points.size(); pointIdx++) {
            if (pointIdx == ia || pointIdx == ib) continue;

            Vector3d ap = new Vector3d(points.get(pointIdx)).sub(a);
            Vector3d cross = new Vector3d(ab).cross(ap);
            double dist = cross.lengthSquared();

            if (dist > maxDist) {
                maxDist = dist;
                ic = pointIdx;
            }
        }

        if (ic == -1) return new ConvexHullShape(points);

        // Find point most distant from plane abc
        Vector3d c = points.get(ic);
        Vector3d ac = new Vector3d(c).sub(a);
        Vector3d planeNormal = new Vector3d(ab).cross(ac);

        double planeLen = planeNormal.length();

        if (planeLen < EPSILON) return new ConvexHullShape(points);

        planeNormal.div(planeLen);

        double planeD = -planeNormal.dot(a);

        maxDist = -1.0D;

        for (int pointIdx = 0; pointIdx < points.size(); pointIdx++) {
            if (pointIdx == ia || pointIdx == ib || pointIdx == ic) continue;

            double dist = Math.abs(planeNormal.dot(points.get(pointIdx)) + planeD);

            if (dist > maxDist) {
                maxDist = dist;
                id = pointIdx;
            }
        }

        if (id == -1 || maxDist < EPSILON) return new ConvexHullShape(points);

        // Build initial tetrahedron (4 faces w/outward normals)
        List<Vector3d> vertices = new ObjectArrayList<>();

        vertices.add(new Vector3d(points.get(ia)));
        vertices.add(new Vector3d(points.get(ib)));
        vertices.add(new Vector3d(points.get(ic)));
        vertices.add(new Vector3d(points.get(id)));

        // Ensure consistent winding (i.e. if d is on positive side of abc, flip abc)
        Vector3d d = points.get(id);

        if (planeNormal.dot(d) + planeD > 0) { // Swap b and c to flip winding
            Vector3d tmp = vertices.get(1);

            vertices.set(1, vertices.get(2));
            vertices.set(2, tmp);
        }

        // Initial 4 faces (outward normals w/CCW winding from outside)
        IntList faces = new IntArrayList();

        faces.add(0);
        faces.add(1);
        faces.add(2);  // Face 0 (bottom)
        faces.add(0);
        faces.add(3);
        faces.add(1);  // Face 1
        faces.add(1);
        faces.add(3);
        faces.add(2);  // Face 2
        faces.add(2);
        faces.add(3);
        faces.add(0);  // Face 3

        ensureOutwardNormals(vertices, faces); // Needed for correctness

        // Incrementally add any remaining points
        for (int pi = 0; pi < points.size(); pi++) { // For each outside point, find visible faces, remove them, then create new faces
            if (pi == ia || pi == ib || pi == ic || pi == id) continue;

            Vector3d p = points.get(pi);

            // Check if point is outside any face
            IntList visibleFaces = new IntArrayList();

            for (int fi = 0; fi < faces.size(); fi += 3) {
                Vector3d fa = vertices.get(faces.getInt(fi));
                Vector3d fb = vertices.get(faces.getInt(fi + 1));
                Vector3d fc = vertices.get(faces.getInt(fi + 2));
                Vector3d fn = new Vector3d(fb).sub(fa).cross(new Vector3d(fc).sub(fa));

                double fDot = new Vector3d(p).sub(fa).dot(fn);

                if (fDot > EPSILON) visibleFaces.add(fi);
            }

            if (visibleFaces.isEmpty()) continue; // Inside hull

            // Find horizon edges (edges shared by exactly one visible face)
            List<int[]> horizonEdges = new ObjectArrayList<>();

            for (int fi : visibleFaces) {
                for (int e = 0; e < 3; e++) {
                    int ei0 = faces.getInt(fi + e);
                    int ei1 = faces.getInt(fi + (e + 1) % 3);

                    // Check if reverse edge exists in another visible face
                    boolean shared = false;

                    for (int fj : visibleFaces) {
                        if (fj == fi) continue;

                        for (int e2 = 0; e2 < 3; e2++) {
                            int ej0 = faces.getInt(fj + e2);
                            int ej1 = faces.getInt(fj + (e2 + 1) % 3);

                            if (ei0 == ej1 && ei1 == ej0) {
                                shared = true;
                                break;
                            }
                        }

                        if (shared) break;
                    }

                    if (!shared) horizonEdges.add(new int[]{ei0, ei1});
                }
            }

            // Remove visible faces (reverse iteration order to preserve indices)
            visibleFaces.sort(null);

            for (int visibleFaceIdx = visibleFaces.size() - 1; visibleFaceIdx >= 0; visibleFaceIdx--) {
                int visibleFaceVertex = visibleFaces.getInt(visibleFaceIdx);

                faces.removeInt(visibleFaceVertex + 2);
                faces.removeInt(visibleFaceVertex + 1);
                faces.removeInt(visibleFaceVertex);
            }

            // Add new point
            int newIdx = vertices.size();

            vertices.add(new Vector3d(p));

            // Create new faces from horizon edges to new point
            for (int[] edge : horizonEdges) {
                faces.add(edge[0]);
                faces.add(edge[1]);
                faces.add(newIdx);
            }

            ensureOutwardNormals(vertices, faces); // Again for new faces
        }

        return new ConvexHullShape(vertices, faces);
    }

    private static int[] findExtremePoints(List<Vector3d> points) {
        int bestA = 0, bestB = 1;
        double bestDistSq = -1.0D;

        // Find min/max on each axis, then pick the pair with greatest distance
        int[] candidates = new int[6];
        double[] vals = {
                Double.MAX_VALUE, -Double.MAX_VALUE,
                Double.MAX_VALUE, -Double.MAX_VALUE,
                Double.MAX_VALUE, -Double.MAX_VALUE
        };

        for (int pointIdx = 0; pointIdx < points.size(); pointIdx++) {
            Vector3d curPoint = points.get(pointIdx);

            if (curPoint.x < vals[0]) {
                vals[0] = curPoint.x;
                candidates[0] = pointIdx;
            }
            if (curPoint.x > vals[1]) {
                vals[1] = curPoint.x;
                candidates[1] = pointIdx;
            }
            if (curPoint.y < vals[2]) {
                vals[2] = curPoint.y;
                candidates[2] = pointIdx;
            }
            if (curPoint.y > vals[3]) {
                vals[3] = curPoint.y;
                candidates[3] = pointIdx;
            }
            if (curPoint.z < vals[4]) {
                vals[4] = curPoint.z;
                candidates[4] = pointIdx;
            }
            if (curPoint.z > vals[5]) {
                vals[5] = curPoint.z;
                candidates[5] = pointIdx;
            }
        }

        for (int axialCandidate = 0; axialCandidate < 6; axialCandidate++) {
            for (int nextAxialCandidate = axialCandidate + 1; nextAxialCandidate < 6; nextAxialCandidate++) {
                double dSq = points.get(candidates[axialCandidate]).distanceSquared(points.get(candidates[nextAxialCandidate]));

                if (dSq > bestDistSq) {
                    bestDistSq = dSq;
                    bestA = candidates[axialCandidate];
                    bestB = candidates[nextAxialCandidate];
                }
            }
        }

        return new int[]{bestA, bestB};
    }

    private static void ensureOutwardNormals(List<Vector3d> vertices, IntList faces) {
        Vector3d centroid = new Vector3d();

        for (Vector3d vertex : vertices) centroid.add(vertex);

        centroid.div(vertices.size());

        for (int faceIdx = 0; faceIdx < faces.size(); faceIdx += 3) {
            Vector3d a = vertices.get(faces.getInt(faceIdx));
            Vector3d b = vertices.get(faces.getInt(faceIdx + 1));
            Vector3d c = vertices.get(faces.getInt(faceIdx + 2));

            Vector3d normal = new Vector3d(b).sub(a).cross(new Vector3d(c).sub(a));
            Vector3d toFace = new Vector3d(a).sub(centroid);

            if (normal.dot(toFace) < 0) { // Flip winding
                int tmp = faces.getInt(faceIdx + 1);

                faces.set(faceIdx + 1, faces.getInt(faceIdx + 2));
                faces.set(faceIdx + 2, tmp);
            }
        }
    }

    private static Vector3d computeCachedCenter(List<Vector3d> hullVertices) {
        double cx = 0.0D, cy = 0.0D, cz = 0.0D;

        for (Vector3d vertex : hullVertices) {
            cx += vertex.x;
            cy += vertex.y;
            cz += vertex.z;
        }

        int hullVertexCount = hullVertices.size();

        return new Vector3d(cx / hullVertexCount, cy / hullVertexCount, cz / hullVertexCount);
    }
}