package io.github.chaosawakens;

import java.util.Locale;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.artifact.versioning.ArtifactVersion;

import io.github.chaosawakens.api.CAReflectionHelper;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.manager.CAModManager;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.forgespi.language.IModInfo;
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
		GeckoLib.initialize();
		
		INSTANCE = this;

		Optional<? extends ModContainer> opt = ModList.get().getModContainerById(MODID);
		if (opt.isPresent()) {
			IModInfo modInfo = opt.get().getModInfo();
			VERSION = modInfo.getVersion();
		} else {
			LOGGER.warn("Could not get version from mod info!");
		}

		LOGGER.debug(MODNAME + " Version is: " + VERSION);
		LOGGER.debug("Mod ID for " + MODNAME + " is: " + MODID);
		DEVELOPMENT_ENVIRONMENT = !FMLEnvironment.production && !DISABLE_IN_DEV;

		CAReflectionHelper.classLoad("io.github.chaosawakens.common.registry.CATags");
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		
		CAModManager.registerAll(modBus, forgeBus);
		forgeBus.register(this);
		
		if (DEVELOPMENT_ENVIRONMENT) {
			DEVELOPMENT = new ItemGroup("chaosawakens.development") {
				@Override
				public ItemStack makeIcon() {
					return new ItemStack(CAItems.DEV_ITEM1.get());
				}

				/**
				 * Fills {@code items} with all items that are in this group.
				 */
				public void fillItemList(NonNullList<ItemStack> items) {
					items.add(Items.SPAWNER.getDefaultInstance());
					items.add(Items.COMMAND_BLOCK.getDefaultInstance());
					items.add(Items.REPEATING_COMMAND_BLOCK.getDefaultInstance());
					items.add(Items.CHAIN_COMMAND_BLOCK.getDefaultInstance());
					items.add(Items.STRUCTURE_BLOCK.getDefaultInstance());
					items.add(Items.STRUCTURE_VOID.getDefaultInstance());
					items.add(Items.BARRIER.getDefaultInstance());
					items.add(Items.JIGSAW.getDefaultInstance());
					items.add(Items.DEBUG_STICK.getDefaultInstance());

					super.fillItemList(items);
				}
			};
		}
	}

	public static final ResourceLocation prefix(String name) {
		return new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT));
	}

	public static boolean isLoaded() {
		return INSTANCE != null;
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
}
