package com.example.examplemod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;



public class Amethyst extends Item implements IHadModels{

	
	public Amethyst(String name)
	{
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
