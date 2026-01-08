package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.geckolib.animation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation.AnimationData;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public record GeckolibAnimationData(String animationName, double animationLength, Double blendWeight, boolean shouldLoop, List<GeckolibBoneData> boneData) implements AnimationData {
    public static final Codec<GeckolibAnimationData> BARE_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.optionalFieldOf("animation_length", 0.0D).forGetter(GeckolibAnimationData::animationLength),
            Codec.DOUBLE.optionalFieldOf("blend_weight", 1.0D).forGetter(GeckolibAnimationData::blendWeight),
            Codec.BOOL.optionalFieldOf("loop", true).forGetter(GeckolibAnimationData::shouldLoop),
            GeckolibBoneData.LIST_CODEC.fieldOf("bones").forGetter(animData -> Optional.ofNullable(animData.boneData).orElse(ObjectArrayList.of()))
    ).apply(instance, GeckolibAnimationData::new));
    public static final Codec<GeckolibAnimationData> STANDALONE_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("animation_name").forGetter(GeckolibAnimationData::animationName),
            Codec.DOUBLE.optionalFieldOf("animation_length", 0.0D).forGetter(GeckolibAnimationData::animationLength),
            Codec.DOUBLE.optionalFieldOf("blend_weight", 1.0D).forGetter(GeckolibAnimationData::blendWeight),
            Codec.BOOL.optionalFieldOf("loop", true).forGetter(GeckolibAnimationData::shouldLoop),
            GeckolibBoneData.LIST_CODEC.fieldOf("bones").forGetter(animData -> Optional.ofNullable(animData.boneData).orElse(ObjectArrayList.of()))
    ).apply(instance, GeckolibAnimationData::new));

    public GeckolibAnimationData(double animationLength, Double blendWeight, boolean shouldLoop, List<GeckolibBoneData> boneData) {
        this("", animationLength, blendWeight, shouldLoop, boneData);
    }

    public GeckolibAnimationData withAnimationName(String animationName) {
        return new GeckolibAnimationData(animationName, animationLength, blendWeight, shouldLoop, boneData);
    }

    @Override
    public String getAnimationName() {
        return animationName;
    }

    @Override
    public double getAnimationLength() {
        return animationLength;
    }

    @Override
    public double getBlendWeight() {
        return blendWeight;
    }

    @Override
    public double getAnimProgressModifier() {
        return 1.0D;
    }

    @Override
    public boolean shouldLoop() {
        return shouldLoop;
    }

    @Override
    public Map<String, GeckolibBoneData> getBoneData() {
        return boneData.stream().collect(Collectors.toMap(GeckolibBoneData::boneName, boneData -> boneData));
    }
}
