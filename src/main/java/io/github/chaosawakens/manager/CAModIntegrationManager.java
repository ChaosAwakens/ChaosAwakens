package io.github.chaosawakens.manager;

import io.github.chaosawakens.common.integration.jade.CAJadePlugin;
import io.github.chaosawakens.common.integration.jer.CAJER;
import io.github.chaosawakens.common.integration.top.TheOneProbePlugin;
import jeresources.JEResources;
import mcjty.theoneprobe.TheOneProbe;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import snownee.jade.Jade;

public class CAModIntegrationManager {

	public static void registerIntegration(IEventBus modBus, IEventBus forgeBus) {
		if (checkModPresence(Jade.MODID)) CAJadePlugin.registerJadeCompat();
		if (checkModPresence(TheOneProbe.MODID)) TheOneProbePlugin.register();
//		if (checkModPresence(IronChests.MODID) || DatagenModLoader.isRunningDataGen()) CAIronChestsPlugin.register();

		handleEvents(modBus, forgeBus);
	}

	private static void handleEvents(IEventBus modBus, IEventBus forgeBus) {
		if (modBus != null && forgeBus != null) {
			modBus.addListener(CAModIntegrationManager::handleCommonSetupModIntegration);
		}
	}

	private static void handleCommonSetupModIntegration(final FMLCommonSetupEvent event) {
		if (checkModPresence(JEResources.ID)) CAJER.register();
	}

	public static boolean checkModPresence(String modid) {
		return ModList.get().isLoaded(modid);
	}

}
