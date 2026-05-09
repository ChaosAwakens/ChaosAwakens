package io.github.chaosawakens.content.entity.base.animatable.mappable;

import io.github.chaosawakens.api.animation_resolver_system.faal.hitbox.MappableHitboxOwner;
import io.github.chaosawakens.content.entity.base.animatable.AnimatableMonster;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class MappableMonster extends AnimatableMonster implements MappableHitboxOwner {

    protected MappableMonster(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }
}
