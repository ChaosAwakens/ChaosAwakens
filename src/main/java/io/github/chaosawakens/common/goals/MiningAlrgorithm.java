package io.github.chaosawakens.common.goals;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;

public class MiningAlrgorithm {
	 private final List<BlockPos> blocksToBreak = new ArrayList<>();
	    private final BlockPos startingBlock;
	    private final List<BlockPos> alreadyChecked = new ArrayList<>();
	    private final World world;
	    private final ServerPlayerEntity player;
	    private int totalXp = 0;
	    private final BlockPos playerPos;
	    private final List<ItemStack> itemsToDrop = new ArrayList<>();

	    public MiningAlrgorithm(BlockPos start, World worldIn, PlayerEntity playerIn) {
	        world = worldIn;
	        player = (ServerPlayerEntity)playerIn;
	        playerPos = new BlockPos(player.getX(), player.getY(), player.getZ());
	        startingBlock = start;
	        blocksToBreak.add(start);
	        alreadyChecked.add(start);
	        itemsToDrop.add(ItemStack.EMPTY);
	    }

	    public void findBlocks() {
	        BlockPos.Mutable checking = new BlockPos.Mutable();

	        int[] range = {0, -1 ,1};
	        List<BlockPos> dummyBlocks = new ArrayList<>();
	        while(!blocksToBreak.equals(dummyBlocks)) {
	            dummyBlocks.clear();
	            dummyBlocks.addAll(blocksToBreak);
	            for (BlockPos p : dummyBlocks) {
	                for (int x : range) {
	                    for (int y : range) {
	                        for (int z : range) {
	                            checking.set(p).move(x, y, z); //3x3x3
	                            if (alreadyChecked.contains(checking))
	                                continue;
	                            blocksToBreak.add(checking.immutable());
	                            alreadyChecked.add(checking.immutable());
	                        }
	                    }
	                }
	            }
	            dummyBlocks.clear();
	            dummyBlocks.addAll(blocksToBreak);

	            blocksToBreak.removeIf(p -> !world.getBlockState(startingBlock).getBlock().equals(world.getBlockState(p).getBlock()));
	            if(blocksToBreak.size() >= 1) {
	                blocksToBreak.subList(1, blocksToBreak.size()).clear();
	                break;
	            }
	        }
	    }

	    public boolean tryBreak(BlockPos p) {
	        BlockState state = world.getBlockState(p);
	        Block block = state.getBlock();
	        int xp;

	        if(block.equals(Blocks.AIR)) {
	            return false;
	        }
	        if(!ForgeHooks.canHarvestBlock(state, player, world, p) && !player.isCreative())
	            return false;

	        if(!world.isClientSide()) {
	            xp = ForgeHooks.onBlockBreakEvent(world, player.gameMode.getGameModeForPlayer(), player, p);
	            if(xp == -1)
	                return false;

	            if(!block.removedByPlayer(state, world, p, player, !player.isCreative(), state.getFluidState()))
	                return false;
	            //block.playerDestroy(world, player, p, state, world.getBlockEntity(p), player.getMainHandItem());

	            if(!player.isCreative()) {
	                if(!world.isClientSide) //Causes extra lag
	                    block.playerDestroy(world, player, p, state, world.getBlockEntity(p), player.getMainHandItem());
	                else {
	                    addToDropsList(p, block, state);
	                    player.awardStat(Stats.BLOCK_MINED.get(block));
	                    player.causeFoodExhaustion((float)(0.3F));
	                }
	                if(xp > 0)
	                    totalXp += xp;
	            }
	        } else {
	            if(!block.removedByPlayer(state, world, p, player, !player.isCreative(), state.getFluidState()))
	                return false;
	            block.playerDestroy(world, player, p, state, world.getBlockEntity(p), player.getMainHandItem()); 
	        }

	        return true;
	    }

	    private void addToDropsList(BlockPos p, Block block, BlockState state) {
	        List<ItemStack> drops = Block.getDrops(state, (ServerWorld)world, p, world.getBlockEntity(p), player, player.getMainHandItem());
	        List<Item> dummyItemsToDrop = new ArrayList<>();
	        int oldCount;
	        int extraCount;

	        itemsToDrop.forEach(i -> dummyItemsToDrop.add(i.getItem()));

	        for(ItemStack drop : drops) {
	            if(dummyItemsToDrop.contains(drop.getItem())) {
	                    oldCount = itemsToDrop.get(dummyItemsToDrop.indexOf(drop.getItem())).getCount();
	                    extraCount = drop.getCount();
	                    itemsToDrop.get(dummyItemsToDrop.indexOf(drop.getItem())).setCount(oldCount + extraCount);
	            } else {
	                itemsToDrop.add(drop);
	            }
	        }

	        itemsToDrop.remove(ItemStack.EMPTY);
	    }

	    public void dropItems() {
	        if(!world.isClientSide() && world.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !world.restoringBlockSnapshots) {
	            for (ItemStack item : itemsToDrop) {
	                if(!player.inventory.add(item)) {
	                    ItemEntity itemEntity = new ItemEntity(world, playerPos.getX() + 0.5, playerPos.getY() + 0.5, playerPos.getZ() + 0.5, item);
	                    itemEntity.setDefaultPickUpDelay();
	                    world.addFreshEntity(itemEntity);
	                }
	            }
	        }
	        itemsToDrop.clear();
	    }

	    public void mine() {
	        totalXp = 0;
	        for(BlockPos p : blocksToBreak) {
	            if(tryBreak(p)) {
	                if(!world.isClientSide()) {
	                    ItemStack mh = player.getMainHandItem();
	                    if(mh.isDamageableItem() && !player.isCreative()) {
	                        if (mh.getDamageValue() < mh.getMaxDamage()) {
	                            mh.hurt(1, player.getRandom(), player);
	                        } else {
	                            //player.sendBreakAnimation(EquipmentSlotType.MAINHAND);
	                            //player.getHeldItemMainhand().shrink(1);
	                            mh.hurtAndBreak(1, player, player ->
	                                    player.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
	                            break;
	                        }
	                    }
	                }
	            }

	        }
	        if(!world.isClientSide())
	            dropItems();
	        //world.getBlockState(startingBlock).getBlock().dropXpOnBlockBreak(world, playerPos, totalXp);
	        world.getBlockState(startingBlock).getBlock().popExperience((ServerWorld)world, playerPos, totalXp);
	    }

}
