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
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Class with method(s) that subscribe to the BiomeLoadingEvent
 *
 * @author invalid2
 */
public class BiomeLoadEventSubscriber {

    public static void onBiomeLoadingEvent(final BiomeLoadingEvent event) {
        StructureHandler.addfeatures(event);
        StructureHandler.addStructureSpawns(event);
        MobSpawnHandler.addMobSpawns(event);
    }

    public static void setEntDungeon(Function<StructureFeature<?, ?>, ?> func, BiomeLoadingEvent event) {
        RegistryKey<Biome> biome = RegistryKey.create(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(event.getName(), "Who registered null name biome, naming criticism!"));

        final String location = biome.location().toString();

        if (location.contains("birch")) {
            func.apply(ConfiguredStructures.CONFIGURED_BIRCH_ENT_TREE);
            return;
        }
        if (location.contains("dark")) {
            func.apply(ConfiguredStructures.CONFIGURED_DARK_OAK_ENT_TREE);
            return;
        }
        if (location.contains("crimson")) {
            func.apply(ConfiguredStructures.CONFIGURED_CRIMSON_ENT_TREE);
            return;
        }
        if (location.contains("warped")) {
            func.apply(ConfiguredStructures.CONFIGURED_WARPED_ENT_TREE);
            return;
        }

        switch (event.getCategory()) {
            case JUNGLE:
                func.apply(ConfiguredStructures.CONFIGURED_JUNGLE_ENT_TREE);
                break;
            case SAVANNA:
                func.apply(ConfiguredStructures.CONFIGURED_ACACIA_ENT_TREE);
                break;
            case TAIGA:
            case EXTREME_HILLS:
                func.apply(ConfiguredStructures.CONFIGURED_SPRUCE_ENT_TREE);
                break;
            case FOREST:
            case PLAINS:
                func.apply(ConfiguredStructures.CONFIGURED_OAK_ENT_TREE);
                break;
            default:
                break;
        }
    }

    private static class MobSpawnHandler {

        // Mobs that appear on any biome, but only on the overworld
//		private static final Consumer<MobSpawnInfoBuilder> OVERWORLD_MOBS = (builder) -> {
//		};
        private static final Consumer<MobSpawnInfoBuilder> SWAMP_MOBS = (builder) -> {
            builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.RUBY_BUG.get(), 20, 3, 6));
            builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.EMERALD_GATOR.get(), 15, 1, 2));
            builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.STINK_BUG.get(), 30, 2, 5));
        };

        private static final Consumer<MobSpawnInfoBuilder> FOREST_MOBS = (builder) -> {
            builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.BEAVER.get(), 10, 1, 2));
            builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.STINK_BUG.get(), 20, 3, 5));
        };

        private static final Consumer<MobSpawnInfoBuilder> PLAINS_MOBS = (builder) -> {
            builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.APPLE_COW.get(), 7, 4, 4));
            builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.GOLDEN_APPLE_COW.get(), 4, 3, 3));
            builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW.get(), 1, 1, 1));
        };

        public static void addMobSpawns(BiomeLoadingEvent event) {
            MobSpawnInfoBuilder spawnInfoBuilder = event.getSpawns();
            RegistryKey<Biome> biome = RegistryKey.create(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(event.getName(), "Who registered null name biome, naming criticism!"));

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
//						OVERWORLD_MOBS.accept(spawnInfoBuilder);
                        break;
            }
        }
    }

    private static class StructureHandler {

        public static void addfeatures(BiomeLoadingEvent event) {
            BiomeGenerationSettingsBuilder gen = event.getGeneration();

            RegistryKey<Biome> biome = RegistryKey.create(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(event.getName(), "Who registered null name biome, naming criticism!"));

            BiomeLoadEventSubscriber.setEntDungeon((struct) -> gen.getStructures().add(() -> struct), event);

            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)) {
                gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.CORN_PATCH);
                gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.TOMATO_PATCH);
                if (CAConfig.COMMON.enableOreGen.get())
                    addOverworldOres(gen);
            }
            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.MOUNTAIN)) {
                if (CAConfig.COMMON.enableOreGen.get())
                    addMountainOres(gen);
            }

            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.PLAINS)) {
                gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.TREES_PEACH);
            }

            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER)) {
                if (CAConfig.COMMON.enableOreGen.get())
                    addNetherOres(gen);
            }

            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST)) {
                gen.getStructures().add(() -> ConfiguredStructures.CONFIGURED_WASP_DUNGEON);
            }

            if (BiomeDictionary.hasType(biome, CABiomes.Type.MINING_DIMENSION)) {
                if (CAConfig.COMMON.enableOreGen.get())
                    addMiningDimOres(gen);
                gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.CORN_PATCH);
            }

            if (BiomeDictionary.hasType(biome, CABiomes.Type.VILLAGE_DIMENSION)) {
                if (CAConfig.COMMON.enableOreGen.get())
                    addVillageDimOres(gen);
                gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.CORN_PATCH);
            }

            if (BiomeDictionary.hasType(biome, CABiomes.Type.CRYSTAL_DIMENSION)) {
                gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CAConfiguredFeatures.TREES_CRYSTAL_PLAINS);
                if (CAConfig.COMMON.enableOreGen.get())
                    addCrystalDimOres(gen);
            }
        }

        public static void addStructureSpawns(BiomeLoadingEvent event) {
            BiomeGenerationSettingsBuilder builder = event.getGeneration();

            BiomeLoadEventSubscriber.setEntDungeon((struct) -> builder.addStructureStart(struct), event);

            switch (event.getCategory()) {
                case FOREST:
                    builder.addStructureStart(ConfiguredStructures.CONFIGURED_WASP_DUNGEON);
                case SWAMP:
                    builder.addStructureStart(ConfiguredStructures.CONFIGURED_WASP_DUNGEON);
                default:
                    break;
            }
        }

        private static void addOverworldOres(BiomeGenerationSettingsBuilder gen) {
            if (CAConfig.COMMON.enableOreRubyGen.get())
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_RUBY_LAVA);
            if (CAConfig.COMMON.enableOreAmethystGen.get())
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_AMETHYST);
            if (CAConfig.COMMON.enableOreUraniumGen.get())
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_URANIUM);
            if (CAConfig.COMMON.enableOreTitaniumGen.get())
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_TITANIUM);
            if (CAConfig.COMMON.enableOreTigersEyeGen.get())
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_TIGERS_EYE);
            if (CAConfig.COMMON.enableOreSaltGen.get())
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_SALT);
            if (CAConfig.COMMON.enableFossilGen.get()) {
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_ENT);
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_HERCULES_BEETLE);
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_RUBY_BUG);
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_EMERALD_GATOR);
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_WTF);
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_SCORPION);
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_WASP);
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.FOSSILISED_PIRAPORU);
            }
            if (CAConfig.COMMON.enableTrollOreGen.get()) {
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.RED_ANT_INFESTED);
            }
            if (CAConfig.COMMON.spawnDzOresInOverworld.get() && CAConfig.COMMON.enableDzMineralOreGen.get()) {
                if (CAConfig.COMMON.enableOreCopperGen.get())
                    gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_COPPER);
                if (CAConfig.COMMON.enableOreTinGen.get())
                    gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_TIN);
                if (CAConfig.COMMON.enableOreSilverGen.get())
                    gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_SILVER);
                if (CAConfig.COMMON.enableOrePlatinumGen.get())
                    gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_PLATINUM);
                if (CAConfig.COMMON.enableOreSunstoneGen.get())
                    gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_SUNSTONE);
                if (CAConfig.COMMON.enableOreBloodstoneGen.get())
                    gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.ORE_BLOODSTONE);
            }
            if (CAConfig.COMMON.enableNestGen.get()) {
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.BROWN_ANT_NEST);
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.RAINBOW_ANT_NEST);
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.RED_ANT_NEST);
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.UNSTABLE_ANT_NEST);
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.TERMITE_NEST);
            }
        }

        private static void addMountainOres(BiomeGenerationSettingsBuilder gen) {
            if (CAConfig.COMMON.enableTrollOreGen.get()) {
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.TERMITE_INFESTED);
            }
        }

        private static void addNetherOres(BiomeGenerationSettingsBuilder gen) {
            if (CAConfig.COMMON.enableOreRubyGen.get())
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.NETHER_ORE_RUBY_LAVA);
        }

        private static void addMiningDimOres(BiomeGenerationSettingsBuilder gen) {
            if (CAConfig.COMMON.enableOreRubyGen.get())
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_RUBY_LAVA);
            if (CAConfig.COMMON.enableOreRubyGen.get())
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_RUBY_NO_SURFACE);
            if (CAConfig.COMMON.enableOreAmethystGen.get())
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_AMETHYST);
            if (CAConfig.COMMON.enableOreUraniumGen.get())
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_URANIUM);
            if (CAConfig.COMMON.enableOreTitaniumGen.get())
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_TITANIUM);
            if (CAConfig.COMMON.enableOreTigersEyeGen.get())
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_TIGERS_EYE);
            if (CAConfig.COMMON.enableOreAluminumGen.get())
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_ALUMINUM);
            if (CAConfig.COMMON.enableDzMineralOreGen.get()) {
                if (CAConfig.COMMON.enableOreCopperGen.get())
                    gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_COPPER);
                if (CAConfig.COMMON.enableOreTinGen.get())
                    gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_TIN);
                if (CAConfig.COMMON.enableOreSilverGen.get())
                    gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_SILVER);
                if (CAConfig.COMMON.enableOrePlatinumGen.get())
                    gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_PLATINUM);
                if (CAConfig.COMMON.enableOreSunstoneGen.get())
                    gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_SUNSTONE);
                if (CAConfig.COMMON.enableOreBloodstoneGen.get())
                    gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_BLOODSTONE);
            }
            if (CAConfig.COMMON.enableOreSaltGen.get())
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_ORE_SALT);
            if (CAConfig.COMMON.enableFossilGen.get()) {
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_ENT);
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_HERCULES_BEETLE);
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_RUBY_BUG);
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_EMERALD_GATOR);
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_WTF);
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_SCORPION);
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_WASP);
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_FOSSILISED_PIRAPORU);
            }
            if (CAConfig.COMMON.enableTrollOreGen.get()) {
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_RED_ANT_INFESTED);
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.MINING_TERMITE_INFESTED);
            }
            if (CAConfig.COMMON.enableNestGen.get()) {
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.RED_ANT_NEST);
            }
        }

        private static void addVillageDimOres(BiomeGenerationSettingsBuilder gen) {
            if (CAConfig.COMMON.enableNestGen.get()) {
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.RAINBOW_ANT_NEST);
            }
        }

        private static void addCrystalDimOres(BiomeGenerationSettingsBuilder gen) {
            if (CAConfig.COMMON.enableOreGen.get()) {
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.CRYSTAL_ORE_ENERGY);
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.GEODE_CATS_EYE);
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.GEODE_PINK_TOURMALINE);
            }
            if (CAConfig.COMMON.enableNestGen.get()) {
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.CRYSTAL_TERMITE_NEST);
            }
        }

//		private static void addDangerDimOres(BiomeGenerationSettingsBuilder gen) {
//			if (CAConfig.COMMON.enableNestGen.get()) {
//				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.UNSTABLE_ANT_NEST);
//			}
//		}

//		private static void addUtopiaDimOres(BiomeGenerationSettingsBuilder gen) {
//			if (CAConfig.COMMON.enableNestGen.get()) {
//				gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAConfiguredFeatures.BROWN_ANT_NEST);
//			}
//		}
    }
}
