package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelInfo;

@FunctionalInterface
public interface ModelSkeletonBakery { // TODO Proper impl/use

    Skeleton bake(ModelInfo srcModelInfo);
}
