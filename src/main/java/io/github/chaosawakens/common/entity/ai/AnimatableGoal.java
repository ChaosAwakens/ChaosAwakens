package io.github.chaosawakens.common.entity.ai;

import java.util.Random;

import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;

public abstract class AnimatableGoal extends Goal {
	protected static final Random RANDOM = new Random();
	protected MobEntity entity;
	protected AnimatableAnimalEntity animalEntity;
	protected long tickDelta;
	protected double animationProgress;
	protected long lastGameTime;
	protected boolean isFirsLoop = true;

	public static double getAttackReachSq(MobEntity attacker, LivingEntity target) {
		double atkRch = attacker.getBbWidth() * 1.75D + (attacker.getEyeHeight() / 3.6D * 0.25D);
		double offset = attacker.getBbWidth() * 0.5D;
		return (atkRch * atkRch) / 1.5;
	}

	public static double getAttackReachSq(AnimatableAnimalEntity attacker, LivingEntity target) {
		double atkRch = attacker.getBbWidth() * 1.75D + (attacker.getEyeHeight() / 3.6D * 0.25D);
		double offset = attacker.getBbWidth() * 0.5D;
		return atkRch * atkRch + offset * offset - 1.8D;
	}

//	public boolean isGoalInProgress() {
	//	return !isAnimationFinished() && canContinueToUse();
	//}

	public void baseTick() {
		if (this.entity == null) return;
		if (this.isFirsLoop) {
			this.isFirsLoop = false;
			this.animationProgress += 1;
			this.lastGameTime = this.entity.level.getGameTime();
			return;
		}
		this.tickDelta = this.entity.level.getGameTime() - this.lastGameTime;
		this.animationProgress += 1 + this.tickDelta / 100000.0;
		this.lastGameTime = this.entity.level.getGameTime();
	}

	@Override
	abstract public boolean canUse();

	@Override
	abstract public boolean canContinueToUse();
}
