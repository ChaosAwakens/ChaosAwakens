package io.github.chaosawakens.client.models.entity.neutral.land.gator;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.neutral.land.gator.CrystalGatorEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.processor.IBone;

public class CrystalGatorEntityModel extends ExtendedAnimatedTickingGeoModel<CrystalGatorEntity> {

	@Override
	public ResourceLocation getAnimationFileLocation(CrystalGatorEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/neutral/land/gator.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(CrystalGatorEntity animatable) {
		return ChaosAwakens.prefix("geo/entity/neutral/land/gator.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(CrystalGatorEntity animatable) {
		switch (animatable.getCrystalGatorType()) {
		default: return ChaosAwakens.prefix("textures/entity/neutral/land/gator/green_crystal_gator.png");
		case 1: return ChaosAwakens.prefix("textures/entity/neutral/land/gator/red_crystal_gator.png");
		case 2: return ChaosAwakens.prefix("textures/entity/neutral/land/gator/yellow_crystal_gator.png");
		case 3: return ChaosAwakens.prefix("textures/entity/neutral/land/gator/orange_crystal_gator.png");
		case 4: return ChaosAwakens.prefix("textures/entity/neutral/land/gator/pink_crystal_gator.png");
		case 5: return ChaosAwakens.prefix("textures/entity/neutral/land/gator/blue_crystal_gator.png");
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
	
	@Override
	protected boolean shouldScaleHeadWithChild() {
		return true;
	}
	
	@Override
	public IBone getBodyBone() {
		return getAnimationProcessor().getBone("body");
	}
}
