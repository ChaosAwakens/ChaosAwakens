package com.example.examplemod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class AmathestBlock extends Block implements IHadModels {

	public AmathestBlock(String name, Material material) {
		super(material);
		// TODO Auto-generated constructor stub
	setUnlocalizedName(name);
	setRegistryName(name);
	setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	
	ExampleMod.BLOCKS.add(this);
	ExampleMod.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerModels() {
		// TODO Auto-generated method stub
	 ExampleMod.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
	
	
	
}
