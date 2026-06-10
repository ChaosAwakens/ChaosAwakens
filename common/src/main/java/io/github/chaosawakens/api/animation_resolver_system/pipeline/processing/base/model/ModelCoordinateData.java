package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.shape.*;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3d;
import org.joml.Matrix4d;
import org.joml.Quaterniond;
import org.joml.Vector3d;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Manages coordinate transformations for a {@link CollisionShape}.
 *
 * <h2>Coordinate Spaces</h2>
 * <ol>
 *     <li>Local Space (Base shape definition)</li>
 *     <ul>
 *         <li>Units: Model units (1 unit = 1 pixel = 1/16 block)</li>
 *         <li>Origin: Shape's local origin (usually geometric center or corner)</li>
 *         <li>Example: bounds (-4, 0, -4) to (4, 16, 4) for an 8 x 16 x 8 cube</li>
 *     </ul>
 *     <li>Model Space (After bone transforms, before entity/object transform)</li>
 *     <ul>
 *         <li>Units: Still model units</li>
 *         <li>Origin: Model root (entity's feet position in model coordinates)</li>
 *         <li>Example use cases: Animation, model-relative calculations</li>
 *     </ul>
 *     <li>World Space (Final position in the Minecraft level)</li>
 *     <ul>
 *         <li>Units: Blocks (1 unit = 1 block)</li>
 *         <li>Origin: World origin (0, 0, 0)</li>
 *         <li>Example use cases: Collision detection, entity interaction</li>
 *     </ul>
 * </ol>
 *
 * <h2>Transform Pipeline (Simplified)</h2>
 * Assume we have some vertex {@code localVertex} in local space:
 *
 * <ol>
 *     <li>{@code boneSpaceVertex} (from provided accum data)</li>
 *     <ul>
 *         <li>pivot -> rotate -> scale -> un-pivot</li>
 *     </ul>
 *     <li>{@code modelSpaceVertex} (from provided model data)</li>
 *     <ul>
 *         <li>Accumulate bone parent transforms</li>
 *     </ul>
 *     <li>{@code scaledModelVertex} (post-processed {@code modelVertex})</li>
 *     <ul>
 *         <li>Scale to world units ({@link #MODEL_TO_WORLD_SCALE})</li>
 *     </ul>
 *     <li>{@code worldSpaceVertex} (from provided entity data)</li>
 *     <ul>
 *         <li>translate {@code entityPos} -> rotate {@code xRotDeg, yRotDeg, zRotDeg}</li>
 *     </ul>
 * </ol>
 *
 * <h2>Usage Examples</h2>
 * <pre>
 *     {@code
 *          // Setup
 *          ModelCoordinateData cubeData = new ModelCoordinateData();
 *          cubeData.setBounds(-4, 0, -4, 4, 16, 4); // 8 x 16 x 8 cube in model units
 *          cubeData.setPivot(0, 8, 0); // Pivot at vertical center
 *
 *          // Update transforms (e.g. from animations)
 *          cubeData.setLocalRotation(45, 0, 0); // Animated rotation
 *          cubeData.setBoneMatrix(parentBone.getAccumulatedTransform());
 *
 *          // Option A: Get model-space data (e.g. for rendering debug, animation)
 *          List<Vector3d> modelVerts = cubeData.getModelSpaceVertices();
 *
 *          // Option B: Get world-space data (e.g. for collision detection)
 *          Vec3 entityPos = entity.position();
 *          float entityYRot = entity.getYRot();
 *          AABB worldAABB = cubeData.getWorldAABB(entityPos, entityYRot);
 *
 *          // Option C: Full world-space OBB for precise collision data
 *          cubeData.computeWorldSpace(entityPos, entityYRot);
 *
 *          Vector3d obbCenter = cubeData.getWorldObbCenter();
 *          Quaterniond obbRotation = cubeData.getWorldObbOrientation();
 *     }
 * </pre>
 *
 * <h2>Misc: View Space (e.g. for item rendering)</h2>
 * View space transforms are handled by the renderer, not this class.
 * <br></br>
 * If you need view-space coordinates:
 *
 * <pre>
 *     {@code
 *          Matrix4d viewMatrix = ...; // From renderer or whatever source
 *          Vector3d worldPos = cubeData.getWorldObbCenter();
 *          Vector3d viewPos = viewMatrix.transformPosition(new Vector3d(worldPos)); // Relative camera pos
 *     }
 * </pre>
 *
 * @apiNote Not all {@link CollisionShape} implementations work properly with {@code ModelCoordinateData} (namely
 * {@link CompoundUnionShape}). As a general rule of thumb, if a {@link CollisionShape} stores child shape information
 * (especially bound info) to reconstruct (or that otherwise directly reference) full shapes, it should be
 * decomposed into its constituent shapes before being used with MCDs.
 * <br></br>
 * {@link CSGUnionShape} doesn't fall under this constraint and works properly, for instance, due to the fact that
 * it only stores post-processed geometric information that comprises a full, singular shape, despite being
 * initially composed of several MCDs.
 */
public class ModelCoordinateData { // TODO Fix localScale[Axis] application
    public static final double MODEL_TO_WORLD_SCALE = 1.0D / 16.0D; // Model units/"pixels" to MC coords
    public static final double EPSILON = 1.0E-6D; // Used mainly in shapes that compose this to eliminate MCDs from certain computation tasks early on (e.g. CSG Union TM computation). Also used in obb computations
    protected final CollisionShape collisionShape;
    protected final Matrix4d boneMatrix = new Matrix4d().identity(); // Stores accumulated transforms from parent bones (done in model units)
    protected final AtomicBoolean modelSpaceDirty = new AtomicBoolean(true); // FIXME So I initially made these atomic cuz there were plans to multithread MCDs themselves, but after taking a step back, I realised that stuff like state management for model/anim engine objects should probably stay on the main thread (as opposed to narrow-phase checks for collisions, which can be more spread out and whatnot). Maybe convert these to primitives later on once everything is more cemented(?)
    protected final AtomicBoolean worldSpaceDirty = new AtomicBoolean(true);
    protected double originalMinX, originalMinY, originalMinZ; // In model units too btw
    protected double originalMaxX, originalMaxY, originalMaxZ;
    protected boolean boundsInitialized = false;
    protected double pivotX, pivotY, pivotZ;
    protected double localRotationX, localRotationY, localRotationZ; // Degrees
    protected double localScaleX = 1.0D, localScaleY = 1.0D, localScaleZ = 1.0D;
    protected double localOffsetX, localOffsetY, localOffsetZ;
    protected List<Vector3d> localVertices = new ObjectArrayList<>();
    protected ObjectArrayList<Vector3d> modelSpaceVertices = new ObjectArrayList<>();
    protected double modelMinX, modelMinY, modelMinZ; // Post-processing model stuff
    protected double modelMaxX, modelMaxY, modelMaxZ;
    protected Vector3d modelObbCenter = new Vector3d();
    protected Vector3d modelObbHalfExtents = new Vector3d();
    protected Quaterniond modelObbOrientation = new Quaterniond();
    protected Vector3d[] modelObbAxes = {
            new Vector3d(),
            new Vector3d(),
            new Vector3d()
    };
    protected ObjectArrayList<Vector3d> worldSpaceVertices = new ObjectArrayList<>(); // Lazily-computed world-space data
    protected double worldMinX, worldMinY, worldMinZ;
    protected double worldMaxX, worldMaxY, worldMaxZ;
    protected Vector3d worldObbCenter = new Vector3d();
    protected Vector3d worldObbHalfExtents = new Vector3d();
    protected Quaterniond worldObbOrientation = new Quaterniond();
    protected Vector3d[] worldObbAxes = {
            new Vector3d(),
            new Vector3d(),
            new Vector3d()
    };
    protected Vec3 lastEntityPos = Vec3.ZERO; // "entity" here is used frequently as a term, but this could technically be any object (probably renaming these later)
    protected float lastEntityYRot = 0.0F;
    protected float lastEntityXRot = 0.0F;
    protected float lastEntityZRot = 0.0F;

    public ModelCoordinateData(CollisionShape shape) {
        this.collisionShape = shape;

        resetBounds();
    }

    public ModelCoordinateData() {
        this(BoxShape.INSTANCE);
    }

    public ModelCoordinateData(ModelCoordinateData other) {
        this.collisionShape = other == null ? BoxShape.INSTANCE : other.collisionShape;

        resetBounds();

        if (other == null) return;

        setBounds(other.originalMinX, other.originalMinY, other.originalMinZ, other.originalMaxX, other.originalMaxY, other.originalMaxZ);
        setPivot(other.pivotX, other.pivotY, other.pivotZ);
        setLocalRotation(other.localRotationX, other.localRotationY, other.localRotationZ);
        setLocalScale(other.localScaleX, other.localScaleY, other.localScaleZ);
        setLocalOffset(other.localOffsetX, other.localOffsetY, other.localOffsetZ);
        setBoneMatrix(new Matrix4d(other.boneMatrix)); // Other data's fundamentally built off of these, so no need to copy or set the rest
    }

    public void setBounds(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.originalMinX = minX;
        this.originalMinY = minY;
        this.originalMinZ = minZ;
        this.originalMaxX = maxX;
        this.originalMaxY = maxY;
        this.originalMaxZ = maxZ;
        this.boundsInitialized = true;

        regenerateLocalVertices();
        markModelSpaceDirty();
    }

    public void setMin(double minX, double minY, double minZ) {
        this.originalMinX = minX;
        this.originalMinY = minY;
        this.originalMinZ = minZ;

        checkBoundsInitialized();
        regenerateLocalVertices();
        markModelSpaceDirty();
    }

    public void setMax(double maxX, double maxY, double maxZ) {
        this.originalMaxX = maxX;
        this.originalMaxY = maxY;
        this.originalMaxZ = maxZ;

        checkBoundsInitialized();
        regenerateLocalVertices();
        markModelSpaceDirty();
    }

    public void setPivot(double pivotX, double pivotY, double pivotZ) {
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        this.pivotZ = pivotZ;

        markModelSpaceDirty();
    }

    public void setLocalRotation(double rotXDeg, double rotYDeg, double rotZDeg) {
        this.localRotationX = rotXDeg;
        this.localRotationY = rotYDeg;
        this.localRotationZ = rotZDeg;

        markModelSpaceDirty();
    }

    public void setLocalScale(double scaleX, double scaleY, double scaleZ) {
        this.localScaleX = scaleX;
        this.localScaleY = scaleY;
        this.localScaleZ = scaleZ;

        markModelSpaceDirty();
    }

    public void setLocalOffset(double xOffset, double yOffset, double zOffset) {
        this.localOffsetX = xOffset;
        this.localOffsetY = yOffset;
        this.localOffsetZ = zOffset;

        markModelSpaceDirty();
    }

    public void initializeDummyBounds() {
        setBounds(-1.0D, -1.0D, -1.0D, 1.0D, 1.0D, 1.0D);
    }

    public void resetBoneMatrix() {
        boneMatrix.identity();

        markModelSpaceDirty();
    }

    public Matrix4d buildLocalTransformMatrix() {
        return new Matrix4d()
                .identity()
                .translate(localOffsetX, localOffsetY, localOffsetZ)
                .translate(pivotX, pivotY, pivotZ)
                .rotateXYZ(
                        Math.toRadians(localRotationX),
                        Math.toRadians(localRotationY),
                        Math.toRadians(localRotationZ)
                )
                .scale(localScaleX, localScaleY, localScaleZ)
                .translate(-pivotX, -pivotY, -pivotZ);
    }

    public Matrix4d buildModelSpaceMatrix() { // modelTransform = boneMatrix * localTransform
        return new Matrix4d(boneMatrix).mul(buildLocalTransformMatrix());
    }

    public Matrix4d buildEntityTransformMatrix(Vec3 entityPos, float xRotDeg, float yRotDeg, float zRotDeg) { // We'll probably implement zRotDeg (roll) for entities ourselves, cuz MC only has pitch (xRotDeg) and yaw (yRotDeg)
        return new Matrix4d()
                .identity()
                .translate(entityPos.x, entityPos.y, entityPos.z)
                .rotateX(Math.toRadians(xRotDeg))
                .rotateY(Math.toRadians(yRotDeg))
                .rotateZ(Math.toRadians(zRotDeg))
                .scale(MODEL_TO_WORLD_SCALE); // Translate to block units
    }

    public Matrix4d buildWorldSpaceMatrix(Vec3 entityPos, float xRot, float yRot, float zRot) { // worldTransform = entityTransform * modelSpaceTransform
        return buildEntityTransformMatrix(entityPos, xRot, yRot, zRot).mul(buildModelSpaceMatrix());
    }

    public void computeWorldSpace(Vec3 entityPos, float xRotDeg, float yRotDeg, float zRotDeg) { // Rotation params passed here are effectively accumulated/applied post-transform to the whole MCD, particularly useful since entities themselves have basic IK from MC (e.g. BodyController)
        boolean entityChanged = !Objects.equals(entityPos, lastEntityPos) ||
                yRotDeg != lastEntityYRot ||
                xRotDeg != lastEntityXRot ||
                zRotDeg != lastEntityZRot;

        if (!entityChanged && !worldSpaceDirty.get()) return; // Handle stationary state by early-exiting

        ensureModelSpaceUpdated(); // ESPECIALLY critical not to forget (the consequences are dire :skull:)

        if (!boundsInitialized || localVertices.isEmpty()) return;

        // Cache entity transform
        this.lastEntityPos = entityPos;
        this.lastEntityYRot = yRotDeg;
        this.lastEntityXRot = xRotDeg;
        this.lastEntityZRot = zRotDeg;

        worldSpaceDirty.set(false);

        Matrix4d worldTransform = buildWorldSpaceMatrix(entityPos, xRotDeg, yRotDeg, zRotDeg);

        // Transform vertices
        this.worldSpaceVertices = new ObjectArrayList<>(localVertices.size());
        this.worldMinX = this.worldMinY = this.worldMinZ = Double.MAX_VALUE;
        this.worldMaxX = this.worldMaxY = this.worldMaxZ = -Double.MAX_VALUE;

        for (Vector3d localVertex : localVertices) {
            Vector3d worldVertex = new Vector3d(localVertex);

            worldTransform.transformPosition(worldVertex);
            worldSpaceVertices.add(worldVertex);

            // Update data for AABB
            this.worldMinX = Math.min(worldMinX, worldVertex.x);
            this.worldMinY = Math.min(worldMinY, worldVertex.y);
            this.worldMinZ = Math.min(worldMinZ, worldVertex.z);
            this.worldMaxX = Math.max(worldMaxX, worldVertex.x);
            this.worldMaxY = Math.max(worldMaxY, worldVertex.y);
            this.worldMaxZ = Math.max(worldMaxZ, worldVertex.z);
        }

        extractObbFromMatrix(worldTransform, worldObbCenter, worldObbHalfExtents, worldObbOrientation, worldObbAxes);
    }

    public void computeWorldSpace(Vec3 entityPos, float yRot) { // Nice lil helper overload
        computeWorldSpace(entityPos, 0, yRot, 0);
    }

    public double getOriginalMinX() {
        return originalMinX;
    }

    public double getOriginalMinY() {
        return originalMinY;
    }

    public double getOriginalMinZ() {
        return originalMinZ;
    }

    public double getOriginalMaxX() {
        return originalMaxX;
    }

    public double getOriginalMaxY() {
        return originalMaxY;
    }

    public double getOriginalMaxZ() {
        return originalMaxZ;
    }

    public Vector3d getOriginalMin() { // TODO Maybe micro-optimize by caching these too
        return new Vector3d(originalMinX, originalMinY, originalMinZ);
    }

    public Vector3d getOriginalMax() {
        return new Vector3d(originalMaxX, originalMaxY, originalMaxZ);
    }

    public Vector3d getOriginalSize() {
        return new Vector3d(
                originalMaxX - originalMinX,
                originalMaxY - originalMinY,
                originalMaxZ - originalMinZ
        );
    }

    public Vector3d getOriginalCenter() {
        return new Vector3d(
                (originalMinX + originalMaxX) / 2.0D,
                (originalMinY + originalMaxY) / 2.0D,
                (originalMinZ + originalMaxZ) / 2.0D
        );
    }

    public List<Vector3d> getLocalVertices() {
        return Collections.unmodifiableList(localVertices);
    }

    public List<Vector3d> getModelSpaceVertices() {
        ensureModelSpaceUpdated();

        return ObjectLists.unmodifiable(modelSpaceVertices);
    }

    public Vector3d getModelSpaceMin() {
        ensureModelSpaceUpdated();

        return new Vector3d(modelMinX, modelMinY, modelMinZ);
    }

    public Vector3d getModelSpaceMax() {
        ensureModelSpaceUpdated();

        return new Vector3d(modelMaxX, modelMaxY, modelMaxZ);
    }

    public Vector3d getModelObbCenter() {
        ensureModelSpaceUpdated();

        return new Vector3d(modelObbCenter);
    }

    public Vector3d getModelObbHalfExtents() {
        ensureModelSpaceUpdated();

        return new Vector3d(modelObbHalfExtents);
    }

    public Quaterniond getModelObbOrientation() {
        ensureModelSpaceUpdated();

        return new Quaterniond(modelObbOrientation);
    }

    public Vector3d[] getModelObbAxes() {
        ensureModelSpaceUpdated();

        return new Vector3d[]{ // Copy to new array, cuz none of these should provide direct access to mutable data
                new Vector3d(modelObbAxes[0]),
                new Vector3d(modelObbAxes[1]),
                new Vector3d(modelObbAxes[2])
        };
    }

    public AABB getModelSpaceAABB() { // Will most likely not be used, but it's probably useful for anything that does model-relative calculations + doesn't hurt to have
        ensureModelSpaceUpdated();

        return new AABB(modelMinX, modelMinY, modelMinZ, modelMaxX, modelMaxY, modelMaxZ);
    }

    // IMPORTANT: All world-space getters below expect #computeWorldSpace to be called beforehand, unlike the model-space getters (cuz it's expected that you'd want to configure the parameters passed-in). There exist helper overloads below, though, sooooo...
    public List<Vector3d> getWorldSpaceVertices() {
        return ObjectLists.unmodifiable(worldSpaceVertices);
    }

    public List<Vector3d> getWorldSpaceVertices(Vec3 entityPos, float xRotDeg, float yRotDeg, float zRotDeg) {
        computeWorldSpace(entityPos, xRotDeg, yRotDeg, zRotDeg);

        return getWorldSpaceVertices();
    }

    public List<Vector3d> getWorldSpaceVertices(Vec3 entityPos, float yRotDeg) {
        return getWorldSpaceVertices(entityPos, 0, yRotDeg, 0);
    }

    public Vector3d getWorldMin() {
        return new Vector3d(worldMinX, worldMinY, worldMinZ);
    }

    public Vector3d getWorldMin(Vec3 entityPos, float xRotDeg, float yRotDeg, float zRotDeg) {
        computeWorldSpace(entityPos, xRotDeg, yRotDeg, zRotDeg);

        return getWorldMin();
    }

    public Vector3d getWorldMin(Vec3 entityPos, float yRotDeg) {
        return getWorldMin(entityPos, 0, yRotDeg, 0);
    }

    public Vector3d getWorldMax() {
        return new Vector3d(worldMaxX, worldMaxY, worldMaxZ);
    }

    public Vector3d getWorldMax(Vec3 entityPos, float xRotDeg, float yRotDeg, float zRotDeg) {
        computeWorldSpace(entityPos, xRotDeg, yRotDeg, zRotDeg);

        return getWorldMax();
    }

    public Vector3d getWorldMax(Vec3 entityPos, float yRotDeg) {
        return getWorldMax(entityPos, 0, yRotDeg, 0);
    }

    public Vector3d getWorldObbCenter() {
        return new Vector3d(worldObbCenter);
    }

    public Vector3d getWorldObbCenter(Vec3 entityPos, float xRotDeg, float yRotDeg, float zRotDeg) {
        computeWorldSpace(entityPos, xRotDeg, yRotDeg, zRotDeg);

        return getWorldObbCenter();
    }

    public Vector3d getWorldObbCenter(Vec3 entityPos, float yRotDeg) {
        return getWorldObbCenter(entityPos, 0, yRotDeg, 0);
    }

    public Vector3d getWorldObbHalfExtents() {
        return new Vector3d(worldObbHalfExtents);
    }

    public Vector3d getWorldObbHalfExtents(Vec3 entityPos, float xRotDeg, float yRotDeg, float zRotDeg) {
        computeWorldSpace(entityPos, xRotDeg, yRotDeg, zRotDeg);

        return getWorldObbHalfExtents();
    }

    public Vector3d getWorldObbHalfExtents(Vec3 entityPos, float yRotDeg) {
        return getWorldObbHalfExtents(entityPos, 0, yRotDeg, 0);
    }

    public Quaterniond getWorldObbOrientation() {
        return new Quaterniond(worldObbOrientation);
    }

    public Quaterniond getWorldObbOrientation(Vec3 entityPos, float xRotDeg, float yRotDeg, float zRotDeg) {
        computeWorldSpace(entityPos, xRotDeg, yRotDeg, zRotDeg);

        return getWorldObbOrientation();
    }

    public Quaterniond getWorldObbOrientation(Vec3 entityPos, float yRotDeg) {
        return getWorldObbOrientation(entityPos, 0, yRotDeg, 0);
    }

    public Vector3d[] getWorldObbAxes() {
        return new Vector3d[]{ // Copy to new array, cuz none of these should provide direct access to mutable data
                new Vector3d(worldObbAxes[0]),
                new Vector3d(worldObbAxes[1]),
                new Vector3d(worldObbAxes[2])
        };
    }

    public Vector3d[] getWorldObbAxes(Vec3 entityPos, float xRotDeg, float yRotDeg, float zRotDeg) {
        computeWorldSpace(entityPos, xRotDeg, yRotDeg, zRotDeg);

        return getWorldObbAxes();
    }

    public Vector3d[] getWorldObbAxes(Vec3 entityPos, float yRotDeg) {
        return getWorldObbAxes(entityPos, 0, yRotDeg, 0);
    }

    public AABB getWorldAABB() {
        return new AABB(worldMinX, worldMinY, worldMinZ, worldMaxX, worldMaxY, worldMaxZ);
    }

    public AABB getWorldAABB(Vec3 entityPos, float xRotDeg, float yRotDeg, float zRotDeg) {
        computeWorldSpace(entityPos, xRotDeg, yRotDeg, zRotDeg);

        return getWorldAABB();
    }

    public AABB getWorldAABB(Vec3 entityPos, float yRotDeg) {
        computeWorldSpace(entityPos, yRotDeg);

        return getWorldAABB();
    }

    public Vector3d getPivot() {
        return new Vector3d(pivotX, pivotY, pivotZ);
    }

    public Vector3d getLocalRotation() {
        return new Vector3d(localRotationX, localRotationY, localRotationZ);
    }

    public Vector3d getLocalScale() {
        return new Vector3d(localScaleX, localScaleY, localScaleZ);
    }

    public Vector3d getLocalOffset() {
        return new Vector3d(localOffsetX, localOffsetY, localOffsetZ);
    }

    public Matrix4d getBoneMatrix() { // Accumulated bone transforms, in matrices
        return new Matrix4d(boneMatrix);
    }

    public void setBoneMatrix(Matrix4d matrix) {
        boneMatrix.set(matrix);

        markModelSpaceDirty();
    }

    public CollisionShape getCollisionShape() {
        return collisionShape;
    }

    public List<Vector3d> getGjkVertices(Vec3 entityPos, float xRotDeg, float yRotDeg, float zRotDeg) { // Delegate impl (any EPA impl with MCDs uses this, so you should override if you need to do smth else)
        return getWorldSpaceVertices(entityPos, xRotDeg, yRotDeg, zRotDeg);
    }

    public boolean containsWorldPoint(Vector3d worldPoint, Vec3 entityPos, float xRotDeg, float yRotDeg, float zRotDeg) {
        computeWorldSpace(entityPos, xRotDeg, yRotDeg, zRotDeg);

        // Quick AABB rejection
        if ((worldPoint.x < worldMinX || worldPoint.x > worldMaxX)
                || (worldPoint.y < worldMinY || worldPoint.y > worldMaxY)
                || (worldPoint.z < worldMinZ || worldPoint.z > worldMaxZ)) {
            return false;
        }

        // Transform to local space for input
        Matrix4d invTransform = new Matrix4d(buildWorldSpaceMatrix(entityPos, xRotDeg, yRotDeg, zRotDeg)).invert();
        Vector3d localPoint = new Vector3d(worldPoint);

        invTransform.transformPosition(localPoint);

        return collisionShape.containsPoint(localPoint, getOriginalMin(), getOriginalMax());
    }

    public boolean containsWorldPoint(Vector3d worldPoint, Vec3 entityPos, float yRotDeg) {
        return containsWorldPoint(worldPoint, entityPos, 0, yRotDeg, 0);
    }

    public Vector3d closestWorldPoint(Vector3d worldPoint, Vec3 entityPos, float xRotDeg, float yRotDeg, float zRotDeg) {
        Matrix4d worldTransform = buildWorldSpaceMatrix(entityPos, xRotDeg, yRotDeg, zRotDeg);
        Matrix4d invTransform = new Matrix4d(worldTransform).invert();

        // Transform to local space
        Vector3d localPoint = new Vector3d(worldPoint);

        invTransform.transformPosition(localPoint);

        Vector3d localClosestPoint = collisionShape.closestPoint(localPoint, getOriginalMin(), getOriginalMax());

        // Back to world space we go
        worldTransform.transformPosition(localClosestPoint);

        return localClosestPoint;
    }

    public Vector3d closestWorldPoint(Vector3d worldPoint, Vec3 entityPos, float yRotDeg) {
        return closestWorldPoint(worldPoint, entityPos, 0, yRotDeg, 0);
    }

    public Vector3d gjkSupport(Vector3d worldSpaceSearchDir, Vec3 entityPos, float xRotDeg, float yRotDeg, float zRotDeg) {
        computeWorldSpace(entityPos, xRotDeg, yRotDeg, zRotDeg);

        if (collisionShape instanceof BoxShape) return gjkSupportBox(worldSpaceSearchDir); // For BoxShape, we can optimize using OBB axes directly
        if (collisionShape instanceof CapsuleShape) return gjkSupportCapsule(worldSpaceSearchDir, entityPos, xRotDeg, yRotDeg, zRotDeg); // For CapsuleShape, use basic specialized algorithm (based on primary axis)
        if (collisionShape instanceof SphereShape) return gjkSupportSphere(worldSpaceSearchDir); // For SphereShape, O(1) center + radius * worldSpaceSearchDir

        return gjkSupportGeneral(worldSpaceSearchDir); // General case: scan all vertices
    }

    public Vector3d gjkSupport(Vector3d direction, Vec3 entityPos, float yRotDeg) {
        return gjkSupport(direction, entityPos, 0, yRotDeg, 0);
    }

    protected Vector3d gjkSupportBox(Vector3d direction) { // For OBB: Support Point = center + sum(sign(d dot axis_i) * halfExtent_i * axis_i)
        Vector3d support = new Vector3d(worldObbCenter);

        for (int axisIdx = 0; axisIdx < 3; axisIdx++) {
            Vector3d axis = worldObbAxes[axisIdx];
            double halfExtent = (axisIdx == 0)
                    ? worldObbHalfExtents.x
                    : (axisIdx == 1)
                    ? worldObbHalfExtents.y
                    : worldObbHalfExtents.z;
            double sign = Math.signum(direction.dot(axis));

            support.add(new Vector3d(axis).mul(sign * halfExtent));
        }

        return support;
    }

    protected Vector3d gjkSupportCapsule(Vector3d direction, Vec3 entityPos, float xRotDeg, float yRotDeg, float zRotDeg) {
        CapsuleShape capsule = (CapsuleShape) collisionShape; // Presuming this is a CapsuleShape atp
        Matrix4d worldTransform = buildWorldSpaceMatrix(entityPos, xRotDeg, yRotDeg, zRotDeg);

        Vector3d axisDir = switch (capsule.getAlignmentAxis()) { // Get capsule axis in world space
            case X -> new Vector3d(1, 0, 0);
            case Z -> new Vector3d(0, 0, 1);
            default -> new Vector3d(0, 1, 0);
        };

        // Transform axis dir to world space (rot only)
        worldTransform.transformDirection(axisDir);
        axisDir.normalize();

        Vector3d size = getOriginalSize(); // In world space btw
        double radius, halfHeight;

        switch (capsule.getAlignmentAxis()) {
            case X -> {
                radius = Math.min(size.y, size.z) / 2.0D * MODEL_TO_WORLD_SCALE;
                halfHeight = Math.max(0, size.x / 2.0D - radius / MODEL_TO_WORLD_SCALE) * MODEL_TO_WORLD_SCALE;
            }
            case Z -> {
                radius = Math.min(size.x, size.y) / 2.0D * MODEL_TO_WORLD_SCALE;
                halfHeight = Math.max(0, size.z / 2.0D - radius / MODEL_TO_WORLD_SCALE) * MODEL_TO_WORLD_SCALE;
            }
            default -> {
                radius = Math.min(size.x, size.z) / 2.0D * MODEL_TO_WORLD_SCALE;
                halfHeight = Math.max(0, size.y / 2.0D - radius / MODEL_TO_WORLD_SCALE) * MODEL_TO_WORLD_SCALE;
            }
        }

        double supDot = direction.dot(axisDir); // Closest EP + rad in direction
        Vector3d endpoint = new Vector3d(worldObbCenter);

        endpoint.add(new Vector3d(axisDir).mul(Math.signum(supDot) * halfHeight));

        Vector3d normalizedDir = new Vector3d(direction).normalize(); // Add rad in the search dir

        endpoint.add(new Vector3d(normalizedDir).mul(radius));

        return endpoint;
    }

    protected Vector3d gjkSupportSphere(Vector3d direction) { // Sphere gjkSupport = center + radius * normalize(direction)
        double dirLen = direction.length();

        if (dirLen < EPSILON) return new Vector3d(worldObbCenter);

        double radius = Math.min(worldObbHalfExtents.x, Math.min(worldObbHalfExtents.y, worldObbHalfExtents.z)); // Under uniform scale all half-extents are equal, otherwise (for non-uniform scale) use min (conservative sphere inscribed in ellipsoid)
        Vector3d normalizedDir = new Vector3d(direction).div(dirLen);

        return new Vector3d(worldObbCenter).add(normalizedDir.mul(radius));
    }

    protected Vector3d gjkSupportGeneral(Vector3d direction) { // O(n)
        Vector3d best = null;
        double maxDot = Double.NEGATIVE_INFINITY;

        for (Vector3d vertex : worldSpaceVertices) {
            double dot = vertex.dot(direction);

            if (dot > maxDot) {
                maxDot = dot;
                best = vertex;
            }
        }

        return (best != null) ? new Vector3d(best) : new Vector3d(worldObbCenter);
    }

    protected void regenerateLocalVertices() {
        if (!boundsInitialized) {
            localVertices.clear();
            return;
        }

        this.localVertices = collisionShape.generateLocalVertices( // Delegate work to shape impl
                new Vector3d(originalMinX, originalMinY, originalMinZ),
                new Vector3d(originalMaxX, originalMaxY, originalMaxZ)
        );
    }

    protected void markModelSpaceDirty() {
        modelSpaceDirty.set(true);
        worldSpaceDirty.set(true); // World depends on model
    }

    protected void markWorldSpaceDirty() {
        worldSpaceDirty.set(true);
    }

    protected void ensureModelSpaceUpdated() {
        if (modelSpaceDirty.compareAndSet(true, false)) computeModelSpaceData();
    }

    protected void computeModelSpaceData() {
        if (!boundsInitialized || localVertices.isEmpty())
            return; // TODO Maybe cache last vertex data for more lazy-eval to save on cpu time

        Matrix4d modelTransform = buildModelSpaceMatrix();

        // Transform vertices
        this.modelSpaceVertices = new ObjectArrayList<>(localVertices.size());
        this.modelMinX = this.modelMinY = this.modelMinZ = Double.MAX_VALUE;
        this.modelMaxX = this.modelMaxY = this.modelMaxZ = -Double.MAX_VALUE;

        for (Vector3d localVertex : localVertices) {
            Vector3d modelVertex = new Vector3d(localVertex);

            modelTransform.transformPosition(modelVertex);
            modelSpaceVertices.add(modelVertex);

            // Update data for AABB
            this.modelMinX = Math.min(modelMinX, modelVertex.x);
            this.modelMinY = Math.min(modelMinY, modelVertex.y);
            this.modelMinZ = Math.min(modelMinZ, modelVertex.z);
            this.modelMaxX = Math.max(modelMaxX, modelVertex.x);
            this.modelMaxY = Math.max(modelMaxY, modelVertex.y);
            this.modelMaxZ = Math.max(modelMaxZ, modelVertex.z);
        }

        extractObbFromMatrix(modelTransform, modelObbCenter, modelObbHalfExtents, modelObbOrientation, modelObbAxes);
    }

    protected void extractObbFromMatrix(Matrix4d transform, Vector3d outCenter, Vector3d outHalfExtents, Quaterniond outOrientation, Vector3d[] outAxes) {
        /*
         * Reference matrix for rotation and scale:
         *
         * [Sx*R00 Sy*R01 Sz*R02 Tx]
         * [Sx*R10 Sy*R11 Sz*R12 Ty]
         * [Sx*R20 Sy*R21 Sz*R22 Tz]
         * [0 0 0 1]
         */

        // Extract scale from matrix columns
        Vector3d col0 = new Vector3d(transform.m00(), transform.m10(), transform.m20());
        Vector3d col1 = new Vector3d(transform.m01(), transform.m11(), transform.m21());
        Vector3d col2 = new Vector3d(transform.m02(), transform.m12(), transform.m22());

        double scaleX = col0.length();
        double scaleY = col1.length();
        double scaleZ = col2.length();

        // Build rotation matrix by normalizing columns first
        Matrix3d rotationMatrix = new Matrix3d();

        if (scaleX > EPSILON) {
            rotationMatrix.m00(col0.x / scaleX);
            rotationMatrix.m10(col0.y / scaleX);
            rotationMatrix.m20(col0.z / scaleX);
        }

        if (scaleY > EPSILON) {
            rotationMatrix.m01(col1.x / scaleY);
            rotationMatrix.m11(col1.y / scaleY);
            rotationMatrix.m21(col1.z / scaleY);
        }

        if (scaleZ > EPSILON) {
            rotationMatrix.m02(col2.x / scaleZ);
            rotationMatrix.m12(col2.y / scaleZ);
            rotationMatrix.m22(col2.z / scaleZ);
        }

        // Convert to quaternion for storage
        outOrientation.setFromNormalized(rotationMatrix);
        outOrientation.normalize();

        // Store axes
        outAxes[0].set(rotationMatrix.m00(), rotationMatrix.m10(), rotationMatrix.m20());
        outAxes[1].set(rotationMatrix.m01(), rotationMatrix.m11(), rotationMatrix.m21());
        outAxes[2].set(rotationMatrix.m02(), rotationMatrix.m12(), rotationMatrix.m22());

        Vector3d originalHalfExtents = collisionShape.computeHalfExtents( // (size / 2) * scale
                new Vector3d(originalMinX, originalMinY, originalMinZ),
                new Vector3d(originalMaxX, originalMaxY, originalMaxZ)
        );

        outHalfExtents.set(
                originalHalfExtents.x * scaleX,
                originalHalfExtents.y * scaleY,
                originalHalfExtents.z * scaleZ
        );

        // Finally: Transform local center
        Vector3d localCenter = collisionShape.computeCenter(
                new Vector3d(originalMinX, originalMinY, originalMinZ),
                new Vector3d(originalMaxX, originalMaxY, originalMaxZ)
        );

        outCenter.set(localCenter);
        transform.transformPosition(outCenter);
    }

    private void checkBoundsInitialized() {
        this.boundsInitialized = (originalMinX != Double.MAX_VALUE && originalMaxX != -Double.MAX_VALUE)
                || (originalMinY != Double.MAX_VALUE && originalMaxY != Double.MAX_VALUE)
                || (originalMinZ != Double.MAX_VALUE && originalMaxZ != Double.MAX_VALUE);
    }

    private void resetBounds() {
        this.originalMinX = this.originalMinY = this.originalMinZ = Double.MAX_VALUE;
        this.originalMaxX = this.originalMaxY = this.originalMaxZ = -Double.MAX_VALUE;

        this.boundsInitialized = false;
    }
}