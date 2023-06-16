package io.github.chaosawakens.client.models.entity.hostile.robo;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.hostile.robo.RoboPounderEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.processor.IBone;

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
	public IBone getHeadBone() {
		return getAnimationProcessor().getBone("Head");
	}
}
