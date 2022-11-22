package io.github.chaosawakens.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.model.AppleCowEntityModel;
import io.github.chaosawakens.common.entity.AppleCowEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class AppleCowEntityRenderer extends GeoEntityRenderer<AppleCowEntity> {
	private static final ResourceLocation TEXTURE_APPLE_COW_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/apple_cow/apple_cow.png");
	private static final ResourceLocation TEXTURE_HALLOWEEN_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/apple_cow/halloween.png");

	public AppleCowEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new AppleCowEntityModel());
		this.shadowRadius = 0.7F;
	}

	@Override
	public void renderEarly(AppleCowEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
	}

	@Override
	public ResourceLocation getTextureLocation(AppleCowEntity entity) {
		switch (entity.getAppleCowType()) {
		case 0:
		default:
			return TEXTURE_APPLE_COW_LOCATION;
		case 1:
			return TEXTURE_HALLOWEEN_LOCATION;
		}
	}

	@Override
	public RenderType getRenderType(AppleCowEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderRecursively(GeoBone bone, MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
}
