package io.github.chaosawakens.common.worldgen;

import java.util.Objects;
import java.util.function.Consumer;

import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.registry.CABiomes;
import io.github.chaosawakens.common.registry.CAConfiguredCarvers;
import io.github.chaosawakens.common.registry.CAConfiguredFeatures;
import io.github.chaosawakens.common.registry.CAConfiguredStructures;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeLoadEventSubscriber {
	public static void onBiomeLoadingEvent(final BiomeLoadingEvent event) {
		StructureHandler.addFeatures(event);
		MobSpawnHandler.addMobSpawns(event);
	}

	private static class MobSpawnHandler {
		// Mobs that appear in any biome, but only in the overworld
//		private static final Consumer<MobSpawnInfoBuilder> OVERWORLD_MOBS = (builder) -> { };

		private static final Consumer<MobSpawnInfoBuilder> SWAMP_MOBS = (builder) -> {
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.RUBY_BUG.get(), 20, 3, 6));
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.EMERALD_GATOR.get(), 15, 1, 2));
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.STINK_BUG.get(), 30, 2, 5));
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.BIRD.get(), 6, 2, 3));
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.RUBY_BUG.get(), 5, 2, 5));
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.EMERALD_GATOR.get(), 5, 2, 5));
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.TREE_FROG.get(), 25, 2, 6));
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.STINK_BUG.get(), 7, 1, 4));
		};

		private static final Consumer<MobSpawnInfoBuilder> OCEAN_MOBS = (builder) -> {
			builder.addSpawn(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.WHALE.get(), 1, 1, 2));
			builder.addSpawn(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.GREEN_FISH.get(), 10, 3, 5));
			builder.addSpawn(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.ROCK_FISH.get(), 14, 3, 5));
			builder.addSpawn(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.WOOD_FISH.get(), 20, 5, 8));
			builder.addSpawn(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.SPARK_FISH.get(), 15, 4, 6));
		};
		
		private static final Consumer<MobSpawnInfoBuilder> MOUNTAIN_MOBS = (builder) -> {
			builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(CAEntityTypes.THROWBACK_HERCULES_BEETLE.get(), 27, 1, 2));
		};
		
		private static final Consumer<MobSpawnInfoBuilder> FOREST_MOBS = (builder) -> {
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.BEAVER.get(), 10, 1, 2));
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.STINK_BUG.get(), 20, 3, 5));
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.BIRD.get(), 6, 2, 4));
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.LEAFY_CHICKEN.get(), 16, 4, 6));
		};

		private static final Consumer<MobSpawnInfoBuilder> PLAINS_MOBS = (builder) -> {
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.APPLE_COW.get(), 7, 4, 4));
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.GOLDEN_APPLE_COW.get(), 4, 3, 3));
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW.get(), 1, 1, 1));
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.CARROT_PIG.get(), 8, 3, 3));
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.GOLDEN_CARROT_PIG.get(), 5, 2, 2));
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.ENCHANTED_GOLDEN_CARROT_PIG.get(), 1, 1, 1));
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.BIRD.get(), 6, 1, 4));
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.GAZELLE.get(), 6, 1, 3));
		};

		private static final Consumer<MobSpawnInfoBuilder> CRYSTAL_WORLD_MOBS = (builder) -> {
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.CRYSTAL_APPLE_COW.get(), 7, 1, 4));
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.CRYSTAL_CARROT_PIG.get(), 4, 1, 3));
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.CRYSTAL_GATOR.get(), 5, 1, 4));
		};

		private static final Consumer<MobSpawnInfoBuilder> VILLAGE_MANIA_MOBS = (builder) -> {
			builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(CAEntityTypes.ROBO_POUNDER.get(), 20, 1, 2));
			builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(CAEntityTypes.ROBO_WARRIOR.get(), 55, 1, 4));
			builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(CAEntityTypes.ROBO_SNIPER.get(), 60, 1, 3));
		};
		
		private static final Consumer<MobSpawnInfoBuilder> STALAGMITE_VALLEY_MOBS = (builder) -> {
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.DIMETRODON.get(), 1, 1, 3));
			builder.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(CAEntityTypes.EMERALD_GATOR.get(), 1, 1, 4));
		};
		
		private static final Consumer<MobSpawnInfoBuilder> MESOZOIC_JUNGLE_MOBS = (builder) -> {
			builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.DIMETRODON.get(), 1, 1, 3));
			builder.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(CAEntityTypes.BIRD.get(), 1, 1, 4));
		};
		
		private static final Consumer<MobSpawnInfoBuilder> DENSE_MOUNTAINS_MOBS = (builder) -> {
			builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(CAEntityTypes.HERCULES_BEETLE.get(), 35, 1, 4));
		};

		private static final Consumer<MobSpawnInfoBuilder> NETHER_MOBS = (builder) -> {
			builder.addSpawn(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.LAVA_EEL.get(), 10, 1, 2));
		};

		private static final Consumer<MobSpawnInfoBuilder> BASALT_DELTA_MOBS = (builder) -> {
			builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(CAEntityTypes.TREE_FROG.get(), 25, 2, 6));
		};

		public static void addMobSpawns(BiomeLoadingEvent event) {
			MobSpawnInfoBuilder spawnInfoBuilder = event.getSpawns();
			RegistryKey<Biome> biome = RegistryKey.create(ForgeRegistries.Keys.BIOMES,
					Objects.requireNonNull(event.getName(), "Who registered null name biome, naming criticism!"));
			final String location = biome.location().toString();

			switch (event.getCategory()) {
				case SWAMP:
					if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD))
						SWAMP_MOBS.accept(spawnInfoBuilder);
				case PLAINS:
					if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD))
						PLAINS_MOBS.accept(spawnInfoBuilder);
				case FOREST:
					if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD))
						FOREST_MOBS.accept(spawnInfoBuilder);
				case OCEAN:
					if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN))
						OCEAN_MOBS.accept(spawnInfoBuilder);
				case THEEND:
				case NETHER:
					if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.HOT) && BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER))
						NETHER_MOBS.accept(spawnInfoBuilder);
					if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER) && location.contains("basalt"))
						BASALT_DELTA_MOBS.accept(spawnInfoBuilder);
					break;
				default:
					if (BiomeDictionary.hasType(biome, CABiomes.Type.CRYSTAL_WORLD))
						CRYSTAL_WORLD_MOBS.accept(spawnInfoBuilder);
					if (BiomeDictionary.hasType(biome, CABiomes.Type.VILLAGE_MANIA))
						VILLAGE_MANIA_MOBS.accept(spawnInfoBuilder);
					if (BiomeDictionary.hasType(biome, CABiomes.Type.STALAGMITE_VALLEY))
						STALAGMITE_VALLEY_MOBS.accept(spawnInfoBuilder);
					if (BiomeDictionary.hasType(biome, CABiomes.Type.DENSE_MOUNTAINS))
						DENSE_MOUNTAINS_MOBS.accept(spawnInfoBuilder);
					if (BiomeDictionary.hasType(biome, CABiomes.Type.MESOZOIC_JUNGLE))
						MESOZOIC_JUNGLE_MOBS.accept(spawnInfoBuilder);
					if ((BiomeDictionary.hasType(biome, BiomeDictionary.Type.MOUNTAIN) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.SAVANNA) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.MESA) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE)) || (BiomeDictionary.hasType(biome, BiomeDictionary.Type.MOUNTAIN) && BiomeDictionary.hasType(biome, BiomeDictionary.Type.MODIFIED) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.SAVANNA) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.MESA) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE)) || (BiomeDictionary.hasType(biome, BiomeDictionary.Type.MOUNTAIN) && BiomeDictionary.hasType(biome, BiomeDictionary.Type.SNOWY) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.SAVANNA) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.MESA) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE)))
						MOUNTAIN_MOBS.accept(spawnInfoBuilder);
//					if (!BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.RIVER)) OVERWORLD_MOBS.accept(spawnInfoBuilder);
			}
		}
	}

	private static class StructureHandler {
		public static void addFeatures(BiomeLoadingEvent event) {
			BiomeGenerationSettingsBuilder gen = event.getGeneration();

			RegistryKey<Biome> biome = RegistryKey.create(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(event.getName(), "Who registered null name biome, naming criticism!"));
			final String location = biome.location().toString();

			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)) {
				if (CACommonConfig.COMMON.enableOreGen.get())
					addOverworldOres(gen);
			}
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)
					&& !BiomeDictionary.hasType(biome, BiomeDictionary.Type.MUSHROOM)) {
				gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.CHAOS_FLOWER_DEFAULT);
			}
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)
					&& BiomeDictionary.hasType(biome, BiomeDictionary.Type.MOUNTAIN)
					&& !BiomeDictionary.hasType(biome, BiomeDictionary.Type.SAVANNA)
					&& !BiomeDictionary.hasType(biome, BiomeDictionary.Type.MESA)
					&& !BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE)) {
				if (!location.contains("birch") && !location.contains("dark")) {
					if (CACommonConfig.COMMON.enableOreGen.get())
						addMountainBiomeOres(gen);
					if (CACommonConfig.COMMON.generateSpruceEntTree.get())
						gen.getStructures().add(() -> CAConfiguredStructures.CONFIGURED_SPRUCE_ENT_TREE);
				}
			}
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)
					&& BiomeDictionary.hasType(biome, BiomeDictionary.Type.PLAINS)
					&& !BiomeDictionary.hasType(biome, BiomeDictionary.Type.SAVANNA)) {
				gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.CORN_PATCH);
				gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.TOMATO_PATCH);
				if (CACommonConfig.COMMON.enableOreGen.get())
					addPlainsBiomeOres(gen);
				if (CACommonConfig.COMMON.generateOakEntTree.get())
					gen.getStructures().add(() -> CAConfiguredStructures.CONFIGURED_OAK_ENT_TREE);
			}
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)
					&& BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST)) {
				if (CACommonConfig.COMMON.enableOreGen.get())
					addForestBiomeOres(gen);
				if (CACommonConfig.COMMON.generateWaspNest.get())
					gen.getStructures().add(() -> CAConfiguredStructures.CONFIGURED_WASP_DUNGEON);
			}
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)
					&& BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST)
					&& !BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE)
					&& !BiomeDictionary.hasType(biome, BiomeDictionary.Type.MOUNTAIN)) {
				if (!location.contains("birch") && !location.contains("dark") && !location.contains("jungle") && !location.contains("taiga")) {
					gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.PATCH_STRAWBERRY_BUSH);
					gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.PATCH_STRAWBERRY_SPARSE);
					gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.PATCH_STRAWBERRY_DECORATED);
					if (CACommonConfig.COMMON.enableOreGen.get())
						addOakForestBiomeOres(gen);
					if (CACommonConfig.COMMON.generateOakEntTree.get())
						gen.getStructures().add(() -> CAConfiguredStructures.CONFIGURED_OAK_ENT_TREE);
				}
			}
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)
					&& location.contains("birch")) {
				if (CACommonConfig.COMMON.enableOreGen.get())
					addBirchBiomeOres(gen);
				if (CACommonConfig.COMMON.generateBirchEntTree.get())
					gen.getStructures().add(() -> CAConfiguredStructures.CONFIGURED_BIRCH_ENT_TREE);
			}
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)
					&& location.contains("taiga")) {
				if (CACommonConfig.COMMON.enableOreGen.get())
					addTaigaBiomeOres(gen);
				if (CACommonConfig.COMMON.generateSpruceEntTree.get())
					gen.getStructures().add(() -> CAConfiguredStructures.CONFIGURED_SPRUCE_ENT_TREE);
			}
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)
					&& location.contains("dark")) {
				if (CACommonConfig.COMMON.enableOreGen.get())
					addDarkForestBiomeOres(gen);
				if (CACommonConfig.COMMON.generateDarkOakEntTree.get())
					gen.getStructures().add(() -> CAConfiguredStructures.CONFIGURED_DARK_OAK_ENT_TREE);
			}
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)
					&& BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE)) {
				if (CACommonConfig.COMMON.enableOreGen.get())
					addJungleBiomeOres(gen);
				if (CACommonConfig.COMMON.generateJungleEntTree.get())
					gen.getStructures().add(() -> CAConfiguredStructures.CONFIGURED_JUNGLE_ENT_TREE);
			}
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)
					&& BiomeDictionary.hasType(biome, BiomeDictionary.Type.SAVANNA)) {
				if (CACommonConfig.COMMON.enableOreGen.get())
					addSavannaBiomeOres(gen);
				if (CACommonConfig.COMMON.generateWaspNest.get())
					gen.getStructures().add(() -> CAConfiguredStructures.CONFIGURED_WASP_DUNGEON);
				if (CACommonConfig.COMMON.generateAcaciaEntTree.get())
					gen.getStructures().add(() -> CAConfiguredStructures.CONFIGURED_ACACIA_ENT_TREE);
			}
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)
					&& BiomeDictionary.hasType(biome, BiomeDictionary.Type.SWAMP)) {
				if (CACommonConfig.COMMON.enableOreGen.get())
					addSwampBiomeOres(gen);
				if (CACommonConfig.COMMON.generateWaspNest.get())
					gen.getStructures().add(() -> CAConfiguredStructures.CONFIGURED_WASP_DUNGEON);
			}
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)
					&& BiomeDictionary.hasType(biome, BiomeDictionary.Type.SNOWY)) {
				if (CACommonConfig.COMMON.enableOreGen.get())
					addSnowyBiomeOres(gen);
			}
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)
					&& BiomeDictionary.hasType(biome, BiomeDictionary.Type.HOT)
					&& BiomeDictionary.hasType(biome, BiomeDictionary.Type.DRY)
					&& BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY)
					&& BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)) {
				if (CACommonConfig.COMMON.enableOreGen.get())
					addDesertBiomeOres(gen);
			}
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)
					&& BiomeDictionary.hasType(biome, BiomeDictionary.Type.PLAINS)
					|| BiomeDictionary.hasType(biome, BiomeDictionary.Type.SNOWY)
					|| location.contains("taiga") || location.contains("desert")
					|| BiomeDictionary.hasType(biome, BiomeDictionary.Type.SAVANNA)) {
				if (CACommonConfig.COMMON.enableOreGen.get())
					addVillageBiomeOres(gen);
			}
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)
					&& (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN)
					|| BiomeDictionary.hasType(biome, BiomeDictionary.Type.RIVER))) {
				if (CACommonConfig.COMMON.enableOreGen.get())
					addRiverOceanBiomeOres(gen);
			}
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)
					&& BiomeDictionary.hasType(biome, BiomeDictionary.Type.MUSHROOM)) {
				if (CACommonConfig.COMMON.enableOreGen.get())
					addMushroomBiomeOres(gen);
			}
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER)) {
				if (CACommonConfig.COMMON.enableOreGen.get())
					addNetherOres(gen);
				if (location.contains("crimson")) {
					if (CACommonConfig.COMMON.enableOreGen.get())
						addCrimsonNetherOres(gen);
					if (CACommonConfig.COMMON.generateCrimsonEntTree.get())
						gen.getStructures().add(() -> CAConfiguredStructures.CONFIGURED_CRIMSON_ENT_TREE);
				}
				if (location.contains("warped")) {
					if (CACommonConfig.COMMON.enableOreGen.get())
						addWarpedNetherOres(gen);
					if (CACommonConfig.COMMON.generateWarpedEntTree.get())
						gen.getStructures().add(() -> CAConfiguredStructures.CONFIGURED_WARPED_ENT_TREE);
				}
			}
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.END)) {
				if (CACommonConfig.COMMON.enableOreGen.get())
					addEndOres(gen);
			}
			if ((BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST)
					&& (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)))) {
				gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.TREES_APPLE);
				gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.TREES_CHERRY);
				gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.TREES_PEACH);
			}
			if (BiomeDictionary.hasType(biome, CABiomes.Type.MINING_PARADISE)) {
				if (CACommonConfig.COMMON.enableOreGen.get()) {
					addMiningParadiseOres(gen);
					if (BiomeDictionary.hasType(biome, CABiomes.Type.STALAGMITE_VALLEY))
						addStalagmiteValleyOres(gen);
				}
				
				if (BiomeDictionary.hasType(biome, CABiomes.Type.STALAGMITE_VALLEY)) {
					addStalagmiteValleyFeatures(gen);
					//if (CACommonConfig.COMMON.generateWaspNest.get())					
					gen.getStructures().add(() -> CAConfiguredStructures.CONFIGURED_MINING_WASP_DUNGEON);
				}
				
				if (BiomeDictionary.hasType(biome, CABiomes.Type.DENSE_GINKGO_FOREST)) {
					gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.DENSE_BULB_DEFAULT);
					gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.TREES_DENSE_GINKGO);
					gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.PATCH_DENSE_FLOWER);
				}
				
				if (BiomeDictionary.hasType(biome, CABiomes.Type.DENSE_PLAINS)) {
					gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.TREES_DENSE_PLAINS);
				}
				
				if (BiomeDictionary.hasType(biome, CABiomes.Type.DENSE_FOREST)) {
					gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.DENSE_BULB_DEFAULT);
					gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.TREES_DENSE_FOREST);
				}
				
				if(BiomeDictionary.hasType(biome, CABiomes.Type.DENSE_MOUNTAINS)) {
					gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.PATCH_THORNY_SUN);
					gen.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, CAConfiguredFeatures.MOUNTAINS_STALAGMITE);
				}
				
				if (BiomeDictionary.hasType(biome, CABiomes.Type.MESOZOIC_JUNGLE)) {
					gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.TREES_MESOZOIC);
					gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.BLOB_TERRA_PRETA);
					gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.BLOB_LATOSOL);
					gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.BLOB_TAR);
					gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.PATCH_MESO_PLANTS);
				}
				gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.PATCH_DENSE_GRASS);
				gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.PATCH_ALSTROEMERIAT);
				addGeneralRockGeneration(gen);
			}

			if (BiomeDictionary.hasType(biome, CABiomes.Type.VILLAGE_MANIA)) {
				if (BiomeDictionary.hasType(biome, CABiomes.Type.VILLAGE_PLAINS)) {
					gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.CORN_PATCH);
					gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.TOMATO_PATCH);
					gen.getStructures().add(() -> CAConfiguredStructures.CONFIGURED_OAK_ENT_TREE);
				}
				if (CACommonConfig.COMMON.enableOreGen.get())
					addVillageManiaOres(gen);
				
				if(BiomeDictionary.hasType(biome, CABiomes.Type.VILLAGE_SNOWY) || BiomeDictionary.hasType(biome, CABiomes.Type.VILLAGE_TAIGA))
					gen.getStructures().add(() -> CAConfiguredStructures.CONFIGURED_SPRUCE_ENT_TREE);
				if(BiomeDictionary.hasType(biome, CABiomes.Type.VILLAGE_SAVANNA))
					gen.getStructures().add(() -> CAConfiguredStructures.CONFIGURED_ACACIA_ENT_TREE);
				
				gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.TREES_APPLE);
				gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.TREES_CHERRY);
				gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.TREES_PEACH);
				gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.CHAOS_FLOWER_DEFAULT);
			}
			if (BiomeDictionary.hasType(biome, CABiomes.Type.CRYSTAL_WORLD)) {
				if (CACommonConfig.COMMON.enableOreGen.get())
					addCrystalWorldOres(gen);
				gen.addCarver(GenerationStage.Carving.AIR, CAConfiguredCarvers.CRYSTAL_CAVE);
				gen.addCarver(GenerationStage.Carving.AIR, CAConfiguredCarvers.CRYSTAL_CANYON);
				gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.TREES_CRYSTAL_PLAINS);
				gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.PATCH_CRYSTAL_GRASS);
				gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.PATCH_TALL_CRYSTAL_GRASS);
				gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.CRYSTAL_FLOWER_DEFAULT);
				gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.CRYSTAL_GROWTH_DEFAULT);
			}
			if (BiomeDictionary.hasType(biome, CABiomes.Type.DANGER_ISLES)) {
				if (CACommonConfig.COMMON.enableOreGen.get())
					addDangerIslesOres(gen);
			}
		}

		private static void addOverworldOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableOreRubyGen.get())
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_RUBY_LAVA);
			if (CACommonConfig.COMMON.enableOreAmethystGen.get())
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_AMETHYST);
			if (CACommonConfig.COMMON.enableOreUraniumGen.get())
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_URANIUM);
			if (CACommonConfig.COMMON.enableOreTitaniumGen.get())
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_TITANIUM);
			if (CACommonConfig.COMMON.enableOreTigersEyeGen.get())
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_TIGERS_EYE);
			if (CACommonConfig.COMMON.enableOreSaltGen.get())
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_SALT);
			if (CACommonConfig.COMMON.enableFossilGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_HERCULES_BEETLE);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_WTF);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_PIRAPORU);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_APPLE_COW);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_GOLDEN_APPLE_COW);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_CARROT_PIG);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_GOLDEN_CARROT_PIG);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_LETTUCE_CHICKEN);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_BIRD);

				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_BAT);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_BEE);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_CAVE_SPIDER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_CHICKEN);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_COW);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_CREEPER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_DONKEY);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_ENDERMAN);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_HORSE);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_PHANTOM);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_PIG);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_RABBIT);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_SHEEP);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_SKELETON);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_SKELETON_HORSE);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_SPIDER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_WANDERING_TRADER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_WITCH);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_ZOMBIE);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_ZOMBIE_HORSE);
			}
			if (CACommonConfig.COMMON.enableTrollOreGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.RED_ANT_INFESTED);
			}
			if (CACommonConfig.COMMON.enableMarbleGenInOverworld.get() && CACommonConfig.COMMON.enableMarbleGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, CAConfiguredFeatures.MINING_MARBLE_CAVE_PATCH);
			}
			if (CACommonConfig.COMMON.enableLimestoneGenInOverworld.get() && CACommonConfig.COMMON.enableLimestoneGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, CAConfiguredFeatures.MINING_LIMESTONE_CAVE_PATCH);
			}
			if (CACommonConfig.COMMON.enableRhinestoneGenInOverworld.get() && CACommonConfig.COMMON.enableRhinestoneGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, CAConfiguredFeatures.MINING_RHINESTONE_CAVE_PATCH);
			}
			if (CACommonConfig.COMMON.spawnDzOresInOverworld.get() && CACommonConfig.COMMON.enableDzMineralOreGen.get()) {
				if (CACommonConfig.COMMON.enableOreCopperGen.get())
					gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_COPPER);
				if (CACommonConfig.COMMON.enableOreTinGen.get())
					gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_TIN);
				if (CACommonConfig.COMMON.enableOreSilverGen.get())
					gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_SILVER);
				if (CACommonConfig.COMMON.enableOrePlatinumGen.get())
					gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_PLATINUM);
				if (CACommonConfig.COMMON.enableOreSunstoneGen.get())
					gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_SUNSTONE);
				if (CACommonConfig.COMMON.enableOreBloodstoneGen.get())
					gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_BLOODSTONE);
			}
			if (CACommonConfig.COMMON.enableNestGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.BROWN_ANT_NEST);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.RAINBOW_ANT_NEST);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.RED_ANT_NEST);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.UNSTABLE_ANT_NEST);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.TERMITE_NEST);
			}
		}

		private static void addMountainBiomeOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableTrollOreGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.TERMITE_INFESTED);
			}
			if (CACommonConfig.COMMON.enableFossilGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_SPRUCE_ENT);

				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_LLAMA);
			}
		}

		private static void addPlainsBiomeOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableFossilGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_OAK_ENT);
				
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_BEAVER);
			}
		}

		private static void addForestBiomeOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableFossilGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_WASP);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_LETTUCE_CHICKEN);
			}
		}

		private static void addOakForestBiomeOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableFossilGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_OAK_ENT);

				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_WOLF);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_BEAVER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_LETTUCE_CHICKEN);
			}
		}

		private static void addBirchBiomeOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableFossilGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_BIRCH_ENT);
				
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_BEAVER);
			}
		}

		private static void addTaigaBiomeOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableFossilGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_SPRUCE_ENT);

				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_FOX);
			}
		}

		private static void addDarkForestBiomeOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableFossilGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_DARK_OAK_ENT);

				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_EVOKER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_ILLUSIONER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_PILLAGER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_VINDICATOR);
				
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_BEAVER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_LETTUCE_CHICKEN);
			}
		}

		private static void addJungleBiomeOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableFossilGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_JUNGLE_ENT);

				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_OCELOT);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_PANDA);
			}
		}

		private static void addSavannaBiomeOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableFossilGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_ACACIA_ENT);
			}
		}

		private static void addSwampBiomeOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableFossilGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_EMERALD_GATOR);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_BEAVER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_RUBY_BUG);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_SLIME);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_WASP);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_FROG);
			}
		}

		private static void addSnowyBiomeOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableFossilGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FROZEN_POLAR_BEAR);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FROZEN_SNOW_GOLEM);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FROZEN_STRAY);
			}
		}

		private static void addDesertBiomeOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableFossilGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_HUSK);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_HUSK_SANDSTONE);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_SCORPION);
			}
		}

		private static void addVillageBiomeOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableFossilGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_IRON_GOLEM);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_PILLAGER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_RAVAGER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_VILLAGER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_VINDICATOR);
			}
		}

		private static void addRiverOceanBiomeOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableFossilGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_COD);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_DROWNED);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_DOLPHIN);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_GUARDIAN);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_PUFFERFISH);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_SALMON);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_SQUID);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_TROPICAL_FISH);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_TURTLE);

				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_GREEN_FISH);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_ROCK_FISH);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_SPARK_FISH);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_WOOD_FISH);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_WHALE);
			}
		}

		private static void addMushroomBiomeOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableFossilGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_MOOSHROOM);
			}
		}

		private static void addNetherOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableOreRubyGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.NETHERRACK_ORE_RUBY_LAVA);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.NETHERRACK_ORE_RUBY);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.BLACKSTONE_ORE_RUBY);
			}
			if (CACommonConfig.COMMON.enableFossilGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.NETHER_FOSSILISED_LAVA_EEL);

				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.NETHER_FOSSILISED_BLAZE);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.NETHER_FOSSILISED_GHAST);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.NETHER_FOSSILISED_HOGLIN);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.NETHER_FOSSILISED_ENDERMAN_NETHERRACK);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.NETHER_FOSSILISED_MAGMA_CUBE_NETHERRACK);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.NETHER_FOSSILISED_MAGMA_CUBE_BLACKSTONE);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.NETHER_FOSSILISED_PIGLIN);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.NETHER_FOSSILISED_SKELETON_SOUL_SOIL);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.NETHER_FOSSILISED_STRIDER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.NETHER_FOSSILISED_WITHER_SKELETON);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.NETHER_FOSSILISED_ZOMBIFIED_PIGLIN);
			}
		}

		private static void addCrimsonNetherOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableFossilGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.NETHER_FOSSILISED_CRIMSON_ENT);
			}
		}

		private static void addWarpedNetherOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableFossilGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.NETHER_FOSSILISED_WARPED_ENT);
			}
		}

		private static void addEndOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableFossilGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.END_FOSSILISED_ENDERMAN_END_STONE);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.END_FOSSILISED_ENDERMITE);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.END_FOSSILISED_SHULKER);
			}
		}

		private static void addMiningParadiseOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableOreRubyGen.get())
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_RUBY_LAVA);
			if (CACommonConfig.COMMON.enableOreAmethystGen.get())
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_AMETHYST);
			if (CACommonConfig.COMMON.enableOreUraniumGen.get())
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_URANIUM);
			if (CACommonConfig.COMMON.enableOreTitaniumGen.get())
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_TITANIUM);
			if (CACommonConfig.COMMON.enableOreTigersEyeGen.get())
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_TIGERS_EYE);
			if (CACommonConfig.COMMON.enableOreAluminumGen.get())
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_ALUMINUM);
			if (CACommonConfig.COMMON.enableDzMineralOreGen.get()) {
				if (CACommonConfig.COMMON.enableOreCopperGen.get())
					gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_COPPER);
				if (CACommonConfig.COMMON.enableOreTinGen.get())
					gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_TIN);
				if (CACommonConfig.COMMON.enableOreSilverGen.get())
					gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_SILVER);
				if (CACommonConfig.COMMON.enableOrePlatinumGen.get())
					gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_PLATINUM);
				if (CACommonConfig.COMMON.enableOreSunstoneGen.get())
					gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_SUNSTONE);
				if (CACommonConfig.COMMON.enableOreBloodstoneGen.get())
					gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_BLOODSTONE);
			}
			if (CACommonConfig.COMMON.enableOreSaltGen.get())
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_SALT);
			if (CACommonConfig.COMMON.enableFossilGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_HERCULES_BEETLE);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_BEAVER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_RUBY_BUG);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_EMERALD_GATOR);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_WTF);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_SCORPION);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_WASP);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_PIRAPORU);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_APPLE_COW);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_GOLDEN_APPLE_COW);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_CARROT_PIG);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_GOLDEN_CARROT_PIG);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_LETTUCE_CHICKEN);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_BIRD);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_FROG);

				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_BAT);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_BEE);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_CAVE_SPIDER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_CHICKEN);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_COW);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_CREEPER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_DONKEY);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_ENDERMAN);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_EVOKER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_FOX);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_GIANT);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_ILLUSIONER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_IRON_GOLEM);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_LLAMA);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_MOOSHROOM);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_OCELOT);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_PANDA);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_PIG);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_PILLAGER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_RABBIT);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_RAVAGER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_SHEEP);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_SKELETON);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_SKELETON_HORSE);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_SLIME);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_SPIDER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_VILLAGER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_VINDICATOR);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_WANDERING_TRADER);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_WITCH);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_WOLF);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_ZOMBIE);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_ZOMBIE_HORSE);
			}
			if (CACommonConfig.COMMON.enableTrollOreGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_RED_ANT_INFESTED);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_TERMITE_INFESTED);
			}
			if (CACommonConfig.COMMON.enableNestGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.DENSE_RED_ANT_NEST);
			}
		}

		private static void addGeneralRockGeneration(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableMarbleGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, CAConfiguredFeatures.MINING_MARBLE_CAVE_PATCH);
			}
			if (CACommonConfig.COMMON.enableLimestoneGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, CAConfiguredFeatures.MINING_LIMESTONE_CAVE_PATCH);
			}
			if (CACommonConfig.COMMON.enableRhinestoneGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, CAConfiguredFeatures.MINING_RHINESTONE_CAVE_PATCH);
			}
		}

		private static void addStalagmiteValleyOres(BiomeGenerationSettingsBuilder gen) {
			gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_DIMETRODON);
		}
		
		private static void addStalagmiteValleyFeatures(BiomeGenerationSettingsBuilder gen) {
			gen.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, CAConfiguredFeatures.STALAGMITE_COMMON);
			gen.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, CAConfiguredFeatures.STALAGMITE_COMMON_SHORT);
			gen.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, CAConfiguredFeatures.STALAGMITE_SKYSCRAPER);
			gen.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, CAConfiguredFeatures.STALAGMITE_CONE);
			
			gen.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, CAConfiguredFeatures.STALAGMITE_COMMON_LIMESTONE);
		}

		private static void addVillageManiaOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableNestGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.RAINBOW_ANT_NEST);
			}
		}

		private static void addCrystalWorldOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableOreGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.CRYSTAL_ORE_ENERGY);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.GEODE_CATS_EYE);
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.GEODE_PINK_TOURMALINE);
				if (CACommonConfig.COMMON.enableFossilGen.get()) {
					gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.CRYSTAL_FOSSILISED_CRYSTAL_APPLE_COW);
					gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.CRYSTAL_FOSSILISED_CRYSTAL_CARROT_PIG);
				}
			}
			if (CACommonConfig.COMMON.enableNestGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.CRYSTAL_TERMITE_NEST);
			}
		}

		private static void addDangerIslesOres(BiomeGenerationSettingsBuilder gen) {
			if (CACommonConfig.COMMON.enableNestGen.get()) {
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.UNSTABLE_ANT_NEST);
			}
		}

//		private static void addUtopiaKingdomOres(BiomeGenerationSettingsBuilder gen) {
//			if (CAConfig.COMMON.enableNestGen.get()) {
//				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.BROWN_ANT_NEST);
//			}
//		}
	}
}
