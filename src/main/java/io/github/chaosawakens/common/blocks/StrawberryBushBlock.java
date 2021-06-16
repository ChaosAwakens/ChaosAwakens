package io.github.chaosawakens.common.blocks;

import java.util.function.Supplier;

import net.minecraft.block.BlockState;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

/**
 * @author invalid2
 *
 */
public class StrawberryBushBlock extends SweetBerryBushBlock {
	
	private Supplier<? extends Item> seedItem;
	private Supplier<? extends Item> foodItem;
	
	/**
	 * @param properties
	 */
	public StrawberryBushBlock(Supplier<? extends Item> seedItem, Supplier<? extends Item> foodItem, Properties properties) {
		super(properties);
		
		this.seedItem = seedItem;
		this.foodItem = foodItem;
	}
	
	@Override
	public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
		return new ItemStack(seedItem.get());
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		int age = state.get(AGE);
		boolean flag = age == 3;
		if (!flag && player.getHeldItem(handIn).getItem() == Items.BONE_MEAL) {
			return ActionResultType.PASS;
		} else if (age > 1) {
			int j = 1 + worldIn.rand.nextInt(2);
			spawnAsEntity(worldIn, pos, new ItemStack(this.foodItem.get(), j + (flag ? 1 : 0)));
			worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
			worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(1)), 2);
			
			return ActionResultType.func_233537_a_(worldIn.isRemote);
		}
		return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
	}
	
	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {}
}
