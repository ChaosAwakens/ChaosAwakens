package io.github.chaosawakens.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.model.EnchantedGoldenAppleCowModel;
import io.github.chaosawakens.client.entity.render.layers.CowGlintLayer;
import io.github.chaosawakens.common.entity.EnchantedGoldenAppleCowEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class EnchantedGoldenAppleCowEntityRender extends GeoEntityRenderer<EnchantedGoldenAppleCowEntity> {
    private static final ResourceLocation GOLDEN_APPLE_COW_TEXTURE = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/apple_cow/golden_apple_cow.png");

    public EnchantedGoldenAppleCowEntityRender(EntityRendererManager renderManager) {
        super(renderManager, new EnchantedGoldenAppleCowModel());
        this.shadowRadius = 0.2F;
        this.addLayer(new CowGlintLayer(this));
    }

    @Override
    public void renderEarly(EnchantedGoldenAppleCowEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(EnchantedGoldenAppleCowEntity entity) {
        return GOLDEN_APPLE_COW_TEXTURE;
    }

    @Override
    public RenderType getRenderType(EnchantedGoldenAppleCowEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
