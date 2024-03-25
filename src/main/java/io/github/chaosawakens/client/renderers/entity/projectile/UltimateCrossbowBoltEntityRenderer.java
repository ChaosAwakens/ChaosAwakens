package io.github.chaosawakens.client.renderers.entity.projectile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chaosawakens.client.models.entity.projectile.UltimateCrossbowBoltEntityModel;
import io.github.chaosawakens.common.entity.projectile.arrow.UltimateCrossbowBoltEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class UltimateCrossbowBoltEntityRenderer extends GeoProjectilesRenderer<UltimateCrossbowBoltEntity> {

    public UltimateCrossbowBoltEntityRenderer(EntityRendererManager manager) {
        super(manager, new UltimateCrossbowBoltEntityModel());
    }

    @Override
    public RenderType getRenderType(UltimateCrossbowBoltEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
