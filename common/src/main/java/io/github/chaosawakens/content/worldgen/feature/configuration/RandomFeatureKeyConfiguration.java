package io.github.chaosawakens.content.worldgen.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.content.worldgen.feature.WeightedPlacedFeatureKey;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public record RandomFeatureKeyConfiguration(List<WeightedPlacedFeatureKey> features,
                                            Supplier<ResourceKey<PlacedFeature>> defaultFeature) implements FeatureConfiguration {
    public static final Codec<RandomFeatureKeyConfiguration> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    WeightedPlacedFeatureKey.CODEC.listOf().fieldOf("features").forGetter(conf -> conf.features),
                    ResourceLocation.CODEC.xmap(resourceLocation -> (Supplier<ResourceKey<PlacedFeature>>) () -> ResourceKey.create(Registries.PLACED_FEATURE, resourceLocation), placedFeatureResourceKey -> placedFeatureResourceKey.get().location()).fieldOf("default").forGetter(conf -> conf.defaultFeature)
            ).apply(instance, RandomFeatureKeyConfiguration::new)
    );

    @Override
    public @NotNull Stream<ConfiguredFeature<?, ?>> getFeatures() {
        HolderGetter<PlacedFeature> getter = VanillaRegistries.createLookup().asGetterLookup().lookupOrThrow(Registries.PLACED_FEATURE);

        return Stream.concat(features.stream().flatMap(key -> getter.getOrThrow(key.feature().get()).value().getFeatures()), getter.getOrThrow(this.defaultFeature.get()).value().getFeatures());
    }
}