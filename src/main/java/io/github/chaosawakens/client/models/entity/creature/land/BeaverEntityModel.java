package io.github.chaosawakens.client.models.entity.creature.land;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.land.BeaverEntity;
import net.minecraft.util.ResourceLocation;

public class BeaverEntityModel extends ExtendedAnimatedTickingGeoModel<BeaverEntity> {
	
	@Override
	public ResourceLocation getAnimationFileLocation(BeaverEntity object) {
		return ChaosAwakens.prefix("animations/entity/creature/land/beaver.animation.json");
	}
	
	@Override
	public ResourceLocation getModelLocation(BeaverEntity object) {
		return ChaosAwakens.prefix("geo/entity/creature/land/beaver.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(BeaverEntity object) {
		return ChaosAwakens.prefix("textures/entity/creature/land/beaver.png");
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
