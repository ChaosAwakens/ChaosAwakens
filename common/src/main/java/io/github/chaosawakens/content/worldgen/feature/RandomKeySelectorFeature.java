package io.github.chaosawakens.content.worldgen.feature;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.content.worldgen.feature.configuration.RandomFeatureKeyConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class RandomKeySelectorFeature extends Feature<RandomFeatureKeyConfiguration> {

    public RandomKeySelectorFeature(Codec<RandomFeatureKeyConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<RandomFeatureKeyConfiguration> context) {
        RandomFeatureKeyConfiguration config = context.config();
        RandomSource random = context.random();
        WorldGenLevel level = context.level();
        ChunkGenerator gen = context.chunkGenerator();
        BlockPos originPos = context.origin();

        for (WeightedPlacedFeatureKey key : config.features()) {
            if (random.nextFloat() < key.chance()) {
                return key.place(level, gen, random, originPos);
            }
        }

        return level.holderLookup(Registries.PLACED_FEATURE).getOrThrow(config.defaultFeature().get()).value().place(level, gen, random, originPos);
    }
}