package io.github.chaosawakens.mixins.inventory;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.chaosawakens.content.item.equipment.armor.LapisArmorItem;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {

    public AnvilMenuMixin(@Nullable MenuType<?> type, int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(type, containerId, playerInventory, access);
    }

    @Definition(id = "cost", field = "Lnet/minecraft/world/inventory/AnvilMenu;cost:Lnet/minecraft/world/inventory/DataSlot;")
    @Definition(id = "set", method = "Lnet/minecraft/world/inventory/DataSlot;set(I)V")
    @Expression("this.cost.set(? + ?)")
    @WrapOperation(method = "createResult", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    public void chaosawakens$createResult(DataSlot instance, int i, Operation<Void> original) {
        boolean isFullLapisSet = true;
        for (ItemStack stack : player.getArmorSlots()) {
            isFullLapisSet = isFullLapisSet && stack.getItem() instanceof LapisArmorItem;
        }
        if (isFullLapisSet) {
            instance.set(Mth.floor(i * 0.9));
        } else {
            instance.set(i);
        }
    }
}
