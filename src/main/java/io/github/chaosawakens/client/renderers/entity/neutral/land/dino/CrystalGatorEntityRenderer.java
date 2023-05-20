package io.github.chaosawakens.client.renderers.entity.neutral.land.dino;

import io.github.chaosawakens.client.models.entity.neutral.land.gator.CrystalGatorEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.neutral.land.gator.CrystalGatorEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class CrystalGatorEntityRenderer extends ExtendedGeoEntityRenderer<CrystalGatorEntity> {

	public CrystalGatorEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new CrystalGatorEntityModel());
	}

	@Override
	protected boolean shouldRotateOnDeath() {
		return true;
	}

	@Override
	protected float getShadowRadius() {
		return 0;
	}

	@Override
	protected ObjectArrayList<GeoLayerRenderer<CrystalGatorEntity>> getLayers() {
		return null;
	}
}
