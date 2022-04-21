package io.github.chaosawakens.common.events;

import io.github.chaosawakens.common.items.ExtendedHitAxeItem;
import io.github.chaosawakens.common.items.ExtendedHitWeaponItem;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

public class EntityAttributeModificationEventSubscriber {
	
	public static void onEntityAttributeModificationEvent(EntityAttributeModificationEvent event) {
//		event.add(EntityType.PLAYER, ForgeMod.REACH_DISTANCE.get(), ExtendedHitWeaponItem.reach);
	}
	
	@EventBusSubscriber(bus = Bus.FORGE)
	public static class ItemAttributeModificationEventSubscriber {
		//Setting the reach attribute in the item's registry or class is useless, for now
		@SubscribeEvent
		public static void onEntityAttributeModificationEvent(ItemAttributeModifierEvent event) {
			ItemStack stack = event.getItemStack();
			EquipmentSlotType slotType = event.getSlotType();
			
			if (stack.getItem() instanceof ExtendedHitWeaponItem && slotType == EquipmentSlotType.MAINHAND && event.getOriginalModifiers() != null) {	
				if (stack.getItem() == CAItems.BIG_BERTHA.get()) {
					event.addModifier(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ExtendedHitWeaponItem.ATTACK_REACH_MODIFIER, "Weapon modifier", 4.5D, AttributeModifier.Operation.ADDITION));
				}
				
				if (stack.getItem() == CAItems.ROYAL_GUARDIAN_SWORD.get()) {
					event.addModifier(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ExtendedHitWeaponItem.ATTACK_REACH_MODIFIER, "Weapon modifier", 5.0D, AttributeModifier.Operation.ADDITION));
				}
				
				if (stack.getItem() == CAItems.ATTITUDE_ADJUSTER.get()) {
					event.addModifier(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ExtendedHitWeaponItem.ATTACK_REACH_MODIFIER, "Weapon modifier", 3.0D, AttributeModifier.Operation.ADDITION));
				}
			}
		
			if (stack.getItem() instanceof ExtendedHitAxeItem && slotType == EquipmentSlotType.MAINHAND && event.getOriginalModifiers() != null) {	
				if (stack.getItem() == CAItems.SLAYER_CHAINSAW.get()) {
					event.addModifier(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ExtendedHitAxeItem.ATTACK_REACH_MODIFIER, "Weapon modifier", 2.5D, AttributeModifier.Operation.ADDITION));
				}
				
				if (stack.getItem() == CAItems.BATTLE_AXE.get()) {
					event.addModifier(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ExtendedHitAxeItem.ATTACK_REACH_MODIFIER, "Weapon modifier", 3.5D, AttributeModifier.Operation.ADDITION));
				}
				
				if (stack.getItem() == CAItems.QUEEN_SCALE_BATTLE_AXE.get()) {
					event.addModifier(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ExtendedHitAxeItem.ATTACK_REACH_MODIFIER, "Weapon modifier", 4.5D, AttributeModifier.Operation.ADDITION));
				}
			}
		}
	}

}
