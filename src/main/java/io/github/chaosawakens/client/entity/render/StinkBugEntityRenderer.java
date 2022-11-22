package io.github.chaosawakens.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.model.StinkBugEntityModel;
import io.github.chaosawakens.common.entity.StinkBugEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class StinkBugEntityRenderer extends GeoEntityRenderer<StinkBugEntity> {
	private static final ResourceLocation TEXTURE_GREEN_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/stink_bug/green.png");
	private static final ResourceLocation TEXTURE_BLUE_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/stink_bug/blue.png");
	private static final ResourceLocation TEXTURE_BROWN_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/stink_bug/brown.png");
	private static final ResourceLocation TEXTURE_CYAN_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/stink_bug/cyan.png");
	private static final ResourceLocation TEXTURE_GRAY_BLUE_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/stink_bug/gray_blue.png");
	private static final ResourceLocation TEXTURE_GRAY_YELLOW_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/stink_bug/gray_yellow.png");
	private static final ResourceLocation TEXTURE_THROWBACK_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/stink_bug/throwback.png");
	
	public StinkBugEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new StinkBugEntityModel());
		this.shadowRadius = 0.4F;
	}

	@Override
	public void renderEarly(StinkBugEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
	}

	@Override
	public ResourceLocation getTextureLocation(StinkBugEntity entity) {
		switch (entity.getStinkBugType()) {
			case 0:
			default:
				return TEXTURE_GREEN_LOCATION;
			case 1:
				return TEXTURE_BLUE_LOCATION;
			case 2:
				return TEXTURE_BROWN_LOCATION;
			case 3:
				return TEXTURE_CYAN_LOCATION;
			case 4:
				return TEXTURE_GRAY_BLUE_LOCATION;
			case 5:
				return TEXTURE_GRAY_YELLOW_LOCATION;
			case 6:
				return TEXTURE_THROWBACK_LOCATION;
		}
	}

	@Override
	public RenderType getRenderType(StinkBugEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderRecursively(GeoBone bone, MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
}
