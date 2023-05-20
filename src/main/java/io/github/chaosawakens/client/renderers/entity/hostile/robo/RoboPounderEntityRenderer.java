package io.github.chaosawakens.client.renderers.entity.hostile.robo;

import io.github.chaosawakens.client.models.entity.hostile.robo.RoboPounderEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.hostile.robo.RoboPounderEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class RoboPounderEntityRenderer extends ExtendedGeoEntityRenderer<RoboPounderEntity> {

	public RoboPounderEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new RoboPounderEntityModel());
	}

	@Override
	protected boolean shouldRotateOnDeath() {
		return false;
	}

	@Override
	protected float getShadowRadius() {
		return 0.9F;
	}

	@Override
	protected ObjectArrayList<GeoLayerRenderer<RoboPounderEntity>> getLayers() {
		return null;
	}
}
