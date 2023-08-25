package io.github.chaosawakens.common.items.projectile;

import io.github.chaosawakens.common.entity.projectile.CALettuceChickenEggEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EggItem;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class LettuceChickenEggItem extends EggItem {

	public LettuceChickenEggItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {		
		ItemStack itemstack = player.getItemInHand(hand);	
		world.playSound((PlayerEntity)null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));		    
		if (!world.isClientSide) {		     
			CALettuceChickenEggEntity egg = new CALettuceChickenEggEntity(world, player);
			egg.setItem(itemstack);		       
			egg.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 1.5F, 1.0F);		      
			world.addFreshEntity(egg);		      
		}		     
		player.awardStat(Stats.ITEM_USED.get(this));		     
		if (!player.abilities.instabuild) {		     
			itemstack.shrink(1);		     
		}		     
		return ActionResult.sidedSuccess(itemstack, world.isClientSide());	  
	}
}
