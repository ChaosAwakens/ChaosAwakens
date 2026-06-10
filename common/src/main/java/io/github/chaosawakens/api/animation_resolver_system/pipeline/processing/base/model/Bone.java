package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelBoneData;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.template.BoneTemplate;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Bone {

    @NotNull
    BoneTemplate getTemplate();

    Optional<? extends Bone> getParentBone();

    List<? extends Cube> getCubes(); // Impls should shallow-copy cuz we still want to be able to mutate cubes and stuff directly

    @NotNull
    Vector3d getPivot(); // TODO Whenever MCDs are properly implemented for bones, this needs to be cleaned tf up

    Optional<Vector3d> getRotation();
    Optional<Vector3d> getScale();

    void setScale(Vector3dc scale);
    void setScale(double scaleX, double scaleY, double scaleZ);

    AABB getGeneralBounds();

    @NotNull
    default ModelBoneData getBackingData() {
        return getTemplate().getBackingData();
    }

    default int getIndex() {
        return getTemplate().getIndex();
    }

    default String getName() {
        return getTemplate().getName();
    }

    default int getParentIndex() {
        return getTemplate().getParentIndex();
    }

    default int[] getCubeIndices() {
        return getTemplate().getCubeIndices();
    }

    default void enable() {
        getCubes().forEach(Cube::enable);
    }

    default void disable() {
        getCubes().forEach(Cube::disable);
    }

    default boolean isEnabled() {
        return getCubes().stream().anyMatch(Cube::isEnabled);
    }

    // TODO Implement aggregator MCD that can perform selective CSGUnions and compound cubes as needed

    default boolean isLeaf() {
        return getCubes().isEmpty();
    }

    default boolean isHomogeneous() {
        return getCubes().isEmpty() || getCubes().stream().noneMatch(Cube::isEnabled);
    }

    default boolean isRoot() {
        return getParentBone().isEmpty();
    }

    default Set<Bone> getDirectParents() { // Sorted bottom -> top
        Set<Bone> directParents = new ObjectLinkedOpenHashSet<>();
        Optional<? extends Bone> parentBone = getParentBone();

        while (parentBone.isPresent()) {
            directParents.add(parentBone.get());

            parentBone = parentBone.get().getParentBone();
        }

        return directParents;
    }
}
