package io.github.chaosawakens.client.renderers.entity.creature.land.carrotpig;

import io.github.chaosawakens.client.models.entity.creature.land.carrotpig.CarrotPigEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.client.renderers.layers.texture.GeoEntitySaddleLayer;
import io.github.chaosawakens.common.entity.creature.land.carrotpig.CarrotPigEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class CarrotPigEntityRenderer extends ExtendedGeoEntityRenderer<CarrotPigEntity> {

	public CarrotPigEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new CarrotPigEntityModel());
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
	protected ObjectArrayList<GeoLayerRenderer<CarrotPigEntity>> getLayers() {
		ObjectArrayList<GeoLayerRenderer<CarrotPigEntity>> renderLayers = new ObjectArrayList<GeoLayerRenderer<CarrotPigEntity>>();
		renderLayers.add(new GeoEntitySaddleLayer<CarrotPigEntity>(this, modelProvider.getModelLocation(animatable)));
		return renderLayers;
	}
}
