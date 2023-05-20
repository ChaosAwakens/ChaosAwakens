package io.github.chaosawakens.client.renderers.entity.creature.land.carrotpig;

import io.github.chaosawakens.client.models.entity.creature.land.carrotpig.EnchantedGoldenCarrotPigEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.EnchantedGeoEntityRenderer;
import io.github.chaosawakens.client.renderers.layers.texture.GeoEntitySaddleLayer;
import io.github.chaosawakens.common.entity.creature.land.carrotpig.EnchantedGoldenCarrotPigEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class EnchantedGoldenCarrotPigEntityRenderer extends EnchantedGeoEntityRenderer<EnchantedGoldenCarrotPigEntity> {

	public EnchantedGoldenCarrotPigEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new EnchantedGoldenCarrotPigEntityModel());
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
	protected ObjectArrayList<GeoLayerRenderer<EnchantedGoldenCarrotPigEntity>> getLayers() {
		ObjectArrayList<GeoLayerRenderer<EnchantedGoldenCarrotPigEntity>> renderLayers = super.getLayers();
		renderLayers.add(new GeoEntitySaddleLayer<EnchantedGoldenCarrotPigEntity>(this, modelProvider.getModelLocation(animatable)));
		return renderLayers;
	}
}