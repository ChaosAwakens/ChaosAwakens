package io.github.chaosawakens.items;

import io.github.chaosawakens.Items;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Generic item class aka any items with no special behavior,
 * several constructors are provided for convenience
 * @author invalid2
 *
 */
public class GenericItem extends Item {
	
	/**
	 * 
	 * @param name Both unlocalized and registry names
	 * @param tab Item creative tab
	 * @param maxStackSize Maximum item stack size
	 */
	public GenericItem(String name, CreativeTabs tab, int maxStackSize)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		setMaxStackSize(maxStackSize);
		
		Items.ITEMS.add(this);
	}
	
	/**
	 * 
	 * @param name Both unlocalized and registry names
	 * @param tab Item creative tab
	 */
	public GenericItem(String name, CreativeTabs tab)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		
		Items.ITEMS.add(this);
	}
	
	/**
	 * 
	 * @param name Both unlocalized and registry names
	 */
	public GenericItem(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MISC);
		
		Items.ITEMS.add(this);
	}
}
