package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.client.entity.model.QueenScaleBattleAxeItemModel;
import io.github.chaosawakens.common.items.extended.QueenScaleBattleAxeItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class QueenScaleBattleAxeItemRenderer extends GeoItemRenderer<QueenScaleBattleAxeItem> {
	public QueenScaleBattleAxeItemRenderer() {
		super(new QueenScaleBattleAxeItemModel());
	}
}
