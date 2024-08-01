package io.github.chaosawakens.api.services;

import io.github.chaosawakens.api.asm.ClassFinder;
import io.github.chaosawakens.api.asm.annotations.NetworkRegistrarEntry;
import io.github.chaosawakens.api.network.BasePacket;
import io.github.chaosawakens.api.network.NetworkSide;
import io.github.chaosawakens.api.platform.services.INetworkManager;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

public class FabricNetworkManager implements INetworkManager {
    private static final Object2ObjectLinkedOpenHashMap<Class<?>, BasePacket<?>> MAPPED_PACKETS = new Object2ObjectLinkedOpenHashMap<>();

    @Override
    public void setupNetworkHandler() {
        ClassFinder.discoverAnnotatedClasses(NetworkRegistrarEntry.class);
    }

    @Override
    public <MSGT> INetworkManager registerPacket(BasePacket<MSGT> packet) {
        NetworkSide targetSide = packet.targetSide();

        MAPPED_PACKETS.putIfAbsent(packet.packetClass(), packet);

        if (targetSide.equals(NetworkSide.C2S)) {
            ServerPlayNetworking.registerGlobalReceiver(packet.packetId(), ((targetServer, playerSender, serverPacketListener, buf, fabricPacketSender) -> {
                buf.readByte(); // Forge discriminator handling (monke see monke do)

                packet.packetDecoder().apply(buf);
                packet.packetHandler().handlePacket(playerSender, targetServer.getLevel(playerSender.level().dimension()), NetworkSide.C2S);
            }));
        } else if (targetSide.equals(NetworkSide.S2C)) {
            ClientPlayNetworking.registerGlobalReceiver(packet.packetId(), ((targetClient, clientPacketListener, buf, fabricPacketSender) -> {
                buf.readByte(); // Forge discriminator handling (monke see monke do)

                packet.packetDecoder().apply(buf);
                packet.packetHandler().handlePacket(targetClient.player, targetClient.level, NetworkSide.S2C);
            }));
        }
        return this;
    }

    @Override
    public <MSGT> void sendToServer(MSGT c2sPacket) {

    }

    @Override
    public <MSGT> void sendToClient(MSGT s2cPacket) {

    }

    @Override
    public <MSGT> void sendToAllClients(MSGT s2cPacket) {

    }

    @Override
    public <MSGT> void sendToTrackingClients(MSGT s2cPacket, Entity trackedEntity, boolean ignoreSelf) {

    }

    @Override
    public <MSGT> void sendToTrackingClients(MSGT s2cPacket, LevelChunk trackedChunk) {

    }

    @Override
    public <MSGT> void sendToClientsInDimension(MSGT s2cPacket, ResourceKey<Level> targetDim) {

    }

    @Override
    public <MSGT> void sendToClientsWithinRange(MSGT s2cPacket, ResourceKey<Level> targetDim, BlockPos originPos, double range) {

    }

    @Override
    public <MSGT> void sendToClient(MSGT s2cPacket, ServerPlayer targetPlayer) {

    }
}
