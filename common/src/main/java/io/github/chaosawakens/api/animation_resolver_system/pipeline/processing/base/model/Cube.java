package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model;

import net.minecraft.world.phys.AABB;

import java.util.Optional;

public interface Cube {

    Optional<Bone> getParentBone();

    ModelCoordinateData getCoordinateData();

    boolean allowsLocalClipping();

    boolean isEnabled();

    void setEnabled(boolean enabled);

    boolean hasCollision();

    boolean shouldSync();

    void setCollision(boolean collision);

    void setAllowsLocalClipping(boolean allowsLocalClipping);

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
