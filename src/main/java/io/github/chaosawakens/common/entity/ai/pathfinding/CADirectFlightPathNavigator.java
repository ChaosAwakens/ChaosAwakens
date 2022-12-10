package io.github.chaosawakens.common.entity.ai.pathfinding;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class CADirectFlightPathNavigator extends FlyingPathNavigator {

	public CADirectFlightPathNavigator(MobEntity owner, World world) {
		super(owner, world);
	}	
	
	@Override
	protected void followThePath() {
		
		
		
		doStuckDetection(getTempMobPos());
	}
	
	private boolean checkNodes(Vector3d centerPos) {
		Path path = getPath();
		if (path == null) return false;
		
		
		
		return true;
	}
	
	private void tryTruncateNodesBudgetEdition() {
		
	}
}
