package io.github.chaosawakens.api.animation_resolver_system.pipeline.rendering.base;

import io.github.chaosawakens.api.animation_resolver_system.faal.AnimationManager;
import io.github.chaosawakens.api.animation_resolver_system.faal.hitbox.MappableHitboxOwner;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Skeleton;
import org.jetbrains.annotations.NotNull;

public interface SkeletonModel<MHO extends MappableHitboxOwner> {

    @NotNull
    Skeleton getSkeleton();

    void manageAnimationState(Skeleton targetSkeleton, MHO mappableHitboxOwner, AnimationManager<MHO> animationManager, float partialTick, float yaw, float pitch); // yaw and pitch here are unused for block entities
}
