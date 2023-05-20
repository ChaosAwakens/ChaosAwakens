package io.github.chaosawakens.client.models.entity.creature.land.applecow;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.land.applecow.CrystalAppleCowEntity;
import net.minecraft.util.ResourceLocation;

public class CrystalAppleCowEntityModel extends ExtendedAnimatedTickingGeoModel<CrystalAppleCowEntity> {
	
	@Override
	public ResourceLocation getAnimationFileLocation(CrystalAppleCowEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/creature/land/apple_cow.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(CrystalAppleCowEntity object) {
		return ChaosAwakens.prefix("geo/entity/land/apple_cow.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(CrystalAppleCowEntity object) {
		return ChaosAwakens.prefix("textures/entity/creature/land/apple_cow/crystal_apple_cow.png");
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
