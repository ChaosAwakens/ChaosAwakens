package io.github.chaosawakens.common.worldgen.foliageplacer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.common.registry.CAFoliagePlacerTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class ConiferousFoliagePlacer extends FoliagePlacer {
	public static final Codec<ConiferousFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> foliagePlacerParts(instance).apply(instance, ConiferousFoliagePlacer::new));
	
	public ConiferousFoliagePlacer(FeatureSpread radius, FeatureSpread offset) {
		super(radius, offset);
	}

	@Override
	protected FoliagePlacerType<?> type() {
		return CAFoliagePlacerTypes.CONIFEROUS_FOLIAGE_PLACER.get();
	}

	@Override
	protected void createFoliage(IWorldGenerationReader reader, Random rand, BaseTreeFeatureConfig config, int maxFreeHeight, Foliage foliage, int foliageHeight, int radius, Set<BlockPos> set, int offset, MutableBoundingBox bBox) {
		int curFoliageHeight = foliageHeight(rand, foliageHeight, config);
		
		for(int i = 1; i > -curFoliageHeight; i--) this.placeLeavesRow(reader, rand, config, foliage.foliagePos(), radius, set, i, foliage.doubleTrunk(), bBox);
	}

	@Override
	public int foliageHeight(Random pRandom, int pHeight, BaseTreeFeatureConfig pConfig) {
		return this.offset.sample(pRandom);
	}

	@Override
	protected boolean shouldSkipLocation(Random pRandom, int x, int y, int z, int radius, boolean doubleTrunk) {
		if (y > 0) return x != 0 || z != 0;
		
		int remainder = -(y % 3), radiusMinus1 = radius - 1, abX = Math.abs(x), abZ = Math.abs(z);
		
		switch(remainder) {
		case 0:
			if (abX + abZ <= radiusMinus1) return false;
			break;
		case 1:
			if (abX <= radiusMinus1 && abZ <= radiusMinus1) return false;
			break;
		case 2:
			if ((abX != radius && abZ != 0) || (abX != 0 && abZ != radius)) return false;
			break;
		}
		return true;
	}

}
