package io.github.chaosawakens.content.worldgen.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;

public record StalagmiteConfiguration(BlockStateProvider block, int minRadius, int maxRadius, float steepness,
                                      float variation) implements FeatureConfiguration {
    public static final Codec<StalagmiteConfiguration> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BlockStateProvider.CODEC.fieldOf("block").forGetter(StalagmiteConfiguration::block),
                    Codec.INT.fieldOf("min_radius").orElse(2).forGetter(StalagmiteConfiguration::minRadius),
                    Codec.INT.fieldOf("max_radius").orElse(4).forGetter(StalagmiteConfiguration::maxRadius),
                    Codec.FLOAT.fieldOf("steepness").orElse(1.5f).forGetter(StalagmiteConfiguration::steepness),
                    Codec.FLOAT.fieldOf("variation").orElse(0.5f).forGetter(StalagmiteConfiguration::variation)
            ).apply(instance, StalagmiteConfiguration::new)
    );

    public StalagmiteConfiguration(Block block, int minRadius, int maxRadius, float steepness, float variation) {
        this(SimpleStateProvider.simple(block.defaultBlockState()), minRadius, maxRadius, steepness, variation);
    }
}