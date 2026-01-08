package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.geckolib.animation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation.AnimationInfo;

import java.util.Map;
import java.util.stream.Collectors;

public record GeckolibAnimationInfo(Map<String, GeckolibAnimationData> mappedAnimations) implements AnimationInfo {
    public static final Codec<GeckolibAnimationInfo> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, GeckolibAnimationData.BARE_CODEC)
                    .xmap(
                            convertedMap -> convertedMap.entrySet().stream()
                                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().withAnimationName(entry.getKey()), (a, b) -> a)),
                            originalMap -> originalMap.entrySet().stream()
                                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a))
                    )
                    .fieldOf("animations").forGetter(GeckolibAnimationInfo::mappedAnimations)
    ).apply(instance, GeckolibAnimationInfo::new));

    @Override
    public Map<String, GeckolibAnimationData> getMappedAnimations() {
        return mappedAnimations;
    }
}
