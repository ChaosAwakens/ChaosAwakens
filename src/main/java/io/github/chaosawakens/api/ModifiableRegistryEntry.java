package io.github.chaosawakens.api;

import java.lang.reflect.Field;

import net.minecraft.item.Item;

public class ModifiableRegistryEntry {
	public final Item item;
	public final int damageValue;
	
	public ModifiableRegistryEntry(Item item, int damageValue) {
		this.item = item;
		this.damageValue = damageValue;
	}
	
	@SuppressWarnings("unchecked")             //Look, I'm cool now :) -Meme Man
	public <M> M getItemOfClass(Class<?> classFrom, Class<M> clazz) {
		if (!classFrom.isInstance(item)) return null;
		
		try {
			for (Field field : classFrom.getDeclaredFields()) {
				field.setAccessible(true);
				Object o = field.get(item);
				if (clazz.isInstance(o)) {
					return (M) o;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Item getItem() {
		return this.item;
	}
	
	public int getDamage() {
		return this.damageValue;
	}
	
}
