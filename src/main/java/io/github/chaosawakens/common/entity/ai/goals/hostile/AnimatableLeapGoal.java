package io.github.chaosawakens.common.entity.ai.goals.hostile;

import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.entity.ai.goal.Goal;

public class AnimatableLeapGoal extends Goal {
	private final AnimatableMonsterEntity owner;
	private final Supplier<SingletonAnimationBuilder> leapAnim;
	private final Supplier<SingletonAnimationBuilder> midairAnim;
	private final Supplier<SingletonAnimationBuilder> landAnim;
	private final byte attackId;
	private final int probability;
	private final double leapPower;
	@Nullable
	private Predicate<AnimatableMonsterEntity> extraActivationConditions;
	
	public AnimatableLeapGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> leapAnim, Supplier<SingletonAnimationBuilder> midairAnim, Supplier<SingletonAnimationBuilder> landAnim, byte attackId, double leapPower, int probability, @Nullable Predicate<AnimatableMonsterEntity> extraActivationConditions) {
		this.owner = owner;
		this.leapAnim = leapAnim;
		this.midairAnim = midairAnim;
		this.landAnim = landAnim;
		this.attackId = attackId;
		this.leapPower = leapPower;
		this.probability = probability;
		this.extraActivationConditions = extraActivationConditions;
	}
	
	public AnimatableLeapGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> leapAnim, Supplier<SingletonAnimationBuilder> midairAnim, Supplier<SingletonAnimationBuilder> landAnim, byte attackId, double leapPower) {
		this(owner, leapAnim, midairAnim, landAnim, attackId, leapPower, 1, null);
	}
	
	public AnimatableLeapGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> leapAnim, Supplier<SingletonAnimationBuilder> midairAnim, Supplier<SingletonAnimationBuilder> landAnim, byte attackId, double leapPower, int probability) {
		this(owner, leapAnim, midairAnim, landAnim, attackId, leapPower, probability, null);
	}
	
	public AnimatableLeapGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> leapAnim, Supplier<SingletonAnimationBuilder> midairAnim, Supplier<SingletonAnimationBuilder> landAnim, byte attackId, double leapPower, @Nullable Predicate<AnimatableMonsterEntity> extraActivationConditions) {
		this(owner, leapAnim, midairAnim, landAnim, attackId, leapPower, 1, extraActivationConditions);
	}
	
	public AnimatableLeapGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> leapAnim, Supplier<SingletonAnimationBuilder> midairAnim, Supplier<SingletonAnimationBuilder> landAnim, byte attackId, int probability, @Nullable Predicate<AnimatableMonsterEntity> extraActivationConditions) {
		this(owner, leapAnim, midairAnim, landAnim, attackId, 30.0D, probability, extraActivationConditions);
	}
	
	public AnimatableLeapGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> leapAnim, Supplier<SingletonAnimationBuilder> midairAnim, Supplier<SingletonAnimationBuilder> landAnim, byte attackId, @Nullable Predicate<AnimatableMonsterEntity> extraActivationConditions) {
		this(owner, leapAnim, midairAnim, landAnim, attackId, 30.0D, 1, extraActivationConditions);
	}
	
	public AnimatableLeapGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> leapAnim, Supplier<SingletonAnimationBuilder> midairAnim, Supplier<SingletonAnimationBuilder> landAnim, byte attackId) {
		this(owner, leapAnim, midairAnim, landAnim, attackId, 30.0D, 1, null);
	}

	@Override
	public boolean canUse() {
		return ObjectUtil.performNullityChecks(false, owner, owner.getTarget(), leapAnim.get(), midairAnim.get(), landAnim.get(), attackId);
	}
	
	@Override
	public boolean canContinueToUse() {
		return ObjectUtil.performNullityChecks(false, owner, leapAnim.get(), midairAnim.get(), landAnim.get(), attackId) && (owner.isOnGround() ? !owner.isDeadOrDying() : true) && !landAnim.get().hasAnimationFinished();
	}
	
	@Override
	public boolean isInterruptable() {
		return owner.isDeadOrDying() && owner.isOnGround();
	}
	
	@Override
	public void start() {
		owner.setAttackID(attackId);
		owner.playAnimation(leapAnim.get(), true);
		owner.getNavigation().stop();
	}
	
	@Override
	public void stop() {
		owner.setAttackID((byte) 0);
	}
	
	@Override
	public void tick() {
		
	}
}