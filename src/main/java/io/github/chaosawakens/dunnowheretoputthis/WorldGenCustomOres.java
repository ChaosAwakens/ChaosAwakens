package io.github.chaosawakens.dunnowheretoputthis;

import java.util.Random;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
//import io.github.chaosawakens.Blocks;


public class WorldGenCustomOres implements IWorldGenerator{

	private WorldGenerator ore_overworld_ruby, ore_overworld_tigereye, ore_overworld_amathest, ore_overworld_oil, ore_overworld_titanium, ore_overworld_uranium, ore_overworld_aluminium, ore_overworld_salt;
	
	public WorldGenCustomOres()
	{
		ore_overworld_ruby = new WorldGenMinable(io.github.chaosawakens.Blocks.ore_overworld.getDefaultState().withProperty(RubyOre.VARIANT, EnumHandler0.EnumType.RUBY), 4, BlockMatcher.forBlock(Blocks.STONE));
		ore_overworld_tigereye = new WorldGenMinable(io.github.chaosawakens.Blocks.ore_overworld.getDefaultState().withProperty(RubyOre.VARIANT, EnumHandler0.EnumType.TIGEREYE), 6, BlockMatcher.forBlock(Blocks.STONE));
		ore_overworld_amathest = new WorldGenMinable(io.github.chaosawakens.Blocks.ore_overworld.getDefaultState().withProperty(RubyOre.VARIANT, EnumHandler0.EnumType.AMATHYEST), 5, BlockMatcher.forBlock(Blocks.STONE));
ore_overworld_oil = new WorldGenMinable(io.github.chaosawakens.Blocks.ore_overworld.getDefaultState().withProperty(RubyOre.VARIANT, EnumHandler0.EnumType.UNPROCESSEDOIL), 15, BlockMatcher.forBlock(Blocks.STONE));
ore_overworld_titanium = new WorldGenMinable(io.github.chaosawakens.Blocks.ore_overworld.getDefaultState().withProperty(RubyOre.VARIANT, EnumHandler0.EnumType.TITANIUM), 3, BlockMatcher.forBlock(Blocks.STONE));
ore_overworld_uranium = new WorldGenMinable(io.github.chaosawakens.Blocks.ore_overworld.getDefaultState().withProperty(RubyOre.VARIANT, EnumHandler0.EnumType.URANIUM), 2, BlockMatcher.forBlock(Blocks.STONE));
ore_overworld_aluminium = new WorldGenMinable(io.github.chaosawakens.Blocks.ore_overworld.getDefaultState().withProperty(RubyOre.VARIANT, EnumHandler0.EnumType.ALUMINIUM), 6, BlockMatcher.forBlock(Blocks.STONE));
ore_overworld_salt = new WorldGenMinable(io.github.chaosawakens.Blocks.ore_overworld.getDefaultState().withProperty(RubyOre.VARIANT, EnumHandler0.EnumType.SALT), 10, BlockMatcher.forBlock(Blocks.STONE));

	
	}
	
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		// TODO Auto-generated method stub
		switch(world.provider.getDimension()) {
		case -1:
//nether
			
			
			
			//chance, minblock, maxblock
		break;
		case 0:
//overworld
			
			runGenerator(ore_overworld_ruby, world, random, chunkX, chunkZ, 50, 100, 250);

			runGenerator(ore_overworld_tigereye, world, random, chunkX, chunkZ, 40, 100, 250);
			
			runGenerator(ore_overworld_amathest, world, random, chunkX, chunkZ, 30, 100, 250);

			runGenerator(ore_overworld_oil, world, random, chunkX, chunkZ, 300, 5, 250);
			runGenerator(ore_overworld_salt, world, random, chunkX, chunkZ, 200, 5, 250);
			runGenerator(ore_overworld_titanium, world, random, chunkX, chunkZ, 20, 100, 250);
			runGenerator(ore_overworld_uranium, world, random, chunkX, chunkZ,10, 100, 250);

			
			
break;
	
		case 1:
//end
		
		
		}
	}
	
	
	
	public void runGenerator(WorldGenerator gen, World world, Random rand, int chunkX, int chunkZ, int chance, int minHeight, int maxHeight)
	{
		int heightDiff = maxHeight - minHeight + 1;
		if(minHeight > maxHeight || minHeight<0 || maxHeight > 256) throw new IllegalArgumentException("Ore Generated out of bounds");
		{
			int x = chunkX * 16 + rand.nextInt(16);
			int y = minHeight + rand.nextInt(heightDiff);
			int z = chunkZ * 16 + rand.nextInt(16);
		
			gen.generate(world, rand, new BlockPos(x,y,z));
			
		}
	
	}
	
	
}
