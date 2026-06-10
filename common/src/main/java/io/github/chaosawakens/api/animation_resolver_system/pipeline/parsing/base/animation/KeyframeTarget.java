package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation;

import org.joml.Vector3d;
import team.unnamed.mocha.MochaEngine;

import java.util.function.BiFunction;

@FunctionalInterface
public interface KeyframeTarget extends BiFunction<MochaEngine<?>, RawValue, Vector3d> {

}
