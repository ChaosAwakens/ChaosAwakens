package io.github.chaosawakens.common.blocks.misc;

import io.github.chaosawakens.common.blocks.crystal.CrystalClusterBlock;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Objects;
import java.util.Random;

public class BuddingBlock extends Block {
	private static final IntegerProperty MAX_GENERATABLE = BlockStateProperties.AGE_25;
	private final CrystalClusterBlock budBlock;

	public BuddingBlock(Properties builder, CrystalClusterBlock budBlock) {
		super(builder);
		this.budBlock = budBlock;
		this.registerDefaultState(this.stateDefinition.any().setValue(MAX_GENERATABLE, new Random().nextInt(8) + 17));
	}

	// TODO Maximum number off clusters generated ever
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(MAX_GENERATABLE);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		random.nextInt(1);
		if (state.getValue(MAX_GENERATABLE) == 0) return;

		ObjectArrayList<BlockStatePos> valids = this.checkValidPositions(worldIn, pos);

		if (valids.isEmpty()) return;

		int numLoops = random.nextInt(3) + 1;
		numLoops = Math.min(numLoops, valids.size());

		for (int i = 0; i < numLoops; i++) {
			BlockStatePos stateAndPos = valids.get(numLoops - 1);
			worldIn.setBlock(stateAndPos.getPos(), stateAndPos.getState(), 2);
		}

		if (random.nextInt(2) == 0) worldIn.setBlockAndUpdate(pos, state.setValue(MAX_GENERATABLE, state.getValue(MAX_GENERATABLE) - 1));

	}

	private ObjectArrayList<BlockStatePos> checkValidPositions(ServerWorld worldIn, BlockPos pos) {
		ObjectArrayList<BlockStatePos> validStatePos = new ObjectArrayList<BlockStatePos>();

		for (Direction direction : Direction.values()) {
			BlockPos budTargetPos = pos;
			budTargetPos = budTargetPos.offset(direction.getNormal());

			BlockState budState = budBlock.defaultBlockState();
			boolean newBudBlock = true;
			
			if (worldIn.getBlockState(budTargetPos).getBlock() instanceof CrystalClusterBlock) {
				budState = worldIn.getBlockState(budTargetPos);
				newBudBlock = false;
			}

			if (Objects.equals(worldIn.getBlockState(budTargetPos).getBlock().getRegistryName(), budBlock.getRegistryName()) || !worldIn.getBlockState(budTargetPos).isFaceSturdy(worldIn, budTargetPos, direction)) validStatePos.add(new BlockStatePos(budTargetPos, budState.setValue(BlockStateProperties.FACING, direction).setValue(BlockStateProperties.AGE_3, newBudBlock ? 0 : worldIn.getBlockState(budTargetPos).getValue(BlockStateProperties.AGE_3) < 3 ? budState.getValue(BlockStateProperties.AGE_3) + 1 : budState.getValue(BlockStateProperties.AGE_3))));
		}

		return validStatePos;
	}

	static class BlockStatePos {
		private final BlockPos pos;
		private final BlockState state;

		public BlockStatePos(BlockPos pos, BlockState state) {
			this.pos = pos;
			this.state = state;
		}

		public BlockPos getPos() {
			return pos;
		}

		public BlockState getState() {
			return state;
		}
	}
}
