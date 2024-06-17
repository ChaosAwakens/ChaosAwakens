package io.github.chaosawakens.common.items.armor;

import io.github.chaosawakens.common.items.base.EnchantedArmorItem;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;

import java.util.function.Supplier;

//TODO Elytra set bonus W.I.P 
public class EnderScaleArmorItem extends EnchantedArmorItem { //TODO Getting redone in 1.20.1+/0.13.x.x+ (duh)

	public EnderScaleArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn, Supplier<EnchantmentData[]> enchantments) {
		super(materialIn, slot, builderIn, enchantments);
	}

}
