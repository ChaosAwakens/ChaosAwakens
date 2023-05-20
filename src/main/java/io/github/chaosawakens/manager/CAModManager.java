package io.github.chaosawakens.manager;

import net.minecraftforge.eventbus.api.IEventBus;

public class CAModManager {
	
	public static final void registerAll(IEventBus modBus, IEventBus forgeBus) {
		CAConfigManager.registerConfigs();
		CAEventManager.registerEvents(modBus, forgeBus);		
		CARegistryManager.registerRegistries(modBus);
		CAModIntegrationManager.registerIntegration(modBus, forgeBus);
	}
}
