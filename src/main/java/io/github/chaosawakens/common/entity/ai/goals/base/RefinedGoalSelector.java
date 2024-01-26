package io.github.chaosawakens.common.entity.ai.goals.base;

import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.profiler.IProfiler;

import java.util.function.Supplier;

public class RefinedGoalSelector extends GoalSelector {

    public RefinedGoalSelector(Supplier<IProfiler> profilerSupplier) {
        super(profilerSupplier);
    }

    @Override
    public void tick() {
        super.tick();
    }
}
