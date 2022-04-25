package io.github.chaosawakens.common.entity.ai;

import io.github.chaosawakens.common.entity.AnimatableMonsterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;

import java.util.EnumSet;

public class AnimatableRamGoal extends AnimatableMovableGoal {
	private final double speedMultiplier;
	private final int checkRate;

	public AnimatableRamGoal(AnimatableMonsterEntity entity, double speedMultiplier, int checkRate) {
		this.entity = entity;
		this.speedMultiplier = speedMultiplier;
		this.checkRate = checkRate;

		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		if (RANDOM.nextInt(this.checkRate) == 0)
			return false;

		return this.isExecutable(this, this.entity, this.entity.getTarget());
	}

	@Override
	public boolean canContinueToUse() {
		if (RANDOM.nextInt(this.checkRate) == 0)
			return true;

		return this.isExecutable(this, this.entity, this.entity.getTarget());
	}

	@Override
	public void start() {
		this.entity.setAggressive(true);
		this.entity.setMoving(true);
		this.entity.getNavigation().moveTo(this.path, this.speedMultiplier);
	}

	@Override
	public void stop() {
		LivingEntity target = this.entity.getTarget();
		if (!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(target))
			this.entity.setTarget(null);
		this.entity.setAggressive(false);
		this.entity.setMoving(false);
		this.entity.getNavigation().stop();
	}

	@Override
	public void tick() {
		LivingEntity target = this.entity.getTarget();
		if (target == null)
			return;

		this.entity.lookAt(target, 30, 30);
	}

	@Override
	protected boolean isExecutable(AnimatableMovableGoal goal, AnimatableMonsterEntity attacker, LivingEntity target) {
		if (target == null)
			return false;
		if (target.isAlive() && !target.isSpectator()) {
			if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative())
				return false;

			double distance = goal.entity.distanceToSqr(target.getX(), target.getY(), target.getZ());

			return attacker.getSensing().canSee(target)
					&& distance >= AnimatableGoal.getAttackReachSq(attacker, target);
		}
		return false;
	}
}
