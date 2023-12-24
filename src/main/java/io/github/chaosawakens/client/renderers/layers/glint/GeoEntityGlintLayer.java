package io.github.chaosawakens.client.renderers.layers.glint;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.chaosawakens.api.animation.IAnimatableEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class GeoEntityGlintLayer<E extends LivingEntity & IAnimatableEntity> extends GeoLayerRenderer<E> {
	private final ResourceLocation modelLoc;

	public GeoEntityGlintLayer(IGeoRenderer<E> entityRendererIn, ResourceLocation modelLoc) {
		super(entityRendererIn);
		this.modelLoc = modelLoc;
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, E entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		final RenderType entityGlint = RenderType.entityGlint();
		getRenderer().render(getEntityModel().getModel(modelLoc), entityLivingBaseIn, partialTicks, entityGlint, matrixStackIn, bufferIn, bufferIn.getBuffer(entityGlint), packedLightIn, packedLightIn, packedLightIn, ageInTicks, netHeadYaw, headPitch);
	}
}
