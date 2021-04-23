package io.github.chaosawakens.blocks;

import io.github.chaosawakens.Blocks;
import io.github.chaosawakens.Items;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

/**
 * Generic block class aka any block with no special behavior
 * @author invalid2
 */
public class GenericBlock extends Block
{
	/**
	 * 
	 * @param name Unlocalized and registry names
	 * @param material Block material
	 * @param tab Creative tab
	 */
	public GenericBlock(String name, Material material, CreativeTabs tab)
	{
		super(material);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
	
		Blocks.BLOCKS.add(this);
		
		Items.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	/**
	 * Tab defaults to CreativeTabs.BUILDING_BLOCKS
	 * @param name Unlocalized and registry names
	 * @param material Block material
	 */
	public GenericBlock(String name, Material material)
	{
		super(material);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	
		Blocks.BLOCKS.add(this);
		
		Items.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	/**
	 * Tab defaults to CreativeTabs.BUILDING_BLOCKS, material defaults to Material.ROCK
	 * @param name Unlocalized and registry names
	 */
	public GenericBlock(String name)
	{
		super(Material.ROCK);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	
		Blocks.BLOCKS.add(this);
		
		Items.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
}
