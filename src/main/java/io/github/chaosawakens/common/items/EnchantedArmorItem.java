package io.github.chaosawakens.common.items;

import io.github.chaosawakens.api.IPreEnchanted;
import io.github.chaosawakens.common.config.CAConfig;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class EnchantedArmorItem extends ArmorItem implements IPreEnchanted {
	
	private final EnchantmentData[] enchantments;
	
	public EnchantedArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Item.Properties builderIn, EnchantmentData[] enchantments) {
		super(materialIn, slot, builderIn);
		this.enchantments = enchantments;
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.isInGroup(group)) {
			ItemStack stack = new ItemStack(this);
			if (CAConfig.COMMON.enableAutoEnchanting.get())
				for(EnchantmentData enchant : enchantments) {
					stack.addEnchantment( enchant.enchantment, enchant.enchantmentLevel);
				}
			items.add(stack);
		}
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return CAConfig.COMMON.enableAutoEnchanting.get() || super.hasEffect(stack);
	}

	@Override
	public EnchantmentData[] enchant() {
		return this.enchantments;
	}
	
}