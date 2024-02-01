package io.github.chaosawakens.common.entity.ai.goals.hostile;

import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.common.util.MathUtil;
import io.github.chaosawakens.common.util.ObjectUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class AnimatableMeleeGoal extends Goal {
	protected final AnimatableMonsterEntity owner;
	protected final Supplier<? extends IAnimationBuilder> meleeAnim;
	protected final byte attackId;
	protected final double actionPointTickStart;
	protected final double actionPointTickEnd;
	protected final double angleRange;
	protected final int probability;
	@Nullable
	protected Predicate<AnimatableMonsterEntity> extraActivationConditions;
	@Nullable
	protected List<Supplier<? extends IAnimationBuilder>> animationsToPick;
	protected final int presetCooldown;
	protected int curCooldown;
	protected Supplier<? extends IAnimationBuilder> curAnim;
	@Nullable
	protected Supplier<SoundEvent> soundOnStart;
	protected float soundPitch = 1.0F;

	public AnimatableMeleeGoal(AnimatableMonsterEntity owner, Supplier<? extends IAnimationBuilder> meleeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double angleRange, int probability, int presetCooldown) {
		this.owner = owner;
		this.meleeAnim = meleeAnim;
		this.attackId = attackId;
		this.actionPointTickStart = actionPointTickStart;
		this.actionPointTickEnd = actionPointTickEnd;
		this.angleRange = angleRange;
		this.probability = probability;
		this.presetCooldown = presetCooldown;
	}

	public AnimatableMeleeGoal(AnimatableMonsterEntity owner, Supplier<? extends IAnimationBuilder> meleeAnim, byte attackId, double angleRange, double actionPointTickStart, double actionPointTickEnd) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, angleRange, 1, 20);
	}

	public AnimatableMeleeGoal(AnimatableMonsterEntity owner, Supplier<? extends IAnimationBuilder> meleeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, int probability) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, 50, probability, 20);
	}

	public AnimatableMeleeGoal(AnimatableMonsterEntity owner, Supplier<? extends IAnimationBuilder> meleeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, 50, 1, 20);
	}

	public AnimatableMeleeGoal(AnimatableMonsterEntity owner, Supplier<? extends IAnimationBuilder> meleeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double angleRange, Predicate<AnimatableMonsterEntity> activationPredicate) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, angleRange, 1, 20);
		this.extraActivationConditions = activationPredicate;
	}

	public AnimatableMeleeGoal(AnimatableMonsterEntity owner, Supplier<? extends IAnimationBuilder> meleeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, Predicate<AnimatableMonsterEntity> activationPredicate) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, 50, 1, 20);
		this.extraActivationConditions = activationPredicate;
	}
	
	public AnimatableMeleeGoal(AnimatableMonsterEntity owner, Supplier<? extends IAnimationBuilder> meleeAnim, byte attackId, double angleRange, double actionPointTickStart, double actionPointTickEnd, int presetCooldown) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, angleRange, 1, presetCooldown);
	}

	public AnimatableMeleeGoal(AnimatableMonsterEntity owner, Supplier<? extends IAnimationBuilder> meleeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, int probability, int presetCooldown) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, 50, probability, presetCooldown);
	}

	public AnimatableMeleeGoal(AnimatableMonsterEntity owner, Supplier<? extends IAnimationBuilder> meleeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, Integer presetCooldown) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, 50, 1, presetCooldown);
	}

	public AnimatableMeleeGoal(AnimatableMonsterEntity owner, Supplier<? extends IAnimationBuilder> meleeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double angleRange, int presetCooldown, Predicate<AnimatableMonsterEntity> activationPredicate) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, angleRange, 1, presetCooldown);
		this.extraActivationConditions = activationPredicate;
	}

	public AnimatableMeleeGoal(AnimatableMonsterEntity owner, Supplier<? extends IAnimationBuilder> meleeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, int presetCooldown, Predicate<AnimatableMonsterEntity> activationPredicate) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, 50, 1, presetCooldown);
		this.extraActivationConditions = activationPredicate;
	}
	
	public AnimatableMeleeGoal(AnimatableMonsterEntity owner, Supplier<? extends IAnimationBuilder> meleeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, int probability, int presetCooldown, Predicate<AnimatableMonsterEntity> activationPredicate) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, 50, probability, presetCooldown);
		this.extraActivationConditions = activationPredicate;
	}
	
	public AnimatableMeleeGoal(AnimatableMonsterEntity owner, Supplier<? extends IAnimationBuilder> meleeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double angleRange, int probability, int presetCooldown, Predicate<AnimatableMonsterEntity> activationPredicate) {
		this(owner, meleeAnim, attackId, actionPointTickStart, actionPointTickEnd, angleRange, probability, presetCooldown);
		this.extraActivationConditions = activationPredicate;
	}
	
	@SafeVarargs
	public final AnimatableMeleeGoal pickBetweenAnimations(Supplier<? extends IAnimationBuilder>... animations) {
		this.animationsToPick = new ObjectArrayList<Supplier<? extends IAnimationBuilder>>(animations.length);
		
		for (Supplier<? extends IAnimationBuilder> anim : animations) {
			if (anim == null || animationsToPick.contains(anim)) continue;
			
			animationsToPick.add(anim);
		}
		
		return this;
	}

	public AnimatableMeleeGoal soundOnStart(Supplier<SoundEvent> sound, float soundPitch) {
		this.soundOnStart = sound;
		this.soundPitch = soundPitch;
		return this;
	}

	@Override
	public boolean canUse() {
		if (curCooldown > 0) curCooldown--;
		
		return ObjectUtil.performNullityChecks(false, owner, owner.getTarget(), attackId) && !owner.isOnAttackCooldown() && curCooldown <= 0 && !owner.getTarget().isInvulnerable() && owner.isAlive() && !owner.isAttacking() && owner.getTarget().isAlive()
				&& owner.distanceTo(owner.getTarget()) <= owner.getMeleeAttackReach(owner.getTarget())
				&& (extraActivationConditions != null ? extraActivationConditions.test(owner) && owner.getRandom().nextInt(probability) == 0 : owner.getRandom().nextInt(probability) == 0);
	}

	@Override
	public boolean canContinueToUse() {
		return ObjectUtil.performNullityChecks(false, owner, curAnim.get(), attackId) && !owner.isDeadOrDying() && !curAnim.get().hasAnimationFinished();
	}

	@Override
	public void start() {
		owner.setAttackID(attackId);
		owner.getNavigation().stop();
		
		Supplier<? extends IAnimationBuilder> targetAnim = animationsToPick != null && !animationsToPick.isEmpty() ? animationsToPick.get(owner.level.getRandom().nextInt(animationsToPick.size())) : meleeAnim;
		
		owner.playAnimation(targetAnim.get(), true);

		if (soundOnStart != null) owner.playSound(soundOnStart.get(), 1.0F, soundPitch);

		this.curAnim = targetAnim;
	}

	@Override
	public void stop() {
		owner.setAttackID((byte) 0);
		owner.setAttackCooldown(10);
		owner.stopAnimation(curAnim.get());
		
		this.curAnim = null;
		this.curCooldown = presetCooldown;
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
		
		double reach = owner.getMeleeAttackReach(target);
		List<LivingEntity> potentialAffectedTargets = EntityUtil.getAllEntitiesAround(owner, reach, reach, reach, reach);

		if (curAnim.get().getWrappedAnimProgress() <= actionPointTickStart) owner.getLookControl().setLookAt(target, 30.0F, 30.0F);
		else EntityUtil.freezeEntityRotation(owner);
		
		for (LivingEntity potentialAffectedTarget : potentialAffectedTargets) {		
			if (owner.isAlliedTo(potentialAffectedTarget) || owner.getClass() == potentialAffectedTarget.getClass()) continue;
			
			double targetAngle = MathUtil.getRelativeAngleBetweenEntities(owner, potentialAffectedTarget);
			double attackAngle = owner.yBodyRot % 360;
			
			if (targetAngle < 0) targetAngle += 360;
			if (attackAngle < 0) attackAngle += 360;
			
			double relativeHitAngle = targetAngle - attackAngle;
			float hitDistanceSqr = (float) (Math.sqrt((target.getZ() - owner.getZ()) * (target.getZ() - owner.getZ()) + (target.getX() - owner.getX()) * (target.getX() - owner.getX())) - owner.getBbWidth() / 2F);
			
			if (MathUtil.isBetween(curAnim.get().getWrappedAnimProgress(), actionPointTickStart, actionPointTickEnd)) {
				if (hitDistanceSqr <= reach && MathUtil.isWithinAngleRestriction(relativeHitAngle, angleRange)) owner.doHurtTarget(potentialAffectedTarget);
			}
		}
	}
}
