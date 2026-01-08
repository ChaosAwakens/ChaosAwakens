package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model;

import java.util.Optional;

public interface Cube {

    Optional<Bone> getParentBoneBB();

    ModelCoordinateData getCoordinateData();

    boolean allowsLocalClipping();
    boolean isEnabled();
    boolean hasCollision();
    boolean shouldSync();

    default boolean isStandalone() {
        return getParentBoneBB().isEmpty();
    }
}
