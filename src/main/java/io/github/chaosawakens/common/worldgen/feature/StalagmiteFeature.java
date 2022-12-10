package io.github.chaosawakens.common.worldgen.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import io.github.chaosawakens.common.config.CACommonConfig;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class StalagmiteFeature extends Feature<StalagmiteFeatureConfig> {

	public StalagmiteFeature(Codec<StalagmiteFeatureConfig> config) {
		super(config);
	}

	@Override
	public boolean place(ISeedReader reader, ChunkGenerator gen, Random rand, BlockPos pos, StalagmiteFeatureConfig cfg) {
		if (1.0F / cfg.chance < rand.nextFloat() || reader.isWaterAt(pos.below())) return false;

		float steepness = cfg.baseSteepness + ((rand.nextFloat() - 0.5F) * cfg.variation) * cfg.baseSteepness;
		int variationFloor = (int) Math.floor(steepness);
		int radius = cfg.baseRadius + (rand.nextInt(variationFloor * 2 + 1) - variationFloor);
		if(radius == 0) radius++;

		pos = pos.below(pos.getY() - getLowestCorner(gen, pos, radius) + 1);
		for(int j = 0; j < radius * radius * steepness; j++) {
			for(int i = -radius; i <= radius; i++) {
				for(int k = -radius; k <= radius; k++) {
					float pillarCeiling = (-i * i * steepness -k * k * steepness + radius * radius * steepness);
					int smallerThanZeroFlag = i < 0 || k < 0 ? -1 : 0; 
					if(pillarCeiling >= -1 * smallerThanZeroFlag && pillarCeiling >= j) {
						reader.setBlock(pos.offset(i, j, k), cfg.block, 2);
						
						if (CACommonConfig.COMMON.enableStalagmiteOreGen.get()) {
							// Copy the position into a final BlockPos variable, helps a bit with optimization
							final BlockPos fPos = pos.offset(i, j, k);
							
							// OPT
							final IForgeRegistry<Block> allLoadedBlocks = ForgeRegistries.BLOCKS;
							
							for (Block block : allLoadedBlocks) {
								//TODO Optimize more --Meme Man
								switch (cfg.type) {
								default:
									break;
								case ORE_COMMON:
									// Prevent major lag in chunk generation
									if (!block.getRegistryName().toString().contains("_ore") || block.getRegistryName().toString().contains("nether") || block.getRegistryName().toString().contains("end")) continue;
									// Prevent some of the more OP ores generating through harvest level, for now
									if (rand.nextInt(400) == 0 && block.getHarvestLevel(reader.getBlockState(fPos)) < 3 && block.getRegistryName().toString().contains("_ore")) {		
								//		reader.setBlock(fPos, block.defaultBlockState(), 3);
										reader.getChunk(fPos).setBlockState(fPos, block.defaultBlockState(), false);
									}
									break;
									
								case ORE_RARE:
									// Prevent major lag in chunk generation
									if (!block.getRegistryName().toString().contains("_ore") || block.getRegistryName().toString().contains("nether") || block.getRegistryName().toString().contains("blackstone") || block.getRegistryName().toString().contains("end")) continue;
									// Prevent OP ores from generating too much
									if (rand.nextInt(1200) == 0 && block.getHarvestLevel(reader.getBlockState(fPos)) >= 3 && block.getRegistryName().toString().contains("_ore")) {		
						//				reader.setBlock(fPos, block.defaultBlockState(), 3);
										reader.getChunk(fPos).setBlockState(fPos, block.defaultBlockState(), false);
									}
									break;
								
								case ORE_ALL:
									// Prevent major lag in chunk generation
									if (!block.getRegistryName().toString().contains("_ore") || block.getRegistryName().toString().contains("nether") || block.getRegistryName().toString().contains("end")) continue;
									if (rand.nextInt(400) == 0 && block.getRegistryName().toString().contains("_ore")) {		
								//		reader.setBlock(fPos, block.defaultBlockState(), 3);
										reader.getChunk(fPos).setBlockState(fPos, block.defaultBlockState(), false);
									}
									break;
									
								case FOSSIL:
									// Prevent major lag in chunk generation
									if (!block.getRegistryName().toString().contains("fossil") || block.getRegistryName().toString().contains("nether") || block.getRegistryName().toString().contains("end")) continue;
									if (rand.nextInt(400) == 0 && block.getRegistryName().toString().contains("fossil")) {		
									//	reader.setBlock(fPos, block.defaultBlockState(), 3);
										reader.getChunk(fPos).setBlockState(fPos, block.defaultBlockState(), false);
									}
									break;
								}
							}
						}
					}
							
					// I know using array loops instead of iteration is only micro-optimization, but DAMN the chunks load slow as hell with this.
					// Since it'll loop through every block anyway and I can't get it to just simply
					// skip/continue over the non-ore blocks
					// Array loops ftw 
	/*				Registry.BLOCK.forEach(block -> {
						if ((block instanceof OreBlock || block.getRegistryName().toString().contains("_ore")) && rand.nextInt(3000) == 0) {
							
							reader.setBlock(fPos, block.defaultBlockState(), 2);
						}
					});*/
				}
			}
		}
		return true;
	}

	private int getLowestCorner(ChunkGenerator gen, BlockPos pos, int radius) {
		int pXpY = gen.getBaseHeight(pos.getX() + radius, pos.getZ() + radius, Heightmap.Type.WORLD_SURFACE);
		int mXpY = gen.getBaseHeight(pos.getX() - radius, pos.getZ() + radius, Heightmap.Type.WORLD_SURFACE);
		int pXmY = gen.getBaseHeight(pos.getX() + radius, pos.getZ() - radius, Heightmap.Type.WORLD_SURFACE);
		int mXmY = gen.getBaseHeight(pos.getX() - radius, pos.getZ() - radius, Heightmap.Type.WORLD_SURFACE);
		return Math.min(Math.min(Math.min(pXpY, mXpY), pXmY), mXmY);
	}
}
