package io.github.chaosawakens.worldgen;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.registry.ModEntityTypes;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
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
        }
    }

    public static void withRubyBug(MobSpawnInfoBuilder builder) {
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.RUBY_BUG.get(), 10, 1, 3));
    }


    public static boolean isSwampBiome(Biome.Category category) {
        return category == Biome.Category.SWAMP;
    }
}