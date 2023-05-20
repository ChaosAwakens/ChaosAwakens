package io.github.chaosawakens.client.models.entity.creature.land.applecow;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.land.applecow.EnchantedGoldenAppleCowEntity;
import net.minecraft.util.ResourceLocation;

public class EnchantedGoldenAppleCowEntityModel extends ExtendedAnimatedTickingGeoModel<EnchantedGoldenAppleCowEntity> {
	
	@Override
	public ResourceLocation getAnimationFileLocation(EnchantedGoldenAppleCowEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/creature/land/apple_cow.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(EnchantedGoldenAppleCowEntity object) {
		return ChaosAwakens.prefix("geo/entity/land/apple_cow.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(EnchantedGoldenAppleCowEntity object) {
		return ChaosAwakens.prefix("textures/entity/creature/land/apple_cow/golden_apple_cow.png");
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
