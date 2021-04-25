package io.github.chaosawakens.worldgenerators;

import java.util.Random;

import io.github.chaosawakens.ModBlocks;
import io.github.chaosawakens.Utils;
import io.github.chaosawakens.blocks.OreBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
//import io.github.chaosawakens.Blocks;


public class OreWorldGenerator implements IWorldGenerator
{

	public OreWorldGenerator() {}
	
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		for(OreBlock ore : ModBlocks.ORES)
			if(world.provider.getDimensionType().equals(ore.getDimension()))
				Utils.generateOre(ore, world, random, chunkX, chunkZ);
	}
}
