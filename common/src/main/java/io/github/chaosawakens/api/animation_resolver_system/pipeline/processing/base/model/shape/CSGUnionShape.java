package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.shape;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.ModelCoordinateData;
import io.github.chaosawakens.util.MathUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import org.joml.Matrix4d;
import org.joml.Vector3d;
import org.joml.Vector4d;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Constructive Solid Geometry (CSG) {@link CollisionShape} implementation.
 * <br></br>
 * Constructive Solid Geometry (CSG) is essentially the practice of fusing primitive shapes together using boolean
 * algebra (+ the math required to actually merge them into their final representative state). In this shape's case,
 * the fusion between shapes/MCDs is done using unions, which basically merges the "outer"/non-intersecting segments
 * together and culls intersecting volumes entirely from the final result (you can read more about this in the references
 * below).
 * <br></br>
 * This implementation treats each source primitive as a model-space OBB, which is more or less the natural
 * end-state of a transformed cube MCD after bone transforms, local pivots/rotations/scales, and bounds have all been
 * resolved. {@link #fromMCDs(List)} performs that conversion by sampling transformed basis points instead of trying to
 * decompose the final transform matrix, since decomposition gets annoying very quickly once pivots and accumulated bone
 * transforms make an appearance.
 * <br></br>
 * Internally, the shape keeps three different representations of the same union because they serve different parts of
 * the collision/debug pipeline:
 * <ul>
 *     <li>{@link SourceOBB}s are the authoritative primitives used for containment checks and support mapping.</li>
 *     <li>{@link Triangle}s provide a surface mesh for closest-point queries and flattened MCD vertex access.</li>
 *     <li>{@link Edge}s are clipped exterior edge segments used for clean-ish wireframe/debug rendering.</li>
 * </ul>
 * The exterior wireframe pass works by taking every raw OBB edge as a {@code [0, 1]} parametric segment, clipping that
 * segment against every other OBB's interior via slab intersection, and keeping only the portions that survive outside
 * all other sources. In other words, internal/intersecting edge portions get eaten, while visible boundary pieces remain
 * available through {@link #getEdges()}.
 * <br></br>
 * <b>Important:</b> This is a pragmatic CSG union tailored for animated model collision/debug geometry, not a full
 * general-purpose polygon "boolean solver", per-se. Triangles are currently generated per source OBB, supported primitives
 * are boxes only atm, and very tight/near-coplanar intersections can still produce tiny visual artifacts unless the numeric
 * tolerances and edge-snapping/orphan-edge cleanup logic are improved further.
 */
public class CSGUnionShape implements CollisionShape { // FIXME Still no clue whether it's the math here or in the test code for rendering, but small artifacts appear as a result of several close/possibly coplanar SourceOBBs clipping with each other and being "subtracted"/unionized (maybe we should try to perform edge-snapping (if we can confirm irregular gaps being present) or just eliminate orphaned edges(?). Idk, requires further testing)
    public static final double EXTERIOR_EDGE_CLIPPING_THRESHOLD = 1.0E-7D;
    public static final double SLAB_EPSILON = 1.0E-12D;
    public static final double DIRECTION_DEDUP = 1.0-4D;
    private final List<Edge> edges; // Clipped exterior edges (for wireframe)
    private final List<Triangle> triangles; // Full triangulation (for collision)
    private final List<SourceOBB> sourceOBBs; // Only boxes supported atm
    private final List<Vector3d> meshVertices; // Flattened vertices list (for MCD vertex usage purposes)
    private final List<Vector3d> faceNormals;
    private final List<Vector3d> edgeDirections;

    public CSGUnionShape(ObjectList<SourceOBB> obbs) {
        this.sourceOBBs = ObjectLists.unmodifiable(obbs);

        ObjectArrayList<Triangle> tempTriangles = new ObjectArrayList<>();
        ObjectArrayList<Edge> tempEdges = new ObjectArrayList<>();

        if (obbs.size() == 1) { // Short-circuit all the extra computation if possible (fair optimization imo)
            triangulateOBB(obbs.get(0), tempTriangles);
            obbEdges(obbs.get(0), tempEdges); // Single OBB (cuz all 12 edges are exterior)
        } else { // Let's go edge-segment clipping! (we want only exterior edges to survive)
            for (int a = 0; a < obbs.size(); a++) {
                triangulateOBB(obbs.get(a), tempTriangles); // Full mesh for collision

                List<Edge> rawEdges = new ObjectArrayList<>();

                obbEdges(obbs.get(a), rawEdges);

                for (Edge rawEdge : rawEdges) {
                    ObjectList<double[]> segs = new ObjectArrayList<>(); // (Insert patrick drooling image here)

                    segs.add(new double[]{0.0, 1.0}); // [t0, t1] parametric intervals on rawEdge

                    for (int b = 0; b < obbs.size(); b++) {
                        if (b == a) continue;

                        segs = clipSegmentsOutside(segs, rawEdge.a, rawEdge.b, obbs.get(b));

                        if (segs.isEmpty()) break;
                    }

                    for (double[] seg : segs) {
                        Vector3d p0 = new Vector3d(rawEdge.a).lerp(rawEdge.b, seg[0]);
                        Vector3d p1 = new Vector3d(rawEdge.a).lerp(rawEdge.b, seg[1]);

                        tempEdges.add(new Edge(p0, p1));
                    }
                }
            }
        }

        this.triangles = ObjectLists.unmodifiable(tempTriangles);
        this.edges = ObjectLists.unmodifiable(tempEdges);

        ObjectArrayList<Vector3d> tempMeshVerts = new ObjectArrayList<>(triangles.size() * 3); // Flatten vertices for proper compat w/MCDs

        for (Triangle curTriangle : triangles) {
            tempMeshVerts.add(curTriangle.getA());
            tempMeshVerts.add(curTriangle.getB());
            tempMeshVerts.add(curTriangle.getC());
        }

        this.meshVertices = ObjectLists.unmodifiable(tempMeshVerts);

        // Collect unique face normals from triangles
        this.faceNormals = deduplicateDirections(
                triangles.stream()
                        .map(Triangle::getNormal)
                        .collect(Collectors.toCollection(ObjectArrayList::new))
        );

        // Collect unique edge directions from clipped edges
        List<Vector3d> rawDirs = new ObjectArrayList<>();

        for (Edge curEdge : edges) {
            rawDirs.add(new Vector3d(curEdge.b).sub(curEdge.a).normalize());
        }

        this.edgeDirections = deduplicateDirections(rawDirs);
    }

    public static CSGUnionShape fromMCDs(List<ModelCoordinateData> cubeMcds) { // cubeMcds should have model-space data ready (i.e. getModelSpaceVertices() non-empty, getModelObbAxes() valid)
        ObjectArrayList<SourceOBB> obbs = new ObjectArrayList<>();

        // Painstaking MCD -> SOBB conversion inbound :manimdea:
        for (ModelCoordinateData mcd : cubeMcds) {
            double minX = mcd.getOriginalMinX(), maxX = mcd.getOriginalMaxX();
            double minY = mcd.getOriginalMinY(), maxY = mcd.getOriginalMaxY();
            double minZ = mcd.getOriginalMinZ(), maxZ = mcd.getOriginalMaxZ();

            double dx = maxX - minX;
            double dy = maxY - minY;
            double dz = maxZ - minZ;

            if (dx < ModelCoordinateData.EPSILON && dy < ModelCoordinateData.EPSILON && dz < ModelCoordinateData.EPSILON) continue;

            // Full model-space matrix = accumulatedBoneMatrix * localTransform, but without using mul() to avoid some JOML property-flag shortcuts (we js transform known reference points directly instead :trol:)
            Matrix4d accumulatedBoneMatrix = mcd.getBoneMatrix();
            Matrix4d localTransformMatrix = mcd.buildLocalTransformMatrix();

            // Doing ts to bypass having to do matrix decomposition
            Vector4d o = new Vector4d(0.0D, 0.0D, 0.0D, 1.0D);
            Vector4d px = new Vector4d(1.0D, 0.0D, 0.0D, 1.0D); // +X
            Vector4d py = new Vector4d(0.0D, 1.0D, 0.0D, 1.0D); // +Y
            Vector4d pz = new Vector4d(0.0D, 0.0D, 1.0D, 1.0D); // +Z

            localTransformMatrix.transform(o);
            localTransformMatrix.transform(px);
            localTransformMatrix.transform(py);
            localTransformMatrix.transform(pz);

            accumulatedBoneMatrix.transform(o);
            accumulatedBoneMatrix.transform(px);
            accumulatedBoneMatrix.transform(py);
            accumulatedBoneMatrix.transform(pz);

            // Compute axes
            Vector3d ax = new Vector3d(px.x - o.x, px.y - o.y, px.z - o.z);
            Vector3d ay = new Vector3d(py.x - o.x, py.y - o.y, py.z - o.z);
            Vector3d az = new Vector3d(pz.x - o.x, pz.y - o.y, pz.z - o.z);

            double sx = ax.length(), sy = ay.length(), sz = az.length();

            if (sx < ModelCoordinateData.EPSILON || sy < ModelCoordinateData.EPSILON || sz < ModelCoordinateData.EPSILON)
                continue;

            ax.div(sx);
            ay.div(sy);
            az.div(sz);

            // Transform localTransformMatrix box center
            double lcx = (minX + maxX) * 0.5D, lcy = (minY + maxY) * 0.5D, lcz = (minZ + maxZ) * 0.5D;
            Vector4d cv = new Vector4d(lcx, lcy, lcz, 1.0D);

            localTransformMatrix.transform(cv);
            accumulatedBoneMatrix.transform(cv);

            Vector3d center = new Vector3d(cv.x, cv.y, cv.z);

            double hx = dx * 0.5D * sx;
            double hy = dy * 0.5D * sy;
            double hz = dz * 0.5D * sz;

            obbs.add(new SourceOBB(center, new Vector3d[]{ax, ay, az}, new Vector3d(hx, hy, hz)));
        }

        return new CSGUnionShape(obbs);
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.CSG_UNION;
    }

    @Override
    public int getVertexCount() {
        return meshVertices.size();
    }

    @Override
    public List<Vector3d> generateLocalVertices(Vector3d minBounds, Vector3d maxBounds) {
        List<Vector3d> defCopy = new ObjectArrayList<>(meshVertices.size());

        for (Vector3d vertex : meshVertices) defCopy.add(new Vector3d(vertex));

        return defCopy;
    }

    @Override
    public Vector3d computeHalfExtents(Vector3d minBounds, Vector3d maxBounds) {
        if (meshVertices.isEmpty()) return new Vector3d();

        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, minZ = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE, maxY = -Double.MAX_VALUE, maxZ = -Double.MAX_VALUE;

        for (Vector3d vertex : meshVertices) {
            minX = Math.min(minX, vertex.x);
            minY = Math.min(minY, vertex.y);
            minZ = Math.min(minZ, vertex.z);
            maxX = Math.max(maxX, vertex.x);
            maxY = Math.max(maxY, vertex.y);
            maxZ = Math.max(maxZ, vertex.z);
        }

        return new Vector3d((maxX - minX) * 0.5D, (maxY - minY) * 0.5D, (maxZ - minZ) * 0.5D);
    }

    @Override
    public Vector3d computeCenter(Vector3d minBounds, Vector3d maxBounds) {
        if (meshVertices.isEmpty()) return new Vector3d();

        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, minZ = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE, maxY = -Double.MAX_VALUE, maxZ = -Double.MAX_VALUE;

        for (Vector3d vertex : meshVertices) {
            minX = Math.min(minX, vertex.x);
            minY = Math.min(minY, vertex.y);
            minZ = Math.min(minZ, vertex.z);
            maxX = Math.max(maxX, vertex.x);
            maxY = Math.max(maxY, vertex.y);
            maxZ = Math.max(maxZ, vertex.z);
        }

        return new Vector3d((minX + maxX) * 0.5D, (minY + maxY) * 0.5D, (minZ + maxZ) * 0.5D);
    }

    @Override
    public List<Vector3d> getFaceNormals() {
        return faceNormals;
    }

    @Override
    public List<Vector3d> getEdgeDirections() {
        return edgeDirections;
    }

    @Override
    public boolean containsPoint(Vector3d point, Vector3d minBounds, Vector3d maxBounds) {
        for (SourceOBB obb : sourceOBBs) {
            if (obb.containsPoint(point)) return true;
        }

        return false;
    }

    @Override
    public Vector3d closestPoint(Vector3d point, Vector3d minBounds, Vector3d maxBounds) {
        if (triangles.isEmpty()) return new Vector3d(point);

        Vector3d best = null;
        double bestDist = Double.MAX_VALUE;

        for (Triangle curTriangle : triangles) { // Closest point is just the min across all triangles
            Vector3d cp = curTriangle.closestPoint(point);
            double dSq = cp.distanceSquared(point);

            if (dSq < bestDist) {
                bestDist = dSq;
                best = cp;
            }
        }

        return best != null ? best : new Vector3d(point);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

    public List<SourceOBB> getSourceOBBs() {
        return sourceOBBs;
    }

    public Vector3d gjkSupport(Vector3d dir) { // EPA should be done per-OBB
        Vector3d best = null;
        double bestDot = -Double.MAX_VALUE;

        for (SourceOBB curSrcObb : sourceOBBs) {
            Vector3d s = curSrcObb.gjkSupport(dir);
            double d = s.dot(dir);

            if (d > bestDot) {
                bestDot = d;
                best = s;
            }
        }

        return best != null ? best : new Vector3d();
    }

    private static void obbEdges(SourceOBB obb, List<Edge> out) { // TODO Support other shapes (currently only does boxes properly)
        Vector3d c = obb.center;
        Vector3d x = new Vector3d(obb.axes[0]).mul(obb.half.x);
        Vector3d y = new Vector3d(obb.axes[1]).mul(obb.half.y);
        Vector3d z = new Vector3d(obb.axes[2]).mul(obb.half.z);
        Vector3d[] v = new Vector3d[8];

        v[0] = new Vector3d(c).sub(x).sub(y).sub(z);
        v[1] = new Vector3d(c).add(x).sub(y).sub(z);
        v[2] = new Vector3d(c).add(x).add(y).sub(z);
        v[3] = new Vector3d(c).sub(x).add(y).sub(z);
        v[4] = new Vector3d(c).sub(x).sub(y).add(z);
        v[5] = new Vector3d(c).add(x).sub(y).add(z);
        v[6] = new Vector3d(c).add(x).add(y).add(z);
        v[7] = new Vector3d(c).sub(x).add(y).add(z);

        // Bottom face
        out.add(new Edge(v[0], v[1]));
        out.add(new Edge(v[1], v[2]));
        out.add(new Edge(v[2], v[3]));
        out.add(new Edge(v[3], v[0]));

        // Top face
        out.add(new Edge(v[4], v[5]));
        out.add(new Edge(v[5], v[6]));
        out.add(new Edge(v[6], v[7]));
        out.add(new Edge(v[7], v[4]));

        // Verticals
        out.add(new Edge(v[0], v[4]));
        out.add(new Edge(v[1], v[5]));
        out.add(new Edge(v[2], v[6]));
        out.add(new Edge(v[3], v[7]));
    }

    /**
     * Clips a list of parametric intervals [t0, t1] on segment (a -> b) against the interior
     * of {@code clipper}, keeping the portions <u>outside</u> the OBB.
     * <br></br>
     * Algorithm: for each input interval, compute its overlap with the OBB interior
     * using parametric slab intersection (all 6 planes simultaneously). The overlap
     * interval [tEnter, tExit] is the part inside the OBB. Subtract it from [t0, t1]
     * to keep the outside portions.
     *
     * @param segs {@link List} of input parametric intervals [t0, t1].
     * @param a Start of the target segment.
     * @param b End of the target segment.
     * @param clipper The OBB to clip against.
     *
     * @return An unmodifiable {@link ObjectList} of remaining parametric intervals outside the OBB.
     */
    private static ObjectList<double[]> clipSegmentsOutside(ObjectList<double[]> segs,
                                                      Vector3d a, Vector3d b,
                                                      SourceOBB clipper) {
        double tEnter = 0.0D, tExit = 1.0D;
        Vector3d ab = new Vector3d(b).sub(a);

        /*
         * Compute the parametric interval where the full ray a -> b is inside the OBB.
         * That'd be the intersection of all 6 half-space intervals.
         */
        Vector3d tmpVc = new Vector3d();

        for (int axis = 0; axis < 3; axis++) {
            Vector3d axisVec = clipper.axes[axis];
            double halfE = (axis == 0.0D
                    ? clipper.half.x
                    : (axis == 1.0D ? clipper.half.y : clipper.half.z));

            tmpVc.set(a);

            double da = tmpVc.sub(clipper.center).dot(axisVec); // Signed distance of a and direction along this axis
            double dd = ab.dot(axisVec);
            double t1, t2;

            if (Math.abs(dd) < SLAB_EPSILON) { // Slab: -halfE <= da + t*dd <= halfE
                // Ray parallel to slab planes
                if (da < -halfE - EXTERIOR_EDGE_CLIPPING_THRESHOLD || da > halfE + EXTERIOR_EDGE_CLIPPING_THRESHOLD) {
                    tEnter = 1.0D;
                    tExit = 0.0D;

                    break;
                }

                continue;
            }

            t1 = (-halfE - da) / dd;
            t2 = (halfE - da) / dd;

            if (t1 > t2) { // Swap for logical coherence/consistency for the interval rep
                double tmp = t1;

                t1 = t2;
                t2 = tmp;
            }

            tEnter = Math.max(tEnter, t1);
            tExit = Math.min(tExit, t2);

            if (tEnter > tExit + EXTERIOR_EDGE_CLIPPING_THRESHOLD) break; // No overlap
        }

        // No overlap with OBB interior (i.e. all input segments survive unchanged) (yay early exit)
        if (tEnter >= tExit - EXTERIOR_EDGE_CLIPPING_THRESHOLD) return segs;

        ObjectArrayList<double[]> result = new ObjectArrayList<>();

        for (double[] seg : segs) { // Subtract [tEnter, tExit] from each input segment
            double s0 = seg[0], s1 = seg[1];
            double oStart = Math.max(s0, tEnter);
            double oEnd = Math.min(s1, tExit);

            if (oStart >= oEnd - EXTERIOR_EDGE_CLIPPING_THRESHOLD) result.add(seg); // Keep the entire segment (no overlap)
            else { // Subtract [oStart,oEnd] from [s0,s1]
                if (s0 < oStart - EXTERIOR_EDGE_CLIPPING_THRESHOLD) result.add(new double[]{s0, oStart});
                if (oEnd < s1 - EXTERIOR_EDGE_CLIPPING_THRESHOLD) result.add(new double[]{oEnd, s1});
            }
        }

        return result;
    }

    private static void triangulateOBB(SourceOBB obb, List<Triangle> out) {
        Vector3d c = obb.center;
        Vector3d x = new Vector3d(obb.axes[0]).mul(obb.half.x);
        Vector3d y = new Vector3d(obb.axes[1]).mul(obb.half.y);
        Vector3d z = new Vector3d(obb.axes[2]).mul(obb.half.z);

        // 8 corners
        Vector3d[] v = new Vector3d[8];
        v[0] = new Vector3d(c).sub(x).sub(y).sub(z);
        v[1] = new Vector3d(c).add(x).sub(y).sub(z);
        v[2] = new Vector3d(c).add(x).add(y).sub(z);
        v[3] = new Vector3d(c).sub(x).add(y).sub(z);
        v[4] = new Vector3d(c).sub(x).sub(y).add(z);
        v[5] = new Vector3d(c).add(x).sub(y).add(z);
        v[6] = new Vector3d(c).add(x).add(y).add(z);
        v[7] = new Vector3d(c).sub(x).add(y).add(z);

        // 6 faces * 2 triangles (outward-facing winding)
        addFace(out, v[0], v[1], v[2], v[3]); // -Z face
        addFace(out, v[5], v[4], v[7], v[6]); // +Z face
        addFace(out, v[4], v[0], v[3], v[7]); // -X face
        addFace(out, v[1], v[5], v[6], v[2]); // +X face
        addFace(out, v[4], v[5], v[1], v[0]); // -Y face
        addFace(out, v[3], v[2], v[6], v[7]); // +Y face
    }

    private static void addFace(List<Triangle> out, Vector3d a, Vector3d b, Vector3d c, Vector3d d) {
        /*
         * Quad (a, b, c, d) splits into (a, b, c) and (a, c, d).
         * The shared diagonal a -> c appears as edge c -> a (idx 2) in tri0 and edge a -> c (idx 0) in tri1.
         */
        out.add(new Triangle(a, b, c, 2)); // diagonal c -> a
        out.add(new Triangle(a, c, d, 0)); // diagonal a -> c
    }

    private static List<Vector3d> deduplicateDirections(List<Vector3d> dirs) {
        ObjectArrayList<Vector3d> uniqueDirs = new ObjectArrayList<>();
        Vector3d tmp = new Vector3d();

        outer:
        for (Vector3d d : dirs) {
            if (d.lengthSquared() < ModelCoordinateData.EPSILON) continue;

            tmp.set(d);

            Vector3d dn = tmp.normalize();

            for (Vector3d curUniqueDir : uniqueDirs) {
                double dot = Math.abs(dn.dot(curUniqueDir));

                if (dot > 1.0D - DIRECTION_DEDUP) continue outer;
            }

            uniqueDirs.add(dn);
        }

        return ObjectLists.unmodifiable(uniqueDirs);
    }

    public record Edge(Vector3d a, Vector3d b) {

        public Edge(Vector3d a, Vector3d b) {
            this.a = new Vector3d(a); // Copy to preserve
            this.b = new Vector3d(b);
        }
    }

    public static final class Triangle { // Convenient wrapper class
        public static final double EPSILON = 1.0E-12D;
        private final Vector3d a, b, c;
        private final Vector3d normal;
        private final int diagonalEdge;

        public Triangle(Vector3d a, Vector3d b, Vector3d c, int diagonalEdge) {
            this.a = new Vector3d(a);
            this.b = new Vector3d(b);
            this.c = new Vector3d(c);
            this.diagonalEdge = diagonalEdge;

            Vector3d ab = new Vector3d(b).sub(a);
            Vector3d ac = new Vector3d(c).sub(a);
            Vector3d n = ab.cross(ac);

            double len = n.length();

            this.normal = len > EPSILON ? n.div(len) : new Vector3d(0.0D, 1.0D, 0.0D);
        }

        public Triangle(Vector3d a, Vector3d b, Vector3d c) {
            this(a, b, c, -1);
        }

        public Vector3d closestPoint(Vector3d p) {
            return MathUtil.closestPointOnTriangle(p, a, b, c);
        }

        public Vector3d getA() {
            return a;
        }

        public Vector3d getB() {
            return b;
        }

        public Vector3d getC() {
            return c;
        }

        public Vector3d getNormal() {
            return normal;
        }

        /**
         * Index of the edge that is a shared quad diagonal and must not be rendered.
         * <br></br>
         * 0 = a -> b | 1 = b -> c | 2 = c -> a. -1 means all edges are real boundary edges.
         *
         * @return This instance's {@code diagonalEdge} index.
         */
        public int getDiagonalEdge() {
            return diagonalEdge;
        }
    }

    public record SourceOBB(Vector3d center, Vector3d[] axes, Vector3d half) { // TODO Support other shapes besides boxes
        public static final double BURY_TOLERANCE = 0.1D;

        public SourceOBB(Vector3d center, Vector3d[] axes, Vector3d half) {
            this.center = new Vector3d(center);
            this.axes = new Vector3d[]{new Vector3d(axes[0]), new Vector3d(axes[1]), new Vector3d(axes[2])}; // Unit vectors btw
            this.half = new Vector3d(half);
        }

        public boolean containsPoint(Vector3d p) {
            Vector3d d = new Vector3d(p).sub(center);

            return Math.abs(d.dot(axes[0])) <= half.x + ModelCoordinateData.EPSILON
                    && Math.abs(d.dot(axes[1])) <= half.y + ModelCoordinateData.EPSILON
                    && Math.abs(d.dot(axes[2])) <= half.z + ModelCoordinateData.EPSILON;
        }

        public boolean isBuried(Vector3d p) {
            Vector3d d = new Vector3d(p).sub(center);

            return Math.abs(d.dot(axes[0])) <= half.x + BURY_TOLERANCE
                    && Math.abs(d.dot(axes[1])) <= half.y + BURY_TOLERANCE
                    && Math.abs(d.dot(axes[2])) <= half.z + BURY_TOLERANCE;
        }

        public Vector3d gjkSupport(Vector3d dir) {
            return new Vector3d(center)
                    .add(new Vector3d(axes[0]).mul(Math.signum(dir.dot(axes[0])) * half.x))
                    .add(new Vector3d(axes[1]).mul(Math.signum(dir.dot(axes[1])) * half.y))
                    .add(new Vector3d(axes[2]).mul(Math.signum(dir.dot(axes[2])) * half.z));
        }

        public List<Vector3d> corners() {
            List<Vector3d> boxPts = new ObjectArrayList<>(8);

            for (int xi = -1; xi <= 1; xi += 2) {
                for (int yi = -1; yi <= 1; yi += 2) {
                    for (int zi = -1; zi <= 1; zi += 2) {
                        boxPts.add(new Vector3d(center)
                                .add(new Vector3d(axes[0]).mul(xi * half.x))
                                .add(new Vector3d(axes[1]).mul(yi * half.y))
                                .add(new Vector3d(axes[2]).mul(zi * half.z))
                        );
                    }
                }
            }

            return boxPts;
        }
    }
}
