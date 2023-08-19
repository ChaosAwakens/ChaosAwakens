package io.github.chaosawakens.common.entity.ai.goals.passive.land;

import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import io.github.chaosawakens.common.util.BlockPosUtil;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.common.util.MathUtil;
import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraftforge.event.ForgeEventFactory;

public class AnimatableEatGrassGoal extends Goal {
	protected final AnimatableAnimalEntity owner;
	protected final Supplier<IAnimationBuilder> grazeAnim;
	protected final double eatActionPointTick;
	protected final int probability;
	@Nullable
	protected Predicate<AnimatableAnimalEntity> extraActivationConditions;
	
	public AnimatableEatGrassGoal(AnimatableAnimalEntity owner, Supplier<IAnimationBuilder> grazeAnim, double eatActionPointTick, int probability) {
		this.owner = owner;
		this.grazeAnim = grazeAnim;
		this.eatActionPointTick = eatActionPointTick;
		this.probability = probability;
	}
	
	public AnimatableEatGrassGoal(AnimatableAnimalEntity owner, Supplier<IAnimationBuilder> grazeAnim, double eatActionPointTick) {
		this(owner, grazeAnim, eatActionPointTick, 750);
	}
	
	public AnimatableEatGrassGoal setExtraActivationConditions(Predicate<AnimatableAnimalEntity> extraActivationConditions) {
		this.extraActivationConditions = extraActivationConditions;
		return this;
	}

	@Override
	public boolean canUse() {
		return ObjectUtil.performNullityChecks(false, owner, grazeAnim, grazeAnim.get()) && owner.isAlive() && (BlockPosUtil.checkValidPos(owner.level, owner.blockPosition().below(), (targetState) -> targetState.is(Blocks.GRASS_BLOCK)) || BlockPosUtil.checkValidPos(owner.level, owner.blockPosition(), (targetState) -> targetState.is(Blocks.GRASS))) && (extraActivationConditions != null ? extraActivationConditions.test(owner) && owner.getRandom().nextInt(probability) == 0 : owner.getRandom().nextInt(probability) == 0);
	}
	
	@Override
	public boolean canContinueToUse() {
		return ObjectUtil.performNullityChecks(false, owner, grazeAnim, grazeAnim.get()) && owner.isAlive() && (BlockPosUtil.checkValidPos(owner.level, owner.blockPosition().below(), (targetState) -> targetState.is(Blocks.GRASS_BLOCK)) || BlockPosUtil.checkValidPos(owner.level, owner.blockPosition(), (targetState) -> targetState.is(Blocks.GRASS))) && owner.hurtTime == 0 && !grazeAnim.get().hasAnimationFinished();
	}
	
	@Override
	public void start() {
		owner.playAnimation(grazeAnim.get(), false);
		owner.getNavigation().stop();
	}
	
	@Override
	public void stop() {
		owner.stopAnimation(grazeAnim.get());
	}
	
	@Override
	public void tick() {
		EntityUtil.freezeEntityRotation(owner);
		
		if (MathUtil.isBetween(grazeAnim.get().getWrappedAnimProgress(), eatActionPointTick, eatActionPointTick + 1)) {
			if (BlockPosUtil.checkValidPos(owner.level, owner.blockPosition().below(), (targetState) -> targetState.is(Blocks.GRASS_BLOCK))) {
				if (ForgeEventFactory.getMobGriefingEvent(owner.level, owner)) {
					owner.level.levelEvent(2001, owner.blockPosition().below(), Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
					owner.level.setBlock(owner.blockPosition().below(), Blocks.DIRT.defaultBlockState(), 2);
				}
				
				owner.ate();
			} else if (BlockPosUtil.checkValidPos(owner.level, owner.blockPosition(), (targetState) -> targetState.is(Blocks.GRASS))) {
				if (ForgeEventFactory.getMobGriefingEvent(owner.level, owner)) owner.level.destroyBlock(owner.blockPosition(), false);
				
				owner.ate();
			}
		}
	}
}
