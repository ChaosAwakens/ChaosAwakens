package io.github.chaosawakens.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.model.BirdEntityModel;
import io.github.chaosawakens.common.entity.BirdEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BirdEntityRenderer extends GeoEntityRenderer<BirdEntity> {
	private static final ResourceLocation TEXTURE_BLACK_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/bird/black.png");
	private static final ResourceLocation TEXTURE_BLUE_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/bird/blue.png");
	private static final ResourceLocation TEXTURE_BROWN_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/bird/brown.png");
	private static final ResourceLocation TEXTURE_GREEN_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/bird/green.png");
	private static final ResourceLocation TEXTURE_RED_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/bird/red.png");
	private static final ResourceLocation TEXTURE_RUBY_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/bird/ruby.png");

	public BirdEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new BirdEntityModel());
		this.shadowRadius = 0.4F;
	}

	@Override
	public void renderEarly(BirdEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
	}

	@Override
	public ResourceLocation getTextureLocation(BirdEntity entity) {
		switch (entity.getBirdType()) {
		case 0:
		default:
			return TEXTURE_BLACK_LOCATION;
		case 1:
			return TEXTURE_BROWN_LOCATION;
		case 2:
			return TEXTURE_BLUE_LOCATION;
		case 3:
			return TEXTURE_GREEN_LOCATION;
		case 4:
			return TEXTURE_RED_LOCATION;
		case 99:
			return TEXTURE_RUBY_LOCATION;
		}
	}

	@Override
	public RenderType getRenderType(BirdEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderRecursively(GeoBone bone, MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
}
