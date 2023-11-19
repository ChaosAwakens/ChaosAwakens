package io.github.chaosawakens.client.renderers.entity.hostile.insect;

import io.github.chaosawakens.client.models.entity.hostile.insect.WaspEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.hostile.insect.WaspEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class WaspEntityRenderer extends ExtendedGeoEntityRenderer<WaspEntity> {

    public WaspEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager, new WaspEntityModel());
    }


    @Override
    protected boolean shouldRotateOnDeath() {
        return false;
    }

    @Override
    protected float getShadowRadius() {
        return 0.2F;
    }

    @Override
    protected ObjectArrayList<GeoLayerRenderer<WaspEntity>> getLayers() {
        return null;
    }
}

