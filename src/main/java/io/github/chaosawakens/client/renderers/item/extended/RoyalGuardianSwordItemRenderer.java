package io.github.chaosawakens.client.renderers.item.extended;

import io.github.chaosawakens.client.models.item.extended.RoyalGuardianSwordItemModel;
import io.github.chaosawakens.common.items.weapons.extended.RoyalGuardianSwordItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class RoyalGuardianSwordItemRenderer extends GeoItemRenderer<RoyalGuardianSwordItem> {
	
	public RoyalGuardianSwordItemRenderer() {
		super(new RoyalGuardianSwordItemModel());
	}
}
