package io.github.chaosawakens.worldgen;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.registry.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RandomBlockMatchRuleTest;
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
                new BlockMatchRuleTest(Blocks.LAVA), ModBlocks.RUBY_ORE.get().getDefaultState(), 8)) //vein Size
                .range(48).square() //maximum height
                .count(16))); //count per chunk

        overworldOres.add(register("ruby_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.RUBY_ORE.get().getDefaultState(), 3)) //vein Size
                .range(24).square() //maximum height
                .count(1))); //count per chunk

        overworldOres.add(register("amethyst_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.AMETHYST_ORE.get().getDefaultState(), 4)) //vein Size
                .range(56).square() //maximum height
                .count(4))); //count per chunk

        overworldOres.add(register("uranium_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.URANIUM_ORE.get().getDefaultState(), 3)) //vein Size
                .range(24).square() //maximum height
                .count(2))); //count per chunk

        overworldOres.add(register("titanium_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.TITANIUM_ORE.get().getDefaultState(), 3)) //vein Size
                .range(24).square() //maximum height
                .count(2))); //count per chunk

        overworldOres.add(register("tigers_eye_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.TIGERS_EYE_ORE.get().getDefaultState(), 7)) //vein Size
                .range(48).square() //maximum height
                .count(5))); //count per chunk

        overworldOres.add(register("aluminium_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.ALUMINIUM_ORE.get().getDefaultState(), 5)) //vein Size
                .range(56).square() //maximum height
                .count(8))); //count per chunk

        overworldOres.add(register("salt_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.SALT_ORE.get().getDefaultState(), 10)) //vein Size
                .range(128).square() //maximum height
                .count(28))); //count per chunk

        overworldOres.add(register("fossilised_ent", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.FOSSILISED_ENT.get().getDefaultState(), 3)) //vein Size
                .range(128).square() //maximum height
                .count(14))); //count per chunk

        overworldOres.add(register("fossilised_hercules_beetle", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.FOSSILISED_HERCULES_BEETLE.get().getDefaultState(), 3)) //vein Size
                .range(128).square() //maximum height
                .count(14))); //count per chunk

        overworldOres.add(register("fossilised_ruby_bug", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.FOSSILISED_RUBY_BUG.get().getDefaultState(), 3)) //vein Size
                .range(128).square() //maximum height
                .count(14))); //count per chunk

        overworldOres.add(register("fossilised_emerald_gator", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.FOSSILISED_EMERALD_GATOR.get().getDefaultState(), 3)) //vein Size
                .range(128).square() //maximum height
                .count(14))); //count per chunk

        overworldOres.add(register("red_ant_infested_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.RED_ANT_INFESTED_ORE.get().getDefaultState(), 10)) //vein Size
                .range(32).square() //maximum height
                .count(8))); //count per chunk

        overworldOres.add(register("termite_infested_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.TERMITE_INFESTED_ORE.get().getDefaultState(), 10)) //vein Size
                .range(32).square() //maximum height
                .count(2))); //count per chunk
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
