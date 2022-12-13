package io.github.chaosawakens.common.worldgen.trunkplacer;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CATrunkPlacerTypes;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

public class GiantGinkgoTrunkPlacer extends AbstractTrunkPlacer {
	public static final Codec<GiantGinkgoTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> {
		return trunkPlacerParts(instance).apply(instance, GiantGinkgoTrunkPlacer::new);
	});
	
	public GiantGinkgoTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
		super(baseHeight, heightRandA, heightRandB);
	}
	
	@Override
	protected TrunkPlacerType<?> type() {
		return CATrunkPlacerTypes.GIANT_GINKGO_TRUNK_PLACER;
	}
	
	@Override
	public List<FoliagePlacer.Foliage> placeTrunk(IWorldGenerationReader reader, Random rand, int p_230382_3_, BlockPos pos, Set<BlockPos> set, MutableBoundingBox bb, BaseTreeFeatureConfig config) {
		BlockPos below = pos.below();
		denseDirtAt(reader, below);
		denseDirtAt(reader, below.east());
		denseDirtAt(reader, below.south());
		denseDirtAt(reader, below.south().east());
		BlockPos.Mutable mutablePos = new BlockPos.Mutable();
			
		for(int i = 0; i < p_230382_3_; ++i) {
			placeLogIfFreeWithOffset(reader, rand, mutablePos, set, bb, config, pos, 0, i, 0);
			if (i < p_230382_3_ - 1) {
				placeLogIfFreeWithOffset(reader, rand, mutablePos, set, bb, config, pos, 1, i, 0);
				placeLogIfFreeWithOffset(reader, rand, mutablePos, set, bb, config, pos, 1, i, 1);
				placeLogIfFreeWithOffset(reader, rand, mutablePos, set, bb, config, pos, 0, i, 1);
			}
		}
		return ImmutableList.of(new FoliagePlacer.Foliage(pos.above(p_230382_3_), 0, true));
	}
	
	private static void placeLogIfFreeWithOffset(IWorldGenerationReader reader, Random rand, BlockPos.Mutable mutable, Set<BlockPos> set, MutableBoundingBox bb, BaseTreeFeatureConfig config, BlockPos pos, int x, int y, int z) {
		mutable.setWithOffset(pos, x, y, z);
		placeLogIfFree(reader, rand, mutable, set, bb, config);
	}
	
	protected static void denseDirtAt(IWorldGenerationReader reader, BlockPos pos) {
		TreeFeature.setBlockKnownShape(reader, pos, CABlocks.DENSE_DIRT.get().defaultBlockState());
	}
}
