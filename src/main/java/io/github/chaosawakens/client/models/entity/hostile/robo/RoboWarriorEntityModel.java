package io.github.chaosawakens.client.models.entity.hostile.robo;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.hostile.robo.RoboWarriorEntity;
import net.minecraft.util.ResourceLocation;

public class RoboWarriorEntityModel extends ExtendedAnimatedTickingGeoModel<RoboWarriorEntity> {
	
	@Override
	public ResourceLocation getAnimationFileLocation(RoboWarriorEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/hostile/robo/robo_warrior.animation.json");
	}
	
	@Override
	public ResourceLocation getModelLocation(RoboWarriorEntity object) {
		return ChaosAwakens.prefix("geo/entity/hostile/robo/robo_warrior.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(RoboWarriorEntity object) {
		return ChaosAwakens.prefix("textures/entity/hostile/robo/robo_warrior.png");
	}

	@Override
	protected boolean shouldApplyHeadRot() {
		return true;
	}

	@Override
	protected boolean shouldApplyChildScaling() {
		return false;
	}
}
