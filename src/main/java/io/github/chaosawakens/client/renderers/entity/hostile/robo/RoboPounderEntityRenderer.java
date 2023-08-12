package io.github.chaosawakens.client.renderers.entity.hostile.robo;

import java.util.Optional;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.client.models.entity.hostile.robo.RoboPounderEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.hostile.robo.RoboPounderEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.particles.ParticleTypes;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class RoboPounderEntityRenderer extends ExtendedGeoEntityRenderer<RoboPounderEntity> {

	public RoboPounderEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new RoboPounderEntityModel());
	}

	@Override
	protected boolean shouldRotateOnDeath() {
		return false;
	}

	@Override
	protected float getShadowRadius() {
		return 0.9F;
	}

	@Override
	public void render(GeoModel model, RoboPounderEntity animatable, float partialTicks, RenderType type, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);

		Optional<GeoBone> leftLeg = model.getBone("LeftLeg2");
		Optional<GeoBone> rightLeg = model.getBone("RightLeg2");
		Optional<GeoBone> leftHatch = model.getBone("LeftHatch");
		Optional<GeoBone> rightHatch = model.getBone("RightHatch");
		Optional<GeoBone> leftBackHatch = model.getBone("LeftBackHatch");
		Optional<GeoBone> rightBackHatch = model.getBone("RightBackHatch");
		Optional<GeoBone> headArmor = model.getBone("Head2");
		IAnimationBuilder rageCrashAnim = animatable.getCachedAnimationByName("Rage Crash");
		IAnimationBuilder cooldownAnim = animatable.getCachedAnimationByName("Cooldown");
		IAnimationBuilder deathAnim = animatable.getCachedAnimationByName("Death");
		
		if (animatable.isPlayingAnimation(rageCrashAnim)) {
			if (leftBackHatch.isPresent() && rightBackHatch.isPresent()) {
				animatable.getCommandSenderWorld().addParticle(
						ParticleTypes.CAMPFIRE_COSY_SMOKE,
						leftBackHatch.get().getWorldPosition().x,
						leftBackHatch.get().getWorldPosition().y,
						leftBackHatch.get().getWorldPosition().z,
						0,
						0.04D,
						0);

				animatable.getCommandSenderWorld().addParticle(
						ParticleTypes.CAMPFIRE_COSY_SMOKE,
						rightBackHatch.get().getWorldPosition().x,
						rightBackHatch.get().getWorldPosition().y,
						rightBackHatch.get().getWorldPosition().z,
						0,
						0.04D,
						0);
			}
		}

		if ((animatable.getAttackID() == (byte) 3 || animatable.isPlayingAnimation(cooldownAnim)) && animatable.isMoving()) {
			if (leftLeg.isPresent() && rightLeg.isPresent()) {
				animatable.getCommandSenderWorld().addParticle(
						ParticleTypes.CAMPFIRE_COSY_SMOKE,
						leftLeg.get().getWorldPosition().x - 1,
						leftLeg.get().getWorldPosition().y - 2,
						leftLeg.get().getWorldPosition().z - 1,
						0,
						0.02D,
						0);
				animatable.getCommandSenderWorld().addParticle(
						ParticleTypes.CAMPFIRE_COSY_SMOKE,
						leftLeg.get().getWorldPosition().x + 1,
						leftLeg.get().getWorldPosition().y - 2,
						leftLeg.get().getWorldPosition().z + 1,
						0,
						0.02D,
						0);

				animatable.getCommandSenderWorld().addParticle(
						ParticleTypes.CAMPFIRE_COSY_SMOKE,
						rightLeg.get().getWorldPosition().x - 1,
						rightLeg.get().getWorldPosition().y - 2,
						rightLeg.get().getWorldPosition().z - 1,
						0,
						0.02D,
						0);
				animatable.getCommandSenderWorld().addParticle(
						ParticleTypes.CAMPFIRE_COSY_SMOKE,
						rightLeg.get().getWorldPosition().x + 1,
						rightLeg.get().getWorldPosition().y - 2,
						rightLeg.get().getWorldPosition().z + 1,
						0,
						0.02D,
						0);
			}
		}

		if (deathAnim.getWrappedAnimProgress() == 30) {
			if (leftHatch.isPresent() && rightHatch.isPresent() && leftBackHatch.isPresent() && rightBackHatch.isPresent()) {
				animatable.getCommandSenderWorld().addParticle(
						ParticleTypes.EXPLOSION,
						true,
						leftHatch.get().getWorldPosition().x,
						leftHatch.get().getWorldPosition().y,
						leftHatch.get().getWorldPosition().z,
						1.0D,
						0,
						1.0D);

				animatable.getCommandSenderWorld().addParticle(
						ParticleTypes.EXPLOSION,
						true,
						rightHatch.get().getWorldPosition().x,
						rightHatch.get().getWorldPosition().y,
						rightHatch.get().getWorldPosition().z,
						1.0D,
						0,
						1.0D);
				
				animatable.getCommandSenderWorld().addParticle(
						ParticleTypes.EXPLOSION,
						true,
						leftBackHatch.get().getWorldPosition().x,
						leftBackHatch.get().getWorldPosition().y,
						leftBackHatch.get().getWorldPosition().z,
						1.0D,
						0,
						1.0D);

				animatable.getCommandSenderWorld().addParticle(
						ParticleTypes.EXPLOSION,
						true,
						rightBackHatch.get().getWorldPosition().x,
						rightBackHatch.get().getWorldPosition().y,
						rightBackHatch.get().getWorldPosition().z,
						1.0D,
						0,
						1.0D);
			}
			
			if (headArmor.isPresent()) {
				animatable.getCommandSenderWorld().addParticle(
						ParticleTypes.EXPLOSION,
						true,
						headArmor.get().getWorldPosition().x,
						headArmor.get().getWorldPosition().y,
						headArmor.get().getWorldPosition().z,
						1.0D,
						0,
						1.0D);
			}
		}
		
		if (animatable.getHealth() <= 50.0F && !animatable.isDeadOrDying()) {
			if (leftHatch.isPresent() && rightHatch.isPresent() && leftBackHatch.isPresent() && rightBackHatch.isPresent()) {
				animatable.getCommandSenderWorld().addParticle(
						ParticleTypes.SMOKE,
						leftHatch.get().getWorldPosition().x + 0.08D,
						leftHatch.get().getWorldPosition().y,
						leftHatch.get().getWorldPosition().z + 0.04D,
						0,
						0.05D,
						0);

				animatable.getCommandSenderWorld().addParticle(
						ParticleTypes.SMOKE,
						rightHatch.get().getWorldPosition().x - 0.01D,
						rightHatch.get().getWorldPosition().y,
						rightHatch.get().getWorldPosition().z - 0.04D,
						0,
						0.05D,
						0);
				
				animatable.getCommandSenderWorld().addParticle(
						ParticleTypes.LARGE_SMOKE,
						leftBackHatch.get().getWorldPosition().x,
						leftBackHatch.get().getWorldPosition().y,
						leftBackHatch.get().getWorldPosition().z,
						0,
						0.07D,
						0);

				animatable.getCommandSenderWorld().addParticle(
						ParticleTypes.LARGE_SMOKE,
						rightBackHatch.get().getWorldPosition().x,
						rightBackHatch.get().getWorldPosition().y,
						rightBackHatch.get().getWorldPosition().z,
						0,
						0.07D,
						0);
			}
		}		
	}

	@Override
	protected ObjectArrayList<GeoLayerRenderer<RoboPounderEntity>> getLayers() {
		return null;
	}
}
