package io.github.chaosawakens.client.renderers.entity.misc;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CAEmptyRenderer extends EntityRenderer<Entity> {

	public CAEmptyRenderer(EntityRendererManager manager) {
		super(manager);
	}

	@Override
	public ResourceLocation getTextureLocation(Entity p_110775_1_) {
		return null;
	}
	
	@Override
	public void render(Entity owner, float yRot, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int light) {
	}
	
	@Override
	protected void renderNameTag(Entity owner, ITextComponent nametagComponent, MatrixStack stack, IRenderTypeBuffer buffer, int light) {
	}
	
	@Override
	public boolean shouldRender(Entity pLivingEntity, ClippingHelper pCamera, double pCamX, double pCamY, double pCamZ) {
		return false;
	}
}
