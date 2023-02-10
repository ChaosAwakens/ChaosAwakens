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

public class ThinGiantTrunkPlacer extends DirtlessGiantTrunkPlacer {
	
	public static final Codec<ThinGiantTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> {
		return trunkPlacerParts(instance)
				.and(BlockState.CODEC.fieldOf("base").forGetter((placer) -> placer.base))
				.apply(instance, ThinGiantTrunkPlacer::new);
	});
	
	public ThinGiantTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, BlockState base) {
		super(baseHeight, heightRandA, heightRandB, base);
	}
	
	@Override
	protected TrunkPlacerType<?> type() {
		return CATrunkPlacerTypes.THIN_GIANT_TRUNK_PLACER;
	}
	
	@Override
	public List<FoliagePlacer.Foliage> placeTrunk(IWorldGenerationReader reader, Random rand, int height, BlockPos pos, Set<BlockPos> set, MutableBoundingBox bb, BaseTreeFeatureConfig config) {
		List<FoliagePlacer.Foliage> list = Lists.newArrayList();
		list.addAll(super.placeTrunk(reader, rand, height, pos, set, bb, config));
		BlockPos.Mutable mutablePos = new BlockPos.Mutable();
		for(int i = 1; i < 4; i++) {
			list.add(placeBranch(reader, rand, mutablePos, height*i/4, pos, set, bb, config));
		}
		return list;
	}
	
	private FoliagePlacer.Foliage placeBranch(IWorldGenerationReader reader, Random rand, BlockPos.Mutable mutablePos, int y, BlockPos pos, Set<BlockPos> set, MutableBoundingBox bb, BaseTreeFeatureConfig config) {
		int offX = rand.nextBoolean()? 1 : -1, offZ = rand.nextBoolean()? 1 : -1;
		placeLogIfFreeWithOffset(reader, rand, mutablePos, set, bb, config, pos, offX*2, y, offZ*2);
		placeLogIfFreeWithOffset(reader, rand, mutablePos, set, bb, config, pos, offX*2 + 1, y, offZ*2);
		placeLogIfFreeWithOffset(reader, rand, mutablePos, set, bb, config, pos, offX*2 + 1, y, offZ*2 + 1);
		placeLogIfFreeWithOffset(reader, rand, mutablePos, set, bb, config, pos, offX*2, y, offZ*2 + 1);
		
		return new FoliagePlacer.Foliage(mutablePos.offset(0, 1, -1), -2, true);
	}
}
