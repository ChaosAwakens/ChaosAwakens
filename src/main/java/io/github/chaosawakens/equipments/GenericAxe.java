package io.github.chaosawakens.equipments;

import io.github.chaosawakens.Items;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemAxe;

/**
 * Generic axe class aka any axe with no special behavior
 * @author invalid2
 */
public class GenericAxe extends ItemAxe
{
	
	/**
	 * 
	 * @param name Both unlocalized and registry names
	 * @param material Tool material
	 */
	public GenericAxe(String name, ToolMaterial material)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.TOOLS);
		
		Items.ITEMS.add(this);
	}
}
