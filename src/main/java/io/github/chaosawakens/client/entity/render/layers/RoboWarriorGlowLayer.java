package io.github.chaosawakens.client.entity.render.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.robo.RoboWarriorEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class RoboWarriorGlowLayer extends GeoLayerRenderer<RoboWarriorEntity> {
	public static final ResourceLocation GLOW = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/robos/robo_warrior_glow.png");
	public static final ResourceLocation MODEL = new ResourceLocation(ChaosAwakens.MODID, "geo/robo_warrior.geo.json");

	public RoboWarriorGlowLayer(IGeoRenderer<RoboWarriorEntity> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, RoboWarriorEntity entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!entityLivingBaseIn.isDeadOrDying()) {
			RenderType renderType = RenderType.eyes(GLOW);
			this.getRenderer().render(this.getEntityModel().getModel(MODEL), entityLivingBaseIn, partialTicks, renderType, matrixStackIn, bufferIn, bufferIn.getBuffer(renderType), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
		}
	}
}
