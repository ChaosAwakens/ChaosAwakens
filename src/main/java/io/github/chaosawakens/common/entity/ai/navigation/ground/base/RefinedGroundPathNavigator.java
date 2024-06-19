package io.github.chaosawakens.common.entity.ai.navigation.ground.base;

import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.util.EntityUtil;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;

public class RefinedGroundPathNavigator extends GroundPathNavigator {
	private final AnimatableMonsterEntity owner;
	private float distancemodifier = 0.75F;
	private int stuckCounter = 0;

	public RefinedGroundPathNavigator(AnimatableMonsterEntity owner, World worldIn) {
		super(owner, worldIn);
		this.owner = owner;
		adjustDistanceModifier(owner);
	}

	public RefinedGroundPathNavigator(AnimatableMonsterEntity owner, World worldIn, float distancemodifier) {
		super(owner, worldIn);
		this.distancemodifier = distancemodifier;
		this.owner = owner;
		adjustDistanceModifier(owner);
	}

	private void adjustDistanceModifier(MobEntity entity) {
		float width = entity.getBbWidth();
		float height = entity.getBbHeight();

		if (width > 2.0F || height > 2.0F) this.distancemodifier *= 1.5F;
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

		if ((withinHorizontalRange && withinVerticalRange) || hasValidPathType(this.path.getNextNode().type) && shouldTargetNextNodeInDirection(currentPosition)) {
			this.path.advance();
		}

		doStuckDetection(currentPosition);
	}

	@Override
	protected void doStuckDetection(Vector3d currentPosition) {
		if (owner.isStuck()) stuckCounter++;

		if (stuckCounter >= 100) {
			this.stuckCounter = 0;
			stop();
			recomputePath();
		}
	}

	private boolean shouldTargetNextNodeInDirection(Vector3d currentPosition) {
		if (this.path.getNextNodeIndex() + 1 >= this.path.getNodeCount()) {
			return false;
		} else {

		}
		return true;
	}
}