package io.github.chaosawakens.common.blocks.crystal;

import io.github.chaosawakens.common.blocks.SpreadableCrystalDirtBlock;
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

import java.util.List;
import java.util.Random;

public class CrystalGrassBlock extends SpreadableCrystalDirtBlock implements IGrowable {
	
	public CrystalGrassBlock(Properties properties) {
		super(properties);
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isValidBonemealTarget(IBlockReader blockReader, BlockPos pos, BlockState state, boolean isOnClient) {
		return blockReader.getBlockState(pos.above()).isAir();
	}

	@Override
	public boolean isBonemealSuccess(World targetWorld, Random rand, BlockPos targetPos, BlockState targetState) {
		return true;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void performBonemeal(ServerWorld targetServerWorld, Random random, BlockPos targetPos, BlockState targetState) {
		BlockPos bonemealPos = targetPos.above();
		BlockState defaultCrystalGrassState = CABlocks.CRYSTAL_GRASS.get().defaultBlockState();

		label48: for (int placementAttempts = 0; placementAttempts < 128; ++placementAttempts) {
			BlockPos targetBonemealPos = bonemealPos;

			for (int actualBlockOffset = 0; actualBlockOffset < placementAttempts / 16; ++actualBlockOffset) {
				targetBonemealPos = targetBonemealPos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);

				if (!targetServerWorld.getBlockState(targetBonemealPos.below()).is(this) || targetServerWorld.getBlockState(targetBonemealPos).isCollisionShapeFullBlock(targetServerWorld, targetBonemealPos)) continue label48;
			}

			BlockState targetBonemealState = targetServerWorld.getBlockState(targetBonemealPos);

			if (targetBonemealState.is(defaultCrystalGrassState.getBlock()) && random.nextInt(10) == 0) ((IGrowable) defaultCrystalGrassState.getBlock()).performBonemeal(targetServerWorld, random, targetBonemealPos, targetBonemealState);
			if (targetBonemealState.isAir()) {
				BlockState targetFlowerState;
				if (random.nextInt(8) == 0) {
					List<ConfiguredFeature<?, ?>> validConfiguredFlowerFeatures = targetServerWorld.getBiome(targetBonemealPos).getGenerationSettings().getFlowerFeatures();
					if (validConfiguredFlowerFeatures.isEmpty()) continue;

					ConfiguredFeature<?, ?> firstAvailableConfiguredFlowerFeature = validConfiguredFlowerFeatures.get(0);
					FlowersFeature targetFlowerFeature = (FlowersFeature) firstAvailableConfiguredFlowerFeature.feature;
					targetFlowerState = targetFlowerFeature.getRandomFlower(random, targetBonemealPos, firstAvailableConfiguredFlowerFeature.config());
				} else targetFlowerState = defaultCrystalGrassState;

				if (targetFlowerState.canSurvive(targetServerWorld, targetBonemealPos)) targetServerWorld.setBlock(targetBonemealPos, targetFlowerState, 3);
			}
		}
	}

	@Override
	public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
		BlockState targetPos = plantable.getPlant(world, pos.relative(facing));

		return targetPos.getBlock() == Blocks.SUGAR_CANE && this == Blocks.SUGAR_CANE || plantable instanceof CrystalBushBlock && ((CrystalBushBlock) plantable).mayPlaceOn(state, world, pos) || plantable instanceof CrystalFlowerBlock && ((CrystalFlowerBlock) plantable).mayPlaceOn(state, world, pos);
	}
}
