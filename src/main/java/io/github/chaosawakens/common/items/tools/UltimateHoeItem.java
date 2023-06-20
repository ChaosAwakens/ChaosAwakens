package io.github.chaosawakens.common.items.tools;

import java.util.function.Supplier;

import io.github.chaosawakens.common.items.base.EnchantedHoeItem;
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
import net.minecraftforge.event.ForgeEventFactory;

public class UltimateHoeItem extends EnchantedHoeItem {
	
	public UltimateHoeItem(CAItemTier pTier, Supplier<IntValue> configDmg, Properties pProperties, Supplier<EnchantmentData[]> enchantments) {
		super(pTier, configDmg, pProperties, enchantments);
	}

	@SuppressWarnings("deprecation")
	@Override
	public ActionResultType useOn(ItemUseContext context) {
		World curWorld = context.getLevel();
		BlockPos eventPos = context.getClickedPos();
		int hoeUseHook = ForgeEventFactory.onHoeUse(context);
		
		if (hoeUseHook != 0) return hoeUseHook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;
		if (context.getClickedFace() != Direction.DOWN && curWorld.isEmptyBlock(eventPos.above())) {
			BlockState modifiedState = curWorld.getBlockState(eventPos).getToolModifiedState(curWorld, eventPos, context.getPlayer(), context.getItemInHand(), ToolType.HOE);
			if (modifiedState != null) {
				PlayerEntity curPlayer = context.getPlayer();
				curWorld.playSound(curPlayer, eventPos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				
				if (!curWorld.isClientSide && curPlayer != null) context.getItemInHand().hurtAndBreak(1, curPlayer, (player) -> player.broadcastBreakEvent(context.getHand()));
				for (int x = -1; x < 2; x++) {
					for (int y = -1; y < 2; y++) {
						for (int z = -1; z < 2; z++) {
							BlockPos targetPos = new BlockPos(eventPos.getX() + x, eventPos.getY() + y, eventPos.getZ() + z);
							
							if (curWorld.isEmptyBlock(targetPos.above())) {
								modifiedState = curWorld.getBlockState(targetPos).getToolModifiedState(curWorld, targetPos, context.getPlayer(), context.getItemInHand(), ToolType.HOE);
								if (modifiedState != null && !curWorld.isClientSide) curWorld.setBlock(targetPos, modifiedState, 11);
							}
						}
					}
				}
				return ActionResultType.sidedSuccess(curWorld.isClientSide);
			}
		}
		return ActionResultType.PASS;
	}
	
	
}
