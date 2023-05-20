package io.github.chaosawakens.common.blocks.vegetation;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
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

public abstract class CAGrassBlock extends CASpreadableDirtBlock implements IGrowable {

	public CAGrassBlock(Properties properties) {
		super(properties);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isValidBonemealTarget(IBlockReader blockReader, BlockPos pos, BlockState state, boolean isClientSide) {
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
		BlockState grassState = getGrassBlock().defaultBlockState();

		label48: 
			for (int i = 0; i < 128; ++i) {
				BlockPos targetPos = abovePos;
				for (int j = 0; j < i / 16; ++j) {
					targetPos = targetPos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
					if (!world.getBlockState(targetPos.below()).is(this) || world.getBlockState(targetPos).isCollisionShapeFullBlock(world, targetPos)) continue label48;
				}

				BlockState targetState = world.getBlockState(targetPos);
				if (targetState.is(grassState.getBlock()) && random.nextInt(10) == 0) ((IGrowable) grassState.getBlock()).performBonemeal(world, random, targetPos, targetState);

				if (targetState.isAir()) {
					BlockState flowerState;
					if (random.nextInt(8) == 0) {
						List<ConfiguredFeature<?, ?>> flowerFeaturesList = world.getBiome(targetPos).getGenerationSettings().getFlowerFeatures();
						if (flowerFeaturesList.isEmpty()) continue;

						ConfiguredFeature<?, ?> flowerFeatures = flowerFeaturesList.get(0);
						FlowersFeature flowerFeature = (FlowersFeature) flowerFeatures.feature;
						flowerState = flowerFeature.getRandomFlower(random, targetPos, flowerFeatures.config());
					} else flowerState = grassState;

					if (flowerState.canSurvive(world, targetPos)) world.setBlock(targetPos, flowerState, 3);
				}
			}
	}

	@Override
	public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
		BlockState plant = plantable.getPlant(world, pos.relative(facing));
		PlantType targetPlantType = plantable.getPlantType(world, pos.relative(facing));

		if (plant.getBlock() == Blocks.SUGAR_CANE && this == Blocks.SUGAR_CANE) return true;
		if (canSustainPlant(plant)) return true;
		if (PlantType.PLAINS.equals(targetPlantType)) return Tags.Blocks.DIRT.contains(this);

		return false;
	}

	public abstract Block getGrassBlock();

	public abstract boolean canSustainPlant(BlockState plantState);
}
