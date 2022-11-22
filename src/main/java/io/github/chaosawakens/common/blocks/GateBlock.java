package io.github.chaosawakens.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class GateBlock extends Block {
	public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
	public static final BooleanProperty VANISHED = BooleanProperty.create("vanished");
	private static final VoxelShape VANISHED_SHAPE = box(6, 6, 6, 10, 10, 10);

	public GateBlock(Properties props) {
		super(props);
		this.registerDefaultState(stateDefinition.any().setValue(ACTIVE, false));
	}

	@Override
	public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (isVanished(state)) {
			if (state.getValue(ACTIVE)) {
				world.setBlockAndUpdate(pos, state.setValue(VANISHED, false).setValue(ACTIVE, false));
			} else {
				world.setBlockAndUpdate(pos, state.setValue(ACTIVE, true));
				world.getBlockTicks().scheduleTick(pos, this, 15);
			}
		} else {
			if (state.getValue(ACTIVE)) {
				if (state.hasProperty(VANISHED)) {
					world.setBlockAndUpdate(pos, state.setValue(ACTIVE, false).setValue(VANISHED, true));
					world.getBlockTicks().scheduleTick(pos, this, 80);
				} else {
					world.removeBlock(pos, false);
				}

				for (Direction e : Direction.values()) {
					activate(world, pos.relative(e));
				}
			}
		}
	}

	private boolean isVanished(BlockState state) {
		return state.hasProperty(VANISHED) && state.getValue(VANISHED);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {
		return isVanished(state) ? VANISHED_SHAPE : VoxelShapes.block();
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ACTIVE);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {
		return isVanished(state) ? VoxelShapes.empty() : this.hasCollision ? state.getShape(world, pos) : VoxelShapes.empty();
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos,
			boolean isMoving) {
		if (world.isClientSide) return;

		if (!isVanished(state) && !state.getValue(ACTIVE) && world.hasNeighborSignal(pos)) activate(world, pos);
	}

	@Override
	public boolean canEntityDestroy(BlockState state, IBlockReader world, BlockPos pos, Entity entity) {
		super.canEntityDestroy(state, world, pos, entity);
		return !state.getValue(ACTIVE);
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!isVanished(state) && !state.getValue(ACTIVE)) {
			activate(world, pos);
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}

	@Override
	public float getExplosionResistance(BlockState state, IBlockReader world, BlockPos pos, Explosion explosion) {
		return !state.getValue(ACTIVE) ? 6000F : super.getExplosionResistance(state, world, pos, explosion);
	}

	private void activate(World world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		if (state.getBlock() instanceof GateBlock && !isVanished(state) && !state.getValue(ACTIVE)) {
			world.setBlockAndUpdate(pos, state.setValue(ACTIVE, true));
			world.getBlockTicks().scheduleTick(pos, state.getBlock(), 2 + world.random.nextInt(5));
		}
	}
}
