package io.github.chaosawakens.api.animation.geckolib.object.animation;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record AnimationBoneInfo(String boneName, Map<KeyframeType, Map<Double, AnimationKeyframeInfo>> keyframes) {
    public static final Codec<Map<Double, AnimationKeyframeInfo>> KEYFRAME_TIMELINE_CODEC = Codec.unboundedMap(Codec.STRING, AnimationKeyframeInfo.CODEC).xmap(
            convertedDoubleKFInfoMap -> convertedDoubleKFInfoMap.entrySet().stream().collect(Collectors.toMap(entry -> Double.parseDouble(entry.getKey()), Map.Entry::getValue, (a, b) -> a)),
            originalMappedKFInfoMap -> originalMappedKFInfoMap.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().toString(), Map.Entry::getValue, (a, b) -> a))
    );
    public static final Codec<Map<KeyframeType, Map<Double, AnimationKeyframeInfo>>> KEYFRAME_MAP_CODEC = Codec.unboundedMap(KeyframeType.CODEC, KEYFRAME_TIMELINE_CODEC);
    public static final Codec<List<AnimationBoneInfo>> LIST_CODEC = Codec.unboundedMap(Codec.STRING, KEYFRAME_MAP_CODEC).xmap(
            animationBoneMap -> animationBoneMap.entrySet().stream()
                    .map(boneInfoEntry -> new AnimationBoneInfo(boneInfoEntry.getKey(), boneInfoEntry.getValue()))
                    .collect(Collectors.toCollection(ObjectArrayList::new)),
            boneInfoList -> boneInfoList.stream()
                    .collect(Collectors.toMap(AnimationBoneInfo::boneName, AnimationBoneInfo::keyframes, (a, b) -> a))
    );
}
