package io.github.chaosawakens.common.worldgen.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class CrystalCaveWorldCarver extends CaveWorldCarver {

	public CrystalCaveWorldCarver(Codec<ProbabilityConfig> codec, int height) {
		super(codec, height);
		this.replaceableBlocks = ImmutableSet.of(CABlocks.KYANITE.get(), CABlocks.BUDDING_CATS_EYE.get(), CABlocks.CATS_EYE_CLUSTER.get(), CABlocks.BUDDING_PINK_TOURMALINE.get(), CABlocks.PINK_TOURMALINE_CLUSTER.get());
	}

}
