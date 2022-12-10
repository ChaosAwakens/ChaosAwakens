package io.github.chaosawakens.common.items;

import net.minecraft.block.BlockState;
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
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.ForgeEventFactory;

public class UltimateHoeItem extends EnchantedHoeItem {
	public UltimateHoeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, EnchantmentData[] enchantments) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn, enchantments);
	}

	@SuppressWarnings("deprecation")
	@Override
	public ActionResultType useOn(ItemUseContext context) {
		World world = context.getLevel();
		BlockPos eventPos = context.getClickedPos();

		int hook = ForgeEventFactory.onHoeUse(context);
		if (hook != 0) return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;
		if (context.getClickedFace() != Direction.DOWN && world.isEmptyBlock(eventPos.above())) {
			BlockState blockState = world.getBlockState(eventPos).getToolModifiedState(world, eventPos, context.getPlayer(), context.getItemInHand(), ToolType.HOE);
			if (blockState != null) {
				PlayerEntity playerentity = context.getPlayer();
				world.playSound(playerentity, eventPos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				if (!world.isClientSide && playerentity != null) context.getItemInHand().hurtAndBreak(1, playerentity, (player) -> player.broadcastBreakEvent(context.getHand()));
				for (int x = -1; x < 2; x++) {
					for (int y = -1; y < 2; y++) {
						for (int z = -1; z < 2; z++) {
							BlockPos targetPos = new BlockPos(eventPos.getX() + x, eventPos.getY() + y, eventPos.getZ() + z);
							if (world.isEmptyBlock(targetPos.above())) {
								blockState = world.getBlockState(targetPos).getToolModifiedState(world, targetPos, context.getPlayer(), context.getItemInHand(), ToolType.HOE);
								if (blockState != null && !world.isClientSide) world.setBlock(targetPos, blockState, 11);
							}
						}
					}
				}
				return ActionResultType.sidedSuccess(world.isClientSide);
			}
		}
		return ActionResultType.PASS;
	}
	
	
}
