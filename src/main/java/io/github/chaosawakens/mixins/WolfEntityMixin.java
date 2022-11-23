package io.github.chaosawakens.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.chaosawakens.common.items.CritterCageItem;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Mixin(WolfEntity.class)
public abstract class WolfEntityMixin extends TameableEntity {
	
	protected WolfEntityMixin(EntityType<? extends TameableEntity> type, World world) {
		super(type, world);
	}

	@Inject(method = "Lnet/minecraft/entity/passive/WolfEntity;mobInteract(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResultType;", at = @At("HEAD"), cancellable = true)
	public void chaosawakens$mobInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResultType> cir) {
		if (this.isTame()) {
			if (player.getMainHandItem().getItem() == CAItems.CRITTER_CAGE.get()) {
				CritterCageItem cage = (CritterCageItem) player.getMainHandItem().getItem();
				
				if (!cage.containsEntity(player.getMainHandItem())) {
					cage.interactLivingEntity(player.getMainHandItem(), player, this, hand);
					cir.setReturnValue(ActionResultType.SUCCESS);
				}
			}
		}
	}

}
