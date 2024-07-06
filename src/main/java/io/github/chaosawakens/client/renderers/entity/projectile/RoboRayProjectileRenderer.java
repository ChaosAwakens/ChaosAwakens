package io.github.chaosawakens.client.renderers.entity.projectile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chaosawakens.client.models.entity.projectile.RoboRayModel;
import io.github.chaosawakens.common.entity.projectile.RoboRayEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class RoboRayProjectileRenderer extends GeoProjectilesRenderer<RoboRayEntity> {

    public RoboRayProjectileRenderer(EntityRendererManager renderManager) {
        super(renderManager, new RoboRayModel());
    }

    @Override
    public RenderType getRenderType(RoboRayEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
