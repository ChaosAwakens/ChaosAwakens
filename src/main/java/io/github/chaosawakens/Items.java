package io.github.chaosawakens;

import java.util.ArrayList;
import java.util.List;

import io.github.chaosawakens.equipments.GenericArmor;
import io.github.chaosawakens.equipments.GenericAxe;
import io.github.chaosawakens.equipments.GenericHoe;
import io.github.chaosawakens.equipments.GenericPickaxe;
import io.github.chaosawakens.equipments.GenericShovel;
import io.github.chaosawakens.equipments.GenericSword;
import io.github.chaosawakens.items.GenericItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

/**
 * Class for item creation, such that the main class is not unreadable
 * @author invalid2
 */
public class Items {
	
	/**
	 * List holding ALL mod items
	 */
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	//Data enums
	
	//Armor materials
	public static final ArmorMaterial EMERALD_ARMOR_MATERIAL = EnumHelper.addArmorMaterial(ArmorMaterials.EMERALD.getName(),
			ArmorMaterials.EMERALD.getTextureName(), ArmorMaterials.EMERALD.getDurability(), ArmorMaterials.EMERALD.getReductionAmounts(),
			ArmorMaterials.EMERALD.getEnchantability(), ArmorMaterials.EMERALD.getSoundOnEquip(), ArmorMaterials.EMERALD.getThoughness());
	public static final ArmorMaterial AMETHYST_ARMOR_MATERIAL = EnumHelper.addArmorMaterial(ArmorMaterials.EMERALD.getName(),
			ArmorMaterials.EMERALD.getTextureName(), ArmorMaterials.AMETHYST.getDurability(), ArmorMaterials.AMETHYST.getReductionAmounts(),
			ArmorMaterials.EMERALD.getEnchantability(), ArmorMaterials.AMETHYST.getSoundOnEquip(), ArmorMaterials.AMETHYST.getThoughness());
	public static final ArmorMaterial RUBY_ARMOR_MATERIAL = EnumHelper.addArmorMaterial(ArmorMaterials.RUBY.getName(),
			ArmorMaterials.RUBY.getTextureName(), ArmorMaterials.RUBY.getDurability(), ArmorMaterials.RUBY.getReductionAmounts(),
			ArmorMaterials.RUBY.getEnchantability(), ArmorMaterials.RUBY.getSoundOnEquip(), ArmorMaterials.RUBY.getThoughness());
	public static final ArmorMaterial TIGERS_EYE_ARMOR_MATERIAL = EnumHelper.addArmorMaterial(ArmorMaterials.TIGERS_EYE.getName(),
			ArmorMaterials.TIGERS_EYE.getTextureName(), ArmorMaterials.TIGERS_EYE.getDurability(), ArmorMaterials.TIGERS_EYE.getReductionAmounts(),
			ArmorMaterials.TIGERS_EYE.getEnchantability(), ArmorMaterials.TIGERS_EYE.getSoundOnEquip(), ArmorMaterials.TIGERS_EYE.getThoughness());
	
	
	//Misc items
	public static final Item AMETHYST = new GenericItem("amethyst", CreativeTabs.MISC); //Needs a texture
		public static final Item Ruby = new GenericItem("Ruby");
	public static final Item TigerEye = new GenericItem("TigerEye");
		public static final Item Titanium = new GenericItem("Titanium");
	public static final Item TitaniumNugget = new GenericItem("TitaniumNugget");
	public static final Item Uranium = new GenericItem("Uranium");
	public static final Item UraniumNugget = new GenericItem("UraniumNugget");
	public static final Item Aluminium = new GenericItem("Aluminium");
	//public static final Item Oil = new Oil("Oil");
	//public static final Item Salt = new Salt("Salt"); //Ant Functionality Delayed
	
	
	
	
	/**
	 * Equipment
	 */
	
	/*
	 * Tools
	 */
	
	public static final ToolMaterial EMERALD_TOOL_MATERIAL = EnumHelper.addToolMaterial(ToolMaterials.EMERALD.getName(),
			ToolMaterials.EMERALD.getHarvestLevel(), ToolMaterials.EMERALD.getMaxUses(), ToolMaterials.EMERALD.getEfficiency(),
			ToolMaterials.EMERALD.getDamage(), ToolMaterials.EMERALD.getEnchantability());
	public static final ToolMaterial AMETHYST_TOOL_MATERIAL = EnumHelper.addToolMaterial(ToolMaterials.AMETHYST.getName(),
			ToolMaterials.AMETHYST.getHarvestLevel(), ToolMaterials.AMETHYST.getMaxUses(), ToolMaterials.AMETHYST.getEfficiency(),
			ToolMaterials.AMETHYST.getDamage(), ToolMaterials.AMETHYST.getEnchantability());
	public static final ToolMaterial TIGERS_EYE_TOOL_MATERIAL = EnumHelper.addToolMaterial(ToolMaterials.TIGERS_EYE.getName(),
			ToolMaterials.TIGERS_EYE.getHarvestLevel(), ToolMaterials.TIGERS_EYE.getMaxUses(), ToolMaterials.TIGERS_EYE.getEfficiency(),
			ToolMaterials.TIGERS_EYE.getDamage(), ToolMaterials.TIGERS_EYE.getEnchantability());
	public static final ToolMaterial RUBY_TOOL_MATERIAL = EnumHelper.addToolMaterial(ToolMaterials.RUBY.getName(),
			ToolMaterials.RUBY.getHarvestLevel(), ToolMaterials.RUBY.getMaxUses(), ToolMaterials.RUBY.getEfficiency(),
			ToolMaterials.RUBY.getDamage(), ToolMaterials.RUBY.getEnchantability());
	public static final ToolMaterial OPTIMISED_TOOL_MATERIAL = EnumHelper.addToolMaterial(ToolMaterials.RUBY.getName(),
			ToolMaterials.OPTIMISED.getHarvestLevel(), ToolMaterials.OPTIMISED.getMaxUses(), ToolMaterials.OPTIMISED.getEfficiency(),
			ToolMaterials.OPTIMISED.getDamage(), ToolMaterials.OPTIMISED.getEnchantability());
	
	
	public static final Item emerald_pickaxe = new GenericPickaxe("emerald_pickaxe", EMERALD_TOOL_MATERIAL);
	public static final Item emerald_hoe = new GenericHoe("emerald_hoe", EMERALD_TOOL_MATERIAL);
	public static final Item emerald_sword = new GenericSword("emerald_sword", EMERALD_TOOL_MATERIAL);
	public static final Item emerald_shovel = new GenericShovel("emerald_shovel", EMERALD_TOOL_MATERIAL);
	//public static final Item emerald_axe = new GenericAxe("emerald_axe", EMERALD_TOOL_MATERIAL);

	public static final Item amethyst_pickaxe = new GenericPickaxe("amethyst_pickaxe", AMETHYST_TOOL_MATERIAL);
	public static final Item amethyst_hoe = new GenericHoe("amethyst_hoe", AMETHYST_TOOL_MATERIAL);
	public static final Item amethyst_sword = new GenericSword("amethyst_sword", AMETHYST_TOOL_MATERIAL);
	public static final Item amethyst_shovel = new GenericShovel("amethyst_shovel", AMETHYST_TOOL_MATERIAL);
	//public static final Item amethyst_axe = new GenericAxe("amythyst_axe", AMETHYST_TOOL_MATERIAL);

	
	public static final Item tigereye_pickaxe = new GenericPickaxe("tigereye_pickaxe", TIGERS_EYE_TOOL_MATERIAL);
	public static final Item tigereye_hoe = new GenericHoe("tigereye_hoe", TIGERS_EYE_TOOL_MATERIAL);
	public static final Item tigereye_sword = new GenericSword("tigereye_sword", TIGERS_EYE_TOOL_MATERIAL);
	public static final Item tigereye_shovel = new GenericShovel("tigereye_shovel", TIGERS_EYE_TOOL_MATERIAL);
	//public static final Item tigereye_axe = new GenericAxe("tigereye_axe", TIGERS_EYE_TOOL_MATERIAL);

	
	public static final Item ruby_pickaxe = new GenericPickaxe("ruby_pickaxe", RUBY_TOOL_MATERIAL);
	public static final Item ruby_hoe = new GenericHoe("ruby_hoe", RUBY_TOOL_MATERIAL);
	public static final Item ruby_sword = new GenericSword("ruby_sword", RUBY_TOOL_MATERIAL);
	public static final Item ruby_shovel = new GenericShovel("ruby_shovel", RUBY_TOOL_MATERIAL);
	//public static final Item ruby_axe = new GenericAxe("ruby_axe", RUBY_TOOL_MATERIAL);


	public static final Item optimised_miners_pic = new GenericPickaxe("optimised_miners_pic", OPTIMISED_TOOL_MATERIAL);
	public static final Item optimised_trench_diggers_shovel = new GenericShovel("optimised_trench_diggers_shovel", OPTIMISED_TOOL_MATERIAL);

	
	
	
	/*
	 * Armors
	 */
	
	//Emerald set
	public static final ItemArmor EMERALD_HELMET = new GenericArmor("emerald_helmet", EMERALD_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD);
	public static final ItemArmor EMERALD_CHESTPLATE = new GenericArmor("emerald_chestplate", EMERALD_ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST);
	public static final ItemArmor EMERALD_LEGGINGS = new GenericArmor("emerald_leggings", EMERALD_ARMOR_MATERIAL, 2, EntityEquipmentSlot.LEGS);
	public static final ItemArmor EMERALD_BOOTS = new GenericArmor("emerald_boots", EMERALD_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET);
	
	//Amethyst set
	public static final ItemArmor AMETHYST_HELMET = new GenericArmor("amethyst_helmet", AMETHYST_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD);
	public static final ItemArmor AMETHYST_CHESTPLATE = new GenericArmor("amethyst_chestplate", AMETHYST_ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST);
	public static final ItemArmor AMETHYST_LEGGINGS = new GenericArmor("aethyst_leggings", AMETHYST_ARMOR_MATERIAL, 2, EntityEquipmentSlot.LEGS);
	public static final ItemArmor AMETHYST_BOOTS = new GenericArmor("amethyst_boots", AMETHYST_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET);
	
	//Tiger's Eye set
		public static final ItemArmor TIGEREYE_HELMET = new GenericArmor("tigereye_helmet", TIGERS_EYE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD);
		public static final ItemArmor TIGEREYE_CHESTPLATE = new GenericArmor("tigereye_chestplate", TIGERS_EYE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST);
		public static final ItemArmor TIGEREYE_LEGGINGS = new GenericArmor("tigereye_leggings", TIGERS_EYE_ARMOR_MATERIAL, 2, EntityEquipmentSlot.LEGS);
		public static final ItemArmor TIGEREYE_BOOTS = new GenericArmor("tigereye_boots", TIGERS_EYE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET);
		
		//Ruby set
		public static final ItemArmor RUBY_HELMET = new GenericArmor("ruby_helmet", RUBY_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD);
		public static final ItemArmor RUBY_CHESTPLATE = new GenericArmor("ruby_chestplate", RUBY_ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST);
		public static final ItemArmor RUBY_LEGGINGS = new GenericArmor("ruby_leggings", RUBY_ARMOR_MATERIAL, 2, EntityEquipmentSlot.LEGS);
		public static final ItemArmor RUBY_BOOTS = new GenericArmor("ruby_boots", RUBY_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET);
		
	public Items() {
		
	}

}
