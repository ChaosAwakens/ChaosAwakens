package io.github.chaosawakens.common.blocks;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.block.*;
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

	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	public boolean isValidBonemealTarget(IBlockReader world, BlockPos pos, BlockState state, boolean p_176473_4_) {
		return true;
	}

	public boolean isBonemealSuccess(World world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	public void performBonemeal(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		DoubleDensePlantBlock doubleDensePlantBlock = (DoubleDensePlantBlock) (this == CABlocks.DENSE_GRASS.get()
				? CABlocks.TALL_DENSE_GRASS.get()
				: CABlocks.DENSE_GRASS.get());
		if (doubleDensePlantBlock.defaultBlockState().canSurvive(world, pos) && world.isEmptyBlock(pos.above())) doubleDensePlantBlock.placeAt(world, pos, 2);
	}

	public AbstractBlock.OffsetType getOffsetType() {
		return AbstractBlock.OffsetType.XYZ;
	}
}
