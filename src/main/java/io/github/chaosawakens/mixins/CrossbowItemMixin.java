package io.github.chaosawakens.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.chaosawakens.common.items.weapons.ranged.UltimateCrossbowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {

	private CrossbowItemMixin() {
		throw new IllegalAccessError("Attempted to instantiate a Mixin Class!");
	}
	
	@Inject(method = "Lnet/minecraft/item/CrossbowItem;getChargeDuration(Lnet/minecraft/item/ItemStack;)I", at = @At("TAIL"), cancellable = true)
	private static void chaosawakens$getChargeDuration(ItemStack pCrossbowStack, CallbackInfoReturnable<Integer> cIR) {
		if (pCrossbowStack.getItem() instanceof UltimateCrossbowItem) cIR.setReturnValue(50 + cIR.getReturnValueI());
	}
}