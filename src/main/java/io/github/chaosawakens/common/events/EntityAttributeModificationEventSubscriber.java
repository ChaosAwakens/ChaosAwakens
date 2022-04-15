package io.github.chaosawakens.common.events;

import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

public class EntityAttributeModificationEventSubscriber {
	public static double reachValue = 4.5;
	
	@EventBusSubscriber(bus = Bus.FORGE)
	public static class ReachRegisterHelper {
		
//		@SubscribeEvent
		public static double setReachValue(double reach) {
			return reachValue = reach;
		}
		
		@SubscribeEvent
		public static void playerReach(PlayerEvent event) {
			PlayerEntity player = event.getPlayer();
			
			if (!(player instanceof PlayerEntity)) return;
			
			ItemStack itemInHand = player.getMainHandItem();
			ItemStack attitudeAdjuster = new ItemStack(CAItems.ATTITUDE_ADJUSTER.get());
			ItemStack bigBertha = new ItemStack(CAItems.BIG_BERTHA.get());
			ItemStack battleAxe = new ItemStack(CAItems.BATTLE_AXE.get());
			ItemStack queenBattleAxe = new ItemStack(CAItems.QUEEN_SCALE_BATTLE_AXE.get());
			ItemStack royalGuardianSword = new ItemStack(CAItems.ROYAL_GUARDIAN_SWORD.get());
			ItemStack slayerChainsaw = new ItemStack(CAItems.SLAYER_CHAINSAW.get());
			
			if (itemInHand == attitudeAdjuster) {
				player.getAttribute(ForgeMod.REACH_DISTANCE.get()).setBaseValue(reachValue + 2.5);
	//			reachValue = 7.0D;
			}
			
			if (itemInHand == bigBertha) {
				player.getAttribute(ForgeMod.REACH_DISTANCE.get()).setBaseValue(reachValue * 2);
		//		reachValue = 9.0D;
			}
			
			if (itemInHand == battleAxe) {
				player.getAttribute(ForgeMod.REACH_DISTANCE.get()).setBaseValue(reachValue + 3.5);
		//		reachValue = 8.0D;
			}
			
			if (itemInHand == queenBattleAxe) {
				player.getAttribute(ForgeMod.REACH_DISTANCE.get()).setBaseValue(reachValue + 3.5);
	//			reachValue = 8.0D;
			}
			
			if (itemInHand == royalGuardianSword) {
				player.getAttribute(ForgeMod.REACH_DISTANCE.get()).setBaseValue(reachValue * 2);
	//			reachValue = 10.0D;
			}
			
			if (itemInHand == slayerChainsaw) {
				player.getAttribute(ForgeMod.REACH_DISTANCE.get()).setBaseValue(reachValue + 1.5);
	//			reachValue = 6.0D;
			}
		}
		
	
	}
	
	public static void onEntityAttributeModificationEvent(EntityAttributeModificationEvent event) {
//		ExtendedHitWeaponItem.LAZY.ifPresent(l -> l.get(ForgeMod.REACH_DISTANCE.get()).forEach(g -> g.getAmount()));
		event.add(EntityType.PLAYER, ForgeMod.REACH_DISTANCE.get(), ForgeMod.REACH_DISTANCE.get().getDefaultValue());
		event.add(CAEntityTypes.HERCULES_BEETLE.get(), ForgeMod.REACH_DISTANCE.get(), 6.0D);
	}

}
