package io.github.chaosawakens.common.entity.ai;

import java.util.EnumSet;

import io.github.chaosawakens.ChaosAwakens;
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
	
	private final double animationLength;
	
	/**
	 * 
	 * @param entity Attacking entity
	 * @param animationLength
	 */
	public AnimatableMeleeGoal(AnimatableMonsterEntity entity, double animationLength) {
		this.entity = entity;
		this.animationLength = animationLength;
		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}
	
	@Override
	public boolean shouldExecute() {
		this.baseTick();
		return AnimatableMeleeGoal.checkIfValid(this, entity, this.entity.getAttackTarget());
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		this.baseTick();
		return AnimatableMeleeGoal.checkIfValid(this, entity, this.entity.getAttackTarget());
	}
	

	@Override
	public void startExecuting() {
		this.entity.setHitting(true);
		this.entity.setAggroed(true);
		this.animationProgress = 0;
	}
	
	@Override
	public void resetTask() {
		LivingEntity target = this.entity.getAttackTarget();
		if (!EntityPredicates.CAN_AI_TARGET.test(target)) {
			this.entity.setAttackTarget(null);
		}
		this.entity.setHitting(false);
		this.entity.setAggroed(false);
		this.animationProgress = 0;
	}
	
	@Override
	public void tick() {
		this.baseTick();
		LivingEntity target = this.entity.getAttackTarget();
		if(target != null) {
			this.entity.faceEntity(target, 30.0F, 30.0F);
			ChaosAwakens.debug("GOAL", this.animationProgress +" "+this.animationProgress/70000+" "+(this.animationProgress/50000 >= this.animationLength));
			if(this.animationProgress/70000 >= this.animationLength) {
				this.animationProgress = 0;
				this.entity.swingArm(Hand.MAIN_HAND);
				this.entity.attackEntityAsMob(target);
			}
		}
	}
	
	private static boolean checkIfValid(AnimatableMeleeGoal goal, AnimatableMonsterEntity attacker, LivingEntity target) {
		if(target == null)return false;
		if(target.isAlive() && !target.isSpectator()) {
			if(target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) {
				attacker.setHitting(false);
				return false;
			}
			double distance = goal.entity.getDistanceSq(target.getPosX(), target.getPosY(), target.getPosZ());
			if(attacker.getEntitySenses().canSee(target) && distance <= AnimatableGoal.getAttackReachSq(attacker, target))return true;
		}
		attacker.setHitting(false);
		return false;
	}
}