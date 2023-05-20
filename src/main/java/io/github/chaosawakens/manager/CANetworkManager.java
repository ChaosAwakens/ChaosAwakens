package io.github.chaosawakens.manager;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.network.ICAPacket;
import io.github.chaosawakens.common.network.packets.c2s.AnimationDataSyncPacket;
import io.github.chaosawakens.common.network.packets.s2c.AnimationStopPacket;
import io.github.chaosawakens.common.network.packets.s2c.AnimationTriggerPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class CANetworkManager {
	private static final String PROTOCOL_VERSION = "1";
	private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(ChaosAwakens.prefix("channel"), () ->
			      PROTOCOL_VERSION, 
			      PROTOCOL_VERSION::equals,
			      PROTOCOL_VERSION::equals);

	public static void registerPackets() {
		int id = 0;
		registerCTSPackets(id);
		registerSTCPackets(id);
	}
	
	private static void registerCTSPackets(int id) {
		CHANNEL.messageBuilder(AnimationDataSyncPacket.class, id++)
		.encoder(AnimationDataSyncPacket::encode)
		.decoder(AnimationDataSyncPacket::decode)
		.consumer(AnimationDataSyncPacket::onRecieve)
		.add();
	}
	
	private static void registerSTCPackets(int id) {
		CHANNEL.messageBuilder(AnimationTriggerPacket.class, id++)
			.encoder(AnimationTriggerPacket::encode)
			.decoder(AnimationTriggerPacket::decode)
			.consumer(AnimationTriggerPacket::onRecieve)
			.add();
		CHANNEL.messageBuilder(AnimationStopPacket.class, id++)
			.encoder(AnimationStopPacket::encode)
			.decoder(AnimationStopPacket::decode)
			.consumer(AnimationStopPacket::onRecieve)
			.add();
	}

	public static void sendPacketToServer(ICAPacket packet) {
		CHANNEL.sendToServer(packet);
	}
	
	public static void sendPacketToClient(ICAPacket packet) {
		CHANNEL.sendTo(packet, Minecraft.getInstance().getConnection().getConnection(), NetworkDirection.PLAY_TO_CLIENT);
	}
	
	/**
	 * Send a tracking entity/player packet to all tracking clients from the server.
	 * @param packet Packet to send (S2C)
	 * @param trackedEntity Tracked Entity
	 */
	public static void sendEntityTrackingPacket(ICAPacket packet, Entity trackedEntity) {
		CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> trackedEntity), packet);
	}
}
