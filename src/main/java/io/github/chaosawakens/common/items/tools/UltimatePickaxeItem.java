package io.github.chaosawakens.common.items.tools;

import java.util.function.Supplier;

import io.github.chaosawakens.api.item.IAutoEnchantable;
import io.github.chaosawakens.common.items.base.EnchantedPickaxeItem;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;

public class UltimatePickaxeItem extends EnchantedPickaxeItem implements IAutoEnchantable {
	
	public UltimatePickaxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, Supplier<EnchantmentData[]> enchantments) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn, enchantments);
	}
	
	@Override
	public boolean isFireResistant() {
		return true;
	}
	
	@Override
	public float getXpRepairRatio(ItemStack stack) {
		return 20.0F;
	}
}
