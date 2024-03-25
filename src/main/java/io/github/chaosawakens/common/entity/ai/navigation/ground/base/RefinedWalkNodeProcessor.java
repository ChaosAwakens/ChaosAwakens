package io.github.chaosawakens.common.entity.ai.navigation.ground.base;

import io.github.chaosawakens.common.entity.ai.pathfinding.base.RefinedPathNode;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.FlaggedPathPoint;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.tags.ITag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.Region;

import javax.annotation.Nullable;

public class RefinedWalkNodeProcessor extends WalkNodeProcessor {
	protected final Object2DoubleArrayMap<RefinedPathNode> mappedNodesToDanger = new Object2DoubleArrayMap<>();
	@Nullable
	protected ITag<Block> breakableBlocksTag;
	@Nullable
	protected ITag<Block> validPassOverride;
	
	public RefinedWalkNodeProcessor() {
		
	}
	
	public RefinedWalkNodeProcessor setBreakableBlocks(ITag<Block> breakableBlocksTag) {
		this.breakableBlocksTag = breakableBlocksTag;
		return this;
	}

	public RefinedWalkNodeProcessor setValidPassOverrideTag(ITag<Block> validPassOverride) {
		this.validPassOverride = validPassOverride;
		return this;
	}
	
	@Override
	public void prepare(Region curRegion, MobEntity owner) {
		super.prepare(curRegion, owner);
	}
	
	@Override
	public PathPoint getStart() {
		return super.getStart();
	}
	
	@Override
	public PathNodeType getBlockPathType(IBlockReader pBlockaccess, int pX, int pY, int pZ, MobEntity pEntityliving, int pXSize, int pYSize, int pZSize, boolean pCanBreakDoors, boolean pCanEnterDoors) {
		return super.getBlockPathType(pBlockaccess, pX, pY, pZ, pEntityliving, pXSize, pYSize, pZSize, pCanBreakDoors, pCanEnterDoors);
	}
	
	@Override
	public FlaggedPathPoint getGoal(double targetX, double targetY, double targetZ) {
		return super.getGoal(targetX, targetY, targetZ);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected PathNodeType evaluateBlockPathType(IBlockReader pLevel, boolean pCanOpenDoors, boolean pCanEnterDoors, BlockPos pPos, PathNodeType pNodeType) {
		BlockState targetState = pLevel.getBlockState(pPos);
		
		switch (pNodeType) {
		case BLOCKED: return targetState.is(breakableBlocksTag) ? PathNodeType.BREACH : targetState.isAir(pLevel, pPos) ? PathNodeType.OPEN : PathNodeType.BLOCKED;
		case BREACH: return targetState.is(validPassOverride) ? PathNodeType.WALKABLE : targetState.isAir(pLevel, pPos) ? PathNodeType.OPEN : PathNodeType.BREACH;
		default: return super.evaluateBlockPathType(pLevel, pCanOpenDoors, pCanEnterDoors, pPos, pNodeType);
		}
	}
	
	
}
