package io.github.chaosawakens.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mojang.serialization.Codec;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.EnchantmentAndLevel;
import io.github.chaosawakens.api.FeatureWrapper;
import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.items.CASpawnEggItem;
import io.github.chaosawakens.common.network.PacketHandler;
import io.github.chaosawakens.common.registry.CABiomes;
import io.github.chaosawakens.common.registry.CAConfiguredFeatures;
import io.github.chaosawakens.common.registry.CAStructures;
import io.github.chaosawakens.common.registry.CAVillagers;
import io.github.chaosawakens.common.worldgen.ConfiguredStructures;
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
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * @author invalid2
 *
 */
public class CommonSetupEvent {
	
	/**
	 * Map that contains all the EALs mapped to their items respective registry name
	 */
	public static Map<ResourceLocation, EnchantmentAndLevel[]> enchantedItems = new HashMap<>();
	
	/**
	 * Same as above, but for configured features
	 */
	public static List<FeatureWrapper> configFeatures = new ArrayList<>();
	public static List<CASpawnEggItem> EGGS = new ArrayList<>();
	private static Method GETCODEC_METHOD;
	
	public static void onFMLCommonSetupEvent(final FMLCommonSetupEvent event) {
		PacketHandler.init();
		
		event.enqueueWork(() -> {
			CAStructures.setupStructures();
			ConfiguredStructures.registerConfiguredStructures();
			CAVillagers.registerVillagerTypes();
			CAVillagers.registerPOIs();
			
			// This should work, but feels wrong so //TODO
			new CAConfiguredFeatures();
			configFeatures.forEach((wrapper) -> Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, wrapper.getIdentifier(), wrapper.getFeatureType()));
		});
		
		// TODO Make it so we don't have to add stuff here manually
		BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, CABiomes.MINING_BIOME.getId()), CABiomes.Type.MINING_DIMENSION);
		BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, CABiomes.STALAGMITE_VALLEY.getId()), CABiomes.Type.MINING_DIMENSION);
		BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, CABiomes.VILLAGE_PLAINS.getId()), CABiomes.Type.VILLAGE_DIMENSION);
		BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, CABiomes.DANGER_ISLANDS.getId()), CABiomes.Type.DANGER_DIMENSION);
		BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, CABiomes.CRYSTAL_PLAINS.getId()), CABiomes.Type.CRYSTAL_DIMENSION);
	}
	
	public static void addDimensionalSpacing(final WorldEvent.Load event) {
		if (event.getWorld() instanceof ServerWorld) {
			ServerWorld serverWorld = (ServerWorld) event.getWorld();
			ServerChunkProvider chunkProvider = serverWorld.getChunkProvider();
			
			try {
				if (GETCODEC_METHOD == null)GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "codec");
				// TODO Fix this
				ResourceLocation cgRL = Registry.CHUNK_GENERATOR_CODEC.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(chunkProvider.generator));
				if (cgRL != null && cgRL.getNamespace().equals("terraforged"))return;
			} catch (IllegalAccessException e) {
				ChaosAwakens.error("WORLDGEN", e);
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				ChaosAwakens.error("WORLDGEN", e);
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				ChaosAwakens.error("WORLDGEN", e);
				e.printStackTrace();
			} finally {
				if (GETCODEC_METHOD == null && CAConfig.COMMON.terraforgedCheckMsg.get())
					ChaosAwakens.info("WORLDGEN", "Unable to check if "+serverWorld.getDimensionKey().getLocation()+" is using Terraforged's ChunkGenerator due to Terraforged not being present or not accessable ");
			}
			
			if (serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator && serverWorld.getDimensionKey().equals(World.OVERWORLD))return;
			
			Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(chunkProvider.generator.func_235957_b_().func_236195_a_());
			tempMap.putIfAbsent(CAStructures.ENT_DUNGEON.get(), DimensionStructuresSettings.field_236191_b_.get(CAStructures.ENT_DUNGEON.get()));
			chunkProvider.generator.func_235957_b_().field_236193_d_ = tempMap;
		}
	}
}