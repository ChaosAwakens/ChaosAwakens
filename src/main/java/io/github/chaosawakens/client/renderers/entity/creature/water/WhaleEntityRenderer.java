package io.github.chaosawakens.client.renderers.entity.creature.water;

import java.util.Optional;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.chaosawakens.client.models.entity.creature.water.WhaleEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.creature.water.WhaleEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class WhaleEntityRenderer extends ExtendedGeoEntityRenderer<WhaleEntity> {
	
	public WhaleEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new WhaleEntityModel());
	}

	@Override
	protected boolean shouldRotateOnDeath() {
		return false;
	}

	@Override
	protected float getShadowRadius() {
		return 3.5F;
	}

	@Override
	protected ObjectArrayList<GeoLayerRenderer<WhaleEntity>> getLayers() {
		return null;
	}
	
	@Override
	public void render(GeoModel model, WhaleEntity animatable, float partialTicks, RenderType type, MatrixStack matrixStackIn,
			@Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn,
			int packedOverlayIn, float red, float green, float blue, float alpha) {
		float yRotDiff = animatable.yRotO - animatable.yRot;
		Optional<GeoBone> root = model.getBone("Whale");
		if (root.isPresent() && yRotDiff != 0) {
			root.get().setRotationZ((float) Math.toRadians(yRotDiff / 4.0f));
		}
		
		Optional<GeoBone> midsection = model.getBone("Midsection");
		if (midsection.isPresent()) {
			midsection.get().setRotationY((float) Math.toRadians(yRotDiff / -1.0f));
		}
		Optional<GeoBone> cube_r3 = model.getBone("cube_r3");
		if (cube_r3.isPresent()) {
			cube_r3.get().setRotationY((float) Math.toRadians(yRotDiff / -1.0f));
		}
		Optional<GeoBone> tail = model.getBone("Tail");
		if (tail.isPresent()) {
			tail.get().setRotationY((float) Math.toRadians(yRotDiff / -1.0f));
		}
		super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn,
				packedOverlayIn, red, green, blue, alpha);
	}
}