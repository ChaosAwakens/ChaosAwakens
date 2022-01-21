package io.github.chaosawakens.common.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

import io.github.chaosawakens.api.IAutoEnchantable;
import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.registry.CATags;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnchantedPickaxeItem extends PickaxeItem implements IAutoEnchantable {

    private final EnchantmentData[] enchantments;

    public EnchantedPickaxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, EnchantmentData[] enchantments) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.enchantments = enchantments;
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            ItemStack stack = new ItemStack(this);
            if (CAConfig.COMMON.enableAutoEnchanting.get())
                for (EnchantmentData enchant : enchantments) {
                    stack.enchant(enchant.enchantment, enchant.level);
                }
            items.add(stack);
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return CAConfig.COMMON.enableAutoEnchanting.get() || super.isFoil(stack);
    }

    @Override
    public EnchantmentData[] enchantments() {
        return this.enchantments;
    }
    
    public static ArrayList<BlockPos> getSimilarBlocks(World world, BlockPos sourcePos, Block block) {
    	ArrayList<BlockPos> al = Lists.newArrayList();
    	BlockPos[] mpos = new BlockPos[] {
    		sourcePos.below().north(), sourcePos.below().east(), sourcePos.below().west(), sourcePos.below().south(),
    		
    		sourcePos.above(), sourcePos.below(), sourcePos.north(), sourcePos.east(), sourcePos.south(), sourcePos.west(),
    		
    		sourcePos.above().south(), sourcePos.above().east(), sourcePos.above().west(), sourcePos.above().north()
    	};
    	for (BlockPos pos : mpos) {
    		if (world.getBlockState(pos).getBlock() == block) {
    			al.add(pos);
    		}
    	}
    	Collections.shuffle(al);
    	return al;
    }
    
    private static int doVeinMiner(BlockState blockState, World world, BlockPos blockPos, int maxBlocks, PlayerEntity pe, ItemStack tool) {
        LongOpenHashSet used = new LongOpenHashSet(51);
        Queue<BlockPos> toBreak = Util.make(Queues.newArrayBlockingQueue(51), queue -> queue.add(blockPos));

        while(!toBreak.isEmpty() && used.size() <= (maxBlocks)) {
            BlockPos currPos = toBreak.poll();
            BlockState currentState = world.getBlockState(currPos);
            used.add(currPos.asLong());
            if (toBreak.size() < 50) {
                getSimilarBlocks(world, currPos, blockState.getBlock())
                    .stream()
                    .filter(pos -> !used.contains(pos.asLong()))
                    .forEach(toBreak::offer);
                    ;
            }
            Block.dropResources(currentState, world, blockPos, null, pe, tool);
            world.destroyBlock(currPos, false);
            currentState.getBlock().playerWillDestroy(world, currPos, currentState, pe);
            tool.mineBlock(world, currentState, currPos, pe);
            
            tool.hurtAndBreak(1, pe,(playerEntity_1)  ->  {
                playerEntity_1.broadcastBreakEvent(pe.getUsedItemHand());
            });
            if (world != null) {
                pe.playSound(SoundEvents.ITEM_BREAK, 1.0F, 1.0F);
                break;
            }
        }
        return used.size();
    }

    
    @Override
    public boolean mineBlock(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity e) {
        if (world.isClientSide) return false;
        if ((PlayerEntity) e == null) return false;
        
        ItemStack tool = e.getMainHandItem();
        if (tool == ItemStack.EMPTY) {
            return false;
        }
        
    	if (state.getBlock().is(CATags.whitelist) && state.getBlock().is(CATags.blacklist) || !state.getBlock().is(CATags.whitelist) && !state.getBlock().is(CATags.blacklist)) {
    		int brokenBlocks = 0;
    		brokenBlocks = doVeinMiner(state, world, pos, 50, (PlayerEntity) e, stack);
    		return true;
    	}
    	return true;
    }
}