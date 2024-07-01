package io.github.chaosawakens.client.models.entity.creature.land.carrotpig;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.land.carrotpig.CrystalCarrotPigEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.processor.IBone;

public class CrystalCarrotPigEntityModel extends ExtendedAnimatedTickingGeoModel<CrystalCarrotPigEntity> {
	
	@Override
	public ResourceLocation getAnimationFileLocation(CrystalCarrotPigEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/creature/land/carrot_pig.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(CrystalCarrotPigEntity object) {
		return ChaosAwakens.prefix("geo/entity/creature/land/carrot_pig.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(CrystalCarrotPigEntity object) {
		return ChaosAwakens.prefix("textures/entity/creature/land/carrot_pig/crystal_carrot_pig.png");
	}

	@Override
	public IBone getBodyBone() {
		return getAnimationProcessor().getBone("body");
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
