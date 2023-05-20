package io.github.chaosawakens.client.models.entity.neutral.land.dino;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.neutral.land.dino.DimetrodonEntity;
import net.minecraft.util.ResourceLocation;

public class DimetrodonEntityModel extends ExtendedAnimatedTickingGeoModel<DimetrodonEntity> {

	@Override
	public ResourceLocation getAnimationFileLocation(DimetrodonEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/neutral/land/dimetrodon.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(DimetrodonEntity animatable) {
		return ChaosAwakens.prefix("geo/entity/neutral/land/dimetrodon.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(DimetrodonEntity animatable) {
		switch (animatable.getDimetrodonType()) {
		default: return ChaosAwakens.prefix("textures/entity/neutral/land/dimetrodon/green_dimetrodon.png");
		case 1: return ChaosAwakens.prefix("textures/entity/neutral/land/dimetrodon/orange_dimetrodon.png");
		case 2: return ChaosAwakens.prefix("textures/entity/neutral/land/dimetrodon/purple_dimetrodon.png");
		case 3: return ChaosAwakens.prefix("textures/entity/neutral/land/dimetrodon/throwback_dimetrodon.png");
		}
	}

	@Override
	protected boolean shouldApplyHeadRot() {
		return true;
	}

	@Override
	protected boolean shouldApplyChildScaling() {
		return true;
	}
}
