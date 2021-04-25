package io.github.chaosawakens.blocks;

import io.github.chaosawakens.ModBlocks;
import io.github.chaosawakens.ModItems;
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
	 * @param hardness Block hardness
	 * @param resistance Block resistance
	 */
	public GenericBlock(String name, Material material, CreativeTabs tab, float hardness, float resistance)
	{
		super(material);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		
		setHardness(hardness);
		setResistance(resistance);
		
		ModBlocks.BLOCKS.add(this);
		
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	/**
	 * 
	 * @param name Unlocalized and registry names
	 * @param material Block material
	 * @param tab Creative tab
	 * @param hardsistance Block hardness and resistance
	 */
	public GenericBlock(String name, Material material, CreativeTabs tab, float hardsistance)
	{
		this(name, material, tab, hardsistance, hardsistance);
	}
	
	/**
	 * Tab defaults to CreativeTabs.BUILDING_BLOCKS
	 * @param name Unlocalized and registry names
	 * @param material Block material
	 * @param tab Creative tab
	 * @param hardsistance Block hardness and resistance
	 */
	public GenericBlock(String name, Material material, float hardness, float resistance)
	{
		this(name, material, CreativeTabs.BUILDING_BLOCKS, hardness, resistance);
	}
	
	/**
	 * Hardness and resistance default to 1.0f
	 * @param name Unlocalized and registry names
	 * @param material Block material
	 * @param tab Creative tab
	 */
	public GenericBlock(String name, Material material, CreativeTabs tab)
	{
		this(name, material, tab, 1.0f, 1.0f);
	}
	
	/**
	 * Tab defaults to CreativeTabs.BUILDING_BLOCKS, hardness and resistance default to 1.0f
	 * @param name Unlocalized and registry names
	 * @param material Block material
	 */
	public GenericBlock(String name, Material material)
	{
		this(name, material, CreativeTabs.BUILDING_BLOCKS, 1.0f, 1.0f);
	}
	
	/**
	 * Tab defaults to CreativeTabs.BUILDING_BLOCKS, material defaults to Material.ROCK,
	 * hardness and resistance default to 1.0f
	 * @param name Unlocalized and registry names
	 */
	public GenericBlock(String name)
	{
		this(name, Material.ROCK, CreativeTabs.BUILDING_BLOCKS, 1.0f, 1.0f);
	}
}
