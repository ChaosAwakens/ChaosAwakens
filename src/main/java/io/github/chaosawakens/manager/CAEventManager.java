package io.github.chaosawakens.manager;

import io.github.chaosawakens.client.events.CABossBarRenderer;
import io.github.chaosawakens.client.events.CAClientMiscEvents;
import io.github.chaosawakens.client.events.CAClientSetupEvents;
import io.github.chaosawakens.common.events.CACommonSetupEvents;
import io.github.chaosawakens.common.events.CAMiscEvents;
import io.github.chaosawakens.server.events.ServerSetupEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class CAEventManager {

	public static void registerEvents(IEventBus modBus, IEventBus forgeBus) {		
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
		modBus.addListener(CACommonSetupEvents::onEntityAttributeCreationEvent);
		modBus.addListener(CACommonSetupEvents::onFMLCommonSetupEvent);
		modBus.addListener(CACommonSetupEvents::onFMLLoadCompleteEvent);
		modBus.addListener(CACommonSetupEvents::onGatherDataEvent);
		modBus.addGenericListener(Block.class, CACommonSetupEvents::onRemapBlocksEvent);
		modBus.addGenericListener(EntityType.class, CACommonSetupEvents::onRemapEntitiesEvent);
		modBus.addGenericListener(Item.class, CACommonSetupEvents::onRemapItemsEvent);

		forgeBus.addListener(EventPriority.HIGH, CACommonSetupEvents::onBiomeLoadingEvent);
		forgeBus.addListener(EventPriority.HIGH, CACommonSetupEvents::onDimensionalSpacingWorldLoadEvent);
		forgeBus.addListener(CACommonSetupEvents::onRegisterCommandsEvent);
		forgeBus.addListener(CACommonSetupEvents::onRegisterFurnaceFuelEvent);
		forgeBus.addListener(CACommonSetupEvents::onVillagerTradesEvent);
		forgeBus.addListener(CACommonSetupEvents::onWandererTradesEvent);
		
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
