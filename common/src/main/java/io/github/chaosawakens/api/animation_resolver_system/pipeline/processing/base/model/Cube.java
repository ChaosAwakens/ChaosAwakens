package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelCubeData;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.template.CubeTemplate;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4d;

import java.util.Optional;

public interface Cube {

    @NotNull
    CubeTemplate getTemplate();

    Optional<? extends Bone> getParentBone();

    @NotNull
    ModelCoordinateData getCoordinateData();

    void refreshCoordinateData();

    Matrix4d buildCubeLocalMatrix();

    boolean allowsLocalClipping();

    void setAllowsLocalClipping(boolean allowsLocalClipping);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    boolean hasCollision();

    void setHasCollision(boolean hasCollision);

    boolean shouldSync();

    void setShouldSync(boolean shouldSync);

    @NotNull
    default ModelCubeData getBackingData() {
        return getTemplate().getBackingData();
    }

    default int getIndex() {
        return getTemplate().getIndex();
    }

    default int getParentBoneIndex() {
        return getTemplate().getParentBoneIndex();
    }

    default void enable() {
        setEnabled(true);
    }

    default void disable() {
        setEnabled(false);
    }

    default boolean isStandalone() {
        return getParentBone().isEmpty();
    }

    default AABB getGeneralBounds() {
        return getCoordinateData().getWorldAABB();
    }
}
