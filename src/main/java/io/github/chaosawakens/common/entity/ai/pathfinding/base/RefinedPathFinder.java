package io.github.chaosawakens.common.entity.ai.pathfinding.base;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedListMultimap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.Region;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RefinedPathFinder extends PathFinder {
	private final LinkedListMultimap<Region, RefinedPath> cachedMappedRegionalPaths = LinkedListMultimap.create();
	private PathfindingState curState = PathfindingState.IDLE;
	@Nullable
	private PathfindingState lastCachedState;
	private boolean isCurrentlyStuck = false;
	private boolean canRecomputePath = false;
	private int maxPathRecomputations = 20;
	private int curStuckRecomputationInterval = 15;
	@Nullable
	private RefinedPath curPath;

	public RefinedPathFinder(NodeProcessor curNodeEvaluator) {
		super(curNodeEvaluator, -1);
	}

	public ImmutableMultimap<Region, RefinedPath> getMappedRegionalPaths() {
		return ImmutableMultimap.copyOf(cachedMappedRegionalPaths);
	}

	public PathfindingState getCurrentState() {
		return curState;
	}

	public void setCurrentState(PathfindingState curState) {
		this.curState = curState;
	}

	@Nullable
	public PathfindingState getPreviousState() {
		return lastCachedState;
	}

	public void setPreviousState(@Nullable PathfindingState lastCachedState) {
		this.lastCachedState = lastCachedState;
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
	@Nonnull
	public Path findMostAccuratePath(Region targetRegion, MobEntity pathfindingOwner, Set<BlockPos> targetPositions) {
		ObjectArrayList<VoxelShape> validCollisions = Stream.concat(targetRegion.getBlockCollisions(pathfindingOwner, pathfindingOwner.getBoundingBox()), targetRegion.getEntityCollisions(pathfindingOwner, pathfindingOwner.getBoundingBox(), (target) -> target.canBeCollidedWith() || target.canCollideWith(pathfindingOwner))).collect(Collectors.toCollection(ObjectArrayList::new));
		
		
		return null;
	}

	public void clearCachedPathData() {
		cachedMappedRegionalPaths.values().clear();
	}

	public void clearAllCachedPathData() {
		cachedMappedRegionalPaths.clear();
	}

	public void resetPathFinder() {
		clearAllCachedPathData();
		setCurrentState(PathfindingState.IDLE);
		setPreviousState(null);
	}
}