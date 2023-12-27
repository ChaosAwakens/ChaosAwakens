package io.github.chaosawakens.client.models.entity.hostile.robo;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.hostile.robo.RoboSniperEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.processor.IBone;

public class RoboSniperEntityModel extends ExtendedAnimatedTickingGeoModel<RoboSniperEntity> {
	
	@Override
	public ResourceLocation getAnimationFileLocation(RoboSniperEntity entity) {
		return ChaosAwakens.prefix("animations/entity/hostile/robo/robo_sniper.animation.json");
	}
	
	@Override
	public ResourceLocation getModelLocation(RoboSniperEntity entity) {
		return ChaosAwakens.prefix("geo/entity/hostile/robo/robo_sniper.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(RoboSniperEntity entity) {
		return ChaosAwakens.prefix("textures/entity/hostile/robo/robo_sniper.png");
	}
	
	@Override
	public IBone getHeadBone() {
		return getAnimationProcessor().getBone("turret");
	}

	@Override
	protected boolean shouldApplyHeadRot() {
		return true;
	}

	@Override
	protected boolean shouldApplyChildScaling() {
		return false;
	}
}
