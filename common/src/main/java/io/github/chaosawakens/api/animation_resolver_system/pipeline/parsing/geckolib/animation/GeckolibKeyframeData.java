package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.geckolib.animation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation.KeyframeData;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation.KeyframeType;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public record GeckolibKeyframeData(KeyframeType targetType, GeckolibKeyframeTarget targetValue, Supplier<GeckolibEasing> easing, List<Double> easingArgs) implements KeyframeData {
    public static final Codec<GeckolibKeyframeData> BARE_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GeckolibKeyframeTarget.CODEC.fieldOf("vector").forGetter(GeckolibKeyframeData::targetValue),
            Codec.STRING.optionalFieldOf("easing", "linear").xmap(
                    GeckolibEasing::getEasing,
                    GeckolibEasing::getEasingName
            ).forGetter(GeckolibKeyframeData::easing),
            Codec.DOUBLE.listOf().optionalFieldOf("easingArgs", ObjectArrayList.of()).forGetter(GeckolibKeyframeData::easingArgs)
    ).apply(instance, GeckolibKeyframeData::new));
    public static final Codec<GeckolibKeyframeData> STANDALONE_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("type").xmap(KeyframeType::byName, KeyframeType::getSerializedName).forGetter(GeckolibKeyframeData::targetType),
            GeckolibKeyframeTarget.CODEC.fieldOf("vector").forGetter(GeckolibKeyframeData::targetValue),
            Codec.STRING.optionalFieldOf("easing", "linear").xmap(
                    GeckolibEasing::getEasing,
                    GeckolibEasing::getEasingName
            ).forGetter(GeckolibKeyframeData::easing),
            Codec.DOUBLE.listOf().optionalFieldOf("easingArgs", ObjectArrayList.of()).forGetter(GeckolibKeyframeData::easingArgs)
    ).apply(instance, GeckolibKeyframeData::new));
    public static final Codec<List<GeckolibKeyframeData>> LIST_BARE_CODEC = Codec.list(BARE_CODEC);
    public static final Codec<List<GeckolibKeyframeData>> LIST_STANDALONE_CODEC = Codec.list(STANDALONE_CODEC);

    public GeckolibKeyframeData(GeckolibKeyframeTarget targetValue, Supplier<GeckolibEasing> easing, List<Double> easingArgs) {
        this(null, targetValue, easing, easingArgs);
    }

   public GeckolibKeyframeData withKeyframeType(KeyframeType targetType) {
        return new GeckolibKeyframeData(targetType, targetValue, easing, easingArgs);
    }

    @Override
    public GeckolibKeyframeTarget getPosition() {
        return Objects.equals(targetType, KeyframeType.POSITION) ? targetValue : null;
    }

    @Override
    public GeckolibKeyframeTarget getRotation() {
        return Objects.equals(targetType, KeyframeType.ROTATION) ? targetValue : null;
    }

    @Override
    public GeckolibKeyframeTarget getScale() {
        return Objects.equals(targetType, KeyframeType.SCALE) ? targetValue : null;
    }

    @Override
    public Supplier<GeckolibEasing> getEasing() {
        return easing;
    }
}
