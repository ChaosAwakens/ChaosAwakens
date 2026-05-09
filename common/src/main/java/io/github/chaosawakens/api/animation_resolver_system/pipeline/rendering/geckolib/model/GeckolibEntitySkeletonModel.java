package io.github.chaosawakens.api.animation_resolver_system.pipeline.rendering.geckolib.model;

import io.github.chaosawakens.api.animation_resolver_system.faal.AnimationManager;
import io.github.chaosawakens.api.animation_resolver_system.faal.hitbox.MappableHitboxOwner;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Skeleton;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.rendering.base.SkeletonModel;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class GeckolibEntitySkeletonModel<AE extends Entity & MappableHitboxOwner> implements SkeletonModel<AE> {

    public GeckolibEntitySkeletonModel() {
    }

    @Override
    public @NotNull Skeleton getSkeleton() {
        return null;
    }

    @Override
    public void manageAnimationState(Skeleton targetSkeleton, AE mappableHitboxOwner, AnimationManager<AE> animationManager, float partialTick, float yaw, float pitch) {

    }
}
