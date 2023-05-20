package io.github.chaosawakens.client.renderers.entity.boss.miniboss;

import io.github.chaosawakens.client.models.entity.boss.miniboss.HerculesBeetleEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.boss.miniboss.HerculesBeetleEntity;
import io.github.chaosawakens.common.entity.boss.miniboss.HerculesBeetleEntity.HerculesBeetleType;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class HerculesBeetleEntityRenderer extends ExtendedGeoEntityRenderer<HerculesBeetleEntity> {

	public HerculesBeetleEntityRenderer(EntityRendererManager renderManager, HerculesBeetleType beetleType) {
		super(renderManager, new HerculesBeetleEntityModel(beetleType));
	}

	@Override
	protected boolean shouldRotateOnDeath() {
		return false;
	}

	@Override
	protected float getShadowRadius() {
		return 2.5F;
	}

	@Override
	protected ObjectArrayList<GeoLayerRenderer<HerculesBeetleEntity>> getLayers() {
		return null;
	}
}
