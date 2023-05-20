package io.github.chaosawakens.common.entity.ai.controllers.movement;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.controller.MovementController;

public class SmoothSwimmingController extends MovementController {
	protected SmoothSwimmingController.Action curOperation = SmoothSwimmingController.Action.FLOAT;
	private final boolean shouldKeepHeadAboveWater;

	public SmoothSwimmingController(MobEntity owner, boolean shouldKeepHeadAboveWater) {
		super(owner);
		this.shouldKeepHeadAboveWater = shouldKeepHeadAboveWater;
	}
	
	public SmoothSwimmingController(MobEntity owner) {
		this(owner, true);
	}
	
	public void setSpeedModifier(double speedMod) {
		this.speedModifier = speedMod;
	}
	
	@Override
	public void tick() {
		if (curOperation == SmoothSwimmingController.Action.FLOAT) {
			
		}
	}
	
	public enum Action {
		SWIM,
		FLOAT,
		JUMP,
		ASCEND,
		DESCEND
	}
}
