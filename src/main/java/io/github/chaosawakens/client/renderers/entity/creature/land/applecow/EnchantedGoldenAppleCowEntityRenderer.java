package io.github.chaosawakens.client.renderers.entity.creature.land.applecow;

import io.github.chaosawakens.client.models.entity.creature.land.applecow.EnchantedGoldenAppleCowEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.EnchantedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.creature.land.applecow.EnchantedGoldenAppleCowEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class EnchantedGoldenAppleCowEntityRenderer extends EnchantedGeoEntityRenderer<EnchantedGoldenAppleCowEntity> {

	public EnchantedGoldenAppleCowEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new EnchantedGoldenAppleCowEntityModel());
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
