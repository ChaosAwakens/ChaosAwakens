package com.example.examplemod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;

public class EmeraldPic extends ItemPickaxe implements IHadModels {
	public EmeraldPic(String name, ToolMaterial material) {
		super(material);
		// TODO Auto-generated constructor stub
		
			setUnlocalizedName(name);
			setRegistryName(name);
			setCreativeTab(CreativeTabs.TOOLS);
			ExampleMod.ITEMS.add(this);

	}
				
		@Override
		public void registerModels() {
			// TODO Auto-generated method stub
		 ExampleMod.proxy.registerItemRenderer(this, 0, "inventory");
		}
}
