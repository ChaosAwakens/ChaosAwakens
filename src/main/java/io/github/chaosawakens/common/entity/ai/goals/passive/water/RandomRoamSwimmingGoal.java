package io.github.chaosawakens.common.entity.ai.goals.passive.water;

import io.github.chaosawakens.common.entity.base.AnimatableFishEntity;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;

public class RandomRoamSwimmingGoal extends RandomSwimmingGoal {
	private AnimatableFishEntity owner;

	public RandomRoamSwimmingGoal(AnimatableFishEntity owner, double speedModifier, int useInterval) {
		super(owner, speedModifier, useInterval);
		this.owner = owner;
	}
	
	public RandomRoamSwimmingGoal(AnimatableFishEntity owner, double speedModifier) {
		this(owner, speedModifier, 20);
	}
	
	public RandomRoamSwimmingGoal(AnimatableFishEntity owner, int useInterval) {
		this(owner, 1.0D, useInterval);
	}
	
	public RandomRoamSwimmingGoal(AnimatableFishEntity owner) {
		this(owner, 1.0D, 20);
	}
	
	@Override
	public boolean canUse() {
		return owner.canRoam() && super.canUse();
	}

}
