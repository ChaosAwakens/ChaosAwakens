package io.github.chaosawakens.client.models.entity.creature.water;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.water.WhaleEntity;
import net.minecraft.util.ResourceLocation;

public class WhaleEntityModel extends ExtendedAnimatedTickingGeoModel<WhaleEntity> {
	
	@Override
	public ResourceLocation getAnimationFileLocation(WhaleEntity object) {
		return ChaosAwakens.prefix("animations/entity/creature/water/whale.animation.json");
	}
	
	@Override
	public ResourceLocation getModelLocation(WhaleEntity object) {
		return ChaosAwakens.prefix("geo/entity/creature/water/whale.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(WhaleEntity object) {
		return ChaosAwakens.prefix("textures/entity/creature/water/whale.png");
	}

	@Override
	protected boolean shouldApplyHeadRot() {
		return false;
	}

	@Override
	protected boolean shouldApplyChildScaling() {
		return false;
	}
}
