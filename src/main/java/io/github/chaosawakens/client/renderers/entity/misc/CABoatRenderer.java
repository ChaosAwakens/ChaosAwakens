package io.github.chaosawakens.client.renderers.entity.misc;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.misc.CABoatEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BoatModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class CABoatRenderer extends EntityRenderer<CABoatEntity> {
	public static final ResourceLocation APPLE_BOAT = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/boats/apple_boat.png");
	public static final ResourceLocation CHERRY_BOAT = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/boats/cherry_boat.png");
	public static final ResourceLocation DUPLICATOR_BOAT = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/boats/duplication_boat.png");
	public static final ResourceLocation GINKGO_BOAT = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/boats/ginkgo_boat.png");
	public static final ResourceLocation MESOZOIC_BOAT = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/boats/mesozoic_boat.png");
	public static final ResourceLocation DENSEWOOD_BOAT = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/boats/densewood_boat.png");
	public static final ResourceLocation PEACH_BOAT = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/boats/peach_boat.png");
	public static final ResourceLocation SKYWOOD_BOAT = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/boats/skywood_boat.png");
	protected final BoatModel boatModel = new BoatModel();

	public CABoatRenderer(EntityRendererManager manager) {
		super(manager);
		this.shadowRadius = 0.8F;
	}
	
	@Override
	public void render(CABoatEntity boat, float yaw, float partialTick, MatrixStack stack, IRenderTypeBuffer buffer, int packedLight) {	 
		stack.pushPose();	  
		stack.translate(0.0D, 0.375D, 0.0D);
		stack.mulPose(Vector3f.YP.rotationDegrees(180.0F - yaw));
		
		float hurtDelta = (float) boat.getHurtTime() - partialTick;		   
		float damageDelta = boat.getDamage() - partialTick;
		
		if (damageDelta < 0.0F) damageDelta = 0.0F;
		if (hurtDelta > 0.0F) stack.mulPose(Vector3f.XP.rotationDegrees(MathHelper.sin(hurtDelta) * hurtDelta * damageDelta / 10.0F * (float) boat.getHurtDir()));
		
		float bubbleAngle = boat.getBubbleAngle(partialTick);
		
		if (!MathHelper.equal(bubbleAngle, 0.0F)) stack.mulPose(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), boat.getBubbleAngle(partialTick), true));
		
		stack.scale(-1.0F, -1.0F, 1.0F);		   
		stack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
		boatModel.setupAnim(boat, partialTick, 0.0F, -0.1F, 0.0F, 0.0F);
		
		IVertexBuilder textureBuffer = buffer.getBuffer(boatModel.renderType(getTextureLocation(boat)));
		
		boatModel.renderToBuffer(stack, textureBuffer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		
		if (!boat.isUnderWater()) {		   
			IVertexBuilder waterMaskBuffer = buffer.getBuffer(RenderType.waterMask());		  
			boatModel.waterPatch().render(stack, waterMaskBuffer, packedLight, OverlayTexture.NO_OVERLAY);		   
		}
		
		stack.popPose();		   
		super.render(boat, yaw, partialTick, stack, buffer, packedLight);	   
	}

	@Override
	public ResourceLocation getTextureLocation(CABoatEntity boat) {
		return ChaosAwakens.prefix("textures/entity/boats/" + boat.getBoatWoodType().substring(boat.getBoatWoodType().indexOf(":") + 1) + "_boat.png");
	}
}
