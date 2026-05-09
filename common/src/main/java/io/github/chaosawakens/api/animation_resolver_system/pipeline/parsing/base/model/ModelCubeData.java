package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model;

import org.joml.Vector3d;

import java.util.Optional;

public interface ModelCubeData {

    Vector3d getOrigin();

    Vector3d getSize();

    Optional<Vector3d> getPivot();

    Optional<Vector3d> getRotation();

    ModelCubeUVData getUVData();

    boolean isMirrored();
}
