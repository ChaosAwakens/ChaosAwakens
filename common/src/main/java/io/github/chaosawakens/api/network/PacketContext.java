package io.github.chaosawakens.api.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

/**
 * The interface responsible for processing packet behaviour whenever a packet is received on any given side.
 */
@FunctionalInterface
public interface PacketContext {

    /**
     * The method which handles enqueued packet behaviour run on the target side of any given {@link BasePacket}.
     *
     * @param packetSender The {@link ServerPlayer} responsible for sending this packet, if it was C2S. Is otherwise {@code null}.
     * @param curLevel The current {@link Level} in which this packet is being processed, I.E. the level on the target side.
     * @param curSide The {@link NetworkSide} on which this packet has been received/is being processed.
     */
    void handlePacket(@Nullable Player packetSender, Level curLevel, NetworkSide curSide);
}
