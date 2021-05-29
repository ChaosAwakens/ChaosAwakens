package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.BeaverEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BeaverEntityModel extends AnimatedGeoModel<BeaverEntity> {
	
	@Override
	public ResourceLocation getModelLocation(BeaverEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "geo/beaver.geo.json");
	}
	
	@Override
	public ResourceLocation getTextureLocation(BeaverEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/beaver.png");
	}
	
	@Override
	public ResourceLocation getAnimationFileLocation(BeaverEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "animations/beaver.animation.json");
	}
	
	@Override
	public void setLivingAnimations(BeaverEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		
		IBone head = this.getAnimationProcessor().getBone("head");
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
	}
}