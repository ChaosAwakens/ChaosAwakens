package io.github.chaosawakens.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.model.EnchantedGoldenCarrotPigModel;
import io.github.chaosawakens.client.entity.render.layers.PigGlintLayer;
import io.github.chaosawakens.common.entity.EnchantedGoldenCarrotPigEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EnchantedGoldenCarrotPigEntityRender extends GeoEntityRenderer<EnchantedGoldenCarrotPigEntity> {
    public EnchantedGoldenCarrotPigEntityRender(EntityRendererManager renderManager) {
        super(renderManager, new EnchantedGoldenCarrotPigModel());
        this.shadowRadius = 0.2F;
        this.addLayer(new PigGlintLayer(this));
    }

    @Override
    public void renderEarly(EnchantedGoldenCarrotPigEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(EnchantedGoldenCarrotPigEntity entity) {
        return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/golden_carrot_pig.png");
    }

    @Override
    public RenderType getRenderType(EnchantedGoldenCarrotPigEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
