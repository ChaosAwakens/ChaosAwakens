package io.github.chaosawakens.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.client.renderer.entity.model.RabbitModel;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

@Mixin(RabbitRenderer.class)
public abstract class RabbitEntityRendererMixin extends MobRenderer<RabbitEntity, RabbitModel<RabbitEntity>> {
	private static final ResourceLocation RABBIT_OREO_LOCATION = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/rabbit/oreo.png");

	public RabbitEntityRendererMixin(EntityRendererManager rendererManager) {
		super(rendererManager, new RabbitModel<>(), 0.3F);
	}

	@Inject(method = "getTextureLocation(Lnet/minecraft/entity/passive/RabbitEntity;)Lnet/minecraft/util/ResourceLocation;", at = @At("HEAD"), cancellable = true)
	private void chaosawakens$getTextureLocation(RabbitEntity rabbitEntity, CallbackInfoReturnable<ResourceLocation> cir) {
		String formattedName = TextFormatting.stripFormatting(rabbitEntity.getName().getString());
		if (formattedName.equals("Oreo")) cir.setReturnValue(RABBIT_OREO_LOCATION);
	}
}
