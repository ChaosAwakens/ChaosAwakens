package io.github.chaosawakens.client.models.entity.creature.land;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.land.AntEntity;
import net.minecraft.util.ResourceLocation;

public class AntEntityModel extends ExtendedAnimatedTickingGeoModel<AntEntity> {
	private final String textureName;

	public AntEntityModel(String textureName) {
		this.textureName = textureName;
	}

	@Override
	public ResourceLocation getAnimationFileLocation(AntEntity object) {
		return ChaosAwakens.prefix("animations/entity/land/ant.animation.json");
	}
	
	@Override
	public ResourceLocation getModelLocation(AntEntity object) {
		return ChaosAwakens.prefix("geo/entity/creature/land/ant.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(AntEntity object) {
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
