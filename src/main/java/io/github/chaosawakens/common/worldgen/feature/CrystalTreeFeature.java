package io.github.chaosawakens.common.worldgen.feature;

import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.BitSetVoxelShapePart;
import net.minecraft.util.math.shapes.VoxelShapePart;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;

public class CrystalTreeFeature extends Feature<BaseTreeFeatureConfig> {
	
	public CrystalTreeFeature(Codec<BaseTreeFeatureConfig> codec) {
		super(codec);
	}
	
	public static boolean isLogsAt(IWorldGenerationBaseReader reader, BlockPos pos) {
		return isReplaceableAt(reader, pos) || reader.hasBlockState(pos, (state) -> {
			return state.isIn(BlockTags.LOGS);
		});
	}
	
	private static boolean isVinesAt(IWorldGenerationBaseReader reader, BlockPos pos) {
		return reader.hasBlockState(pos, (state) -> {
			return state.matchesBlock(Blocks.VINE);
		});
	}
	
	private static boolean isWaterAt(IWorldGenerationBaseReader reader, BlockPos pos) {
		return reader.hasBlockState(pos, (state) -> {
			return state.matchesBlock(Blocks.WATER);
		});
	}
	
	public static boolean isAirOrLeavesAt(IWorldGenerationBaseReader reader, BlockPos pos) {
		return reader.hasBlockState(pos, (state) -> {
			return state.isAir() || state.isIn(BlockTags.LEAVES);
		});
	}
	
	private static boolean isDirtOrFarmlandAt(IWorldGenerationBaseReader reader, BlockPos pos) {
		return reader.hasBlockState(pos, (state) -> {
			Block block = state.getBlock();
			return block.equals(CABlocks.CRYSTAL_GRASS_BLOCK.get());
		});
	}
	
	private static boolean isTallPlantAt(IWorldGenerationBaseReader reader, BlockPos pos) {
		return reader.hasBlockState(pos, (state) -> {
			Material material = state.getMaterial();
			return material == Material.TALL_PLANTS;
		});
	}
	
	public static void setBlockStateWithoutUpdate(IWorldWriter writer, BlockPos pos, BlockState state) {
		writer.setBlockState(pos, state, 19);
	}
	
	public static boolean isReplaceableAt(IWorldGenerationBaseReader reader, BlockPos pos) {
		return isAirOrLeavesAt(reader, pos) || isTallPlantAt(reader, pos) || isWaterAt(reader, pos);
	}
	
	/**
	 * Called when placing the tree feature.
	 */
	private boolean place(IWorldGenerationReader generationReader, Random rand, BlockPos positionIn, Set<BlockPos> logPositions, Set<BlockPos> foliagePositions, MutableBoundingBox boundingBoxIn, BaseTreeFeatureConfig configIn) {
		int i = configIn.trunkPlacer.getHeight(rand);
		int j = configIn.foliagePlacer.func_230374_a_(rand, i, configIn);
		int k = i - j;
		int l = configIn.foliagePlacer.func_230376_a_(rand, k);
		BlockPos blockpos;
		if (!configIn.forcePlacement) {
			int i1 = generationReader.getHeight(Heightmap.Type.OCEAN_FLOOR, positionIn).getY();
			int j1 = generationReader.getHeight(Heightmap.Type.WORLD_SURFACE, positionIn).getY();
			if (j1 - i1 > configIn.maxWaterDepth) {
				return false;
			}
			
			int k1;
			if (configIn.heightmap == Heightmap.Type.OCEAN_FLOOR) {
				k1 = i1;
			} else if (configIn.heightmap == Heightmap.Type.WORLD_SURFACE) {
				k1 = j1;
			} else {
				k1 = generationReader.getHeight(configIn.heightmap, positionIn).getY();
			}
			
			blockpos = new BlockPos(positionIn.getX(), k1, positionIn.getZ());
		} else {
			blockpos = positionIn;
		}
		
		if (blockpos.getY() >= 1 && blockpos.getY() + i + 1 <= 256) {
			if (!isDirtOrFarmlandAt(generationReader, blockpos.down())) {
				return false;
			} else {
				OptionalInt optionalint = configIn.minimumSize.func_236710_c_();
				int l1 = this.getMaxFreeTreeHeightAt(generationReader, i, blockpos, configIn);
				if (l1 >= i || optionalint.isPresent() && l1 >= optionalint.getAsInt()) {
					List<FoliagePlacer.Foliage> list = configIn.trunkPlacer.getFoliages(generationReader, rand, l1, blockpos, logPositions, boundingBoxIn, configIn);
					list.forEach((foliage) -> {
						configIn.foliagePlacer.func_236752_a_(generationReader, rand, configIn, l1, foliage, j, l, foliagePositions, boundingBoxIn);
					});
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}
	
	private int getMaxFreeTreeHeightAt(IWorldGenerationBaseReader reader, int trunkHeight, BlockPos topPosition, BaseTreeFeatureConfig config) {
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
		
		for (int i = 0; i <= trunkHeight + 1; ++i) {
			int j = config.minimumSize.func_230369_a_(trunkHeight, i);
			
			for (int k = -j; k <= j; ++k) {
				for (int l = -j; l <= j; ++l) {
					blockpos$mutable.setAndOffset(topPosition, k, i, l);
					if (!isLogsAt(reader, blockpos$mutable) || !config.ignoreVines && isVinesAt(reader, blockpos$mutable)) {
						return i - 2;
					}
				}
			}
		}
		
		return trunkHeight;
	}
	
	@Override
	public final boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BaseTreeFeatureConfig config) {
		Set<BlockPos> set = Sets.newHashSet();
		Set<BlockPos> set1 = Sets.newHashSet();
		Set<BlockPos> set2 = Sets.newHashSet();
		MutableBoundingBox mutableboundingbox = MutableBoundingBox.getNewBoundingBox();
		boolean flag = this.place(reader, rand, pos, set, set1, mutableboundingbox, config);
		if (mutableboundingbox.minX <= mutableboundingbox.maxX && flag && !set.isEmpty()) {
			if (!config.decorators.isEmpty()) {
				List<BlockPos> list = Lists.newArrayList(set);
				List<BlockPos> list1 = Lists.newArrayList(set1);
				list.sort(Comparator.comparingInt(Vector3i::getY));
				list1.sort(Comparator.comparingInt(Vector3i::getY));
				config.decorators.forEach((decorator) -> {
					decorator.func_225576_a_(reader, rand, list, list1, set2, mutableboundingbox);
				});
			}
			
			VoxelShapePart voxelshapepart = this.getFoliageGrowthArea(reader, mutableboundingbox, set, set2);
			Template.updatePostProcessing(reader, 3, voxelshapepart, mutableboundingbox.minX, mutableboundingbox.minY, mutableboundingbox.minZ);
			return true;
		} else {
			return false;
		}
	}
	
	private VoxelShapePart getFoliageGrowthArea(IWorld world, MutableBoundingBox boundingBox, Set<BlockPos> logPositions, Set<BlockPos> foliagePositions) {
		List<Set<BlockPos>> list = Lists.newArrayList();
		VoxelShapePart voxelshapepart = new BitSetVoxelShapePart(boundingBox.getXSize(), boundingBox.getYSize(), boundingBox.getZSize());
		int i = 6;
		
		for (int j = 0; j < 6; ++j) {
			list.add(Sets.newHashSet());
		}
		
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
		
		for (BlockPos blockpos : Lists.newArrayList(foliagePositions)) {
			if (boundingBox.isVecInside(blockpos)) {
				voxelshapepart.setFilled(blockpos.getX() - boundingBox.minX, blockpos.getY() - boundingBox.minY, blockpos.getZ() - boundingBox.minZ, true, true);
			}
		}
		
		for (BlockPos blockpos1 : Lists.newArrayList(logPositions)) {
			if (boundingBox.isVecInside(blockpos1)) {
				voxelshapepart.setFilled(blockpos1.getX() - boundingBox.minX, blockpos1.getY() - boundingBox.minY, blockpos1.getZ() - boundingBox.minZ, true, true);
			}
			
			for (Direction direction : Direction.values()) {
				blockpos$mutable.setAndMove(blockpos1, direction);
				if (!logPositions.contains(blockpos$mutable)) {
					BlockState blockstate = world.getBlockState(blockpos$mutable);
					if (blockstate.hasProperty(BlockStateProperties.DISTANCE_1_7)) {
						list.get(0).add(blockpos$mutable.toImmutable());
						setBlockStateWithoutUpdate(world, blockpos$mutable, blockstate.with(BlockStateProperties.DISTANCE_1_7, Integer.valueOf(1)));
						if (boundingBox.isVecInside(blockpos$mutable)) {
							voxelshapepart.setFilled(blockpos$mutable.getX() - boundingBox.minX, blockpos$mutable.getY() - boundingBox.minY, blockpos$mutable.getZ() - boundingBox.minZ, true, true);
						}
					}
				}
			}
		}
		
		for (int l = 1; l < 6; ++l) {
			Set<BlockPos> set = list.get(l - 1);
			Set<BlockPos> set1 = list.get(l);
			
			for (BlockPos blockpos2 : set) {
				if (boundingBox.isVecInside(blockpos2)) {
					voxelshapepart.setFilled(blockpos2.getX() - boundingBox.minX, blockpos2.getY() - boundingBox.minY, blockpos2.getZ() - boundingBox.minZ, true, true);
				}
				
				for (Direction direction1 : Direction.values()) {
					blockpos$mutable.setAndMove(blockpos2, direction1);
					if (!set.contains(blockpos$mutable) && !set1.contains(blockpos$mutable)) {
						BlockState blockstate1 = world.getBlockState(blockpos$mutable);
						if (blockstate1.hasProperty(BlockStateProperties.DISTANCE_1_7)) {
							int k = blockstate1.get(BlockStateProperties.DISTANCE_1_7);
							if (k > l + 1) {
								BlockState blockstate2 = blockstate1.with(BlockStateProperties.DISTANCE_1_7, Integer.valueOf(l + 1));
								setBlockStateWithoutUpdate(world, blockpos$mutable, blockstate2);
								if (boundingBox.isVecInside(blockpos$mutable)) {
									voxelshapepart.setFilled(blockpos$mutable.getX() - boundingBox.minX, blockpos$mutable.getY() - boundingBox.minY, blockpos$mutable.getZ() - boundingBox.minZ, true, true);
								}
								
								set1.add(blockpos$mutable.toImmutable());
							}
						}
					}
				}
			}
		}
		
		return voxelshapepart;
	}
}
