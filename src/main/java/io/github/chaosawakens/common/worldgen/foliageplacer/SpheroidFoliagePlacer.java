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
public class SpheroidFoliagePlacer extends FoliagePlacer {
	public static final Codec<SpheroidFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> {
		return foliagePlacerParts(instance)
				.and(FeatureSpread.codec(0, 16, 8).fieldOf("height").forGetter((eliptic) -> eliptic.height))
				.apply(instance, SpheroidFoliagePlacer::new);
	});
	
	private final FeatureSpread height;
	private int sampledHeight;
	private int sampledOffset;
	
	public SpheroidFoliagePlacer(FeatureSpread radius, FeatureSpread offset, FeatureSpread height) {
		super(radius, offset);
		this.height = height;
	}

	@Override
	protected FoliagePlacerType<?> type() {
		return CAFoliagePlacerTypes.SPHEROID_FOLIAGE_PLACER.get();
	}
	
	@Override
	protected void createFoliage(IWorldGenerationReader reader, Random rand,
			BaseTreeFeatureConfig config, int maxFreeHeight, Foliage foliage, int foliageHeight, int radius,
			Set<BlockPos> posSet, int offset, MutableBoundingBox bBox) {
		int height = -foliageHeight;
		this.sampledOffset = this.offset.sample(rand);
		for(int i = 1; i > height; i--)
			this.placeLeavesRow(reader, rand, config, foliage.foliagePos(), radius + foliage.radiusOffset(), posSet, i, foliage.doubleTrunk(), bBox);
	}
	
	public int height() {
		return this.sampledHeight;
	}
	
	public int offset() {
		return this.sampledOffset;
	}
	
	@Override
	public int foliageHeight(Random rand, int height, BaseTreeFeatureConfig config) {
		this.sampledHeight = this.height.sample(rand);
		return sampledHeight;
	}

	@Override
	protected boolean shouldSkipLocation(Random rand, int x, int y, int z, int radius, boolean doubleTrunk) {
		int absX = Math.abs(x), absZ = Math.abs(z);
		double offset = this.offset(),
				hMO = this.height() - offset,
				fRad = radius,
				a = (absX - (doubleTrunk && x > 0 ? 0.5 : 0.0))/fRad,
				b = (absZ - (doubleTrunk && z > 0 ? 0.5 : 0.0))/fRad,
				c = hMO+y >= 0 || offset == 0 ? (hMO+y)/hMO : (hMO+y)/(offset+1);
		if(a*a + b*b + c*c <= 1)
			return false;
		return true;
	}
}
