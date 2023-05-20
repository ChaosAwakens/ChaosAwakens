package io.github.chaosawakens.client.renderers.entity.creature.land.applecow;

import io.github.chaosawakens.client.models.entity.creature.land.applecow.CrystalAppleCowEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.creature.land.applecow.CrystalAppleCowEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class CrystalAppleCowEntityRenderer extends ExtendedGeoEntityRenderer<CrystalAppleCowEntity> {

	public CrystalAppleCowEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new CrystalAppleCowEntityModel());
	}

	@Override
	protected boolean shouldRotateOnDeath() {
		return true;
	}

	@Override
	protected float getShadowRadius() {
		return 0.0F;
	}

	@Override
	protected ObjectArrayList<GeoLayerRenderer<CrystalAppleCowEntity>> getLayers() {
		return null;
	}

}
