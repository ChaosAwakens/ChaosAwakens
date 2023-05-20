package io.github.chaosawakens.client.renderers.entity.projectile;

import io.github.chaosawakens.common.entity.projectile.arrow.UltimateCrossbowArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class UltimateCrossbowArrowProjectileRenderer extends ArrowRenderer<UltimateCrossbowArrowEntity> {
	private static final ResourceLocation ARROW_TEXTURE = new ResourceLocation("textures/entity/projectiles/arrow.png");

	public UltimateCrossbowArrowProjectileRenderer(EntityRendererManager manager) {
		super(manager);
	}

	public ResourceLocation getTextureLocation(UltimateCrossbowArrowEntity entity) {
		return ARROW_TEXTURE;
	}
}
