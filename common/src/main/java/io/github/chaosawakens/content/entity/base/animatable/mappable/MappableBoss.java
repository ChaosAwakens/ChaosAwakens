package io.github.chaosawakens.content.entity.base.animatable.mappable;

import io.github.chaosawakens.api.animation_resolver_system.faal.hitbox.MappableHitboxOwner;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Skeleton;
import io.github.chaosawakens.content.entity.base.animatable.AnimatableBoss;
import io.github.chaosawakens.util.EntityUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class MappableBoss extends AnimatableBoss implements MappableHitboxOwner {
    protected final Skeleton skeleton;

    protected MappableBoss(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);

        this.skeleton = EntityUtil.createGeckolibSkeleton(this);
    }

    @Override
    public @NotNull Skeleton getSkeleton() {
        return skeleton;
    }
}
