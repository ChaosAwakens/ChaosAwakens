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
                new BlockMatchRuleTest(Blocks.LAVA), ModBlocks.RUBY_ORE.get().getDefaultState(), 3)) //vein Size
                .range(36).square() //maximum height
                .count(4))); //count per chunk

        overworldOres.add(register("amethyst_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.AMETHYST_ORE.get().getDefaultState(), 4)) //vein Size
                .range(53).square() //maximum height
                .count(7))); //count per chunk

        overworldOres.add(register("uranium_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.URANIUM_ORE.get().getDefaultState(), 3)) //vein Size
                .range(24).square() //maximum height
                .count(5))); //count per chunk

        overworldOres.add(register("titanium_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.TITANIUM_ORE.get().getDefaultState(), 3)) //vein Size
                .range(24).square() //maximum height
                .count(5))); //count per chunk

        overworldOres.add(register("aluminium_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.ALUMINIUM_ORE.get().getDefaultState(), 5)) //vein Size
                .range(53).square() //maximum height
                .count(7))); //count per chunk
 
     overworldOres.add(register("unprocessed_oil_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.UNPROCESSED_OIL_ORE.get().getDefaultState(), 4)) //vein Size
                .range(53).square() //maximum height
                .count(50))); //count per chunk
   
        
        
     overworldOres.add(register("unprocessed_oil_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                new BlockMatchRuleTest(Blocks.SAND), ModBlocks.UNPROCESSED_OIL_ORE.get().getDefaultState(), 4)) //vein Size
                .range(53).square() //maximum height
                .count(50))); //count per chunk
        
        
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
