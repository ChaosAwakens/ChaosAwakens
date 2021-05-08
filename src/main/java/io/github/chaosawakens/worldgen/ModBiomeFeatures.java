package io.github.chaosawakens.worldgen;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.registry.ModEntityTypes;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID)
public class ModBiomeFeatures {

    private ModBiomeFeatures() {
    }

    public static void addMobSpawns(BiomeLoadingEvent event) {

        Biome.Category category = event.getCategory();
        MobSpawnInfoBuilder spawnInfoBuilder = event.getSpawns();

        if (isSwampBiome(category)) {
            withRubyBug(spawnInfoBuilder);
            withEmeraldGator(spawnInfoBuilder);
        }
        if (isForestBiome(category)) {
            withBeaver(spawnInfoBuilder);
        }
        if (isOverworldBiome(category)) {
            withOverworldAnt(spawnInfoBuilder);
        }
//        if (isVillageManiaBiome(category)) {
//            withRoboSniper(spawnInfoBuilder);
//        }
    }

    public static void addStructureSpawns(BiomeLoadingEvent event) {

        Biome.Category category = event.getCategory();
        BiomeGenerationSettingsBuilder spawnInfoBuilder = event.getGeneration();

        if (isForestBiome(category)) {
            withEntDungeon(spawnInfoBuilder);
        }
    }

    public static void withEntDungeon(BiomeGenerationSettingsBuilder builder) {
        builder.withStructure(ConfiguredStructures.CONFIGURED_ENT_DUNGEON);
    }

    public static void withRubyBug(MobSpawnInfoBuilder builder) {
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.RUBY_BUG.get(), 25, 3, 6));
    }

    public static void withEmeraldGator(MobSpawnInfoBuilder builder) {
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.EMERALD_GATOR.get(), 3, 1, 2));
    }

    public static void withBeaver(MobSpawnInfoBuilder builder) {
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.BEAVER.get(), 15, 1, 2));
    }

    public static void withOverworldAnt(MobSpawnInfoBuilder builder) {
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.BROWN_ANT.get(), 20, 1, 5));
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.RAINBOW_ANT.get(), 20, 1, 5));
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.RED_ANT.get(), 20, 1, 5));
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.UNSTABLE_ANT.get(), 20, 1, 5));
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.TERMITE.get(), 20, 1, 5));
    }

//    public static void withRoboSniper(MobSpawnInfoBuilder builder) {
//        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(ModEntityTypes.ROBO_SNIPER.get(), 500, 5, 10));
//    }

    public static boolean isSwampBiome(Biome.Category category) {
        return category == Biome.Category.SWAMP;
    }

    public static boolean isForestBiome(Biome.Category category) {
        return category == Biome.Category.FOREST;
    }

    public static boolean isOverworldBiome(Biome.Category category) {
        return category != Biome.Category.NETHER || category != Biome.Category.THEEND;
    }

//    public static boolean isVillageManiaBiome(Biome.Category category) {
//        return category == Biome.Category.byName("chaosawakens:village_plains");
//    }
}