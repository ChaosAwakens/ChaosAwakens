package io.github.chaosawakens.client.renderers.entity.hostile;

import io.github.chaosawakens.client.models.entity.hostile.AggressiveAntEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.hostile.AggressiveAntEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class AggressiveAntEntityRenderer extends ExtendedGeoEntityRenderer<AggressiveAntEntity> {

	public AggressiveAntEntityRenderer(EntityRendererManager renderManager, String textureName) {
		super(renderManager, new AggressiveAntEntityModel(textureName));
	}

	@Override
	protected boolean shouldRotateOnDeath() {
		return true;
	}

	@Override
	protected float getShadowRadius() {
		return 0.1F;
	}

	@Override
	protected ObjectArrayList<GeoLayerRenderer<AggressiveAntEntity>> getLayers() {
		return null;
	}
}
