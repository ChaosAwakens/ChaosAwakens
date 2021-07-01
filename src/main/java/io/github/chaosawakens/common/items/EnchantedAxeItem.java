package io.github.chaosawakens.common.items;

import io.github.chaosawakens.api.IPreEnchanted;
import io.github.chaosawakens.common.config.CAConfig;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class EnchantedAxeItem extends AxeItem implements IPreEnchanted {
	
	private final EnchantmentData[] enchantments;
	
	public EnchantedAxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, EnchantmentData[] enchantments) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
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
		return CAConfig.COMMON.enableAutoEnchanting.get() || stack.isEnchanted();
	}

	@Override
	public EnchantmentData[] enchant() {
		return this.enchantments;
	}
	
}