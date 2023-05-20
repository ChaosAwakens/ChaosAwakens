package io.github.chaosawakens.common.entity.ai;

import io.github.chaosawakens.api.entity.IBlockBreakingMob;
import io.github.chaosawakens.common.entity.ai.pathfinding.CAPathFinder;
import net.minecraft.block.Block;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CAGroundPathNavigator extends GroundPathNavigator {
	public boolean shouldAvoidRain;
	public boolean shouldAvoidWater;
	public boolean shouldAvoidLava;
	public boolean shouldAvoidAllFluids;
	public boolean shouldWalkThroughBlocks; 

	public CAGroundPathNavigator(MobEntity mob, World world) {
		super(mob, world);
	}
	
	@Override
	protected PathFinder createPathFinder(int i) {
		this.nodeEvaluator = new CAWalkNodeProcessor();
		this.nodeEvaluator.setCanOpenDoors(true);
		
		return new CAPathFinder(this.nodeEvaluator, i);
	}
	
	@Override
	protected void trimPath() {
		super.trimPath();
		if (shouldAvoidRain && level.isRaining()) {
			if (level.isRainingAt(mob.blockPosition())) {
				return;
			}
		}
		
		if (shouldAvoidWater) {
			if (level.getFluidState(mob.blockPosition()).is(FluidTags.WATER)) {
				return;
			}
		}
		
		if (shouldAvoidLava) {
			if (level.getFluidState(mob.blockPosition()).is(FluidTags.LAVA)) {
				return;
			}
		}
		
		if (shouldAvoidAllFluids) {
			if (level.getFluidState(mob.blockPosition()).is(FluidTags.WATER) || level.getFluidState(mob.blockPosition()).is(FluidTags.LAVA)) {
				return;
			}
		}
		
		if (shouldWalkThroughBlocks) {
			BlockPos pos = mob.getNavigation().getTargetPos();
			PathNodeType type = mob.getNavigation().getNodeEvaluator().getBlockPathType(level, pos.getX(), pos.getY(), pos.getZ());
			if (type == PathNodeType.BLOCKED) {
				type = PathNodeType.WALKABLE;
			}
		}
	}
	
	@Override
	public void tick() {
		super.tick();
		if (!this.mob.fireImmune()) {
			if (this.mob.isInLava()) {
				if (this.mob.getPathfindingMalus(PathNodeType.LAVA) < 8.0F) {
					this.mob.setPathfindingMalus(PathNodeType.LAVA, 8.0F);
				}
			} else if (this.mob.getPathfindingMalus(PathNodeType.LAVA) > -1.0F) {
				this.mob.setPathfindingMalus(PathNodeType.LAVA, -1.0F);	
			}
		}
	}
	
	public CAGroundPathNavigator setShouldAvoidWater(boolean shouldAvoidWater) {
		this.shouldAvoidWater = shouldAvoidWater;
		return this;
	}
	
	public CAGroundPathNavigator setShouldAvoidLava(boolean shouldAvoidLava) {
		this.shouldAvoidLava = shouldAvoidLava;
		return this;
	}
	
	public CAGroundPathNavigator makeBlocksBreakable(Block... blocks) {
		if (!(mob instanceof IBlockBreakingMob)) return this;
		shouldWalkThroughBlocks = mob instanceof IBlockBreakingMob ? true : false;
		this.nodeEvaluator = new CAWalkNodeProcessor().setCanBreakBlocks(true).addBlockToBreakablesList(blocks);
		return this;
	}
}
