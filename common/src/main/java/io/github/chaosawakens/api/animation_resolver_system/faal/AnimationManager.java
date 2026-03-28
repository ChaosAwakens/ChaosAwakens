package io.github.chaosawakens.api.animation_resolver_system.faal;

import io.github.chaosawakens.api.animation_resolver_system.faal.animation.Animatable;
import io.github.chaosawakens.api.animation_resolver_system.faal.controller.AnimationController;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class AnimationManager<A extends Animatable> {
    private static final Map<Animatable, AnimationManager<?>> MANAGED_CONTROLLERS = new Object2ObjectOpenHashMap<>();
    private final List<AnimationController<A>> activeControllers = new LinkedList<>();

    private AnimationManager() {

    }

    public static <A extends Animatable> AnimationController<A> createFor(A targetAnimatable) {
        if (targetAnimatable == null) return null;

        AnimationManager<A> manager = (AnimationManager<A>) MANAGED_CONTROLLERS.computeIfAbsent(targetAnimatable, k -> new AnimationManager<>());
        AnimationController<A> controller = new AnimationController<>(targetAnimatable);

        manager.activeControllers.add(controller);

        return controller;
    }
}
