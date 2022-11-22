package io.github.chaosawakens.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.model.CrystalCarrotPigEntityModel;
import io.github.chaosawakens.client.entity.render.layers.CrystalCarrotPigSaddleLayer;
import io.github.chaosawakens.common.entity.CrystalCarrotPigEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CrystalCarrotPigEntityRenderer extends GeoEntityRenderer<CrystalCarrotPigEntity> {
	public CrystalCarrotPigEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new CrystalCarrotPigEntityModel());
		this.addLayer(new CrystalCarrotPigSaddleLayer(this));
		this.shadowRadius = 0;
	}

	@Override
	public void renderEarly(CrystalCarrotPigEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
	}

	@Override
	public ResourceLocation getTextureLocation(CrystalCarrotPigEntity entity) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/crystal/crystal_carrot_pig.png");
	}

	@Override
	public RenderType getRenderType(CrystalCarrotPigEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderRecursively(GeoBone bone, MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
}
