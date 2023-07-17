package io.github.chaosawakens.common.entity.ai.goals.hostile;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.common.util.EnumUtil.BlockPatternShape;
import io.github.chaosawakens.common.util.MathUtil;
import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.command.arguments.EntityAnchorArgument.Type;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;

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
	private Predicate<AnimatableMonsterEntity> extraActivationConditions;
	private Predicate<Block> blockAffectConditions;
	@Nullable
	protected List<Supplier<? extends IAnimationBuilder>> animationsToPick;
	protected int curCooldown;
	protected Supplier<? extends IAnimationBuilder> curAnim;

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange, int amountThreshold, int probability, boolean shouldFreezeRotation, boolean shouldAffectBlocks) {
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
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange, int amountThreshold, int probability) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, aoeRange, amountThreshold, probability, true, false);
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange, int amountThreshold, boolean shouldFreezeRotation, boolean shouldAffectBlocks) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, aoeRange, amountThreshold, 1, shouldFreezeRotation, shouldAffectBlocks);
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange, int amountThreshold) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, aoeRange, 3, 1, true, false);
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange, boolean shouldFreezeRotation, boolean shouldAffectBlocks) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, aoeRange, 3, 1, shouldFreezeRotation, shouldAffectBlocks);
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, aoeRange, 3, 1, true, false);
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, boolean shouldFreezeRotation, boolean shouldAffectBlocks) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, 8.0D, 3, 1, shouldFreezeRotation, shouldAffectBlocks);
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, 8.0D, 3, 1, true, false);
	}

	//TODO Organize
	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange, int amountThreshold, Predicate<AnimatableMonsterEntity> extraActivationConditions) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, aoeRange, amountThreshold, 1, true, false);
		this.extraActivationConditions = extraActivationConditions;
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange, int amountThreshold, boolean shouldFreezeRotation, boolean shouldAffectBlocks, Predicate<AnimatableMonsterEntity> extraActivationConditions) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, aoeRange, amountThreshold, 1, shouldFreezeRotation, shouldAffectBlocks);
		this.extraActivationConditions = extraActivationConditions;
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange, boolean shouldFreezeRotation, boolean shouldAffectBlocks, Predicate<AnimatableMonsterEntity> extraActivationConditions) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, aoeRange, 3, 1, shouldFreezeRotation, shouldAffectBlocks);
		this.extraActivationConditions = extraActivationConditions;
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange, Predicate<AnimatableMonsterEntity> extraActivationConditions) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, aoeRange, 3, 1, true, false);
		this.extraActivationConditions = extraActivationConditions;
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, boolean shouldFreezeRotation, boolean shouldAffectBlocks, Predicate<AnimatableMonsterEntity> extraActivationConditions) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, 8.0D, 3, 1, shouldFreezeRotation, shouldAffectBlocks);
		this.extraActivationConditions = extraActivationConditions;
	}

	public AnimatableAOEGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, Predicate<AnimatableMonsterEntity> extraActivationConditions) {
		this(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, 8.0D, 3, 1, true, false);
		this.extraActivationConditions = extraActivationConditions;
	}
	
	public AnimatableAOEGoal setBlockAffectingConditions(Predicate<Block> blockAffectConditions) {
		this.blockAffectConditions = blockAffectConditions;
		
		return this;
	}

	@Override
	public boolean canUse() {
		return ObjectUtil.performNullityChecks(false, owner, owner.getTarget(), aoeAnim, attackId, shouldFreezeRotation)
				&& EntityUtil.getAllEntitiesAround(owner, aoeRange, aoeRange, aoeRange, aoeRange).size() >= amountThreshold
				&& owner.isAlive() && owner.getTarget().isAlive() && owner.getAttackID() == (byte) 0
				&& actionPointTickStart <= aoeAnim.get().getWrappedAnimLength()
				&& actionPointTickEnd <= aoeAnim.get().getWrappedAnimLength()
				&& (extraActivationConditions != null ? extraActivationConditions.test(owner) : owner.getRandom().nextInt(probability) == 0);
	}

	@Override
	public boolean canContinueToUse() {
		return ObjectUtil.performNullityChecks(false, owner, owner.getTarget(), aoeAnim, attackId, shouldFreezeRotation)
				&& !aoeAnim.get().hasAnimationFinished() && owner.isAlive();
	}

	@Override
	public void start() {
		owner.playAnimation(aoeAnim.get(), false);
		owner.setAttackID(attackId);
		owner.getNavigation().stop();
	}

	@Override
	public void stop() {
		owner.setAttackID((byte) 0);
	}

	@Override
	public void tick() {
		List<LivingEntity> affectedTargets = EntityUtil.getAllEntitiesAround(owner, aoeRange, aoeRange, aoeRange, aoeRange);
		List<BlockPos> affectedBlockPositions = BlockPatternShape.CIRCLE.applyShape(owner.level, owner.blockPosition(), aoeRange, 1, true, false, blockAffectConditions);

		if (shouldFreezeRotation) EntityUtil.freezeEntityRotation(owner);
		else if (aoeAnim.get().getWrappedAnimProgress() < actionPointTickStart) owner.lookAt(Type.EYES, owner.getTarget().position());
		
		if (!affectedTargets.isEmpty()) {
			for (LivingEntity affectedTarget : affectedTargets) {
				if (MathUtil.isBetween(aoeAnim.get().getWrappedAnimProgress(), actionPointTickStart, actionPointTickEnd)) {
					if (MathUtil.getDistanceBetween(owner, affectedTarget) <= aoeRange) owner.doHurtTarget(affectedTarget);
				}
			}
		}
		affectedBlockPositions.forEach((targetPos) -> owner.level.setBlock(targetPos, Blocks.BRAIN_CORAL_BLOCK.defaultBlockState(), 2));
		
		if (aoeAnim.get().getWrappedAnimProgress() >= actionPointTickStart && !shouldFreezeRotation) EntityUtil.freezeEntityRotation(owner);
	}

}
