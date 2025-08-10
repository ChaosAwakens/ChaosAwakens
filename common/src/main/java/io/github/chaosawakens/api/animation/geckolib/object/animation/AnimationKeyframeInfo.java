package io.github.chaosawakens.api.animation.geckolib.object.animation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.api.animation.geckolib.math.easing.base.MathematicalEasing;
import io.github.chaosawakens.api.codec.OtherCodecs;
import org.joml.Vector3d;

import java.util.List;
import java.util.Optional;

public record AnimationKeyframeInfo(Vector3d targetValue, Optional<MathematicalEasing> easing, Optional<List<Double>> easingArgs) {
    public static final Codec<AnimationKeyframeInfo> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            OtherCodecs.VECTOR_3D_CODEC.fieldOf("vector").forGetter(AnimationKeyframeInfo::targetValue),
            Codec.STRING.optionalFieldOf("easing").xmap(
                    easingName -> easingName.map(MathematicalEasing::getEasing).or(() -> Optional.of(MathematicalEasing.LINEAR)),
                    easing -> Optional.of(MathematicalEasing.getEasingName(easing.orElseGet(() -> MathematicalEasing.LINEAR)))).forGetter(AnimationKeyframeInfo::easing),
            Codec.DOUBLE.listOf().optionalFieldOf("easingArgs").forGetter(AnimationKeyframeInfo::easingArgs)
    ).apply(instance, AnimationKeyframeInfo::new));
    public static final Codec<List<AnimationKeyframeInfo>> LIST_CODEC = Codec.list(AnimationKeyframeInfo.CODEC);
}
