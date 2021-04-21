package com.example.examplemod;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {
@Override
public void registerItemRenderer(Item item, int meta, String id) {
	
	//super.registerItemRenderer(item, meta, id);
	ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
}



}
