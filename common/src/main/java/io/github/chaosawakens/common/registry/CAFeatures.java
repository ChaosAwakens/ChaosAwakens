package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableList;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.asm.annotations.RegistrarEntry;
import io.github.chaosawakens.api.platform.CAServices;
import io.github.chaosawakens.common.worldgen.feature.NBTTreeFeature;
import io.github.chaosawakens.common.worldgen.feature.configurations.NBTTreeConfiguration;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;
import java.util.function.Supplier;

public class CAFeatures {

    @RegistrarEntry
    public static class Features {
        private static final ObjectArrayList<Supplier<Feature<?>>> FEATURES = new ObjectArrayList<>();

        public static final Supplier<Feature<NBTTreeConfiguration>> NBT_TREE = registerFeature("nbt_tree", () -> new NBTTreeFeature(NBTTreeConfiguration.CODEC));

        private static <FC extends FeatureConfiguration, F extends Feature<FC>> Supplier<F> registerFeature(String id, Supplier<Feature<?>> featureSup) {
            Supplier<Feature<?extends FeatureConfiguration>> placedFeatureSup = CAServices.REGISTRAR.registerObject(CAConstants.prefix(id), featureSup, BuiltInRegistries.FEATURE);
            FEATURES.add(featureSup);
            return (Supplier<F>) placedFeatureSup;
        }

        public static ImmutableList<Supplier<Feature<?>>> getFeatures() {
            return ImmutableList.copyOf(FEATURES);
        }
    }

    @RegistrarEntry
    public static class CAConfiguredFeatures {
        private static final ObjectArrayList<Supplier<ResourceKey<ConfiguredFeature<?, ?>>>> CONFIGURED_FEATURES = new ObjectArrayList<>();

        // Bonemeal
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> SINGLE_DENSE_GRASS = registerConfiguredFeature("single_dense_grass", () -> new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.DENSE_GRASS.get()))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> SINGLE_CRYSTAL_GRASS = registerConfiguredFeature("single_crystal_grass", () -> new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.CRYSTAL_GRASS.get()))));

        // Mining Paradise
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DENSE_GRASS_PATCH = registerConfiguredFeature("dense_grass_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.DENSE_GRASS.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> TALL_DENSE_GRASS_PATCH = registerConfiguredFeature("tall_dense_grass_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.TALL_DENSE_GRASS.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> THORNY_SUN_PATCH = registerConfiguredFeature("thorny_sun_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(96, 12, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.THORNY_SUN.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> ALSTROEMERIAT_PATCH = registerConfiguredFeature("alstroemeriat_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(96, 12, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.ALSTROEMERIAT.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DENSE_BUSH_PATCH = registerConfiguredFeature("dense_bush_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(96, 12, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.DENSE_BUSH.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> TALL_DENSE_BUSH_PATCH = registerConfiguredFeature("tall_dense_bush_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(96, 12, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.TALL_DENSE_BUSH.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> SMALL_CARNIVOROUS_PLANT_PATCH = registerConfiguredFeature("small_carnivorous_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(96, 12, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.SMALL_CARNIVOROUS_PLANT.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> BIG_CARNIVOROUS_PLANT_PATCH = registerConfiguredFeature("big_carnivorous_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(96, 12, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.BIG_CARNIVOROUS_PLANT.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> BUSH_PATCH = registerConfiguredFeature("bush_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(96, 12, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.BUSH.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> TALL_BUSH_PATCH = registerConfiguredFeature("tall_bush_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(96, 12, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.TALL_BUSH.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> LARGE_MESOZOIC_BUSH_PATCH = registerConfiguredFeature("large_mesozoic_bush_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(96, 12, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.LARGE_MESOZOIC_BUSH.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DREDGESTONE_ROCK = registerConfiguredFeature("dredgestone_rock", () -> new ConfiguredFeature<>(Feature.FOREST_ROCK, new BlockStateConfiguration(CABlocks.DREDGESTONE.get().defaultBlockState())));

        private static Supplier<ResourceKey<ConfiguredFeature<?, ?>>> registerConfiguredFeature(ResourceLocation id, Supplier<ConfiguredFeature<?, ?>> actualPlacedFeatureSup) {
            Supplier<ResourceKey<ConfiguredFeature<?, ?>>> placedFeatureSup = CAServices.REGISTRAR.registerDatapackObject(id, b -> actualPlacedFeatureSup, Registries.CONFIGURED_FEATURE);
            CONFIGURED_FEATURES.add(placedFeatureSup);
            return placedFeatureSup;
        }

        private static Supplier<ResourceKey<ConfiguredFeature<?, ?>>> registerConfiguredFeature(String id, Supplier<ConfiguredFeature<?, ?>> actualPlacedFeatureSup) {
            return registerConfiguredFeature(CAConstants.prefix(id), actualPlacedFeatureSup);
        }

        public static ImmutableList<Supplier<ResourceKey<ConfiguredFeature<?, ?>>>> getConfiguredFeatures() {
            return ImmutableList.copyOf(CONFIGURED_FEATURES);
        }
    }

    @RegistrarEntry
    public static class CAPlacedFeatures {
        private static final ObjectArrayList<Supplier<ResourceKey<PlacedFeature>>> PLACED_FEATURES = new ObjectArrayList<>();

        // Bonemeal
        public static final Supplier<ResourceKey<PlacedFeature>> DENSE_GRASS_BONEMEAL = registerPlacedFeature("dense_grass_bonemeal", CAConfiguredFeatures.SINGLE_DENSE_GRASS, ObjectArrayList.of(PlacementUtils.isEmpty()));
        public static final Supplier<ResourceKey<PlacedFeature>> CRYSTAL_GRASS_BONEMEAL = registerPlacedFeature("crystal_grass_bonemeal", CAConfiguredFeatures.SINGLE_CRYSTAL_GRASS, ObjectArrayList.of(PlacementUtils.isEmpty()));

        // Mining Paradise
        public static final Supplier<ResourceKey<PlacedFeature>> DENSE_GRASS_PATCH = registerPlacedFeature("dense_grass_patch", CAConfiguredFeatures.DENSE_GRASS_PATCH, ObjectArrayList.of(NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> TALL_DENSE_GRASS_PATCH = registerPlacedFeature("tall_dense_grass_patch", CAConfiguredFeatures.TALL_DENSE_GRASS_PATCH, ObjectArrayList.of(NoiseThresholdCountPlacement.of(0D, 2, 5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> THORNY_SUN_PATCH = registerPlacedFeature("thorny_sun_patch", CAConfiguredFeatures.THORNY_SUN_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> ALSTROEMERIAT_PATCH = registerPlacedFeature("alstroemeriat_patch", CAConfiguredFeatures.ALSTROEMERIAT_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> DENSE_BUSH_PATCH = registerPlacedFeature("dense_bush_patch", CAConfiguredFeatures.DENSE_BUSH_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> TALL_DENSE_BUSH_PATCH = registerPlacedFeature("tall_dense_bush_patch", CAConfiguredFeatures.TALL_DENSE_BUSH_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> SMALL_CARNIVOROUS_PLANT_PATCH = registerPlacedFeature("small_carnivorous_plant_patch", CAConfiguredFeatures.SMALL_CARNIVOROUS_PLANT_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> BIG_CARNIVOROUS_PLANT_PATCH = registerPlacedFeature("big_carnivorous_plant_patch", CAConfiguredFeatures.BIG_CARNIVOROUS_PLANT_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> BUSH_PATCH = registerPlacedFeature("bush_patch", CAConfiguredFeatures.BUSH_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> TALL_BUSH_PATCH = registerPlacedFeature("tall_bush_patch", CAConfiguredFeatures.TALL_BUSH_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> LARGE_MESOZOIC_BUSH_PATCH = registerPlacedFeature("large_mesozoic_bush_patch", CAConfiguredFeatures.LARGE_MESOZOIC_BUSH_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> DREDGESTONE_ROCK = registerPlacedFeature("dredgestone_rock", CAConfiguredFeatures.DREDGESTONE_ROCK, ObjectArrayList.of(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

        private static Supplier<ResourceKey<PlacedFeature>> registerPlacedFeature(ResourceLocation id, Supplier<ResourceKey<ConfiguredFeature<?, ?>>> configuredFeatureHolder, List<PlacementModifier> placementModifiers) {
            Supplier<ResourceKey<PlacedFeature>> placedFeatureSup = CAServices.REGISTRAR.registerDatapackObject(id, b -> () -> new PlacedFeature(b.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(configuredFeatureHolder.get()), List.copyOf(placementModifiers)), Registries.PLACED_FEATURE);
            PLACED_FEATURES.add(placedFeatureSup);
            return placedFeatureSup;
        }

        private static Supplier<ResourceKey<PlacedFeature>> registerPlacedFeature(String id, Supplier<ResourceKey<ConfiguredFeature<?, ?>>> configuredFeatureHolder, List<PlacementModifier> placementModifiers) {
            return registerPlacedFeature(CAConstants.prefix(id), configuredFeatureHolder, placementModifiers);
        }

        public static ImmutableList<Supplier<ResourceKey<PlacedFeature>>> getPlacedFeatures() {
            return ImmutableList.copyOf(PLACED_FEATURES);
        }
    }
}
