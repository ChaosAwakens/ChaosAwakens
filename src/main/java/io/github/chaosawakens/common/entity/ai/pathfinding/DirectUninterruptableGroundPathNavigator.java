package io.github.chaosawakens.common.entity.ai.pathfinding;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class DirectUninterruptableGroundPathNavigator extends GroundPathNavigator {

	public DirectUninterruptableGroundPathNavigator(MobEntity owner, World world) {
		super(owner, world);
	}
	
	@Override
	protected boolean canMoveDirectly(Vector3d startVec, Vector3d targetVec, int x, int y, int z) {
		return true;
	}

}
