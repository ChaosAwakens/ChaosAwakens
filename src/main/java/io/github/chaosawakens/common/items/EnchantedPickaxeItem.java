package io.github.chaosawakens.common.items;

import io.github.chaosawakens.common.config.CAConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.world.World;

public class EnchantedPickaxeItem extends PickaxeItem {
	
	private final int[] enchantmentLevels;
	private final Enchantment[] enchantmentIds;
	
	public EnchantedPickaxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, Enchantment[] enchants, int[] lvls) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
		enchantmentIds = enchants;
		enchantmentLevels = lvls;
	}
	
	public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		if (!CAConfig.COMMON.enableAutoEnchanting.get())return;
		if (stack.isEnchanted())return;
		for (int i = 0; i < enchantmentIds.length; i++) {
			stack.addEnchantment(enchantmentIds[i], enchantmentLevels[i]);
		}
	}
	
	public void inventoryTick(ItemStack stack, World worldInD, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!CAConfig.COMMON.enableAutoEnchanting.get())return;
		if (stack.isEnchanted())return;
		if (EnchantmentHelper.getEnchantmentLevel(enchantmentIds[0], stack) <= 0) {
			for (int i = 0; i < enchantmentIds.length; i++) {
				stack.addEnchantment(enchantmentIds[i], enchantmentLevels[i]);
			}
		}
	}
	
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
	
}