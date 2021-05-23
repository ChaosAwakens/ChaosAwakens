/**
 * 
 */
package io.github.chaosawakens.common;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.dto.EnchantmentAndLevel;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;

/**
 * @author invalid2
 *
 */
public class CraftingEventHandler {
	
	public static void onItemCraftedEvent(ItemCraftedEvent event) {
		
		EnchantmentAndLevel[] craftedEnchants = ChaosAwakens.enchantedItems.get(event.getCrafting().getItem().getRegistryName());
		if(craftedEnchants != null) {
			for(EnchantmentAndLevel enchant : craftedEnchants) {
				event.getCrafting().addEnchantment( enchant.getEnchantment(), enchant.getEnchantLevel());
			}
		}
	}
}
