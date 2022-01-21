package io.github.chaosawakens.common.entity.ai;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.entity.AnimatableMonsterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public abstract class AnimatableMultiAttackGoal extends AnimatableGoal{
	protected AnimatableMonsterEntity attacker;
	protected LivingEntity target;
	protected AnimatableMeleeGoal attack1;
	protected Goal attack2;
	protected Goal attack3;
	
	/**
	 * 3 attacks, nullable if needed, attack 1 being the melee attack, attack 2 is a bit more of a ranged attack (if the range between the 
	 * target and the entity is greater than the melee range of the entity AND the target is far away while the creature cannot move), and 
	 * attack 3 being just a quick attack. 
	 * @param attack1 melee attack
	 * @param attack2 farther range attack
	 * @param attack3 extra attack for the entity, van be null
	 */
	public AnimatableMultiAttackGoal(AnimatableMonsterEntity attacker, @Nullable AnimatableMeleeGoal attack1, @Nullable Goal attack2, @Nullable Goal attack3) {
		this.attacker = attacker;
		this.target = attacker.getTarget();
		this.attack1 = attack1;
		this.attack2 = attack2;
		this.attack3 = attack3;
	}
	
	public void setAttacksUp() {
		boolean canUseMeleeAttack = attack1.canUse();
		boolean canUseRangeAttack = attack2.canUse();
		boolean canUseExtraAttack = attack3.canUse();
		boolean canContinueToUseMeleeAttack = attack1.canContinueToUse();
		boolean canContinueToUseRangeAttack = attack2.canContinueToUse();
		boolean canContinueToUseExtraAttack = attack2.canContinueToUse();
		
	//	List<Goal> attacks = new ArrayList(); 
		
	//	attacks.add(attack1);
	//	attacks.add(attack2);
	//	attacks.add(attack3);
		if (target == null) {
			return;
		}
		if (attacker.level == null) {
			return;
		}
		if (target.level == null) {
			return;
		}
		if (canUseMeleeAttack && !canUseRangeAttack && !canUseExtraAttack) {
			if (canContinueToUseRangeAttack || canContinueToUseExtraAttack) {
				attack2.stop();
				attack3.stop();
			}
			if (targetIsNotNull()) {
				attack1.start();
			}
			if (!canContinueToUseMeleeAttack) {
				attack1.stop();
			}
		} else if (!canUseMeleeAttack && canUseRangeAttack && !canUseExtraAttack) {
			if (canContinueToUseMeleeAttack || canContinueToUseExtraAttack) {
				attack1.stop();
				attack3.stop();
			}
			attack2.start();
			if (!canContinueToUseRangeAttack) {
				attack2.stop();
			}
		} else if (!canUseMeleeAttack && !canUseRangeAttack && canUseExtraAttack) {
			if (canContinueToUseMeleeAttack || canContinueToUseRangeAttack) {
				attack1.stop();
				attack2.stop();
			}
			attack3.start();
			if (!canContinueToUseExtraAttack) {
				attack3.stop();
			}
		}
		if (canUseAllAttacksAtOnce()) {
			double distanceToTarget = attacker.distanceToSqr(target.getX(), target.getY(), target.getZ());
			if (distanceToTarget <= getAttackReachSq(attacker, target)) {
				attack1.start();
				if (canContinueToUseMeleeAttack) {
					attack1.tick();
				}
				attack2.stop();
				attack3.stop();
			} else if (distanceToTarget >= getAttackReachSq(attacker, target)) {
				attack2.start();
				if (canContinueToUseRangeAttack) {
					attack2.tick();
				}
				attack1.stop();
				attack3.stop();
			} else if (distanceToTarget <= getAttackReachSq(attacker, target) && !canContinueToUseMeleeAttack || !canUseMeleeAttack) {
				attack3.start();
				if (canContinueToUseExtraAttack) {
					attack3.tick();
				}
				attack1.stop();
				attack2.stop();
			} else if (distanceToTarget >= getAttackReachSq(attacker, target) && !canContinueToUseRangeAttack || !canUseRangeAttack) {
				attack3.start();
				if (canContinueToUseExtraAttack) {
					attack3.tick();
				}
				attack1.stop();
				attack2.stop();
			}
		}
		if (canContinueToUseAllAttacksAtOnce()) {
			double distanceToTarget = attacker.distanceToSqr(target.getX(), target.getY(), target.getZ());
			if (distanceToTarget <= getAttackReachSq(attacker, target)) {
				attack1.start();
				if (canContinueToUseMeleeAttack) {
					attack1.tick();
				}
				attack2.stop();
				attack3.stop();
			} else if (distanceToTarget >= getAttackReachSq(attacker, target)) {
				attack2.start();
				if (canContinueToUseRangeAttack) {
					attack2.tick();
				}
				attack1.stop();
				attack3.stop();
			} else if (distanceToTarget <= getAttackReachSq(attacker, target) && !canContinueToUseMeleeAttack || !canUseMeleeAttack) {
				attack3.start();
				if (canContinueToUseExtraAttack) {
					attack3.tick();
				}
				attack1.stop();
				attack2.stop();
			} else if (distanceToTarget >= getAttackReachSq(attacker, target) && !canContinueToUseRangeAttack || !canUseRangeAttack) {
				attack3.start();
				if (canContinueToUseExtraAttack) {
					attack3.tick();
				}
				attack1.stop();
				attack2.stop();
			}
		}
		if (attack1 == null) {
			
		}
	}
	
	//Taken from the AnimatableGoal class because I was too lazy to call for it from the class
    protected static double getAttackReachSq(AnimatableMonsterEntity attacker, LivingEntity target) {
        return attacker.getBbWidth() * 2F * attacker.getBbWidth() * 2F + target.getBbWidth();
    }
	
	public float attackRange() {
		return attacker.distanceTo(target);
	}
	
	public boolean canUseAllAttacksAtOnce() {
		return attack1.canUse() && attack2.canUse() && attack3.canUse();
	}
	
	public boolean canContinueToUseAllAttacksAtOnce() {
		return attack1.canContinueToUse() && attack2.canContinueToUse() && attack3.canContinueToUse();
	}
	
	public boolean targetIsNotNull() {
		return target != null;
	}
	
	@Override
	public boolean canUse() {
		return attack1 != null && attack2 != null && attack3 != null;
	}
	
	@Override
	public boolean canContinueToUse() {
		return target != null && this.canUse();
	}
	
	@Override
	public void tick() {
		if (target != null) {
			setAttacksUp();
		}
	}
}
