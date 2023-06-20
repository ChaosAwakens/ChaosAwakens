package io.github.chaosawakens.common.items.base;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnchantedItem extends Item {
	
	public EnchantedItem(Properties builderIn) {
		super(builderIn);
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}
}
