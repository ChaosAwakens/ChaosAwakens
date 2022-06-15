package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.client.entity.model.BattleAxeItemModel;
import io.github.chaosawakens.common.items.extended.BattleAxeItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class BattleAxeItemRender extends GeoItemRenderer<BattleAxeItem> {
	public BattleAxeItemRender() {
		super(new BattleAxeItemModel());
	}
}
