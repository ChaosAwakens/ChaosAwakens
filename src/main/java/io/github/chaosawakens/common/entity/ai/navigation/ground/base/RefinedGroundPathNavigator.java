package io.github.chaosawakens.common.entity.ai.navigation.ground.base;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;

public class RefinedGroundPathNavigator extends GroundPathNavigator {

	private float distancemodifier = 0.75F;

	public RefinedGroundPathNavigator(MobEntity entitylivingIn, World worldIn) {
		super(entitylivingIn, worldIn);
	}

	public RefinedGroundPathNavigator(MobEntity entitylivingIn, World worldIn, float distancemodifier) {
		super(entitylivingIn, worldIn);
		this.distancemodifier = distancemodifier;
	}

	protected void followThePath() {
		Vector3d vector3d = this.getTempMobPos();
		this.maxDistanceToWaypoint = this.mob.getBbWidth() * distancemodifier;
		Vector3i vector3i = this.path.getNextNodePos();
		double d0 = Math.abs(this.mob.getX() - ((double)vector3i.getX() + 0.5D));
		double d1 = Math.abs(this.mob.getY() - (double)vector3i.getY());
		double d2 = Math.abs(this.mob.getZ() - ((double)vector3i.getZ() + 0.5D));
		boolean flag = d0 < (double)this.maxDistanceToWaypoint && d2 < (double)this.maxDistanceToWaypoint && d1 < 1.0D;
		if (flag || this.hasValidPathType(this.path.getNextNode().type) && this.shouldTargetNextNodeInDirection(vector3d)) {
			this.path.advance();
		}

		this.doStuckDetection(vector3d);
	}

	private boolean shouldTargetNextNodeInDirection(Vector3d currentPosition) {
		if (this.path.getNextNodeIndex() + 1 >= this.path.getNodeCount()) {
			return false;
		} else {
			Vector3d vector3d = Vector3d.atBottomCenterOf(this.path.getNextNodePos());
			if (!currentPosition.closerThan(vector3d, 2.0D)) {
				return false;
			} else {
				Vector3d vector3d1 = Vector3d.atBottomCenterOf(this.path.getNodePos(this.path.getNextNodeIndex() + 1));
				Vector3d vector3d2 = vector3d1.subtract(vector3d);
				Vector3d vector3d3 = currentPosition.subtract(vector3d);
				return vector3d2.dot(vector3d3) > 0.0D;
			}
		}
	}
}