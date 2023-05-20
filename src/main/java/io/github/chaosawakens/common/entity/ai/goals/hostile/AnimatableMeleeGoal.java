package io.github.chaosawakens.common.entity.ai.goals.hostile;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.common.util.MathUtil;
import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.command.arguments.EntityAnchorArgument.Type;
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

	public AnimatableMeleeGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> meleeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double angleRange, int probability) {
		this.owner = owner;
		this.meleeAnim = meleeAnim;
		this.attackId = attackId;
		this.actionPointTickStart = actionPointTickStart;
		this.actionPointTickEnd = actionPointTickEnd;
		this.angleRange = angleRange;
		this.probability = probability;
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
		return ObjectUtil.performNullityChecks(false, owner, owner.getTarget(), meleeAnim.get(), attackId) && owner.getTarget() != null && owner.isAlive() && !owner.isAttacking() && owner.getTarget().isAlive() && owner.distanceToSqr(owner.getTarget()) <= owner.getMeleeAttackReachSqr(owner.getTarget()) + 1D &&/* actionPointTickStart <= meleeAnim.getLengthTicks() && actionPointTickEnd <= meleeAnim.getLengthTicks() &&*/ (extraActivationConditions != null ? extraActivationConditions.test(owner) : owner.getRandom().nextInt(probability) == 0);
	}

	@Override
	public boolean canContinueToUse() {
		return ObjectUtil.performNullityChecks(false, owner, owner.getTarget(), meleeAnim.get(), attackId) && owner.isAlive() && meleeAnim.get() != null && !meleeAnim.get().hasAnimationFinished(owner.getId()) && owner.getTarget().isAlive();
	}

	@Override
	public void start() {
		owner.playAnimation(meleeAnim.get());
		owner.setAttackID(attackId);
		owner.getNavigation().stop();
	}

	@Override
	public void stop() {
		owner.setAttackID((byte) 0);
	}

	@Override
	public void tick() {
		owner.getNavigation().stop();
		LivingEntity target = owner.getTarget();
		if(ObjectUtil.performNullityChecks(false, target))return;
		double reach = owner.getMeleeAttackReachSqr(target);
		List<LivingEntity> potentialAffectedTargets = EntityUtil.getAllEntitiesAround(owner, reach, reach, reach, reach);

		if (meleeAnim.get().getProgressTicks(owner.getId()) < actionPointTickStart) owner.lookAt(Type.EYES, target.position());
		for (LivingEntity potentialAffectedTarget : potentialAffectedTargets) {			
			float targetAngle = (float) MathUtil.getAngleBetweenEntities(owner, potentialAffectedTarget);
			float attackAngle = owner.yBodyRot % 360;

			if (targetAngle < 0) targetAngle += 360;
			if (attackAngle < 0) attackAngle += 360;

			float relativeHitAngle = targetAngle - attackAngle;

			if (MathUtil.isBetween(meleeAnim.get().getProgressTicks(owner.getId()), actionPointTickStart, actionPointTickEnd)) {
				if (owner.distanceToSqr(owner.getTarget()) <= reach && MathUtil.isWithinAngleRestriction(relativeHitAngle, angleRange)) {
					owner.doHurtTarget(potentialAffectedTarget);
				}
			}
		}
		if (meleeAnim.get().getProgressTicks(owner.getId()) >= actionPointTickStart) EntityUtil.freezeEntityRotation(owner);
	}

}
