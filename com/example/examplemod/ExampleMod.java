package com.example.examplemod;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import java.util.*;

@Mod(modid = ExampleMod.MODID, name = ExampleMod.NAME, version = ExampleMod.VERSION)
public class ExampleMod
{
    public static final String MODID = "examplemod";
    public static final String NAME = "Example Mod";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }


public static final List<Item> ITEMS = new ArrayList<Item>();

//Armour Materials
public static final ArmorMaterial ArmourSetEmerald = EnumHelper.addArmorMaterial("Emerald", 40, new int [] {2, 6, 5, 2}, 10 );

//public static final Item Pickaxe_Emerald = new EmeraldPic("pickaxe_emerald");
public static Item EmeraldHelmet = new ArmourSetEmerald(1513, 0, 0, "Emerald").setUnlocalizedName("Emerald Helmet");

}
