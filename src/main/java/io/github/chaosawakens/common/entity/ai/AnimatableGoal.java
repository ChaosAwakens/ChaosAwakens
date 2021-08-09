package io.github.chaosawakens.common.entity.ai;

import io.github.chaosawakens.common.entity.AnimatableMonsterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.Random;

/**
 * Base goal for Animatable entities
 * @author invalid2
 */
public abstract class AnimatableGoal extends Goal {

	protected AnimatableMonsterEntity entity;

	protected static final Random RANDOM = new Random();

	private long lastGameTime;
	protected long tickDelta;
	protected double animationProgress;
	private boolean isFirsLoop = true;

	/**
	 * Basic tick functionality that most AnimatableGoals will use
	 */
	public void baseTick() {
		if(this.isFirsLoop) {
			this.isFirsLoop = false;
			this.animationProgress += 1;
			this.lastGameTime = this.entity.level.getGameTime();
			return;
		}
		this.tickDelta = this.entity.level.getGameTime() - this.lastGameTime;
		this.animationProgress += 1 + this.tickDelta/100000.0;
		this.lastGameTime = this.entity.level.getGameTime();
	}

	protected static double getAttackReachSq(AnimatableMonsterEntity attacker, LivingEntity target) {
		return attacker.getBbWidth() * 2F * attacker.getBbWidth() * 2F + target.getBbWidth();
	}

	@Override
	abstract public boolean canUse();
}
