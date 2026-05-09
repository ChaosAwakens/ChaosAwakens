package io.github.chaosawakens.api.ai.prototyping.goals.passive;

import io.github.chaosawakens.content.entity.base.stateful.StatefulAnimal;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;

public class StatefulPanicGoal extends PanicGoal {

    public StatefulPanicGoal(PathfinderMob mob, double speedModifier) {
        super(mob, speedModifier);
    }

    public StatefulPanicGoal(PathfinderMob mob) {
        this(mob, mob instanceof StatefulAnimal statefulAnimal ? statefulAnimal.getPanicSpeedModifier() : 1.5D);
    }

    @Override
    public void start() {
        super.start();

        if (mob instanceof StatefulAnimal statefulAnimal) statefulAnimal.setPanicking(true);
    }

    @Override
    public void stop() {
        super.stop();

        if (mob instanceof StatefulAnimal statefulAnimal) statefulAnimal.setPanicking(false);
    }
}
