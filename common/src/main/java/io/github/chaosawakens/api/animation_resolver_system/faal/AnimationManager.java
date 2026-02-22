package io.github.chaosawakens.api.animation_resolver_system.faal;

import io.github.chaosawakens.api.animation_resolver_system.faal.controller.AnimationController;
import io.github.chaosawakens.api.animation_resolver_system.faal.animation.Animatable;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public final class AnimationManager {
    private static final Object2ObjectOpenHashMap<Animatable, AnimationController<?>> MAPPED_CONTROLLERS = new Object2ObjectOpenHashMap<>();

    public static <A extends Animatable> AnimationController<A> createFor(A targetAnimatable) {
        AnimationController<A> controller = new AnimationController<>(targetAnimatable, false);
        MAPPED_CONTROLLERS.put(targetAnimatable, controller);
        return controller;
    }
}
