package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.worldgen.structures.SurfaceStructure;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class CAStructures {
	public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, ChaosAwakens.MODID);

	public static final RegistryObject<Structure<NoFeatureConfig>> ACACIA_ENT_TREE = STRUCTURES.register("acacia_ent_tree", () -> new SurfaceStructure(NoFeatureConfig.CODEC, "ent_tree/acacia"));
	public static final RegistryObject<Structure<NoFeatureConfig>> BIRCH_ENT_TREE = STRUCTURES.register("birch_ent_tree", () -> new SurfaceStructure(NoFeatureConfig.CODEC, "ent_tree/birch"));
	public static final RegistryObject<Structure<NoFeatureConfig>> CRIMSON_ENT_TREE = STRUCTURES.register("crimson_ent_tree", () -> new SurfaceStructure(NoFeatureConfig.CODEC, "ent_tree/crimson"));
	public static final RegistryObject<Structure<NoFeatureConfig>> DARK_OAK_ENT_TREE = STRUCTURES.register("dark_oak_ent_tree", () -> new SurfaceStructure(NoFeatureConfig.CODEC, "ent_tree/dark_oak"));
	public static final RegistryObject<Structure<NoFeatureConfig>> JUNGLE_ENT_TREE = STRUCTURES.register("jungle_ent_tree", () -> new SurfaceStructure(NoFeatureConfig.CODEC, "ent_tree/jungle"));
	public static final RegistryObject<Structure<NoFeatureConfig>> OAK_ENT_TREE = STRUCTURES.register("oak_ent_tree", () -> new SurfaceStructure(NoFeatureConfig.CODEC, "ent_tree/oak"));
	public static final RegistryObject<Structure<NoFeatureConfig>> SPRUCE_ENT_TREE = STRUCTURES.register("spruce_ent_tree", () -> new SurfaceStructure(NoFeatureConfig.CODEC, "ent_tree/spruce"));
	public static final RegistryObject<Structure<NoFeatureConfig>> WARPED_ENT_TREE = STRUCTURES.register("warped_ent_tree", () -> new SurfaceStructure(NoFeatureConfig.CODEC, "ent_tree/warped"));
	public static final RegistryObject<Structure<NoFeatureConfig>> GINKGO_ENT_TREE = STRUCTURES.register("ginkgo_ent_tree", () -> new SurfaceStructure(NoFeatureConfig.CODEC, "ent_tree/ginkgo"));
	public static final RegistryObject<Structure<NoFeatureConfig>> JEFFERY_DUNGEON_PLACEHOLDER = STRUCTURES.register("jeffery_dungeon_placeholder", () -> new SurfaceStructure(NoFeatureConfig.CODEC, "robo/jeffery_dungeon/jeffery_dungeon_placeholder"));

//	public static final RegistryObject<Structure<NoFeatureConfig>> WASP_DUNGEON = STRUCTURES.register("wasp_dungeon", () -> new SurfaceStructure(NoFeatureConfig.CODEC, "wasp_dungeon/start_pool", new BlockPos(0, -5, 0)));
//	public static final RegistryObject<Structure<NoFeatureConfig>> MINING_WASP_DUNGEON = STRUCTURES.register("mining_wasp_dungeon", () -> new SurfaceStructure(NoFeatureConfig.CODEC, "wasp_dungeon/mining_start_pool", new BlockPos(0, -17, 0)));
	
	public static void setupStructures() {
		setupMapSpacingAndLand(ACACIA_ENT_TREE.get(), new StructureSeparationSettings(27, 25, 32034987), false);
		setupMapSpacingAndLand(BIRCH_ENT_TREE.get(), new StructureSeparationSettings(27, 25, 38875937), false);
		setupMapSpacingAndLand(CRIMSON_ENT_TREE.get(), new StructureSeparationSettings(27, 25, 136387957), true);
		setupMapSpacingAndLand(DARK_OAK_ENT_TREE.get(), new StructureSeparationSettings(27, 25, 49351645), true);
		setupMapSpacingAndLand(JUNGLE_ENT_TREE.get(), new StructureSeparationSettings(27, 25, 193669707), true);
		setupMapSpacingAndLand(OAK_ENT_TREE.get(), new StructureSeparationSettings(27, 25, 170597541), true);
		setupMapSpacingAndLand(SPRUCE_ENT_TREE.get(), new StructureSeparationSettings(27, 25, 49224289), true);
		setupMapSpacingAndLand(WARPED_ENT_TREE.get(), new StructureSeparationSettings(27, 25, 37834092), true);
		setupMapSpacingAndLand(GINKGO_ENT_TREE.get(), new StructureSeparationSettings(27, 25, 276270520), true);
		setupMapSpacingAndLand(JEFFERY_DUNGEON_PLACEHOLDER.get(), new StructureSeparationSettings(80, 65, 314159265), true);

//		setupMapSpacingAndLand(WASP_DUNGEON.get(), new StructureSeparationSettings(21, 19, 159332311), false);
//		setupMapSpacingAndLand(MINING_WASP_DUNGEON.get(), new StructureSeparationSettings(38, 34, 159342356), false);
	}

	public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
		Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

		if (transformSurroundingLand) Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder().addAll(Structure.NOISE_AFFECTING_FEATURES).add(structure).build();

		DimensionStructuresSettings.DEFAULTS = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.DEFAULTS).put(structure, structureSeparationSettings).build();

		WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
			Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().structureSettings().structureConfig;

			if (structureMap instanceof ImmutableMap) {
				HashMap<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<Structure<?>, StructureSeparationSettings>(structureMap);
				
				tempMap.put(structure, structureSeparationSettings);
				settings.getValue().structureSettings().structureConfig = tempMap;
			} else structureMap.put(structure, structureSeparationSettings);
		});
	}
}
