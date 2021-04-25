package io.github.chaosawakens;

import java.util.Random;

import com.google.common.base.Predicate;

import io.github.chaosawakens.blocks.OreBlock;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class Utils
{
	public Utils() {}
	
	public static void generateOre(OreBlock ore, World world, Random random, int x, int z)
	{
		for(int i = 0; i < ore.getTries(); i++)
		{
			BlockPos generationPos = new BlockPos(random.nextInt(16)+x, random.nextInt(ore.getMaximumY()-ore.getMinimumY())+ore.getMinimumY(), random.nextInt(16)+z);
			
			ore.getWorldGenMinable().generate(world, random, generationPos);
		}
	}
}
