package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.template;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelCubeData;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.ModelCoordinateData;
import org.jetbrains.annotations.NotNull;

public interface CubeTemplate {

    @NotNull
    ModelCubeData getBackingData();

    int getIndex();
    int getParentBoneIndex();

    @NotNull
    ModelCoordinateData createCoordinateData();

    boolean allowsLocalClipping();
    boolean hasCollision();
    boolean shouldSync();
}
