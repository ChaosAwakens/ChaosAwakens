package io.github.chaosawakens.client.renderers.entity.creature.land;

import io.github.chaosawakens.client.models.entity.creature.land.AntEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.creature.land.AntEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class AntEntityRenderer extends ExtendedGeoEntityRenderer<AntEntity> {

	public AntEntityRenderer(EntityRendererManager renderManager, String textureName) {
		super(renderManager, new AntEntityModel(textureName));
	}

	@Override
	protected boolean shouldRotateOnDeath() {
		return false;
	}

	@Override
	protected float getShadowRadius() {
		return 0.1F;
	}

	@Override
	protected ObjectArrayList<GeoLayerRenderer<AntEntity>> getLayers() {
		return null;
	}
}
