package io.github.chaosawakens.entities.entities.ent;

import io.github.chaosawakens.ChaosAwakens;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class EntEntityRenderer extends GeoEntityRenderer<EntEntity> {

    private VertexConsumerProvider vertexConsumerProvider;
    private EntEntity entEntity;

    public EntEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new EntEntityModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public void renderEarly(EntEntity animatable, MatrixStack stackIn, float ticks, VertexConsumerProvider vertexConsumerProvider, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        this.entEntity = animatable;
        this.vertexConsumerProvider = vertexConsumerProvider;

        super.renderEarly(animatable, stackIn, ticks, vertexConsumerProvider, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public Identifier getTextureLocation(EntEntity instance) {
        return new Identifier(ChaosAwakens.modId, "textures/entity/ent.png");
    }

    @Override
    public RenderLayer getRenderType(EntEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntitySmoothCutout(getTextureLocation(animatable));
    }
}
