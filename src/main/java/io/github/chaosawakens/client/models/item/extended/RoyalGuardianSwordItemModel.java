package io.github.chaosawakens.client.models.item.extended;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.item.base.DummyAnimatedGeoModel;
import io.github.chaosawakens.common.items.weapons.extended.RoyalGuardianSwordItem;
import net.minecraft.util.ResourceLocation;

public class RoyalGuardianSwordItemModel extends DummyAnimatedGeoModel<RoyalGuardianSwordItem> {
	
	@Override
	public ResourceLocation getModelLocation(RoyalGuardianSwordItem object) {
		return ChaosAwakens.prefix("geo/item/extended/royal_guardian_sword.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(RoyalGuardianSwordItem object) {
		return ChaosAwakens.prefix("textures/item/royal_guardian_sword_model.png");
	}
}