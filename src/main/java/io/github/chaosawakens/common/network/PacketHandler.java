package io.github.chaosawakens.common.network;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry
            .newSimpleChannel(ChaosAwakens.prefix("channel"),
                    () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void init() {
        int id = 0;
        CHANNEL.messageBuilder(PacketThrowPlayer.class, id++).encoder(PacketThrowPlayer::encode)
                .decoder(PacketThrowPlayer::new).consumer(PacketThrowPlayer.Handler::onMessage).add();
        CHANNEL.messageBuilder(PacketReach.class, id++).encoder(PacketReach::encode)
        .decoder(PacketReach::decode).consumer(PacketReach.Handler::reach).add();
    }
    
    public static void sendPacketToServer(Object packet) {
    	CHANNEL.sendToServer(packet);
    }
}