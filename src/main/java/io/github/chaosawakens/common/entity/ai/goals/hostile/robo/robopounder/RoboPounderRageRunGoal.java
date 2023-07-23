package io.github.chaosawakens.common.entity.ai.goals.hostile.robo.robopounder;

import java.util.List;
import java.util.function.Supplier;

import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.common.entity.hostile.robo.RoboPounderEntity;
import io.github.chaosawakens.common.util.BlockPosUtil;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.common.util.MathUtil;
import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class RoboPounderRageRunGoal extends Goal {
	private final RoboPounderEntity owner;
	private final Supplier<SingletonAnimationBuilder> rageBeginAnim;
	private final Supplier<SingletonAnimationBuilder> rageRunAnim;
	private final Supplier<SingletonAnimationBuilder> rageCooldownAnim;
	private final Supplier<SingletonAnimationBuilder> rageRestartAnim;
	private final byte rageRunAttackId;
	private final int presetMaxCooldown;
	private int curCooldown;
	private final int probability;
	private BlockPos targetRageRunPos;
	private boolean isPathingRageRun;
	
	public RoboPounderRageRunGoal(RoboPounderEntity owner, Supplier<SingletonAnimationBuilder> rageBeginAnim, Supplier<SingletonAnimationBuilder> rageRunAnim, Supplier<SingletonAnimationBuilder> rageCooldownAnim, Supplier<SingletonAnimationBuilder> rageRestartAnim, byte rageRunAttackId, int presetMaxCooldown, int probability) {
		this.owner = owner;
		this.rageBeginAnim = rageBeginAnim;
		this.rageRunAnim = rageRunAnim;
		this.rageCooldownAnim = rageCooldownAnim;
		this.rageRestartAnim = rageRestartAnim;
		this.rageRunAttackId = rageRunAttackId;
		this.presetMaxCooldown = presetMaxCooldown;
		this.probability = probability;
	}
	
	public RoboPounderRageRunGoal(RoboPounderEntity owner, Supplier<SingletonAnimationBuilder> rageBeginAnim, Supplier<SingletonAnimationBuilder> rageRunAnim, Supplier<SingletonAnimationBuilder> rageCooldownAnim, Supplier<SingletonAnimationBuilder> rageRestartAnim, byte rageRunAttackId, int presetMaxCooldown) {
		this(owner, rageBeginAnim, rageRunAnim, rageCooldownAnim, rageRestartAnim, rageRunAttackId, presetMaxCooldown, 1);
	}
	
	public RoboPounderRageRunGoal(RoboPounderEntity owner, Supplier<SingletonAnimationBuilder> rageBeginAnim, Supplier<SingletonAnimationBuilder> rageRunAnim, Supplier<SingletonAnimationBuilder> rageCooldownAnim, Supplier<SingletonAnimationBuilder> rageRestartAnim, byte rageRunAttackId, Integer probability) {
		this(owner, rageBeginAnim, rageRunAnim, rageCooldownAnim, rageRestartAnim, rageRunAttackId, 400, probability);
	}
	
	public RoboPounderRageRunGoal(RoboPounderEntity owner, Supplier<SingletonAnimationBuilder> rageBeginAnim, Supplier<SingletonAnimationBuilder> rageRunAnim, Supplier<SingletonAnimationBuilder> rageCooldownAnim, Supplier<SingletonAnimationBuilder> rageRestartAnim, byte rageRunAttackId) {
		this(owner, rageBeginAnim, rageRunAnim, rageCooldownAnim, rageRestartAnim, rageRunAttackId, 400, 1);
	}

	@Override
	public boolean canUse() {
		if (curCooldown > 0) curCooldown--;
		
		return ObjectUtil.performNullityChecks(false, owner, owner.getTarget()) && !owner.isOnAttackCooldown() && curCooldown <= 0 && !owner.getTarget().isInvulnerable()
				&& owner.isAlive() && !owner.isAttacking() && owner.getTarget().isAlive()
				&& owner.distanceTo(owner.getTarget()) > owner.getMeleeAttackReach(owner.getTarget()) * 5 && owner.distanceTo(owner.getTarget()) <= owner.getFollowRange()
				&& owner.shouldRageRunBasedOnChance() && owner.getRandom().nextInt(probability) == 0;
	}
	
	@Override
	public boolean canContinueToUse() {
		return ObjectUtil.performNullityChecks(false, owner) && !owner.isDeadOrDying() && !rageRestartAnim.get().hasAnimationFinished();
	}
	
	@Override
	public boolean isInterruptable() {
		return owner.isDeadOrDying();
	}
	
	@Override
	public void start() {
		owner.setAttackID(rageRunAttackId);
		owner.setRageRunning(true);
		owner.getNavigation().stop();
		owner.setRageRunAttributes();
		owner.playAnimation(rageBeginAnim.get(), true);
		
		this.isPathingRageRun = false;
	}
	
	@Override
	public void stop() {
		owner.setAttackID((byte) 0);
		owner.setAttackCooldown(10);
		owner.setRageRunning(false);
		owner.resetAttributes();
		
		this.curCooldown = presetMaxCooldown;
	}
	
	private void createRageRunPath() {
		PathNavigator ownerPathNav = owner.getNavigation();
		Path rageRunPath = null;
		Vector3d relevantLookPos = null;
		
		if (owner.getTarget() != null && owner.getTarget().isAlive() && rageRunAnim.get().isPlaying()) {
			if (!isPathingRageRun) {
				this.targetRageRunPos = BlockPosUtil.findHorizontalPositionBeyond(owner, owner.getTarget().blockPosition(), owner.getRandom().nextInt(10, 20));
				this.isPathingRageRun = true;
			}
			
			if (targetRageRunPos != null) {				
				rageRunPath = ownerPathNav.createPath(targetRageRunPos, 0);
				relevantLookPos = new Vector3d(targetRageRunPos.getX(), targetRageRunPos.getY(), targetRageRunPos.getZ());
			}
		}
		
		if (ObjectUtil.performNullityChecks(false, rageRunPath, relevantLookPos)) {
			ownerPathNav.moveTo(rageRunPath, 1);
			owner.getLookControl().setLookAt(relevantLookPos);
			
			if (!rageRunPath.isDone()) rageRunPath.setNextNodeIndex(rageRunPath.getNodeCount() - 1);
		}
		
		if ((ownerPathNav.isDone() || (relevantLookPos != null && owner.distanceToSqr(relevantLookPos) <= 15.0D)) && isPathingRageRun) this.isPathingRageRun = false;
	}
	
	private void affectTargets() {
		LivingEntity target = owner.getTarget();
		
		if (target != null) {
			double reach = owner.getMeleeAttackReach(target);
			List<LivingEntity> potentialAffectedTargets = EntityUtil.getAllEntitiesAround(owner, reach, reach, reach, reach);
			
			for (LivingEntity potentialAffectedTarget : potentialAffectedTargets) {
				if (owner.isAlliedTo(potentialAffectedTarget) || owner.getClass() == potentialAffectedTarget.getClass()) continue;
				
				double targetAngle = MathUtil.getRelativeAngleBetweenEntities(owner, potentialAffectedTarget);
				double attackAngle = owner.yBodyRot % 360;
				
				if (targetAngle < 0) targetAngle += 360;
				if (attackAngle < 0) attackAngle += 360;
				
				double relativeHitAngle = targetAngle - attackAngle;
				float hitDistanceSqr = (float) (Math.sqrt((target.getZ() - owner.getZ()) * (target.getZ() - owner.getZ()) + (target.getX() - owner.getX()) * (target.getX() - owner.getX())) - owner.getBbWidth() / 2F);
				
				if (hitDistanceSqr <= reach && MathUtil.isWithinAngleRestriction(relativeHitAngle, 300.0D)) owner.doHurtTarget(potentialAffectedTarget);
			}
		}
	}
	
	private boolean shouldExitCooldown() {
		return rageCooldownAnim.get().isPlaying() ? rageCooldownAnim.get().getWrappedAnimProgress() >= owner.getRandom().nextDouble(rageCooldownAnim.get().getWrappedAnimLength() / 2, rageCooldownAnim.get().getWrappedAnimLength()) : false;
	}
	
	@Override
	public void tick() {
		LivingEntity target = owner.getTarget();
		boolean hasCharged = false;
		
		if (rageBeginAnim.get().isPlaying() || rageRestartAnim.get().isPlaying() || rageCooldownAnim.get().isPlaying()) {
			owner.getNavigation().stop();
			EntityUtil.freezeEntityRotation(owner);
		}
		
		if (rageBeginAnim.get().hasAnimationFinished()) {
			if (target != null) {
				owner.playAnimation(rageRunAnim.get(), false);
				createRageRunPath();
			}
		}
		
		if (rageRunAnim.get().isPlaying()) {
			// TODO Rage Crash
			if (target == null || (target != null && target.isDeadOrDying()) || owner.getRageRunDuration() <= 0) owner.playAnimation(rageCooldownAnim.get(), false);
			createRageRunPath();
			affectTargets();
		}
		
		if (rageCooldownAnim.get().isPlaying()) {
			if (!hasCharged) {
				EntityUtil.chargeTowards(owner, BlockPosUtil.findHorizontalPositionBeyond(owner, targetRageRunPos, owner.getRandom().nextInt(4)), 5, 4, 0.02);
				hasCharged = true;
			}
			
			if (rageCooldownAnim.get().getWrappedAnimProgress() >= owner.getRandom().nextInt(30, 35)) {
				owner.setDeltaMovement(0, owner.getDeltaMovement().y, 0);
				EntityUtil.freezeEntityRotation(owner);
			}
		}
		
		if (shouldExitCooldown()) owner.playAnimation(rageRestartAnim.get(), false);
	}
}
