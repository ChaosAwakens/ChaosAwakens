package io.github.chaosawakens.api.services;

import io.github.chaosawakens.api.asm.ClassFinder;
import io.github.chaosawakens.api.asm.annotations.NetworkRegistrarEntry;
import io.github.chaosawakens.api.network.BasePacket;
import io.github.chaosawakens.api.platform.services.INetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

public class FabricNetworkManager implements INetworkManager {

    @Override
    public void setupNetworkHandler() {
        ClassFinder.discoverAnnotatedClasses(NetworkRegistrarEntry.class);
    }

    @Override
    public <MSGT> INetworkManager registerPacket(BasePacket<MSGT> packet) {
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
