package io.github.chaosawakens;

import io.github.chaosawakens.api.CAReflectionHelper;
import io.github.chaosawakens.client.CABlockItemColors;
import io.github.chaosawakens.client.ClientSetupEvent;
import io.github.chaosawakens.client.ToolTipEventSubscriber;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

import java.util.Locale;
import java.util.Optional;

@Mod(ChaosAwakens.MODID)
public class ChaosAwakens {
	public static final String MODID = "chaosawakens";
	public static final String MODNAME = "Chaos Awakens";
	public static ArtifactVersion VERSION = null;
	public static final Logger LOGGER = LogManager.getLogger();

	public ChaosAwakens() {
		GeckoLibMod.DISABLE_IN_DEV = true;
		GeckoLib.initialize();

		Optional<? extends ModContainer> opt = ModList.get().getModContainerById(MODID);
		if (opt.isPresent()) {
			IModInfo modInfo = opt.get().getModInfo();
			VERSION = modInfo.getVersion();
		} else {
			LOGGER.warn("Cannot get version from mod info");
		}

		LOGGER.debug(MODNAME + " Version is: " + VERSION);
		LOGGER.debug("Mod ID for " + MODNAME + " is: " + MODID);

		CAReflectionHelper.classLoad("io.github.chaosawakens.common.registry.CATags");

		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

		// Register to the mod event bus
		eventBus.addListener(CommonSetupEvent::onFMLCommonSetupEvent);
		eventBus.addListener(this::gatherData);
		eventBus.addListener(this::onInterModEnqueueEvent);

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CAConfig.COMMON_SPEC);

		if (FMLEnvironment.dist == Dist.CLIENT) {
			eventBus.addListener(ClientSetupEvent::onFMLClientSetupEvent);
//			eventBus.addListener(ClientSetupEvent::mouseEvent);
//			eventBus.addListener(ClientSetupEvent::renderParticles);
			MinecraftForge.EVENT_BUS.addListener(ToolTipEventSubscriber::onToolTipEvent);
			eventBus.addListener(EventPriority.NORMAL, CABlockItemColors::registerBlockColors);
			eventBus.addListener(EventPriority.NORMAL, CABlockItemColors::registerItemColors);
		}

		// Register the deferred registers
		CABiomes.BIOMES.register(eventBus);
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
		eventBus.addListener(EntityAttributeModificationEventSubscriber::onEntityAttributeModificationEvent);

		// Register to the forge event bus
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(EventPriority.HIGH, BiomeLoadEventSubscriber::onBiomeLoadingEvent);
		forgeBus.addListener(EventPriority.NORMAL, CommonSetupEvent::addDimensionalSpacing);
		forgeBus.addListener(MiscEventHandler::livingDeathEvent);
		forgeBus.addListener(MiscEventHandler::onRegisterCommandEvent);
		forgeBus.addListener(MiscEventHandler::onEntityJoin);
		forgeBus.addListener(MiscEventHandler::onPlayerLoggedIn);
		forgeBus.addListener(GiantEventHandler::onEntityJoin);
		forgeBus.addListener(EventPriority.NORMAL, CAVanillaCompat::registerFurnaceFuel);
		forgeBus.addListener(CraftingEventSubscriber::onItemCraftedEvent);
		forgeBus.addListener(EventPriority.LOWEST, MiscEventHandler::onMobDrops);
		forgeBus.register(this);
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
		if (event.includeServer()) {
			dataGenerator.addProvider(new CAAdvancementProvider(dataGenerator));
			dataGenerator.addProvider(new CACustomConversionProvider(dataGenerator));
			dataGenerator.addProvider(new CADimensionTypeProvider(dataGenerator));
			dataGenerator.addProvider(new CALootModifierProvider(dataGenerator, MODID));
			dataGenerator.addProvider(new CALootTableProvider(dataGenerator));
			dataGenerator.addProvider(new CARecipeProvider(dataGenerator));
			dataGenerator.addProvider(new CATagProvider.CABlockTagProvider(dataGenerator, existing));
			dataGenerator.addProvider(new CATagProvider.CAItemTagProvider(dataGenerator, existing));
			dataGenerator.addProvider(new CATagProvider.CAEntityTypeTagProvider(dataGenerator, existing));
//			dataGenerator.addProvider(new CATagProvider.CAFluidTagProvider(dataGenerator, existing));
		}
	}

	private void onInterModEnqueueEvent(final InterModEnqueueEvent event) {
		if (ModList.get().isLoaded("theoneprobe"))
			TheOneProbePlugin.register();
	}
}
