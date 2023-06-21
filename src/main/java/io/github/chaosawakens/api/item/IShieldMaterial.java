package io.github.chaosawakens.api.item;

import java.util.function.Supplier;

import net.minecraft.item.crafting.Ingredient;

public interface IShieldMaterial {
	
	/**
	 * Gets the name of this shield material (modid included).
	 * @return The name of this shield material.
	 */
	String getMaterialName();
	
	/**
	 * Gets the max damage/durability shields of this material have.
	 * @return The durability shields of this material have.
	 */
	int getDurability();
	
	/**
	 * Gets the toughness (I.E. durability damage reduction) (%) shields of this material have when taking damage. Values over 100 default to 100, same goes for 0.
	 * @return The toughness (in percentage) shields of this material have.
	 */
	float getToughness();
	
	/**
	 * Gets the enchantability of this shield material.
	 * @return The enchantment value/enchantability of this shield mterial.
	 */
	int getEnchantability();
	
	/**
	 * Gets the knockback resistance (%) shields of this material provide if they're bypassed. Values over 100 default to 100, same goes for 0.
	 * @return The knockback resistance (in percentage) shields of this material provide.
	 */
	float getKnockbackResistance();
	
	/**
	 * Gets the damage resistance (%) shields of this material provide if they're bypassed. Values over 100 default to 100, same goes for 0.
	 * @return The damage resistance (in percentage) shields of this material provide.
	 */
	double getDamageResistance();
	
	/**
	 * Gets the repair ingredient shields of this material accept. Values returned using this getter method should be returned via {@link Supplier}s.
	 * @return The repair ingredient/item(s) shields of this material accept in order to be repaired.
	 */
	Ingredient getRepairIngredient();
}
