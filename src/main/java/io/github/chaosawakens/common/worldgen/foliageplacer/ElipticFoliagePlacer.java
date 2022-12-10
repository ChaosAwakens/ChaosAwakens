package io.github.chaosawakens.common.worldgen.foliageplacer;

import java.util.Random;
import java.util.Set;

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

/**
 */
public class ElipticFoliagePlacer extends FoliagePlacer {
	public static final Codec<ElipticFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> {
		return foliagePlacerParts(instance).and(FeatureSpread.codec(0, 16, 8)
				.fieldOf("height").forGetter((eliptic) -> eliptic.height)).apply(instance, ElipticFoliagePlacer::new);
	});
	
	private final FeatureSpread height;
	
	public ElipticFoliagePlacer(FeatureSpread radius, FeatureSpread offset, FeatureSpread height) {
		super(radius, offset);
		this.height = height;
	}

	@Override
	protected FoliagePlacerType<?> type() {
		return CAFoliagePlacerTypes.GINKGO_FOLIAGE_TYPE.get();
	}
	
	@Override
	protected void createFoliage(IWorldGenerationReader reader, Random rand,
			BaseTreeFeatureConfig config, int maxFreeHeight, Foliage foliage, int foliageHeight, int radius,
			Set<BlockPos> posSet, int offset, MutableBoundingBox bBox) {
		int height = -this.foliageHeight(rand);
		for(int i = 1; i > height; i--)
			this.placeLeavesRow(reader, rand, config, foliage.foliagePos(), radius, posSet, i, foliage.doubleTrunk(), bBox);
		offset++;
	}
	
	public int foliageHeight(Random rand) {
		return this.height.sample(rand);
	}
	
	@Override
	public int foliageHeight(Random rand, int height, BaseTreeFeatureConfig config) {
		return this.height.sample(rand);
	}

	@Override
	protected boolean shouldSkipLocation(Random rand, int x, int y, int z, int radius, boolean doubleTrunk) {
		float height = this.foliageHeight(rand)-1, fRad = radius, a = x/fRad, b = z/fRad, c = (y+height)/height;
		if(a*a + b*b + c*c <= 1)
			return false;
		return true;
	}
}
