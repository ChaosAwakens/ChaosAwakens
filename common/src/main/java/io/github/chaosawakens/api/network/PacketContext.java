package io.github.chaosawakens.api.network;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * The interface responsible for processing packet behaviour whenever a packet is received on any given side.
 */
@FunctionalInterface
public interface PacketContext {

    /**
     * The method which handles enqueued packet behaviour run on the target side of any given {@link BasePacket}.
     *
     * @param packetSender The {@link ServerPlayer} responsible for sending this packet, if it was C2S. Is otherwise {@link Minecraft#player}. May be {@code null},
     *                     so make sure to add nullity checks, <b>particularly if you're on the client</b>.
     * @param curLevel The current {@link Level} in which this packet is being processed, I.E. the {@link Level} on the target {@link NetworkSide}.
     * @param curSide The {@link NetworkSide} on which this packet has been received/is being processed.
     */
    void handlePacket(@Nullable Player packetSender, Level curLevel, NetworkSide curSide);
}
