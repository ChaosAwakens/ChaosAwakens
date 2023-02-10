package io.github.chaosawakens.common.worldgen.foliageplacer;

import java.util.Random;
import java.util.Set;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CAFoliagePlacerTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;

public class CubicSkipFoliagePlacer extends FoliagePlacer {
	
	public static final Codec<CubicSkipFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(FeatureSpread.codec(0, 8, 8).fieldOf("width").forGetter((placer) -> placer.radius),
				FeatureSpread.codec(0, 8, 8).fieldOf("height").forGetter((placer) -> placer.offset),
				FeatureSpread.codec(0, 16, 8).fieldOf("skip").forGetter((eliptic) -> eliptic.skip))
				.apply(instance, CubicSkipFoliagePlacer::new);
	});
	
	protected final FeatureSpread skip;
	private boolean isSkipping = false;
	
	public CubicSkipFoliagePlacer(FeatureSpread blobWidthRange, FeatureSpread blobHeightRange, FeatureSpread skipBlobRange) {
		super(blobWidthRange, blobHeightRange);
		this.skip = skipBlobRange;
	}

	@Override
	protected FoliagePlacerType<?> type() {
		return CAFoliagePlacerTypes.CUBIC_SKIP_FOLIAGE_PLACER.get();
	}

	@Override
	protected void createFoliage(IWorldGenerationReader reader, Random rand,
			BaseTreeFeatureConfig config, int maxFreeHeight, Foliage foliage, int foliageHeight, int radius,
			Set<BlockPos> set, int offset, MutableBoundingBox bBox) {
		int startY = foliageHeight - rand.nextInt(3) - 1, bWidth = this.radius.sample(rand), bHeight = this.offset.sample(rand), skip = 0;
		for(int i = 0; i > -startY; i--) {
			ChaosAwakens.LOGGER.debug("sk "+ skip +" bw "+ bWidth + " bh " + bHeight);
			if(isSkipping) {
				if(skip > 1) {
					skip--;
				} else {
					bHeight = this.offset.sample(rand);
					this.isSkipping = false;
				}
			} else {
				if(bHeight > 0) {
					this.placeLeavesRow(reader, rand, config, foliage.foliagePos(), bWidth, set, i, foliage.doubleTrunk(), bBox);
					bHeight--;
				} else {
					skip = this.skip.sample(rand);
					this.isSkipping = true;
				}
			}
		}
	}

	@Override
	public int foliageHeight(Random pRandom, int pHeight, BaseTreeFeatureConfig pConfig) {
		return pHeight;
	}

	@Override
	protected boolean shouldSkipLocation(Random rand, int x, int y, int z, int radius, boolean doubleTrunk) {
		return false;
	}

}
