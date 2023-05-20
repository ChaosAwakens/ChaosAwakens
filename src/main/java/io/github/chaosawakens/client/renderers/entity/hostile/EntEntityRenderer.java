package io.github.chaosawakens.client.renderers.entity.hostile;

import io.github.chaosawakens.client.models.entity.hostile.EntEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.hostile.EntEntity;
import io.github.chaosawakens.common.entity.hostile.EntEntity.EntType;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class EntEntityRenderer extends ExtendedGeoEntityRenderer<EntEntity> {

	public EntEntityRenderer(EntityRendererManager renderManager, EntType entType) {
		super(renderManager, new EntEntityModel(entType));
	}

	@Override
	protected boolean shouldRotateOnDeath() {
		return false;
	}

	@Override
	protected float getShadowRadius() {
		return 2.0F;
	}

	@Override
	protected ObjectArrayList<GeoLayerRenderer<EntEntity>> getLayers() {
		return null;
	}
}
