package io.github.chaosawakens.common.entity.ai.pathfinding;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Region;

import javax.annotation.Nullable;
import java.util.Set;

public class CAPathFinder extends PathFinder {

	public CAPathFinder(NodeProcessor processor, int maxVisitedNodes) {
		super(processor, maxVisitedNodes);
	}
	
	@Nullable
	@Override
	public Path findPath(Region region, MobEntity pathfindingEntity, Set<BlockPos> targetPosSet, float range, int accuracy, float ySearchMultiplier) {
		Path superPath = super.findPath(region, pathfindingEntity, targetPosSet, range, accuracy, ySearchMultiplier);
		return superPath == null ? null : new CAPath(superPath);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

}
