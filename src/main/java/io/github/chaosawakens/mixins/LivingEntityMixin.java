package io.github.chaosawakens.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.items.armor.EnderScaleArmorItem;
import io.github.chaosawakens.common.registry.CAEffects;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	
	private LivingEntityMixin() {
		throw new IllegalAccessError("Attempted to instantiate a Mixin Class!");
	}
	
	@ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setSharedFlag(IZ)V"), method = "updateFallFlying")
	public boolean chaosawakens$setFlag(boolean value) {
		LivingEntity entity = (LivingEntity) (Object) this;
		if (EnderScaleArmorItem.isElytraToggled((entity.getItemBySlot(EquipmentSlotType.CHEST)))) {
			ItemStack chestplate = entity.getItemBySlot(EquipmentSlotType.CHEST);
			value = IUtilityHelper.isFullArmorSet((PlayerEntity) entity, CAItems.ENDER_DRAGON_SCALE_HELMET.get(), CAItems.ENDER_DRAGON_SCALE_CHESTPLATE.get(), CAItems.ENDER_DRAGON_SCALE_LEGGINGS.get(), CAItems.ENDER_DRAGON_SCALE_BOOTS.get()) && EnderScaleArmorItem.isElytraToggled(chestplate);
			entity.setSharedFlag(7, entity.getSharedFlag(7) && value);
			return entity.getSharedFlag(7);
		}
		return entity.getSharedFlag(7);
	}

	@Inject(method = "isImmobile", at = @At("HEAD"), cancellable = true)
	protected void chaosawakens$isImmobile(CallbackInfoReturnable<Boolean> cir) {
		LivingEntity entity = (LivingEntity) (Object) this;
		if (entity.hasEffect(CAEffects.PARALYSIS_EFFECT.get())) cir.setReturnValue(true);
	}
	
//	@ModifyConstant(method = "Lnet/minecraft/world/entity/LivingEntity;travel(Lnet/minecraft/world/phys/Vec3;)V")
	
/*	@Inject(method = "Lnet/minecraft/world/entity/LivingEntity;travel(Lnet/minecraft/world/phys/Vec3;)V", at = @At("INVOKE"), cancellable = true)
	public void chaosawakens$travel(Vector3d movementVec) {
		
	}*/
}