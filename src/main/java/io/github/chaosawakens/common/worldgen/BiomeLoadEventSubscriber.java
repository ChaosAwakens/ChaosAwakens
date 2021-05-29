package io.github.chaosawakens.common.worldgen;

import java.util.Objects;
import java.util.function.Consumer;

import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.registry.CABiomes;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAFeatures;
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
			builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.BROWN_ANT.get(), 20, 1, 5));
			builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.RAINBOW_ANT.get(), 20, 1, 5));
			builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.RED_ANT.get(), 20, 1, 5));
			builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.UNSTABLE_ANT.get(), 20, 1, 5));
			builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.TERMITE.get(), 20, 1, 5));
		};
		private static final Consumer<MobSpawnInfoBuilder> SWAMP_MOBS = (builder) -> {
			builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.RUBY_BUG.get(), 25, 3, 6));
			builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.EMERALD_GATOR.get(), 20, 1, 2));
		};
		
		private static final Consumer<MobSpawnInfoBuilder> FOREST_MOBS = (builder) -> {
			builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.BEAVER.get(), 15, 1, 2));
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
					SWAMP_MOBS.accept(spawnInfoBuilder);
				case PLAINS:
					if (!BiomeDictionary.hasType(biome, CABiomes.Type.CRYSTAL_DIMENSION))
						PLAINS_MOBS.accept(spawnInfoBuilder);
				case FOREST:
					if (!BiomeDictionary.hasType(biome, CABiomes.Type.CRYSTAL_DIMENSION))
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
				if (CAConfig.COMMON.enableOreGen.get())
					addOverworldOres(gen);
			}
			
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST)) {
				gen.getStructures().add(() -> ConfiguredStructures.CONFIGURED_ENT_DUNGEON);
			}
			
			if (BiomeDictionary.hasType(biome, CABiomes.Type.MINING_DIMENSION)) {
				if (CAConfig.COMMON.enableOreGen.get())
					addMiningDimOres(gen);
			}
			
			if (BiomeDictionary.hasType(biome, CABiomes.Type.CRYSTAL_DIMENSION)) {
				gen.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAFeatures.TREES_CRYSTAL_PLAINS);
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
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.ORE_RUBY_LAVA);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.ORE_AMETHYST);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.ORE_URANIUM);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.ORE_TITANIUM);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.ORE_TIGERS_EYE);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.ORE_SALT);
			if (CAConfig.COMMON.enableFossilGen.get()) {
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.FOSSILISED_ENT);
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.FOSSILISED_HERCULES_BEETLE);
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.FOSSILISED_RUBY_BUG);
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.FOSSILISED_EMERALD_GATOR);
			}
			if (CAConfig.COMMON.enableTrollOreGen.get()) {
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.RED_ANT_INFESTED);
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.TERMITE_INFESTED);
			}
		}
		
		private static void addMiningDimOres(BiomeGenerationSettingsBuilder gen) {
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.MINING_ORE_RUBY_LAVA);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.MINING_ORE_RUBY_NO_SURFACE);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.MINING_ORE_AMETHYST);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.MINING_ORE_URANIUM);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.MINING_ORE_TITANIUM);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.MINING_ORE_TIGERS_EYE);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.MINING_ORE_ALUMINUM);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.MINING_ORE_COPPER);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.MINING_ORE_TIN);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.MINING_ORE_SILVER);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.MINING_ORE_PLATINUM);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.MINING_ORE_SUNSTONE);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.MINING_ORE_BLOODSTONE);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.MINING_ORE_SALT);
			if (CAConfig.COMMON.enableFossilGen.get()) {
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.MINING_FOSSILISED_ENT);
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.MINING_FOSSILISED_HERCULES_BEETLE);
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.MINING_FOSSILISED_RUBY_BUG);
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.MINING_FOSSILISED_EMERALD_GATOR);
			}
			if (CAConfig.COMMON.enableTrollOreGen.get()) {
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.MINING_RED_ANT_INFESTED);
				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.MINING_TERMITE_INFESTED);
			}
		}
		
		private static void addCrystalDimOres(BiomeGenerationSettingsBuilder gen) {
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.CRYSTAL_ORE_ENERGY);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.GEODE_CATS_EYE);
			gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.GEODE_PINK_TOURMALINE);
		}
	}
	
}
