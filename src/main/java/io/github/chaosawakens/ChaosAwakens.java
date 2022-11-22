package io.github.chaosawakens;

import java.util.Locale;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.artifact.versioning.ArtifactVersion;

import io.github.chaosawakens.api.CAReflectionHelper;
import io.github.chaosawakens.client.CABlockItemColors;
import io.github.chaosawakens.client.ClientSetupEvent;
import io.github.chaosawakens.client.ClientSetupEvent.ClientEventsHelper;
import io.github.chaosawakens.client.ToolTipEventSubscriber;
import io.github.chaosawakens.client.config.CAClientConfig;
import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.events.CommonSetupEvent;
import io.github.chaosawakens.common.events.EntitySetAttributeEventSubscriber;
import io.github.chaosawakens.common.events.MiscEventHandler;
import io.github.chaosawakens.common.integration.CAJadePlugin;
import io.github.chaosawakens.common.integration.TheOneProbePlugin;
import io.github.chaosawakens.common.registry.CAAttributes;
import io.github.chaosawakens.common.registry.CABiomes;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAContainerTypes;
import io.github.chaosawakens.common.registry.CAEffects;
import io.github.chaosawakens.common.registry.CAEnchantments;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAFeatures;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CALootModifiers;
import io.github.chaosawakens.common.registry.CAPaintings;
import io.github.chaosawakens.common.registry.CAParticleTypes;
import io.github.chaosawakens.common.registry.CARecipes;
import io.github.chaosawakens.common.registry.CASoundEvents;
import io.github.chaosawakens.common.registry.CAStats;
import io.github.chaosawakens.common.registry.CAStructures;
import io.github.chaosawakens.common.registry.CATileEntities;
import io.github.chaosawakens.common.registry.CAVanillaCompat;
import io.github.chaosawakens.common.registry.CAVillagerTrades;
import io.github.chaosawakens.common.registry.CAVillagers;
import io.github.chaosawakens.common.worldgen.BiomeLoadEventSubscriber;
import io.github.chaosawakens.data.CAAdvancementProvider;
import io.github.chaosawakens.data.CABlockModelProvider;
import io.github.chaosawakens.data.CABlockStateProvider;
import io.github.chaosawakens.data.CACustomConversionProvider;
import io.github.chaosawakens.data.CADimensionTypeProvider;
import io.github.chaosawakens.data.CAGlobalLootModifierProvider;
import io.github.chaosawakens.data.CAItemModelProvider;
import io.github.chaosawakens.data.CALootTableProvider;
import io.github.chaosawakens.data.CAParticleTypeProvider;
import io.github.chaosawakens.data.CARecipeProvider;
import io.github.chaosawakens.data.CATagProvider;
import io.github.chaosawakens.server.ServerSetupEvent;
import io.github.chaosawakens.server.config.CAServerConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.forgespi.language.IModInfo;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

@Mod(ChaosAwakens.MODID)
public class ChaosAwakens {
	public static final String MODID = "chaosawakens";
	public static final String MODNAME = "Chaos Awakens";
	public static ArtifactVersion VERSION = null;
	public static final Logger LOGGER = LogManager.getLogger();
	public static ChaosAwakens INSTANCE;
	public static boolean DISABLE_IN_DEV = false;
	public static ItemGroup DEVELOPMENT;
	public static boolean DEVELOPMENT_ENVIRONMENT = false;
	
	public ChaosAwakens() {
		GeckoLibMod.DISABLE_IN_DEV = true;
		GeckoLib.initialize();
		INSTANCE = this;

		Optional<? extends ModContainer> opt = ModList.get().getModContainerById(MODID);
		if (opt.isPresent()) {
			IModInfo modInfo = opt.get().getModInfo();
			VERSION = modInfo.getVersion();
		} else {
			LOGGER.warn("Cannot get version from mod info");
		}

		LOGGER.debug(MODNAME + " Version is: " + VERSION);
		LOGGER.debug("Mod ID for " + MODNAME + " is: " + MODID);
		DEVELOPMENT_ENVIRONMENT = !FMLEnvironment.production && !DISABLE_IN_DEV;

		CAReflectionHelper.classLoad("io.github.chaosawakens.common.registry.CATags");

		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CAClientConfig.CLIENT_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CACommonConfig.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, CAServerConfig.SERVER_SPEC);

		// Register to the mod event bus
		eventBus.addListener(CommonSetupEvent::onFMLCommonSetupEvent);
		eventBus.addListener(CommonSetupEvent::onFMLLoadCompleteEvent);
		eventBus.addListener(this::gatherData);
		eventBus.addListener(this::onInterModEnqueueEvent);
		eventBus.addListener(ServerSetupEvent::onFMLServerSetupEvent);
		
		if (FMLEnvironment.dist == Dist.CLIENT) {
			eventBus.addListener(ClientSetupEvent::onFMLClientSetupEvent);
			eventBus.addListener(ClientEventsHelper::onParticleRegistrationEvent);
			eventBus.addListener(EventPriority.NORMAL, ClientSetupEvent.ClientEventsHelper::onClientLoadComplete);
			MinecraftForge.EVENT_BUS.addListener(ToolTipEventSubscriber::onToolTipEvent);
			MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, ClientEventsHelper::onCameraSetup);
	/*		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, ClientEventsHelper::onPreRenderPlayer);
			MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, ClientEventsHelper::onPostRenderPlayer);
			MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, ClientEventsHelper::onRenderPlayerHand);
			MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, ClientEventsHelper::onPreRenderLiving);
			MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, ClientEventsHelper::onPostRenderLiving);*/
			MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, ClientEventsHelper::onPreRenderHUD);
			MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, ClientEventsHelper::renderFogColor);
			MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, ClientEventsHelper::renderFog);
			MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, ClientEventsHelper::renderParticles);
			MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, ClientEventsHelper::handleOverlay);
			eventBus.addListener(EventPriority.NORMAL, CABlockItemColors::registerBlockColors);
			eventBus.addListener(EventPriority.NORMAL, CABlockItemColors::registerItemColors);
		}

		// Register the deferred registers
		CAAttributes.ATTRIBUTES.register(eventBus);
		CABiomes.BIOMES.register(eventBus);
		CABlocks.ITEM_BLOCKS.register(eventBus);
		CABlocks.BLOCKS.register(eventBus);
		CAContainerTypes.CONTAINERS.register(eventBus);
		CAEntityTypes.ENTITY_TYPES.register(eventBus);
		CAParticleTypes.PARTICLE_TYPES.register(eventBus);
		CAPaintings.PAINTINGS.register(eventBus);
		CAItems.ITEMS.register(eventBus);
		CAEnchantments.ENCHANTMENTS.register(eventBus);
		CAEffects.EFFECTS.register(eventBus);
		CAEffects.POTIONS.register(eventBus);
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

		//TODO Armor set bonuses fix, merge extended items, merge other stuff
		
		// Register to the forge event bus
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(EventPriority.HIGH, BiomeLoadEventSubscriber::onBiomeLoadingEvent);
		forgeBus.addListener(EventPriority.HIGH, ServerSetupEvent::onFMLServerStartEvent);
		forgeBus.addListener(EventPriority.HIGH, ServerSetupEvent::onFMLServerStartingEvent);
		forgeBus.addListener(EventPriority.NORMAL, CommonSetupEvent::addDimensionalSpacing);
		forgeBus.addListener(CAVillagerTrades::onWandererTradesEvent);
		forgeBus.addListener(CAVillagerTrades::onVillagerTradesEvent);
		forgeBus.addListener(CAVillagerTrades::onArchaeologistTradesEvent);
		forgeBus.addListener(MiscEventHandler::onRegisterCommandEvent);
		forgeBus.addListener(MiscEventHandler::livingDeathEvent);
		forgeBus.addListener(MiscEventHandler::onMobDrops);
		forgeBus.addListener(MiscEventHandler::onMobXPDrop);
		forgeBus.addListener(MiscEventHandler::onBlockBreakXP);
		forgeBus.addListener(MiscEventHandler::onEnchant);
		forgeBus.addListener(MiscEventHandler::onLivingJump);
		forgeBus.addListener(MiscEventHandler::onBucketFill);
		forgeBus.addListener(MiscEventHandler::onLivingAttack);
		forgeBus.addListener(MiscEventHandler::onLivingBlockPlace);
		forgeBus.addListener(MiscEventHandler::onLivingUse);
		forgeBus.addListener(MiscEventHandler::onPlayerInteract);
		forgeBus.addListener(MiscEventHandler::onPlayerLeftClickInteractEmpty);
		forgeBus.addListener(MiscEventHandler::onPlayerRightClickInteractEmpty);
		forgeBus.addListener(MiscEventHandler::onPlayerLeftClickInteractBlock);
		forgeBus.addListener(MiscEventHandler::onPlayerRightClickInteractBlock);
		forgeBus.addListener(MiscEventHandler::onPlayerItemPickup);
		forgeBus.addListener(MiscEventHandler::onPlayerLoggedIn);
		forgeBus.addListener(MiscEventHandler::onEntityJoin);
		forgeBus.addListener(MiscEventHandler::onSleepFinished);
		forgeBus.addListener(EventPriority.NORMAL, CAVanillaCompat::registerFurnaceFuel);
		forgeBus.register(this);

		if (DEVELOPMENT_ENVIRONMENT) {
			DEVELOPMENT = new ItemGroup("chaosawakens.development") {
				@Override
				public ItemStack makeIcon() {
					return new ItemStack(CAItems.DEV_ITEM1.get());
				}
			};
		}
	}

	public static ResourceLocation prefix(String name) {
		return new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT));
	}

	private void gatherData(final GatherDataEvent event) {
		DataGenerator dataGenerator = event.getGenerator();
		final ExistingFileHelper existing = event.getExistingFileHelper();
		if (event.includeClient()) {
			dataGenerator.addProvider(new CABlockModelProvider(dataGenerator, MODID, existing));
			dataGenerator.addProvider(new CAItemModelProvider(dataGenerator, existing));
			dataGenerator.addProvider(new CABlockStateProvider(dataGenerator, MODID, existing));
		}
//		dataGenerator.addProvider(new CALanguageProvider(dataGenerator));
		dataGenerator.addProvider(new CAParticleTypeProvider(dataGenerator));
		if (event.includeServer()) {
			dataGenerator.addProvider(new CAAdvancementProvider(dataGenerator));
			dataGenerator.addProvider(new CACustomConversionProvider(dataGenerator));
			dataGenerator.addProvider(new CADimensionTypeProvider(dataGenerator));
			dataGenerator.addProvider(new CAGlobalLootModifierProvider(dataGenerator));
			dataGenerator.addProvider(new CALootTableProvider(dataGenerator));
			dataGenerator.addProvider(new CARecipeProvider(dataGenerator));
			dataGenerator.addProvider(new CATagProvider.CABlockTagProvider(dataGenerator, existing));
			dataGenerator.addProvider(new CATagProvider.CAItemTagProvider(dataGenerator, existing));
			dataGenerator.addProvider(new CATagProvider.CAEntityTypeTagProvider(dataGenerator, existing));
		}
	}
	
	public static boolean isLoaded() {
		return INSTANCE != null;
	}

	private void onInterModEnqueueEvent(final InterModEnqueueEvent event) {
		if (ModList.get().isLoaded("theoneprobe")) TheOneProbePlugin.register();
		if (ModList.get().isLoaded("jade")) new CAJadePlugin();
	}

	/**
	 * For those quick info checks to see if things are working, should be removed in releases or
	 * when you are done!
	 * @param <D> Type of the message
	 * @param domain Rather abstract, but basically where this is from
	 * @param message What you want to be printed (duh)
	 */
	public static <D> void debug(String domain, D message) {
		LOGGER.debug("[" + domain + "]: " + message != null ? message.toString() : message);
	}

	/**
	 * For general info, can be left in releases
	 * @param <I> Type of the message
	 * @param domain Rather abstract, but basically where this is from
	 * @param message What you want to be printed (duh)
	 */
	public static <I> void info(String domain, I message) {
		LOGGER.info("[" + domain + "]: " + message != null ? message.toString() : message);
	}

	/**
	 * For errors, warnings(duh), runtime problems and the like
	 * @param <W> Type of the message
	 * @param domain Rather abstract, but basically where this is from
	 * @param message What you want to be printed (duh)
	 */
	public static <W> void warn(String domain, W message) {
		LOGGER.warn("[" + domain + "]: " + message != null ? message.toString() : message);
	}
}
