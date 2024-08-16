package io.github.chaosawakens.mixins;

import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	
	private PlayerEntityMixin() {
		throw new IllegalAccessError("Attempted to instantiate a Mixin Class!");
	}

	@Inject(method = "Lnet/minecraft/entity/player/PlayerEntity;wantsToStopRiding()Z", at = @At("HEAD"), cancellable = true)
	private void chaosawakens$wantsToStopRiding(CallbackInfoReturnable<Boolean> cir) {
		PlayerEntity targetPlayer = (PlayerEntity) (Object) this;
		
		if (targetPlayer.getVehicle() != null) {
			if (targetPlayer.getVehicle() instanceof AnimatableMonsterEntity) {
				if (!((AnimatableMonsterEntity) targetPlayer.getVehicle()).shouldAllowDismount()) cir.setReturnValue(false);
			}
		}
	}
}