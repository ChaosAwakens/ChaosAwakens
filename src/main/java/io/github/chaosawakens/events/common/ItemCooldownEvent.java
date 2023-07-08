package io.github.chaosawakens.events.common;

import net.minecraft.item.Item;
import net.minecraft.util.CooldownTracker.Cooldown;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * This event is fired whenever any of its child events are fired. You can use each of these events to manipulate items at any point 
 * during cooldown, and can even use both {@link ItemCooldownStartEvent} and {@link ItemCooldownTickEvent} to change the cooldown timer. 
 * You can also cancel each of these events (with the exception of {@link ItemCooldownEndEvent}) to cancel the cooldown of a specific item. 
 * <br> </br>
 * This event and its subclasses are all fired on the {@link MinecraftForge#EVENT_BUS}.
 */
public class ItemCooldownEvent extends Event {
	private final Item targetCooldownItem;
	
	public ItemCooldownEvent(Item targetCooldownItem) {
		this.targetCooldownItem = targetCooldownItem;
	}
	
	public Item getCooldownItem() {
		return targetCooldownItem;
	}
	
	/**
	 * This event is fired when a {@link Cooldown} starts on an {@link Item} (primarily on the first tick). It can be used to manipulate the target {@link Item}/{@link Cooldown}, 
	 * or outright remove the {@link Cooldown} from the {@link Item}.
	 * <br> </br>
	 * This event is {@link Cancelable}.
	 */
	@Cancelable
	public static class ItemCooldownStartEvent extends ItemCooldownEvent {
		private int cooldownTicks;

		public ItemCooldownStartEvent(Item targetCooldownItem, int cooldownTicks) {
			super(targetCooldownItem);
			this.cooldownTicks = cooldownTicks;
		}
		
		public int getCooldownTicks() {
			return cooldownTicks;
		}
		
		public void setCooldownTicks(int cooldownTicks) {
			this.cooldownTicks = cooldownTicks;
		}
	}
	
	/**
	 * This event is fired when a {@link Cooldown} is ticking on an {@link Item}. It can be used to manipulate the target {@link Item}/{@link Cooldown}, 
	 * or outright remove the {@link Cooldown} from the {@link Item}.
	 * <br> </br>
	 * This event is {@link Cancelable}.
	 */
	@Cancelable
	public static class ItemCooldownTickEvent extends ItemCooldownEvent {
		private int cooldownTicks;

		public ItemCooldownTickEvent(Item targetCooldownItem, int cooldownTicks) {
			super(targetCooldownItem);
			this.cooldownTicks = cooldownTicks;
		}
		
		public int getCooldownTicks() {
			return cooldownTicks;
		}
		
		public void setCooldownTicks(int cooldownTicks) {
			this.cooldownTicks = cooldownTicks;
		}
	}
	
	/**
	 * This event is fired when a {@link Cooldown} ends on an {@link Item} (primarily on the last tick). It can be used to manipulate the target {@link Item}.
	 * <br> </br>
	 * This event is not {@link Cancelable}.
	 */
	public static class ItemCooldownEndEvent extends ItemCooldownEvent {

		public ItemCooldownEndEvent(Item targetCooldownItem) {
			super(targetCooldownItem);
		}
	}
}
