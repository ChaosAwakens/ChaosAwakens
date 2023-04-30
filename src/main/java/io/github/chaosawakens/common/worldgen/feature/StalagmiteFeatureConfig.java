package io.github.chaosawakens.common.worldgen.feature;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class StalagmiteFeatureConfig implements IFeatureConfig {

	public static final Codec<StalagmiteFeatureConfig> CODEC = RecordCodecBuilder.create(builder -> builder
			.group(BlockState.CODEC.fieldOf("state").forGetter(config -> config.state),
					Codec.intRange(1, 16).fieldOf("base_radius").forGetter(config -> config.baseRadius),
					Codec.floatRange(0.1f, 10).fieldOf("base_steepness").forGetter(config -> config.baseSteepness),
					Codec.floatRange(0, 10).fieldOf("variation").forGetter(config -> config.variation),
					ITag.codec(() -> TagCollectionManager.getInstance().getBlocks()).optionalFieldOf("ores_tag")
						.forGetter(config -> config.oresTag),
					Codec.floatRange(0, 1).fieldOf("ore_chance").forGetter(config -> config.oreChance)
			).apply(builder, StalagmiteFeatureConfig::new));

	public final BlockState state;
	public final int baseRadius;
	public final float baseSteepness;
	public final float variation;	
	public final Optional<ITag<Block>> oresTag;
	public final float oreChance;
	
	public StalagmiteFeatureConfig(BlockState state, int baseRadius, float baseSteepness, float variation,
			Optional<ITag<Block>> oresTag, float oreChance) {
		this.state = state;
		this.baseRadius = baseRadius;
		this.baseSteepness = baseSteepness;
		this.variation = variation;
		this.oresTag = oresTag;
		this.oreChance = oreChance;
	}
}
