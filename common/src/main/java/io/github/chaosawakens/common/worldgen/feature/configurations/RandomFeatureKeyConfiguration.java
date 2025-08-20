package io.github.chaosawakens.common.worldgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.common.worldgen.feature.WeightedPlacedFeatureKey;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class RandomFeatureKeyConfiguration implements FeatureConfiguration {
    public static final Codec<RandomFeatureKeyConfiguration> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    WeightedPlacedFeatureKey.CODEC.listOf().fieldOf("features").forGetter(conf -> conf.features),
                    ResourceLocation.CODEC.xmap(resourceLocation -> (Supplier<ResourceKey<PlacedFeature>>)() -> ResourceKey.create(Registries.PLACED_FEATURE, resourceLocation), placedFeatureResourceKey -> placedFeatureResourceKey.get().location()).fieldOf("default").forGetter(conf -> conf.defaultFeature)
            ).apply(instance, RandomFeatureKeyConfiguration::new)
    );
    public final List<WeightedPlacedFeatureKey> features;
    public final Supplier<ResourceKey<PlacedFeature>> defaultFeature;

    public RandomFeatureKeyConfiguration(List<WeightedPlacedFeatureKey> $$0, Supplier<ResourceKey<PlacedFeature>> $$1) {
        this.features = $$0;
        this.defaultFeature = $$1;
    }

    @Override
    public Stream<ConfiguredFeature<?, ?>> getFeatures() {
        HolderGetter<PlacedFeature> getter = VanillaRegistries.createLookup().asGetterLookup().lookupOrThrow(Registries.PLACED_FEATURE);
        return Stream.concat(this.features.stream().flatMap(key -> getter.getOrThrow(key.feature.get()).value().getFeatures()), getter.getOrThrow(this.defaultFeature.get()).value().getFeatures());
    }
}
