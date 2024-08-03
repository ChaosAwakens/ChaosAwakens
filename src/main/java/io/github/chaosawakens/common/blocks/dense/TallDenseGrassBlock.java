package io.github.chaosawakens.common.blocks.dense;

import io.github.chaosawakens.common.blocks.dense.DenseBushBlock;
import io.github.chaosawakens.common.blocks.dense.DoubleDensePlantBlock;
import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;

import java.util.Random;

public class TallDenseGrassBlock extends DenseBushBlock implements IGrowable, IForgeShearable {
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

	public TallDenseGrassBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@Override
	public boolean isValidBonemealTarget(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(World world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		DoubleDensePlantBlock doubleDensePlantBlock = (DoubleDensePlantBlock) (this == CABlocks.DENSE_GRASS.get() ? CABlocks.TALL_DENSE_GRASS.get() : CABlocks.DENSE_GRASS.get());
		if (doubleDensePlantBlock.defaultBlockState().canSurvive(world, pos) && world.isEmptyBlock(pos.above())) doubleDensePlantBlock.placeAt(world, pos, 2);
	}

	@Override
	public AbstractBlock.OffsetType getOffsetType() {
		return AbstractBlock.OffsetType.XYZ;
	}
}
