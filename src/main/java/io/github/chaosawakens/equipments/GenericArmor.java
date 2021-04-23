package io.github.chaosawakens.equipments;

import io.github.chaosawakens.Items;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

/**
 * Generic armor class aka any armor with no special behavior
 * @author invalid2
 */
public class GenericArmor extends ItemArmor
{
	public String armourName;
	
	/**
	 * 
	 * @param name Name
	 * @param material Material
	 * @param renderIndex Render Index
	 * @param equipmentSlot Equipment slot
	 * 
	 * @apiNote Arg javadoc names are a stub
	 */
	public GenericArmor(String name, ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot)
	{
		super(material, renderIndex, equipmentSlot);
		
		armourName = name;
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		
		Items.ITEMS.add(this);
	}
	
	
	/**
	 * Dont know what this code below for, so i didnt remove it -invalid2
	 */
//	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
//	{
//		if(stack.toString().contains("leggings"))
//		{
//			return "chaosawakens:" + armourName + "_2.png";
//		}
//	if(stack.toString().contains("Leggings")) if (itemID == ExampleMod.EmeraldLeggings.item);
//	{
//		return "chaosawakens:" + armourName + "_2.png";
//
//		
//	}
//	return "chaosawakens:" + armourName + "_1.png";
//
//	}
}
