package io.github.chaosawakens.api.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public interface ICAPacket {
	void encode(PacketBuffer buf);
	void onRecieve(Supplier<Context> ctx);
}