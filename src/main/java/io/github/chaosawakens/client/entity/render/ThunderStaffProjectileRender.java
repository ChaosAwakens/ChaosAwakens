package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.common.entity.projectile.ThunderStaffProjectileEntity;
import io.github.chaosawakens.common.entity.projectile.UltimateArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ThunderStaffProjectileRender extends EntityRenderer<ThunderStaffProjectileEntity> {
    public static final ResourceLocation ARROW_TEXTURE = new ResourceLocation("textures/entity/projectiles/arrow.png");

    public ThunderStaffProjectileRender(EntityRendererManager manager) {
        super(manager);
    }

    public ResourceLocation getEntityTexture(ThunderStaffProjectileEntity entity) {
        return ARROW_TEXTURE;
    }

}

