package io.github.chaosawakens.api.animation.geckolib.object.animation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.api.animation.geckolib.math.easing.base.MathematicalEasing;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

import java.util.List;
import java.util.Optional;

public record AnimationKeyframeInfo(Vector3d targetValue, @Nullable MathematicalEasing easing, Optional<List<Double>> easingArgs) {
    public static final Codec<AnimationKeyframeInfo> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.listOf().xmap(
                    valList -> new Vector3d(valList.get(0), valList.get(1), valList.get(2)),
                    vector -> ObjectArrayList.of(vector.x(), vector.y(), vector.z()))
                    .fieldOf("vector").forGetter(AnimationKeyframeInfo::targetValue),
            Codec.STRING.optionalFieldOf("easing").xmap(
                    easingName -> easingName.isEmpty() ? MathematicalEasing.LINEAR : MathematicalEasing.getEasing(easingName.get()),
                    easing -> Optional.of(MathematicalEasing.getEasingName(easing))).forGetter(AnimationKeyframeInfo::easing),
            Codec.DOUBLE.listOf().optionalFieldOf("easingArgs").forGetter(AnimationKeyframeInfo::easingArgs)
    ).apply(instance, AnimationKeyframeInfo::new));
    public static final Codec<List<AnimationKeyframeInfo>> LIST_CODEC = Codec.list(AnimationKeyframeInfo.CODEC);
}
