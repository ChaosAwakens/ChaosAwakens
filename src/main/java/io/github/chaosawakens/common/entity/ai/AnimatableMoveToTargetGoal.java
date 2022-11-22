package io.github.chaosawakens.common.entity.ai;

import java.util.EnumSet;

import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.vector.Vector3d;

public class AnimatableMoveToTargetGoal extends AnimatableMovableGoal {
	protected final double speedMultiplier;
	@SuppressWarnings("unused")
	private final int checkRate;
	protected int pathCheckRate;

	/**
	 * Move an AnimatableMonsterEntity to a target entity
	 *
	 * @param entity          AnimatableMonsterEntity instance
	 * @param speedMultiplier Entity will move by base speed * this
	 * @param checkRate       Check rate with formula:
	 *                        {@code if(RANDOM.nextInt(rate) == 0)}, so bigger =
	 *                        less often
	 */
	public AnimatableMoveToTargetGoal(AnimatableMonsterEntity entity, double speedMultiplier, int checkRate) {
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
		return this.isExecutable(this, this.entity, this.entity.getTarget()) && this.entity.isWithinRestriction(this.entity.getTarget().blockPosition()) && !entity.getAttacking();
	}
	
	@Override
	public void start() {
		pathCheckRate = 10;
		this.entity.setAggressive(true);
		this.entity.setMoving(true);
		this.entity.lookAt(this.entity.getTarget(), 100, 100);
		this.entity.getLookControl().setLookAt(this.entity.getTarget(), 30F, 30F);
		this.entity.getNavigation().moveTo(this.path, this.speedMultiplier);
	}
	
	@Override
	public void stop() {
		pathCheckRate = 1;
		LivingEntity target = this.entity.getTarget();
		if (!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(target)) this.entity.setTarget(null);
		this.entity.setAggressive(false);
		this.entity.setMoving(false);
		this.entity.getNavigation().stop();
	}

	@SuppressWarnings("unused")
	@Override
	public void tick() {
		LivingEntity target = this.entity.getTarget();
		if (target == null || !EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(target) || !target.isAlive()) return;
//		if (this.entity.level.getGameTime() < 1 || this.entity.tickCount < 1) return;
		if (pathCheckRate > 0) pathCheckRate--;
//		ChaosAwakens.LOGGER.debug(pathCheckRate);
		//FIX Crash debugging the path (when it becomes null for any reason)
//		if (this.entity.getNavigation().getPath() != null) {
//			ChaosAwakens.LOGGER.debug(this.entity.getNavigation().getPath().toString());
//		}
		
//		ChaosAwakens.LOGGER.debug(this.entity.level.getGameTime());
		
		this.entity.lookAt(target, 100, 100);
		this.entity.getLookControl().setLookAt(target, 30F, 30F);
		
		if (pathCheckRate <= 0 && this.entity.getSensing().canSee(target) && this.entity.distanceToSqr(target) >= AnimatableGoal.getAttackReachSq(this.entity, target) - 1) {
			Vector3d targetPosition = target.position();
			pathCheckRate = IUtilityHelper.randomBetween(4, 11);
			this.entity.getNavigation().moveTo(path, this.speedMultiplier);
			this.entity.lookAt(target, 100, 100);
			this.entity.getLookControl().setLookAt(target, 30F, 30F);
			
	//		if (path == null) {
	//			this.path = this.entity.getNavigation().createPath(target, 0);				
	//		}
			
			//Fix entities mindlessly spinning due to next node index being out of bounds
			//It never caused any exceptions, though? I dunno how that happened --Meme Man
			if (this.entity.getNavigation().getPath() != null) {
				if (this.entity.getNavigation().getPath().getNextNodeIndex() >= this.entity.getNavigation().getPath().getNodeCount() - 1) {
					this.entity.getNavigation().stop();
					this.path = this.entity.getNavigation().createPath(target, 0);
					this.entity.getNavigation().moveTo(path, this.speedMultiplier);
				}
			}
			
			if (this.entity.distanceToSqr(target.getX(), target.getY(), target.getZ()) > 256) {
				pathCheckRate += 5;
				if (this.entity.distanceToSqr(target.getX(), target.getY(), target.getZ()) > 1024) {
					pathCheckRate += 10;
				}
			}
			
			if (!this.entity.getNavigation().moveTo(target, this.speedMultiplier)) pathCheckRate += 15;
		}
	}
}
