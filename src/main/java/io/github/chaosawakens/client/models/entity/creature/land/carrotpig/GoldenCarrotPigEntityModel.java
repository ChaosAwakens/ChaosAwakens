package io.github.chaosawakens.client.models.entity.creature.land.carrotpig;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.land.carrotpig.GoldenCarrotPigEntity;
import net.minecraft.util.ResourceLocation;

public class GoldenCarrotPigEntityModel extends ExtendedAnimatedTickingGeoModel<GoldenCarrotPigEntity> {
	
	@Override
	public ResourceLocation getAnimationFileLocation(GoldenCarrotPigEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/creature/land/carrot_pig.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(GoldenCarrotPigEntity object) {
		return ChaosAwakens.prefix("geo/entity/land/carrot_pig.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(GoldenCarrotPigEntity object) {
		return ChaosAwakens.prefix("textures/entity/creature/land/carrot_pig/golden_carrot_pig.png");
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
