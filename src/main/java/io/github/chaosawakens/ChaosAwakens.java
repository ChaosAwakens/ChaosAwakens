package io.github.chaosawakens;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.serialization.Codec;

import io.github.chaosawakens.api.EnchantmentAndLevel;
import io.github.chaosawakens.api.FeatureWrapper;
import io.github.chaosawakens.client.ClientSetupEvent;
import io.github.chaosawakens.common.CraftingEventSubscriber;
import io.github.chaosawakens.common.EntitySetAttributeEventSubscriber;
import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.integration.CAEMCValues;
import io.github.chaosawakens.common.network.PacketHandler;
import io.github.chaosawakens.common.registry.CABiomes;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAConfiguredFeatures;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAFeatures;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CASoundEvents;
import io.github.chaosawakens.common.registry.CAStructures;
import io.github.chaosawakens.common.registry.CATags;
import io.github.chaosawakens.common.registry.CATileEntities;
import io.github.chaosawakens.common.worldgen.BiomeLoadEventSubscriber;
import io.github.chaosawakens.common.worldgen.ConfiguredStructures;
import io.github.chaosawakens.data.CAAdvancementProvider;
import io.github.chaosawakens.data.CAItemModelGenerator;
import io.github.chaosawakens.data.CALootTableProvider;
import net.minecraft.data.DataGenerator;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

@Mod(ChaosAwakens.MODID)
public class ChaosAwakens {

	public static final String MODID = "chaosawakens";
	public static final String MODNAME = "Chaos Awakens";

	public static ChaosAwakens INSTANCE;

	public static final Logger LOGGER = LogManager.getLogger();

	/**
	 * Map that contains all the EALs mapped to their items respective registry name,
	 * would go on a common setup class, but we dont we one have so... :shrug:
	 */
	public static Map<ResourceLocation, EnchantmentAndLevel[]> enchantedItems = new HashMap<>();
	
	/**
	 * Same as above, but for configured features
	 */
	public static List<FeatureWrapper> configFeatures = new ArrayList<>();
	
	public ChaosAwakens() {
		INSTANCE = this;
		GeckoLib.initialize();
		GeckoLibMod.DISABLE_IN_DEV = true;
		
		new CATags();
		
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		//Register to the mod event bus
		eventBus.addListener(this::setup); //TODO Detach this from the mod main class
		eventBus.addListener(this::gatherData);
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientSetupEvent::register);

		CABiomes.BIOMES.register(eventBus);
		CABlocks.ITEM_BLOCKS.register(eventBus);
		CABlocks.BLOCKS.register(eventBus);
		CAItems.ITEMS.register(eventBus);
		CATileEntities.TILE_ENTITIES.register(eventBus);
		CAEntityTypes.ENTITY_TYPES.register(eventBus);
		CAStructures.STRUCTURES.register(eventBus);
		CAFeatures.FEATURES.register(eventBus);
		CASoundEvents.SOUND_EVENTS.register(eventBus);
		eventBus.addListener(EntitySetAttributeEventSubscriber::onEntityAttributeCreationEvent);
		
		if (ModList.get().isLoaded("projecte")) {
			CAEMCValues.init();
		}

//		if (ModList.get().isLoaded("jeresources")) {
//			CAJER.init();
//		}
		
		//Register to the forge event bus
		MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, BiomeLoadEventSubscriber::onBiomeLoadingEvent);
		MinecraftForge.EVENT_BUS.addListener(CraftingEventSubscriber::onItemCraftedEvent);
		MinecraftForge.EVENT_BUS.register(this);

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CAConfig.COMMON_SPEC);
	}

	private static Method GETCODEC_METHOD;

	public void addDimensionalSpacing(final WorldEvent.Load event) {
		if (event.getWorld() instanceof ServerWorld) {
			ServerWorld serverWorld = (ServerWorld) event.getWorld();
			ServerChunkProvider chunkProvider = serverWorld.getChunkProvider();
			try {
				if (GETCODEC_METHOD == null)
					GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "codec");
				//TODO Fix this
				ResourceLocation cgRL = Registry.CHUNK_GENERATOR_CODEC.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(chunkProvider.generator));
				if (cgRL != null && cgRL.getNamespace().equals("terraforged"))
					return;

			} catch (Exception e) {
				ChaosAwakens.warn("WORLDGEN", String.format("%s: Was unable to check if %s is using Terraforged's ChunkGenerator.", e.getCause(), serverWorld.getDimensionKey().getLocation()) );
			}

			if (serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator && serverWorld.getDimensionKey().equals(World.OVERWORLD))return;
			
			Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>( chunkProvider.generator.func_235957_b_().func_236195_a_());
			tempMap.putIfAbsent(CAStructures.ENT_DUNGEON.get(), DimensionStructuresSettings.field_236191_b_.get(CAStructures.ENT_DUNGEON.get()));
			chunkProvider.generator.func_235957_b_().field_236193_d_ = tempMap;
		}
	}
	
	private void setup(final FMLCommonSetupEvent event) {
		PacketHandler.init();

		event.enqueueWork(() -> {
			CAStructures.setupStructures();
			ConfiguredStructures.registerConfiguredStructures();
			
			//This should work, but feels wrong so //TODO
			new CAConfiguredFeatures();
			configFeatures.forEach( (wrapper) -> Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, wrapper.getIdentifier(), wrapper.getFeatureType()));
		});
		
		//TODO Make it so we don't have to add stuff here manually
		BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, CABiomes.MINING_BIOME.getId()), CABiomes.Type.MINING_DIMENSION);
		BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, CABiomes.STALAGMITE_VALLEY.getId()), CABiomes.Type.MINING_DIMENSION);
		BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, CABiomes.VILLAGE_PLAINS.getId()), CABiomes.Type.VILLAGE_DIMENSION);
		BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, CABiomes.DANGER_ISLANDS.getId()), CABiomes.Type.DANGER_DIMENSION);
		BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, CABiomes.CRYSTAL_PLAINS.getId()), CABiomes.Type.CRYSTAL_DIMENSION);
	}

	private void gatherData(final GatherDataEvent event) {
		DataGenerator dataGenerator = event.getGenerator();
		final ExistingFileHelper existing = event.getExistingFileHelper();

		if (event.includeServer()) {
			dataGenerator.addProvider(new CAAdvancementProvider(dataGenerator));
			dataGenerator.addProvider(new CALootTableProvider(dataGenerator));
			dataGenerator.addProvider(new CAItemModelGenerator(dataGenerator, existing));
		}
	}
	
	public static ResourceLocation prefix(String name) {
		return new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT));
	}
	
	public static <D> void debug(String domain, D message) {
		LOGGER.debug("[" + domain + "]: " + message == null ? "null" : message.toString());
	}
	
	public static <I> void info(String domain, I message) {
		LOGGER.info("[" + domain + "]: " + message == null ? "null" : message.toString());
	}
	
	public static <W> void warn(String domain, W message) {
		LOGGER.warn("[" + domain + "]: " + message == null ? "null" : message.toString());
	}
}
