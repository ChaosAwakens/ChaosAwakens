package io.github.chaosawakens.client.renderers.entity.boss.robo;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.client.models.entity.boss.robo.RoboJefferyEntityModel;
import io.github.chaosawakens.client.renderers.entity.base.ExtendedGeoEntityRenderer;
import io.github.chaosawakens.common.entity.boss.robo.RoboJefferyEntity;
import io.github.chaosawakens.common.util.MathUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.particles.ParticleTypes;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

import javax.annotation.Nullable;
import java.util.Optional;

public class RoboJefferyEntityRenderer extends ExtendedGeoEntityRenderer<RoboJefferyEntity> {

	public RoboJefferyEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new RoboJefferyEntityModel());
	}

	@Override
	protected boolean shouldRotateOnDeath() {
		return false;
	}

	@Override
	protected float getShadowRadius() {
		return 2.6F;
	}

	@Override
	protected ObjectArrayList<GeoLayerRenderer<RoboJefferyEntity>> getLayers() {
		return null;
	}

	@Override
	public void render(GeoModel model, RoboJefferyEntity animatable, float partialTicks, RenderType type, MatrixStack matrixStackIn, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);

		Optional<GeoBone> core = model.getBone("Core");
		IAnimationBuilder deathAnim = animatable.getCachedAnimationByName("Death");

		if (MathUtil.isBetween(deathAnim.getWrappedAnimProgress(), 74, 75)) {
            core.ifPresent(geoBone -> animatable.getCommandSenderWorld().addParticle(
                    ParticleTypes.EXPLOSION,
                    true,
                    geoBone.getWorldPosition().x,
                    geoBone.getWorldPosition().y,
                    geoBone.getWorldPosition().z,
                    1.5D,
                    0,
                    1.5D));
		}
	}
}