package io.github.chaosawakens.api.loader;

import net.minecraft.world.level.Level;

/**
 * A basic holder {@code enum} representing sides as objects.
 * <br></br>
 * This should only ever be referred to by dependant mods either for checking physical sides or checking logical sides
 * in the absence of any {@link Level} context. Note that this still checks for the physical side regardless.
 * <br></br>
 * For checking logical sides, {@link Level#isClientSide()} (or derived {@code instanceof} checks, e.g. {@code someLevel
 * instanceof ServerLevel}) should always be preferred when available.
 *
 * @see <a href="https://wiki.fabricmc.net/tutorial:side">Fabric Wiki on sides</a>
 * @see <a href="https://docs.minecraftforge.net/en/1.20.x/concepts/sides/">Forge Wiki on sides</a>
 * @see <a href="https://docs.neoforged.net/docs/concepts/sides">NeoForge Wiki on sides</a>
 */
public enum EnvironmentSide {
    CLIENT("Client"),
    DEDICATED_SERVER("Dedicated Server");

    private final String sideName;

    EnvironmentSide(String sideName) {
        this.sideName = sideName;
    }

    public String getSideName() {
        return sideName;
    }

    /**
     * Whether this side is the physical client.
     *
     * @return Whether this side is the physical client.
     */
    public boolean isClient() {
        return this == CLIENT;
    }

    /**
     * Whether this side is the physical server.
     *
     * @return Whether this side is the physical server.
     */
    public boolean isDedicatedServer() {
        return this == DEDICATED_SERVER;
    }
}
