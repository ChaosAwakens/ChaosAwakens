package io.github.chaosawakens;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.chaosawakens.client.ClientSetupEvent;
import io.github.chaosawakens.common.CAVillagerInit;
import io.github.chaosawakens.common.CommonSetupEvent;
import io.github.chaosawakens.common.CraftingEventSubscriber;
import io.github.chaosawakens.common.EntitySetAttributeEventSubscriber;
import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.integration.CAEMCValues;
import io.github.chaosawakens.common.registry.CABiomes;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAFeatures;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CASoundEvents;
import io.github.chaosawakens.common.registry.CAStructures;
import io.github.chaosawakens.common.registry.CATileEntities;
import io.github.chaosawakens.common.worldgen.BiomeLoadEventSubscriber;
import io.github.chaosawakens.data.CAAdvancementProvider;
import io.github.chaosawakens.data.CAItemModelGenerator;
import io.github.chaosawakens.data.CALootTableProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
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

	public ChaosAwakens() {
		INSTANCE = this;
		GeckoLib.initialize();
		GeckoLibMod.DISABLE_IN_DEV = true;
		
		try {
			Class.forName("io.github.chaosawakens.common.registry.CATags");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		//Register to the mod event bus
		eventBus.addListener(CommonSetupEvent::onFMLCommonSetupEvent);
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
		CAVillagerInit.POINT_OF_INTEREST_TYPES.register(eventBus);
		CAVillagerInit.VILLAGER_PROFESSIONS.register(eventBus);
		eventBus.addListener(EntitySetAttributeEventSubscriber::onEntityAttributeCreationEvent);
		
		if (ModList.get().isLoaded("projecte")) {
			CAEMCValues.init();
		}

//		if (ModList.get().isLoaded("jeresources")) {
//			CAJER.init();
//		}
		
		//Register to the forge event bus
		MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, CommonSetupEvent::addDimensionalSpacing);
		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, BiomeLoadEventSubscriber::onBiomeLoadingEvent);
		MinecraftForge.EVENT_BUS.addListener(CraftingEventSubscriber::onItemCraftedEvent);
		MinecraftForge.EVENT_BUS.register(this);

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CAConfig.COMMON_SPEC);
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
		LOGGER.debug("[" + domain + "]: " + (message == null ? "null" : message.toString()));
	}
	
	public static <I> void info(String domain, I message) {
		LOGGER.info("[" + domain + "]: " + (message == null ? "null" : message.toString()));
	}
	
	public static <W> void warn(String domain, W message) {
		LOGGER.warn("[" + domain + "]: " + (message == null ? "null" : message.toString()));
	}
}
