package io.github.chaosawakens.common.entity.ai.pathfinding.base;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Region;

import javax.annotation.Nullable;
import java.util.*;

public class BandaidPathfinder extends PathFinder { // Just a copy of the vanilla one with some minor algorithmic optimizations, but nothing major (as this is a quick bandaid solution (duh))
    private final PathPoint[] neighbors = new PathPoint[32];
    private final int maxVisitedNodes;
    private final NodeProcessor nodeEvaluator;
    private final PathHeap openSet = new PathHeap();

    public BandaidPathfinder(NodeProcessor processor, int maxVisitedNodes) {
        super(processor, maxVisitedNodes);
        this.nodeEvaluator = processor;
        this.maxVisitedNodes = maxVisitedNodes;
    }

    @Nullable
    @Override
    public Path findPath(Region pRegion, MobEntity pMob, Set<BlockPos> pTargetPositions, float pMaxRange, int pAccuracy, float pSearchDepthMultiplier) {
        return findPath(pRegion, pMob, pTargetPositions, pMaxRange, pAccuracy, pSearchDepthMultiplier, 2.5F);
    }

    @Nullable
    public Path findPath(Region pRegion, MobEntity pMob, Set<BlockPos> pTargetPositions, float pMaxRange, int pAccuracy, float pSearchDepthMultiplier, float pathInflationBias) {
        this.openSet.clear();
        this.nodeEvaluator.prepare(pRegion, pMob);
        PathPoint startPathPoint = this.nodeEvaluator.getStart();

        Object2ObjectLinkedOpenHashMap<FlaggedPathPoint, BlockPos> goalMap = new Object2ObjectLinkedOpenHashMap<>(pTargetPositions.size());
        for (BlockPos blockPos : pTargetPositions) {
            FlaggedPathPoint goal = this.nodeEvaluator.getGoal(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            goalMap.put(goal, blockPos);
        }

        Path path = findPath(startPathPoint, goalMap, pMaxRange, pAccuracy, pSearchDepthMultiplier, pathInflationBias);
        this.nodeEvaluator.done();
        return path;
    }

    @Nullable
    private Path findPath(PathPoint startNode, Map<FlaggedPathPoint, BlockPos> targetPoints, float maxSearchDistance, int manhattanDistanceThreshold, float searchDepthMultiplier, float inflationBias) {
        Set<FlaggedPathPoint> targetFlaggedPathPoints = targetPoints.keySet();
        startNode.g = 0.0F;
        startNode.h = getBestH(startNode, targetFlaggedPathPoints);
        startNode.f = startNode.h;
        this.openSet.clear();
        this.openSet.insert(startNode);
        int iterations = 0;
        ObjectOpenHashSet<FlaggedPathPoint> reachedFlaggedPathPoints = new ObjectOpenHashSet<>(targetFlaggedPathPoints.size());
        int maxIterations = (int)((float)this.maxVisitedNodes * searchDepthMultiplier);

        while (!this.openSet.isEmpty() && iterations < maxIterations && !reachedFlaggedPathPoints.isEmpty()) {
            ++iterations;

            PathPoint currentPathPoint = this.openSet.pop();
            currentPathPoint.closed = true;

            for (FlaggedPathPoint flaggedTarget : targetFlaggedPathPoints) {
                if (currentPathPoint.distanceManhattan(flaggedTarget) <= manhattanDistanceThreshold) {
                    flaggedTarget.setReached();
                    reachedFlaggedPathPoints.add(flaggedTarget);
                }
            }

            if (currentPathPoint.distanceTo(startNode) < maxSearchDistance) {
                int neighborCount = this.nodeEvaluator.getNeighbors(this.neighbors, currentPathPoint);

                for (int curIdx = 0; curIdx < neighborCount; ++curIdx) {
                    PathPoint neighborNode = this.neighbors[curIdx];
                    float distanceToNeighbor = currentPathPoint.distanceTo(neighborNode);
                    neighborNode.walkedDistance = currentPathPoint.walkedDistance + distanceToNeighbor;
                    float tentativeCostFromStart = currentPathPoint.g + distanceToNeighbor + neighborNode.costMalus;

                    if (neighborNode.walkedDistance < maxSearchDistance && (!neighborNode.inOpenSet() || tentativeCostFromStart < neighborNode.g)) {
                        neighborNode.cameFrom = currentPathPoint;
                        neighborNode.g = tentativeCostFromStart;
                        neighborNode.h = getBestH(neighborNode, targetFlaggedPathPoints) * inflationBias;

                        if (neighborNode.inOpenSet()) this.openSet.changeCost(neighborNode, neighborNode.g + neighborNode.h);
                        else {
                            neighborNode.f = neighborNode.g + neighborNode.h;
                            this.openSet.insert(neighborNode);
                        }
                    }
                }
            }
        }

        Optional<Path> shortestPath = !reachedFlaggedPathPoints.isEmpty() ? reachedFlaggedPathPoints.stream()
                .map((flaggedPathPoint) -> reconstructPath(flaggedPathPoint.getBestNode(), targetPoints.get(flaggedPathPoint), true))
                .min(Comparator.comparingInt(Path::getNodeCount))
                : targetFlaggedPathPoints.stream()
                .map((flaggedPathPoint) -> reconstructPath(flaggedPathPoint.getBestNode(), targetPoints.get(flaggedPathPoint), false))
                .min(Comparator.comparingDouble(Path::getDistToTarget)
                        .thenComparingInt(Path::getNodeCount));

        return Optional.ofNullable(shortestPath).get().get();
    }

    private Path reconstructPath(PathPoint finalPathPoint, BlockPos targetPosition, boolean reachesTarget) {
        LinkedList<PathPoint> pathPoints = new LinkedList<>();
        PathPoint currentPathPoint = finalPathPoint;

        while (currentPathPoint != null) {
            pathPoints.addFirst(currentPathPoint);
            currentPathPoint = currentPathPoint.cameFrom;
        }

        return new Path(pathPoints, targetPosition, reachesTarget);
    }

    private float getBestH(PathPoint originalPathpoint, Set<FlaggedPathPoint> targetPathPoints) {
        float updatedHCost = Float.MAX_VALUE;

        for (FlaggedPathPoint flaggedpathpoint : targetPathPoints) {
            float manhDist = originalPathpoint.distanceTo(flaggedpathpoint);
            flaggedpathpoint.updateBest(manhDist, originalPathpoint);
            updatedHCost = Math.min(manhDist, updatedHCost);
        }

        return updatedHCost;
    }
}
