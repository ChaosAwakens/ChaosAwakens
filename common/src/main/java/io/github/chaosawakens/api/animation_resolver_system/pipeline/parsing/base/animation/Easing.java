package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation;

@FunctionalInterface
public interface Easing {

    double apply(double timeDelta);
}
