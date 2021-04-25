package io.github.chaosawakens.equipment;

import io.github.chaosawakens.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

/**
 * Generic sword class aka any sword with no special behavior
 * @author invalid2
 */
public class GenericSword extends ItemSword
{

	/**
	 * 
	 * @param name Unlocalized and registry names
	 * @param material Tool material
	 */
	public GenericSword(String name, ToolMaterial material) {
		super(material);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.COMBAT);
		
		ModItems.ITEMS.add(this);
	}	
}
