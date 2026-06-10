package io.github.chaosawakens.content.entity.base.animatable;

import io.github.chaosawakens.api.animation_resolver_system.faal.animation.Animatable;
import io.github.chaosawakens.content.entity.base.stateful.StatefulMonster;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class AnimatableMonster extends StatefulMonster implements Animatable {
    protected float zRot = 0.0F;
    protected float zRotO = 0.0F;

    protected AnimatableMonster(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }
}
