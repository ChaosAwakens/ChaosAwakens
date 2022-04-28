package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.client.entity.model.RoyalGuardianItemModel;
import io.github.chaosawakens.common.items.ExtendedHitWeaponItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class RoyalGuardianItemRender extends GeoItemRenderer<ExtendedHitWeaponItem> {
	public RoyalGuardianItemRender() {
		super(new RoyalGuardianItemModel());
	}
}
