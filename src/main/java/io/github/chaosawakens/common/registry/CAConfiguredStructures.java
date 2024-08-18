package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class CAConfiguredStructures {
	public static StructureFeature<?, ?> CONFIGURED_ACACIA_ENT_TREE = CAStructures.ACACIA_ENT_TREE.get().configured(IFeatureConfig.NONE);
	public static StructureFeature<?, ?> CONFIGURED_BIRCH_ENT_TREE = CAStructures.BIRCH_ENT_TREE.get().configured(IFeatureConfig.NONE);
	public static StructureFeature<?, ?> CONFIGURED_CRIMSON_ENT_TREE = CAStructures.CRIMSON_ENT_TREE.get().configured(IFeatureConfig.NONE);
	public static StructureFeature<?, ?> CONFIGURED_DARK_OAK_ENT_TREE = CAStructures.DARK_OAK_ENT_TREE.get().configured(IFeatureConfig.NONE);
	public static StructureFeature<?, ?> CONFIGURED_JUNGLE_ENT_TREE = CAStructures.JUNGLE_ENT_TREE.get().configured(IFeatureConfig.NONE);
	public static StructureFeature<?, ?> CONFIGURED_OAK_ENT_TREE = CAStructures.OAK_ENT_TREE.get().configured(IFeatureConfig.NONE);
	public static StructureFeature<?, ?> CONFIGURED_SPRUCE_ENT_TREE = CAStructures.SPRUCE_ENT_TREE.get().configured(IFeatureConfig.NONE);
	public static StructureFeature<?, ?> CONFIGURED_WARPED_ENT_TREE = CAStructures.WARPED_ENT_TREE.get().configured(IFeatureConfig.NONE);
	public static StructureFeature<?, ?> CONFIGURED_GINKGO_ENT_TREE = CAStructures.GINKGO_ENT_TREE.get().configured(IFeatureConfig.NONE);

	public static StructureFeature<?, ?> CONFIGURED_JEFFERY_DUNGEON_PLACEHOLDER = CAStructures.JEFFERY_DUNGEON_PLACEHOLDER.get().configured(IFeatureConfig.NONE);

	public static StructureFeature<?, ?> CONFIGURED_WASP_NEST = CAStructures.WASP_NEST.get().configured(IFeatureConfig.NONE);

//	public static StructureFeature<?, ?> CONFIGURED_WASP_DUNGEON = CAStructures.WASP_DUNGEON.get().configured(IFeatureConfig.NONE);
//	public static StructureFeature<?, ?> CONFIGURED_MINING_WASP_DUNGEON = CAStructures.MINING_WASP_DUNGEON.get().configured(IFeatureConfig.NONE);

	public static void registerConfiguredStructures() {
		Registry<StructureFeature<?, ?>> csfRegistry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
		
		Registry.register(csfRegistry, ChaosAwakens.prefix("configured_acacia_ent_tree"), CONFIGURED_ACACIA_ENT_TREE);
		Registry.register(csfRegistry, ChaosAwakens.prefix("configured_birch_ent_tree"), CONFIGURED_BIRCH_ENT_TREE);
		Registry.register(csfRegistry, ChaosAwakens.prefix("configured_crimson_ent_tree"), CONFIGURED_CRIMSON_ENT_TREE);
		Registry.register(csfRegistry, ChaosAwakens.prefix("configured_dark_oak_ent_tree"), CONFIGURED_DARK_OAK_ENT_TREE);
		Registry.register(csfRegistry, ChaosAwakens.prefix("configured_jungle_ent_tree"), CONFIGURED_JUNGLE_ENT_TREE);
		Registry.register(csfRegistry, ChaosAwakens.prefix("configured_oak_ent_tree"), CONFIGURED_OAK_ENT_TREE);
		Registry.register(csfRegistry, ChaosAwakens.prefix("configured_spruce_ent_tree"), CONFIGURED_SPRUCE_ENT_TREE);
		Registry.register(csfRegistry, ChaosAwakens.prefix("configured_warped_ent_tree"), CONFIGURED_WARPED_ENT_TREE);
		Registry.register(csfRegistry, ChaosAwakens.prefix("configured_ginkgo_ent_tree"), CONFIGURED_GINKGO_ENT_TREE);

		Registry.register(csfRegistry, ChaosAwakens.prefix("configured_jeffery_dungeon_placeholder"), CONFIGURED_JEFFERY_DUNGEON_PLACEHOLDER);

		Registry.register(csfRegistry, ChaosAwakens.prefix("configured_wasp_nest"), CONFIGURED_WASP_NEST);

//		Registry.register(registry, ChaosAwakens.prefix("configured_wasp_dungeon"), CONFIGURED_WASP_DUNGEON);
//		Registry.register(registry, ChaosAwakens.prefix("configured_mining_wasp_dungeon"), CONFIGURED_MINING_WASP_DUNGEON);

		FlatGenerationSettings.STRUCTURE_FEATURES.put(CAStructures.ACACIA_ENT_TREE.get(), CONFIGURED_ACACIA_ENT_TREE);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(CAStructures.BIRCH_ENT_TREE.get(), CONFIGURED_BIRCH_ENT_TREE);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(CAStructures.CRIMSON_ENT_TREE.get(), CONFIGURED_CRIMSON_ENT_TREE);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(CAStructures.DARK_OAK_ENT_TREE.get(), CONFIGURED_DARK_OAK_ENT_TREE);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(CAStructures.JUNGLE_ENT_TREE.get(), CONFIGURED_JUNGLE_ENT_TREE);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(CAStructures.OAK_ENT_TREE.get(), CONFIGURED_OAK_ENT_TREE);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(CAStructures.SPRUCE_ENT_TREE.get(), CONFIGURED_SPRUCE_ENT_TREE);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(CAStructures.WARPED_ENT_TREE.get(), CONFIGURED_WARPED_ENT_TREE);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(CAStructures.GINKGO_ENT_TREE.get(), CONFIGURED_GINKGO_ENT_TREE);

		FlatGenerationSettings.STRUCTURE_FEATURES.put(CAStructures.JEFFERY_DUNGEON_PLACEHOLDER.get(), CONFIGURED_JEFFERY_DUNGEON_PLACEHOLDER);

		FlatGenerationSettings.STRUCTURE_FEATURES.put(CAStructures.WASP_NEST.get(), CONFIGURED_WASP_NEST);

//		FlatGenerationSettings.STRUCTURE_FEATURES.put(CAStructures.WASP_DUNGEON.get(), CONFIGURED_WASP_DUNGEON);
//		FlatGenerationSettings.STRUCTURE_FEATURES.put(CAStructures.MINING_WASP_DUNGEON.get(), CONFIGURED_MINING_WASP_DUNGEON);
	}
}
