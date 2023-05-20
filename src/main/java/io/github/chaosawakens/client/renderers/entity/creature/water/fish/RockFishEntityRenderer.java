package io.github.chaosawakens.client.renderers.entity.creature.water.fish;

import io.github.chaosawakens.client.models.entity.creature.water.fish.RockFishEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.creature.water.fish.RockFishEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class RockFishEntityRenderer extends ExtendedGeoEntityRenderer<RockFishEntity> {

	public RockFishEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new RockFishEntityModel());
	}

	@Override
	protected boolean shouldRotateOnDeath() {
		return false;
	}

	@Override
	protected float getShadowRadius() {
		return 0.35F;
	}

	@Override
	protected ObjectArrayList<GeoLayerRenderer<RockFishEntity>> getLayers() {
		return null;
	}
}
