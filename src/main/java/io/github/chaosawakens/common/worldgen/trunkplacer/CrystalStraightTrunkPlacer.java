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

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

/**
 * @author invalid2
 *
 */
public class CrystalStraightTrunkPlacer extends AbstractTrunkPlacer {
	public static final Codec<CrystalStraightTrunkPlacer> CODEC = RecordCodecBuilder.create((builderInstance) -> {
		return trunkPlacerParts(builderInstance).apply(builderInstance, CrystalStraightTrunkPlacer::new);
	});
	
	public CrystalStraightTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
		super(baseHeight, heightRandA, heightRandB);
	}
	
	@Override
	protected TrunkPlacerType<?> type() {
		return null; //CATrunkPlacerType.CRYSTAL_STRAIGHT_TRUNK_PLACER;
	}
	
	@Override
	public List<FoliagePlacer.Foliage> placeTrunk(IWorldGenerationReader reader, Random rand, int treeHeight, BlockPos p_230382_4_, Set<BlockPos> p_230382_5_, MutableBoundingBox p_230382_6_, BaseTreeFeatureConfig p_230382_7_) {
		for (int i = 0; i < treeHeight; ++i) {
			placeLog(reader, rand, p_230382_4_.above(i), p_230382_5_, p_230382_6_, p_230382_7_);
		}
		
		return ImmutableList.of(new FoliagePlacer.Foliage(p_230382_4_.above(treeHeight), 0, false));
	}
}
