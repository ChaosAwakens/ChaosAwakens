package io.github.chaosawakens.common.network;

import com.google.common.base.Supplier;

import io.github.chaosawakens.common.registry.CAAttributes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PacketReach {
	private final float motion;
	private final int id;
	
	public PacketReach(float motion, int id) {
		this.motion = motion;
		this.id = id;
	}
	
	public static void encode(PacketReach packet, PacketBuffer buf) {
		buf.writeFloat(packet.motion);
		buf.writeInt(packet.id);
	}
	
	//Useless constructor because yes
	public PacketReach(PacketBuffer buf) {
		motion = buf.readFloat();
		id = buf.readInt();
	}
	
	public static PacketReach decode(PacketBuffer buf) {
		return new PacketReach(buf.readFloat(), buf.readInt());
	}
	
	public static class Handler {
		
		public static void handleReach(PacketReach packet, Supplier<Context> networkContext) {
			if (packet == null) return;
			
			networkContext.get().enqueueWork(() -> {
				ServerPlayerEntity player = networkContext.get().getSender();
				Entity target = player.level.getEntity(packet.id);
				ItemStack weapon = player.getMainHandItem();
				double attackReachDistance = player.getAttributeValue(CAAttributes.ATTACK_REACH.get());
				double distanceToTargetSq = player.distanceToSqr(target);
				double attackReachSq = attackReachDistance * attackReachDistance;
				
				if (weapon.isEmpty()) return;
				if (player == null || target == null || player.level == null || target.level == null) return;
				if (attackReachDistance == CAAttributes.ATTACK_REACH.get().getDefaultValue()) return;
				
				if (attackReachSq >= distanceToTargetSq) {
					player.getPersistentData().putFloat("chaosawakens_motion", packet.motion);
					player.doHurtTarget(target);
				}
				
				player.getItemInHand(Hand.MAIN_HAND);
				player.resetLastActionTime();
			});
		}
		
	}

}
