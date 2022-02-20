package io.github.chaosawakens;

import io.github.chaosawakens.api.CAReflectionHelper;
import io.github.chaosawakens.client.ClientSetupEvent;
import io.github.chaosawakens.client.ToolTipEventSubscriber;
import io.github.chaosawakens.common.UpdateHandler;
import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.events.*;
import io.github.chaosawakens.common.integration.TheOneProbePlugin;
import io.github.chaosawakens.common.registry.*;
import io.github.chaosawakens.common.worldgen.BiomeLoadEventSubscriber;
import io.github.chaosawakens.data.*;
import net.minecraft.data.DataGenerator;
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
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

import java.util.Locale;

@Mod(ChaosAwakens.MODID)
public class ChaosAwakens {
	public static final String MODID = "chaosawakens";
	public static final String MODNAME = "Chaos Awakens";
	public static final String VERSION = "0.9.1.0-preview3";
	public static final Logger LOGGER = LogManager.getLogger();

	public ChaosAwakens() {
		GeckoLibMod.DISABLE_IN_DEV = true;
		GeckoLib.initialize();

		LOGGER.debug(MODNAME + " Version is: " + VERSION);

		CAReflectionHelper.classLoad("io.github.chaosawakens.common.registry.CATags");

		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

		//Register to the mod event bus
		eventBus.addListener(CommonSetupEvent::onFMLCommonSetupEvent);
		eventBus.addListener(this::gatherData);
		eventBus.addListener(this::onInterModEnqueueEvent);

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CAConfig.COMMON_SPEC);

		if (FMLEnvironment.dist == Dist.CLIENT) {
			eventBus.addListener(ClientSetupEvent::onFMLClientSetupEvent);
			MinecraftForge.EVENT_BUS.addListener(ToolTipEventSubscriber::onToolTipEvent);
		}

		//Register the deferred registers
		CABiomes.BIOMES.register(eventBus);
	//	ForgeMod.ATTRIBUTES.register(eventBus);
		CABlocks.ITEM_BLOCKS.register(eventBus);
		CABlocks.BLOCKS.register(eventBus);
		CAContainerTypes.CONTAINERS.register(eventBus);
		CAEntityTypes.ENTITY_TYPES.register(eventBus);
		CAItems.ITEMS.register(eventBus);
		CATileEntities.TILE_ENTITIES.register(eventBus);
		CARecipes.RECIPE_SERIALIZERS.register(eventBus);
		CAStats.STAT_TYPES.register(eventBus);
		CAStructures.STRUCTURES.register(eventBus);
		CAFeatures.FEATURES.register(eventBus);
		CASoundEvents.SOUND_EVENTS.register(eventBus);
		CAVillagers.POI_TYPES.register(eventBus);
		CAVillagers.PROFESSIONS.register(eventBus);
		CALootModifiers.LOOT_MODIFIERS.register(eventBus);
		eventBus.addListener(EntitySetAttributeEventSubscriber::onEntityAttributeCreationEvent);

		//Register to the forge event bus
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(EventPriority.HIGH, BiomeLoadEventSubscriber::onBiomeLoadingEvent);
		forgeBus.addListener(EventPriority.NORMAL, CommonSetupEvent::addDimensionalSpacing);
		forgeBus.addListener(EventPriority.NORMAL, CAVanillaCompat::registerFurnaceFuel);
	//	forgeBus.addListener(EventPriority.NORMAL, CommonSetupEvent::registerReachModifiers);
		forgeBus.addListener(MiscEventHandler::livingDeathEvent);
		forgeBus.addListener(MiscEventHandler::onRegisterCommandEvent);
		forgeBus.addListener(MiscEventHandler::onEntityJoin);
	//	forgeBus.addListener(MiscEventHandler::onPlayerReach);
	//	forgeBus.addListener(EventPriority.HIGH, ChainsawEventSubscriber::onBlockBreak);
	//	forgeBus.addListener(EventPriority.HIGH,ChainsawEventSubscriber::onBreakSpeed);
		//forgeBus.addListener(EventPriority.HIGH, ChainsawEventSubscriber::onBlockBreak);
		//forgeBus.addListener(EventPriority.HIGH,ChainsawEventSubscriber::onBreakSpeed);
		forgeBus.addListener(LoginEventHandler::onPlayerLogin);
		forgeBus.addListener(GiantEventHandler::onEntityJoin);
		forgeBus.addListener(CraftingEventSubscriber::onItemCraftedEvent);
		forgeBus.addListener(EventPriority.LOWEST, MiscEventHandler::onMobDrops);
		forgeBus.register(this);

		//Check for updates
		if (CAConfig.COMMON.showUpdateMessage.get())
			UpdateHandler.init();
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

	private void gatherData(final GatherDataEvent event) {
		DataGenerator dataGenerator = event.getGenerator();
		final ExistingFileHelper existing = event.getExistingFileHelper();

		if (event.includeServer()) {
			dataGenerator.addProvider(new CAAdvancementProvider(dataGenerator));
			dataGenerator.addProvider(new CALootTableProvider(dataGenerator));
			dataGenerator.addProvider(new CABlockModelProvider(dataGenerator, MODID, existing));
			dataGenerator.addProvider(new CAItemModelGenerator(dataGenerator, existing));
			dataGenerator.addProvider(new CABlockStateProvider(dataGenerator, MODID, existing));
			dataGenerator.addProvider(new CATagProvider.CATagProviderForBlocks(dataGenerator, existing));
			dataGenerator.addProvider(new CATagProvider.CAItemTagProvider(dataGenerator, existing));
			dataGenerator.addProvider(new CARecipeProvider(dataGenerator));
			dataGenerator.addProvider(new CALootModifierProvider(dataGenerator, MODID));
			dataGenerator.addProvider(new CACustomConversionProvider(dataGenerator));
		}
	}

	private void onInterModEnqueueEvent(final InterModEnqueueEvent event) {
		if (ModList.get().isLoaded("theoneprobe")) TheOneProbePlugin.register();
	}
} 
