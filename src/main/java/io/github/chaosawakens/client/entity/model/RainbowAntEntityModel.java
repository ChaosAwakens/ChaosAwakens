package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.RainbowAntEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class RainbowAntEntityModel extends AnimatedGeoModel<RainbowAntEntity> {
	
	@Override
	public ResourceLocation getModelLocation(RainbowAntEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "geo/ant.geo.json");
	}
	
	@Override
	public ResourceLocation getTextureLocation(RainbowAntEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/ant/rainbow_ant.png");
	}
	
	@Override
	public ResourceLocation getAnimationFileLocation(RainbowAntEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "animations/ant.animation.json");
	}
	
	@Override
	public void setLivingAnimations(RainbowAntEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		
		IBone head = this.getAnimationProcessor().getBone("head");
		// ChaosAwakens.LOGGER.debug(entity);
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
	}
}
