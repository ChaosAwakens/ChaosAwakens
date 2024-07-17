package io.github.chaosawakens.manager;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.integration.jade.CAJadePlugin;
import io.github.chaosawakens.common.integration.jer.CAJER;
import io.github.chaosawakens.common.integration.top.TheOneProbePlugin;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CAModIntegrationManager {

	protected static void registerIntegration(IEventBus modBus, IEventBus forgeBus) {
		ChaosAwakens.debug("MANAGER [Integration]", "Registering mod integration...");

		if (checkModPresence("jade")) CAJadePlugin.register();
		if (checkModPresence("theoneprobe")) TheOneProbePlugin.register();
//		if (checkModPresence(IronChests.MODID) || DatagenModLoader.isRunningDataGen()) CAIronChestsPlugin.register();

		handleEvents(modBus, forgeBus);

		ChaosAwakens.debug("MANAGER [Integration]", "Successfully registered mod integration!");
	}

	private static void handleEvents(IEventBus modBus, IEventBus forgeBus) {
		if (modBus != null && forgeBus != null) {
			modBus.addListener(CAModIntegrationManager::handleCommonSetupModIntegration);
		}
	}

	private static void handleCommonSetupModIntegration(final FMLCommonSetupEvent event) {
		if (checkModPresence("jeresources")) CAJER.register();
	}

	public static boolean checkModPresence(String modid) {
		return ModList.get().isLoaded(modid);
	}
}