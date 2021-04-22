package com.example.examplemod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.text.ITextComponent;
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
    	RegistryHandler.otherRegistries();
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
//public static final Block ore_overworld_amethyst = new AmethystBlock("ore_overworld_amethyst", Material.IRON);

//Same Characteristics as Amethyst
public static final Item Ruby = new Amethyst("Ruby");
public static final Block RubyBlock = new AmethystBlock("RubyBlock", Material.IRON);
public static final Block ore_overworld = new RubyOre("ore_overworld", "overworld");
public static final Item TigerEye = new Amethyst("TigerEye");
//public static final Block ore_overworld_tigereye = new ore_tigereye_overworld("ore_overworld_tigereye", "overworld");
public static final Block TigerEyeBlock = new AmethystBlock("TigerEyeBlock", Material.IRON);
//public static final Item Oil = new Oil("Oil");
//public static final Block ore_unprocessedoil_overworld = new ore_unprocessedoil_overworld("ore_unprocessedoil_overworld", "overworld");
//public static final Item Salt = new Salt("Salt"); //Ant Functionality Delayed
//public static final Block ore_salt_overworld = new SaltOre("SaltOre", "overworld"); //Ant Functionality Delayed
public static final Item Titanium = new Amethyst("Titanium");
public static final Item TitaniumNugget = new Amethyst("TitaniumNugget");
//public static final Block ore_titanium_overworld = new ore_titanium_overworld("ore_titanium_overworld", "overworld");
public static final Block TitaniumBlock = new AmethystBlock("TitaniumBlock", Material.IRON);
public static final Item Uranium = new Amethyst("Uranium");
public static final Item UraniumNugget = new Amethyst("UraniumNugget");
//public static final Block ore_uranium_overworld = new ore_uranium_overworld("ore_uranium_overworld", "overworld");
public static final Block UraniumBlock = new AmethystBlock("UraniumBlock", Material.IRON);
public static final Item Aluminium = new Amethyst("Aluminium");
//public static final Block ore_aluminium_overworld = new ore_aluminium_overworld("ore_aluminium_overworld", "overworld");
public static final Block AluminiumBlock = new AmethystBlock("AluminiumBlock", Material.IRON);


//I will do troll blocks later but also with better Ores like Uranium or Titanium and worser Ores Like Coal/Oil or Salt






////Armour Materials
public static final ArmorMaterial ArmourSetOSEmerald = EnumHelper.addArmorMaterial("OSEmerald", Reference.MODID + ":emerald", 25, new int[] {5, 10, 10, 5}, 20, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 20.0F );
public static final ArmorMaterial ArmourSetAmethyst = EnumHelper.addArmorMaterial("ArmourSetAmethyst", Reference.MODID + ":amethyst", 25, new int[] {10, 20, 20, 10}, 50, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 30.0F );
public static final ArmorMaterial RubyArmour = EnumHelper.addArmorMaterial("RubyArmour", Reference.MODID + ":ruby", 50, new int[] {15, 25, 25, 15}, 60, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 40.0F );
public static final ArmorMaterial TigerEyeArmour = EnumHelper.addArmorMaterial("TigerEyeArmour", Reference.MODID + ":tigereye", 35, new int[] {10, 15, 15, 10}, 30, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 25.0F );


//ToolMaterials
public static final ToolMaterial OSTOOLEmerald = EnumHelper.addToolMaterial("OSEmerald", 2, 2000, 10.0F, 9.0F, 10);
public static final ToolMaterial AmethystTool = EnumHelper.addToolMaterial("AmethystTool", 8, 5000, 20.0F, 18.0F, 20);
public static final ToolMaterial RubyTool = EnumHelper.addToolMaterial("RubyTool", 12, 8000, 20.0F, 30.0F, 30);
public static final ToolMaterial TigerEyeTool = EnumHelper.addToolMaterial("TigerEyeTool", 5, 3500, 15.0F, 10.0F, 15);
public static final ToolMaterial OptimisedTool = EnumHelper.addToolMaterial("OptimisedTool", 15, 13000, 75.0F, 8.0F, 45);




//public static final Item EmeraldAxe = new EmeraldAxe("EmeraldAxe", OSTOOLEmerald);
public static final Item EmeraldPic = new EmeraldPic("EmeraldPic", OSTOOLEmerald);
public static final Item EmeraldHoe = new EmeraldHoe("EmeraldHoe", OSTOOLEmerald);
public static final Item EmeraldSword = new EmeraldSword("EmeraldSword", OSTOOLEmerald);
public static final Item EmeraldShovel = new EmeraldShovel("EmeraldShovel", OSTOOLEmerald);
public static final Item EmeraldHelmet = new EmeraldArmour("EmeraldHelmet", ArmourSetOSEmerald, 1, EntityEquipmentSlot.HEAD);
public static final Item EmeraldChest = new EmeraldArmour("EmeraldChest", ArmourSetOSEmerald, 1, EntityEquipmentSlot.CHEST);
public static final Item EmeraldLeggings = new EmeraldArmour("EmeraldLeggings", ArmourSetOSEmerald, 2, EntityEquipmentSlot.LEGS);
public static final Item EmeraldBoots = new EmeraldArmour("EmeraldBoots", ArmourSetOSEmerald, 1, EntityEquipmentSlot.FEET);

//same properties as Emeralds
public static final Item AmethystPic = new EmeraldPic("AmethystPic", AmethystTool);
public static final Item AmethystHoe = new EmeraldHoe("AmethystHoe", AmethystTool);
public static final Item AmethystSword = new EmeraldSword("AmethystSword", AmethystTool);
public static final Item AmethystShovel = new EmeraldShovel("AmethystShovel", AmethystTool);
public static final Item AmethystHelmet = new EmeraldArmour("AmethystHelmet", ArmourSetAmethyst, 1, EntityEquipmentSlot.HEAD);
public static final Item AmethystChest = new EmeraldArmour("AmethystChest", ArmourSetAmethyst, 1, EntityEquipmentSlot.CHEST);
public static final Item AmethystLeggings = new EmeraldArmour("AmethystLeggings", ArmourSetAmethyst, 2, EntityEquipmentSlot.LEGS);
public static final Item AmethystBoots = new EmeraldArmour("AmethystBoots", ArmourSetAmethyst, 1, EntityEquipmentSlot.FEET);


//Same Properties as Emeralds
public static final Item RubyPic = new EmeraldPic("RubyPic", RubyTool);
public static final Item RubyHoe = new EmeraldHoe("RubyHoe", RubyTool);
public static final Item RubySword = new EmeraldSword("RubySword", RubyTool);
public static final Item RubyShovel = new EmeraldShovel("RubyShovel", RubyTool);
public static final Item RubyHelmet = new EmeraldArmour("RubyHelmet", RubyArmour, 1, EntityEquipmentSlot.HEAD);
public static final Item RubyChest = new EmeraldArmour("RubyChest", RubyArmour, 1, EntityEquipmentSlot.CHEST);
public static final Item RubyLeggings = new EmeraldArmour("RubyLeggings", RubyArmour, 2, EntityEquipmentSlot.LEGS);
public static final Item RubyBoots = new EmeraldArmour("RubyBoots", RubyArmour, 1, EntityEquipmentSlot.FEET);


//Same Properties as Emeralds
public static final Item TigerEyePic = new EmeraldPic("TigerEyePic", TigerEyeTool);
public static final Item TigerEyeHoe = new EmeraldHoe("TigerEyeHoe", TigerEyeTool);
public static final Item TigerEyeSword = new EmeraldSword("TigerEyeSword", TigerEyeTool);
public static final Item TigerEyeShovel = new EmeraldShovel("TigerEyeShovel", TigerEyeTool);
public static final Item TigerEyeHelmet = new EmeraldArmour("TigerEyeHelmet", TigerEyeArmour, 1, EntityEquipmentSlot.HEAD);
public static final Item TigerEyeChest = new EmeraldArmour("TigerEyeChest", TigerEyeArmour, 1, EntityEquipmentSlot.CHEST);
public static final Item TigerEyeLeggings = new EmeraldArmour("TigerEyeLeggings", TigerEyeArmour, 2, EntityEquipmentSlot.LEGS);
public static final Item TigerEyeBoots = new EmeraldArmour("TigerEyeBoots", TigerEyeArmour, 1, EntityEquipmentSlot.FEET);

//Same Properties as Emeralds
public static final Item OptimisedMinersPic = new EmeraldPic("OptimisedMinersPic", OptimisedTool);
public static final Item OptimisedTrenchDiggersShovel = new EmeraldShovel("OptimisedTrenchDiggersShovel", OptimisedTool);









}
