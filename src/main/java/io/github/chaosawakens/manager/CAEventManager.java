package io.github.chaosawakens.manager;

import io.github.chaosawakens.client.events.CABossBarRenderer;
import io.github.chaosawakens.client.events.CAClientMiscEvents;
import io.github.chaosawakens.client.events.CAClientSetupEvents;
import io.github.chaosawakens.common.events.CACommonSetupEvents.ForgeSetupEvents;
import io.github.chaosawakens.common.events.CACommonSetupEvents.ModSetupEvents;
import io.github.chaosawakens.common.events.CAMiscEvents;
import io.github.chaosawakens.server.events.ServerSetupEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class CAEventManager {

	protected static void registerEvents(IEventBus modBus, IEventBus forgeBus) {		
		registerClientEvents(modBus, forgeBus);
		registerCommonEvents(modBus, forgeBus);
		registerServerEvents(modBus, forgeBus);
	}

	private static void registerClientEvents(IEventBus modBus, IEventBus forgeBus) {
		if (FMLEnvironment.dist.equals(Dist.CLIENT)) {
			modBus.register(CAClientSetupEvents.class);			
			forgeBus.register(CAClientMiscEvents.class);
			//BOSSBAR EVENT TODO 
			forgeBus.addListener(CABossBarRenderer::onBossInfoRender);
		}
	}

	private static void registerCommonEvents(IEventBus modBus, IEventBus forgeBus) {		
		modBus.register(ModSetupEvents.class);

		forgeBus.register(ForgeSetupEvents.class);
		forgeBus.register(CAMiscEvents.class);
	}

	private static void registerServerEvents(IEventBus modBus, IEventBus forgeBus) {
		if (FMLEnvironment.dist.equals(Dist.DEDICATED_SERVER)) {
			modBus.addListener(ServerSetupEvent::onFMLServerSetupEvent);

			forgeBus.addListener(ServerSetupEvent::onFMLServerStartEvent);
			forgeBus.addListener(ServerSetupEvent::onFMLServerStartingEvent);
		}
	}
}
