package io.github.chaosawakens;

import io.github.chaosawakens.api.platform.CAServices;

public class ChaosAwakens {

    public static void init() {
        CAServices.REGISTRAR.setupRegistrar();
    }
}