package io.github.chaosawakens;

import io.github.chaosawakens.worldgenerators.OreWorldGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class ChaosAwakens
{
	
	@Instance
	public static ChaosAwakens instance;
	
	@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
	public static CommonProxy proxy;
	
	//Items class holding ALL the items
	ModItems items = new ModItems();
	
	//Blocks class holding ALL the blocks
	ModBlocks blocks = new ModBlocks();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		GameRegistry.registerWorldGenerator( new OreWorldGenerator(), 0);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		
    }
	
	@EventHandler
	public void postinit(FMLPostInitializationEvent event)
	{

	}
	
}
