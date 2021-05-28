package io.github.chaosawakens.common.items;

import io.github.chaosawakens.api.dto.EnchantmentAndLevel;
import net.minecraft.block.BlockState;
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

    public UltimateHoeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, EnchantmentAndLevel[] enchantments) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn, enchantments);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos eventPos = context.getPos();
        
        int hook = ForgeEventFactory.onHoeUse(context);
        if (hook != 0) return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;
        if (context.getFace() != Direction.DOWN && world.isAirBlock(eventPos.up())) {
            BlockState blockState = world.getBlockState(eventPos).getToolModifiedState(world, eventPos, context.getPlayer(), context.getItem(), ToolType.HOE);
            if (blockState != null) {
                PlayerEntity playerentity = context.getPlayer();
                world.playSound(playerentity, eventPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!world.isRemote && playerentity != null) {
                    context.getItem().damageItem(1, playerentity, (player) -> player.sendBreakAnimation(context.getHand()));
                }
                for (int x = -1; x < 2; x++) {
                    for (int y = -1; y < 2; y++) {
                        for (int z = -1; z < 2; z++) {
                        	BlockPos targetPos = new BlockPos(eventPos.getX() + x, eventPos.getY() + y, eventPos.getZ() + z);
                            if (world.isAirBlock(targetPos.up())) {
                                blockState = world.getBlockState(targetPos).getToolModifiedState(world, targetPos, context.getPlayer(), context.getItem(), ToolType.HOE);
                                if (blockState != null) {
                                    if (!world.isRemote) {
                                        world.setBlockState(targetPos, blockState, 11);
                                    }
                                }
                            }
                        }
                    }
                }
                return ActionResultType.func_233537_a_(world.isRemote);
            }
        }
        return ActionResultType.PASS;
    }
}