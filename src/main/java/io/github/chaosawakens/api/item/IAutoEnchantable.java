package io.github.chaosawakens.api.item;

import net.minecraft.enchantment.EnchantmentData;

/**
 * Functional interface for items that will come with enchantments when crafted
 * or acquired
 *
 * @author invalid2
 */
@FunctionalInterface
public interface IAutoEnchantable {
	EnchantmentData[] getEnchantments();
}
