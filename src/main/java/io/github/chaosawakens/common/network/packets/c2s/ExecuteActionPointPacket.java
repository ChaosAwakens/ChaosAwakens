package io.github.chaosawakens.common.network.packets.c2s;

import java.util.function.Supplier;

import io.github.chaosawakens.api.network.ICAPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ExecuteActionPointPacket implements ICAPacket {
	private final double actionPointTick;
	
	public ExecuteActionPointPacket(double actionPointTick) {
		this.actionPointTick = actionPointTick;
	}
	
	public static ExecuteActionPointPacket decode(PacketBuffer buf) {
		return new ExecuteActionPointPacket(buf.readDouble());
	}

	@Override
	public void encode(PacketBuffer buf) {
		buf.writeDouble(actionPointTick);
	}

	@Override
	public void onRecieve(Supplier<Context> ctx) {
		
	}
}
