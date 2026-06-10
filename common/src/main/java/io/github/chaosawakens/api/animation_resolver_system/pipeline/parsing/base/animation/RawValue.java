package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation;

import com.mojang.datafixers.util.Either;

public interface RawValue {

    Either<String, Double> x();

    Either<String, Double> y();

    Either<String, Double> z();

    default boolean isLiteral() {
        return x().right().isPresent() && y().right().isPresent() && z().right().isPresent();
    }
}
