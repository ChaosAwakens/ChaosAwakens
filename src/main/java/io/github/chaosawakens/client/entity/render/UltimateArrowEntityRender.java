package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.entity.UltimateArrowEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class UltimateArrowEntityRender extends ArrowRenderer<UltimateArrowEntity> {

    private IRenderTypeBuffer renderTypeBuffer;
    private UltimateArrowEntity entity;
    private static final ResourceLocation ARROW_TEXTURE = new ResourceLocation("textures/entity/arrow.png");

    public UltimateArrowEntityRender(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(UltimateArrowEntity entity) {
        return ARROW_TEXTURE;
    }
}