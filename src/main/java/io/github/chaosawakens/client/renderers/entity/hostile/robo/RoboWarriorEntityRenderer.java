package io.github.chaosawakens.client.renderers.entity.hostile.robo;

import io.github.chaosawakens.client.models.entity.hostile.robo.RoboWarriorEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.hostile.robo.RoboWarriorEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class RoboWarriorEntityRenderer extends ExtendedGeoEntityRenderer<RoboWarriorEntity> {

	public RoboWarriorEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new RoboWarriorEntityModel());
	}

	@Override
	protected boolean shouldRotateOnDeath() {
		return false;
	}

	@Override
	protected float getShadowRadius() {
		return 0.6F;
	}

	@Override
	protected ObjectArrayList<GeoLayerRenderer<RoboWarriorEntity>> getLayers() {
		return null;
	}
}
