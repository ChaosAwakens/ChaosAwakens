package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.LavaEelEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LavaEelEntityModel extends AnimatedGeoModel<LavaEelEntity> {

	@Override
	public ResourceLocation getModelLocation(LavaEelEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "geo/lava_eel.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(LavaEelEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/lava_eel.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(LavaEelEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "animations/lava_eel.animation.json");
	}

	@Override
	public void setLivingAnimations(LavaEelEntity entity, Integer uniqueID,
			@SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
	}

}
