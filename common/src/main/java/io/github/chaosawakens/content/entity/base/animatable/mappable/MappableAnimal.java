package io.github.chaosawakens.content.entity.base.animatable.mappable;

import io.github.chaosawakens.api.animation_resolver_system.faal.hitbox.MappableHitboxOwner;
import io.github.chaosawakens.content.entity.base.animatable.AnimatableAnimal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public abstract class MappableAnimal extends AnimatableAnimal implements MappableHitboxOwner {

    protected MappableAnimal(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }
}
