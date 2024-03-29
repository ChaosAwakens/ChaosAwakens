package io.github.chaosawakens.client.entity.render.layers;

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.EnchantedGoldenCarrotPigEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@OnlyIn(Dist.CLIENT)
public class PigGlintLayer extends GeoLayerRenderer<EnchantedGoldenCarrotPigEntity> {
	private static final ResourceLocation MODEL = new ResourceLocation(ChaosAwakens.MODID, "geo/carrot_pig.geo.json");

	public PigGlintLayer(IGeoRenderer<EnchantedGoldenCarrotPigEntity> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, EnchantedGoldenCarrotPigEntity entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		RenderType renderType = RenderType.entityGlint();
		this.getRenderer().render(this.getEntityModel().getModel(MODEL), entityLivingBaseIn, partialTicks, renderType, matrixStackIn, bufferIn, bufferIn.getBuffer(renderType), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
	}
}
