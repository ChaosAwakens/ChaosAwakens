package io.github.chaosawakens.content.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.Supplier;

public record WeightedPlacedFeatureKey(Supplier<ResourceKey<PlacedFeature>> feature, float chance) {
    public static final Codec<WeightedPlacedFeatureKey> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            ResourceLocation.CODEC.xmap(placedFeatureKey -> (Supplier<ResourceKey<PlacedFeature>>) () -> ResourceKey.create(Registries.PLACED_FEATURE, placedFeatureKey), placedFeatureResourceKey -> placedFeatureResourceKey.get().location()).fieldOf("feature").forGetter(WeightedPlacedFeatureKey::feature),
                            Codec.floatRange(0.0F, 1.0F).fieldOf("chance").forGetter(WeightedPlacedFeatureKey::chance)
                    )
                    .apply(instance, WeightedPlacedFeatureKey::new)
    );

    public boolean place(WorldGenLevel level, ChunkGenerator gen, RandomSource random, BlockPos pos) {
        return level.holderLookup(Registries.PLACED_FEATURE).getOrThrow(this.feature.get()).value().place(level, gen, random, pos);
    }
}