package io.github.chaosawakens.worldgen;

import io.github.chaosawakens.registry.CAFeatures;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;


public class EventBiomeLoading {
	
	@SubscribeEvent
	public static void onBiomeLoading(final BiomeLoadingEvent event) {
		
		BiomeGenerationSettingsBuilder gen = event.getGeneration();
		
		RegistryKey<Biome> biome = RegistryKey.getOrCreateKey(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(event.getName(), "Who registered null name biome, naming criticism!"));
		
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)) {
			addCustomOres(gen);

			//ChaosAwakens.LOGGER.debug(event.getName());
		}

		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST)) {
			gen.getStructures().add( () -> ConfiguredStructures.CONFIGURED_ENT_DUNGEON);
		}
		
		if (event.getName().toString().equals("chaosawakens:mining_biome")) {
			addCustomOres(gen);
		}
	}
	
	public static void addCustomOres(BiomeGenerationSettingsBuilder gen) {
		
		gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.ORE_RUBY_LAVA);
		gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.ORE_RUBY_NO_SURFACE);
		gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.ORE_AMETHYST);
		gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.ORE_URANIUM);
		gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.ORE_TITANIUM);
		gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.ORE_TIGERS_EYE_ORE);
		gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.ORE_ALUMINIUM);
		gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.ORE_SALT);
		gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.FOSSILISED_ENT);
		gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.FOSSILISED_HERCULES_BEETLE);
		gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.FOSSILISED_RUBY_BUG);
		gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.FOSSILISED_EMERALD_GATOR);
		gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.RED_ANT_INFESTED_ORE);
		gen.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CAFeatures.TERMITE_INFESTED_ORE);
		
	}
}
