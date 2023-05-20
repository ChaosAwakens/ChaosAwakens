package io.github.chaosawakens.client.models.entity.creature.water.fish;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.water.fish.RockFishEntity;
import net.minecraft.util.ResourceLocation;

public class RockFishEntityModel extends ExtendedAnimatedTickingGeoModel<RockFishEntity> {

	@Override
	public ResourceLocation getAnimationFileLocation(RockFishEntity object) {
		return ChaosAwakens.prefix("animations/entity/creature/water/fish/rock_fish.animation.json");
	}
	
	@Override
	public ResourceLocation getModelLocation(RockFishEntity object) {
		return ChaosAwakens.prefix("geo/entity/creature/water/fish/rock_fish.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(RockFishEntity object) {
		return ChaosAwakens.prefix("textures/entity/creature/water/fish/rock_fish.png");
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
