package io.github.chaosawakens.client.renderers.item.extended;

import io.github.chaosawakens.client.models.item.extended.BigBerthaItemModel;
import io.github.chaosawakens.common.items.weapons.extended.BigBerthaItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class BigBerthaItemRenderer extends GeoItemRenderer<BigBerthaItem> {
	
	public BigBerthaItemRenderer() {
		super(new BigBerthaItemModel());
	}
}
