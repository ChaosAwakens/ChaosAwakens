package io.github.chaosawakens.common.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mojang.serialization.Codec;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.CAReflectionHelper;
import io.github.chaosawakens.api.FeatureWrapper;
import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.integration.CAJER;
import io.github.chaosawakens.common.network.PacketHandler;
import io.github.chaosawakens.common.registry.CABiomes;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAConfiguredStructures;
import io.github.chaosawakens.common.registry.CAEffects;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAStructures;
import io.github.chaosawakens.common.registry.CASurfaceBuilders;
import io.github.chaosawakens.common.registry.CAVanillaCompat;
import io.github.chaosawakens.common.registry.CAVillagers;
import net.minecraft.block.WoodType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper.UnableToFindMethodException;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

public class CommonSetupEvent {
	public static List<FeatureWrapper> configFeatures = new ArrayList<>();

	private static Method codecMethod;

	public static void onFMLCommonSetupEvent(FMLCommonSetupEvent event) {
		CAEntityTypes.registerSpawnPlacementTypes();
		PacketHandler.init();
		CAEffects.registerBrewingRecipes(); // Unused Currently, Here for FUTURE use.
		Raid.WaveMember.create("illusioner", EntityType.ILLUSIONER, new int[]{0, 0, 0, 0, 1, 1, 0, 2});

		event.enqueueWork(() -> {
			CAVanillaCompat.setup();
			CAStructures.setupStructures();
			CAConfiguredStructures.registerConfiguredStructures();
			CASurfaceBuilders.Configured.registerConfiguredSurfaceBuilders();
			CAVillagers.registerVillagerTypes();
	//		CAVillagers.registerVillagerStructures();
			CABlocks.flowerPots();
			WoodType.register(CABlocks.APPLE);
			WoodType.register(CABlocks.CHERRY);
			WoodType.register(CABlocks.DUPLICATION);
			WoodType.register(CABlocks.PEACH);
			WoodType.register(CABlocks.SKYWOOD);
			WoodType.register(CABlocks.GINKGO);
			
			CAReflectionHelper.classLoad("io.github.chaosawakens.common.registry.CAConfiguredFeatures");
			configFeatures.forEach((wrapper) -> Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, wrapper.getIdentifier(), wrapper.getFeatureType()));
		});

		ModList modList = ModList.get();
		if (modList.isLoaded("jeresources")) CAJER.init();

		BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.DENSE_MOUNTAIN.getId()), CABiomes.Type.MINING_PARADISE, CABiomes.Type.DENSE_MOUNTAIN);
		BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.STALAGMITE_VALLEY.getId()), CABiomes.Type.MINING_PARADISE, CABiomes.Type.STALAGMITE_VALLEY);
		BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.VILLAGE_PLAINS.getId()), CABiomes.Type.VILLAGE_MANIA, CABiomes.Type.VILLAGE_PLAINS);
		BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.VILLAGE_SAVANNA.getId()), CABiomes.Type.VILLAGE_MANIA, CABiomes.Type.VILLAGE_SAVANNA);
		BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.VILLAGE_TAIGA.getId()), CABiomes.Type.VILLAGE_MANIA, CABiomes.Type.VILLAGE_TAIGA);
		BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.VILLAGE_SNOWY.getId()), CABiomes.Type.VILLAGE_MANIA, CABiomes.Type.VILLAGE_SNOWY);
		BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.VILLAGE_DESERT.getId()), CABiomes.Type.VILLAGE_MANIA, CABiomes.Type.VILLAGE_DESERT);
		BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.DANGER_ISLANDS.getId()), CABiomes.Type.DANGER_ISLES);
		BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.CRYSTAL_PLAINS.getId()), CABiomes.Type.CRYSTAL_WORLD);
		BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.CRYSTAL_HILLS.getId()), CABiomes.Type.CRYSTAL_WORLD);
	}
	
    public static void onFMLLoadCompleteEvent(FMLLoadCompleteEvent event) {
    	modifyAttributeValues();
    }
    
    //Took the main functionality of AttributeFix and clamped it into a singular method --Meme Man
    public static void modifyAttributeValues() {
    	 Map<RangedAttribute, ?> attributes = new HashMap<>();
    	 for (final Entry<RangedAttribute, ?> e : attributes.entrySet()) {
    		 final RangedAttribute a = e.getKey();
    		 a.maxValue = Math.max(a.maxValue, 65536D);
    	 }
    }

	@SuppressWarnings("unchecked")
	public static void addDimensionalSpacing(final WorldEvent.Load event) {
		if (!(event.getWorld() instanceof ServerWorld)) return;

		ServerWorld serverWorld = (ServerWorld) event.getWorld();
		ServerChunkProvider chunkProvider = serverWorld.getChunkSource();

		try {
			if (codecMethod == null) codecMethod = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "codec");
			// TODO Fix this
			ResourceLocation chunkGeneratorKey = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) codecMethod.invoke(chunkProvider.generator));
			if (chunkGeneratorKey != null && chunkGeneratorKey.getNamespace().equals("terraforged")) return;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			ChaosAwakens.LOGGER.warn("[WORLDGEN]: " + e);
			e.printStackTrace();
		} catch (UnableToFindMethodException e) {
			if (CACommonConfig.COMMON.terraforgedCheckMsg.get())
				ChaosAwakens.LOGGER.info("[WORLDGEN]: Unable to check if " + serverWorld.dimension().location()
						+ " is using Terraforged's ChunkGenerator due to Terraforged not being present or not accessible,"
						+ " if you aren't using Terraforged please ignore this message");
		}

		if (serverWorld.getChunkSource().getGenerator() instanceof FlatChunkGenerator && serverWorld.dimension().equals(World.OVERWORLD)) return;

		Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(
				chunkProvider.generator.getSettings().structureConfig());

		tempMap.putIfAbsent(CAStructures.ACACIA_ENT_TREE.get(),
				DimensionStructuresSettings.DEFAULTS.get(CAStructures.ACACIA_ENT_TREE.get()));
		tempMap.putIfAbsent(CAStructures.BIRCH_ENT_TREE.get(),
				DimensionStructuresSettings.DEFAULTS.get(CAStructures.BIRCH_ENT_TREE.get()));
		tempMap.putIfAbsent(CAStructures.CRIMSON_ENT_TREE.get(),
				DimensionStructuresSettings.DEFAULTS.get(CAStructures.CRIMSON_ENT_TREE.get()));
		tempMap.putIfAbsent(CAStructures.DARK_OAK_ENT_TREE.get(),
				DimensionStructuresSettings.DEFAULTS.get(CAStructures.DARK_OAK_ENT_TREE.get()));
		tempMap.putIfAbsent(CAStructures.JUNGLE_ENT_TREE.get(),
				DimensionStructuresSettings.DEFAULTS.get(CAStructures.JUNGLE_ENT_TREE.get()));
		tempMap.putIfAbsent(CAStructures.OAK_ENT_TREE.get(),
				DimensionStructuresSettings.DEFAULTS.get(CAStructures.OAK_ENT_TREE.get()));
		tempMap.putIfAbsent(CAStructures.SPRUCE_ENT_TREE.get(),
				DimensionStructuresSettings.DEFAULTS.get(CAStructures.SPRUCE_ENT_TREE.get()));
		tempMap.putIfAbsent(CAStructures.WARPED_ENT_TREE.get(),
				DimensionStructuresSettings.DEFAULTS.get(CAStructures.WARPED_ENT_TREE.get()));
		tempMap.putIfAbsent(CAStructures.WASP_DUNGEON.get(),
				DimensionStructuresSettings.DEFAULTS.get(CAStructures.WASP_DUNGEON.get()));
		tempMap.putIfAbsent(CAStructures.MINING_WASP_DUNGEON.get(),
				DimensionStructuresSettings.DEFAULTS.get(CAStructures.MINING_WASP_DUNGEON.get()));

		chunkProvider.generator.getSettings().structureConfig = tempMap;
	}
}
