package io.github.chaosawakens.common.entity.ai.navigation.ground.base;

import io.github.chaosawakens.common.entity.ai.pathfinding.base.BandaidPathfinder;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RefinedGroundPathNavigator extends GroundPathNavigator {
	protected final AnimatableMonsterEntity owner;
	protected float distancemodifier = 0.75F;
	protected int stuckCounter = 0;
	protected BandaidPathfinder bandaidPathfinder;
	protected BlockPos targetPos;
	protected int reachRange;

	public RefinedGroundPathNavigator(AnimatableMonsterEntity owner, World worldIn) {
		super(owner, worldIn);
		this.owner = owner;
		this.bandaidPathfinder = createPathFinder((int) Math.floor(owner.getFollowRange() * 16.0D));
		adjustDistanceModifier(owner);
	}

	public RefinedGroundPathNavigator(AnimatableMonsterEntity owner, World worldIn, float distancemodifier) {
		super(owner, worldIn);
		this.distancemodifier = distancemodifier;
		this.owner = owner;
		adjustDistanceModifier(owner);
	}

	@Override
	protected BandaidPathfinder createPathFinder(int pMaxVisitedNodes) {
		this.nodeEvaluator = new WalkNodeProcessor();
		this.nodeEvaluator.setCanPassDoors(true);
		return new BandaidPathfinder(this.nodeEvaluator, pMaxVisitedNodes);
	}

	private void adjustDistanceModifier(MobEntity entity) {
		float width = entity.getBbWidth();
		float height = entity.getBbHeight();

		if (width > 2.0F || height > 2.0F) this.distancemodifier *= 1.5F;
	}

/*	@Override
	public void recomputePath() {
		if (this.level.getGameTime() - this.timeLastRecompute > 20L) {
			if (this.targetPos != null) {
				this.path = null;
				this.path = createPath(this.targetPos, this.reachRange);
				this.timeLastRecompute = this.level.getGameTime();
				this.hasDelayedRecomputation = false;
			}
		} else this.hasDelayedRecomputation = true;
	}

	@Nullable
	@Override
	protected Path createPath(Set<BlockPos> pTargets, int pRegionOffset, boolean pOffsetUpward, int pAccuracy) {
		return createPath(pTargets, pRegionOffset, pOffsetUpward, pAccuracy, 2.0F);
	}

	@Nullable
	protected Path createPath(Set<BlockPos> pTargets, int pRegionOffset, boolean pOffsetUpward, int pAccuracy, float pathInflationBias) {
		if (pTargets.isEmpty() || this.owner.getY() < 0.0D || !canUpdatePath()) return null;
		else if (this.path != null && !this.path.isDone() && pTargets.contains(getTargetPos())) return this.path;
		else {
			this.level.getProfiler().push("pathfind");

			double followRange = owner.getFollowRange();
			BlockPos ownerPos = pOffsetUpward ? this.owner.blockPosition().above() : this.owner.blockPosition();
			int offsetFollowRange = (int) (followRange + pRegionOffset);
			Region pathfindingRegion = new Region(this.level, ownerPos.offset(-offsetFollowRange, -offsetFollowRange, -offsetFollowRange), ownerPos.offset(offsetFollowRange, offsetFollowRange, offsetFollowRange));
			Path finalPath = this.bandaidPathfinder.findPath(pathfindingRegion, this.owner, pTargets, (float) followRange, pAccuracy, 1.0F, pathInflationBias);

			this.level.getProfiler().pop();

			if (finalPath != null && finalPath.getTarget() != null) {
				this.targetPos = finalPath.getTarget();
				this.reachRange = pAccuracy;

				resetStuckTimeout();
			}

			return finalPath;
		}
	}

	private void resetStuckTimeout() {
		this.timeoutCachedNode = Vector3i.ZERO;
		this.timeoutTimer = 0L;
		this.timeoutLimit = 0.0D;
		this.stuckCounter = 0;
	}

	@Override
	protected void followThePath() {
		Vector3d currentPosition = getTempMobPos();
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
		super.doStuckDetection(currentPosition);
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
	} */
}