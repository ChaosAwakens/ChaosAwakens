package io.github.chaosawakens.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.items.extended.AttitudeAdjusterItem;
import io.github.chaosawakens.common.items.extended.BattleAxeItem;
import io.github.chaosawakens.common.items.extended.BigBerthaItem;
import io.github.chaosawakens.common.items.extended.QueenScaleBattleAxeItem;
import io.github.chaosawakens.common.items.extended.RoyalGuardianSwordItem;
import io.github.chaosawakens.common.items.extended.ScytheItem;
import io.github.chaosawakens.common.items.extended.SlayerChainsawItem;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Mixin(VillagerEntity.class)
public abstract class VillagerMixin extends AbstractVillagerEntity {

	public VillagerMixin(EntityType<? extends VillagerEntity> type, World world) {
		super(type, world);
	}
	
	@Inject(method = "Lnet/minecraft/entity/merchant/villager/VillagerEntity;updateSpecialPrices(Lnet/minecraft/entity/player/PlayerEntity;)V", at = @At("HEAD"), cancellable = true)
	public void chaosawakens$updateSpecialPrices(PlayerEntity player, CallbackInfo info) {
		VillagerEntity villager = (VillagerEntity) (Object) this;		
		//TODO make this armor also affect player rep once we get a better understanding of how the system works --Meme Man
	//	int rep = villager.getPlayerReputation(player);
		
		if (CACommonConfig.COMMON.enableEmeraldArmorSetBonus.get()) {					
			if (IUtilityHelper.isFullArmorSet(player, CAItems.EMERALD_HELMET.get(), CAItems.EMERALD_CHESTPLATE.get(), CAItems.EMERALD_LEGGINGS.get(), CAItems.EMERALD_BOOTS.get())) {			
				for (MerchantOffer offer : villager.getOffers()) {			
					//4 = ~50% Discount
					double amp = 0.3D + 0.0625D * CACommonConfig.COMMON.emeraldArmorDiscountMultiplier.get();
					if (CACommonConfig.COMMON.emeraldArmorDiscountMultiplier.get() > 8.0D || CACommonConfig.COMMON.emeraldArmorDiscountMultiplier.get() < 0.0D) {
						CACommonConfig.COMMON.emeraldArmorDiscountMultiplier.set(4.0D);
					}
					int cost = (int) Math.floor(amp * (double) offer.getBaseCostA().getCount());
					if (CACommonConfig.COMMON.emeraldArmorDiscountMultiplier.get() == 8.0D) offer.addToSpecialPriceDiff(-1);
					offer.addToSpecialPriceDiff(-Math.max(cost, 1));	
				}
			}
		}
	}
	
	@Inject(method = "Lnet/minecraft/entity/merchant/villager/VillagerEntity;mobInteract(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResultType;", at = @At("HEAD"), cancellable = true)
	public void chaosawakens$mobInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResultType> cir) {
		Item mainHandItem = player.getMainHandItem().getItem();
		
		//TODO Make a base class for all those items
		if (mainHandItem instanceof AttitudeAdjusterItem || mainHandItem instanceof BattleAxeItem || mainHandItem instanceof BigBerthaItem
				|| mainHandItem instanceof ScytheItem || mainHandItem instanceof QueenScaleBattleAxeItem || mainHandItem instanceof RoyalGuardianSwordItem
				|| mainHandItem instanceof SlayerChainsawItem) {
			cir.setReturnValue(ActionResultType.FAIL);
		}
	}
	
}
