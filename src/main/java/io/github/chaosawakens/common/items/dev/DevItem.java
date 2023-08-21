package io.github.chaosawakens.common.items.dev;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class DevItem extends Item {
	
	public DevItem(Properties builderIn) {
		super(builderIn);
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		playerIn.awardStat(Stats.ITEM_USED.get(this));
		return super.use(worldIn, playerIn, handIn);
	}
}