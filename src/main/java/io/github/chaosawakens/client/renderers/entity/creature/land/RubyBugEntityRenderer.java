package io.github.chaosawakens.client.renderers.entity.creature.land;

import io.github.chaosawakens.client.models.entity.creature.land.RubyBugEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.creature.land.RubyBugEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class RubyBugEntityRenderer extends ExtendedGeoEntityRenderer<RubyBugEntity> {

	public RubyBugEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new RubyBugEntityModel());
	}

	@Override
	protected boolean shouldRotateOnDeath() {
		return true;
	}

	@Override
	protected float getShadowRadius() {
		return 0.4F;
	}

	@Override
	protected ObjectArrayList<GeoLayerRenderer<RubyBugEntity>> getLayers() {
		return null;
	}
}
