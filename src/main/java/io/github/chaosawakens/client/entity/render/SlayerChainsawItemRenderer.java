package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.client.entity.model.SlayerChainsawItemModel;
import io.github.chaosawakens.common.items.extended.SlayerChainsawItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class SlayerChainsawItemRenderer extends GeoItemRenderer<SlayerChainsawItem> {
	public SlayerChainsawItemRenderer() {
		super(new SlayerChainsawItemModel());
	}
}
