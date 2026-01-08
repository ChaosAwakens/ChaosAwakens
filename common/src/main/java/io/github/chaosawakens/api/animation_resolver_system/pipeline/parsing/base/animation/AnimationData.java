package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation;

import java.util.Map;

public interface AnimationData {

    String getAnimationName();

    double getAnimationLength();
    double getBlendWeight();
    double getAnimProgressModifier();

    boolean shouldLoop();

    <BD extends BoneData> Map<String, BD> getBoneData();
}
