package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.common.entity.projectile.RayGunProjectileEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RayGunProjectileRender extends EntityRenderer<RayGunProjectileEntity> {
    public static final ResourceLocation ARROW_TEXTURE = new ResourceLocation("textures/entity/projectiles/arrow.png");

    public RayGunProjectileRender(EntityRendererManager manager) {
        super(manager);
    }

    public ResourceLocation getEntityTexture(RayGunProjectileEntity entity) {
        return ARROW_TEXTURE;
    }

}

