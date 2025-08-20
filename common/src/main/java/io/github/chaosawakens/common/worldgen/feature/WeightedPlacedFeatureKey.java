package io.github.chaosawakens.common.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.Supplier;

public class WeightedPlacedFeatureKey {
    public static final Codec<WeightedPlacedFeatureKey> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            ResourceLocation.CODEC.xmap(resourceLocation -> (Supplier<ResourceKey<PlacedFeature>>)() -> ResourceKey.create(Registries.PLACED_FEATURE, resourceLocation), placedFeatureResourceKey -> placedFeatureResourceKey.get().location()).fieldOf("feature").forGetter(placed -> placed.feature),
                            Codec.floatRange(0.0F, 1.0F).fieldOf("chance").forGetter($$0x -> $$0x.chance)
                    )
                    .apply(instance, WeightedPlacedFeatureKey::new)
    );
    public final Supplier<ResourceKey<PlacedFeature>> feature;
    public final float chance;

    public WeightedPlacedFeatureKey(Supplier<ResourceKey<PlacedFeature>> feature, float chance) {
        this.feature = feature;
        this.chance = chance;
    }

    public boolean place(WorldGenLevel level, ChunkGenerator gen, RandomSource random, BlockPos pos) {
        return level.holderLookup(Registries.PLACED_FEATURE).getOrThrow(this.feature.get()).value().place(level, gen, random, pos);
    }
}
