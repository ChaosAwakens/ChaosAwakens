package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.robo.RoboPounderEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class RoboPounderEntityModel extends AnimatedTickingGeoModel<RoboPounderEntity> {
	@Override
	public ResourceLocation getModelLocation(RoboPounderEntity entity) {
		return new ResourceLocation(ChaosAwakens.MODID, "geo/robo_pounder.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(RoboPounderEntity entity) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/robos/robo_pounder.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(RoboPounderEntity entity) {
		return new ResourceLocation(ChaosAwakens.MODID, "animations/robo_pounder.animation.json");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setLivingAnimations(RoboPounderEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent event) {
		super.setLivingAnimations(entity, uniqueID, event);

		IBone head = this.getAnimationProcessor().getBone("Head");
		EntityModelData extraData = (EntityModelData) event.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
	}
}
