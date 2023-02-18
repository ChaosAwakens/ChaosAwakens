package io.github.chaosawakens.common.worldgen.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.world.gen.carver.CanyonWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class CrystalCanyonWorldCarver extends CanyonWorldCarver {

	public CrystalCanyonWorldCarver(Codec<ProbabilityConfig> config) {
		super(config);
		this.replaceableBlocks = ImmutableSet.of(CABlocks.KYANITE.get(), CABlocks.BUDDING_CATS_EYE.get(), CABlocks.CATS_EYE_CLUSTER.get(), CABlocks.BUDDING_PINK_TOURMALINE.get(), CABlocks.PINK_TOURMALINE_CLUSTER.get());
	}

}
