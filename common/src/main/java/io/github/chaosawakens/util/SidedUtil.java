package io.github.chaosawakens.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.Nullable;

/**
 * Pseudo-Utility Class for writing sided code without worrying about accidentally loading classes wherever they aren't meant to be loaded.
 * Primarily comprised of client-only methods.
 */
public final class SidedUtil {

    private SidedUtil() {
        throw new IllegalAccessError("Attempted to instantiate a Utility Class!");
    }

    @Nullable
    public static LocalPlayer getLocalPlayer() {
        return Minecraft.getInstance() == null ? null : Minecraft.getInstance().player;
    }

    @Nullable
    public static ClientLevel getClientLevel() {
        return Minecraft.getInstance() == null ? null : Minecraft.getInstance().level;
    }
}
