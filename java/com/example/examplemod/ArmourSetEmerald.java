package com.example.examplemod;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class ArmourSetEmerald extends ItemArmor {

	public ArmourSetEmerald(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		// TODO Auto-generated constructor stub
	
	this.setCreativeTab(getCreativeTab().COMBAT);
	
	
	
	}

}
