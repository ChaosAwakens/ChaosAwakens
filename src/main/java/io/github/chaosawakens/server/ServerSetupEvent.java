package io.github.chaosawakens.server;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

/**
 * @author invalid2
 */
public class ServerSetupEvent {

    public void onFMLServerSetupEvent(FMLDedicatedServerSetupEvent event) {
    	ChaosAwakens.LOGGER.info("Inducing chaos upon the world...");
    }
}
