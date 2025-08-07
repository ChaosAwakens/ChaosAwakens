package io.github.chaosawakens.api.animation.geckolib.object.animation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import java.util.Optional;

public record AnimationInfo(Optional<Boolean> shouldLoop, Optional<Double> animLengthSec, List<AnimationBoneInfo> boneInfo) {
    public static final Codec<AnimationInfo> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("loop").forGetter(AnimationInfo::shouldLoop),
            Codec.DOUBLE.optionalFieldOf("animation_length").forGetter(AnimationInfo::animLengthSec),
            AnimationBoneInfo.LIST_CODEC.fieldOf("bones").forGetter(AnimationInfo::boneInfo)
    ).apply(instance, AnimationInfo::new));
}
