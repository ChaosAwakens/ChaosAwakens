package io.github.chaosawakens.client.models.entity.hostile.robo;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.hostile.robo.RoboPounderEntity;
import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.AnimationProcessor;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class RoboPounderEntityModel extends ExtendedAnimatedTickingGeoModel<RoboPounderEntity> {

	@Override
	public ResourceLocation getAnimationFileLocation(RoboPounderEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/hostile/robo/robo_pounder.animation.json");
	}
	
	@Override
	public ResourceLocation getModelLocation(RoboPounderEntity object) {
		return ChaosAwakens.prefix("geo/entity/hostile/robo/robo_pounder.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(RoboPounderEntity object) {
		return ChaosAwakens.prefix("textures/entity/hostile/robo/robo_pounder.png");
	}

	@Override
	protected boolean shouldApplyHeadRot() {
		return true;
	}

	@Override
	protected boolean shouldApplyChildScaling() {
		return false;
	}
	
	@Override
	public void applyHeadRotations(AnimationProcessor<?> targetProcessor, AnimationEvent<?> customPredicate) {
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		IBone head = targetProcessor.getBone("Head");
		
		if (ObjectUtil.performNullityChecks(false, extraData, head)) {
			head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
			head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
		}
	}
}
