package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.geckolib.animation;

import com.mojang.datafixers.util.Either;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation.RawValue;

public record GeckolibRawValue(Either<String, Double> x, Either<String, Double> y, Either<String, Double> z) implements RawValue {
}
