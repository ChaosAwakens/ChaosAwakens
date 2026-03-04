package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.geckolib.animation;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation.KeyframeTarget;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation.RawValue;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.joml.Vector3d;

public record GeckolibKeyframeTarget(GeckolibRawValue backingValue) implements KeyframeTarget {
    public static final Codec<GeckolibKeyframeTarget> CODEC = Codec.list(Codec.either(Codec.STRING, Codec.DOUBLE)).xmap(
            exprOrLiteralList -> new GeckolibKeyframeTarget(new GeckolibRawValue(exprOrLiteralList.get(0), exprOrLiteralList.get(1), exprOrLiteralList.get(2))),
            keyframeTarget -> ObjectArrayList.of(keyframeTarget.backingValue().getX(), keyframeTarget.backingValue().getY(), keyframeTarget.backingValue().getZ())
    );

    @Override
    public Vector3d apply(RawValue rawValue) {
        return null;
    }
}
