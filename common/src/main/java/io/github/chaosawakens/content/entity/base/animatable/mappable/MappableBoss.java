package io.github.chaosawakens.content.entity.base.animatable.mappable;

import io.github.chaosawakens.api.animation_resolver_system.faal.hitbox.MappableHitboxOwner;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.ARSPipeline;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Skeleton;
import io.github.chaosawakens.content.entity.base.animatable.AnimatableBoss;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public abstract class MappableBoss extends AnimatableBoss implements MappableHitboxOwner {
    protected final Skeleton skeleton;

    protected MappableBoss(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);

        this.skeleton = ARSPipeline.createGeckolibSkeleton(this);
    }

    @Override
    public @NotNull Skeleton getSkeleton() {
        return skeleton;
    }

    @Override
    public @NotNull AABB getBoundingBox() {
        return getSkeleton().getGeneralWorldBounds(this);
    }
}
