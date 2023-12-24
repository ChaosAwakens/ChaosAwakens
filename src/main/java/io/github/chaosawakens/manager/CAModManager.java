package io.github.chaosawakens.manager;

import net.minecraftforge.eventbus.api.IEventBus;

public final class CAModManager {
	
	public static final void registerAll(IEventBus modBus, IEventBus forgeBus) {
		CAConfigManager.registerConfigs();
		CARegistryManager.registerRegistries(modBus);
		CAEventManager.registerEvents(modBus, forgeBus);
		CAModIntegrationManager.registerIntegration(modBus, forgeBus);
	}
}