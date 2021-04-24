
package net.mcreator.chaosawakens.world.structure;

import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.WorldServer;
import net.minecraft.world.World;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Rotation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Mirror;
import net.minecraft.init.Blocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.Block;

import net.mcreator.chaosawakens.ElementsChaosawakensMod;

import java.util.Random;

@ElementsChaosawakensMod.ModElement.Tag
public class StructureBigTreeDungeon extends ElementsChaosawakensMod.ModElement {
	public StructureBigTreeDungeon(ElementsChaosawakensMod instance) {
		super(instance, 8);
	}

	@Override
	public void generateWorld(Random random, int i2, int k2, World world, int dimID, IChunkGenerator cg, IChunkProvider cp) {
		boolean dimensionCriteria = false;
		boolean isNetherType = false;
		if (dimID == 0)
			dimensionCriteria = true;
		if (!dimensionCriteria)
			return;
		if ((random.nextInt(1000000) + 1) <= 10000) {
			int count = random.nextInt(1) + 1;
			for (int a = 0; a < count; a++) {
				int i = i2 + random.nextInt(16) + 8;
				int k = k2 + random.nextInt(16) + 8;
				int height = 255;
				if (isNetherType) {
					boolean notpassed = true;
					while (height > 0) {
						if (notpassed && (world.isAirBlock(new BlockPos(i, height, k)) || !world.getBlockState(new BlockPos(i, height, k)).getBlock()
								.getMaterial(world.getBlockState(new BlockPos(i, height, k))).blocksMovement()))
							notpassed = false;
						else if (!notpassed && !world.isAirBlock(new BlockPos(i, height, k)) && world.getBlockState(new BlockPos(i, height, k))
								.getBlock().getMaterial(world.getBlockState(new BlockPos(i, height, k))).blocksMovement())
							break;
						height--;
					}
				} else {
					while (height > 0) {
						if (!world.isAirBlock(new BlockPos(i, height, k)) && world.getBlockState(new BlockPos(i, height, k)).getBlock()
								.getMaterial(world.getBlockState(new BlockPos(i, height, k))).blocksMovement())
							break;
						height--;
					}
				}
				int j = height - 1;
				IBlockState blockAt = world.getBlockState(new BlockPos(i, j + 1, k));
				boolean blockCriteria = false;
				IBlockState require;
				require = Blocks.GRASS.getDefaultState();
				if (blockAt.getBlock() == require.getBlock())
					blockCriteria = true;
				if (!blockCriteria)
					continue;
				boolean biomeCriteria = false;
				Biome biome = world.getBiome(new BlockPos(i, j, k));
				if (Biome.REGISTRY.getNameForObject(biome).equals(new ResourceLocation("plains")))
					biomeCriteria = true;
				if (!biomeCriteria)
					continue;
				if (world.isRemote)
					return;
				Template template = ((WorldServer) world).getStructureTemplateManager().getTemplate(world.getMinecraftServer(),
						new ResourceLocation("chaosawakens", "tree_dungeon_2"));
				if (template == null)
					return;
				Rotation rotation = Rotation.NONE;
				Mirror mirror = Mirror.NONE;
				BlockPos spawnTo = new BlockPos(i, j + 0, k);
				IBlockState iblockstate = world.getBlockState(spawnTo);
				world.notifyBlockUpdate(spawnTo, iblockstate, iblockstate, 3);
				template.addBlocksToWorldChunk(world, spawnTo, new PlacementSettings().setRotation(rotation).setMirror(mirror)
						.setChunk((ChunkPos) null).setReplacedBlock((Block) null).setIgnoreStructureBlock(false).setIgnoreEntities(false));
			}
		}
	}
}
