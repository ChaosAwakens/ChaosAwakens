package io.github.chaosawakens.api.animation_resolver_system.faal.rendering.base;

import io.github.chaosawakens.api.animation_resolver_system.faal.AnimationManager;
import io.github.chaosawakens.api.animation_resolver_system.faal.hitbox.MappableHitboxOwner;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Skeleton;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public interface SkeletonModel<MHO extends MappableHitboxOwner> {

    @NotNull
    ResourceLocation getTextureLocation(@NotNull MHO mappableHitboxOwner);

    @NotNull
    default RenderType getRenderType(@NotNull MHO mappableHitboxOwner, @NotNull ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(textureLocation);
    }

    default void manageAnimationState(@NotNull Skeleton targetSkeleton, @NotNull MHO mappableHitboxOwner, AnimationManager<MHO> animationManager, float partialTick, float yaw, float pitch) {
    }
}
