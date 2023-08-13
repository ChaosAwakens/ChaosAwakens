package io.github.chaosawakens.client.models.entity.misc;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.misc.JefferyShockwaveEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class JefferyShockwaveEntityModel extends AnimatedTickingGeoModel<JefferyShockwaveEntity> {

	@Override
	public ResourceLocation getAnimationFileLocation(JefferyShockwaveEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/misc/jeffery_shockwave.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(JefferyShockwaveEntity object) {
		return ChaosAwakens.prefix("geo/entity/misc/jeffery_shockwave.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(JefferyShockwaveEntity object) {
		return ChaosAwakens.prefix("textures/entity/misc/jeffery_shockwave.png");
	}
}
