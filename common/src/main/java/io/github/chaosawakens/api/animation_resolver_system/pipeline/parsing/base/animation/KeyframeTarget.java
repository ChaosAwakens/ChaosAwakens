package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation;

import org.joml.Vector3d;
import team.unnamed.mocha.MochaEngine;

import java.util.function.Function;

@FunctionalInterface
public interface KeyframeTarget extends Function<RawValue, Vector3d> {

    default double getX(MochaEngine engine, RawValue rawValue) {
        return rawValue.getX().left()
                .map(expr -> Double.parseDouble(expr))
                .orElseGet(rawValue.getX().right()::get);
    }

    default double getY(RawValue rawValue) {
        return rawValue.getY().left()
                .map(expr -> Double.parseDouble(expr))
                .orElseGet(rawValue.getY().right()::get);
    }

    default double getZ(RawValue rawValue) {
        return rawValue.getZ().left()
                .map(expr -> Double.parseDouble(expr))
                .orElseGet(rawValue.getZ().right()::get);
    }
}
