package io.github.chaosawakens.equipment;

import io.github.chaosawakens.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemHoe;

/**
 * Generic axe class aka any axe with no special behavior
 * @author invalid2
 */
public class GenericHoe extends ItemHoe
{
	
	/**
	 * 
	 * @param name Unlocalized and registry names
	 * @param material Tool material
	 */
	public GenericHoe(String name, ToolMaterial material)
	{
		super(material);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.TOOLS);
		
		ModItems.ITEMS.add(this);
	}
}