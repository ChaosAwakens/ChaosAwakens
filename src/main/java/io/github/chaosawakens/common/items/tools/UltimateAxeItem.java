package io.github.chaosawakens.common.items.tools;

import io.github.chaosawakens.common.items.base.EnchantedAxeItem;
import io.github.chaosawakens.common.util.EnumUtil.CAItemTier;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.ToolType;

import java.util.function.Supplier;

public class UltimateAxeItem extends EnchantedAxeItem {

	public UltimateAxeItem(CAItemTier pTier, Supplier<IntValue> configDmg, Properties pProperties, Supplier<EnchantmentData[]> enchantments) {
		super(pTier, configDmg, pProperties, enchantments);
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
					BlockState modifiedState = blockstate.getToolModifiedState(world, targetPos, playerentity, ctx.getItemInHand(), ToolType.AXE);
					
					if (world.getBlockState(targetPos).is(blockstate.getBlock())) {
						if (modifiedState != null) {
							world.playSound(playerentity, targetPos, SoundEvents.AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
							if (!world.isClientSide) world.setBlock(targetPos, modifiedState, 11);
						} else return ActionResultType.PASS;
					}
				}
			}
		}
		return ActionResultType.sidedSuccess(world.isClientSide);
	}
}
