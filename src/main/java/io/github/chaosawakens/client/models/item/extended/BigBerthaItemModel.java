package io.github.chaosawakens.client.models.item.extended;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.item.base.DummyAnimatedGeoModel;
import io.github.chaosawakens.common.items.weapons.extended.BigBerthaItem;
import net.minecraft.util.ResourceLocation;

public class BigBerthaItemModel extends DummyAnimatedGeoModel<BigBerthaItem> {
	
	@Override
	public ResourceLocation getModelLocation(BigBerthaItem object) {
		return ChaosAwakens.prefix("geo/item/extended/big_bertha.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(BigBerthaItem object) {
		return ChaosAwakens.prefix("textures/item/big_bertha_model.png");
	}

}
