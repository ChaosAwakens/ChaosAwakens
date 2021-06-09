package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.StinkBugEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class StinkBugEntityModel extends AnimatedGeoModel<StinkBugEntity> {
	
	@Override
	public ResourceLocation getModelLocation(StinkBugEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "geo/stink_bug.geo.json");
	}
	
	@Override
	public ResourceLocation getTextureLocation(StinkBugEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/stink_bug.png");
	}
	
	@Override
	public ResourceLocation getAnimationFileLocation(StinkBugEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "animations/stink_bug.animation.json");
	}
}
