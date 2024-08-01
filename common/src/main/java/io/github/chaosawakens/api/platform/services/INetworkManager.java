package io.github.chaosawakens.api.platform.services;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.network.BasePacket;
import io.github.chaosawakens.api.network.NetworkSide;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;

/**
 * Loader-agnostic interface used for dynamically registering and sending cross-loader packets without needing multiple methods, classes, or redundant loader-specific setup.
 *
 * @see BasePacket
 */
public interface INetworkManager {

    /**
     * Main method for this service interface, called in {@link ChaosAwakens} in order to load it and its loader-specific implementations accordingly. Should NOT be called anywhere else!
     */
    void setupNetworkHandler();

    /**
     * Method for registering S2C/C2S packets (based on the provided {@linkplain BasePacket BasePacket's} {@link NetworkSide} definition).
     *
     * @param packet The packet object to register.
     *
     * @return {@code this}.
     *
     * @param <MSGT> The type of the packet object.
     */
    <MSGT> INetworkManager registerPacket(BasePacket<MSGT> packet);

    /**
     * Sends a C2S packet (client -> server) from the current client.
     *
     * @param c2sPacket The packet object to send.
     *
     * @param <MSGT> The type of the packet object.
     */
    <MSGT> void sendToServer(MSGT c2sPacket);

    /**
     * Sends an S2C packet (server -> client) to the current client.
     *
     * @param s2cPacket The packet object to send.
     *
     * @param <MSGT> The type of the packet object.
     */
    <MSGT> void sendToClient(MSGT s2cPacket);

    /**
     * Sends an S2C packet (server -> client) to all connected clients on the current server.
     *
     * @param s2cPacket The packet object to send.
     *
     * @param <MSGT> The type of the packet object.
     */
    <MSGT> void sendToAllClients(MSGT s2cPacket);

    /**
     * Sends an S2C packet (server -> client) to all connected clients tracking the specified {@code trackedEntity},
     * as well as the {@code trackedEntity} itself if it's a {@link Player} and {@code ignoreSelf} is set to {@code false}.
     *
     * @param s2cPacket The packet object to send.
     * @param trackedEntity The tracked {@link Entity}.
     * @param ignoreSelf Whether to ignore (I.E. not send a packet to) the {@code trackedEntity} (if it's a {@link Player}).
     *
     * @param <MSGT> The type of the packet object.
     */
    <MSGT> void sendToTrackingClients(MSGT s2cPacket, Entity trackedEntity, boolean ignoreSelf);

    /**
     * Overloaded variant of {@link #sendToTrackingClients(MSGT, Entity, boolean)} with {@code ignoreSelf} set to {@code false}.
     *
     * @param s2cPacket The packet object to send.
     * @param trackedEntity The tracked {@link Entity}.
     *
     * @param <MSGT> The type of the packet object.
     */
    default <MSGT> void sendToTrackingClients(MSGT s2cPacket, Entity trackedEntity) {
        sendToTrackingClients(s2cPacket, trackedEntity, false);
    }

    /**
     * Sends an S2C packet (server -> client) to all connected clients tracking the specified {@code trackedChunk}.
     *
     * @param s2cPacket The packet object to send.
     * @param trackedChunk The tracked {@linkplain ChunkAccess Chunk}.
     *
     * @param <MSGT> The type of the packet object.
     */
    <MSGT> void sendToTrackingClients(MSGT s2cPacket, ChunkAccess trackedChunk);

    /**
     * Sends an S2C packet (server -> client) to all connected clients in the specified {@code targetDim}.
     *
     * @param s2cPacket The packet object to send.
     * @param targetDim The target dimension's {@link ResourceKey}.
     *
     * @param <MSGT> The type of the packet object.
     */
    <MSGT> void sendToClientsInDimension(MSGT s2cPacket, ResourceKey<Level> targetDim);

    /**
     * Sends an S2C packet (server -> client) to all connected clients within the specified {@code range} of the specified {@code originPos}.
     *
     * @param s2cPacket The packet object to send.
     * @param originPos The origin {@link BlockPos} from which range will be counted.
     * @param range The range from the specified {@code originPos} within which clients will receive the packet.
     *
     * @param <MSGT> The type of the packet object.
     */
    <MSGT> void sendToClientsWithinRange(MSGT s2cPacket, BlockPos originPos, double range);

    /**
     * Sends an S2C packet (server -> client) to the specified {@code targetPlayer}.
     *
     * @param s2cPacket The packet object to send.
     * @param targetPlayer The target {@link ServerPlayer}.
     *
     * @param <MSGT> The type of the packet object.
     */
    <MSGT> void sendToClient(MSGT s2cPacket, ServerPlayer targetPlayer);
}
