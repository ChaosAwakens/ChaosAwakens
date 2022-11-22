package io.github.chaosawakens.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;

import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.model.GazelleEntityModel;
import io.github.chaosawakens.common.entity.GazelleEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GazelleEntityRenderer extends GeoEntityRenderer<GazelleEntity> {
	private static final ResourceLocation TEXTURE_BLACK_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/gazelle/black.png");
	private static final ResourceLocation TEXTURE_RED_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/gazelle/red.png");
	private static final ResourceLocation TEXTURE_DARK_RED_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/gazelle/dark_red.png");
	private static final ResourceLocation TEXTURE_BROWN_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/gazelle/brown.png");
	private static final ResourceLocation TEXTURE_BEIJ_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/gazelle/beij.png");

	public GazelleEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new GazelleEntityModel());
		this.shadowRadius = 0.4f;
	}

	@Override
	public void renderEarly(GazelleEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
	}

	@Override
	public ResourceLocation getTextureLocation(GazelleEntity entity) {
		switch (entity.getGazelleType()) {
		case 0:
		default:
			return TEXTURE_BROWN_LOCATION;
		case 1:
			return TEXTURE_RED_LOCATION;
		case 2:
			return TEXTURE_DARK_RED_LOCATION;
		case 3:
			return TEXTURE_BLACK_LOCATION;
		case 4:
			return TEXTURE_BEIJ_LOCATION;
		}
	}

	@Override
	public RenderType getRenderType(GazelleEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderRecursively(GeoBone bone, MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

}
