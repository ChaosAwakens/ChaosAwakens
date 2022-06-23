package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.items.extended.RoyalGuardianSwordItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RoyalGuardianSwordItemModel extends AnimatedGeoModel<RoyalGuardianSwordItem> {
	@Override
	public ResourceLocation getModelLocation(RoyalGuardianSwordItem object) {
		return new ResourceLocation(ChaosAwakens.MODID, "geo/royal_guardian_sword.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(RoyalGuardianSwordItem object) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/item/royal_guardian_sword_model.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(RoyalGuardianSwordItem animatable) {
		return new ResourceLocation(ChaosAwakens.MODID, "animations/dummy.animation.json");
	}
}