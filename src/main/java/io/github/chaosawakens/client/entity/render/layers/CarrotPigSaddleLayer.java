package io.github.chaosawakens.client.entity.render.layers;

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.CarrotPigEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

//TODO make the other carrot pig classes extend CarrotPigEntity
public class CarrotPigSaddleLayer extends GeoLayerRenderer<CarrotPigEntity> {
	private static final ResourceLocation MODEL = new ResourceLocation(ChaosAwakens.MODID, "geo/carrot_pig.geo.json");
	private static final ResourceLocation SADDLE = new ResourceLocation(ChaosAwakens.MODID,
			"textures/entity/pigs/pig_saddle.png");

	public CarrotPigSaddleLayer(IGeoRenderer<CarrotPigEntity> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
			CarrotPigEntity entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch) {
		RenderType renderType = RenderType.armorCutoutNoCull(SADDLE);
		if (entityLivingBaseIn.isSaddled()) {
			this.getRenderer().render(this.getEntityModel().getModel(MODEL), entityLivingBaseIn, partialTicks,
					renderType, matrixStackIn, bufferIn, bufferIn.getBuffer(renderType), packedLightIn,
					OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
		}
	}
}