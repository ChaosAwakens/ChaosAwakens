package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model;

import java.util.List;
import java.util.Optional;

public interface Bone {

    Optional<Bone> getParentBoneBB();

    ModelCoordinateData getCoordinateData();

    List<Cube> getCubeBBs();

    default boolean isHomogeneous() {
        return getCubeBBs().isEmpty() || getCubeBBs().stream().noneMatch(Cube::isEnabled);
    }

    default boolean isRoot() {
        return getParentBoneBB().isEmpty();
    }
}
