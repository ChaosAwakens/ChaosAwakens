package io.github.chaosawakens.common.items;

import io.github.chaosawakens.common.entity.base.AnimatableRideableAnimalEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BeetrootOnAStickItem extends Item {
	private final int consumeItemDamage;

	public BeetrootOnAStickItem(Properties prop, int damage) {
		super(prop);
		this.consumeItemDamage = damage;
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack mainHandItem = player.getItemInHand(hand);
		
		if (!world.isClientSide) {
			Entity vehicleEntity = player.getVehicle();
			
			if (player.isPassenger() && vehicleEntity instanceof AnimatableRideableAnimalEntity) {
				AnimatableRideableAnimalEntity rideableVehicleEntity = (AnimatableRideableAnimalEntity) vehicleEntity;
				
				if (rideableVehicleEntity.boost()) {
					mainHandItem.hurtAndBreak(consumeItemDamage, player, (level) -> level.broadcastBreakEvent(hand));		
					
					if (mainHandItem.isEmpty()) {
						ItemStack fishingRodStack = new ItemStack(Items.FISHING_ROD);
						fishingRodStack.setTag(mainHandItem.getTag());
						
						return ActionResult.success(fishingRodStack);
					}
					return ActionResult.success(mainHandItem);
				}
			}
			player.awardStat(Stats.ITEM_USED.get(this));
		}
		return ActionResult.pass(mainHandItem);
	}
}
