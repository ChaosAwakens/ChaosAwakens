package io.github.chaosawakens.common.entity.ai;

import java.util.EnumSet;
import java.util.function.BiFunction;

import io.github.chaosawakens.common.entity.AnimatableMonsterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;

/**
 * Melee attack a given entity
 * @author invalid2
 */
public class AnimatableMeleeGoal extends AnimatableGoal {
	
	private boolean hasHit;
	private final double animationLength;
	private final BiFunction<Double, Double, Boolean> attackPredicate;
	/**
	 * 
	 * @param entity Attacking entity
	 * @param animationLength
	 */
	public AnimatableMeleeGoal(AnimatableMonsterEntity entity, double animationLength, double attackBegin, double attackEnd) {
		this.entity = entity;
		this.animationLength = animationLength;
		this.attackPredicate = (progress, length) -> attackBegin < progress/length && progress/length < attackEnd;
		this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
	}
	
	@Override
	public boolean shouldExecute() {
		if(Math.random() <= 0.1)return false;
		
		return AnimatableMeleeGoal.checkIfValid(this, entity, this.entity.getAttackTarget());
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		if(Math.random() <= 0.1)return true;
		
		return AnimatableMeleeGoal.checkIfValid(this, entity, this.entity.getAttackTarget());
	}
	

	@Override
	public void startExecuting() {
		this.entity.setAttacking(true);
		this.entity.setAggroed(true);
		this.animationProgress = 0;
	}
	
	@Override
	public void resetTask() {
		LivingEntity target = this.entity.getAttackTarget();
		if (!EntityPredicates.CAN_AI_TARGET.test(target)) {
			this.entity.setAttackTarget(null);
		}
		this.entity.setAttacking(false);
		this.entity.setAggroed(false);
		this.hasHit = false;
		this.animationProgress = 0;
	}
	
	@Override
	public void tick() {
		this.baseTick();
		LivingEntity target = this.entity.getAttackTarget();
		if(target != null) {
			this.entity.faceEntity(target, 30.0F, 30.0F);
			if(this.attackPredicate.apply(this.animationProgress, this.animationLength) && !this.hasHit) {
				this.entity.swingArm(Hand.MAIN_HAND);
				this.entity.attackEntityAsMob(target);
				this.hasHit = true;
			}
			
			if(this.animationProgress > this.animationLength) {
				this.animationProgress = 0;
				this.hasHit = false;
			}
		}
	}
	
	private static boolean checkIfValid(AnimatableMeleeGoal goal, AnimatableMonsterEntity attacker, LivingEntity target) {
		if(target == null)return false;
		if(target.isAlive() && !target.isSpectator()) {
			if(target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) {
				attacker.setAttacking(false);
				return false;
			}
			double distance = goal.entity.getDistanceSq(target.getPosX(), target.getPosY(), target.getPosZ());
			if(distance <= AnimatableGoal.getAttackReachSq(attacker, target))return true;
		}
		attacker.setAttacking(false);
		return false;
	}
}