package io.github.chaosawakens.common.entity.ai.goals.passive.land;

import java.util.function.Supplier;

import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import net.minecraft.entity.ai.goal.Goal;

public class AnimatableEatGrassGoal extends Goal {
	protected final AnimatableAnimalEntity owner;
	protected final Supplier<IAnimationBuilder> grazeAnim;
	protected final double eatActionPointTick;
	protected final int probability;
	
	public AnimatableEatGrassGoal(AnimatableAnimalEntity owner, Supplier<IAnimationBuilder> grazeAnim, double eatActionPointTick, int probability) {
		this.owner = owner;
		this.grazeAnim = grazeAnim;
		this.eatActionPointTick = eatActionPointTick;
		this.probability = probability;
	}

	@Override
	public boolean canUse() {
		return false;
	}
	
	@Override
	public boolean canContinueToUse() {
		return false;
	}
	
	@Override
	public void start() {
		
	}
	
	@Override
	public void stop() {
		
	}
	
	@Override
	public void tick() {
		
	}
}
