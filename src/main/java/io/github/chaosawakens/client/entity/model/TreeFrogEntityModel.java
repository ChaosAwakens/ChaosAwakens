package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.TreeFrogEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class TreeFrogEntityModel extends AnimatedGeoModel<TreeFrogEntity> {
	@Override
	public ResourceLocation getModelLocation(TreeFrogEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "geo/tree_frog.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(TreeFrogEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/tree_frog/green.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(TreeFrogEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "animations/tree_frog.animation.json");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setLivingAnimations(TreeFrogEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		IBone head = this.getAnimationProcessor().getBone("head");
		head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
	}
}
