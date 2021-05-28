package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.EntEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class EntEntityModel extends AnimatedGeoModel<EntEntity> {
	
	@Override
	public ResourceLocation getModelLocation(EntEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "geo/ent.geo.json");
	}
	
	@Override
	public ResourceLocation getTextureLocation(EntEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/ent.png");
	}
	
	@Override
	public ResourceLocation getAnimationFileLocation(EntEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "animations/ent.animation.json");
	}
	
	@Override
	public void setLivingAnimations(EntEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		
		IBone head = this.getAnimationProcessor().getBone("Head");
		//ChaosAwakens.LOGGER.debug(entity);
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
	}
}