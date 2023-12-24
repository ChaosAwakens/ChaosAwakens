package io.github.chaosawakens.common.entity.ai.navigation.base;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public class RefinedPath extends Path {

	public RefinedPath(List<PathPoint> nodes, BlockPos targetPos, boolean hasReached) {
		super(nodes, targetPos, hasReached);
	}

	@Override
	public PathPoint getNode(int pIndex) {
		return super.getNode(MathHelper.clamp(pIndex, 0, getNodeCount() - 1));
	}
	
	@Override
	public BlockPos getNodePos(int nodeIndex) {
		return getNode(nodeIndex).asBlockPos();
	}

	@Override
	public Vector3d getEntityPosAtNode(Entity pEntity, int pIndex) {
		PathPoint curNode = getNode(pIndex);

		double nodeX = curNode.x + Math.round(pEntity.getBbWidth() + 1.0F) * 0.5D;
		double nodeY = curNode.y;
		double nodeZ = curNode.z + Math.round(pEntity.getBbWidth() + 1.0F) * 0.5D;

		return new Vector3d(nodeX, nodeY, nodeZ);
	}
	
	public static RefinedPath copyFrom(Path pathToCopy) {
		ObjectArrayList<PathPoint> copiedNodes = new ObjectArrayList<PathPoint>(pathToCopy.getNodeCount());
		
		for (int i = 0; i < pathToCopy.getNodeCount(); i++) copiedNodes.add(pathToCopy.getNode(i));
		
		return new RefinedPath(copiedNodes, pathToCopy.getTarget(), pathToCopy.canReach());
	}
	
	@Override
	public String toString() {
		return String.format("RefinedPath{length = %1$d, targetPos = %2$s, prevNodeIndex = %3$d, prevNode = %4$s, curNodeIndex = %5$d, curNode = %6$s, nextNodeIndex = %7$d, nextNode = %8$s}", getNodeCount(), getTarget(), getNextNodeIndex() - 1, getNode(getNextNodeIndex() - 1), getNextNodeIndex(), getNextNode(), getNextNodeIndex() + 1, getNode(getNextNodeIndex() + 1));
	}
}