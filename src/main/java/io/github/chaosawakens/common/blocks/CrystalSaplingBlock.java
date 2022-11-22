package io.github.chaosawakens.common.blocks;

import net.minecraft.block.*;
import net.minecraft.block.trees.Tree;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class CrystalSaplingBlock extends Block implements IGrowable, IPlantable {
	public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
	private final Tree treeGrower;

	public CrystalSaplingBlock(Tree tree, AbstractBlock.Properties properties) {
		super(properties);
		this.treeGrower = tree;
		this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0));
	}

	public boolean canSurvive(BlockState state, IWorldReader worldReader, BlockPos pos) {
		BlockPos blockpos = pos.below();
		BlockState blockstate = worldReader.getBlockState(blockpos);
		return blockstate.getBlock() instanceof CrystalBlock && blockstate.isRandomlyTicking();
	}

	public VoxelShape getShape(BlockState blockState, IBlockReader blockReader, BlockPos pos, ISelectionContext selectionContext) {
		return SHAPE;
	}

	@SuppressWarnings("deprecation")
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		return !state.canSurvive(world, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, world, currentPos, facingPos);
	}

	public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos pos, Random random) {
		if (serverWorld.getMaxLocalRawBrightness(pos.above()) >= 9 && random.nextInt(7) == 0) {
			if (!serverWorld.isAreaLoaded(pos, 1)) return;
			this.advanceTree(serverWorld, pos, blockState, random);
		}
	}

	public void advanceTree(ServerWorld serverWorld, BlockPos pos, BlockState blockState, Random random) {
		if (blockState.getValue(STAGE) == 0) {
			serverWorld.setBlock(pos, blockState.cycle(STAGE), 4);
		} else {
			if (!net.minecraftforge.event.ForgeEventFactory.saplingGrowTree(serverWorld, random, pos)) return;
			this.treeGrower.growTree(serverWorld, serverWorld.getChunkSource().getGenerator(), pos, blockState, random);
		}
	}

	public boolean isValidBonemealTarget(IBlockReader blockReader, BlockPos pos, BlockState blockState, boolean isClient) {
		return true;
	}

	public boolean isBonemealSuccess(World world, Random random, BlockPos pos, BlockState blockState) {
		return (double) world.random.nextFloat() < 0.45D;
	}

	public void performBonemeal(ServerWorld serverWorld, Random random, BlockPos pos, BlockState blockState) {
		this.advanceTree(serverWorld, pos, blockState, random);
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
		stateBuilder.add(STAGE);
	}

	public boolean propagatesSkylightDown(BlockState state, IBlockReader blockReader, BlockPos pos) {
		return state.getFluidState().isEmpty();
	}

	@SuppressWarnings("deprecation")
	public boolean isPathfindable(BlockState state, IBlockReader blockReader, BlockPos pos, PathType type) {
		return type == PathType.AIR && !this.hasCollision || super.isPathfindable(state, blockReader, pos, type);
	}

	@Override
	public BlockState getPlant(IBlockReader world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		if (state.getBlock() != this) return defaultBlockState();
		return state;
	}
}
