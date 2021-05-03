package io.github.chaosawakens.worldgen;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.registry.ModFeatures;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)

public class EventBiomeLoading {
    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder gen = event.getGeneration();
        RegistryKey<Biome> biome = RegistryKey.getOrCreateKey(ForgeRegistries.Keys.BIOMES,
                Objects.requireNonNull(event.getName(),
                        "Who registered null name biome, naming criticism!"));

        if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)) {
            ChaosAwakens.LOGGER.debug("Ores Overworld Gen");
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.ORE_RUBY_LAVA);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.ORE_RUBY_NO_SURFACE);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.ORE_AMETHYST);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.ORE_URANIUM);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.ORE_TITANIUM);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.ORE_TIGERS_EYE_ORE);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.ORE_ALUMINIUM);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.ORE_SALT);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.FOSSILISED_ENT);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.FOSSILISED_HERCULES_BEETLE);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.FOSSILISED_RUBY_BUG);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.FOSSILISED_EMERALD_GATOR);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.RED_ANT_INFESTED_ORE);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.TERMITE_INFESTED_ORE);
        }
        if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.getType("chaosawakens:mining_biome"))) {
            ChaosAwakens.LOGGER.debug("Ores Mining Gen");
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.ORE_RUBY_LAVA);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.ORE_RUBY_NO_SURFACE);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.ORE_AMETHYST);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.ORE_URANIUM);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.ORE_TITANIUM);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.ORE_TIGERS_EYE_ORE);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.ORE_ALUMINIUM);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.ORE_SALT);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.FOSSILISED_ENT);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.FOSSILISED_HERCULES_BEETLE);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.FOSSILISED_RUBY_BUG);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.FOSSILISED_EMERALD_GATOR);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.RED_ANT_INFESTED_ORE);
            gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.TERMITE_INFESTED_ORE);
        }
    }
}
