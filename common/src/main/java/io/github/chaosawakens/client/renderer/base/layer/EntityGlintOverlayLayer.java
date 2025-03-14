package io.github.chaosawakens.client.renderer.base.layer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.Entity;

public class EntityGlintOverlayLayer<E extends Entity, EM extends EntityModel<E>> extends BasicOverlayRenderLayer<E, EM> {

    public EntityGlintOverlayLayer(RenderLayerParent<E, EM> pRenderer) {
        super(pRenderer, RenderType.entityGlint());
    }
}
