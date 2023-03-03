package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.common.entity.projectile.UltimateCrossbowArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UltimateCrossbowArrowProjectileRenderer extends ArrowRenderer<UltimateCrossbowArrowEntity> {
	public static final ResourceLocation ARROW_TEXTURE = new ResourceLocation("textures/entity/projectiles/arrow.png");

	public UltimateCrossbowArrowProjectileRenderer(EntityRendererManager manager) {
		super(manager);
	}

	public ResourceLocation getTextureLocation(UltimateCrossbowArrowEntity entity) {
		return ARROW_TEXTURE;
	}
}
