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

public class BeetrootOnAStickItem extends Item{
	

	   private final int consumeItemDamage;
	   
		public BeetrootOnAStickItem(Properties prop, int duraDmg) {
			super(prop);
			this.consumeItemDamage = duraDmg;
		}

		@Override
	   public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
	      ItemStack itemstack = player.getItemInHand(hand);
	      if (world.isClientSide) {
	         return ActionResult.pass(itemstack);
	      } else {
	         Entity entity = player.getVehicle();
	         if (player.isPassenger() && entity instanceof IRideable && entity instanceof PigEntity) {
	            IRideable irideable = (IRideable)entity;
	            if (irideable.boost()) {
	               itemstack.hurtAndBreak(this.consumeItemDamage, player, (level) -> {
	                  level.broadcastBreakEvent(hand);
	               });
	               if (itemstack.isEmpty()) {
	                  ItemStack itemstack1 = new ItemStack(Items.FISHING_ROD);
	                  itemstack1.setTag(itemstack.getTag());
	                  return ActionResult.success(itemstack1);
	               }

	               return ActionResult.success(itemstack);
	            }
	         }

	         player.awardStat(Stats.ITEM_USED.get(this));
	         return ActionResult.pass(itemstack);
	      }
	   }

}
