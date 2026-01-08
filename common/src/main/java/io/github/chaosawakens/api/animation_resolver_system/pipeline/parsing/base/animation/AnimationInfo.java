package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation;

import java.util.Map;

public interface AnimationInfo {

    <AD extends AnimationData> Map<String, AD> getMappedAnimations();
}
