package io.github.chaosawakens.client.renderers.entity.boss.robo;

import io.github.chaosawakens.client.models.entity.boss.robo.RoboJefferyEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.boss.robo.RoboJefferyEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class RoboJefferyEntityRenderer extends ExtendedGeoEntityRenderer<RoboJefferyEntity> {

	public RoboJefferyEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new RoboJefferyEntityModel());
	}

	@Override
	protected boolean shouldRotateOnDeath() {
		return false;
	}

	@Override
	protected float getShadowRadius() {
		return 2.6F;
	}
	
	@Override
	protected ObjectArrayList<GeoLayerRenderer<RoboJefferyEntity>> getLayers() {
		return null;
	}
}