package io.github.chaosawakens.common.entity.ai;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.controller.BodyController;

public class CABodyController extends BodyController {

	public CABodyController(MobEntity entity) {
		super(entity);
	}
	
	@Override
	public void clientTick() {
		super.clientTick();
	}

}
