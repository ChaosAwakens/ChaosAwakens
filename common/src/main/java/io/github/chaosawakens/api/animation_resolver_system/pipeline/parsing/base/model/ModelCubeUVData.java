package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model;

public interface ModelCubeUVData {

    ModelCubeDirectionalUVData getNorthUVInfo();
    ModelCubeDirectionalUVData getSouthUVInfo();
    ModelCubeDirectionalUVData getEastUVInfo();
    ModelCubeDirectionalUVData getWestUVInfo();
    ModelCubeDirectionalUVData getUpUVInfo();
    ModelCubeDirectionalUVData getDownUVInfo();
}
