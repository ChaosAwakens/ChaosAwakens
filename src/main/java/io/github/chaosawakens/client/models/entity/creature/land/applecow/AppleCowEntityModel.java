package io.github.chaosawakens.client.models.entity.creature.land.applecow;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.land.applecow.AppleCowEntity;
import net.minecraft.util.ResourceLocation;

public class AppleCowEntityModel extends ExtendedAnimatedTickingGeoModel<AppleCowEntity> {

	@Override
	public ResourceLocation getAnimationFileLocation(AppleCowEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/creature/land/apple_cow.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(AppleCowEntity animatable) {
		return ChaosAwakens.prefix("geo/entity/land/apple_cow.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(AppleCowEntity animatable) {
		switch (animatable.getAppleCowType()) {
		default: return ChaosAwakens.prefix("textures/entity/creature/land/apple_cow/apple_cow.png");
		case 1: return ChaosAwakens.prefix("textures/entity/creature/land/apple_cow/halloween_apple_cow.png");
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