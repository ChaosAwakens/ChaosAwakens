package io.github.chaosawakens.core.network.s2c;

import com.mememan.nexus.network.PacketContext;
import net.minecraft.network.FriendlyByteBuf;

public class SkeletalDataSyncPacket {

    public SkeletalDataSyncPacket() {

    }

    public SkeletalDataSyncPacket(FriendlyByteBuf buf) {

    }

    public void encode(FriendlyByteBuf buf) {

    }

    public static PacketContext handle(SkeletalDataSyncPacket packet) {
        return (nullablePlayerOwner, currentLevel, currentConnection, currentSide) -> {

        };
    }
}
