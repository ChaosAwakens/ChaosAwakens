package io.github.chaosawakens;

import io.github.chaosawakens.api.asm.ClassFinder;
import io.github.chaosawakens.api.asm.annotations.LoadEarly;
import io.github.chaosawakens.api.platform.CAServices;

public class ChaosAwakens {

    /**
     * The main method responsible for initializing pretty much all Chaos Awakens content and whatnot.
     */
    public static void setup() {
        ClassFinder.discoverAnnotatedClasses(LoadEarly.class);

        CAServices.REGISTRAR.setupRegistrar();

        if (CAServices.PLATFORM.getPlatformName().equals("Fabric")) CAServices.NETWORK_MANAGER.setupNetworkHandler();
    }

    /**
     * Method for some tasks that generally need to be deferred to a specific mod-loading stage (Primarily for Forge/NeoForge).
     */
    public static void deferredSetup() {
        CAServices.NETWORK_MANAGER.setupNetworkHandler();
    }
}