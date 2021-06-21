package io.github.chaosawakens.common.entity.ai;

import io.github.chaosawakens.common.entity.AnimatableMonsterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

/**
 * @author invalid2
 *
 */
public abstract class AnimatableGoal extends Goal {
	
	protected AnimatableMonsterEntity entity;
	protected long tickDelta;
	protected long animationProgress;
	private boolean isFirsLoop = true;
	
	public void baseTick() {
		if(this.isFirsLoop) {
			this.isFirsLoop = false;
			this.tickDelta = this.entity.world.getGameTime();
		}
		this.tickDelta = this.entity.world.getGameTime() - this.tickDelta;
		this.animationProgress += this.tickDelta;
	}
	
	protected static double getAttackReachSq(AnimatableMonsterEntity attacker, LivingEntity target) {
		return attacker.getWidth() * 2.0F * attacker.getWidth() * 2.0F + target.getWidth();
	}
	
	@Override
	abstract public boolean shouldExecute();
}
