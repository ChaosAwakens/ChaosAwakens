package io.github.chaosawakens.client.renderers.entity.creature.land.carrotpig;

import io.github.chaosawakens.client.models.entity.creature.land.carrotpig.CrystalCarrotPigEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.client.renderers.layers.texture.GeoEntitySaddleLayer;
import io.github.chaosawakens.common.entity.creature.land.carrotpig.CrystalCarrotPigEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class CrystalCarrotPigEntityRenderer extends ExtendedGeoEntityRenderer<CrystalCarrotPigEntity> {

	public CrystalCarrotPigEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new CrystalCarrotPigEntityModel());
	}
	
	@Override
	protected boolean shouldRotateOnDeath() {
		return true;
	}

	@Override
	protected float getShadowRadius() {
		return 0.0F;
	}

	@Override
	protected ObjectArrayList<GeoLayerRenderer<CrystalCarrotPigEntity>> getLayers() {
		ObjectArrayList<GeoLayerRenderer<CrystalCarrotPigEntity>> renderLayers = new ObjectArrayList<GeoLayerRenderer<CrystalCarrotPigEntity>>();
		renderLayers.add(new GeoEntitySaddleLayer<CrystalCarrotPigEntity>(this, modelProvider.getModelLocation(animatable)));
		return renderLayers;
	}
}
