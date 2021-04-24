package io.github.chaosawakens.worldgen;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.registry.ModBlocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OreGeneration {

    private static final ArrayList<ConfiguredFeature<?, ?>> overworldOres = new ArrayList<ConfiguredFeature<?, ?>>();

    public static void registerOres() {

        overworldOres.add(register("ruby_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.RUBY_ORE.get().getDefaultState(), 4)) //vein Size
                .range(3).square() //maximum height
                .count(10))); //count per chunk

        overworldOres.add(register("amethyst_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.AMETHYST_ORE.get().getDefaultState(), 14)) //vein Size
                .range(84).square() //maximum height
                .count(18))); //count per chunk
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void gen(BiomeLoadingEvent event) {

        BiomeGenerationSettingsBuilder generation = event.getGeneration();

        for (ConfiguredFeature<?, ?> ore : overworldOres) {
            if (ore != null) {
                generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore);
            }
        }
    }

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, ChaosAwakens.MODID + ":" + name, configuredFeature);
    }
}