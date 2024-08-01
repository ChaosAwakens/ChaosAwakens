package io.github.chaosawakens;

import io.github.chaosawakens.api.platform.CAServices;

public class ChaosAwakens {

    /**
     * The main method responsible for initializing pretty much all Chaos Awakens content and whatnot.
     */
    public static void init() {
        CAServices.REGISTRAR.setupRegistrar();
    }
}