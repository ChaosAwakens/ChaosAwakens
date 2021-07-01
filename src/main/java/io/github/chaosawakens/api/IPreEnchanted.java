package io.github.chaosawakens.api;

import net.minecraft.enchantment.EnchantmentLevelEntry;

@FunctionalInterface
public interface IPreEnchanted {
    EnchantmentLevelEntry[] enchant();
}
