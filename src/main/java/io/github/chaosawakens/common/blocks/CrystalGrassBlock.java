package io.github.chaosawakens.common.blocks;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.block.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

import java.util.List;
import java.util.Random;

public class CrystalGrassBlock extends SpreadableCrystalDirtBlock implements IGrowable {
	public CrystalGrassBlock(Properties properties) {
		super(properties);
	}

	@SuppressWarnings("deprecation")
	public boolean isValidBonemealTarget(IBlockReader blockReader, BlockPos pos, BlockState state, boolean p_176473_4_) {
		return blockReader.getBlockState(pos.above()).isAir();
	}

	public boolean isBonemealSuccess(World p_180670_1_, Random p_180670_2_, BlockPos p_180670_3_, BlockState p_180670_4_) {
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void performBonemeal(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		BlockPos blockpos = pos.above();
		BlockState blockstate = CABlocks.CRYSTAL_GRASS.get().defaultBlockState();

		label48: for (int i = 0; i < 128; ++i) {
			BlockPos blockpos1 = blockpos;
			for (int j = 0; j < i / 16; ++j) {
				blockpos1 = blockpos1.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
				if (!world.getBlockState(blockpos1.below()).is(this) || world.getBlockState(blockpos1).isCollisionShapeFullBlock(world, blockpos1)) continue label48;
			}

			BlockState blockstate2 = world.getBlockState(blockpos1);
			if (blockstate2.is(blockstate.getBlock()) && random.nextInt(10) == 0) ((IGrowable) blockstate.getBlock()).performBonemeal(world, random, blockpos1, blockstate2);

			if (blockstate2.isAir()) {
				BlockState blockstate1;
				if (random.nextInt(8) == 0) {
					List<ConfiguredFeature<?, ?>> list = world.getBiome(blockpos1).getGenerationSettings().getFlowerFeatures();
					if (list.isEmpty()) continue;

					ConfiguredFeature<?, ?> configuredfeature = list.get(0);
					FlowersFeature flowersfeature = (FlowersFeature) configuredfeature.feature;
					blockstate1 = flowersfeature.getRandomFlower(random, blockpos1, configuredfeature.config());
				} else blockstate1 = blockstate;

				if (blockstate1.canSurvive(world, blockpos1)) world.setBlock(blockpos1, blockstate1, 3);
			}
		}
	}

	@Override
	public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
		BlockState plant = plantable.getPlant(world, pos.relative(facing));

		if (plant.getBlock() == Blocks.SUGAR_CANE && this == Blocks.SUGAR_CANE) return true;
		if (plantable instanceof CrystalBushBlock && ((CrystalBushBlock) plantable).mayPlaceOn(state, world, pos)) return true;
		if (plantable instanceof CrystalFlowerBlock && ((CrystalFlowerBlock) plantable).mayPlaceOn(state, world, pos)) return true;

		return false;
	}
}
