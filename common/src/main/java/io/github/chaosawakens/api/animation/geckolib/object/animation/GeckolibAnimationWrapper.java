package io.github.chaosawakens.api.animation.geckolib.object.animation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Map;

public record GeckolibAnimationWrapper(Map<String, AnimationInfo> animations) {
    public static final Codec<GeckolibAnimationWrapper> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, AnimationInfo.CODEC).fieldOf("animations").forGetter(GeckolibAnimationWrapper::animations)
    ).apply(instance, GeckolibAnimationWrapper::new));
}
