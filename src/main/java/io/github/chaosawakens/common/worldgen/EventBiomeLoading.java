package io.github.chaosawakens.common.worldgen;

import io.github.chaosawakens.common.config.CAConfig;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

import io.github.chaosawakens.common.registry.CABiomes;
import io.github.chaosawakens.common.registry.CAFeatures;


public class EventBiomeLoading {
	
	@SubscribeEvent
	public static void onBiomeLoadingEvent(final BiomeLoadingEvent event) {
		
		BiomeGenerationSettingsBuilder gen = event.getGeneration();
		
		RegistryKey<Biome> biome = RegistryKey.getOrCreateKey(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(event.getName(), "Who registered null name biome, naming criticism!"));
		
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)) {
			if (CAConfig.COMMON.enableOreGen.get()) { addOverworldOres(gen); }
		}

		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST)) {
			gen.getStructures().add( () -> ConfiguredStructures.CONFIGURED_ENT_DUNGEON);
		}
		
		if (BiomeDictionary.hasType(biome, CABiomes.Type.MINING_DIMENSION)) {
			if (CAConfig.COMMON.enableOreGen.get()) { addMiningDimOres(gen); }
		}

		if (BiomeDictionary.hasType(biome, CABiomes.Type.CRYSTAL_DIMENSION)) {
			gen.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, CAFeatures.GREEN_CRYSTAL_TREE);
			if (CAConfig.COMMON.enableOreGen.get()) { addMiningDimOres(gen); }
		}
	}

	public static void addOverworldOres(BiomeGenerationSettingsBuilder gen) {
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

	public static void addMiningDimOres(BiomeGenerationSettingsBuilder gen) {
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

	public static void addCrystalDimOres(BiomeGenerationSettingsBuilder gen) {

	}
}
