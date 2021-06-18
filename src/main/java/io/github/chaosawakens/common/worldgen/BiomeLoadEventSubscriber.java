package io.github.chaosawakens.common.worldgen;

import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.registry.CABiomes;
import io.github.chaosawakens.common.registry.CAConfiguredFeatures;
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

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Class with method(s) that subscribe to the BiomeLoadingEvent
 * @author invalid2
 */
public class BiomeLoadEventSubscriber {
	
	public static void onBiomeLoadingEvent(final BiomeLoadingEvent event) {
		StructureHandler.addfeatures(event);
		StructureHandler.addStructureSpawns(event);
		MobSpawnHandler.addMobSpawns(event);
	}
	
	private static class MobSpawnHandler {
		
		// Mobs that appear on any biome, but only on the overworld
		private static final Consumer<MobSpawnInfoBuilder> OVERWORLD_MOBS = (builder) -> {
			builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.BROWN_ANT.get(), 20, 4, 6));
			builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.RAINBOW_ANT.get(), 20, 4, 6));
			builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.RED_ANT.get(), 20, 4, 6));
			builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.TERMITE.get(), 20, 4, 6));
			builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.UNSTABLE_ANT.get(), 20, 4, 6));
		};
		private static final Consumer<MobSpawnInfoBuilder> SWAMP_MOBS = (builder) -> {
			builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.RUBY_BUG.get(), 20, 3, 6));
			builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.EMERALD_GATOR.get(), 15, 1, 2));
			builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.STINK_BUG.get(), 30, 2, 5));
		};
		
		private static final Consumer<MobSpawnInfoBuilder> FOREST_MOBS = (builder) -> {
			builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.BEAVER.get(), 10, 1, 2));
			builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.STINK_BUG.get(), 20, 3, 5));
		};
		
		private static final Consumer<MobSpawnInfoBuilder> PLAINS_MOBS = (builder) -> {
			builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.APPLE_COW.get(), 7, 4, 4));
			builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.GOLDEN_APPLE_COW.get(), 4, 3, 3));
			builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW.get(), 1, 1, 1));
		};
		
		public static void addMobSpawns(BiomeLoadingEvent event) {
			MobSpawnInfoBuilder spawnInfoBuilder = event.getSpawns();
			RegistryKey<Biome> biome = RegistryKey.getOrCreateKey(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(event.getName(), "Who registered null name biome, naming criticism!"));
			
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
				case THEEND:
				case NETHER:
					break;
				default:
					if (!BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.RIVER))
						OVERWORLD_MOBS.accept(spawnInfoBuilder);
					break;
			}
		}
	}
	
	private static class StructureHandler {
		
		public static void addfeatures(BiomeLoadingEvent event) {
			BiomeGenerationSettingsBuilder gen = event.getGeneration();
			
			RegistryKey<Biome> biome = RegistryKey.getOrCreateKey(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(event.getName(), "Who registered null name biome, naming criticism!"));
			
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)) {
				gen.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.CORN_PATCH);
				gen.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.TOMATO_PATCH);
				if (CAConfig.COMMON.enableOreGen.get())
					addOverworldOres(gen);
			}

			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER)) {
				if (CAConfig.COMMON.enableOreGen.get())
					addNetherOres(gen);
			}
			
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST)) {
				gen.getStructures().add(() -> ConfiguredStructures.CONFIGURED_ENT_DUNGEON);
			}
			
			if (BiomeDictionary.hasType(biome, CABiomes.Type.MINING_DIMENSION)) {
				if (CAConfig.COMMON.enableOreGen.get())
					addMiningDimOres(gen);
				gen.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.CORN_PATCH);
			}
			
			if (BiomeDictionary.hasType(biome, CABiomes.Type.CRYSTAL_DIMENSION)) {
				gen.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.TREES_CRYSTAL_PLAINS);
				if (CAConfig.COMMON.enableOreGen.get())
					addCrystalDimOres(gen);
			}
		}
		
		public static void addStructureSpawns(BiomeLoadingEvent event) {
			BiomeGenerationSettingsBuilder builder = event.getGeneration();
			
			switch (event.getCategory()) {
				case FOREST:
					builder.withStructure(ConfiguredStructures.CONFIGURED_ENT_DUNGEON);
				default:
					break;
			}
		}
		
		private static void addOverworldOres(BiomeGenerationSettingsBuilder gen) {
			if (CAConfig.COMMON.enableOreRubyGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_RUBY_LAVA);
			if (CAConfig.COMMON.enableOreAmethystGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_AMETHYST);
			if (CAConfig.COMMON.enableOreUraniumGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_URANIUM);
			if (CAConfig.COMMON.enableOreTitaniumGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_TITANIUM);
			if (CAConfig.COMMON.enableOreTigersEyeGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_TIGERS_EYE);
			if (CAConfig.COMMON.enableOreSaltGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_SALT);
			if (CAConfig.COMMON.enableFossilGen.get()) {
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_ENT);
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_HERCULES_BEETLE);
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_RUBY_BUG);
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_EMERALD_GATOR);
			}
			if (CAConfig.COMMON.enableTrollOreGen.get()) {
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.RED_ANT_INFESTED);
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.TERMITE_INFESTED);
			}
			if (CAConfig.COMMON.spawnDzOresInOverworld.get() && CAConfig.COMMON.enableDzMineralOreGen.get()) {
				if (CAConfig.COMMON.enableOreCopperGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_COPPER);
				if (CAConfig.COMMON.enableOreTinGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_TIN);
				if (CAConfig.COMMON.enableOreSilverGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_SILVER);
				if (CAConfig.COMMON.enableOrePlatinumGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_PLATINUM);
				if (CAConfig.COMMON.enableOreSunstoneGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_SUNSTONE);
				if (CAConfig.COMMON.enableOreBloodstoneGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_BLOODSTONE);
			}
		}

		private static void addNetherOres(BiomeGenerationSettingsBuilder gen) {
			if (CAConfig.COMMON.enableOreRubyGen.get())
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.NETHER_ORE_RUBY_LAVA);
		}

		private static void addMiningDimOres(BiomeGenerationSettingsBuilder gen) {
			if (CAConfig.COMMON.enableOreRubyGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_RUBY_LAVA);
			if (CAConfig.COMMON.enableOreRubyGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_RUBY_NO_SURFACE);
			if (CAConfig.COMMON.enableOreAmethystGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_AMETHYST);
			if (CAConfig.COMMON.enableOreUraniumGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_URANIUM);
			if (CAConfig.COMMON.enableOreTitaniumGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_TITANIUM);
			if (CAConfig.COMMON.enableOreTigersEyeGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_TIGERS_EYE);
			if (CAConfig.COMMON.enableOreAluminumGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_ALUMINUM);
			if (CAConfig.COMMON.enableDzMineralOreGen.get()) {
				if (CAConfig.COMMON.enableOreCopperGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_COPPER);
				if (CAConfig.COMMON.enableOreTinGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_TIN);
				if (CAConfig.COMMON.enableOreSilverGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_SILVER);
				if (CAConfig.COMMON.enableOrePlatinumGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_PLATINUM);
				if (CAConfig.COMMON.enableOreSunstoneGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_SUNSTONE);
				if (CAConfig.COMMON.enableOreBloodstoneGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_BLOODSTONE);
			}
			if (CAConfig.COMMON.enableOreSaltGen.get()) gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_SALT);
			if (CAConfig.COMMON.enableFossilGen.get()) {
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_ENT);
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_HERCULES_BEETLE);
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_RUBY_BUG);
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_EMERALD_GATOR);
			}
			if (CAConfig.COMMON.enableTrollOreGen.get()) {
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_RED_ANT_INFESTED);
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_TERMITE_INFESTED);
			}
		}
		
		private static void addCrystalDimOres(BiomeGenerationSettingsBuilder gen) {
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.CRYSTAL_ORE_ENERGY);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.GEODE_CATS_EYE);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.GEODE_PINK_TOURMALINE);
		}
	}
	
}
