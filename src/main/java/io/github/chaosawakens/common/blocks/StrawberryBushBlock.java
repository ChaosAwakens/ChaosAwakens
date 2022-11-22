package io.github.chaosawakens.common.blocks;

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

import java.util.function.Supplier;

public class StrawberryBushBlock extends SweetBerryBushBlock {
	private final Supplier<? extends Item> seedItem;
	private final Supplier<? extends Item> foodItem;

	public StrawberryBushBlock(Supplier<? extends Item> seedItem, Supplier<? extends Item> foodItem, Properties properties) {
		super(properties);
		this.seedItem = seedItem;
		this.foodItem = foodItem;
	}

	@Override
	public ItemStack getCloneItemStack(IBlockReader worldIn, BlockPos pos, BlockState state) {
		return new ItemStack(seedItem.get());
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		int age = state.getValue(AGE);
		boolean flag = age == 3;
		if (!flag && player.getItemInHand(handIn).getItem() == Items.BONE_MEAL) {
			return ActionResultType.PASS;
		} else if (age > 1) {
			int j = 1 + worldIn.random.nextInt(2);
			popResource(worldIn, pos, new ItemStack(this.foodItem.get(), j + (flag ? 1 : 0)));
			worldIn.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
			worldIn.setBlock(pos, state.setValue(AGE, 1), 2);

			return ActionResultType.sidedSuccess(worldIn.isClientSide);
		}
		return super.use(state, worldIn, pos, player, handIn, hit);
	}

	@Override
	public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {

	}
}
