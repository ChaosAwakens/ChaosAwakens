package io.github.chaosawakens.common.items;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.EnchantmentAndLevel;
import io.github.chaosawakens.common.config.CAConfig;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.NonNullList;

public class EnchantedShovelItem extends ShovelItem {
	
	private final EnchantmentAndLevel[] enchantments;
	
	public EnchantedShovelItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, EnchantmentAndLevel[] enchantments) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
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