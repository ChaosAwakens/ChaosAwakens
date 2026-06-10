package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelInfo;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.template.SkeletonTemplate;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4d;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public interface Skeleton { // Topological + Upward Traversal
    Comparator<Bone> DEFAULT_BONE_SORTER = Comparator.comparingInt((Bone bone) -> bone.getDirectParents().size()).thenComparing(Bone::getName);

    @NotNull
    SkeletonTemplate getTemplate();

    int getTotalBoneCount();
    int getTotalCubeCount();

    @NotNull
    Bone getBone(int boneIndex);

    @NotNull
    Cube getCube(int cubeIndex);

    @NotNull
    List<? extends Bone> getAllBones();

    @NotNull
    List<? extends Bone> getRootBones();

    @NotNull
    List<? extends Cube> getAllCubes();

    Optional<? extends Bone> getBoneByName(String boneName);

    List<? extends Bone> getChildren(Bone bone);
    List<? extends Cube> getCubesFor(Bone bone);

    void refresh(ModelInfo updatedModelInfo);
    void markDirty();

    @NotNull
    Vector3d getPivot();

    Optional<Vector3d> getRotation();
    Optional<Vector3d> getScale();

    void setRotation(Vector3dc rotation);
    void setRotation(double xRotDeg, double yRotDeg, double zRotDeg); // See below

    void setScale(Vector3dc scale);
    void setScale(double xScale, double yScale, double zScale); // Not defaulted cuz we want to be able to directly set it via the field instead of creating a new object every time this is called (and vice-versa cuz we want to be able to clear rot entirely by passing "null" in)

    Matrix4d buildSkeletonMatrix(); // Probably the most mathematically-sound approach to skeleton-level transforms. instead of abusing tf out of root bones (which may not hierarch-ally(?) exist)
    Matrix4d buildBoneMatrix(Bone targetBone);

    AABB getGeneralWorldBounds(Entity owner);

    AABB getGeneralModelBounds();

    default boolean isEmpty() {
        return getTotalBoneCount() == 0;
    }

    @NotNull
    default ModelInfo getBackingInfo() {
        return getTemplate().getBackingInfo();
    }

    default void updateStructure(BiConsumer<Cube, ModelCoordinateData> cubeUpdateConsumer) {
        for (Cube cube : getAllCubes()) {
            if (!cube.isEnabled()) continue;

            cubeUpdateConsumer.accept(cube, cube.getCoordinateData());
        }

        markDirty();
    }

    default void refreshStructure() {
        updateStructure((curCube, cubeMcd) -> curCube.refreshCoordinateData());
    }

    default List<? extends Bone> getAllParentsFor(Bone targetBone, boolean topological) {
        List<Bone> parentChain = topological ? new LinkedList<>() : new ObjectArrayList<>();
        Bone cur = targetBone;

        while (cur != null) {
            if (topological) parentChain.add(0, cur); // LIFO retrieval, cuz that's how we read targetBone chains for applying parent transforms (i.e. standard iterations go in order of the eldest bone in the chain, down to the current one)
            else parentChain.add(cur);

            cur = cur.getParentBone()
                    .filter(curBone -> getBoneByName(curBone.getName()).isPresent())
                    .orElse(null);
        }

        return parentChain;
    }

    default List<? extends Bone> getAllParentsFor(Bone targetBone) {
        return getAllParentsFor(targetBone, true);
    }

    default Matrix4d buildCubeMatrix(Cube targetCube) {
        Matrix4d accumParentMat = targetCube.getParentBone()
                .map(this::buildBoneMatrix)
                .orElseGet(Matrix4d::new);

        return accumParentMat.mul(targetCube.buildCubeLocalMatrix());
    }

    default void setPivot(Vector3dc pivot) {
        getPivot().set(pivot);
    }

    default void setPivot(double xModelSpace, double yModelSpace, double zModelSpace) {
        getPivot().set(xModelSpace, yModelSpace, zModelSpace);
    }

    // TODO Implement aggregator MCD that can perform selective CSGUnions and compound bones/cubes as needed
}
