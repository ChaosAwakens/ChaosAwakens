package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.HerculesBeetleEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class HerculesBeetleEntityModel extends AnimatedTickingGeoModel<HerculesBeetleEntity> {
	@Override
	public ResourceLocation getModelLocation(HerculesBeetleEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "geo/hercules_beetle.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(HerculesBeetleEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/hercules_beetle/hercules_beetle.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(HerculesBeetleEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "animations/hercules_beetle.animation.json");
	}

	@Override
	public void setLivingAnimations(HerculesBeetleEntity entity, Integer uniqueID) {
		super.setLivingAnimations(entity, uniqueID);
	}
}
