package io.github.chaosawakens.common.entity.ai.controllers.movement;

import io.github.chaosawakens.common.entity.creature.land.TreeFrogEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;

public class TreeFrogMovementController extends MovementController {
	private final TreeFrogEntity owner;
	private float yRot;
	private int jumpDelay;
	private boolean isPanicking;

	public TreeFrogMovementController(TreeFrogEntity owner) {
		super(owner);
		this.owner = owner;
		this.yRot = (float) (180.0F * owner.yRot / Math.PI);
	}

	public void setDirection(float newYrot, boolean isPanicking) {
		this.yRot = newYrot;
		this.isPanicking = isPanicking;
	}

	public void setWantedMovement(double speedModifier) {
		this.speedModifier = speedModifier;
		this.operation = MovementController.Action.MOVE_TO;
	}

	@Override
	public void tick() {
		this.mob.yRot = rotlerp(this.mob.yRot, this.yRot, 180.0F);
		this.mob.yHeadRot = this.mob.yRot;
		this.mob.yBodyRot = this.mob.yRot;
		if (this.operation != MovementController.Action.MOVE_TO) {
			this.mob.setZza(0.0F);
		} else {
			this.operation = MovementController.Action.WAIT;
			if (this.mob.isOnGround()) {
				this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
				if (--jumpDelay <= 0) {
			//		this.jumpDelay = owner.getJumpDelay();
					if (this.isPanicking) {
						this.jumpDelay /= 8;
					}

					owner.getJumpControl().jump();
				//	if (owner.doPlayJumpSound()) {
				///		this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), this.slime.getSoundPitch());
				//	}
				} else {
					owner.xxa = 0.0F;
					owner.zza = 0.0F;
					this.mob.setSpeed(0.0F);
				}
			} else {
				this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
			}
		}
	}
}
