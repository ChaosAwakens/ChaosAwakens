package io.github.chaosawakens.client.models.entity.boss.robo;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.boss.robo.RoboJefferyEntity;
import net.minecraft.util.ResourceLocation;

public class RoboJefferyEntityModel extends ExtendedAnimatedTickingGeoModel<RoboJefferyEntity> {
	
	@Override
	public ResourceLocation getAnimationFileLocation(RoboJefferyEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/boss/robo/robo_jeffery.animation.json");
	}
	
	@Override
	public ResourceLocation getModelLocation(RoboJefferyEntity object) {
		return ChaosAwakens.prefix("geo/entity/boss/robo/robo_jeffery.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(RoboJefferyEntity object) {
		return ChaosAwakens.prefix("textures/entity/boss/robo/robo_jeffery.png");
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
