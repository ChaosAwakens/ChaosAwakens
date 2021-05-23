/**
 * 
 */
package io.github.chaosawakens.api.dto;

import net.minecraft.enchantment.Enchantment;

/**
 * Enchantment and enchantment level DTO
 * @author invalid2
 */
public class EnchantmentAndLevel {
	private Enchantment enchantment;
	private int enchantLevel;
	
	public EnchantmentAndLevel(Enchantment enchantment, int enchantLevel) {
		super();
		this.enchantment = enchantment;
		this.enchantLevel = enchantLevel;
	}

	public Enchantment getEnchantment() { return enchantment; }
	public void setEnchantment(Enchantment enchantment) { this.enchantment = enchantment; }
	
	public int getEnchantLevel() { return enchantLevel; }
	public void setEnchantLevel(int enchantLevel) { this.enchantLevel = enchantLevel; }
}
