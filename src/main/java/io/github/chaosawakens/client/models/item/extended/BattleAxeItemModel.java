package io.github.chaosawakens.client.models.item.extended;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.item.base.DummyAnimatedGeoModel;
import io.github.chaosawakens.common.items.weapons.extended.BattleAxeItem;
import net.minecraft.util.ResourceLocation;

public class BattleAxeItemModel extends DummyAnimatedGeoModel<BattleAxeItem> {
	
	@Override
	public ResourceLocation getModelLocation(BattleAxeItem object) {
		return ChaosAwakens.prefix("geo/item/extended/battle_axe.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(BattleAxeItem object) {
		return ChaosAwakens.prefix("textures/item/battle_axe_model.png");
	}
}
