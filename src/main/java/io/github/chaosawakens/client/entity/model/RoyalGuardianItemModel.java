package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.items.ExtendedHitWeaponItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RoyalGuardianItemModel extends AnimatedGeoModel<ExtendedHitWeaponItem> {
	@Override
	public ResourceLocation getModelLocation(ExtendedHitWeaponItem object) {
		return new ResourceLocation(ChaosAwakens.MODID, "geo/royal_guardian_sword.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ExtendedHitWeaponItem object) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/item/royal_guardian_sword_model.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ExtendedHitWeaponItem animatable) {
		return new ResourceLocation(ChaosAwakens.MODID, "animations/dummy.animation.json");
	}
}
