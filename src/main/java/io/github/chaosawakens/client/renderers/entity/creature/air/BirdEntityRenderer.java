package io.github.chaosawakens.client.renderers.entity.creature.air;

import io.github.chaosawakens.client.models.entity.creature.air.BirdEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.creature.air.BirdEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class BirdEntityRenderer extends ExtendedGeoEntityRenderer<BirdEntity> {

	public BirdEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new BirdEntityModel());
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
	protected ObjectArrayList<GeoLayerRenderer<BirdEntity>> getLayers() {
		return null;
	}
}