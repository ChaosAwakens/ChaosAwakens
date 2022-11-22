package io.github.chaosawakens.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.CABoatEntity;
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

//Dunno how I can use GeckoLib stuff so I'm going with vanilla rendering until I figure out how to use GeckoLib and stuff --Meme Man
public class CABoatRenderer extends EntityRenderer<CABoatEntity> {
	public static final ResourceLocation APPLE_BOAT = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/boats/apple_boat.png");
	public static final ResourceLocation CHERRY_BOAT = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/boats/cherry_boat.png");
	public static final ResourceLocation DUPLICATOR_BOAT = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/boats/duplication_boat.png");
	public static final ResourceLocation GINKGO_BOAT = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/boats/ginkgo_boat.png");
	public static final ResourceLocation PEACH_BOAT = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/boats/peach_boat.png");
	public static final ResourceLocation SKYWOOD_BOAT = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/boats/skywood_boat.png");
	protected final BoatModel model = new BoatModel();

	public CABoatRenderer(EntityRendererManager manager) {
		super(manager);
		this.shadowRadius = 0.8F;
	}
	
	@Override
	public void render(CABoatEntity boat, float fl1, float fl2, MatrixStack stack, IRenderTypeBuffer buffer, int p_225623_6_) {	 
		stack.pushPose();	  
		stack.translate(0.0D, 0.375D, 0.0D);
		stack.mulPose(Vector3f.YP.rotationDegrees(180.0F - fl1));		   
		float f = (float)boat.getHurtTime() - fl2;		   
		float f1 = boat.getDamage() - fl2;
		if (f1 < 0.0F) {		  
			f1 = 0.0F;		  
		}		  
		if (f > 0.0F) {		  
			stack.mulPose(Vector3f.XP.rotationDegrees(MathHelper.sin(f) * f * f1 / 10.0F * (float)boat.getHurtDir()));	   
		}	  
		float f2 = boat.getBubbleAngle(fl2);	   
		if (!MathHelper.equal(f2, 0.0F)) {		    
			stack.mulPose(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), boat.getBubbleAngle(fl2), true));	    
		}	   
		stack.scale(-1.0F, -1.0F, 1.0F);		   
		stack.mulPose(Vector3f.YP.rotationDegrees(90.0F));		   
		this.model.setupAnim(boat, fl2, 0.0F, -0.1F, 0.0F, 0.0F);		    
		IVertexBuilder ivertexbuilder = buffer.getBuffer(this.model.renderType(this.getTextureLocation(boat)));		   
		this.model.renderToBuffer(stack, ivertexbuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);		    
		if (!boat.isUnderWater()) {		   
			IVertexBuilder ivertexbuilder1 = buffer.getBuffer(RenderType.waterMask());		  
			this.model.waterPatch().render(stack, ivertexbuilder1, p_225623_6_, OverlayTexture.NO_OVERLAY);		   
		}
		stack.popPose();		   
		super.render(boat, fl1, fl2, stack, buffer, p_225623_6_);	   
	}

	@Override
	public ResourceLocation getTextureLocation(CABoatEntity boat) {
		switch (boat.getBoatWoodType()) {
		default:
			return APPLE_BOAT;
		case "apple":
			return APPLE_BOAT;
		case "cherry":
			return CHERRY_BOAT;
		case "duplication":
			return DUPLICATOR_BOAT;
		case "ginkgo":
			return GINKGO_BOAT;
		case "peach":
			return PEACH_BOAT;
		case "skywood":
			return SKYWOOD_BOAT;
		}
	}
}
