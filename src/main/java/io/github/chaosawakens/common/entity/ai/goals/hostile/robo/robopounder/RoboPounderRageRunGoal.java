package io.github.chaosawakens.common.entity.ai.goals.hostile.robo.robopounder;

import io.github.chaosawakens.common.entity.hostile.robo.RoboPounderEntity;
import net.minecraft.entity.ai.goal.Goal;

public class RoboPounderRageRunGoal extends Goal {
	private final RoboPounderEntity animatableOwner;
	
	public RoboPounderRageRunGoal(RoboPounderEntity animatableOwner) {
		this.animatableOwner = animatableOwner;
	}

	@Override
	public boolean canUse() {
		return false;
	}
	
	@Override
	public boolean canContinueToUse() {
		return super.canContinueToUse();
	}
	
	@Override
	public boolean isInterruptable() {
		return super.isInterruptable();
	}
	
	@Override
	public void start() {
	}
	
	@Override
	public void stop() {
	}
	
	@Override
	public void tick() {
		
	}
}
