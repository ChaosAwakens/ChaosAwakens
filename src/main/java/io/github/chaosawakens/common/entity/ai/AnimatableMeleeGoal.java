package io.github.chaosawakens.common.entity.ai;

import java.util.EnumSet;
import java.util.function.BiFunction;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;

public class AnimatableMeleeGoal extends AnimatableGoal {
	protected final double animationLength;
	protected final BiFunction<Double, Double, Boolean> attackPredicate;
	protected final BiFunction<Double, Double, Boolean> attackEndPredicate;
	protected boolean hasHit;
	public boolean shouldLoop;

	public AnimatableMeleeGoal(AnimatableMonsterEntity entity, double animationLength, double attackBegin, double attackEnd) {
		this.entity = entity;
		this.animationLength = animationLength;
		this.attackPredicate = (progress, length) -> attackBegin < progress / (length) && progress / (length) < attackEnd;
		this.attackEndPredicate = (progress, length) -> ++progress <= animationLength;
		this.setFlags(EnumSet.of(Goal.Flag.LOOK));
	}

	protected static boolean checkIfValid(AnimatableMeleeGoal goal, AnimatableMonsterEntity attacker, LivingEntity target) {
		if (target == null) return false;
		if (target.isAlive() && !target.isSpectator()) {
			if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) {
				attacker.setAttacking(false);
				return false;
			}
//			double distance = goal.entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
//			if (distance <= AnimatableGoal.getAttackReachSq(attacker, target)) return true;
		}
//		attacker.setAttacking(false);
		return true;
	}

	//TODO fix looping and anim sync
	@Override
	public boolean canUse() {
	//	if (Math.random() <= 0.1) return false;
		LivingEntity target = this.entity.getTarget();
		if (target == null) return false;
		double distance = this.entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
		return AnimatableMeleeGoal.checkIfValid(this, entity, target) && distance <= AnimatableGoal.getAttackReachSq(this.entity, target) - 0.2D;
	}

	@Override
	public boolean canContinueToUse() {
	//	if (Math.random() <= 0.1) return true;
		LivingEntity target = this.entity.getTarget();
		if (target == null) return false;
		double distance = this.entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
		return (AnimatableMeleeGoal.checkIfValid(this, entity, this.entity.getTarget())) && this.attackEndPredicate.apply(this.animationProgress, this.animationLength) /*&& distance <= AnimatableGoal.getAttackReachSq(this.entity, target)*/;
	}

	@Override
	public void start() {
		this.entity.setAttacking(true);
		this.entity.setAggressive(true);
		this.animationProgress = 0;
	}

	@Override
	public void stop() {
		LivingEntity target = this.entity.getTarget();
		if (!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(target)) this.entity.setTarget(null);
		this.entity.setAttacking(false);
		this.entity.setAggressive(false);
		this.hasHit = false;
		this.animationProgress = 0;
	}

	public void animateAttack(LivingEntity target) {

	}

	@Override
	public void tick() {
		this.baseTick();
		LivingEntity target = this.entity.getTarget();
		if (target != null) {
			 ChaosAwakens.debug("GOAL", this.animationProgress + "" + this.animationLength + "" + this.tickDelta + "" + this.animationProgress/this.animationLength);
			
			 BrainUtil.lookAtEntity(this.entity, target);
			 
			if (this.attackPredicate.apply(this.animationProgress, this.animationLength) && !this.hasHit) {
				if (this.entity.distanceTo(target) >= AnimatableGoal.getAttackReachSq(this.entity, target)) {
					this.entity.getNavigation().moveTo(this.entity.getNavigation().getPath(), 1);
				}
				this.entity.swing(Hand.MAIN_HAND);
				this.entity.doHurtTarget(target);
				this.hasHit = true;
			}

			if (this.animationProgress > this.animationLength) {
				this.animationProgress = 0;
				this.hasHit = false;
			}
		}
	}
}
