package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.geckolib.model;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelInfo;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Bone;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Cube;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.ModelCoordinateData;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Skeleton;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.template.SkeletonTemplate;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.geckolib.model.template.GeckolibSkeletonTemplate;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4d;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GeckolibSkeleton implements Skeleton {
    protected GeckolibSkeletonTemplate template;
    protected GeckolibBone[] bones;
    protected GeckolibBone[] rootBones;
    protected GeckolibCube[] cubes;
    protected boolean needsUpdate = true;
    protected AABB generalModelBounds = new AABB(0, 0, 0, 0, 0, 0);
    protected AABB generalWorldBounds = new AABB(0, 0, 0, 0, 0, 0);
    @NotNull
    protected Vector3d skeletonPivot; // Essentially the origin in model space
    @Nullable
    protected Vector3d skeletonRotation;
    @Nullable
    protected Vector3d skeletonScale;

    public GeckolibSkeleton(GeckolibSkeletonTemplate template) {
        initializeFromTemplate(template);
    }

    public GeckolibSkeleton(ModelInfo modelInfo) {
        this(new GeckolibSkeletonTemplate(modelInfo));
    }

    public GeckolibSkeleton(GeckolibSkeleton other) {
        this(other.getTemplate());
    }

    @Override
    public @NotNull GeckolibSkeletonTemplate getTemplate() {
        return template;
    }

    @Override
    public int getTotalBoneCount() {
        return bones.length;
    }

    @Override
    public int getTotalCubeCount() {
        return cubes.length;
    }

    @Override
    public @NotNull GeckolibBone getBone(int boneIndex) {
        validateBoneIndex(boneIndex);

        return bones[boneIndex];
    }

    @Override
    public @NotNull GeckolibCube getCube(int cubeIndex) {
        validateCubeIndex(cubeIndex);

        return cubes[cubeIndex];
    }

    @Override
    public @NotNull List<GeckolibBone> getAllBones() {
        return ObjectArrayList.of(bones);
    }

    @Override
    public @NotNull List<GeckolibBone> getRootBones() {
        return ObjectArrayList.of(rootBones);
    }

    @Override
    public @NotNull List<GeckolibCube> getAllCubes() {
        return ObjectArrayList.of(cubes);
    }

    @Override
    public Optional<Bone> getBoneByName(String boneName) {
        int boneIndex = template.getBoneIndex(boneName);

        return boneIndex == SkeletonTemplate.ROOT_INDEX ? Optional.empty() : Optional.of(getBone(boneIndex));
    }

    @Override
    public List<GeckolibBone> getChildren(Bone bone) {
        return Arrays.stream(template.getChildBoneIndices(bone.getIndex()))
                .mapToObj(this::getBone)
                .toList();
    }

    @Override
    public List<GeckolibCube> getCubesFor(Bone bone) {
        return Arrays.stream(template.getCubeIndices(bone.getIndex()))
                .mapToObj(this::getCube)
                .toList();
    }

    @Override
    public void refresh(ModelInfo updatedModelInfo) {
        if (updatedModelInfo == null) {
            throw new IllegalArgumentException("Attempted to refresh GeckolibSkeleton with null model info!");
        }

        if (getBackingInfo() != updatedModelInfo) initializeFromTemplate(new GeckolibSkeletonTemplate(updatedModelInfo));
    }

    @Override
    public void markDirty() {
        this.needsUpdate = true;
    }

    @Override
    public @NotNull Vector3d getPivot() {
        return skeletonPivot;
    }

    @Override
    public Optional<Vector3d> getRotation() {
        return Optional.ofNullable(skeletonRotation);
    }

    @Override
    public Optional<Vector3d> getScale() {
        return Optional.ofNullable(skeletonScale);
    }

    @Override
    public void setRotation(Vector3dc rotation) {
        this.skeletonRotation = rotation == null ? null : new Vector3d(rotation);
    }

    @Override
    public void setRotation(double xRotDeg, double yRotDeg, double zRotDeg) {
        if (skeletonRotation == null) setRotation(new Vector3d(xRotDeg, yRotDeg, zRotDeg));
        else skeletonRotation.set(xRotDeg, yRotDeg, zRotDeg);
    }

    @Override
    public void setScale(Vector3dc scale) {
        this.skeletonScale = scale == null ? null : new Vector3d(scale);
    }

    @Override
    public void setScale(double xScale, double yScale, double zScale) {
        if (skeletonScale == null) setScale(new Vector3d(xScale, yScale, zScale));
        else skeletonScale.set(xScale, yScale, zScale);
    }

    @Override
    public Matrix4d buildSkeletonMatrix() {
        Matrix4d baseSkeletonMat = new Matrix4d().identity();

        if (getRotation().isPresent() || getScale().isPresent()) {
            baseSkeletonMat.translate(-skeletonPivot.x, skeletonPivot.y, skeletonPivot.z); // See #buildBoneMatrix below (basically, Geckolib does ts)

            getRotation().ifPresent(r -> {
                if (r.z != 0) baseSkeletonMat.rotateZ(Math.toRadians(r.z));
                if (r.y != 0) baseSkeletonMat.rotateY(Math.toRadians(-r.y));
                if (r.x != 0) baseSkeletonMat.rotateX(Math.toRadians(-r.x));
            });
            getScale().ifPresent(s -> {
                double normX = Math.max(0.1D, Math.abs(s.x));
                double normY = Math.max(0.1D, Math.abs(s.y));
                double normZ = Math.max(0.1D, Math.abs(s.z));

                baseSkeletonMat.scale(normX, normY, normZ);
            });

            baseSkeletonMat.translate(skeletonPivot.x, -skeletonPivot.y, -skeletonPivot.z);
        }

        return baseSkeletonMat;
    }

    @Override
    public Matrix4d buildBoneMatrix(Bone targetBone) {
        if (getBoneByName(targetBone.getName()).isEmpty()) return new Matrix4d();

        Matrix4d accum = buildSkeletonMatrix();

        for (Bone bone : getAllParentsFor(targetBone)) {
            Vector3d standalonePivot = bone.getPivot();

            if (standalonePivot == null) continue; // Somehow (JIC)

            double px = -standalonePivot.x; // Geckolib does ts
            double py = standalonePivot.y;
            double pz = standalonePivot.z;

            Matrix4d curAccum = new Matrix4d().identity();

            curAccum.translate(px, py, pz);

            bone.getRotation().ifPresent(r -> {
                if (r.z != 0) curAccum.rotateZ(Math.toRadians(r.z));
                if (r.y != 0) curAccum.rotateY(Math.toRadians(-r.y));
                if (r.x != 0) curAccum.rotateX(Math.toRadians(-r.x));
            });
            bone.getScale().ifPresent(s -> {
                double normX = Math.max(0.1D, Math.abs(s.x));
                double normY = Math.max(0.1D, Math.abs(s.y));
                double normZ = Math.max(0.1D, Math.abs(s.z));

                curAccum.scale(normX, normY, normZ);
            }); // TODO Add proper guarding against 0-scaling (debating how this should work atm but the winning design choice seems to be allowing 0-scale on the client but rejecting it/disabling collisions for scales below some chosen epsilon value on the server)

            curAccum.translate(-px, -py, -pz);
            accum.mul(curAccum);
        }

        return accum;
    }

    @Override
    public AABB getGeneralWorldBounds(Entity owner) {
        return generalWorldBounds = computeGeneralBounds(owner); // TODO Proper update flags
    }

    @Override
    public AABB getGeneralModelBounds() {
        return needsUpdate
                ? (generalModelBounds = computeGeneralBounds(null))
                : generalModelBounds;
    }

    private void initializeFromTemplate(GeckolibSkeletonTemplate template) {
        if (template == null) {
            throw new IllegalArgumentException("Attempted to initialize GeckolibSkeleton using null template!");
        }

        this.template = template;
        this.bones = new GeckolibBone[template.getTotalBoneCount()];
        this.cubes = new GeckolibCube[template.getTotalCubeCount()];

        for (int boneIndex = 0; boneIndex < bones.length; boneIndex++) {
            bones[boneIndex] = new GeckolibBone(this, boneIndex);
        }

        for (int cubeIndex = 0; cubeIndex < cubes.length; cubeIndex++) {
            cubes[cubeIndex] = new GeckolibCube(this, cubeIndex);
        }

        this.rootBones = Arrays.stream(template.getRootBoneIndices())
                .mapToObj(this::getBone)
                .toArray(GeckolibBone[]::new);

        this.generalWorldBounds = computeGeneralBounds(null);
        this.skeletonPivot = computeSkeletonPivot();
        this.needsUpdate = false;
    }

    @NotNull
    private Vector3d computeSkeletonPivot() {
        if (rootBones.length == 1) return new Vector3d(rootBones[0].getPivot());

        Vector3d weightedCentroid = new Vector3d();
        double totalVolume = 0.0D;

        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;

        boolean hasBounds = false;

        for (GeckolibCube cube : cubes) {
            if (!cube.isEnabled()) continue;

            ModelCoordinateData coordinateData = cube.getTemplate().createCoordinateData();

            double cubeMinX = coordinateData.getOriginalMinX();
            double cubeMinY = coordinateData.getOriginalMinY();
            double cubeMinZ = coordinateData.getOriginalMinZ();
            double cubeMaxX = coordinateData.getOriginalMaxX();
            double cubeMaxY = coordinateData.getOriginalMaxY();
            double cubeMaxZ = coordinateData.getOriginalMaxZ();

            double volume = coordinateData.getCollisionShape().computeVolume(coordinateData.getOriginalMin(), coordinateData.getOriginalMax());

            double centerX = (cubeMinX + cubeMaxX) * 0.5D;
            double centerY = (cubeMinY + cubeMaxY) * 0.5D;
            double centerZ = (cubeMinZ + cubeMaxZ) * 0.5D;

            minX = Math.min(minX, cubeMinX);
            minY = Math.min(minY, cubeMinY);
            minZ = Math.min(minZ, cubeMinZ);
            maxX = Math.max(maxX, cubeMaxX);
            maxY = Math.max(maxY, cubeMaxY);
            maxZ = Math.max(maxZ, cubeMaxZ);
            hasBounds = true;

            if (volume <= ModelCoordinateData.EPSILON) continue;

            weightedCentroid.fma(volume, new Vector3d(centerX, centerY, centerZ));
            totalVolume += volume;
        }

        if (totalVolume > ModelCoordinateData.EPSILON) return weightedCentroid.div(totalVolume);
        if (hasBounds) return new Vector3d((minX + maxX) * 0.5D, (minY + maxY) * 0.5D, (minZ + maxZ) * 0.5D);

        return new Vector3d();
    }

    private AABB computeGeneralBounds(@Nullable Entity owner) {
        AABB bounds = null;
        Vec3 entityPos = owner == null ? Vec3.ZERO : owner.position();
        float xRot = 0.0F;
        float yRot = 0.0F;
        // TODO zRot

        for (Cube cube : cubes) {
            if (!cube.isEnabled() || !cube.hasCollision()) continue;

            AABB cubeBounds = owner == null // Maybe we should branch this into 2 loops in case this changes somehow?
                    ? cube.getCoordinateData().getModelSpaceAABB()
                    : cube.getCoordinateData().getWorldAABB(entityPos, xRot, yRot, 0.0F);

            bounds = bounds == null ? cubeBounds : bounds.minmax(cubeBounds);
        }

        return bounds == null
                ? owner == null
                ? new AABB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D)
                : new AABB(owner.getX(), owner.getY(), owner.getZ(), owner.getX(), owner.getY(), owner.getZ())
                : bounds;
    }

    private void validateBoneIndex(int boneIndex) { // TODO Perhaps generalize this
        if (boneIndex < 0 || boneIndex >= bones.length) {
            throw new IndexOutOfBoundsException(String.format("Bone index out of bounds: %d", boneIndex));
        }
    }

    private void validateCubeIndex(int cubeIndex) {
        if (cubeIndex < 0 || cubeIndex >= cubes.length) {
            throw new IndexOutOfBoundsException(String.format("Cube index out of bounds: %d", cubeIndex));
        }
    }
}
