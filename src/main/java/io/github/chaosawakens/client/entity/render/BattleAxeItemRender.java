package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.client.entity.model.BattleAxeItemModel;
import io.github.chaosawakens.common.items.ExtendedHitAxeItem;
import io.github.chaosawakens.common.items.ExtendedHitWeaponItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class BattleAxeItemRender extends GeoItemRenderer<ExtendedHitAxeItem> {
    public BattleAxeItemRender() {
        super(new BattleAxeItemModel());
    }
}
