package io.github.chaosawakens.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

@Mixin(RabbitRenderer.class)
public abstract class RabbitEntityRendererMixin {
	private static final ResourceLocation RABBIT_OREO_LOCATION = ChaosAwakens.prefix("textures/entity/rabbit/oreo.png");

	private RabbitEntityRendererMixin() {
		throw new IllegalAccessError("Attempted to instantiate a Mixin Class!");
	}

	@Inject(method = "getTextureLocation(Lnet/minecraft/entity/passive/RabbitEntity;)Lnet/minecraft/util/ResourceLocation;", at = @At("HEAD"), cancellable = true)
	private void chaosawakens$getTextureLocation(RabbitEntity rabbitEntity, CallbackInfoReturnable<ResourceLocation> cir) {
		String formattedName = TextFormatting.stripFormatting(rabbitEntity.getName().getString());
		
		if (formattedName.equalsIgnoreCase("Oreo")) cir.setReturnValue(RABBIT_OREO_LOCATION);
	}
}