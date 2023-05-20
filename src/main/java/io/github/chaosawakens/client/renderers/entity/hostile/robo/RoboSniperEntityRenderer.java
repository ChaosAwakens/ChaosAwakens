package io.github.chaosawakens.client.renderers.entity.hostile.robo;

import io.github.chaosawakens.client.models.entity.hostile.robo.RoboSniperEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.client.renderers.layers.texture.RoboSniperGlowLayer;
import io.github.chaosawakens.common.entity.hostile.robo.RoboSniperEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class RoboSniperEntityRenderer extends ExtendedGeoEntityRenderer<RoboSniperEntity> {
	
	public RoboSniperEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new RoboSniperEntityModel());
	}

	@Override
	protected boolean shouldRotateOnDeath() {
		return true;
	}

	@Override
	protected float getShadowRadius() {
		return 0.7F;
	}

	@Override
	protected ObjectArrayList<GeoLayerRenderer<RoboSniperEntity>> getLayers() {
		ObjectArrayList<GeoLayerRenderer<RoboSniperEntity>> renderLayers = new ObjectArrayList<GeoLayerRenderer<RoboSniperEntity>>();
		renderLayers.add(new RoboSniperGlowLayer(this));
		return renderLayers;
	}
}
