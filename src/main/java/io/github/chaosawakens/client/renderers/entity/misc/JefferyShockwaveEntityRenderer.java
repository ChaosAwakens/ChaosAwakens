package io.github.chaosawakens.client.renderers.entity.misc;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.client.models.entity.misc.JefferyShockwaveEntityModel;
import io.github.chaosawakens.common.entity.misc.JefferyShockwaveEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

import javax.annotation.Nullable;

public class JefferyShockwaveEntityRenderer extends GeoProjectilesRenderer<JefferyShockwaveEntity> {

	public JefferyShockwaveEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new JefferyShockwaveEntityModel());
	}
	
	@Override
	public RenderType getRenderType(JefferyShockwaveEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderEarly(JefferyShockwaveEntity animatable, MatrixStack stackIn, float partialTicks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		float horizontalScale = 1;
		horizontalScale = (float) (horizontalScale * animatable.getRadius() / 3.2 * 0.328754F);
		
		stackIn.scale(horizontalScale, horizontalScale / 1.4654336F, horizontalScale);

		super.renderEarly(animatable, stackIn, partialTicks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
	
	@Override
	public float getHeightScale(JefferyShockwaveEntity entity) {
		return super.getHeightScale(entity);
	}
	
	@Override
	public float getWidthScale(JefferyShockwaveEntity animatable2) {
		return super.getWidthScale(animatable2);
	}

	@Override
	public Color getRenderColor(JefferyShockwaveEntity animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn) {
		float curAlpha = 1.0F;

		curAlpha = 1.0F - ((float) animatable.tickCount / animatable.getMaxAge()) * 2.5F;

		return Color.ofRGBA(1, 1, 1, MathHelper.clamp(curAlpha, 0, 1));
	}
}