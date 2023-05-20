package io.github.chaosawakens.client.models.entity.creature.land.applecow;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.land.applecow.UltimateAppleCowEntity;
import net.minecraft.util.ResourceLocation;

public class UltimateAppleCowEntityModel extends ExtendedAnimatedTickingGeoModel<UltimateAppleCowEntity> {
	
	@Override
	public ResourceLocation getAnimationFileLocation(UltimateAppleCowEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/creature/land/apple_cow.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(UltimateAppleCowEntity object) {
		return ChaosAwakens.prefix("geo/entity/land/apple_cow.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(UltimateAppleCowEntity object) {
		return ChaosAwakens.prefix("textures/entity/creature/land/apple_cow/ultimate_apple_cow.png");
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
