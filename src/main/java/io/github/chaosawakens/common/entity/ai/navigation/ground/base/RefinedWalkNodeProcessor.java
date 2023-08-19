package io.github.chaosawakens.common.entity.ai.navigation.ground.base;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.FlaggedPathPoint;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.Region;

public class RefinedWalkNodeProcessor extends WalkNodeProcessor {
	
	public RefinedWalkNodeProcessor() {
		
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
	
	@Override
	protected PathNodeType evaluateBlockPathType(IBlockReader pLevel, boolean pCanOpenDoors, boolean pCanEnterDoors, BlockPos pPos, PathNodeType pNodeType) {
		return super.evaluateBlockPathType(pLevel, pCanOpenDoors, pCanEnterDoors, pPos, pNodeType);
	}
}
