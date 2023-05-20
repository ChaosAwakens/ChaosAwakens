package io.github.chaosawakens.client.models.entity.creature.land.carrotpig;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.land.carrotpig.CarrotPigEntity;
import net.minecraft.util.ResourceLocation;

public class CarrotPigEntityModel extends ExtendedAnimatedTickingGeoModel<CarrotPigEntity> {
	
	@Override
	public ResourceLocation getAnimationFileLocation(CarrotPigEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/creature/land/carrot_pig.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(CarrotPigEntity object) {
		return ChaosAwakens.prefix("geo/entity/land/carrot_pig.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(CarrotPigEntity object) {
		return ChaosAwakens.prefix("textures/entity/creature/land/carrot_pig/carrot_pig.png");
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
