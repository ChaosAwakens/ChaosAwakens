package io.github.chaosawakens.common.blocks.dense;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.Tags;

import java.util.List;
import java.util.Random;

public class DenseGrassBlock extends SpreadableDenseDirtBlock implements IGrowable {

	public DenseGrassBlock(Properties properties) {
		super(properties);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isValidBonemealTarget(IBlockReader blockReader, BlockPos pos, BlockState state, boolean p_176473_4_) {
		return blockReader.getBlockState(pos.above()).isAir();
	}

	@Override
	public boolean isBonemealSuccess(World world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	@Override
	public void performBonemeal(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		BlockPos abovePos = pos.above();
		BlockState denseGrassState = CABlocks.DENSE_GRASS.get().defaultBlockState();

		label48: 
			for (int i = 0; i < 128; ++i) {
				BlockPos targetPos = abovePos;
				for (int j = 0; j < i / 16; ++j) {
					targetPos = targetPos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
					if (!world.getBlockState(targetPos.below()).is(this) || world.getBlockState(targetPos).isCollisionShapeFullBlock(world, targetPos)) continue label48;
				}

				BlockState targetState = world.getBlockState(targetPos);
				if (targetState.is(denseGrassState.getBlock()) && random.nextInt(10) == 0) ((IGrowable) denseGrassState.getBlock()).performBonemeal(world, random, targetPos, targetState);

				if (targetState.isAir()) {
					BlockState flowerState;
					if (random.nextInt(8) == 0) {
						List<ConfiguredFeature<?, ?>> flowers = world.getBiome(targetPos).getGenerationSettings().getFlowerFeatures();
						if (flowers.isEmpty()) continue;

						ConfiguredFeature<?, ?> flowerFeatures = flowers.get(0);
						FlowersFeature flowerFeature = (FlowersFeature) flowerFeatures.feature;
						flowerState = flowerFeature.getRandomFlower(random, targetPos, flowerFeatures.config());
					} else flowerState = denseGrassState;

					if (flowerState.canSurvive(world, targetPos)) world.setBlock(targetPos, flowerState, 3);
				}
			}
	}

	@Override
	public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
		BlockState plant = plantable.getPlant(world, pos.relative(facing));
		PlantType type = plantable.getPlantType(world, pos.relative(facing));

		if (plant.getBlock() == Blocks.SUGAR_CANE && this == Blocks.SUGAR_CANE) return true;
		if (plantable instanceof DenseBushBlock && ((DenseBushBlock) plantable).mayPlaceOn(state, world, pos)) return true;
		if (PlantType.PLAINS.equals(type)) return this.getBlock() == Blocks.GRASS_BLOCK || Tags.Blocks.DIRT.contains(this);

		return false;
	}
}
