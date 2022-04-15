package io.github.chaosawakens.common.network;


import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import io.github.chaosawakens.common.registry.CAItems;
//Test
public class PacketReach {
	private int id;
	private float motion;

    public PacketReach(int id, float motion) {
    	this.id = id;
    	this.motion = motion;
    }

    public static PacketReach decode(PacketBuffer buf) {
    	return new PacketReach(buf.readInt(), buf.readFloat());
    }

    public static void encode(PacketReach packet, PacketBuffer buf) {
        buf.writeInt(packet.id);
        buf.writeFloat(packet.motion);
    }

    public static class Handler {

        public static void reach(PacketReach packet, Supplier<NetworkEvent.Context> ctx) {
        	ctx.get().enqueueWork(() -> {
        		ServerPlayerEntity player = ctx.get().getSender();
        		Entity target = player.level.getEntity(packet.id);
        		
        		ItemStack weapon = player.getMainHandItem();
        		
        		double reachDistance = player.getAttributeValue(ForgeMod.REACH_DISTANCE.get())  + 4.0D;
        		double reachDistanceSq = reachDistance * reachDistance;
        		double distanceToTargetSq = player.distanceToSqr(target);
        		
        		boolean attitudeAdjusterReach = weapon.getItem() == CAItems.ATTITUDE_ADJUSTER.get();
        		boolean berthaReach = weapon.getItem() == CAItems.BIG_BERTHA.get();
        		boolean battleAxeReach = weapon.getItem() == CAItems.BATTLE_AXE.get();
        		boolean queenScaleBattleAxeReach = weapon.getItem() == CAItems.QUEEN_SCALE_BATTLE_AXE.get();
        		boolean royalGuardianSwordReach = weapon.getItem() == CAItems.ROYAL_GUARDIAN_SWORD.get();
        		boolean slayerChainsawReach = weapon.getItem() == CAItems.SLAYER_CHAINSAW.get();
        		boolean canReach = attitudeAdjusterReach && berthaReach && battleAxeReach && queenScaleBattleAxeReach && royalGuardianSwordReach && slayerChainsawReach;     		
        		
        		if (reachDistance == ForgeMod.REACH_DISTANCE.get().getDefaultValue()) return;
        		if (weapon.isEmpty()) return;
        		
        		if (reachDistanceSq < distanceToTargetSq && canReach) {
        			player.getPersistentData().putFloat("chaosawakens_motion", packet.motion);
        			player.doHurtTarget(target);
        		}
        		
        		player.swing(Hand.MAIN_HAND);
//        		player.broadcastBreakEvent(Hand.MAIN_HAND);       		
        	});
        }
    }
}