package io.github.chaosawakens.common.entity.ai;

import io.github.chaosawakens.common.entity.AnimatableAnimalEntity;
import io.github.chaosawakens.common.entity.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.robo.RoboPounderEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.Random;

public abstract class AnimatableGoal extends Goal {
	protected static final Random RANDOM = new Random();
	protected AnimatableMonsterEntity entity;
	protected AnimatableAnimalEntity animalEntity;
	protected RoboPounderEntity roboPounder;
	protected long tickDelta;
	protected double animationProgress;
	protected double animationLength;
	private long lastGameTime;
	private boolean isFirsLoop = true;

	public static double getAttackReachSq(AnimatableMonsterEntity attacker, LivingEntity target) {
		return attacker.getBbWidth() * 2F * attacker.getBbWidth() * 2F + target.getBbWidth();
	}

	public static double getAttackReachSq(AnimatableAnimalEntity attacker, LivingEntity target) {
		return attacker.getBbWidth() * 2F * attacker.getBbWidth() * 2F + target.getBbWidth();
	}

	public boolean isGoalInProgress() {
		return !isAnimationFinished() && canContinueToUse();
	}

	public boolean isAnimationFinished() {
		return this.animationProgress >= this.animationLength;
	}

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

	public void baseRoboTick() {
		if (this.roboPounder == null) return;
		if (this.isFirsLoop) {
			this.isFirsLoop = false;
			this.animationProgress += 1;
			this.lastGameTime = this.roboPounder.level.getGameTime();
			return;
		}
		this.tickDelta = this.roboPounder.level.getGameTime() - this.lastGameTime;
		this.animationProgress += 1 + this.tickDelta / 100000.0;
		this.lastGameTime = this.roboPounder.level.getGameTime();
	}

	@Override
	abstract public boolean canUse();

	@Override
	abstract public boolean canContinueToUse();
}
