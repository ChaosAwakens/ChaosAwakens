/**
 * 
 */
package io.github.chaosawakens.common.worldgen.trunkplacer;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAFeatures.CATrunkPlacerType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer.Foliage;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

/**
 * @author invalid2
 *
 */
public class CrystalStraightTrunkPlacer extends AbstractTrunkPlacer {
	public static final Codec<CrystalStraightTrunkPlacer> CODEC = RecordCodecBuilder.create((builderInstance) -> {
		return getAbstractTrunkCodec(builderInstance).apply(builderInstance, CrystalStraightTrunkPlacer::new);
	});
	
	public CrystalStraightTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
		super(baseHeight, heightRandA, heightRandB);
	}
	
	protected TrunkPlacerType<?> getPlacerType() {
		return CATrunkPlacerType.CRYSTAL_STRAIGHT_TRUNK_PLACER;
	}
	
	public List<FoliagePlacer.Foliage> getFoliages(IWorldGenerationReader reader, Random rand, int treeHeight, BlockPos p_230382_4_, Set<BlockPos> p_230382_5_, MutableBoundingBox p_230382_6_, BaseTreeFeatureConfig p_230382_7_) {
		func_236909_a(reader, p_230382_4_.down());
		//ChaosAwakens.LOGGER.debug("state");
		for (int i = 0; i < treeHeight; ++i) {
			func_236911_a_(reader, rand, p_230382_4_.up(i), p_230382_5_, p_230382_6_, p_230382_7_);
		}
		
		return ImmutableList.of(new FoliagePlacer.Foliage(p_230382_4_.up(treeHeight), 0, false));
	}
	
	private static boolean func_236912_a(IWorldGenerationBaseReader reader, BlockPos pos) {
		return reader.hasBlockState(pos, (state) -> {
			ChaosAwakens.LOGGER.debug("pos: "+state+" "+state.matchesBlock(CABlocks.CRYSTAL_GRASS_BLOCK.get()));
			return state.matchesBlock(CABlocks.CRYSTAL_GRASS_BLOCK.get());
		});
	}
	
	protected static final void func_236909_a(IWorldGenerationReader reader, BlockPos pos) {
		if (func_236912_a(reader, pos)) {
			TreeFeature.setBlockStateWithoutUpdate(reader, pos, CABlocks.CRYSTAL_GRASS_BLOCK.get().getDefaultState());
		}
		
	}
}
