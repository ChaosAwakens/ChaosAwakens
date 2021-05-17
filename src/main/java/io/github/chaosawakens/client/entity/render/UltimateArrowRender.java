package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.common.entity.projectile.UltimateArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UltimateArrowRender extends ArrowRenderer<UltimateArrowEntity> {
    public static final ResourceLocation ARROW_TEXTURE = new ResourceLocation("textures/entity/projectiles/arrow.png");

    public UltimateArrowRender(EntityRendererManager manager) {
        super(manager);
    }

    public ResourceLocation getEntityTexture(UltimateArrowEntity entity) {
        return ARROW_TEXTURE;
    }

}

