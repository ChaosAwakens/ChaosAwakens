package io.github.chaosawakens.items;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UltimateHoeItem extends EnchantedHoeItem {

    public UltimateHoeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, Enchantment[] enchants, int[] lvls) {
        super(tier,attackDamageIn,attackSpeedIn,builderIn,enchants,lvls);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockPos blockpos2;
        int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(context);
        if (hook != 0) return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;
        if (context.getFace() != Direction.DOWN && world.isAirBlock(blockpos.up())) {
            BlockState blockstate = world.getBlockState(blockpos).getToolModifiedState(world, blockpos, context.getPlayer(), context.getItem(), net.minecraftforge.common.ToolType.HOE);
            if (blockstate != null) {
                PlayerEntity playerentity = context.getPlayer();
                world.playSound(playerentity, blockpos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!world.isRemote && playerentity != null) {
                    context.getItem().damageItem(1, playerentity, (player) -> {
                        player.sendBreakAnimation(context.getHand());
                    });
                }
                for (int x = -1; x < 2; x++) {
                    for (int y = -1; y < 2; y++) {
                        for (int z = -1; z < 2; z++) {
                            blockpos2 = new BlockPos(blockpos.getX() + x, blockpos.getY() + y, blockpos.getZ() + z);
                            if (world.isAirBlock(blockpos2.up())) {
                                blockstate = world.getBlockState(blockpos2).getToolModifiedState(world, blockpos2, context.getPlayer(), context.getItem(), net.minecraftforge.common.ToolType.HOE);
                                if (blockstate != null) {
                                    if (!world.isRemote) {
                                        world.setBlockState(blockpos2, blockstate, 11);
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