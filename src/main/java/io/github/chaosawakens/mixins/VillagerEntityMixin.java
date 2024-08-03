package io.github.chaosawakens.mixins;

import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.MerchantOffer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends AbstractVillagerEntity {

	public VillagerEntityMixin(EntityType<? extends AbstractVillagerEntity> type, World world) {
		super(type, world);
	}

	@Inject(method = "updateSpecialPrices(Lnet/minecraft/entity/player/PlayerEntity;)V", at = @At("HEAD"), cancellable = true)
	private void chaosawakens$updateSpecialPrices(PlayerEntity player, CallbackInfo info) {
		VillagerEntity villager = (VillagerEntity) (Object) this;
		
		if (CAConfigManager.MAIN_COMMON.enableEmeraldArmorSetBonus.get()) {					
			if (EntityUtil.isFullArmorSet(player, CAItems.EMERALD_HELMET.get(), CAItems.EMERALD_CHESTPLATE.get(), CAItems.EMERALD_LEGGINGS.get(), CAItems.EMERALD_BOOTS.get()) && villager.getTradingPlayer() != null && villager.getTradingPlayer().getUUID().equals(player.getUUID())) {			
				for (MerchantOffer offer : villager.getOffers()) {			
					//4 = ~50% Discount
					double amp = 0.3D + 0.0625D * CAConfigManager.MAIN_COMMON.emeraldArmorDiscountMultiplier.get();
					
					if (CAConfigManager.MAIN_COMMON.emeraldArmorDiscountMultiplier.get() > 8.0D || CAConfigManager.MAIN_COMMON.emeraldArmorDiscountMultiplier.get() < 0.0D) CAConfigManager.MAIN_COMMON.emeraldArmorDiscountMultiplier.set(4.0D);
					
					int cost = (int) Math.floor(amp * (double) offer.getBaseCostA().getCount());
					if (CAConfigManager.MAIN_COMMON.emeraldArmorDiscountMultiplier.get() == 8.0D) offer.addToSpecialPriceDiff(-1);
					offer.addToSpecialPriceDiff(-Math.max(cost, 1));
				}
			}
		}
	}
}