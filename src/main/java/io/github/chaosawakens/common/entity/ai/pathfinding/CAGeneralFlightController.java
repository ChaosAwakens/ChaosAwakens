package io.github.chaosawakens.common.entity.ai.pathfinding;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;

import static net.minecraft.entity.ai.controller.MovementController.Action.MOVE_TO;
import static net.minecraft.entity.ai.controller.MovementController.Action.WAIT;

public class CAGeneralFlightController extends MovementController {
	private final boolean shouldAlwaysFaceTarget;
	private final boolean shouldAlwaysMove;
	private final boolean shouldStrafeAround;
	private FlightMode mode;

	public CAGeneralFlightController(MobEntity owner, double speedModifier, boolean shouldAlwaysFaceTarget, boolean shouldAlwaysMove, boolean shouldStrafeAround) {
		super(owner);
		this.speedModifier = speedModifier;
		this.shouldAlwaysFaceTarget = shouldAlwaysFaceTarget;
		this.shouldAlwaysMove = shouldAlwaysMove;
		this.shouldStrafeAround = shouldStrafeAround;
	}
	
	@Override
	public void tick() {
		if (operation == MOVE_TO) {
			Vector3d distanceVector = new Vector3d(wantedX - mob.getX(), wantedY - mob.getY(), wantedZ - mob.getZ());
			Path path = mob.getNavigation().getPath();
			if (path == null) mob.getNavigation().createPath(wantedX, wantedY, wantedZ, 1);
			
			if (distanceVector.length() < mob.getBoundingBox().getSize()) {
				operation = WAIT;
				mob.setDeltaMovement(mob.getDeltaMovement().scale(0.5D));
			} else mob.setDeltaMovement(mob.getDeltaMovement().add(mob.getDeltaMovement().scale(speedModifier * 0.8F * 0.05D / distanceVector.length())));
			
			if (shouldAlwaysFaceTarget && mob.getTarget() != null && mob.getTarget().isAlive()) mob.lookAt(mob.getTarget(), 100, 100);
			if (shouldAlwaysMove) {
				if ((path != null) && mob.getTarget() != null) {
			//		path = mob.getNavigation().createPath(mob.getTarget(), 1);
					setWantedPosition(path.getTarget().getX(), path.getTarget().getY(), path.getTarget().getZ(), speedModifier);
					mob.getNavigation().moveTo(path, speedModifier);
				}
			}
			
	//		if (mob.horizontalCollision) {
	//			if (!shouldAlwaysFaceTarget) mob.yBodyRot += 180;
	//			mob.getNavigation().stop();
	//		}
			
			if (mode == FlightMode.HOVER) {
				if (!hasCollision(distanceVector, Math.ceil(distanceVector.length()))) mob.setDeltaMovement(mob.getDeltaMovement().scale(0.2D));
			}
		}
	}
	
	private boolean hasCollision(Vector3d path, double distance) {
		AxisAlignedBB collisionBox = mob.getBoundingBox();
		
		for (int offset = 1; offset < distance; offset++) {
			collisionBox = collisionBox.move(path);
			if (mob.level.noCollision(collisionBox)) return true;
		}
		return false;
	}
	
	//TODO W.I.P Implementation
	public enum FlightMode {
		HOVER,
		MOVE
	}
}
