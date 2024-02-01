package io.github.chaosawakens.common.entity.ai.goals.hostile.herculesbeetle;

import io.github.chaosawakens.common.entity.boss.insect.HerculesBeetleEntity;
import net.minecraft.entity.ai.goal.Goal;

public class HerculesBeetleRepelGoal extends Goal {
	private final HerculesBeetleEntity ownerBeetle;
	
	public HerculesBeetleRepelGoal(HerculesBeetleEntity ownerBeetle) {
		this.ownerBeetle = ownerBeetle;
	}

	@Override
	public boolean canUse() {
		return false;
	}

}
