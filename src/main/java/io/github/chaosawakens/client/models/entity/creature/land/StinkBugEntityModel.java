package io.github.chaosawakens.client.models.entity.creature.land;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.land.StinkBugEntity;
import net.minecraft.util.ResourceLocation;

public class StinkBugEntityModel extends ExtendedAnimatedTickingGeoModel<StinkBugEntity> {
	
	@Override
	public ResourceLocation getAnimationFileLocation(StinkBugEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/creature/land/stink_bug.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(StinkBugEntity animatable) {
		return ChaosAwakens.prefix("geo/entity/land/stink_bug.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(StinkBugEntity animatable) {
		switch (animatable.getStinkBugType()) {
		default: return ChaosAwakens.prefix("textures/entity/creature/land/stink_bug/green_stink_bug.png");
		case 1: return ChaosAwakens.prefix("textures/entity/creature/land/stink_bug/blue_stink_bug.png");
		case 2: return ChaosAwakens.prefix("textures/entity/creature/land/stink_bug/brown_stink_bug.png");
		case 3: return ChaosAwakens.prefix("textures/entity/creature/land/stink_bug/cyan_stink_bug.png");
		case 4: return ChaosAwakens.prefix("textures/entity/creature/land/stink_bug/gray_blue_stink_bug.png");
		case 5: return ChaosAwakens.prefix("textures/entity/creature/land/stink_bug/gray_yellow_stink_bug.png");
		case 6: return ChaosAwakens.prefix("textures/entity/creature/land/stink_bug/throwback_stink_bug.png");
		}
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
