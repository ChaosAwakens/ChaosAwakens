package io.github.chaosawakens.core.network;

import com.mememan.nexus.asm.annotations.NetworkRegistrarEntry;
import com.mememan.nexus.network.BasePacket;
import com.mememan.nexus.network.NetworkSide;
import com.mememan.nexus.network.PacketContext;
import com.mememan.nexus.platform.NexusServices;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.core.network.s2c.AnimationDataSyncPacket;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.BiConsumer;
import java.util.function.Function;

@NetworkRegistrarEntry
public class CAPackets {

    // S2C
    public static final BasePacket<AnimationDataSyncPacket> ANIMATION_DATA_SYNC_PACKET = registerS2CPacket(AnimationDataSyncPacket.class, AnimationDataSyncPacket::encode, AnimationDataSyncPacket::new, AnimationDataSyncPacket::handle);

    // C2S

    private static <MSGT> BasePacket<MSGT> createPacket(String modId, Class<MSGT> packetClass, BiConsumer<MSGT, FriendlyByteBuf> packetEncoder, Function<FriendlyByteBuf, MSGT> packetDecoder, Function<MSGT, PacketContext> packetHandler, NetworkSide targetSide) {
        return new BasePacket<>(modId, packetClass, packetEncoder, packetDecoder, packetHandler, targetSide);
    }

    private static <MSGT> BasePacket<MSGT> createS2CPacket(String modId, Class<MSGT> packetClass, BiConsumer<MSGT, FriendlyByteBuf> packetEncoder, Function<FriendlyByteBuf, MSGT> packetDecoder, Function<MSGT, PacketContext> packetHandler) {
        return createPacket(modId, packetClass, packetEncoder, packetDecoder, packetHandler, NetworkSide.S2C);
    }

    private static <MSGT> BasePacket<MSGT> createC2SPacket(String modId, Class<MSGT> packetClass, BiConsumer<MSGT, FriendlyByteBuf> packetEncoder, Function<FriendlyByteBuf, MSGT> packetDecoder, Function<MSGT, PacketContext> packetHandler) {
        return createPacket(modId, packetClass, packetEncoder, packetDecoder, packetHandler, NetworkSide.C2S);
    }

    private static <MSGT> BasePacket<MSGT> registerPacket(BasePacket<MSGT> packet) {
        return NexusServices.NETWORK_MANAGER.registerPacket(packet);
    }

    private static <MSGT> BasePacket<MSGT> registerPacket(String modId, Class<MSGT> packetClass, BiConsumer<MSGT, FriendlyByteBuf> packetEncoder, Function<FriendlyByteBuf, MSGT> packetDecoder, Function<MSGT, PacketContext> packetHandler, NetworkSide targetSide) {
        return registerPacket(createPacket(modId, packetClass, packetEncoder, packetDecoder, packetHandler, targetSide));
    }

    private static <MSGT> BasePacket<MSGT> registerS2CPacket(String modId, Class<MSGT> packetClass, BiConsumer<MSGT, FriendlyByteBuf> packetEncoder, Function<FriendlyByteBuf, MSGT> packetDecoder, Function<MSGT, PacketContext> packetHandler) {
        return registerPacket(createS2CPacket(modId, packetClass, packetEncoder, packetDecoder, packetHandler));
    }

    private static <MSGT> BasePacket<MSGT> registerC2SPacket(String modId, Class<MSGT> packetClass, BiConsumer<MSGT, FriendlyByteBuf> packetEncoder, Function<FriendlyByteBuf, MSGT> packetDecoder, Function<MSGT, PacketContext> packetHandler) {
        return registerPacket(createC2SPacket(modId, packetClass, packetEncoder, packetDecoder, packetHandler));
    }

    private static <MSGT> BasePacket<MSGT> registerS2CPacket(Class<MSGT> packetClass, BiConsumer<MSGT, FriendlyByteBuf> packetEncoder, Function<FriendlyByteBuf, MSGT> packetDecoder, Function<MSGT, PacketContext> packetHandler) {
        return registerS2CPacket(CAConstants.MOD_ID, packetClass, packetEncoder, packetDecoder, packetHandler);
    }

    private static <MSGT> BasePacket<MSGT> registerC2SPacket(Class<MSGT> packetClass, BiConsumer<MSGT, FriendlyByteBuf> packetEncoder, Function<FriendlyByteBuf, MSGT> packetDecoder, Function<MSGT, PacketContext> packetHandler) {
        return registerC2SPacket(CAConstants.MOD_ID, packetClass, packetEncoder, packetDecoder, packetHandler);
    }
}
