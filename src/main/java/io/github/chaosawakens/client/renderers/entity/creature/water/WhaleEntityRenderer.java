package io.github.chaosawakens.client.renderers.entity.creature.water;

import io.github.chaosawakens.client.models.entity.creature.water.WhaleEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.creature.water.WhaleEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class WhaleEntityRenderer extends ExtendedGeoEntityRenderer<WhaleEntity> {
	
	public WhaleEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new WhaleEntityModel());
	}

	@Override
	protected boolean shouldRotateOnDeath() {
		return false;
	}

	@Override
	protected float getShadowRadius() {
		return 3.5F;
	}

	@Override
	protected ObjectArrayList<GeoLayerRenderer<WhaleEntity>> getLayers() {
		return null;
	}
}
