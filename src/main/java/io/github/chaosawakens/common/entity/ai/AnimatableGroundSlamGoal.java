package io.github.chaosawakens.common.entity.ai;

import net.minecraft.entity.Entity;

public class AnimatableGroundSlamGoal extends AnimatableGoal{
	
	public AnimatableGroundSlamGoal(Entity ent, float animDuration, float slamFrame, double areaX, double areaY, double areaZ) {
		
	}

	@Override
	public boolean canUse() {
		return false;
	}

	@Override
	public boolean canContinueToUse() {
		// TODO Auto-generated method stub
		return false;
	}

}
