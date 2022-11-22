package io.github.chaosawakens.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.projectile.UltimateFishingBobberEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class UltimateBobberProjectileRenderer extends EntityRenderer<UltimateFishingBobberEntity> {
	private static final ResourceLocation BOBBER = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/ultimate_fishing_hook.png");
	private static final RenderType RENDER_TYPE = RenderType.entityCutout(BOBBER);

	public UltimateBobberProjectileRenderer(EntityRendererManager manager) {
		super(manager);
	}

	@SuppressWarnings("unused")
	@Override
	public void render(@Nonnull UltimateFishingBobberEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
		PlayerEntity playerentity = p_225623_1_.getPlayerOwner();
		if (playerentity != null) {
			p_225623_4_.pushPose();
			p_225623_4_.pushPose();
			p_225623_4_.scale(0.5F, 0.5F, 0.5F);
			p_225623_4_.mulPose(this.entityRenderDispatcher.cameraOrientation());
			p_225623_4_.mulPose(Vector3f.YP.rotationDegrees(180.0F));
			MatrixStack.Entry matrixstack$entry = p_225623_4_.last();
			Matrix4f matrix4f = matrixstack$entry.pose();
			Matrix3f matrix3f = matrixstack$entry.normal();
			IVertexBuilder ivertexbuilder = p_225623_5_.getBuffer(RENDER_TYPE);
			vertex(ivertexbuilder, matrix4f, matrix3f, p_225623_6_, 0.0F, 0, 0, 1);
			vertex(ivertexbuilder, matrix4f, matrix3f, p_225623_6_, 1.0F, 0, 1, 1);
			vertex(ivertexbuilder, matrix4f, matrix3f, p_225623_6_, 1.0F, 1, 1, 0);
			vertex(ivertexbuilder, matrix4f, matrix3f, p_225623_6_, 0.0F, 1, 0, 0);
			p_225623_4_.popPose();
			int i = playerentity.getMainArm() == HandSide.RIGHT ? 1 : -1;
			ItemStack itemstack = playerentity.getMainHandItem();
			if (itemstack.getItem() != Items.FISHING_ROD) {
				i = -i;
			}

			float f = playerentity.getAttackAnim(p_225623_3_);
			float f1 = MathHelper.sin(MathHelper.sqrt(f) * (float) Math.PI);
			float f2 = MathHelper.lerp(p_225623_3_, playerentity.yBodyRotO, playerentity.yBodyRot) * ((float) Math.PI / 180F);
			double d0 = MathHelper.sin(f2);
			double d1 = MathHelper.cos(f2);
			double d2 = (double) i * 0.35D;
			double d3 = 0.8D;
			double d4;
			double d5;
			double d6;
			float f3;
			if ((this.entityRenderDispatcher.options == null || this.entityRenderDispatcher.options.getCameraType().isFirstPerson()) && playerentity == Minecraft.getInstance().player) {
				double d7 = this.entityRenderDispatcher.options.fov;
				d7 = d7 / 100.0D;
				Vector3d vector3d = new Vector3d((double) i * -0.36D * d7, -0.045D * d7, 0.4D);
				vector3d = vector3d.xRot(-MathHelper.lerp(p_225623_3_, playerentity.xRotO, playerentity.xRot) * ((float) Math.PI / 180F));
				vector3d = vector3d.yRot(-MathHelper.lerp(p_225623_3_, playerentity.yRotO, playerentity.yRot) * ((float) Math.PI / 180F));
				vector3d = vector3d.yRot(f1 * 0.5F);
				vector3d = vector3d.xRot(-f1 * 0.7F);
				d4 = MathHelper.lerp(p_225623_3_, playerentity.xo, playerentity.getX()) + vector3d.x;
				d5 = MathHelper.lerp(p_225623_3_, playerentity.yo, playerentity.getY()) + vector3d.y;
				d6 = MathHelper.lerp(p_225623_3_, playerentity.zo, playerentity.getZ()) + vector3d.z;
				f3 = playerentity.getEyeHeight();
			} else {
				d4 = MathHelper.lerp(p_225623_3_, playerentity.xo, playerentity.getX()) - d1 * d2 - d0 * 0.8D;
				d5 = playerentity.yo + (double) playerentity.getEyeHeight() + (playerentity.getY() - playerentity.yo) * (double) p_225623_3_ - 0.45D;
				d6 = MathHelper.lerp(p_225623_3_, playerentity.zo, playerentity.getZ()) - d0 * d2 + d1 * 0.8D;
				f3 = playerentity.isCrouching() ? -0.1875F : 0.0F;
			}

			double d9 = MathHelper.lerp(p_225623_3_, p_225623_1_.xo, p_225623_1_.getX());
			double d10 = MathHelper.lerp(p_225623_3_, p_225623_1_.yo, p_225623_1_.getY()) + 0.25D;
			double d8 = MathHelper.lerp(p_225623_3_, p_225623_1_.zo, p_225623_1_.getZ());
			float f4 = (float) (d4 - d9);
			float f5 = (float) (d5 - d10) + f3;
			float f6 = (float) (d6 - d8);
			IVertexBuilder ivertexbuilder1 = p_225623_5_.getBuffer(RenderType.lines());
			Matrix4f matrix4f1 = p_225623_4_.last().pose();
			int j = 16;

			for (int k = 0; k < 16; ++k) {
				stringVertex(f4, f5, f6, ivertexbuilder1, matrix4f1, fraction(k, 16));
				stringVertex(f4, f5, f6, ivertexbuilder1, matrix4f1, fraction(k + 1, 16));
			}

			p_225623_4_.popPose();
			super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
		}
	}

	private static float fraction(int p_229105_0_, int p_229105_1_) {
		return (float) p_229105_0_ / (float) p_229105_1_;
	}

	private static void vertex(IVertexBuilder p_229106_0_, Matrix4f p_229106_1_, Matrix3f p_229106_2_, int p_229106_3_, float p_229106_4_, int p_229106_5_, int p_229106_6_, int p_229106_7_) {
		p_229106_0_.vertex(p_229106_1_, p_229106_4_ - 0.5F, (float) p_229106_5_ - 0.5F, 0.0F).color(255, 255, 255, 255).uv((float) p_229106_6_, (float) p_229106_7_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_229106_3_).normal(p_229106_2_, 0.0F, 1.0F, 0.0F).endVertex();
	}

	private static void stringVertex(float p_229104_0_, float p_229104_1_, float p_229104_2_, IVertexBuilder p_229104_3_, Matrix4f p_229104_4_, float p_229104_5_) {
		p_229104_3_.vertex(p_229104_4_, p_229104_0_ * p_229104_5_, p_229104_1_ * (p_229104_5_ * p_229104_5_ + p_229104_5_) * 0.5F + 0.25F, p_229104_2_ * p_229104_5_).color(0, 0, 0, 255).endVertex();
	}

	public ResourceLocation getEntityTexture(UltimateFishingBobberEntity entity) {
		return BOBBER;
	}

	@Override
	public ResourceLocation getTextureLocation(UltimateFishingBobberEntity p_110775_1_) {
		return BOBBER;
	}
}
