package io.github.chaosawakens.common.entity.ai.goals.hostile;

import java.util.function.Supplier;

import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import net.minecraft.entity.ai.goal.Goal;

public class AnimatableLeapGoal extends Goal {
	private final AnimatableMonsterEntity owner;
	private final Supplier<SingletonAnimationBuilder> leapAnim;
	
	public AnimatableLeapGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> leapAnim) {
		this.owner = owner;
		this.leapAnim = leapAnim;
	}

	@Override
	public boolean canUse() {
		return false;
	}
	
	@Override
	public boolean canContinueToUse() {
		return super.canContinueToUse();
	}
	
	@Override
	public boolean isInterruptable() {
		return super.isInterruptable();
	}
	
	@Override
	public void start() {
		
	}
	
	@Override
	public void stop() {
		
	}
}
