package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.items.ExtendedHitWeaponItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BigBerthaItemModel extends AnimatedGeoModel<ExtendedHitWeaponItem> {
	@Override
	public ResourceLocation getModelLocation(ExtendedHitWeaponItem object) {
		return new ResourceLocation(ChaosAwakens.MODID, "geo/big_bertha.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ExtendedHitWeaponItem object) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/item/big_bertha_model.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ExtendedHitWeaponItem animatable) {
		return new ResourceLocation(ChaosAwakens.MODID, "animations/dummy.animation.json");
	}
}
