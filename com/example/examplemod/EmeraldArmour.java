package com.example.examplemod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;

public class EmeraldArmour extends ItemArmor implements IHadModels {
	public EmeraldArmour(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		// TODO Auto-generated constructor stub
		
			setUnlocalizedName(name);
			setRegistryName(name);
			setCreativeTab(CreativeTabs.MATERIALS);
			ExampleMod.ITEMS.add(this);
	}
		
		
		@Override
		public void registerModels() {
			// TODO Auto-generated method stub
		 ExampleMod.proxy.registerItemRenderer(this, 0, "inventory");
		}
}
