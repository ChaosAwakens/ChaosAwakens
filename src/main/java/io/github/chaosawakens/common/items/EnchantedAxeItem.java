package io.github.chaosawakens.common.items;

import com.google.common.collect.ImmutableMap;
import io.github.chaosawakens.api.IAutoEnchantable;
import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Map;

public class EnchantedAxeItem extends AxeItem implements IAutoEnchantable {
	
	private final EnchantmentData[] enchantments;
	
	//TODO Improve this
	static {
		Map<Block, Block> temp;
		temp = (new ImmutableMap.Builder<Block, Block>()).putAll(AxeItem.STRIPABLES)
				.put(CABlocks.APPLE_LOG.get(), CABlocks.STRIPPED_APPLE_LOG.get())
				.put(CABlocks.CHERRY_LOG.get(), CABlocks.STRIPPED_CHERRY_LOG.get())
				.put(CABlocks.PEACH_LOG.get(), CABlocks.STRIPPED_PEACH_LOG.get())
				.put(CABlocks.DUPLICATION_LOG.get(), CABlocks.STRIPPED_DUPLICATION_LOG.get())
				.put(CABlocks.SKYWOOD_LOG.get(), CABlocks.STRIPPED_SKYWOOD_LOG.get()).build();

		AxeItem.STRIPABLES = temp;
	}
	
	public EnchantedAxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, EnchantmentData[] enchantments) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
		this.enchantments = enchantments;
	}
	
	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.allowdedIn(group)) {
			ItemStack stack = new ItemStack(this);
			if (CAConfig.COMMON.enableAutoEnchanting.get())
				for (EnchantmentData enchant : enchantments) {
					stack.enchant(enchant.enchantment, enchant.level);
				}
			items.add(stack);
		}
	}
	
	@Override
	public boolean isFoil(ItemStack stack) {
		return CAConfig.COMMON.enableAutoEnchanting.get() || super.isFoil(stack);
	}
	
	@Override
	public EnchantmentData[] enchantments() {
		return this.enchantments;
	}
	
}