package io.github.chaosawakens.common.items.tools;

import java.util.function.Supplier;

import io.github.chaosawakens.common.items.base.EnchantedShovelItem;
import io.github.chaosawakens.common.util.EnumUtil.CAItemTier;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
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

public class UltimateShovelItem extends EnchantedShovelItem {

	public UltimateShovelItem(CAItemTier pTier, Supplier<IntValue> configDmg, Properties pProperties, Supplier<EnchantmentData[]> enchantments) {
		super(pTier, configDmg, pProperties, enchantments);
	}

	@Override
	public ActionResultType useOn(ItemUseContext ctx) {
		World curWorld = ctx.getLevel();
		BlockPos eventPos = ctx.getClickedPos();
		BlockState clickedPos = curWorld.getBlockState(eventPos);
		PlayerEntity curPlayer = ctx.getPlayer();
		
		if (ctx.getClickedFace() == Direction.DOWN) return ActionResultType.PASS;
		else {
			if (!curWorld.isClientSide && curPlayer != null) ctx.getItemInHand().hurtAndBreak(1, curPlayer, (curTool) -> curTool.broadcastBreakEvent(ctx.getHand()));
			for (int x = -1; x < 2; x++) {
				for (int y = -1; y < 2; y++) {
					for (int z = -1; z < 2; z++) {
						BlockPos targetPos = new BlockPos(eventPos.getX() + x, eventPos.getY() + y, eventPos.getZ() + z);
						BlockState modifiedState = clickedPos.getToolModifiedState(curWorld, targetPos, curPlayer, ctx.getItemInHand(), ToolType.SHOVEL);
						BlockState targetState = null;
						
						if (curWorld.isEmptyBlock(targetPos.above())) {
							if (curWorld.getBlockState(targetPos).is(clickedPos.getBlock())) {
								if (modifiedState != null) {
									curWorld.playSound(curPlayer, targetPos, SoundEvents.SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
									targetState = modifiedState;
								} else if (clickedPos.getBlock() instanceof CampfireBlock && clickedPos.getValue(CampfireBlock.LIT)) {
									if (!curWorld.isClientSide()) curWorld.levelEvent(null, 1009, targetPos, 0);

									CampfireBlock.dowse(curWorld, targetPos, clickedPos);
									targetState = clickedPos.setValue(CampfireBlock.LIT, Boolean.FALSE);
								}

								if (targetState != null && !curWorld.isClientSide) curWorld.setBlock(targetPos, targetState, 11);
							}
						}
					}
				}
			}
		}
		return ActionResultType.sidedSuccess(curWorld.isClientSide);
	}
}
