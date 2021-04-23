package io.github.chaosawakens;

import java.util.ArrayList;
import java.util.List;

import io.github.chaosawakens.equipments.GenericArmor;
import io.github.chaosawakens.items.GenericItem;
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
	
	/**
	 * Equipment
	 */
	
	/*
	 * Tools
	 */
	
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
	
	public Items() {
		
	}

}
