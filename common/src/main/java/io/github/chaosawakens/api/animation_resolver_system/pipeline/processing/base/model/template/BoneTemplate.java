package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.template;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelBoneData;
import org.jetbrains.annotations.NotNull;

public interface BoneTemplate {

    int getIndex();

    @NotNull
    String getName();

    @NotNull
    ModelBoneData getBackingData();

    int getParentIndex();

    int getDepth();

    int[] getChildIndices();
    int[] getCubeIndices();

    default boolean isRoot() {
        return getParentIndex() == SkeletonTemplate.ROOT_INDEX;
    }

    default boolean hasCubes() {
        return getCubeIndices().length > 0;
    }
}
