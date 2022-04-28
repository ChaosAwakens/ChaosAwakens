package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.robo.RoboSniperEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RoboSniperEntityModel extends AnimatedGeoModel<RoboSniperEntity> {
	@Override
	public ResourceLocation getModelLocation(RoboSniperEntity entity) {
		return new ResourceLocation(ChaosAwakens.MODID, "geo/robo_sniper.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(RoboSniperEntity entity) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/robos/robo_sniper.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(RoboSniperEntity entity) {
		return new ResourceLocation(ChaosAwakens.MODID, "animations/robo_sniper.animation.json");
	}
}
