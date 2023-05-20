package io.github.chaosawakens.client.renderers.entity.creature.land.carrotpig;

import io.github.chaosawakens.client.models.entity.creature.land.carrotpig.GoldenCarrotPigEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.client.renderers.layers.texture.GeoEntitySaddleLayer;
import io.github.chaosawakens.common.entity.creature.land.carrotpig.GoldenCarrotPigEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class GoldenCarrotPigEntityRenderer extends ExtendedGeoEntityRenderer<GoldenCarrotPigEntity> {

	public GoldenCarrotPigEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new GoldenCarrotPigEntityModel());
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
	protected ObjectArrayList<GeoLayerRenderer<GoldenCarrotPigEntity>> getLayers() {
		ObjectArrayList<GeoLayerRenderer<GoldenCarrotPigEntity>> renderLayers = new ObjectArrayList<GeoLayerRenderer<GoldenCarrotPigEntity>>();
		renderLayers.add(new GeoEntitySaddleLayer<GoldenCarrotPigEntity>(this, modelProvider.getModelLocation(animatable)));
		return renderLayers;
	}
}
