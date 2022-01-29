package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.client.entity.model.SlayerChainsawItemModel;
import io.github.chaosawakens.common.items.SlayerChainsawItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class SlayerChainsawItemRender extends GeoItemRenderer<SlayerChainsawItem> {

    public SlayerChainsawItemRender() {
        super(new SlayerChainsawItemModel());
    }
}
