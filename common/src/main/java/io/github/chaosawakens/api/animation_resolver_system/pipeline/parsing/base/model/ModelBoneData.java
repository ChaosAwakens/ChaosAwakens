package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model;

import org.joml.Vector3d;

import java.util.List;
import java.util.Optional;

public interface ModelBoneData {

    String getBoneName();

    Vector3d getPivot();

    Optional<String> getParentBoneName();

    Optional<Vector3d> getRotation();

    <MCD extends ModelCubeData> List<MCD> getCubes();
}
