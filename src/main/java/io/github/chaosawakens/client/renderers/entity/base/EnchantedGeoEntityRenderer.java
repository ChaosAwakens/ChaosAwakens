package io.github.chaosawakens.client.renderers.entity.base;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.client.renderers.layers.glint.GeoEntityGlintLayer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public abstract class EnchantedGeoEntityRenderer<E extends LivingEntity & IAnimatableEntity> extends ExtendedGeoEntityRenderer<E> {

	public EnchantedGeoEntityRenderer(EntityRendererManager renderManager, AnimatedGeoModel<E> modelProvider) {
		super(renderManager, modelProvider);
	}
	
	@Override
	protected ObjectArrayList<GeoLayerRenderer<E>> getLayers() {
		ObjectArrayList<GeoLayerRenderer<E>> renderLayers = new ObjectArrayList<GeoLayerRenderer<E>>();
		renderLayers.add(new GeoEntityGlintLayer<E>(this, modelProvider.getModelLocation(animatable)));
		return renderLayers;
	}
}
