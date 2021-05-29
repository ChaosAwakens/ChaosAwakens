package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.AppleCowEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AppleCowRender extends MobRenderer<AppleCowEntity, CowModel<AppleCowEntity>> {
	private static final ResourceLocation APPLE_COW_TEXTURES = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/apple_cow.png");
	
	public AppleCowRender(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new CowModel<>(), 0.7F);
	}
	
	@Override
	public ResourceLocation getEntityTexture(AppleCowEntity entity) {
		return APPLE_COW_TEXTURES;
	}
}
