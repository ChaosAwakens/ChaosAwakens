package io.github.chaosawakens.content.worldgen.config.crystal_world.biome.crystalline_reef;

import io.github.chaosawakens.content.registry.CAFeatures;
import io.github.chaosawakens.content.worldgen.config.base.BiomeConfig;
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

public class CrystallineReefConfig implements BiomeConfig {
    public static final BiomeSpecialEffects EFFECTS = new BiomeSpecialEffects.Builder()
            .fogColor(6986985)
            .waterColor(6014659)
            .waterFogColor(13937093)
            .skyColor(14722514)
            .build();

    public CrystallineReefConfig() {
    }

    @Override
    public boolean hasPrecipitation() {
        return false;
    }

    @Override
    public float getTemperature() {
        return 0.8F;
    }

    @Override
    public float getDownfall() {
        return 0;
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
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatureGetter.getOrThrow(CAFeatures.CAPlacedFeatures.CRYSTAL_GRASS_PATCH.get()));
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatureGetter.getOrThrow(CAFeatures.CAPlacedFeatures.PURPLE_CRYSTAL_GROWTH_PATCH.get()));
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatureGetter.getOrThrow(CAFeatures.CAPlacedFeatures.GREEN_CRYSTAL_GROWTH_PATCH.get()));
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatureGetter.getOrThrow(CAFeatures.CAPlacedFeatures.PINK_CRYSTAL_GROWTH_PATCH.get()));
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatureGetter.getOrThrow(CAFeatures.CAPlacedFeatures.YELLOW_CRYSTAL_GROWTH_PATCH.get()));
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatureGetter.getOrThrow(CAFeatures.CAPlacedFeatures.RED_CRYSTAL_FLOWER_PATCH.get()));
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatureGetter.getOrThrow(CAFeatures.CAPlacedFeatures.BLUE_CRYSTAL_FLOWER_PATCH.get()));
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatureGetter.getOrThrow(CAFeatures.CAPlacedFeatures.PINK_CRYSTAL_FLOWER_PATCH.get()));



        // Ores

        // Terrain

        return biomeBuilder.build();
    }

    @Override
    public @Nullable Biome.TemperatureModifier getTemperatureModifier() {
        return null;
    }
}