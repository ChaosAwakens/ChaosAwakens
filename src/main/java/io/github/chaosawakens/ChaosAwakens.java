package io.github.chaosawakens;

import com.electronwill.nightconfig.toml.TomlParser;
import io.github.chaosawakens.api.CAReflectionHelper;
import io.github.chaosawakens.client.ClientSetupEvent;
import io.github.chaosawakens.common.UpdateHandler;
import io.github.chaosawakens.common.events.*;
import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.integration.CAEMCValues;
import io.github.chaosawakens.common.integration.CAJER;
import io.github.chaosawakens.common.registry.*;
import io.github.chaosawakens.common.worldgen.BiomeLoadEventSubscriber;
import io.github.chaosawakens.data.CAAdvancementProvider;
import io.github.chaosawakens.data.CAItemModelGenerator;
import io.github.chaosawakens.data.CALootTableProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.MinecraftVersion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.fml.mclanguageprovider.MinecraftModLanguageProvider;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

import java.util.Locale;

@Mod(ChaosAwakens.MODID)
public class ChaosAwakens {

	public static final String MODID = "chaosawakens";
	public static final String MODNAME = "Chaos Awakens";
	public static final String VERSION = "0.9-preview4";

	public static ChaosAwakens INSTANCE;

	public static final Logger LOGGER = LogManager.getLogger();

	public ChaosAwakens() {
		INSTANCE = this;
		GeckoLibMod.DISABLE_IN_DEV = true;
		GeckoLib.initialize();

		LOGGER.debug(MODNAME + " Version is:" + VERSION);

		CAReflectionHelper.classLoad("io.github.chaosawakens.common.registry.CATags");
		
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		//Register to the mod event bus
		eventBus.addListener(CommonSetupEvent::onFMLCommonSetupEvent);
		eventBus.addListener(this::gatherData);
		
		if(FMLEnvironment.dist == Dist.CLIENT)
			eventBus.addListener(ClientSetupEvent::onFMLClientSetupEvent);

		CABiomes.BIOMES.register(eventBus);
		CABlocks.ITEM_BLOCKS.register(eventBus);
		CABlocks.BLOCKS.register(eventBus);
		CAEntityTypes.ENTITY_TYPES.register(eventBus);
		CAItems.ITEMS.register(eventBus);
		CATileEntities.TILE_ENTITIES.register(eventBus);
		CAStructures.STRUCTURES.register(eventBus);
		CAFeatures.FEATURES.register(eventBus);
		CASoundEvents.SOUND_EVENTS.register(eventBus);
		CAVillagers.POI_TYPES.register(eventBus);
		CAVillagers.PROFESSIONS.register(eventBus);
		eventBus.addListener(EntitySetAttributeEventSubscriber::onEntityAttributeCreationEvent);

		if (ModList.get().isLoaded("projecte")) {
			CAEMCValues.init();
		}

		if (ModList.get().isLoaded("jeresources")) {
			CAJER.init();
		}

		//Register to the forge event bus
		MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, CommonSetupEvent::addDimensionalSpacing);
		MinecraftForge.EVENT_BUS.register(new MiscEventHandler());
		MinecraftForge.EVENT_BUS.register(new LoginEventHandler());
		if (CAConfig.COMMON.showUpdateMessage.get())
			UpdateHandler.init();
		MinecraftForge.EVENT_BUS.register(new GiantEventHandler());
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
	
	public static <E> void error(String domain, E message) {
		LOGGER.error("[" + domain + "]: " + (message == null ? "null" : message.toString()));
	}
}
