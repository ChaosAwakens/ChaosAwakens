package io.github.chaosawakens.common.entity.ai.goals.hostile;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.misc.AOEHitboxEntity;
import io.github.chaosawakens.common.entity.misc.CAScreenShakeEntity;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.common.util.MathUtil;
import io.github.chaosawakens.common.util.ObjectUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.command.arguments.EntityAnchorArgument.Type;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;

public class AnimatableAOEGoal extends Goal {
	private final AnimatableMonsterEntity owner;
	private Supplier<SingletonAnimationBuilder> aoeAnim;
	private final byte attackId;
	private final double actionPointTickStart;
	private final double actionPointTickEnd;
	private double aoeRange;
	private final int amountThreshold;
	private final int probability;
	private final boolean shouldFreezeRotation;
	private final boolean shouldAffectBlocks;
	private final boolean isProgressive;
	@Nullable
	private Predicate<AnimatableMonsterEntity> extraActivationConditions;
	@Nullable
	private Predicate<Block> blockAffectConditions;
	private final int presetCooldown;
	@Nullable
	protected List<Supplier<? extends IAnimationBuilder>> animationsToPick;
	protected int curCooldown;
	protected Supplier<? extends IAnimationBuilder> curAnim;
	private final ObjectArrayList<LivingEntity> affectedEntities = new ObjectArrayList<>();
	private AOEHitboxEntity aoeDamageHitBox; // Cache to prevent lag

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange, int amountThreshold, int probability, boolean shouldFreezeRotation, boolean shouldAffectBlocks, boolean isProgressive, int presetCooldown) {
		this.owner = owner;
		this.aoeAnim = aoeAnim;
		this.attackId = attackId;
		this.actionPointTickStart = actionPointTickStart;
		this.actionPointTickEnd = actionPointTickEnd;
		this.aoeRange = aoeRange;
		this.amountThreshold = amountThreshold;
		this.probability = probability;
		this.shouldFreezeRotation = shouldFreezeRotation;
		this.shouldAffectBlocks = shouldAffectBlocks;
		this.isProgressive = isProgressive;
		this.presetCooldown = presetCooldown;
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange, int amountThreshold, int probability, int presetCooldown) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, aoeRange, amountThreshold, probability, true, false, true, presetCooldown);
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange, int amountThreshold, boolean shouldFreezeRotation, boolean shouldAffectBlocks, boolean isProgressive, int presetCooldown) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, aoeRange, amountThreshold, 1, shouldFreezeRotation, shouldAffectBlocks, isProgressive, presetCooldown);
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange, int amountThreshold, int presetCooldown) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, aoeRange, 3, 1, true, false, true, presetCooldown);
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange, boolean shouldFreezeRotation, boolean shouldAffectBlocks, boolean isProgressive, int presetCooldown) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, aoeRange, 3, 1, shouldFreezeRotation, shouldAffectBlocks, isProgressive, presetCooldown);
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange, int presetCooldown) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, aoeRange, 3, 1, true, false, true, presetCooldown);
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, boolean shouldFreezeRotation, boolean shouldAffectBlocks, boolean isProgressive, int presetCooldown) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, 8.0D, 3, 1, shouldFreezeRotation, shouldAffectBlocks, isProgressive, presetCooldown);
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, int presetCooldown) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, 8.0D, 3, 1, true, false, true, presetCooldown);
	}

	//TODO Organize
	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange, int amountThreshold, int presetCooldown, Predicate<AnimatableMonsterEntity> extraActivationConditions) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, aoeRange, amountThreshold, 1, true, false, true, presetCooldown);
		this.extraActivationConditions = extraActivationConditions;
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange, int amountThreshold, boolean shouldFreezeRotation, boolean shouldAffectBlocks, boolean isProgressive, int presetCooldown, Predicate<AnimatableMonsterEntity> extraActivationConditions) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, aoeRange, amountThreshold, 1, shouldFreezeRotation, shouldAffectBlocks, isProgressive, presetCooldown);
		this.extraActivationConditions = extraActivationConditions;
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange, boolean shouldFreezeRotation, boolean shouldAffectBlocks, boolean isProgressive, int presetCooldown, Predicate<AnimatableMonsterEntity> extraActivationConditions) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, aoeRange, 3, 1, shouldFreezeRotation, shouldAffectBlocks, isProgressive, presetCooldown);
		this.extraActivationConditions = extraActivationConditions;
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange, int presetCooldown, Predicate<AnimatableMonsterEntity> extraActivationConditions) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, aoeRange, 3, 1, true, false, true, presetCooldown);
		this.extraActivationConditions = extraActivationConditions;
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, boolean shouldFreezeRotation, boolean shouldAffectBlocks, boolean isProgressive, int presetCooldown, Predicate<AnimatableMonsterEntity> extraActivationConditions) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, 8.0D, 3, 1, shouldFreezeRotation, shouldAffectBlocks, isProgressive, presetCooldown);
		this.extraActivationConditions = extraActivationConditions;
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, int presetCooldown, Predicate<AnimatableMonsterEntity> extraActivationConditions) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, 8.0D, 3, 1, true, false, true, presetCooldown);
		this.extraActivationConditions = extraActivationConditions;
	}

	public AnimatableAOEGoal setBlockAffectingConditions(Predicate<Block> blockAffectConditions) {
		this.blockAffectConditions = blockAffectConditions;

		return this;
	}

	public AnimatableAOEGoal pickBetweenAnimations(Supplier<? extends IAnimationBuilder>... animations) {
		this.animationsToPick = new ObjectArrayList<Supplier<? extends IAnimationBuilder>>(animations.length);

		for (Supplier<? extends IAnimationBuilder> anim : animations) {
			if (anim == null || animationsToPick.contains(anim)) continue;

			animationsToPick.add(anim);
		}

		return this;
	}

	@Override
	public boolean canUse() {
		if (curCooldown > 0) curCooldown--;

		return ObjectUtil.performNullityChecks(false, owner, owner.getTarget(), attackId, shouldFreezeRotation) && curCooldown <= 0
				&& owner.distanceTo(owner.getTarget()) <= aoeRange + 2.0D
				&& EntityUtil.getAllEntitiesAround(owner, aoeRange, aoeRange, aoeRange, aoeRange).size() >= amountThreshold
				&& owner.isAlive() && owner.getTarget().isAlive() && !owner.isAttacking()
				&& (extraActivationConditions != null ? extraActivationConditions.test(owner) && owner.getRandom().nextInt(probability) == 0 : owner.getRandom().nextInt(probability) == 0);
	}

	@Override
	public boolean canContinueToUse() {
		return ObjectUtil.performNullityChecks(false, owner, owner.getTarget(), curAnim, attackId, shouldFreezeRotation) && !curAnim.get().hasAnimationFinished() && !owner.isDeadOrDying();
	}

	@Override
	public void start() {
		owner.setAttackID(attackId);
		owner.getNavigation().stop();

		Supplier<? extends IAnimationBuilder> targetAnim = animationsToPick != null && !animationsToPick.isEmpty() ? animationsToPick.get(owner.level.getRandom().nextInt(animationsToPick.size())) : aoeAnim;

		owner.playAnimation(targetAnim.get(), true);

		this.curAnim = targetAnim;
		
		this.aoeDamageHitBox = new AOEHitboxEntity(owner.level, owner.blockPosition(), (float) aoeRange, (float) aoeRange / 2, (int) (curAnim.get().getWrappedAnimLength() - curAnim.get().getWrappedAnimProgress()), 1, null);

		aoeDamageHitBox.setActionOnIntersection((target) -> {
			if (!affectedEntities.contains(target)) {
				owner.doHurtTarget(target);

				double targetAngle = (MathUtil.getAngleBetweenEntities(aoeDamageHitBox, target) + 90) * Math.PI / 180; //TODO Dist calc
				double kbMultiplier = target instanceof PlayerEntity ? -Math.min(owner.getAttackDamage() / 5, 100.0D) : -Math.min(owner.getAttackDamage() / 5, 100.0D) / 2.1D;

				target.setDeltaMovement(kbMultiplier * Math.cos(targetAngle), target.getDeltaMovement().normalize().y + Math.min(owner.getAttackDamage() / 10, 1.0), kbMultiplier * Math.sin(targetAngle));
				affectedEntities.add(target);
			}
		});

		affectedEntities.clear();
	}

	@Override
	public void stop() {
		owner.setAttackID((byte) 0);
		owner.playAnimation(owner.getIdleAnim(), true);

		this.curAnim = null;
		this.curCooldown = presetCooldown;

		affectedEntities.clear();
	}

	@Override
	public boolean isInterruptable() {
		return owner.isDeadOrDying();
	}

	@Override
	public void tick() {
		owner.getNavigation().stop();
		owner.setDeltaMovement(0, owner.getDeltaMovement().y, 0);

		List<LivingEntity> affectedTargets = EntityUtil.getAllEntitiesAround(owner, aoeRange, aoeRange, aoeRange, aoeRange);
	//	List<BlockPos> affectedBlockPositions = BlockPatternShape.CIRCLE.applyShape(owner.level, owner.blockPosition(), aoeRange, 1, true, false, blockAffectConditions);

		if (shouldFreezeRotation || curAnim.get().getWrappedAnimProgress() >= actionPointTickStart) EntityUtil.freezeEntityRotation(owner);
		else if (curAnim.get().getWrappedAnimProgress() < actionPointTickStart) owner.lookAt(Type.EYES, owner.getTarget().position());

		if (isProgressive) {
			if (MathUtil.isBetween(curAnim.get().getWrappedAnimProgress(), actionPointTickStart, actionPointTickStart + 1)) {
				CAScreenShakeEntity.shakeScreen(owner.level, owner.position(), (float) aoeRange, (float) Math.min(aoeRange / 100, 1.0D), 20, (int) aoeRange * 2);
				
				if (aoeDamageHitBox != null) owner.level.addFreshEntity(aoeDamageHitBox);
			}
		} else {
			if (!affectedTargets.isEmpty()) {
				for (LivingEntity affectedTarget : affectedTargets) {
					if (MathUtil.isBetween(curAnim.get().getWrappedAnimProgress(), actionPointTickStart, actionPointTickEnd)) {
						if (MathUtil.getDistanceBetween(owner, affectedTarget) <= aoeRange) {
							CAScreenShakeEntity.shakeScreen(owner.level, owner.position(), (float) aoeRange, (float) Math.min(aoeRange / 100, 1.0D), 20, (int) aoeRange * 2);

							if (!affectedEntities.contains(affectedTarget)) {
								owner.doHurtTarget(affectedTarget);
								affectedTargets.add(affectedTarget);
							}
						}
					}
				}
			}
		}

		if (shouldAffectBlocks) {
			//			affectedBlockPositions.forEach((targetPos) -> owner.level.setBlock(targetPos, Blocks.BRAIN_CORAL_BLOCK.defaultBlockState(), 2));
		}
	}
}
