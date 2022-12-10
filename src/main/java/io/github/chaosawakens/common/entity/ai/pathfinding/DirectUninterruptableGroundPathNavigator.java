package io.github.chaosawakens.common.entity.ai.pathfinding;

import net.minecraft.entity.Entity;
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
		mob.getMoveControl().setWantedPosition(x, y, z, 1);
		return true;
	}
	
	@Override
	public boolean moveTo(Entity target, double speedModifier) {
		mob.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), speedModifier);
		return true;
	}
	
	@Override
	public boolean moveTo(double x, double y, double z, double speedModifier) {
		mob.getMoveControl().setWantedPosition(x, y, z, speedModifier);
		return true;
	}
	
	@Override
	public void tick() {
		++tick;
	}
}
