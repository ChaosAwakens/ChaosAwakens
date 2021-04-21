package com.example.examplemod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSword;

public class EmeraldSword extends ItemSword implements IHadModels {

	
	public EmeraldSword(String name, ToolMaterial material) {
		super(material);
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
