package io.github.chaosawakens.mixins.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.content.item.equipment.armor.ExperienceArmorItem;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ExperienceOrb.class)
public abstract class ExperienceOrbMixin {

    @WrapOperation(method = "playerTouch", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;repairPlayerItems(Lnet/minecraft/world/entity/player/Player;I)I"))
    private int chaosawakens$playerTouch(ExperienceOrb instance, Player player, int repairAmount, Operation<Integer> original) {
        boolean isUsingExperienceSet = true;
        for (ItemStack stack : player.getArmorSlots()) {
            isUsingExperienceSet = isUsingExperienceSet && stack.getItem() instanceof ExperienceArmorItem;
        }
        return original.call(instance, player, isUsingExperienceSet ? (int) Math.round(instance.getValue() * 1.5) : instance.getValue());
    }
}
