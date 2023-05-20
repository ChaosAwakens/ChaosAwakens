package io.github.chaosawakens.client.renderers.item.extended;

import io.github.chaosawakens.client.models.item.extended.AttitudeAdjusterItemModel;
import io.github.chaosawakens.common.items.weapons.extended.AttitudeAdjusterItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class AttitudeAdjusterItemRenderer extends GeoItemRenderer<AttitudeAdjusterItem> {
	
	public AttitudeAdjusterItemRenderer() {
		super(new AttitudeAdjusterItemModel());
	}
}
