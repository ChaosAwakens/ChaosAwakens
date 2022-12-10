package io.github.chaosawakens.server;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.server.config.CAServerConfig;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

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
	
	@SuppressWarnings("unused")
	public static void onFMLServerStartEvent(FMLServerStartedEvent event) {
		if (ChaosAwakens.isLoaded()) {
			MinecraftServer server = event.getServer();
			ChaosAwakens.LOGGER.info("Successfully loaded Chaos Awakens!");			
		} else {
			ChaosAwakens.LOGGER.fatal("There was an error loading Chaos Awakens, this may be due to file corruption or some other error. Please try restarting the server or reinstalling the mod.");
			event.getServer().close();
		}
		
	}
	
	public static void onFMLServerStartingEvent(FMLServerStartingEvent event) {
		MinecraftServer server = event.getServer();
		boolean na = false;
		
		if (CAServerConfig.SERVER.debugLogging.get()) {						
			if (!server.isFlightAllowed() && server.getDefaultGameType().isSurvival()) {
				na = true;
				ChaosAwakens.LOGGER.warn("Flight isn't enabled on this server. It is recommended that you enable it in order to avoid repetetive kicks or bans through the use of modded items.");				
				if (CAServerConfig.SERVER.modServerPermission.get()) {
					ChaosAwakens.LOGGER.debug("Setting server property 'allow-flight' to true...");			
					server.setFlightAllowed(true);			
					if (server.isFlightAllowed() && na) ChaosAwakens.LOGGER.debug("Successfully enabled flight!");			
				} else {				
					ChaosAwakens.LOGGER.warn("Looks like Chaos Awakens doesn't have permission to change server settings, falling back from changing server settings.");			
				}			
			}
			//Insert more debug stuff here
			
		}
	}
}
