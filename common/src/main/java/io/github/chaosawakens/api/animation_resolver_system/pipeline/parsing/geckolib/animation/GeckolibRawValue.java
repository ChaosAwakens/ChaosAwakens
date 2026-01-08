package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.geckolib.animation;

import com.mojang.datafixers.util.Either;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation.RawValue;

public class GeckolibRawValue implements RawValue {
    protected final Either<String, Double> x;
    protected final Either<String, Double> y;
    protected final Either<String, Double> z;

    public GeckolibRawValue(Either<String, Double> x, Either<String, Double> y, Either<String, Double> z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public Either<String, Double> getX() {
        return x;
    }

    @Override
    public Either<String, Double> getY() {
        return y;
    }

    @Override
    public Either<String, Double> getZ() {
        return z;
    }
}
