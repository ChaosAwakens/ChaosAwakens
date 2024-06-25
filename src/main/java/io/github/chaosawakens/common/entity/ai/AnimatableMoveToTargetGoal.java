package io.github.chaosawakens.common.entity.ai;

import io.github.chaosawakens.common.util.EntityUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import java.util.EnumSet;

public class AnimatableMoveToTargetGoal extends AnimatableMovableGoal {
	protected final double speedMultiplier;
	@SuppressWarnings("unused")
	private final int checkRate;
	protected int pathCheckRate;
	protected int failedIterations = 0;

	/**
	 * Move an AnimatableMonsterEntity to a target entity
	 *
	 * @param entity          AnimatableMonsterEntity instance
	 * @param speedMultiplier Entity will move by base speed * this
	 * @param checkRate       Check rate with formula:
	 *                        {@code if(RANDOM.nextInt(rate) == 0)}, so bigger =
	 *                        less often
	 */
	public AnimatableMoveToTargetGoal(MobEntity entity, double speedMultiplier, int checkRate) {
		this.entity = entity;
		this.speedMultiplier = speedMultiplier;
		this.checkRate = checkRate;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
	}

	@Override
	public boolean canUse() {
//		if (RANDOM.nextInt(this.checkRate) == 0) return false;
		return this.isExecutable(this, this.entity, this.entity.getTarget());
	}

	@Override
	public boolean canContinueToUse() {
//		if (RANDOM.nextInt(this.checkRate) == 0) return true;
		return this.isExecutable(this, this.entity, this.entity.getTarget()) && this.entity.isWithinRestriction(this.entity.getTarget().blockPosition()) && failedIterations < 20 && entity.distanceToSqr(entity.getTarget()) > EntityUtil.getMeleeAttackReachSqr(entity, entity.getTarget());
	}
	
	@Override
	public void start() {
		pathCheckRate = 1;
		failedIterations = 0;
	//	this.entity.lookAt(this.entity.getTarget(), 100, 100);
	//	this.entity.getLookControl().setLookAt(this.entity.getTarget(), 30F, 30F);

		if (this.entity.getTarget() != null) this.entity.getNavigation().moveTo(this.entity.getTarget(), this.speedMultiplier);
	}
	
	@Override
	public void stop() {
		pathCheckRate = 1;
		LivingEntity target = this.entity.getTarget();
		if (!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(target)) this.entity.setTarget(null);
		this.entity.getNavigation().stop();
	}

	@SuppressWarnings("unused")
	@Override
	public void tick() {
		LivingEntity target = this.entity.getTarget();
		if (target == null || !EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(target) || !target.isAlive()) return;
		if (this.entity.level.getGameTime() < 1 || this.entity.tickCount < 1) return;
		if (pathCheckRate > 0) pathCheckRate--;
		
		this.entity.getLookControl().setLookAt(target, 30F, 30F);

		if ((pathCheckRate <= 0 || this.entity.getNavigation().isDone() || this.entity.getNavigation().isStuck()) && this.entity.getSensing().canSee(target) && this.entity.distanceToSqr(target) >= EntityUtil.getMeleeAttackReachSqr(entity, entity.getTarget())) {
			Vector3d targetPosition = target.position();
			pathCheckRate = MathHelper.nextInt(entity.getRandom(), 4, 11);

			if (!this.entity.getNavigation().moveTo(this.entity.getTarget(), this.speedMultiplier)) pathCheckRate += 15;
		}
	}
}
