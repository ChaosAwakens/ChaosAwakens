package io.github.chaosawakens.api.animation_resolver_system.faal.animation;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation.AnimationInfo;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelInfo;

public interface Animatable {

    AnimationInfo getAnimationInfo();

    ModelInfo getModelInfo();

    double getAnimatableAge();

    boolean isValid();
}
