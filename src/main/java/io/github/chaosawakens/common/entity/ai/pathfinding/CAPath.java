package io.github.chaosawakens.common.entity.ai.pathfinding;

import java.util.ArrayList;
import java.util.List;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.vector.Vector3d;

public class CAPath extends Path {
	
	public CAPath(Path origin) {
		super(copyPathPoints(origin), origin.getTarget(), origin.canReach());
	}
	
	public static List<PathPoint> copyPathPoints(Path origin) {
		List<PathPoint> pathpoints = new ArrayList<>();
		
		for (int count = 0; count < origin.getNodeCount(); count++) {
			pathpoints.add(origin.getNode(count));
		}
		
		return pathpoints;
	}
	
	@Override
	public Vector3d getEntityPosAtNode(Entity pathEntity, int index) {
		PathPoint nextPos = getNode(index);
		
		double x = nextPos.x + Math.floor(pathEntity.getBbWidth() + 1.0F) * 0.5D;
		double y = nextPos.y;
		double z = nextPos.z + Math.floor(pathEntity.getBbWidth() + 1.0F) * 0.5D;
		
		Vector3d newNextPos = new Vector3d(x, y, z);
		
		return newNextPos;
	}
	
	public static void divertPath(MobEntity pathEntity, Path from, Path to, double speedMultiplier) {
		if (pathEntity.getNavigation().isDone()) return;
		
		if (pathEntity.getNavigation().getPath().equals(from)) {
			if (pathEntity.getNavigation().isInProgress()) {
				pathEntity.getNavigation().stop();
				to = pathEntity.getNavigation().createPath(to.getTarget(), 0);
				pathEntity.getNavigation().moveTo(to, speedMultiplier);
			}
		}
	}
	
	public static void divertPath(MobEntity pathEntity, Path from, Path to) {
		divertPath(pathEntity, from, to, 1);
	}
	
	public static void printTargetCoordsOut(PathPoint target) {
		ChaosAwakens.LOGGER.debug("[TARGET POS]: ", target.x + " " + target.y + " " + target.z);
	}
	
	@Override
	public String toString() {
		//Avoid crashes
		if (this.getNextNodeIndex() < this.getNodeCount()) {
			return ChaosAwakens.MODNAME + " Path: " + "[length = " + this.getNodeCount() + ", start pos = " + this.getNodePos(0) + ", next target pos = " + this.getNextNodePos().toString() + ", final destination pos = " + this.getEndNode().asBlockPos().toString() + "]";
		}
		return "NULL OR INVALID PATH";
	}

}
