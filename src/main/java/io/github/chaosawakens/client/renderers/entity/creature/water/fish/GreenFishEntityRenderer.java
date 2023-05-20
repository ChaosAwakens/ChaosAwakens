package io.github.chaosawakens.client.renderers.entity.creature.water.fish;

import io.github.chaosawakens.client.models.entity.creature.water.fish.GreenFishEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.creature.water.fish.GreenFishEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class GreenFishEntityRenderer extends ExtendedGeoEntityRenderer<GreenFishEntity> {

	public GreenFishEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new GreenFishEntityModel());
	}
	
	@Override
	protected boolean shouldRotateOnDeath() {
		return false;
	}

	@Override
	protected float getShadowRadius() {
		return 0.3F;
	}

	@Override
	protected ObjectArrayList<GeoLayerRenderer<GreenFishEntity>> getLayers() {
		return null;
	}
}
