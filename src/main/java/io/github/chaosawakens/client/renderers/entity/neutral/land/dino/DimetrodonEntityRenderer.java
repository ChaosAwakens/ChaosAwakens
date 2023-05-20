package io.github.chaosawakens.client.renderers.entity.neutral.land.dino;

import io.github.chaosawakens.client.models.entity.neutral.land.dino.DimetrodonEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.neutral.land.dino.DimetrodonEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class DimetrodonEntityRenderer extends ExtendedGeoEntityRenderer<DimetrodonEntity> {

	public DimetrodonEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new DimetrodonEntityModel());
	}

	@Override
	protected boolean shouldRotateOnDeath() {
		return true;
	}

	@Override
	protected float getShadowRadius() {
		return 0.4F;
	}

	@Override
	protected ObjectArrayList<GeoLayerRenderer<DimetrodonEntity>> getLayers() {
		return null;
	}
}
