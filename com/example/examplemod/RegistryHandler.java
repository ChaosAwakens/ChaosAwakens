package com.example.examplemod;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber
public class RegistryHandler {

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(ExampleMod.ITEMS.toArray(new Item[0]));
	}
	
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(ExampleMod.BLOCKS.toArray(new Block[0]));
	}
	
	
	
	
	public static void onModelRegister(ModelRegistryEvent event)
	{
for (Item item : ExampleMod.ITEMS)
{
	if(item instanceof IHadModels)
	{
	((IHadModels)item).registerModels();	
	}
	
}
for (Block block : ExampleMod.BLOCKS)
{
	if(block instanceof IHadModels)
	{
	((IHadModels)block).registerModels();	
	}
	
}
	
	}

	
	public static void otherRegistries()
	{
		GameRegistry.registerWorldGenerator(new WorldGenCustomOres(), 0);
		
	}
	
	
	
	
	
	
}
