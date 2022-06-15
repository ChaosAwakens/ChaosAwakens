package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.items.extended.QueenScaleBattleAxeItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class QueenScaleBattleAxeItemModel extends AnimatedGeoModel<QueenScaleBattleAxeItem> {
	@Override
	public ResourceLocation getModelLocation(QueenScaleBattleAxeItem object) {
		return new ResourceLocation(ChaosAwakens.MODID, "geo/queen_scale_battle_axe.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(QueenScaleBattleAxeItem object) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/item/queen_scale_battle_axe_model.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(QueenScaleBattleAxeItem animatable) {
		return new ResourceLocation(ChaosAwakens.MODID, "animations/dummy.animation.json");
	}
}
