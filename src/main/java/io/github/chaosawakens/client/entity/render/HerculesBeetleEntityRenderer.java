package io.github.chaosawakens.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.model.HerculesBeetleEntityModel;
import io.github.chaosawakens.common.entity.HerculesBeetleEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class HerculesBeetleEntityRenderer extends GeoEntityRenderer<HerculesBeetleEntity> {
	private static final ResourceLocation MODERN_BEETLE_TEXTURE = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/hercules_beetle/hercules_beetle.png");
	private static final ResourceLocation THROWBACK_BEETLE_TEXTURE = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/hercules_beetle/hercules_beetle_throwback.png");
	
	public HerculesBeetleEntityRenderer(EntityRendererManager renderManager, HerculesBeetleEntity.Type type) {
		super(renderManager, new HerculesBeetleEntityModel());
		this.shadowRadius = 2.5F;
	}

	@Override
	public void renderEarly(HerculesBeetleEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
	}

	@Override
	public ResourceLocation getTextureLocation(HerculesBeetleEntity entity) {
		switch (entity.getBeetleType()) {
		default:
			return MODERN_BEETLE_TEXTURE;
		case MODERN:
			return MODERN_BEETLE_TEXTURE;
		case THROWBACK:
			return THROWBACK_BEETLE_TEXTURE;
		}
	}

	@Override
	public RenderType getRenderType(HerculesBeetleEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderRecursively(GeoBone bone, MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
	
	@Override
	protected float getDeathMaxRotation(HerculesBeetleEntity entity) {
		return 0.0F;
	}
}