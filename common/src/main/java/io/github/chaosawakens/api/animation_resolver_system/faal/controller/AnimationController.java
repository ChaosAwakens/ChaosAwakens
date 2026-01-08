package io.github.chaosawakens.api.animation_resolver_system.faal.controller;

import io.github.chaosawakens.api.animation_resolver_system.faal.animation.Animatable;

public class AnimationController<A extends Animatable> {
    protected final A animatable;
    protected double authoritativeTickProgress = 0.0D;

    public AnimationController(A animatable) {
        this.animatable = animatable;
    }
}
