package io.github.chaosawakens.common.items;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.EnchantmentAndLevel;
import io.github.chaosawakens.common.config.CAConfig;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class EnchantedArmorItem extends ArmorItem {
	
	private final EnchantmentAndLevel[] enchantments;
	
	public EnchantedArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Item.Properties builderIn, EnchantmentAndLevel[] enchantments) {
		super(materialIn, slot, builderIn);
		this.enchantments = enchantments;
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.isInGroup(group)) {
			ItemStack stack = new ItemStack(this);
			if (CAConfig.COMMON.enableAutoEnchanting.get())
				for(EnchantmentAndLevel enchant : enchantments) {
					stack.addEnchantment( enchant.getEnchantment(), enchant.getEnchantLevel());
				}
			items.add(stack);
		}
		ChaosAwakens.enchantedItems.put(this.getRegistryName(), enchantments);
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return CAConfig.COMMON.enableAutoEnchanting.get();
	}
	
}