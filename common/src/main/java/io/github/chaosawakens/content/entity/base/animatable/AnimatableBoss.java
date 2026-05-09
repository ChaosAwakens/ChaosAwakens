package io.github.chaosawakens.content.entity.base.animatable;

import io.github.chaosawakens.api.animation_resolver_system.faal.animation.Animatable;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation.AnimationInfo;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelInfo;
import io.github.chaosawakens.content.entity.base.stateful.StatefulBoss;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class AnimatableBoss extends StatefulBoss implements Animatable {

    protected AnimatableBoss(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public AnimationInfo getAnimationInfo() {
        return null;
    }

    @Override
    public ModelInfo getModelInfo() {
        return null;
    }

    @Override
    public double getAnimatableAge() {
        return tickCount;
    }

    @Override
    public boolean isValid() {
        return !isRemoved();
    }
}
