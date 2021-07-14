package io.github.chaosawakens.common.entity.ai;

import java.util.Random;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.AnimatableMonsterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import software.bernie.geckolib3.network.ISyncable;

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
			this.lastGameTime = this.entity.world.getGameTime();
			return;
		}
		this.tickDelta = this.entity.world.getGameTime() - this.lastGameTime;
		this.animationProgress += 1 + this.tickDelta/100000.0;
		this.lastGameTime = this.entity.world.getGameTime();
	}
	
	protected static double getAttackReachSq(AnimatableMonsterEntity attacker, LivingEntity target) {
		return attacker.getWidth() * 2F * attacker.getWidth() * 2F + target.getWidth();
	}
	
	@Override
	abstract public boolean shouldExecute();
}
