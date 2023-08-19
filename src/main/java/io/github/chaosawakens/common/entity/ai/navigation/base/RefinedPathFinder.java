package io.github.chaosawakens.common.entity.ai.navigation.base;

import java.util.Set;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Region;

public class RefinedPathFinder extends PathFinder {

	public RefinedPathFinder(NodeProcessor curNodeEvaluator, int maxVisitedNodes) {
		super(curNodeEvaluator, maxVisitedNodes);
	}
	
	@Override
	public Path findPath(Region pRegion, MobEntity pMob, Set<BlockPos> pTargetPositions, float pMaxRange, int pAccuracy, float pSearchDepthMultiplier) {
		return super.findPath(pRegion, pMob, pTargetPositions, pMaxRange, pAccuracy, pSearchDepthMultiplier);
	}
	
	/**
	 * Searches and attempts to find the most accurate possible path in a {@link MobEntity}'s current path using the provided positions.
	 * @param targetRegion
	 * @param pathfindingOwner
	 * @param targetPositions
	 * @return The most accurate possible path 
	 */
	public Path findMostAccuratePath(Region targetRegion, MobEntity pathfindingOwner, Set<BlockPos> targetPositions) {
		return null;
	}
}