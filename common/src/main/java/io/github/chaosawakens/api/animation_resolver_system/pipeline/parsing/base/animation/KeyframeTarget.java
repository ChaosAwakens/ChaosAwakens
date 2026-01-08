package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation;

import org.joml.Vector3d;

import java.util.function.Function;

@FunctionalInterface
public interface KeyframeTarget extends Function<RawValue, Vector3d> {

}
