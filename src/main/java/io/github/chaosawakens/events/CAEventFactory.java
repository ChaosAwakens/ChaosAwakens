package io.github.chaosawakens.events;

import io.github.chaosawakens.events.common.ItemCooldownEvent.ItemCooldownEndEvent;
import io.github.chaosawakens.events.common.ItemCooldownEvent.ItemCooldownStartEvent;
import io.github.chaosawakens.events.common.ItemCooldownEvent.ItemCooldownTickEvent;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

public class CAEventFactory {
	
	public static ItemCooldownStartEvent onItemCooldownStart(Item targetCooldownItem, int cooldownTicks) {
		ItemCooldownStartEvent icdsEvent = new ItemCooldownStartEvent(targetCooldownItem, cooldownTicks);
		
		MinecraftForge.EVENT_BUS.post(icdsEvent);
		
		return icdsEvent;
	}
	
	public static ItemCooldownTickEvent onItemCooldownTick(Item targetCooldownItem, int cooldownTicks) {
		ItemCooldownTickEvent icdtEvent = new ItemCooldownTickEvent(targetCooldownItem, cooldownTicks);
		
		MinecraftForge.EVENT_BUS.post(icdtEvent);
		
		return icdtEvent;
	}

	public static ItemCooldownEndEvent onItemCooldownEnd(Item targetCooldownItem) {
		ItemCooldownEndEvent icdeEvent = new ItemCooldownEndEvent(targetCooldownItem);
		
		MinecraftForge.EVENT_BUS.post(icdeEvent);
		
		return icdeEvent;
	}
}