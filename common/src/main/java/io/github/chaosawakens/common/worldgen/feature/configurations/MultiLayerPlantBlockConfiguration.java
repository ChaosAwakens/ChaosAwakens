package io.github.chaosawakens.common.worldgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record MultiLayerPlantBlockConfiguration(BlockStateProvider provider, int maxLevel) implements FeatureConfiguration {
    public static Codec<MultiLayerPlantBlockConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("state").forGetter(conf -> conf.provider()),
            Codec.intRange(1, 10).fieldOf("max_level").forGetter(conf -> conf.maxLevel()))
            .apply(instance, MultiLayerPlantBlockConfiguration::new));
}
