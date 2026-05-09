package io.github.chaosawakens.core.network.s2c;

import com.mememan.nexus.network.PacketContext;
import net.minecraft.network.FriendlyByteBuf;

public class AnimationDataSyncPacket {

    public AnimationDataSyncPacket() {

    }

    public AnimationDataSyncPacket(FriendlyByteBuf buf) {

    }

    public void encode(FriendlyByteBuf buf) {

    }

    public static PacketContext handle(AnimationDataSyncPacket packet) {
        return (nullablePlayerOwner, currentLevel, currentConnection, currentSide) -> {

        };
    }
}
