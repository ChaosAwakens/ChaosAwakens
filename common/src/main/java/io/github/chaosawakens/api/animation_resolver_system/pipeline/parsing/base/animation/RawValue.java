package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation;

import com.mojang.datafixers.util.Either;

public interface RawValue {

    Either<String, Double> getX();
    Either<String, Double> getY();
    Either<String, Double> getZ();

    default boolean isLiteral() {
        return getX().right().isPresent() && getY().right().isPresent() && getZ().right().isPresent();
    }
}
