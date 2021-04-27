package io.github.chaosawakens.items;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EnchantedShovel extends ShovelItem {

    private int[] enchantmentLevels;
    private Enchantment[] enchantmentIds;

    public EnchantedShovel(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn, Enchantment[] enchants, int[] lvls) {
        super(tier,attackDamageIn,attackSpeedIn,builderIn);
        enchantmentIds = enchants;
        enchantmentLevels = lvls;
    }

    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
            for (int i = 0; i < enchantmentIds.length; i++) {
                stack.addEnchantment(enchantmentIds[i], enchantmentLevels[i]);
            }
        }

    public void inventoryTick(ItemStack stack, World worldInD, Entity entityIn, int itemSlot, boolean isSelected) {
        if (EnchantmentHelper.getEnchantmentLevel(enchantmentIds[0],stack) <= 0) {
            for (int i = 0; i < enchantmentIds.length; i++) {
                stack.addEnchantment(enchantmentIds[i], enchantmentLevels[i]);
            }
        }
    }

}