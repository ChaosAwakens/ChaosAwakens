package io.github.chaosawakens.common.blocks.vegetation;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.IPlantable;

public class CASaplingBlock extends SaplingBlock implements IPlantable {
	protected Supplier<Block> soilBlock;

	public CASaplingBlock(Tree tree, Properties properties) {
		super(tree, properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, Integer.valueOf(0)));
	}
	
	public CASaplingBlock(Tree tree, Supplier<Block> soilBlock, Properties properties) {
		super(tree, properties);
		this.soilBlock = soilBlock;
		this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, Integer.valueOf(0)));
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldReader, BlockPos pos) {
		BlockPos belowPos = pos.below();
		BlockState targetState = worldReader.getBlockState(belowPos);
		return this.soilBlock.get() != null ? targetState.getBlock().is(this.soilBlock.get()) && targetState.isRandomlyTicking() : super.canSurvive(targetState, worldReader, belowPos);
	}
	
	@Override
	protected boolean mayPlaceOn(BlockState pState, IBlockReader pLevel, BlockPos pPos) {
		return this.soilBlock.get() != null ? pLevel.getBlockState(pPos).getBlock().is(this.soilBlock.get()) : super.mayPlaceOn(pState, pLevel, pPos);
	}
}
