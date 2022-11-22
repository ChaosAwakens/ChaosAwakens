package io.github.chaosawakens.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.model.RoboSniperEntityModel;
import io.github.chaosawakens.client.entity.render.layers.RoboSniperGlowLayer;
import io.github.chaosawakens.common.entity.robo.RoboSniperEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RoboSniperEntityRenderer extends GeoEntityRenderer<RoboSniperEntity> {
	public RoboSniperEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new RoboSniperEntityModel());
		this.shadowRadius = 0.7F;
		this.addLayer(new RoboSniperGlowLayer(this));
	}

	@Override
	public void renderEarly(RoboSniperEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
	}

	@Override
	public ResourceLocation getTextureLocation(RoboSniperEntity entity) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/robos/robo_sniper.png");
	}

	@Override
	public RenderType getRenderType(RoboSniperEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderRecursively(GeoBone bone, MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
}
