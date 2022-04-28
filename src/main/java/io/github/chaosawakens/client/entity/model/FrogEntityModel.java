package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.FrogEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class FrogEntityModel extends AnimatedGeoModel<FrogEntity> {
	@Override
	public ResourceLocation getModelLocation(FrogEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "geo/frog.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(FrogEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/frog/green.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(FrogEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "animations/frog.animation.json");
	}

	@Override
	public void setLivingAnimations(FrogEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		IBone head = this.getAnimationProcessor().getBone("head");
		head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
	}
}
