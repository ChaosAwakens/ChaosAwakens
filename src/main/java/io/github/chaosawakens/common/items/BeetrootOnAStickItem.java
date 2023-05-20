package io.github.chaosawakens.common.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IRideable;
import net.minecraft.entity.passive.PigEntity;
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
			
			if (player.isPassenger() && vehicleEntity instanceof PigEntity) {
				IRideable rideableVehicleEntity = (IRideable) vehicleEntity;
				
				if (rideableVehicleEntity.boost()) {
					mainHandItem.hurtAndBreak(this.consumeItemDamage, player, (level) -> level.broadcastBreakEvent(hand));		
					
					if (mainHandItem.isEmpty()) {
						ItemStack fishingRod = new ItemStack(Items.FISHING_ROD);
						fishingRod.setTag(mainHandItem.getTag());
						
						return ActionResult.success(fishingRod);
					}
					return ActionResult.success(mainHandItem);
				}
			}
			player.awardStat(Stats.ITEM_USED.get(this));
		}
		return ActionResult.pass(mainHandItem);
	}
}
