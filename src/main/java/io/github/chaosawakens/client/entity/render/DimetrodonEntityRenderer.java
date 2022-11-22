package io.github.chaosawakens.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.model.DimetrodonEntityModel;
import io.github.chaosawakens.common.entity.DimetrodonEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DimetrodonEntityRenderer extends GeoEntityRenderer<DimetrodonEntity> {
	private static final ResourceLocation TEXTURE_GREEN_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/dimetrodon/green.png");
	private static final ResourceLocation TEXTURE_ORANGE_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/dimetrodon/orange.png");
	private static final ResourceLocation TEXTURE_PURPLE_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/dimetrodon/purple.png");
	private static final ResourceLocation TEXTURE_THROWBACK_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/dimetrodon/throwback.png");

	public DimetrodonEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new DimetrodonEntityModel());
		this.shadowRadius = 0.4F;
	}

	@Override
	public void renderEarly(DimetrodonEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
	}

	@Override
	public ResourceLocation getTextureLocation(DimetrodonEntity entity) {
		switch (entity.getDimetrodonType()) {
		case 0:
		default:
			return TEXTURE_GREEN_LOCATION;
		case 1:
			return TEXTURE_ORANGE_LOCATION;
		case 2:
			return TEXTURE_PURPLE_LOCATION;
		case 3:
			return TEXTURE_THROWBACK_LOCATION;
		}
	}

	@Override
	public RenderType getRenderType(DimetrodonEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderRecursively(GeoBone bone, MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
}
