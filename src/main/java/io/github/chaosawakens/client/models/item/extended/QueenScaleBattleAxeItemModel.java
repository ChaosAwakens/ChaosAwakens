package io.github.chaosawakens.client.models.item.extended;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.item.base.DummyAnimatedGeoModel;
import io.github.chaosawakens.common.items.weapons.extended.QueenScaleBattleAxeItem;
import net.minecraft.util.ResourceLocation;

public class QueenScaleBattleAxeItemModel extends DummyAnimatedGeoModel<QueenScaleBattleAxeItem> {
	
	@Override
	public ResourceLocation getModelLocation(QueenScaleBattleAxeItem object) {
		return ChaosAwakens.prefix("geo/item/extended/queen_scale_battle_axe.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(QueenScaleBattleAxeItem object) {
		return ChaosAwakens.prefix("textures/item/queen_scale_battle_axe_model.png");
	}
}
