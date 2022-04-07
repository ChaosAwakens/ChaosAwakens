package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.client.entity.model.QueenScaleBattleAxeItemModel;
import io.github.chaosawakens.common.items.ExtendedHitAxeItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class QueenScaleBattleAxeItemRender extends GeoItemRenderer<ExtendedHitAxeItem> {
    public QueenScaleBattleAxeItemRender() {
        super(new QueenScaleBattleAxeItemModel());
    }
}
