package io.github.chaosawakens.common.worldgen.trunkplacer;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.github.chaosawakens.common.registry.CATrunkPlacerTypes;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

public class GiantConiferTrunkPlacer extends DirtlessGiantTrunkPlacer {
	public static final Codec<GiantConiferTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) ->
		trunkPlacerParts(instance).and(BlockState.CODEC.fieldOf("base").forGetter((placer) -> placer.base))
			.apply(instance, GiantConiferTrunkPlacer::new));
	
	public GiantConiferTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, BlockState base) {
		super(baseHeight, heightRandA, heightRandB, base);
	}
	
	@Override
	protected TrunkPlacerType<?> type() {
		return CATrunkPlacerTypes.GIANT_CONIFER_TRUNK_PLACER;
	}
	
	@Override
	public List<FoliagePlacer.Foliage> placeTrunk(IWorldGenerationReader reader, Random rand, int height, BlockPos pos, Set<BlockPos> set, MutableBoundingBox bb, BaseTreeFeatureConfig config) {
		List<FoliagePlacer.Foliage> list = Lists.newArrayList();
		list.addAll(super.placeTrunk(reader, rand, height, pos, set, bb, config));
		BlockPos.Mutable mutablePos = new BlockPos.Mutable();
		for(int i = 1; i >= -1; i--)
			for(int j = 1; j >= -1; j--) {
				if(i == 0 && j == 0)continue;
				if(i != 0 && j != 0)list.add(placeBranch(reader, rand, mutablePos, i, height/3, j, 8, pos, set, bb, config));
				if(i == 0 || j == 0)list.add(placeBranch(reader, rand, mutablePos, i, height*2/3, j, 8, pos, set, bb, config));
			}
		return list;
	}
	
	private FoliagePlacer.Foliage placeBranch(IWorldGenerationReader reader, Random rand, BlockPos.Mutable mutablePos, int offX, int y, int offZ, int baseLength, BlockPos pos, Set<BlockPos> set, MutableBoundingBox bb, BaseTreeFeatureConfig config) {
		int length = rand.nextInt(2) - 2 + baseLength;
		y = rand.nextInt(5) - 2 + y;
		for(int i = 0; i < length; i++) {
			placeLogIfFreeWithOffset(reader, rand, mutablePos, set, bb, config, pos, offX * i, y - 1 + (i/2), offZ * i);
			placeLogIfFreeWithOffset(reader, rand, mutablePos, set, bb, config, pos, offX * i + 1, y - 1 + (i/2), offZ * i);
			placeLogIfFreeWithOffset(reader, rand, mutablePos, set, bb, config, pos, offX * i + 1, y - 1 + (i/2), offZ * i + 1);
			placeLogIfFreeWithOffset(reader, rand, mutablePos, set, bb, config, pos, offX * i, y - 1 + (i/2), offZ * i + 1);
		}
		return new FoliagePlacer.Foliage(mutablePos.offset(0, 1, -1), -2, true);
	}
}
