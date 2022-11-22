package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.WhaleEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class WhaleEntityModel extends AnimatedTickingGeoModel<WhaleEntity> {
	@Override
	public ResourceLocation getModelLocation(WhaleEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "geo/whale.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(WhaleEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/whale.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(WhaleEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "animations/whale.animation.json");
	}

	@SuppressWarnings({"unchecked" })
	@Override
	public void setLivingAnimations(WhaleEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		
		IBone head = this.getAnimationProcessor().getBone("Head");
		IBone midSection = this.getAnimationProcessor().getBone("Midsection");
		IBone tail = this.getAnimationProcessor().getBone("Tail");
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
		midSection.setRotationY((head.getRotationY()) * ((float) Math.PI / 270F)); 
		tail.setRotationY((midSection.getRotationY()) * ((float) Math.PI / 270F)); 
	}
}
