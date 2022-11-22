package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.common.entity.projectile.RayGunProjectileEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class RayGunProjectileRenderer extends EntityRenderer<RayGunProjectileEntity> {
	public static final ResourceLocation ARROW_TEXTURE = new ResourceLocation("textures/entity/projectiles/arrow.png");

	public RayGunProjectileRenderer(EntityRendererManager manager) {
		super(manager);
	}

	public ResourceLocation getTextureLocation(RayGunProjectileEntity entity) {
		return ARROW_TEXTURE;
	}

}
