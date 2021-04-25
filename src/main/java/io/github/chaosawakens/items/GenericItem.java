package io.github.chaosawakens.items;

import io.github.chaosawakens.ModItems;
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
		
		ModItems.ITEMS.add(this);
	}
	
	/**
	 * Item max stack defaults to 64
	 * @param name Both unlocalized and registry names
	 * @param tab Item creative tab
	 */
	public GenericItem(String name, CreativeTabs tab)
	{
		this(name, tab, 64);
	}
	
	/**
	 * Tab defaults to CreativeTabs.MISC
	 * @param name Both unlocalized and registry names
	 * @param tab Item creative tab
	 */
	public GenericItem(String name, int maxStackSize)
	{
		this(name, CreativeTabs.MISC, maxStackSize);
	}
	/**
	 * Tab defaults to CreativeTabs.MISC and item max stack to 64
	 * @param name Both unlocalized and registry names
	 */
	public GenericItem(String name)
	{
		this(name, CreativeTabs.MISC, 64);
	}
}
