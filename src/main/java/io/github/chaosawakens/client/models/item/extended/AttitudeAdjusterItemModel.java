package io.github.chaosawakens.client.models.item.extended;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.item.base.DummyAnimatedGeoModel;
import io.github.chaosawakens.common.items.weapons.extended.AttitudeAdjusterItem;
import net.minecraft.util.ResourceLocation;

public class AttitudeAdjusterItemModel extends DummyAnimatedGeoModel<AttitudeAdjusterItem> {
	
	@Override
	public ResourceLocation getModelLocation(AttitudeAdjusterItem object) {
		return ChaosAwakens.prefix("geo/item/extended/attitude_adjuster.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(AttitudeAdjusterItem object) {
		return ChaosAwakens.prefix("textures/item/attitude_adjuster_model.png");
	}
}
