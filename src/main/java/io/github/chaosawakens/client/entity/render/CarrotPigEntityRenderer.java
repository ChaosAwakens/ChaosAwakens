package io.github.chaosawakens.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.model.CarrotPigEntityModel;
import io.github.chaosawakens.client.entity.render.layers.CarrotPigSaddleLayer;
import io.github.chaosawakens.common.entity.CarrotPigEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CarrotPigEntityRenderer extends GeoEntityRenderer<CarrotPigEntity> {
	public CarrotPigEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new CarrotPigEntityModel());
		this.addLayer(new CarrotPigSaddleLayer(this));
		this.shadowRadius = 0.7F;
	}

	@Override
	public void renderEarly(CarrotPigEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
	}

	@Override
	public ResourceLocation getTextureLocation(CarrotPigEntity entity) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/carrot_pig.png");
	}

	@Override
	public RenderType getRenderType(CarrotPigEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderRecursively(GeoBone bone, MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
}
