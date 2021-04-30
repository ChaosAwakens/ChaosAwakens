package io.github.chaosawakens.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.model.BrownAntEntityModel;
import io.github.chaosawakens.entity.BrownAntEntity;
import io.github.chaosawakens.entity.UltimateArrowEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class UltimateArrowEntityRender extends ArrowRenderer<UltimateArrowEntity> {

    private IRenderTypeBuffer renderTypeBuffer;
    private static final ResourceLocation ARROW_TEXTURE = new ResourceLocation("textures/entity/arrow.png");

    public UltimateArrowEntityRender(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(UltimateArrowEntity entity) {
        return ARROW_TEXTURE;
    }
}