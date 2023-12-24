package io.github.chaosawakens.client.renderers.layers.texture;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.hostile.robo.RoboSniperEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

//TODO Make base texture layer class
public class RoboSniperGlowLayer extends GeoLayerRenderer<RoboSniperEntity> {
	private static final ResourceLocation GLOW = ChaosAwakens.prefix("textures/entity/layer/glow/robo_sniper_glow.png");
	private static final ResourceLocation MODEL = ChaosAwakens.prefix("geo/entity/hostile/robo/robo_sniper.geo.json");

	public RoboSniperGlowLayer(IGeoRenderer<RoboSniperEntity> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, RoboSniperEntity entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!entityLivingBaseIn.isDeadOrDying()) {
			final RenderType renderType = RenderType.eyes(GLOW);
			this.getRenderer().render(getEntityModel().getModel(MODEL), entityLivingBaseIn, partialTicks, renderType, matrixStackIn, bufferIn, bufferIn.getBuffer(renderType), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
		}
	}
}
