package io.github.chaosawakens.common.entity.ai.goals.hostile;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.common.util.MathUtil;
import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class AnimatableMeleeGoal extends Goal {
	private final AnimatableMonsterEntity owner;
	private final Supplier<SingletonAnimationBuilder> meleeAnim;
	private final byte attackId;
	private final double actionPointTickStart;
	private final double actionPointTickEnd;
	private final double angleRange;
	private final int probability;
	private Predicate<AnimatableMonsterEntity> extraActivationConditions;
	private int presetCooldown = 10;

	public AnimatableMeleeGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> meleeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double angleRange, int probability) {
		this.owner = owner;
		this.meleeAnim = meleeAnim;
		this.attackId = attackId;
		this.actionPointTickStart = actionPointTickStart;
		this.actionPointTickEnd = actionPointTickEnd;
		this.angleRange = angleRange;
		this.probability = probability;
		setFlags(EnumSet.of(Flag.TARGET, Flag.LOOK));
	}

	public AnimatableMeleeGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> meleeAnim, byte attackId, double angleRange, double actionPointTickStart, double actionPointTickEnd) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, angleRange, 1);
	}

	public AnimatableMeleeGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> meleeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, int probability) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, 50, probability);
	}

	public AnimatableMeleeGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> meleeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, 50, 1);
	}

	public AnimatableMeleeGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> meleeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double angleRange, Predicate<AnimatableMonsterEntity> activationPredicate) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, angleRange, 1);
		this.extraActivationConditions = activationPredicate;
	}

	public AnimatableMeleeGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> meleeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, Predicate<AnimatableMonsterEntity> activationPredicate) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, 50, 1);
		this.extraActivationConditions = activationPredicate;
	}

	@Override
	public boolean canUse() {
		if (presetCooldown > 0) {
			presetCooldown--;
			return false;
		}
		return ObjectUtil.performNullityChecks(false, owner, owner.getTarget(), meleeAnim.get(), attackId) && !owner.getTarget().isInvulnerable() && owner.isAlive() && !owner.isAttacking() && owner.getTarget().isAlive()
				&& owner.distanceToSqr(owner.getTarget()) <= owner.getMeleeAttackReachSqr(owner.getTarget())
				&& (extraActivationConditions != null ? extraActivationConditions.test(owner) : owner.getRandom().nextInt(probability) == 0);
	}

	@Override
	public boolean canContinueToUse() {
		return ObjectUtil.performNullityChecks(false, owner, meleeAnim.get(), attackId) && owner.isAlive() && !meleeAnim.get().hasAnimationFinished();
	}

	@Override
	public void start() {
		owner.setAttackID(attackId);
		owner.getNavigation().stop();
		owner.playAnimation(meleeAnim.get(), true);
	}

	@Override
	public void stop() {
		owner.setAttackID((byte) 0);
		presetCooldown = 30;
	}
	
	@Override
	public boolean isInterruptable() {
		return owner.isDeadOrDying();
	}

	@Override
	public void tick() {
		owner.getNavigation().stop();
		owner.setDeltaMovement(0, owner.getDeltaMovement().y(), 0);
		LivingEntity target = owner.getTarget();
		
		if (!ObjectUtil.performNullityChecks(false, target)) return;
		
		double reach = EntityUtil.getMeleeAttackReachSqr(owner, target);
		List<LivingEntity> potentialAffectedTargets = EntityUtil.getAllEntitiesAround(owner, reach, reach, reach, reach);

		if (meleeAnim.get().getWrappedAnimProgress() < actionPointTickStart) owner.lookAt(target, 30F, 30F);;
		for (LivingEntity potentialAffectedTarget : potentialAffectedTargets) {			
			double targetAngle = MathUtil.getAngleBetweenEntities(owner, potentialAffectedTarget);
			double attackAngle = owner.yBodyRot + 180;
			
			if (targetAngle < 0) targetAngle += 360;
			if (attackAngle < 0) attackAngle += 360;
			
			double relativeHitAngle = targetAngle - attackAngle;
			float hitDistanceSqr = (float) (Math.sqrt((target.getZ() - owner.getZ()) * (target.getZ() - owner.getZ()) + (target.getX() - owner.getX()) * (target.getX() - owner.getX())) - owner.getBbWidth() / 2F);
			
			if (MathUtil.isBetween(meleeAnim.get().getWrappedAnimProgress(), actionPointTickStart, actionPointTickEnd)) {
				if (hitDistanceSqr <= reach && MathUtil.isWithinAngleRestriction(relativeHitAngle, angleRange)) owner.doHurtTarget(potentialAffectedTarget);
			}
		}
		if (meleeAnim.get().getWrappedAnimProgress() >= actionPointTickStart) EntityUtil.freezeEntityRotation(owner);
	}
}
