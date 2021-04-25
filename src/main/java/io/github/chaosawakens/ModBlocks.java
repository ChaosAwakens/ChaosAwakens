package io.github.chaosawakens;

import java.util.ArrayList;
import java.util.List;

import io.github.chaosawakens.blocks.GenericBlock;
import io.github.chaosawakens.blocks.OreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Class for block creation, such that the main class is not unreadable
 * @author invalid2
 */
public class ModBlocks
{
	
	/**
	 * List holding ALL mod blocks
	 */
	public static final List<Block> BLOCKS = new ArrayList<Block>();

	/**
	 * List holding ALL mod blocks
	 */
	public static final List<OreBlock> ORES = new ArrayList<OreBlock>();
	
	public static final Block RUBY_BLOCK = new GenericBlock("ruby_block", Material.IRON);
	public static final Block TIGERS_EYE_BLOCK = new GenericBlock("tigers_eye_block", Material.IRON);
	public static final Block TITANIUM_BLOCK = new GenericBlock("titanium_block", Material.IRON);
	public static final Block URANIUM_BLOCK = new GenericBlock("uranium_block", Material.IRON);
	public static final Block ALUMINIUM_BLOCK = new GenericBlock("aluminum_block", Material.IRON);
	
	public static final Block OIL_ORE = new GenericBlock("oil_ore", Material.IRON);
	public static final Block RUBY_ORE = new GenericBlock("ruby_ore", Material.IRON);
	public static final Block AMETHYST_ORE = new GenericBlock("amethyst_ore", Material.IRON);
	
	public ModBlocks()
	{
		
	}
}
