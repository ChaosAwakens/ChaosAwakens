package io.github.chaosawakens.common.entity.ai;

import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.util.EntityUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

public abstract class AnimatableMovableGoal extends AnimatableGoal {

	@Override
	abstract public boolean canUse();

	protected boolean isExecutable(AnimatableMovableGoal goal, MobEntity attacker, LivingEntity target) {
		if (target == null) return false;
		if (target.isAlive() && !target.isSpectator()) {
			if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) return false;

			if (attacker instanceof AnimatableMonsterEntity) {
				return attacker.getSensing().canSee(target) && !((AnimatableMonsterEntity) attacker).isAttacking() && !((AnimatableMonsterEntity) attacker).isOnAttackCooldown() && attacker.distanceTo(target) > ((AnimatableMonsterEntity) attacker).getMeleeAttackReach();
			}
			else return attacker.getSensing().canSee(target);
		}
		return false;
	}
	
	protected boolean isExecutable(AnimatableMovableGoal goal, AnimatableAnimalEntity attacker, LivingEntity target) {
		if (target == null) return false;
		if (target.isAlive() && !target.isSpectator()) {
			if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) return false;
			double distance = goal.entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
			return attacker.getSensing().canSee(target) && distance >= EntityUtil.getMeleeAttackReachSqr(entity, entity.getTarget());
		}
		return false;
	}
}
