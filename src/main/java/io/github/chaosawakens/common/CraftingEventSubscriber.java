package io.github.chaosawakens.common;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.dto.EnchantmentAndLevel;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;

/**
 * Class with method(s) that subscribe to the ItemCraftedEvent
 * @author invalid2
 */
public class CraftingEventSubscriber {
	
	public static void onItemCraftedEvent(final ItemCraftedEvent event) {
		
		EnchantmentAndLevel[] craftedEnchants = ChaosAwakens.enchantedItems.get(event.getCrafting().getItem().getRegistryName());
		if(craftedEnchants != null) {
			for(EnchantmentAndLevel enchant : craftedEnchants) {
				event.getCrafting().addEnchantment( enchant.getEnchantment(), enchant.getEnchantLevel());
			}
		}
	}
}
