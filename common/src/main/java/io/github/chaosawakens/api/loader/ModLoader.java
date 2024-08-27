package io.github.chaosawakens.api.loader;

/**
 * A basic holder enum representing mod-loaders as objects.
 */
public enum ModLoader {
    FORGE("Forge"),
    FABRIC("Fabric");

    private final String platformName;

    ModLoader(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformName() {
        return platformName;
    }
}
