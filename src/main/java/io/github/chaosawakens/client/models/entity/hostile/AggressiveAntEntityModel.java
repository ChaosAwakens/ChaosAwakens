package io.github.chaosawakens.client.models.entity.hostile;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.hostile.AggressiveAntEntity;
import net.minecraft.util.ResourceLocation;

public class AggressiveAntEntityModel extends ExtendedAnimatedTickingGeoModel<AggressiveAntEntity> {
	private final String textureName;

	public AggressiveAntEntityModel(String textureName) {
		this.textureName = textureName;
	}
	
	@Override
	public ResourceLocation getAnimationFileLocation(AggressiveAntEntity object) {
		return ChaosAwakens.prefix("animations/entity/land/ant.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(AggressiveAntEntity object) {
		return ChaosAwakens.prefix("geo/entity/creature/land/ant.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(AggressiveAntEntity object) {
		return ChaosAwakens.prefix("textures/entity/creature/land/ant/" + textureName + ".png");
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
