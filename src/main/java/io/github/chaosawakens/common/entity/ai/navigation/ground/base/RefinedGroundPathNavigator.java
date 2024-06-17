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
		adjustDistanceModifier(entitylivingIn);
	}

	public RefinedGroundPathNavigator(MobEntity entitylivingIn, World worldIn, float distancemodifier) {
		super(entitylivingIn, worldIn);
		this.distancemodifier = distancemodifier;
	}

	private void adjustDistanceModifier(MobEntity entity) {
		float width = entity.getBbWidth();
		float height = entity.getBbHeight();

		if (width > 2.0F || height > 2.0F) this.distancemodifier *= 1.5F; //TODO Arbitrary value moment, but it works here and this code ain't being touched again, soooooo...
	}

	@Override
	protected void followThePath() {
		Vector3d currentPosition = this.getTempMobPos();
		this.maxDistanceToWaypoint = this.mob.getBbWidth() * this.distancemodifier;

		Vector3i nextNodePos = this.path.getNextNodePos();
		double dx = Math.abs(this.mob.getX() - (nextNodePos.getX() + 0.5D));
		double dy = Math.abs(this.mob.getY() - nextNodePos.getY());
		double dz = Math.abs(this.mob.getZ() - (nextNodePos.getZ() + 0.5D));
		boolean withinHorizontalRange = dx < this.maxDistanceToWaypoint && dz < this.maxDistanceToWaypoint;
		boolean withinVerticalRange = dy < 1.0D;

		if ((withinHorizontalRange && withinVerticalRange) || this.hasValidPathType(this.path.getNextNode().type) && this.shouldTargetNextNodeInDirection(currentPosition)) {
			this.path.advance();
		}

		this.doStuckDetection(currentPosition);
	}

	@Override
	protected void doStuckDetection(Vector3d currentPosition) {

	}

	private boolean shouldTargetNextNodeInDirection(Vector3d currentPosition) {
		if (this.path.getNextNodeIndex() + 1 >= this.path.getNodeCount()) {
			return false;
		} else {

		}
		return true;
	}
}