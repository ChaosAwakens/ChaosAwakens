package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.asm.annotations.RegistrarEntry;
import io.github.chaosawakens.api.platform.CAServices;
import io.github.chaosawakens.common.worldgen.feature.*;
import io.github.chaosawakens.common.worldgen.feature.configurations.StalagmiteConfiguration;
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
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
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
        public static final Supplier<Feature<StalagmiteConfiguration>> STALAGMITE = registerFeature("stalagmite", () -> new StalagmiteFeature(StalagmiteConfiguration.CODEC));
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
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> STALAGMITE = registerConfiguredFeature("stalagmite", () -> new ConfiguredFeature<>(Features.STALAGMITE.get(), new StalagmiteConfiguration(CABlocks.DREDGESTONE.get(), 6, 8, 0.8f, 0.5f)));
            
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
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> TAR_PUDDLE = registerConfiguredFeature("tar_puddle", () -> new ConfiguredFeature<>(Feature.REPLACE_BLOBS, new ReplaceSphereConfiguration(CABlocks.DENSE_GRASS_BLOCK.get().defaultBlockState(), CABlocks.TAR.get().defaultBlockState(), UniformInt.of(3, 7))));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> LATOSOL_PUDDLE = registerConfiguredFeature("latosol_puddle", () -> new ConfiguredFeature<>(Feature.REPLACE_BLOBS, new ReplaceSphereConfiguration(CABlocks.DENSE_GRASS_BLOCK.get().defaultBlockState(), CABlocks.LATOSOL.get().defaultBlockState(), UniformInt.of(3, 7))));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> MESOZOIC_TREE_VARIANT_1 = registerConfiguredFeature("mesozoic_tree_variant_1", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/mesozoic_tree/mesozoic_tree_1")), 0, Optional.of(new StructureProcessorList(ObjectArrayList.of(new MesozoicVineProcessor(0.8f)))) )));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> MESOZOIC_TREE_VARIANT_2 = registerConfiguredFeature("mesozoic_tree_variant_2", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/mesozoic_tree/mesozoic_tree_2")), 0, Optional.of(new StructureProcessorList(ObjectArrayList.of(new MesozoicVineProcessor(0.8f)))) )));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> MESOZOIC_TREE_VARIANT_3 = registerConfiguredFeature("mesozoic_tree_variant_3", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/mesozoic_tree/mesozoic_tree_3")), 0, Optional.of(new StructureProcessorList(ObjectArrayList.of(new MesozoicVineProcessor(0.8f)))) )));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> MESOZOIC_TREE_VARIANT_4 = registerConfiguredFeature("mesozoic_tree_variant_4", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/mesozoic_tree/mesozoic_tree_4")), 0, Optional.of(new StructureProcessorList(ObjectArrayList.of(new MesozoicVineProcessor(0.8f)))) )));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> MESOZOIC_TREE_VARIANT_5 = registerConfiguredFeature("mesozoic_tree_variant_5", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/mesozoic_tree/mesozoic_tree_5")), 0, Optional.of(new StructureProcessorList(ObjectArrayList.of(new MesozoicVineProcessor(0.8f)))) )));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> MESOZOIC_TREE_VARIANT_6 = registerConfiguredFeature("mesozoic_tree_variant_6", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/mesozoic_tree/mesozoic_tree_6")), 0, Optional.of(new StructureProcessorList(ObjectArrayList.of(new MesozoicVineProcessor(0.8f)))) )));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GINKGO_TREE_VARIANT_1 = registerConfiguredFeature("ginkgo_tree_variant_1", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/ginkgo_tree/ginkgo_tree_1")), 0, Optional.empty() )));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GINKGO_TREE_VARIANT_2 = registerConfiguredFeature("ginkgo_tree_variant_2", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/ginkgo_tree/ginkgo_tree_2")), 0, Optional.empty() )));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GINKGO_TREE_VARIANT_3 = registerConfiguredFeature("ginkgo_tree_variant_3", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/ginkgo_tree/ginkgo_tree_3")), 0, Optional.empty() )));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GINKGO_TREE_VARIANT_4 = registerConfiguredFeature("ginkgo_tree_variant_4", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/ginkgo_tree/ginkgo_tree_4")), 0, Optional.empty() )));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GINKGO_TREE_VARIANT_5 = registerConfiguredFeature("ginkgo_tree_variant_5", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/ginkgo_tree/ginkgo_tree_5")), 0, Optional.empty() )));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DENSEWOOD_TREE_VARIANT_1 = registerConfiguredFeature("densewood_tree_variant_1", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/densewood_tree/densewood_tree_1")), 0, Optional.empty() )));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DENSEWOOD_TREE_VARIANT_2 = registerConfiguredFeature("densewood_tree_variant_2", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/densewood_tree/densewood_tree_2")), 0, Optional.empty() )));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DENSEWOOD_TREE_VARIANT_3 = registerConfiguredFeature("densewood_tree_variant_3", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/densewood_tree/densewood_tree_3")), 0, Optional.empty() )));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DENSEWOOD_TREE_VARIANT_4 = registerConfiguredFeature("densewood_tree_variant_4", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/densewood_tree/densewood_tree_4")), 0, Optional.empty() )));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DENSEWOOD_TREE_VARIANT_5 = registerConfiguredFeature("densewood_tree_variant_5", () -> new ConfiguredFeature<>(Features.NBT_TREE.get(), new NBTTreeConfiguration(Either.left(CAConstants.prefix("feature_presets/nbt_tree/densewood_tree/densewood_tree_5")), 0, Optional.empty() )));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> TREES_MESOZOIC = registerConfiguredFeature("trees_mesozoic", () -> new ConfiguredFeature<>(Features.RANDOM_KEY_SELECTOR.get(), new RandomFeatureKeyConfiguration(ObjectArrayList.of(new WeightedPlacedFeatureKey(CAPlacedFeatures.MESOZOIC_TREE_VARIANT_1, 0.16f), new WeightedPlacedFeatureKey(CAPlacedFeatures.MESOZOIC_TREE_VARIANT_2, 0.16f), new WeightedPlacedFeatureKey(CAPlacedFeatures.MESOZOIC_TREE_VARIANT_3, 0.16f), new WeightedPlacedFeatureKey(CAPlacedFeatures.MESOZOIC_TREE_VARIANT_4, 0.16f), new WeightedPlacedFeatureKey(CAPlacedFeatures.MESOZOIC_TREE_VARIANT_5, 0.16f)), CAPlacedFeatures.MESOZOIC_TREE_VARIANT_6)));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> TREES_DENSEWOOD = registerConfiguredFeature("trees_densewood", () -> new ConfiguredFeature<>(Features.RANDOM_KEY_SELECTOR.get(), new RandomFeatureKeyConfiguration(ObjectArrayList.of(new WeightedPlacedFeatureKey(CAPlacedFeatures.DENSEWOOD_TREE_VARIANT_1, 0.2f), new WeightedPlacedFeatureKey(CAPlacedFeatures.DENSEWOOD_TREE_VARIANT_2, 0.2f), new WeightedPlacedFeatureKey(CAPlacedFeatures.DENSEWOOD_TREE_VARIANT_3, 0.2f), new WeightedPlacedFeatureKey(CAPlacedFeatures.DENSEWOOD_TREE_VARIANT_4, 0.2f)), CAPlacedFeatures.DENSEWOOD_TREE_VARIANT_5)));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> TREES_GINKGO = registerConfiguredFeature("trees_ginkgo", () -> new ConfiguredFeature<>(Features.RANDOM_KEY_SELECTOR.get(), new RandomFeatureKeyConfiguration(ObjectArrayList.of(new WeightedPlacedFeatureKey(CAPlacedFeatures.GINKGO_TREE_VARIANT_1, 0.2f), new WeightedPlacedFeatureKey(CAPlacedFeatures.GINKGO_TREE_VARIANT_2, 0.2f), new WeightedPlacedFeatureKey(CAPlacedFeatures.GINKGO_TREE_VARIANT_3, 0.2f), new WeightedPlacedFeatureKey(CAPlacedFeatures.GINKGO_TREE_VARIANT_4, 0.2f)), CAPlacedFeatures.GINKGO_TREE_VARIANT_5)));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DREDGESTONE_URANIUM_ORE = registerConfiguredFeature("dredgestone_uranium_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.DREDGESTONE.get()), CABlocks.DREDGESTONE_URANIUM_ORE.get().defaultBlockState(), 3)));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GLOOMSTONE_URANIUM_ORE = registerConfiguredFeature("gloomstone_uranium_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.GLOOMSTONE.get()), CABlocks.GLOOMSTONE_URANIUM_ORE.get().defaultBlockState(), 4)));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DREDGESTONE_TITANIUM_ORE = registerConfiguredFeature("dredgestone_titanium_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.DREDGESTONE.get()), CABlocks.DREDGESTONE_TITANIUM_ORE.get().defaultBlockState(), 3)));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GLOOMSTONE_TITANIUM_ORE = registerConfiguredFeature("gloomstone_titanium_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.GLOOMSTONE.get()), CABlocks.GLOOMSTONE_TITANIUM_ORE.get().defaultBlockState(), 4)));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DREDGESTONE_SUNSTONE_ORE = registerConfiguredFeature("dredgestone_sunstone_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.DREDGESTONE.get()), CABlocks.DREDGESTONE_SUNSTONE_ORE.get().defaultBlockState(), 22)));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GLOOMSTONE_SUNSTONE_ORE = registerConfiguredFeature("gloomstone_sunstone_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.GLOOMSTONE.get()), CABlocks.GLOOMSTONE_SUNSTONE_ORE.get().defaultBlockState(), 15)));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DREDGESTONE_RUBY_ORE = registerConfiguredFeature("dredgestone_ruby_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.DREDGESTONE.get()), CABlocks.DREDGESTONE_RUBY_ORE.get().defaultBlockState(), 2)));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GLOOMSTONE_RUBY_ORE = registerConfiguredFeature("gloomstone_ruby_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.GLOOMSTONE.get()), CABlocks.GLOOMSTONE_RUBY_ORE.get().defaultBlockState(), 3)));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DREDGESTONE_REDSTONE_ORE = registerConfiguredFeature("dredgestone_redstone_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.DREDGESTONE.get()), CABlocks.DREDGESTONE_REDSTONE_ORE.get().defaultBlockState(), 5)));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GLOOMSTONE_REDSTONE_ORE = registerConfiguredFeature("gloomstone_redstone_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.GLOOMSTONE.get()), CABlocks.GLOOMSTONE_REDSTONE_ORE.get().defaultBlockState(), 6)));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DREDGESTONE_PLATINUM_ORE = registerConfiguredFeature("dredgestone_platinum_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.DREDGESTONE.get()), CABlocks.DREDGESTONE_PLATINUM_ORE.get().defaultBlockState(), 4)));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GLOOMSTONE_PLATINUM_ORE = registerConfiguredFeature("gloomstone_platinum_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.GLOOMSTONE.get()), CABlocks.GLOOMSTONE_PLATINUM_ORE.get().defaultBlockState(), 5)));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DREDGESTONE_LAPIS_ORE = registerConfiguredFeature("dredgestone_lapis_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.DREDGESTONE.get()), CABlocks.DREDGESTONE_LAPIS_ORE.get().defaultBlockState(), 7)));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GLOOMSTONE_LAPIS_ORE = registerConfiguredFeature("gloomstone_lapis_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.GLOOMSTONE.get()), CABlocks.GLOOMSTONE_LAPIS_ORE.get().defaultBlockState(), 7)));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DREDGESTONE_KUNZITE_ORE = registerConfiguredFeature("dredgestone_kunzite_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.DREDGESTONE.get()), CABlocks.DREDGESTONE_KUNZITE_ORE.get().defaultBlockState(), 4)));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GLOOMSTONE_KUNZITE_ORE = registerConfiguredFeature("gloomstone_kunzite_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.GLOOMSTONE.get()), CABlocks.GLOOMSTONE_KUNZITE_ORE.get().defaultBlockState(), 5)));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DREDGESTONE_IRON_ORE = registerConfiguredFeature("dredgestone_iron_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.DREDGESTONE.get()), CABlocks.DREDGESTONE_IRON_ORE.get().defaultBlockState(), 9)));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GLOOMSTONE_IRON_ORE = registerConfiguredFeature("gloomstone_iron_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.GLOOMSTONE.get()), CABlocks.GLOOMSTONE_IRON_ORE.get().defaultBlockState(), 9)));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DREDGESTONE_GOLD_ORE = registerConfiguredFeature("dredgestone_gold_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.DREDGESTONE.get()), CABlocks.DREDGESTONE_GOLD_ORE.get().defaultBlockState(), 9)));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GLOOMSTONE_GOLD_ORE = registerConfiguredFeature("gloomstone_gold_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.GLOOMSTONE.get()), CABlocks.GLOOMSTONE_GOLD_ORE.get().defaultBlockState(), 9)));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DREDGESTONE_DIAMOND_ORE = registerConfiguredFeature("dredgestone_diamond_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.DREDGESTONE.get()), CABlocks.DREDGESTONE_DIAMOND_ORE.get().defaultBlockState(), 4)));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GLOOMSTONE_DIAMOND_ORE = registerConfiguredFeature("gloomstone_diamond_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.GLOOMSTONE.get()), CABlocks.GLOOMSTONE_DIAMOND_ORE.get().defaultBlockState(), 12)));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DREDGESTONE_COPPER_ORE = registerConfiguredFeature("dredgestone_copper_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.DREDGESTONE.get()), CABlocks.DREDGESTONE_COPPER_ORE.get().defaultBlockState(), 20)));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GLOOMSTONE_COPPER_ORE = registerConfiguredFeature("gloomstone_copper_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.GLOOMSTONE.get()), CABlocks.GLOOMSTONE_COPPER_ORE.get().defaultBlockState(), 15)));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DREDGESTONE_COAL_ORE = registerConfiguredFeature("dredgestone_coal_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.DREDGESTONE.get()), CABlocks.DREDGESTONE_COAL_ORE.get().defaultBlockState(), 24)));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GLOOMSTONE_COAL_ORE = registerConfiguredFeature("gloomstone_coal_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.GLOOMSTONE.get()), CABlocks.GLOOMSTONE_COAL_ORE.get().defaultBlockState(), 17)));

        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> DREDGESTONE_ALUMINUM_ORE = registerConfiguredFeature("dredgestone_aluminum_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.DREDGESTONE.get()), CABlocks.DREDGESTONE_ALUMINUM_ORE.get().defaultBlockState(), 15)));
        public static final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> GLOOMSTONE_ALUMINUM_ORE = registerConfiguredFeature("gloomstone_aluminum_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(CABlocks.GLOOMSTONE.get()), CABlocks.GLOOMSTONE_ALUMINUM_ORE.get().defaultBlockState(), 20)));

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
        public static final Supplier<ResourceKey<PlacedFeature>> STALAGMITE_PLACED = registerPlacedFeature("stalagmite", CAConfiguredFeatures.STALAGMITE, ObjectArrayList.of(RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(),PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,BiomeFilter.biome()));
        
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

        public static final Supplier<ResourceKey<PlacedFeature>> MESOZOIC_TREE_VARIANT_1 = registerPlacedFeature("mesozoic_tree_variant_1", CAConfiguredFeatures.MESOZOIC_TREE_VARIANT_1, nbtTreePlacement(new BoundingBox(4, 0, 7, 11, 16, 13), 16, 18));
        public static final Supplier<ResourceKey<PlacedFeature>> MESOZOIC_TREE_VARIANT_2 = registerPlacedFeature("mesozoic_tree_variant_2", CAConfiguredFeatures.MESOZOIC_TREE_VARIANT_2, nbtTreePlacement(new BoundingBox(6, 0, 6, 12, 16, 13), 18, 18));
        public static final Supplier<ResourceKey<PlacedFeature>> MESOZOIC_TREE_VARIANT_3 = registerPlacedFeature("mesozoic_tree_variant_3", CAConfiguredFeatures.MESOZOIC_TREE_VARIANT_3, nbtTreePlacement(new BoundingBox(5, 0, 6, 11, 16, 11), 18, 17));
        public static final Supplier<ResourceKey<PlacedFeature>> MESOZOIC_TREE_VARIANT_4 = registerPlacedFeature("mesozoic_tree_variant_4", CAConfiguredFeatures.MESOZOIC_TREE_VARIANT_4, nbtTreePlacement(new BoundingBox(6, 0, 2, 13, 16, 9), 18, 14));
        public static final Supplier<ResourceKey<PlacedFeature>> MESOZOIC_TREE_VARIANT_5 = registerPlacedFeature("mesozoic_tree_variant_5", CAConfiguredFeatures.MESOZOIC_TREE_VARIANT_5, nbtTreePlacement(new BoundingBox(6, 0, 1, 12, 16, 9), 18, 14));
        public static final Supplier<ResourceKey<PlacedFeature>> MESOZOIC_TREE_VARIANT_6 = registerPlacedFeature("mesozoic_tree_variant_6", CAConfiguredFeatures.MESOZOIC_TREE_VARIANT_6, nbtTreePlacement(new BoundingBox(6, 0, 7, 12, 16, 12), 18, 14));

        public static final Supplier<ResourceKey<PlacedFeature>> GINKGO_TREE_VARIANT_1 = registerPlacedFeature("ginkgo_tree_variant_1", CAConfiguredFeatures.GINKGO_TREE_VARIANT_1, nbtTreePlacement(new BoundingBox(2, 0, 2, 6, 15, 6), 9, 8));
        public static final Supplier<ResourceKey<PlacedFeature>> GINKGO_TREE_VARIANT_2 = registerPlacedFeature("ginkgo_tree_variant_2", CAConfiguredFeatures.GINKGO_TREE_VARIANT_2, nbtTreePlacement(new BoundingBox(2, 0, 3, 7, 15, 7), 8, 9));
        public static final Supplier<ResourceKey<PlacedFeature>> GINKGO_TREE_VARIANT_3 = registerPlacedFeature("ginkgo_tree_variant_3", CAConfiguredFeatures.GINKGO_TREE_VARIANT_3, nbtTreePlacement(new BoundingBox(1, 0, 1, 5, 15, 5), 7, 7));
        public static final Supplier<ResourceKey<PlacedFeature>> GINKGO_TREE_VARIANT_4 = registerPlacedFeature("ginkgo_tree_variant_4", CAConfiguredFeatures.GINKGO_TREE_VARIANT_4, nbtTreePlacement(new BoundingBox(1, 0, 2, 5, 15, 6), 6, 7));
        public static final Supplier<ResourceKey<PlacedFeature>> GINKGO_TREE_VARIANT_5 = registerPlacedFeature("ginkgo_tree_variant_5", CAConfiguredFeatures.GINKGO_TREE_VARIANT_5, nbtTreePlacement(new BoundingBox(2, 0, 2, 6, 15, 6), 8, 8));

        public static final Supplier<ResourceKey<PlacedFeature>> DENSEWOOD_TREE_VARIANT_1 = registerPlacedFeature("densewood_tree_variant_1", CAConfiguredFeatures.DENSEWOOD_TREE_VARIANT_1, nbtTreePlacement(new BoundingBox(3, 0, 3, 8, 10, 8), 10, 11));
        public static final Supplier<ResourceKey<PlacedFeature>> DENSEWOOD_TREE_VARIANT_2 = registerPlacedFeature("densewood_tree_variant_2", CAConfiguredFeatures.DENSEWOOD_TREE_VARIANT_2, nbtTreePlacement(new BoundingBox(2, 0, 1, 7, 10, 6), 9, 7));
        public static final Supplier<ResourceKey<PlacedFeature>> DENSEWOOD_TREE_VARIANT_3 = registerPlacedFeature("densewood_tree_variant_3", CAConfiguredFeatures.DENSEWOOD_TREE_VARIANT_3, nbtTreePlacement(new BoundingBox(1, 0, 1, 6, 10, 6), 7, 7));
        public static final Supplier<ResourceKey<PlacedFeature>> DENSEWOOD_TREE_VARIANT_4 = registerPlacedFeature("densewood_tree_variant_4", CAConfiguredFeatures.DENSEWOOD_TREE_VARIANT_4, nbtTreePlacement(new BoundingBox(2, 0, 3, 7, 10, 8), 10, 10));
        public static final Supplier<ResourceKey<PlacedFeature>> DENSEWOOD_TREE_VARIANT_5 = registerPlacedFeature("densewood_tree_variant_5", CAConfiguredFeatures.DENSEWOOD_TREE_VARIANT_5, nbtTreePlacement(new BoundingBox(2, 0, 2, 7, 10, 7), 10, 9));

        public static final Supplier<ResourceKey<PlacedFeature>> TREES_MESOZOIC = registerPlacedFeature("trees_mesozoic", CAConfiguredFeatures.TREES_MESOZOIC, ObjectArrayList.of(PlacementUtils.countExtra(4, 0.1F, 1)));
        public static final Supplier<ResourceKey<PlacedFeature>> TREES_DENSEWOOD = registerPlacedFeature("trees_densewood", CAConfiguredFeatures.TREES_DENSEWOOD, ObjectArrayList.of(PlacementUtils.countExtra(3, 0.1F, 1)));
        public static final Supplier<ResourceKey<PlacedFeature>> TREES_GINKGO = registerPlacedFeature("trees_ginkgo", CAConfiguredFeatures.TREES_GINKGO, ObjectArrayList.of(PlacementUtils.countExtra(3, 0.1F, 1)));

        public static final Supplier<ResourceKey<PlacedFeature>> DREDGESTONE_URANIUM_ORE = registerPlacedFeature("dredgestone_uranium_ore", CAConfiguredFeatures.DREDGESTONE_URANIUM_ORE, commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(48))));
        public static final Supplier<ResourceKey<PlacedFeature>> GLOOMSTONE_URANIUM_ORE = registerPlacedFeature("gloomstone_uranium_ore", CAConfiguredFeatures.GLOOMSTONE_URANIUM_ORE, commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.absolute(-128), VerticalAnchor.absolute(-5))));

        public static final Supplier<ResourceKey<PlacedFeature>> DREDGESTONE_TITANIUM_ORE = registerPlacedFeature("dredgestone_titanium_ore", CAConfiguredFeatures.DREDGESTONE_TITANIUM_ORE, commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(48))));
        public static final Supplier<ResourceKey<PlacedFeature>> GLOOMSTONE_TITANIUM_ORE = registerPlacedFeature("gloomstone_titanium_ore", CAConfiguredFeatures.GLOOMSTONE_TITANIUM_ORE, commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.absolute(-128), VerticalAnchor.absolute(-5))));

        public static final Supplier<ResourceKey<PlacedFeature>> DREDGESTONE_SUNSTONE_ORE = registerPlacedFeature("dredgestone_sunstone_ore", CAConfiguredFeatures.DREDGESTONE_SUNSTONE_ORE, commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(48))));
        public static final Supplier<ResourceKey<PlacedFeature>> GLOOMSTONE_SUNSTONE_ORE = registerPlacedFeature("gloomstone_sunstone_ore", CAConfiguredFeatures.GLOOMSTONE_SUNSTONE_ORE, commonOrePlacement(13, HeightRangePlacement.triangle(VerticalAnchor.absolute(-128), VerticalAnchor.absolute(-5))));

        public static final Supplier<ResourceKey<PlacedFeature>> DREDGESTONE_RUBY_ORE = registerPlacedFeature("dredgestone_ruby_ore", CAConfiguredFeatures.DREDGESTONE_RUBY_ORE, commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(48))));
        public static final Supplier<ResourceKey<PlacedFeature>> GLOOMSTONE_RUBY_ORE = registerPlacedFeature("gloomstone_ruby_ore", CAConfiguredFeatures.GLOOMSTONE_RUBY_ORE, commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(-128), VerticalAnchor.absolute(-5))));

        public static final Supplier<ResourceKey<PlacedFeature>> DREDGESTONE_REDSTONE_ORE = registerPlacedFeature("dredgestone_redstone_ore", CAConfiguredFeatures.DREDGESTONE_REDSTONE_ORE, commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(48))));
        public static final Supplier<ResourceKey<PlacedFeature>> GLOOMSTONE_REDSTONE_ORE = registerPlacedFeature("gloomstone_redstone_ore", CAConfiguredFeatures.GLOOMSTONE_REDSTONE_ORE, commonOrePlacement(12, HeightRangePlacement.triangle(VerticalAnchor.absolute(-128), VerticalAnchor.absolute(-5))));

        public static final Supplier<ResourceKey<PlacedFeature>> DREDGESTONE_PLATINUM_ORE = registerPlacedFeature("dredgestone_platinum_ore", CAConfiguredFeatures.DREDGESTONE_PLATINUM_ORE, commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(48))));
        public static final Supplier<ResourceKey<PlacedFeature>> GLOOMSTONE_PLATINUM_ORE = registerPlacedFeature("gloomstone_platinum_ore", CAConfiguredFeatures.GLOOMSTONE_PLATINUM_ORE, commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-128), VerticalAnchor.absolute(-5))));

        public static final Supplier<ResourceKey<PlacedFeature>> DREDGESTONE_LAPIS_ORE = registerPlacedFeature("dredgestone_lapis_ore", CAConfiguredFeatures.DREDGESTONE_LAPIS_ORE, commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.absolute(1), VerticalAnchor.absolute(96))));
        public static final Supplier<ResourceKey<PlacedFeature>> GLOOMSTONE_LAPIS_ORE = registerPlacedFeature("gloomstone_lapis_ore", CAConfiguredFeatures.GLOOMSTONE_LAPIS_ORE, commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.absolute(-128), VerticalAnchor.absolute(-1))));

        public static final Supplier<ResourceKey<PlacedFeature>> DREDGESTONE_KUNZITE_ORE = registerPlacedFeature("dredgestone_kunzite_ore", CAConfiguredFeatures.DREDGESTONE_KUNZITE_ORE, commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(48))));
        public static final Supplier<ResourceKey<PlacedFeature>> GLOOMSTONE_KUNZITE_ORE = registerPlacedFeature("gloomstone_kunzite_ore", CAConfiguredFeatures.GLOOMSTONE_KUNZITE_ORE, commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-128), VerticalAnchor.absolute(-5))));

        public static final Supplier<ResourceKey<PlacedFeature>> DREDGESTONE_IRON_ORE = registerPlacedFeature("dredgestone_iron_ore", CAConfiguredFeatures.DREDGESTONE_IRON_ORE, commonOrePlacement(15, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(40))));
        public static final Supplier<ResourceKey<PlacedFeature>> GLOOMSTONE_IRON_ORE = registerPlacedFeature("gloomstone_iron_ore", CAConfiguredFeatures.GLOOMSTONE_IRON_ORE, commonOrePlacement(15, HeightRangePlacement.triangle(VerticalAnchor.absolute(-128), VerticalAnchor.absolute(-5))));

        public static final Supplier<ResourceKey<PlacedFeature>> DREDGESTONE_GOLD_ORE = registerPlacedFeature("dredgestone_gold_ore", CAConfiguredFeatures.DREDGESTONE_GOLD_ORE, commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.absolute(1), VerticalAnchor.absolute(64))));
        public static final Supplier<ResourceKey<PlacedFeature>> GLOOMSTONE_GOLD_ORE = registerPlacedFeature("gloomstone_gold_ore", CAConfiguredFeatures.GLOOMSTONE_GOLD_ORE, commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.absolute(-128), VerticalAnchor.absolute(-1))));

        public static final Supplier<ResourceKey<PlacedFeature>> DREDGESTONE_DIAMOND_ORE = registerPlacedFeature("dredgestone_diamond_ore", CAConfiguredFeatures.DREDGESTONE_DIAMOND_ORE, commonOrePlacement(9, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(48))));
        public static final Supplier<ResourceKey<PlacedFeature>> GLOOMSTONE_DIAMOND_ORE = registerPlacedFeature("gloomstone_diamond_ore", CAConfiguredFeatures.GLOOMSTONE_DIAMOND_ORE, commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-128), VerticalAnchor.absolute(-5))));

        public static final Supplier<ResourceKey<PlacedFeature>> DREDGESTONE_COPPER_ORE = registerPlacedFeature("dredgestone_copper_ore", CAConfiguredFeatures.DREDGESTONE_COPPER_ORE, commonOrePlacement(24, HeightRangePlacement.triangle(VerticalAnchor.absolute(1), VerticalAnchor.absolute(144))));
        public static final Supplier<ResourceKey<PlacedFeature>> GLOOMSTONE_COPPER_ORE = registerPlacedFeature("gloomstone_copper_ore", CAConfiguredFeatures.GLOOMSTONE_COPPER_ORE, commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(-1))));

        public static final Supplier<ResourceKey<PlacedFeature>> DREDGESTONE_COAL_ORE = registerPlacedFeature("dredgestone_coal_ore", CAConfiguredFeatures.DREDGESTONE_COAL_ORE, commonOrePlacement(40, HeightRangePlacement.triangle(VerticalAnchor.absolute(1), VerticalAnchor.absolute(352))));
        public static final Supplier<ResourceKey<PlacedFeature>> GLOOMSTONE_COAL_ORE = registerPlacedFeature("gloomstone_coal_ore", CAConfiguredFeatures.GLOOMSTONE_COAL_ORE, commonOrePlacement(26, HeightRangePlacement.triangle(VerticalAnchor.absolute(-20), VerticalAnchor.absolute(-1))));

        public static final Supplier<ResourceKey<PlacedFeature>> DREDGESTONE_ALUMINUM_ORE = registerPlacedFeature("dredgestone_aluminum_ore", CAConfiguredFeatures.DREDGESTONE_ALUMINUM_ORE, commonOrePlacement(30, HeightRangePlacement.triangle(VerticalAnchor.absolute(1), VerticalAnchor.absolute(144))));
        public static final Supplier<ResourceKey<PlacedFeature>> GLOOMSTONE_ALUMINUM_ORE = registerPlacedFeature("gloomstone_aluminum_ore", CAConfiguredFeatures.GLOOMSTONE_ALUMINUM_ORE, commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(-1))));


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
