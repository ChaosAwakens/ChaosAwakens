package io.github.chaosawakens.client.models.entity.creature.water.fish;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.water.fish.WoodFishEntity;
import net.minecraft.util.ResourceLocation;

public class WoodFishEntityModel extends ExtendedAnimatedTickingGeoModel<WoodFishEntity> {
	
	@Override
	public ResourceLocation getAnimationFileLocation(WoodFishEntity object) {
		return ChaosAwakens.prefix("animations/entity/creature/water/fish/wood_fish.animation.json");
	}
	
	@Override
	public ResourceLocation getModelLocation(WoodFishEntity object) {
		return ChaosAwakens.prefix("geo/entity/creature/water/fish/wood_fish.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(WoodFishEntity object) {
		return ChaosAwakens.prefix("textures/entity/creature/water/fish/wood_fish.png");
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
