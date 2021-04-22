package com.example.examplemod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
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

//@EventHandler
//public static void preInit(FMLPreInitializationEvent event) {}
//@EventHandler
//public static void init(FMLInitializationEvent event) {}
//@EventHandler
//public static void postInit(FMLPostInitializationEvent event) {}

    
//    
public static final List<Item> ITEMS = new ArrayList<Item>();
public static final List<Block> BLOCKS = new ArrayList<Block>();

public static final Item amethyst = new Amethyst("amethyst");
public static final Block amethystblock = new AmethystBlock("amethystblock", Material.IRON);




////Armour Materials
public static final ArmorMaterial ArmourSetOSEmerald = EnumHelper.addArmorMaterial("OSEmerald", Reference.MODID + ":emerald", 25, new int[] {5, 10, 10, 5}, 20, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 20.0F );
//ToolMaterials
public static final ToolMaterial OSTOOLEmerald = EnumHelper.addToolMaterial("OSEmerald", 2, 2000, 10.0F, 9.0F, 10);



//public static final Item EmeraldAxe = new EmeraldAxe("EmeraldAxe", OSTOOLEmerald);
public static final Item EmeraldPic = new EmeraldPic("EmeraldPic", OSTOOLEmerald);
public static final Item EmeraldHoe = new EmeraldHoe("EmeraldHoe", OSTOOLEmerald);
public static final Item EmeraldSword = new EmeraldSword("EmeraldSword", OSTOOLEmerald);
public static final Item EmeraldShovel = new EmeraldShovel("EmeraldShovel", OSTOOLEmerald);
public static final Item EmeraldHelmet = new EmeraldArmour("EmeraldHelmet", ArmourSetOSEmerald, 1, EntityEquipmentSlot.HEAD);
public static final Item EmeraldChest = new EmeraldArmour("EmeraldChest", ArmourSetOSEmerald, 1, EntityEquipmentSlot.CHEST);
public static final Item EmeraldLeggings = new EmeraldArmour("EmeraldLeggings", ArmourSetOSEmerald, 2, EntityEquipmentSlot.LEGS);
public static final Item EmeraldBoots = new EmeraldArmour("EmeraldBoots", ArmourSetOSEmerald, 1, EntityEquipmentSlot.FEET);


}
