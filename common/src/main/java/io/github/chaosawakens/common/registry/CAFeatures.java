package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.asm.annotations.RegistrarEntry;
import io.github.chaosawakens.api.platform.CAServices;
import io.github.chaosawakens.common.worldgen.feature.NBTTreeFeature;
import io.github.chaosawakens.common.worldgen.feature.RandomKeySelectorFeature;
import io.github.chaosawakens.common.worldgen.feature.SimpleSupplierStateProvider;
import io.github.chaosawakens.common.worldgen.feature.WeightedPlacedFeatureKey;
import io.github.chaosawakens.common.worldgen.feature.configurations.NBTTreeConfiguration;
import io.github.chaosawakens.common.worldgen.feature.configurations.RandomFeatureKeyConfiguration;
import io.github.chaosawakens.common.worldgen.placement_modifier.InSquareBBPlacement;
import io.github.chaosawakens.common.worldgen.placement_modifier.PickLowestHeightPlacement;
import io.github.chaosawakens.common.worldgen.placement_modifier.SurfaceAreaCheckPlacement;
import io.github.chaosawakens.common.worldgen.placement_modifier.SurfaceCheckPlacement;
import io.github.chaosawakens.common.worldgen.structure.BeeHiveProcessor;
import io.github.chaosawakens.common.worldgen.structure.MesozoicVineProcessor;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class CAFeatures {

    @RegistrarEntry
    public static class Features {
        private static final ObjectArrayList<Supplier<Feature<?>>> FEATURES = new ObjectArrayList<>();

        public static final Supplier<Feature<NBTTreeConfiguration>> NBT_TREE = registerFeature("nbt_tree", () -> new NBTTreeFeature(NBTTreeConfiguration.CODEC));
        public static final Supplier<Feature<RandomFeatureKeyConfiguration>> RANDOM_KEY_SELECTOR = registerFeature("random_key_selector", () -> new RandomKeySelectorFeature(RandomFeatureKeyConfiguration.CODEC));

        private static <FC extends FeatureConfiguration, F extends Feature<FC>> Supplier<F> registerFeature(String id, Supplier<Feature<?>> featureSup) {
            Supplier<F> placedFeatureSup = (Supplier<F>) CAServices.REGISTRAR.registerObject(CAConstants.prefix(id), featureSup, BuiltInRegistries.FEATURE);
            FEATURES.add(featureSup);
            return placedFeatureSup;
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

        // Crystal World
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> CRYSTAL_GRASS_PATCH = registerConfiguredFeature("crystal_grass_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.CRYSTAL_GRASS.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> TALL_CRYSTAL_GRASS_PATCH = registerConfiguredFeature("tall_crystal_grass_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.TALL_CRYSTAL_GRASS.get()))))));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> BLACK_CRYSTAL_GROWTH_PATCH = registerConfiguredFeature("black_crystal_growth_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(96, 12, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.BLACK_CRYSTAL_GROWTH.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> BROWN_CRYSTAL_GROWTH_PATCH = registerConfiguredFeature("brown_crystal_growth_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(96, 12, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.BROWN_CRYSTAL_GROWTH.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> PURPLE_CRYSTAL_GROWTH_PATCH = registerConfiguredFeature("purple_crystal_growth_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(96, 12, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.PURPLE_CRYSTAL_GROWTH.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GREEN_CRYSTAL_GROWTH_PATCH = registerConfiguredFeature("green_crystal_growth_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(96, 12, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.GREEN_CRYSTAL_GROWTH.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> PINK_CRYSTAL_GROWTH_PATCH = registerConfiguredFeature("pink_crystal_growth_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(96, 12, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.PINK_CRYSTAL_GROWTH.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> YELLOW_CRYSTAL_GROWTH_PATCH = registerConfiguredFeature("yellow_crystal_growth_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(96, 12, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.YELLOW_CRYSTAL_GROWTH.get()))))));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> ORANGE_CRYSTAL_FLOWER_PATCH = registerConfiguredFeature("orange_crystal_flower_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(96, 12, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.ORANGE_CRYSTAL_FLOWER.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> PINK_CRYSTAL_FLOWER_PATCH = registerConfiguredFeature("pink_crystal_flower_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(96, 12, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.PINK_CRYSTAL_FLOWER.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> RED_CRYSTAL_FLOWER_PATCH = registerConfiguredFeature("red_crystal_flower_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(96, 12, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.RED_CRYSTAL_FLOWER.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> YELLOW_CRYSTAL_FLOWER_PATCH = registerConfiguredFeature("yellow_crystal_flower_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(96, 12, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.YELLOW_CRYSTAL_FLOWER.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> BLUE_CRYSTAL_FLOWER_PATCH = registerConfiguredFeature("blue_crystal_flower_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(96, 12, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.BLUE_CRYSTAL_FLOWER.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GREEN_CRYSTAL_FLOWER_PATCH = registerConfiguredFeature("green_crystal_flower_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(96, 12, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.GREEN_CRYSTAL_FLOWER.get()))))));

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
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> TAR_PUDDLE = registerConfiguredFeature("tar_puddle", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(128, 3, 1, PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.TAR.get())), BlockPredicate.matchesBlocks(List.of(CABlocks.DENSE_DIRT.get(), CABlocks.DENSE_GRASS_BLOCK.get()))))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> LATOSOL_PUDDLE = registerConfiguredFeature("latosol_puddle", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(128, 3, 1, PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(CABlocks.LATOSOL.get())), BlockPredicate.matchesBlocks(List.of(CABlocks.DENSE_DIRT.get(), CABlocks.DENSE_GRASS_BLOCK.get()))))));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> MESOZOIC_TREE_VARIANT_1 = registerConfiguredFeature("mesozoic_tree_variant_1", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/mesozoic_tree/mesozoic_tree_1")), 0, Optional.of(new StructureProcessorList(ObjectArrayList.of(new BeeHiveProcessor(0.1f), new MesozoicVineProcessor(0.8f)))) )));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> MESOZOIC_TREE_VARIANT_2 = registerConfiguredFeature("mesozoic_tree_variant_2", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/mesozoic_tree/mesozoic_tree_2")), 0, Optional.of(new StructureProcessorList(ObjectArrayList.of(new BeeHiveProcessor(0.1f), new MesozoicVineProcessor(0.8f)))) )));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> MESOZOIC_TREE_VARIANT_3 = registerConfiguredFeature("mesozoic_tree_variant_3", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/mesozoic_tree/mesozoic_tree_3")), 0, Optional.of(new StructureProcessorList(ObjectArrayList.of(new BeeHiveProcessor(0.1f), new MesozoicVineProcessor(0.8f)))) )));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GINKGO_TREE_VARIANT_1 = registerConfiguredFeature("ginkgo_tree_variant_1", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/ginkgo_tree/ginkgo_tree_1")), 0, Optional.of(new StructureProcessorList(ObjectArrayList.of(new BeeHiveProcessor(0.2f)))) )));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GINKGO_TREE_VARIANT_2 = registerConfiguredFeature("ginkgo_tree_variant_2", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/ginkgo_tree/ginkgo_tree_2")), 0, Optional.of(new StructureProcessorList(ObjectArrayList.of(new BeeHiveProcessor(0.2f)))) )));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GINKGO_TREE_VARIANT_3 = registerConfiguredFeature("ginkgo_tree_variant_3", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/ginkgo_tree/ginkgo_tree_3")), 0, Optional.of(new StructureProcessorList(ObjectArrayList.of(new BeeHiveProcessor(0.2f)))) )));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DENSEWOOD_TREE_VARIANT_1 = registerConfiguredFeature("densewood_tree_variant_1", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/densewood_tree_1")), 0, Optional.of(new StructureProcessorList(ObjectArrayList.of(new BeeHiveProcessor(0.2f)))) )));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> TREES_MESOZOIC = registerConfiguredFeature("trees_mesozoic", () -> new ConfiguredFeature<>(Features.RANDOM_KEY_SELECTOR.get(), new RandomFeatureKeyConfiguration( ObjectArrayList.of(new WeightedPlacedFeatureKey(CAPlacedFeatures.MESOZOIC_TREE_VARIANT_1, 0.2f), new WeightedPlacedFeatureKey(CAPlacedFeatures.MESOZOIC_TREE_VARIANT_2, 0.4f)), CAPlacedFeatures.MESOZOIC_TREE_VARIANT_3)));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> URANIUM_BLOCK = registerConfiguredFeature("uranium_block", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.DREDGESTONE.get()), CABlocks.URANIUM_BLOCK.get().defaultBlockState(), 64)));

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

        // Crystal World
        public static final Supplier<ResourceKey<PlacedFeature>> CRYSTAL_GRASS_PATCH = registerPlacedFeature("crystal_grass_patch", CAConfiguredFeatures.CRYSTAL_GRASS_PATCH, ObjectArrayList.of(NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> TALL_CRYSTAL_GRASS_PATCH = registerPlacedFeature("tall_crystal_grass_patch", CAConfiguredFeatures.TALL_CRYSTAL_GRASS_PATCH, ObjectArrayList.of(NoiseThresholdCountPlacement.of(0D, 2, 5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

        public static final Supplier<ResourceKey<PlacedFeature>> BLACK_CRYSTAL_GROWTH_PATCH = registerPlacedFeature("black_crystal_growth_patch", CAConfiguredFeatures.BLACK_CRYSTAL_GROWTH_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> BROWN_CRYSTAL_GROWTH_PATCH = registerPlacedFeature("brown_crystal_growth_patch", CAConfiguredFeatures.BROWN_CRYSTAL_GROWTH_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> PURPLE_CRYSTAL_GROWTH_PATCH = registerPlacedFeature("purple_crystal_growth_patch", CAConfiguredFeatures.PURPLE_CRYSTAL_GROWTH_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> GREEN_CRYSTAL_GROWTH_PATCH = registerPlacedFeature("green_crystal_growth_patch", CAConfiguredFeatures.GREEN_CRYSTAL_GROWTH_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> PINK_CRYSTAL_GROWTH_PATCH = registerPlacedFeature("pink_crystal_growth_patch", CAConfiguredFeatures.PINK_CRYSTAL_GROWTH_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> YELLOW_CRYSTAL_GROWTH_PATCH = registerPlacedFeature("yellow_crystal_growth_patch", CAConfiguredFeatures.YELLOW_CRYSTAL_GROWTH_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

        public static final Supplier<ResourceKey<PlacedFeature>> ORANGE_CRYSTAL_FLOWER_PATCH = registerPlacedFeature("orange_crystal_flower_patch", CAConfiguredFeatures.ORANGE_CRYSTAL_FLOWER_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> PINK_CRYSTAL_FLOWER_PATCH = registerPlacedFeature("pink_crystal_flower_patch", CAConfiguredFeatures.PINK_CRYSTAL_FLOWER_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> RED_CRYSTAL_FLOWER_PATCH = registerPlacedFeature("red_crystal_flower_patch", CAConfiguredFeatures.RED_CRYSTAL_FLOWER_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> YELLOW_CRYSTAL_FLOWER_PATCH = registerPlacedFeature("yellow_crystal_flower_patch", CAConfiguredFeatures.YELLOW_CRYSTAL_FLOWER_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> BLUE_CRYSTAL_FLOWER_PATCH = registerPlacedFeature("blue_crystal_flower_patch", CAConfiguredFeatures.BLUE_CRYSTAL_FLOWER_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> GREEN_CRYSTAL_FLOWER_PATCH = registerPlacedFeature("green_crystal_flower_patch", CAConfiguredFeatures.GREEN_CRYSTAL_FLOWER_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

        // Mining Paradise
        public static final Supplier<ResourceKey<PlacedFeature>> DENSE_GRASS_PATCH = registerPlacedFeature("dense_grass_patch", CAConfiguredFeatures.DENSE_GRASS_PATCH, ObjectArrayList.of(NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> TALL_DENSE_GRASS_PATCH = registerPlacedFeature("tall_dense_grass_patch", CAConfiguredFeatures.TALL_DENSE_GRASS_PATCH, ObjectArrayList.of(NoiseThresholdCountPlacement.of(0D, 2, 5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

        public static final Supplier<ResourceKey<PlacedFeature>> THORNY_SUN_PATCH = registerPlacedFeature("thorny_sun_patch", CAConfiguredFeatures.THORNY_SUN_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> ALSTROEMERIAT_PATCH = registerPlacedFeature("alstroemeriat_patch", CAConfiguredFeatures.ALSTROEMERIAT_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

        public static final Supplier<ResourceKey<PlacedFeature>> DENSE_BUSH_PATCH = registerPlacedFeature("dense_bush_patch", CAConfiguredFeatures.DENSE_BUSH_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(24), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> TALL_DENSE_BUSH_PATCH = registerPlacedFeature("tall_dense_bush_patch", CAConfiguredFeatures.TALL_DENSE_BUSH_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(24), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

        public static final Supplier<ResourceKey<PlacedFeature>> SMALL_CARNIVOROUS_PLANT_PATCH = registerPlacedFeature("small_carnivorous_plant_patch", CAConfiguredFeatures.SMALL_CARNIVOROUS_PLANT_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> BIG_CARNIVOROUS_PLANT_PATCH = registerPlacedFeature("big_carnivorous_plant_patch", CAConfiguredFeatures.BIG_CARNIVOROUS_PLANT_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

        public static final Supplier<ResourceKey<PlacedFeature>> BUSH_PATCH = registerPlacedFeature("bush_patch", CAConfiguredFeatures.BUSH_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(24), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> TALL_BUSH_PATCH = registerPlacedFeature("tall_bush_patch", CAConfiguredFeatures.TALL_BUSH_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(24), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

        public static final Supplier<ResourceKey<PlacedFeature>> LARGE_MESOZOIC_BUSH_PATCH = registerPlacedFeature("large_mesozoic_bush_patch", CAConfiguredFeatures.LARGE_MESOZOIC_BUSH_PATCH, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

        public static final Supplier<ResourceKey<PlacedFeature>> DREDGESTONE_ROCK = registerPlacedFeature("dredgestone_rock", CAConfiguredFeatures.DREDGESTONE_ROCK, ObjectArrayList.of(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> TAR_PUDDLE = registerPlacedFeature("tar_puddle", CAConfiguredFeatures.TAR_PUDDLE, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(48), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
        public static final Supplier<ResourceKey<PlacedFeature>> LATOSOL_PUDDLE = registerPlacedFeature("latosol_puddle", CAConfiguredFeatures.LATOSOL_PUDDLE, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(48), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

        public static final Supplier<ResourceKey<PlacedFeature>> MESOZOIC_TREE_VARIANT_1 = registerPlacedFeature("mesozoic_tree_variant_1", CAConfiguredFeatures.MESOZOIC_TREE_VARIANT_1, nbtTreePlacement(new BoundingBox(11, 0, 9, 16, 10, 14), 27, 25));
        public static final Supplier<ResourceKey<PlacedFeature>> MESOZOIC_TREE_VARIANT_2 = registerPlacedFeature("mesozoic_tree_variant_2", CAConfiguredFeatures.MESOZOIC_TREE_VARIANT_2, nbtTreePlacement(new BoundingBox(6, 0, 7, 11, 10, 12), 25, 25));
        public static final Supplier<ResourceKey<PlacedFeature>> MESOZOIC_TREE_VARIANT_3 = registerPlacedFeature("mesozoic_tree_variant_3", CAConfiguredFeatures.MESOZOIC_TREE_VARIANT_3, nbtTreePlacement(new BoundingBox(6, 0, 6, 11, 10, 11), 19, 17));

        public static final Supplier<ResourceKey<PlacedFeature>> GINKGO_TREE_VARIANT_1 = registerPlacedFeature("ginkgo_tree_variant_1", CAConfiguredFeatures.GINKGO_TREE_VARIANT_1, nbtTreePlacement(new BoundingBox(10, 0, 8, 17, 10, 15), 27, 25));
        public static final Supplier<ResourceKey<PlacedFeature>> GINKGO_TREE_VARIANT_2 = registerPlacedFeature("ginkgo_tree_variant_2", CAConfiguredFeatures.GINKGO_TREE_VARIANT_2, nbtTreePlacement(new BoundingBox(5, 0, 6, 11, 10, 12), 25, 25));
        public static final Supplier<ResourceKey<PlacedFeature>> GINKGO_TREE_VARIANT_3 = registerPlacedFeature("ginkgo_tree_variant_3", CAConfiguredFeatures.GINKGO_TREE_VARIANT_3, nbtTreePlacement(new BoundingBox(5, 0, 5, 11, 10, 11), 19, 17));

        public static final Supplier<ResourceKey<PlacedFeature>> DENSEWOOD_TREE_VARIANT_1 = registerPlacedFeature("densewood_tree_variant_1", CAConfiguredFeatures.DENSEWOOD_TREE_VARIANT_1, nbtTreePlacementExtra(RarityFilter.onAverageOnceEvery(8), new BoundingBox(4, 0, 5, 6, 10, 7), 11, 13));

        public static final Supplier<ResourceKey<PlacedFeature>> TREES_MESOZOIC = registerPlacedFeature("trees_mesozoic", CAConfiguredFeatures.TREES_MESOZOIC, ObjectArrayList.of(PlacementUtils.countExtra(4, 0.1F, 1)));

        public static final Supplier<ResourceKey<PlacedFeature>> URANIUM_BLOCK = registerPlacedFeature("uranium_block", CAConfiguredFeatures.URANIUM_BLOCK, commonOrePlacement(30, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(192))));

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

        private static ObjectArrayList<PlacementModifier> nbtTreePlacement(BoundingBox trunkBB, int x, int z) {
            return ObjectArrayList.of(
                    new InSquareBBPlacement(x, z),
                    SurfaceWaterDepthFilter.forMaxDepth(0),
                    PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                    new SurfaceAreaCheckPlacement(new SimpleSupplierStateProvider(() -> CABlocks.DENSE_GRASS_BLOCK.get().defaultBlockState()), trunkBB),
                    new PickLowestHeightPlacement(trunkBB)
            );
        }

        private static ObjectArrayList<PlacementModifier> nbtTreePlacementExtra(PlacementModifier modifier, BoundingBox trunkBB, int x, int z) {
            return ObjectArrayList.of(
                    modifier,
                    new InSquareBBPlacement(x, z),
                    SurfaceWaterDepthFilter.forMaxDepth(0),
                    PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                    BiomeFilter.biome(),
                    new SurfaceAreaCheckPlacement(new SimpleSupplierStateProvider(() -> CABlocks.DENSE_GRASS_BLOCK.get().defaultBlockState()), trunkBB),
                    new PickLowestHeightPlacement(trunkBB));
        }

        private static List<PlacementModifier> orePlacement(PlacementModifier pCountPlacement, PlacementModifier pHeightRange) {
            return List.of(pCountPlacement, InSquarePlacement.spread(), pHeightRange, BiomeFilter.biome());
        }

        private static List<PlacementModifier> commonOrePlacement(int pCount, PlacementModifier pHeightRange) {
            return orePlacement(CountPlacement.of(pCount), pHeightRange);
        }

        private static List<PlacementModifier> rareOrePlacement(int pChance, PlacementModifier pHeightRange) {
            return orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange);
        }
    }

    @RegistrarEntry
    public static class PlacementModifiers {
        private static final ObjectArrayList<Supplier<PlacementModifierType<?>>> PLACEMENT_MODIFIERS = new ObjectArrayList<>();

        public static final Supplier<PlacementModifierType<InSquareBBPlacement>> IN_BB_SQUARE = registerModifier("in_bb_square", () -> () -> InSquareBBPlacement.CODEC);
        public static final Supplier<PlacementModifierType<SurfaceCheckPlacement>> SURFACE_CHECK = registerModifier("surface_check", () -> () -> SurfaceCheckPlacement.CODEC);
        public static final Supplier<PlacementModifierType<SurfaceAreaCheckPlacement>> SURFACE_AREA_CHECK = registerModifier("surface_area_check", () -> () -> SurfaceAreaCheckPlacement.CODEC);
        public static final Supplier<PlacementModifierType<PickLowestHeightPlacement>> PICK_LOWEST_HEIGHT = registerModifier("pick_lowest_height", () -> () -> PickLowestHeightPlacement.CODEC);

        private static <P extends PlacementModifier> Supplier<PlacementModifierType<P>> registerModifier(String id, Supplier<PlacementModifierType<P>> codecSup) {
            Supplier<PlacementModifierType<P>> typeSupplier = CAServices.REGISTRAR.registerObject(CAConstants.prefix(id), codecSup, BuiltInRegistries.PLACEMENT_MODIFIER_TYPE);
            PLACEMENT_MODIFIERS.add((Supplier) typeSupplier);
            return typeSupplier;
        }

        public static ImmutableList<Supplier<PlacementModifierType<?>>> getModifiers() {
            return ImmutableList.copyOf(PLACEMENT_MODIFIERS);
        }
    }

    @RegistrarEntry
    public static class StructureProcessors {
        private static final ObjectArrayList<Supplier<StructureProcessorType<?>>> STRUCTURE_PROCESSORS = new ObjectArrayList<>();

        public static final Supplier<StructureProcessorType<BeeHiveProcessor>> BEE_HIVE = registerProcessor("bee_hive", () -> () -> BeeHiveProcessor.CODEC);
        public static final Supplier<StructureProcessorType<MesozoicVineProcessor>> MESOZOIC_VINE = registerProcessor("mesozoic_vine", () -> () -> MesozoicVineProcessor.CODEC);

        private static <P extends StructureProcessor> Supplier<StructureProcessorType<P>> registerProcessor(String id, Supplier<StructureProcessorType<P>> codecSup) {
            Supplier<StructureProcessorType<P>> typeSupplier = CAServices.REGISTRAR.registerObject(CAConstants.prefix(id), codecSup, BuiltInRegistries.STRUCTURE_PROCESSOR);
            STRUCTURE_PROCESSORS.add((Supplier) typeSupplier);
            return typeSupplier;
        }

        public static ImmutableList<Supplier<StructureProcessorType<?>>> getProcessors() {
            return ImmutableList.copyOf(STRUCTURE_PROCESSORS);
        }
    }

    @RegistrarEntry
    public static class StateProviders {
        private static final ObjectArrayList<Supplier<BlockStateProviderType<?>>> STATE_PROVIDERS = new ObjectArrayList<>();

        public static final Supplier<BlockStateProviderType<SimpleSupplierStateProvider>> SIMPLE_SUPPLIER_PROVIDER = registerProvider("simple_supplier_provider", SimpleSupplierStateProvider.CODEC);

        private static <P extends BlockStateProvider> Supplier<BlockStateProviderType<P>> registerProvider(String id, Codec<P> codec) {
            Supplier<BlockStateProviderType<P>> typeSupplier = CAServices.REGISTRAR.registerObject(CAConstants.prefix(id), () -> new BlockStateProviderType(codec), BuiltInRegistries.BLOCKSTATE_PROVIDER_TYPE);
            STATE_PROVIDERS.add((Supplier) typeSupplier);
            return typeSupplier;
        }

        public static ImmutableList<Supplier<BlockStateProviderType<?>>> getProviders() {
            return ImmutableList.copyOf(STATE_PROVIDERS);
        }
    }
}
