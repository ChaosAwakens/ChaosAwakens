package io.github.chaosawakens.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.model.FrogEntityModel;
import io.github.chaosawakens.common.entity.FrogEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FrogEntityRender extends GeoEntityRenderer<FrogEntity> {
	private static final ResourceLocation TEXTURE_GREEN_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/frog/green.png");
	private static final ResourceLocation TEXTURE_BROWN_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/frog/brown.png");
	private static final ResourceLocation TEXTURE_PINK_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/frog/pink.png");
	private static final ResourceLocation TEXTURE_DARK_GREEN_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/frog/dark_green.png");
	private static final ResourceLocation TEXTURE_RED_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/frog/red.png");
	private static final ResourceLocation TEXTURE_ORANGE_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/frog/orange.png");
	private static final ResourceLocation TEXTURE_PALE_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/frog/pale.png");
	private static final ResourceLocation TEXTURE_YELLOW_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/frog/yellow.png");
	private static final ResourceLocation TEXTURE_FROAKIE_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/frog/froakie.png");
	private static final ResourceLocation TEXTURE_BLACK_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/frog/black.png");

	public FrogEntityRender(EntityRendererManager renderManager) {
		super(renderManager, new FrogEntityModel());
		this.shadowRadius = 0.4F;
	}

	@Override
	public void renderEarly(FrogEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
	}

	@Override
	public ResourceLocation getTextureLocation(FrogEntity entity) {
		String s = TextFormatting.stripFormatting(entity.getName().getString());
		if ("Froakie".equalsIgnoreCase(s)) {
			return TEXTURE_FROAKIE_LOCATION;
		} else {
			switch (entity.getFrogType()) {
			case 0:
			default:
				return TEXTURE_GREEN_LOCATION;
			case 1:
				return TEXTURE_BROWN_LOCATION;
			case 2:
				return TEXTURE_PINK_LOCATION;
			case 3:
				return TEXTURE_DARK_GREEN_LOCATION;
			case 4:
				return TEXTURE_RED_LOCATION;
			case 5:
				return TEXTURE_ORANGE_LOCATION;
			case 6:
				return TEXTURE_PALE_LOCATION;
			case 7:
				return TEXTURE_YELLOW_LOCATION;
			case 99:
				return TEXTURE_BLACK_LOCATION;
			}
		}
	}

	@Override
	public RenderType getRenderType(FrogEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderRecursively(GeoBone bone, MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
}