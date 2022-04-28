package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.items.ExtendedHitAxeItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class QueenScaleBattleAxeItemModel extends AnimatedGeoModel<ExtendedHitAxeItem> {
	@Override
	public ResourceLocation getModelLocation(ExtendedHitAxeItem object) {
		return new ResourceLocation(ChaosAwakens.MODID, "geo/queen_scale_battle_axe.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ExtendedHitAxeItem object) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/item/queen_scale_battle_axe_model.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ExtendedHitAxeItem animatable) {
		return new ResourceLocation(ChaosAwakens.MODID, "animations/dummy.animation.json");
	}
}
