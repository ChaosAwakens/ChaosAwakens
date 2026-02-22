package io.github.chaosawakens.api.animation_resolver_system.pipeline.rendering.geckolib;

import io.github.chaosawakens.api.animation_resolver_system.faal.animation.Animatable;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.geckolib.model.GeckolibSkeleton;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;

public abstract class GeckolibEntitySkeletonRenderer<AE extends Entity & Animatable> extends EntityRenderer<AE> {
    protected final GeckolibSkeleton parentSkeleton;

    protected GeckolibEntitySkeletonRenderer(EntityRendererProvider.Context context, GeckolibSkeleton parentSkeleton) {
        super(context);

        this.parentSkeleton = parentSkeleton;
    }
}
