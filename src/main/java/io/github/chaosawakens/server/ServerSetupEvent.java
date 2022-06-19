package io.github.chaosawakens.server;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

public class ServerSetupEvent {
	public static void onFMLServerSetupEvent(FMLDedicatedServerSetupEvent event) {
		ChaosAwakens.LOGGER.info("Inducing chaos upon the world...");
		if (ModList.get().isLoaded("theoneprobe")) ChaosAwakens.LOGGER.info("[" + ChaosAwakens.MODNAME + "]: " + "Loading The One Probe plugin");
		if (ModList.get().isLoaded("jade")) ChaosAwakens.LOGGER.info("[" + ChaosAwakens.MODNAME + "]: " + "Loading Jade plugin");
		if (ModList.get().isLoaded("jei")) ChaosAwakens.LOGGER.info("[" + ChaosAwakens.MODNAME + "]: " + "Loading Just Enough Items plugin");
		if (ModList.get().isLoaded("jeresources")) ChaosAwakens.LOGGER.info("[" + ChaosAwakens.MODNAME + "]: " + "Loading Just Enough Resources plugin");
		if (ModList.get().isLoaded("jeed")) ChaosAwakens.LOGGER.info("[" + ChaosAwakens.MODNAME + "]: " + "Loading Just Enough Effect Descriptions plugin");
		if (ModList.get().isLoaded("projecte")) ChaosAwakens.LOGGER.info("[" + ChaosAwakens.MODNAME + "]: " + "Loading ProjectE plugin");
	}
}
