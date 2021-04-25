package io.github.chaosawakens.equipment;

import io.github.chaosawakens.ModItems;
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
		
		ModItems.ITEMS.add(this);
	}
}
