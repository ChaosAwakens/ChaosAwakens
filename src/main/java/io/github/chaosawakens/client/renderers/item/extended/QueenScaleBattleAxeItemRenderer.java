package io.github.chaosawakens.client.renderers.item.extended;

import io.github.chaosawakens.client.models.item.extended.QueenScaleBattleAxeItemModel;
import io.github.chaosawakens.common.items.weapons.extended.QueenScaleBattleAxeItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class QueenScaleBattleAxeItemRenderer extends GeoItemRenderer<QueenScaleBattleAxeItem> {
	
	public QueenScaleBattleAxeItemRenderer() {
		super(new QueenScaleBattleAxeItemModel());
	}
}
