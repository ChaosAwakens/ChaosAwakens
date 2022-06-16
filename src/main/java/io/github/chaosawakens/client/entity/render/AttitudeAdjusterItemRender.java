package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.client.entity.model.AttitudeAdjusterItemModel;
import io.github.chaosawakens.common.items.extended.AttitudeAdjusterItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class AttitudeAdjusterItemRender extends GeoItemRenderer<AttitudeAdjusterItem> {
	public AttitudeAdjusterItemRender() {
		super(new AttitudeAdjusterItemModel());
	}
}
