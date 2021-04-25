package io.github.chaosawakens.blocks;

import com.google.common.base.Predicate;

import io.github.chaosawakens.ModBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.world.DimensionType;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class OreBlock extends GenericBlock
{
	
	private DimensionType dimension;
	private int minimumY;
	private int maximumY;
	private int size;
	private int tries;
	private Predicate<IBlockState> blockReplaced;
	private WorldGenMinable worldGenMinable;
	
	/**
	 * 
	 * @param name Unlocalized and registry names
	 * @param material Block material
	 * @param hardness Block hardness
	 * @param resistance Block resistance
	 * @param dimensionType Dimension where the ore will be generated
	 * @param minimumY Minimum Y level for ore generation
	 * @param maximumY Maximum Y level for ore generation
	 * @param size Size of the clumps
	 * @param tries Number of generation attempts
	 * @param blockReplaced Block that will be replaced by ore
	 */
	public OreBlock(String name, Material material, float hardness, float resistance, DimensionType dimensionType, int minimumY, int maximumY, int size, int tries, Predicate<IBlockState> blockReplaced)
	{
		super(name, material, hardness, resistance);
		
		this.dimension = dimensionType;
		this.minimumY = minimumY;
		this.maximumY = maximumY;
		this.size = size;
		this.tries = tries;
		this.blockReplaced = blockReplaced;
		this.worldGenMinable = new WorldGenMinable(this.getDefaultState(), tries, blockReplaced);
		
		ModBlocks.ORES.add(this);
	}
	
	/**
	 * 
	 * @param name Unlocalized and registry names
	 * @param material Block material
	 * @param hardsistance Block resistance+hardness
	 * @param dimensionType Dimension where the ore will be generated
	 * @param minimumY Minimum Y level for ore generation
	 * @param maximumY Maximum Y level for ore generation
	 * @param size Size of the clumps
	 * @param tries Number of generation attempts
	 * @param blockReplaced Block that will be replaced by ore
	 */
	public OreBlock(String name, Material material, float hardsistance, DimensionType dimensionType, int minimumY, int maximumY, int size, int tries, Predicate<IBlockState> blockReplaced)
	{
		this(name, material, hardsistance, hardsistance, dimensionType, minimumY, maximumY, size, tries, blockReplaced);
	}
	
	/**
	 * 
	 * @param name Unlocalized and registry names
	 * @param material Block material
	 * @param hardness Block hardness
	 * @param resistance Block resistance
	 * @param dimensionType Dimension where the ore will be generated
	 * @param minimumY Minimum Y level for ore generation
	 * @param maximumY Maximum Y level for ore generation
	 * @param size Size of the clumps
	 * @param tries Number of generation attempts
	 */
	public OreBlock(String name, Material material, float hardness, float resistance, DimensionType dimensionType, int minimumY, int maximumY, int size, int tries)
	{
		this(name, material, hardness, resistance, dimensionType, minimumY, maximumY, size, tries, BlockMatcher.forBlock(Blocks.STONE));
	}
	
	/**
	 * 
	 * @param name Unlocalized and registry names
	 * @param material Block material
	 * @param hardsistance Block resistance+hardness
	 * @param dimensionType Dimension where the ore will be generated
	 * @param minimumY Minimum Y level for ore generation
	 * @param maximumY Maximum Y level for ore generation
	 * @param size Size of the clumps
	 * @param tries Number of generation attempts
	 */
	public OreBlock(String name, Material material, float hardsistance, DimensionType dimensionType, int minimumY, int maximumY, int size, int tries)
	{
		this(name, material, hardsistance, hardsistance, dimensionType, minimumY, maximumY, size, tries);
	}
	
	public DimensionType getDimension()
	{
		return dimension;
	}
	
	public int getMinimumY()
	{
		return minimumY;
	}
	
	public int getMaximumY()
	{
		return maximumY;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public int getTries()
	{
		return tries;
	}
	
	public WorldGenMinable getWorldGenMinable()
	{
		return worldGenMinable;
	}
}
