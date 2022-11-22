package io.github.chaosawakens.common.network;

import net.minecraft.client.Minecraft;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketThrowPlayer {
	private final float motionX;
	private final float motionY;
	private final float motionZ;

	public PacketThrowPlayer(float motionX, float motionY, float motionZ) {
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
	}

	public PacketThrowPlayer(PacketBuffer buf) {
		motionX = buf.readFloat();
		motionY = buf.readFloat();
		motionZ = buf.readFloat();
	}

	public void encode(PacketBuffer buf) {
		buf.writeFloat(motionX);
		buf.writeFloat(motionY);
		buf.writeFloat(motionZ);
	}

	public static class Handler {
		@SuppressWarnings("resource")
		public static boolean onMessage(PacketThrowPlayer message, Supplier<NetworkEvent.Context> ctx) {			
			ctx.get().enqueueWork(() ->{				
				Minecraft.getInstance().player.push(message.motionX, message.motionY, message.motionZ);			
			});		
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
