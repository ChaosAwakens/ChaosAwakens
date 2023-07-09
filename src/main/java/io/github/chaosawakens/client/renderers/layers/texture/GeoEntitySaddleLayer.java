package io.github.chaosawakens.client.renderers.layers.texture;

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.animation.IAnimatableEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.IRideable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class GeoEntitySaddleLayer<E extends LivingEntity & IAnimatableEntity & IRideable & IEquipable> extends GeoLayerRenderer<E> {
	private static final ResourceLocation SADDLE = ChaosAwakens.prefix("textures/entity/layer/saddle/saddle.png");
	private final ResourceLocation modelLoc;

	public GeoEntitySaddleLayer(IGeoRenderer<E> entityRendererIn, ResourceLocation modelLoc) {
		super(entityRendererIn);
		this.modelLoc = modelLoc;
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, E entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		final RenderType saddleCutoutNoCull = RenderType.armorCutoutNoCull(SADDLE);
		
		if (entityLivingBaseIn.isSaddled()) {
			getRenderer().render(getEntityModel().getModel(modelLoc), entityLivingBaseIn, partialTicks, saddleCutoutNoCull, matrixStackIn, bufferIn, bufferIn.getBuffer(saddleCutoutNoCull), packedLightIn, packedLightIn, packedLightIn, ageInTicks, netHeadYaw, headPitch);
		}
	}
}
