package io.github.chaosawakens.client.models.entity.neutral.land.gator;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.neutral.land.gator.EmeraldGatorEntity;
import net.minecraft.util.ResourceLocation;

public class EmeraldGatorEntityModel extends ExtendedAnimatedTickingGeoModel<EmeraldGatorEntity> {

	@Override
	public ResourceLocation getAnimationFileLocation(EmeraldGatorEntity object) {
		return ChaosAwakens.prefix("animations/entity/neutral/land/gator.animation.json");
	}
	
	@Override
	public ResourceLocation getModelLocation(EmeraldGatorEntity object) {
		return ChaosAwakens.prefix("geo/entity/neutral/land/gator.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(EmeraldGatorEntity object) {
		return ChaosAwakens.prefix("textures/entity/neutral/land/gator/emerald_gator.png");
	}
	
	@Override
	protected boolean shouldApplyHeadRot() {
		return true;
	}

	@Override
	protected boolean shouldApplyChildScaling() {
		return true;
	}
	
	@Override
	protected boolean shouldScaleHeadWithChild() {
		return true;
	}
}
