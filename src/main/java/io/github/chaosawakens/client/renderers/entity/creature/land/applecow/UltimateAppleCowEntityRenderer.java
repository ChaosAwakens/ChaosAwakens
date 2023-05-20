package io.github.chaosawakens.client.renderers.entity.creature.land.applecow;

import io.github.chaosawakens.client.models.entity.creature.land.applecow.UltimateAppleCowEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.EnchantedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.creature.land.applecow.UltimateAppleCowEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class UltimateAppleCowEntityRenderer extends EnchantedGeoEntityRenderer<UltimateAppleCowEntity> {

	public UltimateAppleCowEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new UltimateAppleCowEntityModel());
	}

	@Override
	protected boolean shouldRotateOnDeath() {
		return true;
	}

	@Override
	protected float getShadowRadius() {
		return 0.7F;
	}
}
