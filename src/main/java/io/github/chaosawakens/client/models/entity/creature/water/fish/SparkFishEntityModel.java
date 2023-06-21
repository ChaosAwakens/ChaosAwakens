package io.github.chaosawakens.client.models.entity.creature.water.fish;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.water.fish.SparkFishEntity;
import net.minecraft.util.ResourceLocation;

public class SparkFishEntityModel extends ExtendedAnimatedTickingGeoModel<SparkFishEntity> {
	
	@Override
	public ResourceLocation getAnimationFileLocation(SparkFishEntity object) {
		return ChaosAwakens.prefix("animations/entity/creature/water/fish/spark_fish.animation.json");
	}
	
	@Override
	public ResourceLocation getModelLocation(SparkFishEntity object) {
		return ChaosAwakens.prefix("geo/entity/creature/water/fish/spark_fish.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SparkFishEntity object) {
		return ChaosAwakens.prefix("textures/entity/creature/water/fish/spark_fish.png");
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
