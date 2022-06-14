package io.github.chaosawakens.common.entity.ai;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.vector.Vector3d;
import net.royawesome.jlibnoise.MathHelper;

public class FlightMovementController extends MovementController{
	protected MobEntity entity;
	private float speedModifier;
	private boolean needsHelpVerticalNav;

	public FlightMovementController(MobEntity entity, float speedModifier, boolean needsHelpVerticalNav) {
		super(entity);
		this.entity = entity;
		this.speedModifier = speedModifier;
		this.needsHelpVerticalNav = needsHelpVerticalNav;
	}
	
	public FlightMovementController(MobEntity entity, float speedModifier) {
		this(entity, speedModifier, true);
		this.entity = entity;
		this.speedModifier = speedModifier;
	}
	
	@Override
	public void tick() {
		if (this.operation == MovementController.Action.MOVE_TO) {
			Vector3d targetPos = new Vector3d(this.getWantedX() - entity.getX(), this.getWantedY() - entity.getY(), this.getWantedZ() - entity.getZ());
			double distance = targetPos.length();
			if (distance < entity.getBoundingBox().getSize()) {
				this.operation = MovementController.Action.WAIT;
				entity.setDeltaMovement(entity.getDeltaMovement().scale(0.5D));
			} else {
				entity.setDeltaMovement(entity.getDeltaMovement().add(targetPos.scale(this.getSpeedModifier() * speedModifier * 0.06 / distance)));
				if (needsHelpVerticalNav) {
					double distanceVertical = this.getWantedY() - entity.getY();
					entity.setDeltaMovement(entity.getDeltaMovement().add(0.0D, entity.getSpeed() * MathHelper.clamp(distanceVertical, -1, 1) * 0.7, 0.0D));
				}
			}
		}
	}
}
