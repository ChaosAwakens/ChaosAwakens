package io.github.chaosawakens.equipments;

import io.github.chaosawakens.Items;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemPickaxe;

/**
 * Generic pickaxe class aka any pickaxe with no special behavior
 * @author invalid2
 */
public class GenericPickaxe extends ItemPickaxe
{
	
	/**
	 * 
	 * @param name Unlocalized and registry names
	 * @param material Tool material
	 */
	public GenericPickaxe(String name, ToolMaterial material)
	{
		super(material);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.TOOLS);
		
		Items.ITEMS.add(this);
	}
}
