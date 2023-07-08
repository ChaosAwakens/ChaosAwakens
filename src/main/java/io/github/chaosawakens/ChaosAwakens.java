package io.github.chaosawakens;

import java.util.Locale;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.artifact.versioning.ArtifactVersion;

import io.github.chaosawakens.common.util.ObjectUtil;
import io.github.chaosawakens.manager.CAModManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import software.bernie.geckolib3.GeckoLib;

@Mod(ChaosAwakens.MODID)
public class ChaosAwakens {
	public static final String MODID = "chaosawakens";
	public static final String MODNAME = "Chaos Awakens";
	public static ArtifactVersion VERSION = null;
	public static final Logger LOGGER = LogManager.getLogger();
	private static ChaosAwakens INSTANCE;
	public static boolean DISABLE_IN_DEV = false;
	private static boolean DEVELOPMENT_ENVIRONMENT = false;

	public ChaosAwakens() {		
		GeckoLib.initialize();
		
		INSTANCE = this;
		Optional<? extends ModContainer> mod = ModList.get().getModContainerById(MODID);
		
		if (mod.isPresent()) VERSION = mod.get().getModInfo().getVersion();
		else LOGGER.warn("Could not get version from mod info!");

		LOGGER.debug(MODNAME + " Version is: " + VERSION);
		LOGGER.debug("Mod ID for " + MODNAME + " is: " + MODID);
		DEVELOPMENT_ENVIRONMENT = !FMLEnvironment.production && !DISABLE_IN_DEV;

		ObjectUtil.loadClass("io.github.chaosawakens.common.registry.CATags");
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		
		CAModManager.registerAll(modBus, forgeBus);
	}

	public static final ResourceLocation prefix(String name) {
		return new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT));
	}
	
	public static ChaosAwakens getInstance() {
		return INSTANCE;
	}

	public static boolean isLoaded() {
		return INSTANCE != null;
	}
	
	public static boolean isInDevEnv() {
		return DEVELOPMENT_ENVIRONMENT;
	}

	/**
	 * For those quick info checks to see if things are working, should be removed in releases or
	 * when you are done!
	 * @param <D> Type of the message
	 * @param domain Rather abstract, but basically where this is from
	 * @param message What you want to be printed (duh)
	 */
	public static <D> void debug(String domain, D message) {
		LOGGER.debug("[" + domain + "]: " + (message != null ? message.toString() : message));
	}
}
