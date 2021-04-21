package com.example.examplemod;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import java.util.*;
import com.example.examplemod.*;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class ExampleMod
{
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
    @Instance
public static ExampleMod instance;

@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
    public static CommonProxy proxy;

package com.example.examplemod;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import java.util.*;
import com.example.examplemod.*;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class ExampleMod
{
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
    @Instance
public static ExampleMod instance;

@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
    public static CommonProxy proxy;
    
//public static final List<Item> ITEMS = new ArrayList<Item>();
//
////Armour Materials
//public static final ArmorMaterial ArmourSetEmerald = EnumHelper.addArmorMaterial("Emerald", 40, new int [] {2, 6, 5, 2}, 10 );
//
////public static final Item Pickaxe_Emerald = new EmeraldPic("pickaxe_emerald");
//public static Item EmeraldHelmet = new ArmourSetEmerald(1513, 0, 0, "Emerald").setUnlocalizedName("Emerald Helmet");

}

}
