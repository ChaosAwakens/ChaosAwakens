package io.github.chaosawakens.common.worldgen.config.mining_paradise.biome.surface.level_1;

import io.github.chaosawakens.common.registry.CAFeatures;
import io.github.chaosawakens.common.worldgen.config.base.BiomeConfig;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.Nullable;

public class GinkgoForestBiomeConfig implements BiomeConfig {
    public static final BiomeSpecialEffects EFFECTS = new BiomeSpecialEffects.Builder()
            .fogColor(2302743)
            .waterColor(4159204)
            .waterFogColor(329011)
            .skyColor(8030207)
            .build();

    public GinkgoForestBiomeConfig() {}

    @Override
    public boolean hasPrecipitation() {
        return true;
    }

    @Override
    public float getTemperature() {
        return 0.6F;
    }

    @Override
    public float getDownfall() {
        return 0.4F;
    }

    @Override
    public @Nullable BiomeSpecialEffects getSpecialEffects() {
        return EFFECTS;
    }

    @Override
    public @Nullable MobSpawnSettings getMobSpawnSettings() {
        return null;
    }

    @Override
    public @Nullable BiomeGenerationSettings getGenerationSettings(BootstapContext<Biome> regCtx) {
        HolderGetter<PlacedFeature> placedFeatureGetter = regCtx.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> configuredWorldCarverGetter = regCtx.lookup(Registries.CONFIGURED_CARVER);

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(placedFeatureGetter, configuredWorldCarverGetter);

        // Vegetation
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatureGetter.getOrThrow(CAFeatures.CAPlacedFeatures.DENSE_GRASS_PATCH.get()));
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatureGetter.getOrThrow(CAFeatures.CAPlacedFeatures.ALSTROEMERIAT_PATCH.get()));

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatureGetter.getOrThrow(CAFeatures.CAPlacedFeatures.TREES_GINKGO.get()));

        return biomeBuilder.build();
    }

    @Override
    public @Nullable Biome.TemperatureModifier getTemperatureModifier() {
        return null;
    }
}
