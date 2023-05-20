package io.github.chaosawakens.common.blocks.vegetation;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;

public abstract class CASpreadableDirtBlock extends Block {

	public CASpreadableDirtBlock(Properties properties) {
		super(properties);
	}
	
	private static boolean canBeGrass(BlockState state, IWorldReader worldReader, BlockPos pos) {
		BlockPos abovePos = pos.above();
		BlockState aboveState = worldReader.getBlockState(abovePos);
		if (aboveState.getFluidState().getAmount() == 8) return false;
		else {
			int lightLevel = LightEngine.getLightBlockInto(worldReader, state, pos, aboveState, abovePos, Direction.UP, aboveState.getLightBlock(worldReader, abovePos));
			return lightLevel < worldReader.getMaxLightLevel();
		}
	}

	private static boolean canPropagate(BlockState state, IWorldReader worldReader, BlockPos pos) {
		BlockPos grassPos = pos.above();
		return canBeGrass(state, worldReader, pos) && !worldReader.getFluidState(grassPos).is(FluidTags.WATER);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld serverWorld, BlockPos pos, Random random) {
		if (!canBeGrass(state, serverWorld, pos)) {
			if (!serverWorld.isAreaLoaded(pos, 3)) return;
			serverWorld.setBlockAndUpdate(pos, getDirtBlock().defaultBlockState());
		} else {
			if (serverWorld.getMaxLocalRawBrightness(pos.above()) >= 9) {
				BlockState targetState = this.defaultBlockState();

				for (int i = 0; i < 4; ++i) {
					BlockPos randDirtPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
					if (serverWorld.getBlockState(randDirtPos).is(getDirtBlock()) && canPropagate(targetState, serverWorld, randDirtPos)) serverWorld.setBlockAndUpdate(randDirtPos, targetState);
				}
			}
		}
	}
	
	public abstract Block getDirtBlock();
}
