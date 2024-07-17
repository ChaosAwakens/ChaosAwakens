package io.github.chaosawakens.manager;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraftforge.eventbus.api.IEventBus;

public final class CAModManager {
	
	public static final void registerAll(IEventBus modBus, IEventBus forgeBus) {
		ChaosAwakens.debug("MANAGER", "Initializing mod...");

		CAConfigManager.registerConfigs();
		CARegistryManager.registerRegistries(modBus);
		CAEventManager.registerEvents(modBus, forgeBus);
		CAModIntegrationManager.registerIntegration(modBus, forgeBus);

		ChaosAwakens.debug("MANAGER", "Successfully registered all mod-related components!");
	}
}