package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.geckolib.animation;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation.BoneData;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation.KeyframeType;
import io.github.chaosawakens.util.CodecUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public record GeckolibBoneData(String boneName, Map<KeyframeType, Map<Double, GeckolibKeyframeData>> keyframeData) implements BoneData {
    public static final Codec<Map<Double, GeckolibKeyframeData>> KEYFRAME_TIMELINE_CODEC = Codec.unboundedMap(Codec.STRING, GeckolibKeyframeData.BARE_CODEC).xmap(
            convertedDoubleKFInfoMap -> convertedDoubleKFInfoMap.entrySet().stream().collect(Collectors.toMap(entry -> Double.parseDouble(entry.getKey()), Map.Entry::getValue, (a, b) -> a)),
            originalMappedKFInfoMap -> originalMappedKFInfoMap.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().toString(), Map.Entry::getValue, (a, b) -> a))
    );
    public static final Codec<Map<KeyframeType, Map<Double, GeckolibKeyframeData>>> KEYFRAME_MAP_CODEC = Codec.unboundedMap(Codec.STRING.xmap(KeyframeType::byName, KeyframeType::getSerializedName), Codec.either(KEYFRAME_TIMELINE_CODEC, CodecUtil.wrapInObject(GeckolibKeyframeTarget.CODEC, "vector"))).xmap(
            convertedMap -> convertedMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().map(Function.identity(), data -> Map.of(0.0D, new GeckolibKeyframeData(entry.getKey(), data.value(), () -> GeckolibEasing.LINEAR, ObjectArrayList.of()))), (a, b) -> a)),
            originalMap -> originalMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> Either.left(entry.getValue()), (a, b) -> a))
    );
    public static final Codec<List<GeckolibBoneData>> LIST_CODEC = Codec.unboundedMap(Codec.STRING, KEYFRAME_MAP_CODEC).xmap(
            animationBoneMap -> animationBoneMap.entrySet().stream()
                    .map(boneInfoEntry -> new GeckolibBoneData(boneInfoEntry.getKey(), boneInfoEntry.getValue().entrySet().stream()
                            .collect(Collectors.toMap(Map.Entry::getKey, kfBasedEntry -> kfBasedEntry.getValue().entrySet().stream()
                                    .collect(Collectors.toMap(Map.Entry::getKey, timelineBasedEntry -> timelineBasedEntry.getValue().withKeyframeType(kfBasedEntry.getKey()), (a, b) -> a, Object2ObjectOpenHashMap::new)), (a, b) -> a, Object2ObjectOpenHashMap::new))))
                    .collect(Collectors.toCollection(ObjectArrayList::new)),
            boneInfoList -> boneInfoList.stream()
                    .collect(Collectors.toMap(GeckolibBoneData::boneName, GeckolibBoneData::keyframeData, (a, b) -> a, Object2ObjectOpenHashMap::new))
    );

    @Override
    public String getBoneName() {
        return boneName;
    }

    @Override
    public Map<KeyframeType, Map<Double, GeckolibKeyframeData>> getKeyframeData() {
        return keyframeData;
    }
}
