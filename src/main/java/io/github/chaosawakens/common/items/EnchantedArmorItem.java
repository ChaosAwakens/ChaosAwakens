package io.github.chaosawakens.common.items;

import io.github.chaosawakens.common.config.CAConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class EnchantedArmorItem extends ArmorItem {
	
	private final int[] enchantmentLevels;
	private final Enchantment[] enchantmentIds;
	
	public EnchantedArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Item.Properties builderIn, Enchantment[] enchants, int[] lvls) {
		super(materialIn, slot, builderIn);
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
		return CAConfig.COMMON.enableAutoEnchanting.get();
	}
	
}