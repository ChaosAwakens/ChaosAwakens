package io.github.chaosawakens.client.renderers.item.extended;

import io.github.chaosawakens.client.models.item.extended.BattleAxeItemModel;
import io.github.chaosawakens.common.items.weapons.extended.BattleAxeItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class BattleAxeItemRenderer extends GeoItemRenderer<BattleAxeItem> {
	
	public BattleAxeItemRenderer() {
		super(new BattleAxeItemModel());
	}
}
