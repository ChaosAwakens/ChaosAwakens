package io.github.chaosawakens.client.models.entity.hostile;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.hostile.EntEntity;
import io.github.chaosawakens.common.util.EnumUtil.EntType;
import net.minecraft.util.ResourceLocation;

public class EntEntityModel extends ExtendedAnimatedTickingGeoModel<EntEntity> {
	private EntType entType;
	
	public EntEntityModel(EntType entType) {
		super();
		this.entType = entType;
	}

	@Override
	public ResourceLocation getAnimationFileLocation(EntEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/hostile/ent/ent.animation.json");
	}
	
	@Override
	public ResourceLocation getModelLocation(EntEntity object) {
		// Only Oak Ent geo right now
//		return ChaosAwakens.prefix("geo/entity/hostile/ent/" + entType.getName() + "_ent.geo.json");
		return ChaosAwakens.prefix("geo/entity/hostile/ent/ent.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(EntEntity object) {
		return ChaosAwakens.prefix("textures/entity/hostile/ent/ent_" + entType.getName() + ".png");
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
