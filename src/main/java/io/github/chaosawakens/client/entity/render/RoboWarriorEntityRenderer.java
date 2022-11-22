package io.github.chaosawakens.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.model.RoboWarriorEntityModel;
import io.github.chaosawakens.client.entity.render.layers.RoboWarriorGlowLayer;
import io.github.chaosawakens.common.entity.robo.RoboWarriorEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RoboWarriorEntityRenderer extends GeoEntityRenderer<RoboWarriorEntity> {
	public RoboWarriorEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new RoboWarriorEntityModel());
		this.shadowRadius = 0.4F;
		this.addLayer(new RoboWarriorGlowLayer(this));
	}

	@Override
	public void renderEarly(RoboWarriorEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
	}

	@Override
	public ResourceLocation getTextureLocation(RoboWarriorEntity entity) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/robos/robo_warrior.png");
	}

	@Override
	public RenderType getRenderType(RoboWarriorEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderRecursively(GeoBone bone, MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
}
