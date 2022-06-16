package io.github.chaosawakens.server;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

public class ServerSetupEvent {
	public void onFMLServerSetupEvent(FMLDedicatedServerSetupEvent event) {
		ChaosAwakens.LOGGER.info("Inducing chaos upon the world...");
		if (ModList.get().isLoaded("theoneprobe")) {
			ChaosAwakens.LOGGER.info("Loading The One Probe plugin");
		}
	}
}
