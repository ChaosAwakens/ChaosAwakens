package io.github.chaosawakens.common.items.base;

import io.github.chaosawakens.common.util.EnumUtil.CAItemTier;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

import java.util.function.Supplier;

public class EnchantedScytheItem extends EnchantedSwordItem {

	public EnchantedScytheItem(CAItemTier pTier, Supplier<IntValue> configDmg, Properties pProperties, Supplier<EnchantmentData[]> enchantments) {
		super(pTier, configDmg, -1.9F, pProperties, enchantments);
	}
	
	public EnchantedScytheItem(CAItemTier pTier, Supplier<IntValue> configDmg, double reach, Properties pProperties, Supplier<EnchantmentData[]> enchantments) {
		super(pTier, configDmg, -1.9F, reach, pProperties, enchantments);
	}
	
	@Override
	public boolean isCorrectToolForDrops(BlockState targetState) {
		return targetState.is(BlockTags.CROPS);
	}
}
