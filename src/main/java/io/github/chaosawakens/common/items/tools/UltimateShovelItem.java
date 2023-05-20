package io.github.chaosawakens.common.items.tools;

import java.util.function.Supplier;

import io.github.chaosawakens.common.items.base.EnchantedShovelItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UltimateShovelItem extends EnchantedShovelItem {
	
	public UltimateShovelItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, Supplier<EnchantmentData[]> enchantments) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn, enchantments);
	}

	@Override
	public ActionResultType useOn(ItemUseContext ctx) {
		World world = ctx.getLevel();
		BlockPos eventPos = ctx.getClickedPos();
		BlockState blockstate = world.getBlockState(eventPos);
		PlayerEntity playerentity = ctx.getPlayer();
		if (ctx.getClickedFace() == Direction.DOWN) return ActionResultType.PASS;
		else {
			if (!world.isClientSide && playerentity != null) ctx.getItemInHand().hurtAndBreak(1, playerentity, (p_220041_1_) -> p_220041_1_.broadcastBreakEvent(ctx.getHand()));
			for (int x = -1; x < 2; x++) {
				for (int y = -1; y < 2; y++) {
					for (int z = -1; z < 2; z++) {
						BlockPos targetPos = new BlockPos(eventPos.getX() + x, eventPos.getY() + y, eventPos.getZ() + z);
						BlockState blockstate1 = blockstate.getToolModifiedState(world, targetPos, playerentity, ctx.getItemInHand(), net.minecraftforge.common.ToolType.SHOVEL);
						BlockState blockstate2 = null;
						if (world.isEmptyBlock(targetPos.above())) {
							if (world.getBlockState(targetPos).is(blockstate.getBlock())) {
								if (blockstate1 != null) {
									world.playSound(playerentity, targetPos, SoundEvents.SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
									blockstate2 = blockstate1;
								} else if (blockstate.getBlock() instanceof CampfireBlock && blockstate.getValue(CampfireBlock.LIT)) {
									if (!world.isClientSide()) world.levelEvent(null, 1009, targetPos, 0);

									CampfireBlock.dowse(world, targetPos, blockstate);
									blockstate2 = blockstate.setValue(CampfireBlock.LIT, Boolean.FALSE);
								}

								if (blockstate2 != null && !world.isClientSide) world.setBlock(targetPos, blockstate2, 11);
							}
						}
					}
				}
			}
		}
		return ActionResultType.sidedSuccess(world.isClientSide);
	}
}
