package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.WaspEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class WaspEntityModel extends AnimatedGeoModel<WaspEntity> {
	
	@Override
	public ResourceLocation getModelLocation(WaspEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "geo/wasp.geo.json");
	}
	
	@Override
	public ResourceLocation getTextureLocation(WaspEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/wasp.png");
	}
	
	@Override
	public ResourceLocation getAnimationFileLocation(WaspEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "animations/wasp.animation.json");
	}
	
	@Override
	public void setLivingAnimations(WaspEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		
		IBone head = this.getAnimationProcessor().getBone("Head");
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
	}
}