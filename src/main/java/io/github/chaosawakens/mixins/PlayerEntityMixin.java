package io.github.chaosawakens.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.chaosawakens.common.entity.boss.miniboss.HerculesBeetleEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	
	public PlayerEntityMixin(EntityType<? extends LivingEntity> type, World world) {
		super(type, world);
	}

	@Inject(method = "Lnet/minecraft/entity/player/PlayerEntity;wantsToStopRiding()Z", at = @At("INVOKE"), cancellable = true)
	private void chaosawakens$wantsToStopRiding(CallbackInfoReturnable<Boolean> cir) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		
		if (player.getVehicle() != null) {
			if (player.getVehicle() instanceof HerculesBeetleEntity) {
				cir.setReturnValue(false);
			}
		}
	}

}
