package io.github.chaosawakens.api.animation_resolver_system.faal;

import com.mememan.nexus.asm.annotations.PostInit;
import com.mememan.nexus.template.event.blueprint.common.TickEventBlueprint;
import io.github.chaosawakens.api.animation_resolver_system.faal.animation.Animatable;
import io.github.chaosawakens.api.animation_resolver_system.faal.animation.controller.AnimationController;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@PostInit
public final class AnimationManager<A extends Animatable> {
    private static final Map<Animatable, AnimationManager<?>> MANAGED_CONTROLLERS = new Object2ObjectOpenHashMap<>();

    static {
        TickEventBlueprint.SERVER_LEVEL_TICK.onEvent(event -> {
            MANAGED_CONTROLLERS.entrySet().removeIf(curEntry -> {
                if (!curEntry.getKey().isValid()) return true;

                curEntry.getValue().tickAllControllers();

                return false;
            });
        });
    }

    private final A owner;
    private final ObjectArrayList<AnimationController<A>> activeControllers = new ObjectArrayList<>();

    private AnimationManager(A owner) {
        this.owner = owner;
    }

    public static <A extends Animatable> AnimationController<A> createFor(A targetAnimatable, ResourceLocation controllerId) {
        if (targetAnimatable == null || controllerId == null) return null;

        AnimationManager<A> manager = (AnimationManager<A>) MANAGED_CONTROLLERS.computeIfAbsent(targetAnimatable, k -> new AnimationManager<>(k));
        AnimationController<A> controller = new AnimationController<>(controllerId, targetAnimatable);

        manager.activeControllers.add(controller);

        return controller;
    }

    public void tickAllControllers() {
        activeControllers.forEach(AnimationController::validateAndTickControllerState);
    }

    public A getAnimatableOwner() {
        return owner;
    }

    public List<AnimationController<A>> getActiveControllers() {
        return ObjectLists.unmodifiable(activeControllers);
    }

    public Optional<AnimationController<A>> getActiveController(ResourceLocation controllerId) {
        return activeControllers.stream()
                .filter(curController -> curController.getControllerId().equals(controllerId))
                .findFirst();
    }
}
