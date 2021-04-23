package io.github.chaosawakens;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class ChaosAwakens
{
	
	@Instance
	public static ChaosAwakens instance;
	
	@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
	public static CommonProxy proxy;
	
	//Items class holding ALL the items
	Items items = new Items();
	
	//Blocks class holding ALL the blocks
	Blocks blocks = new Blocks();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		//GameRegistry
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
