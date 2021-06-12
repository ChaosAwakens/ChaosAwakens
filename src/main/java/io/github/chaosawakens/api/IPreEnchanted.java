package io.github.chaosawakens.api;

import net.minecraft.enchantment.EnchantmentData;

/**
 * @author invalid2
 */
@FunctionalInterface
public interface IPreEnchanted {
	
	EnchantmentData[] enchant();
}
