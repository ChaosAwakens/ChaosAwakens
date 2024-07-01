package io.github.chaosawakens.client.renderers.entity.creature.water;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.creature.water.WhaleEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.creature.water.WhaleEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

import javax.annotation.Nullable;
import java.util.Optional;

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
	public void render(GeoModel model, WhaleEntity animatable, float partialTicks, RenderType type, MatrixStack matrixStackIn, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		float yRotDiff = animatable.prevBodyRot - animatable.yRot == 0 ? 0 : MathHelper.clamp(MathHelper.wrapDegrees(animatable.prevBodyRot - animatable.yRot), animatable.yRot - 40, animatable.yRot + 40);
		float targetRotationZ = (float) Math.toRadians(yRotDiff / 3.0F);
        float rotMod = -0.25F;
		float targetRotationY = (float) MathHelper.clamp(MathHelper.wrapDegrees(Math.toRadians(yRotDiff / rotMod)), animatable.yRot - 40, animatable.yRot + 40);

		ChaosAwakens.debug("PREVYROT", animatable.prevBodyRot);
		ChaosAwakens.debug("YROT", animatable.yRot);
		ChaosAwakens.debug("YROTDIFF", yRotDiff);
		ChaosAwakens.debug("TARGETYROT", targetRotationY);

		Optional<GeoBone> root = model.getBone("Whale"); // Adjust: prev > cur || cur > prev and wrap

	//	if (root.isPresent()) root.get().setRotationZ(smoothRotation(root.get().getRotationZ(), targetRotationZ, partialTicks));

		Optional<GeoBone> midsection = model.getBone("Midsection");
		Optional<GeoBone> cube_r3 = model.getBone("cube_r3");
		Optional<GeoBone> tail = model.getBone("Tail");

		midsection.ifPresent(geoBone -> {
			geoBone.setRotationY(MathHelper.wrapDegrees(smoothRotation(geoBone.getRotationY(), targetRotationY, partialTicks)));
			ChaosAwakens.debug("MIDSEC", geoBone.getRotationY() + "||" + Math.toDegrees(geoBone.getRotationY()));
		});
		cube_r3.ifPresent(geoBone -> {
			geoBone.setRotationY(MathHelper.wrapDegrees(smoothRotation(geoBone.getRotationY(), targetRotationY, partialTicks)));
			ChaosAwakens.debug("CUBER3", geoBone.getRotationY() + "||" + Math.toDegrees(geoBone.getRotationY()));
		});
        tail.ifPresent(geoBone -> {
			geoBone.setRotationY(MathHelper.wrapDegrees(smoothRotation(geoBone.getRotationY(), targetRotationY, partialTicks)));
			ChaosAwakens.debug("TAIL", geoBone.getRotationY() + "||" + Math.toDegrees(geoBone.getRotationY()));
		});

		super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);

		if (animatable.tickCount % 20 == 0) animatable.prevBodyRot = animatable.yRot;
	}

	private float smoothRotation(float currentRotation, float targetRotation, float partialTicks) {
		return MathHelper.wrapDegrees(MathHelper.approachDegrees(currentRotation, targetRotation, 0.30F));
	}
}