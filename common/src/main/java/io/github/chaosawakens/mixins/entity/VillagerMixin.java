package io.github.chaosawakens.mixins.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.content.item.equipment.armor.EmeraldArmorItem;
import io.github.chaosawakens.content.item.equipment.armor.ExperienceArmorItem;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public abstract class VillagerMixin {

    @Inject(method = "updateSpecialPrices", at = @At(value = "HEAD"))
    public void chaosawakens$updateSpecialPrices(Player player, CallbackInfo ci) {
        Villager villager = (Villager) (Object) this;
        boolean isUsingEmeraldSet = true;
        for (ItemStack stack : player.getArmorSlots()) {
            isUsingEmeraldSet = isUsingEmeraldSet && stack.getItem() instanceof EmeraldArmorItem;
        }
        if (isUsingEmeraldSet) {
            for (MerchantOffer offer : villager.getOffers()) {
                int j = (int) Math.floor(0.2 * offer.getBaseCostA().getCount());
                offer.addToSpecialPriceDiff(-Math.max(j, 1));
            }
        }
    }
}
