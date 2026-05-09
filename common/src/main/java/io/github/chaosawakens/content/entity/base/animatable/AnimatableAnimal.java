package io.github.chaosawakens.content.entity.base.animatable;

import io.github.chaosawakens.api.animation_resolver_system.faal.animation.Animatable;
import io.github.chaosawakens.content.entity.base.stateful.StatefulAnimal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public abstract class AnimatableAnimal extends StatefulAnimal implements Animatable {

    protected AnimatableAnimal(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }
}
