package io.github.chaosawakens.api.animation_resolver_system.faal.animation;

import io.github.chaosawakens.api.animation_resolver_system.faal.AnimationManager;
import io.github.chaosawakens.api.animation_resolver_system.faal.controller.AnimationController;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public interface AnimatableEntity extends Animatable {

    <AE extends AnimatableEntity> AnimationManager<AE> getAnimationManager();

    @Nullable
    default <AE extends AnimatableEntity> AnimationController<AE> getAnimationControllerByName(ResourceLocation controllerId) {
        return getAnimationManager().getActiveController(controllerId)
                .map(controller -> (AnimationController<AE>) controller) // getAnimationManager()'s generic type already asserts the presumption being made by this cast
                .orElse(null);
    }
}
