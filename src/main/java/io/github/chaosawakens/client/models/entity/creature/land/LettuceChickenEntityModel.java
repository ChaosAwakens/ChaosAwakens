package io.github.chaosawakens.client.models.entity.creature.land;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.land.LettuceChickenEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.processor.IBone;

public class LettuceChickenEntityModel extends ExtendedAnimatedTickingGeoModel<LettuceChickenEntity> {

	@Override
	public ResourceLocation getAnimationFileLocation(LettuceChickenEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/creature/land/lettuce_chicken.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(LettuceChickenEntity animatable) {
		return ChaosAwakens.prefix("geo/entity/creature/land/lettuce_chicken.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(LettuceChickenEntity animatable) {
		return ChaosAwakens.prefix("textures/entity/creature/land/lettuce_chicken.png");
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
