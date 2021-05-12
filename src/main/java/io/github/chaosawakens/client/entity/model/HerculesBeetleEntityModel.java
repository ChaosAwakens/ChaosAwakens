package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.entity.HerculesBeetleEntity;
import io.github.chaosawakens.entity.RubyBugEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class HerculesBeetleEntityModel extends AnimatedGeoModel<HerculesBeetleEntity> {
	
	@Override
	public ResourceLocation getModelLocation(HerculesBeetleEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "geo/hercules_beetle.geo.json");
	}
	
	@Override
	public ResourceLocation getTextureLocation(HerculesBeetleEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/hercules_beetle.png");
	}
	
	@Override
	public ResourceLocation getAnimationFileLocation(HerculesBeetleEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "animations/hercules_beetle.animation.json");
	}
}