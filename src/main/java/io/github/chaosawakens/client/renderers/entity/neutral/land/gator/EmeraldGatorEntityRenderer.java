package io.github.chaosawakens.client.renderers.entity.neutral.land.gator;

import io.github.chaosawakens.client.models.entity.neutral.land.gator.EmeraldGatorEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.neutral.land.gator.EmeraldGatorEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class EmeraldGatorEntityRenderer extends ExtendedGeoEntityRenderer<EmeraldGatorEntity> {

	public EmeraldGatorEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new EmeraldGatorEntityModel());
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
	protected ObjectArrayList<GeoLayerRenderer<EmeraldGatorEntity>> getLayers() {
		return null;
	}
}
