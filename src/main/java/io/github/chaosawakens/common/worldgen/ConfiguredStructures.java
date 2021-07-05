package io.github.chaosawakens.common.worldgen;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CAStructures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ConfiguredStructures {

	public static StructureFeature<?, ?> CONFIGURED_ENT_DUNGEON = CAStructures.ENT_DUNGEON.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
	public static StructureFeature<?, ?> CONFIGURED_WASP_DUNGEON = CAStructures.WASP_DUNGEON.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
	
	public static void registerConfiguredStructures() {
		Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
		Registry.register(registry, new ResourceLocation(ChaosAwakens.MODID, "configured_ent_dungeon"), CONFIGURED_ENT_DUNGEON);
		Registry.register(registry, new ResourceLocation(ChaosAwakens.MODID, "configured_wasp_dungeon"), CONFIGURED_WASP_DUNGEON);

		FlatGenerationSettings.STRUCTURES.put(CAStructures.ENT_DUNGEON.get(), CONFIGURED_ENT_DUNGEON);
		FlatGenerationSettings.STRUCTURES.put(CAStructures.WASP_DUNGEON.get(), CONFIGURED_WASP_DUNGEON);
	}
}