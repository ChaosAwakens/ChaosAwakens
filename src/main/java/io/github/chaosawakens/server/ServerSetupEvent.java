package io.github.chaosawakens.server;

import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

/**
 * @author invalid2
 * @author RaveTr
 */
public class ServerSetupEvent {

	public void onFMLServerSetupEvent(FMLDedicatedServerSetupEvent event) {
		System.out.println("Starting server");
	}
}
