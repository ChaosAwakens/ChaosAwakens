package io.github.chaosawakens.client.renderers.entity.projectile;

import io.github.chaosawakens.common.entity.projectile.ThunderStaffProjectileEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ThunderStaffProjectileRenderer extends EntityRenderer<ThunderStaffProjectileEntity> {
	private static final ResourceLocation ARROW_TEXTURE = new ResourceLocation("textures/entity/projectiles/arrow.png"); //TODO Texture

	public ThunderStaffProjectileRenderer(EntityRendererManager manager) {
		super(manager);
	}

	public ResourceLocation getTextureLocation(ThunderStaffProjectileEntity entity) {
		return ARROW_TEXTURE;
	}
}
