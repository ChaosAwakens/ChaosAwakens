package io.github.chaosawakens.client.models.entity.boss.insect;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.boss.insect.HerculesBeetleEntity;
import io.github.chaosawakens.common.util.EnumUtil.HerculesBeetleType;
import net.minecraft.util.ResourceLocation;

public class HerculesBeetleEntityModel extends ExtendedAnimatedTickingGeoModel<HerculesBeetleEntity> {
	private HerculesBeetleType beetleType;
	
	public HerculesBeetleEntityModel(HerculesBeetleType beetleType) {
		super();
		this.beetleType = beetleType;
	}

	@Override
	public ResourceLocation getAnimationFileLocation(HerculesBeetleEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/boss/insect/hercules_beetle.animation.json");
	}
	
	@Override
	public ResourceLocation getModelLocation(HerculesBeetleEntity object) {
		return ChaosAwakens.prefix("geo/entity/boss/insect/hercules_beetle.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(HerculesBeetleEntity object) {
		return ChaosAwakens.prefix("textures/entity/boss/insect/" + beetleType.getName() + "_hercules_beetle.png");
	}

	@Override
	protected boolean shouldApplyHeadRot() {
		return false;
	}

	@Override
	protected boolean shouldApplyChildScaling() {
		return false;
	}

}
