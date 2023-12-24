package io.github.chaosawakens.mixins;

import io.github.chaosawakens.common.items.CritterCageItem;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {
	
	private MobEntityMixin() {
		throw new IllegalAccessError("Attempted to instantiate a Mixin Class!");
	}
	
	@Inject(method = "Lnet/minecraft/entity/MobEntity;mobInteract(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResultType;", at = @At("HEAD"), cancellable = true)
	private void chaosawakens$mobInteract(PlayerEntity interactingPlayer, Hand curHand, CallbackInfoReturnable<ActionResultType> cir) {
		MobEntity targetMob = (MobEntity) (Object) this;
		
		// Account for other entities (Tameable entities, etc.) that don't get caged due to limitations set in their mobInteract methods
		if (interactingPlayer.getMainHandItem().getItem().equals(CAItems.CRITTER_CAGE.get())) {
			CritterCageItem curCage = (CritterCageItem) interactingPlayer.getMainHandItem().getItem();
			
			if (!curCage.containsEntity(interactingPlayer.getMainHandItem())) {
				curCage.interactLivingEntity(interactingPlayer.getMainHandItem(), interactingPlayer, targetMob, curHand);
				cir.setReturnValue(ActionResultType.SUCCESS);
			}
		}
	}
}