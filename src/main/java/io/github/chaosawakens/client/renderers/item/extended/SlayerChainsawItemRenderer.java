package io.github.chaosawakens.client.renderers.item.extended;

import io.github.chaosawakens.client.models.item.extended.SlayerChainsawItemModel;
import io.github.chaosawakens.common.items.weapons.extended.SlayerChainsawItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class SlayerChainsawItemRenderer extends GeoItemRenderer<SlayerChainsawItem> {
	
	public SlayerChainsawItemRenderer() {
		super(new SlayerChainsawItemModel());
	}
}
