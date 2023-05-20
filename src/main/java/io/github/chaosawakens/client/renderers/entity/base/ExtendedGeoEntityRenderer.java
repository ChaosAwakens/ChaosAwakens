package io.github.chaosawakens.client.renderers.entity.base;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public abstract class ExtendedGeoEntityRenderer<E extends LivingEntity & IAnimatableEntity> extends GeoEntityRenderer<E> {

	public ExtendedGeoEntityRenderer(EntityRendererManager renderManager, AnimatedGeoModel<E> modelProvider) {
		super(renderManager, modelProvider);
		this.shadowRadius = getShadowRadius();
		
		if (getLayers() != null && !getLayers().isEmpty()) {
			for (GeoLayerRenderer<E> renderLayer : getLayers()) {
				if (renderLayer != null && !this.layerRenderers.contains(renderLayer)) this.addLayer(renderLayer);
			}
		}
	}
	
	protected abstract boolean shouldRotateOnDeath();
	
	protected abstract float getShadowRadius();
	
	@Nullable
	protected abstract ObjectArrayList<GeoLayerRenderer<E>> getLayers();
	
	@Override
	protected float getDeathMaxRotation(E entityLivingBaseIn) {
		return shouldRotateOnDeath() ? super.getDeathMaxRotation(entityLivingBaseIn) : 0.0F;
	}
	
	@Override
	public RenderType getRenderType(E animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
