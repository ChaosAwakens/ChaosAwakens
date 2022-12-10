package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.common.entity.projectile.ThunderStaffProjectileEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ThunderStaffProjectileRenderer extends EntityRenderer<ThunderStaffProjectileEntity> {
	public static final ResourceLocation ARROW_TEXTURE = new ResourceLocation("textures/entity/projectiles/arrow.png");

	public ThunderStaffProjectileRenderer(EntityRendererManager manager) {
		super(manager);
	}

	public ResourceLocation getTextureLocation(ThunderStaffProjectileEntity entity) {
		return ARROW_TEXTURE;
	}
}
