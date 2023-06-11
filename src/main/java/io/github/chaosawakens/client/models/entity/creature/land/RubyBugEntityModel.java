package io.github.chaosawakens.client.models.entity.creature.land;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.land.RubyBugEntity;
import net.minecraft.util.ResourceLocation;

public class RubyBugEntityModel extends ExtendedAnimatedTickingGeoModel<RubyBugEntity> {
	
	@Override
	public ResourceLocation getAnimationFileLocation(RubyBugEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/creature/land/ruby_bug.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(RubyBugEntity object) {
		return ChaosAwakens.prefix("geo/entity/creature/land/ruby_bug.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(RubyBugEntity object) {
		return ChaosAwakens.prefix("textures/entity/creature/land/ruby_bug.png");
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
