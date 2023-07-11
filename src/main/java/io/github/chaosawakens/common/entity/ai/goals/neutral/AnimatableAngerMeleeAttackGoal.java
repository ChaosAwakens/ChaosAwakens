package io.github.chaosawakens.common.entity.ai.goals.neutral;

import java.util.List;
import java.util.function.Predicate;

import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.common.entity.base.AnimatableAngerableAnimalEntity;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.common.util.MathUtil;
import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.command.arguments.EntityAnchorArgument.Type;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class AnimatableAngerMeleeAttackGoal extends Goal {
	private final AnimatableAngerableAnimalEntity owner;
	private SingletonAnimationBuilder meleeAnim;
	private final byte attackId;
	private final double actionPointTickStart;
	private final double actionPointTickEnd;
	private final double angleRange;
	private final int probability;
	private Predicate<AnimatableAngerableAnimalEntity> extraActivationConditions;

	public AnimatableAngerMeleeAttackGoal(AnimatableAngerableAnimalEntity owner, SingletonAnimationBuilder meleeAnim,
			byte attackId, double actionPointTickStart, double actionPointTickEnd, double angleRange, int probability) {
		this.owner = owner;
		this.meleeAnim = meleeAnim;
		this.attackId = attackId;
		this.actionPointTickStart = actionPointTickStart;
		this.actionPointTickEnd = actionPointTickEnd;
		this.angleRange = angleRange;
		this.probability = probability;
	}

	public AnimatableAngerMeleeAttackGoal(AnimatableAngerableAnimalEntity owner, SingletonAnimationBuilder meleeAnim,
			byte attackId, double angleRange, double actionPointTickStart, double actionPointTickEnd) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, angleRange, 1);
	}

	public AnimatableAngerMeleeAttackGoal(AnimatableAngerableAnimalEntity owner, SingletonAnimationBuilder meleeAnim,
			byte attackId, double actionPointTickStart, double actionPointTickEnd, int probability) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, 50, probability);
	}

	public AnimatableAngerMeleeAttackGoal(AnimatableAngerableAnimalEntity owner, SingletonAnimationBuilder meleeAnim,
			byte attackId, double actionPointTickStart, double actionPointTickEnd) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, 50, 1);
	}

	public AnimatableAngerMeleeAttackGoal(AnimatableAngerableAnimalEntity owner, SingletonAnimationBuilder meleeAnim,
			byte attackId, double actionPointTickStart, double actionPointTickEnd, double angleRange,
			Predicate<AnimatableAngerableAnimalEntity> activationPredicate) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, angleRange, 1);
		this.extraActivationConditions = activationPredicate;
	}

	public AnimatableAngerMeleeAttackGoal(AnimatableAngerableAnimalEntity owner, SingletonAnimationBuilder meleeAnim,
			byte attackId, double actionPointTickStart, double actionPointTickEnd,
			Predicate<AnimatableAngerableAnimalEntity> activationPredicate) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, 50, 1);
		this.extraActivationConditions = activationPredicate;
	}

	@Override
	public boolean canUse() {
		return ObjectUtil.performNullityChecks(false, owner, owner.getTarget(), meleeAnim, attackId) && owner.isAlive()
				&& !owner.isAttacking() && owner.isAngry() && owner.getTarget().isAlive()
				&& MathUtil.getDistanceBetween(owner, owner.getTarget()) <= owner.getMeleeAttackReachSqr(owner.getTarget())
				&& actionPointTickStart <= meleeAnim.getWrappedController().getAnimationLength()
				&& actionPointTickEnd <= meleeAnim.getWrappedController().getAnimationLength()
				&& (extraActivationConditions != null ? extraActivationConditions.test(owner)
						: owner.getRandom().nextInt(probability) == 0);
	}

	@Override
	public boolean canContinueToUse() {
		if (!owner.isAngry()) owner.stopAnimation(meleeAnim);
		return ObjectUtil.performNullityChecks(false, owner, owner.getTarget(), meleeAnim, attackId) && owner.isAlive()
				&& !meleeAnim.hasAnimationFinished() && owner.isAngry() && owner.getTarget().isAlive();
	}

	@Override
	public void start() {
		owner.playAnimation(meleeAnim, false);
		owner.setAttackID(attackId);
		owner.getNavigation().stop();
	}

	@Override
	public void stop() {
		owner.setAttackID((byte) 0);
	}

	@Override
	public void tick() {
		LivingEntity target = owner.getTarget();
		double reach = owner.getMeleeAttackReachSqr(target);
		List<LivingEntity> potentialAffectedTargets = EntityUtil.getAllEntitiesAround(owner, reach, reach, reach, reach);

		if (meleeAnim.getWrappedAnimProgress() < actionPointTickStart)
			owner.lookAt(Type.EYES, target.position());
		for (LivingEntity potentialAffectedTarget : potentialAffectedTargets) {			
			float targetAngle = (float) MathUtil.getRelativeAngleBetweenEntities(owner, potentialAffectedTarget);
			float attackAngle = owner.yBodyRot % 360;

			if (targetAngle < 0) targetAngle += 360;
			if (attackAngle < 0) attackAngle += 360;

			float relativeHitAngle = targetAngle - attackAngle;

			if (MathUtil.isBetween(meleeAnim.getWrappedAnimProgress(), actionPointTickStart, actionPointTickEnd)) {
				if (MathUtil.getDistanceBetween(owner, potentialAffectedTarget) <= reach
						&& MathUtil.isWithinAngleRestriction(relativeHitAngle, angleRange)) {
					owner.doHurtTarget(potentialAffectedTarget);
				}
			}
		}
		if (meleeAnim.getWrappedAnimProgress() >= actionPointTickStart)
			EntityUtil.freezeEntityRotation(owner);
	}
}
