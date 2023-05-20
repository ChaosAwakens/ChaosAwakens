package io.github.chaosawakens.client.renderers.entity.projectile;

import io.github.chaosawakens.common.entity.projectile.arrow.UltimateArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class UltimateArrowProjectileRenderer extends ArrowRenderer<UltimateArrowEntity> {
	private static final ResourceLocation ARROW_TEXTURE = new ResourceLocation("textures/entity/projectiles/arrow.png");

	public UltimateArrowProjectileRenderer(EntityRendererManager manager) {
		super(manager);
	}

	public ResourceLocation getTextureLocation(UltimateArrowEntity entity) {
		return ARROW_TEXTURE;
	}
}
