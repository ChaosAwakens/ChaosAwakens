package io.github.chaosawakens.common.worldgen.feature;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.common.worldgen.feature.configurations.RandomFeatureKeyConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

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
        BlockPos pos = context.origin();

        for (WeightedPlacedFeatureKey key : config.features) {
            if (random.nextFloat() < key.chance) {
                return key.place(level, gen, random, pos);
            }
        }

        return level.holderLookup(Registries.PLACED_FEATURE).getOrThrow(config.defaultFeature.get()).value().place(level, gen, random, pos);
    }
}
