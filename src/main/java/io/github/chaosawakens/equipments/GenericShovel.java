package io.github.chaosawakens.equipments;

import io.github.chaosawakens.Items;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSpade;

/**
 * Generic shovel class aka any shovel with no special behavior
 * @author invalid2
 */
public class GenericShovel extends ItemSpade
{
	/**
	 * 
	 * @param name Unlocalized and registry names
	 * @param material Tool material
	 */
	public GenericShovel(String name, ToolMaterial material)
	{
		super(material);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		
		Items.ITEMS.add(this);
	}
}
