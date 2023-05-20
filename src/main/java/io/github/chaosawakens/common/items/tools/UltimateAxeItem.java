package io.github.chaosawakens.common.items.tools;

import java.util.function.Supplier;

import io.github.chaosawakens.common.items.base.EnchantedAxeItem;
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

public class UltimateAxeItem extends EnchantedAxeItem {
	
	public UltimateAxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, Supplier<EnchantmentData[]> enchantments) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn, enchantments);
	}

	@Override
	public ActionResultType useOn(ItemUseContext ctx) {
		World world = ctx.getLevel();
		BlockPos blockpos = ctx.getClickedPos();
		PlayerEntity playerentity = ctx.getPlayer();
		BlockState blockstate = world.getBlockState(blockpos);
		if (ctx.getClickedFace() == Direction.DOWN) return ActionResultType.PASS;
		else {
			if (!world.isClientSide && playerentity != null) ctx.getItemInHand().hurtAndBreak(1, playerentity, (p_220040_1_) -> p_220040_1_.broadcastBreakEvent(ctx.getHand()));
		}
		for (int x = -1; x < 2; x++) {
			for (int y = -1; y < 2; y++) {
				for (int z = -1; z < 2; z++) {
					BlockPos targetPos = new BlockPos(blockpos.getX() + x, blockpos.getY() + y, blockpos.getZ() + z);
					BlockState block = blockstate.getToolModifiedState(world, targetPos, playerentity, ctx.getItemInHand(), ToolType.AXE);
					if (world.getBlockState(targetPos).is(blockstate.getBlock())) {
						if (block != null) {
							world.playSound(playerentity, targetPos, SoundEvents.AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
							if (!world.isClientSide) world.setBlock(targetPos, block, 11);
						} else return ActionResultType.PASS;
					}
				}
			}
		}
		return ActionResultType.sidedSuccess(world.isClientSide);
	}
}
