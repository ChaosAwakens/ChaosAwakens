package io.github.chaosawakens;

import io.github.chaosawakens.api.platform.Services;

public class ChaosAwakens {

    public static void init() {
        Services.REGISTRAR.setupRegistrar();
    }
}