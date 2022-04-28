package io.github.chaosawakens.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.model.CrystalGatorModel;
import io.github.chaosawakens.common.entity.CrystalGatorEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CrystalGatorRender extends GeoEntityRenderer<CrystalGatorEntity> {
	private static final ResourceLocation TEXTURE_BLUE_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/gator/blue.png");
	private static final ResourceLocation TEXTURE_RED_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/gator/red.png");
	private static final ResourceLocation TEXTURE_YELLOW_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/gator/yellow.png");
	private static final ResourceLocation TEXTURE_ORANGE_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/gator/orange.png");
	private static final ResourceLocation TEXTURE_PINK_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/gator/pink.png");
	private static final ResourceLocation TEXTURE_GREEN_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/gator/green.png");

	public CrystalGatorRender(EntityRendererManager renderManager) {
		super(renderManager, new CrystalGatorModel());
		this.shadowRadius = 0;
	}

	@Override
	public void renderEarly(CrystalGatorEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
	}

	@Override
	public ResourceLocation getTextureLocation(CrystalGatorEntity entity) {
		switch (entity.getGatorType()) {
		case 0:
		default:
			return TEXTURE_BLUE_LOCATION;
		case 1:
			return TEXTURE_RED_LOCATION;
		case 2:
			return TEXTURE_YELLOW_LOCATION;
		case 3:
			return TEXTURE_ORANGE_LOCATION;
		case 4:
			return TEXTURE_PINK_LOCATION;
		case 5:
			return TEXTURE_GREEN_LOCATION;
		}
	}

	@Override
	public RenderType getRenderType(CrystalGatorEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderRecursively(GeoBone bone, MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
}
