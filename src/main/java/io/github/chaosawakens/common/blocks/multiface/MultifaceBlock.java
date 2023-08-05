package io.github.chaosawakens.common.blocks.multiface;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

@SuppressWarnings("all")
public abstract class MultifaceBlock extends Block {
	private static final float AABB_OFFSET = 1.0F;
	private static final VoxelShape UP_AABB = Block.box(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	private static final VoxelShape DOWN_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
	private static final VoxelShape WEST_AABB = Block.box(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
	private static final VoxelShape EAST_AABB = Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	private static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
	private static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
	private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION;
	public static final Map<Direction, VoxelShape> SHAPE_BY_DIRECTION = Util.make(Maps.newEnumMap(Direction.class), (map) -> {
		map.put(Direction.NORTH, NORTH_AABB);
		map.put(Direction.EAST, EAST_AABB);
		map.put(Direction.SOUTH, SOUTH_AABB);
		map.put(Direction.WEST, WEST_AABB);
		map.put(Direction.UP, UP_AABB);
		map.put(Direction.DOWN, DOWN_AABB);
	});
	protected static final Direction[] DIRECTIONS = Direction.values();
	private final ImmutableMap<BlockState, VoxelShape> shapesCache;
	private final boolean canRotate;
	private final boolean canMirrorX;
	private final boolean canMirrorZ;

	public MultifaceBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(getDefaultMultifaceState(this.stateDefinition));
		this.shapesCache = this.getShapeForEachState(MultifaceBlock::calculateMultifaceShape);
		this.canRotate = Direction.Plane.HORIZONTAL.stream().allMatch(this::isFaceSupported);
		this.canMirrorX = Direction.Plane.HORIZONTAL.stream().filter(Direction.Axis.X).filter(this::isFaceSupported).count() % 2L == 0L;
		this.canMirrorZ = Direction.Plane.HORIZONTAL.stream().filter(Direction.Axis.Z).filter(this::isFaceSupported).count() % 2L == 0L;
	}

	protected ImmutableMap<BlockState, VoxelShape> getShapeForEachState(Function<BlockState, VoxelShape> stateShapeFunc) {
		return this.stateDefinition.getPossibleStates().stream().collect(ImmutableMap.toImmutableMap(Function.identity(), stateShapeFunc));
	}

	public static Set<Direction> availableFaces(BlockState state) {
		if (!(state.getBlock() instanceof MultifaceBlock)) return null;
		else {
			Set<Direction> availableDirs = EnumSet.noneOf(Direction.class);

			for (Direction targetDir : Direction.values()) {
				if (hasFace(state, targetDir)) availableDirs.add(targetDir);
			}

			return availableDirs;
		}
	}

	public static Set<Direction> unpack(byte curStateByte) {
		Set<Direction> availableDirections = EnumSet.noneOf(Direction.class);

		for (Direction targetDir : Direction.values()) {
			if ((curStateByte & (byte)(1 << targetDir.ordinal())) > 0) availableDirections.add(targetDir);
		}

		return availableDirections;
	}

	public static byte pack(Collection<Direction> directions) {
		byte packedDirBytes = 0;

		for (Direction targetDir : directions) packedDirBytes = (byte) (packedDirBytes | 1 << targetDir.ordinal());

		return packedDirBytes;
	}

	protected boolean isFaceSupported(Direction targetDir) {
		return true;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
		for (Direction targetDir : DIRECTIONS) {
			if (isFaceSupported(targetDir)) {
				pBuilder.add(getFaceProperty(targetDir));
			}
		}
	}

	@Override
	public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, IWorld pWorld, BlockPos pCurrentPos, BlockPos pNeighborPos) {
		if (!hasAnyFace(pState)) return Blocks.AIR.defaultBlockState();
		return hasFace(pState, pDirection) && !canAttachTo(pWorld, pDirection, pNeighborPos, pNeighborState) ? removeFace(pState, getFaceProperty(pDirection)) : pState;
	}
	
	@Override
	public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
		return this.shapesCache.get(pState);
	}

	public boolean canSurvive(BlockState pState, World pWorld, BlockPos pPos) {
		boolean canAttachToFace = false;

		for (Direction targetDir : DIRECTIONS) {
			if (hasFace(pState, targetDir)) {
				BlockPos relativePos = pPos.relative(targetDir);
				
				if (!canAttachTo(pWorld, targetDir, relativePos, pWorld.getBlockState(relativePos))) return false;

				canAttachToFace = true;
			}
		}

		return canAttachToFace;
	}

	@Override
	public boolean canBeReplaced(BlockState pState, BlockItemUseContext pUseContext) {
		return hasAnyVacantFace(pState);
	}
	
	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext pContext) {
		World curWorld = pContext.getLevel();
		BlockPos targetPos = pContext.getClickedPos();
		BlockState targetState = curWorld.getBlockState(targetPos);
		
		return Arrays.stream(pContext.getNearestLookingDirections()).map((targettDir) -> {
			return getStateForPlacement(targetState, curWorld, targetPos, targettDir);
		}).filter(Objects::nonNull).findFirst().orElse((BlockState) null);
	}

	public boolean isValidStateForPlacement(World world, BlockState curState, BlockPos targetPos, Direction targetDir) {
		return isFaceSupported(targetDir) && (!curState.is(this) || !hasFace(curState, targetDir)) ? canAttachTo(world, targetDir, targetPos.relative(targetDir), world.getBlockState(targetPos.relative(targetDir))) : false;
	}

	@Nullable
	public BlockState getStateForPlacement(BlockState pCurrentState, World pWorld, BlockPos pPos, Direction pLookingDirection) {
		if (!isValidStateForPlacement(pWorld, pCurrentState, pPos, pLookingDirection)) return null;
		else {
			BlockState targetState;
			
			if (pCurrentState.is(this)) targetState = pCurrentState;
			else if (isWaterloggable() && pCurrentState.getFluidState().is(FluidTags.WATER)) targetState = defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true);
			else targetState = defaultBlockState();

			return targetState.setValue(getFaceProperty(pLookingDirection), true);
		}
	}

	@Override
	public BlockState rotate(BlockState pState, Rotation pRotation) {
		return !canRotate ? pState : mapDirections(pState, pRotation::rotate);
	}

	@Override
	public BlockState mirror(BlockState pState, Mirror pMirror) {
		if (pMirror == Mirror.FRONT_BACK && !canMirrorX) return pState;
		else return pMirror == Mirror.LEFT_RIGHT && !canMirrorZ ? pState : mapDirections(pState, pMirror::mirror);
	}

	private BlockState mapDirections(BlockState targetState, Function<Direction, Direction> mapDirFunc) {
		BlockState curState = targetState;

		for (Direction targetDir : DIRECTIONS) {
			if (isFaceSupported(targetDir)) curState = curState.setValue(getFaceProperty(mapDirFunc.apply(targetDir)), curState.getValue(getFaceProperty(targetDir)));
		}

		return curState;
	}

	public static boolean hasFace(BlockState state, Direction dir) {
		BooleanProperty faceProperty = getFaceProperty(dir);
		
		return state.hasProperty(faceProperty) && state.getValue(faceProperty);
	}

	public static boolean canAttachTo(IWorld world, Direction dir, BlockPos pos, BlockState state) {
		return Block.isFaceFull(state.getBlockSupportShape(world, pos), dir.getOpposite()) || Block.isFaceFull(state.getCollisionShape(world, pos), dir.getOpposite());
	}

	private boolean isWaterloggable() {
		return stateDefinition.getProperties().contains(BlockStateProperties.WATERLOGGED);
	}

	private static BlockState removeFace(BlockState state, BooleanProperty boolProp) {
		BlockState blockstate = state.setValue(boolProp, false);
		return hasAnyFace(blockstate) ? blockstate : Blocks.AIR.defaultBlockState();
	}

	public static BooleanProperty getFaceProperty(Direction targetDir) {
		return PROPERTY_BY_DIRECTION.get(targetDir);
	}

	public static int getFaceCount() {
		int faceCount = 0;
		
		for (Direction targetDir : DIRECTIONS) {
			if (getFaceProperty(targetDir).getAllValues().findFirst().get().value()) faceCount++;
		}
		
		return faceCount;
	}

	private static BlockState getDefaultMultifaceState(StateContainer<Block, BlockState> container) {
		BlockState pickedTargetState = container.any();

		for (BooleanProperty dirProperty : PROPERTY_BY_DIRECTION.values()) {
			if (pickedTargetState.hasProperty(dirProperty)) pickedTargetState = pickedTargetState.setValue(dirProperty, false);
		}

		return pickedTargetState;
	}

	private static VoxelShape calculateMultifaceShape(BlockState state) {
		VoxelShape curVoxelShape = VoxelShapes.empty();

		for (Direction targetDir : DIRECTIONS) {
			if (hasFace(state, targetDir)) curVoxelShape = VoxelShapes.or(curVoxelShape, SHAPE_BY_DIRECTION.get(targetDir));
		}

		return curVoxelShape.isEmpty() ? VoxelShapes.block() : curVoxelShape;
	}

	protected static boolean hasAnyFace(BlockState state) {
		return Arrays.stream(DIRECTIONS).anyMatch((dir) -> {
			return hasFace(state, dir);
		});
	}

	private static boolean hasAnyVacantFace(BlockState state) {
		return Arrays.stream(DIRECTIONS).anyMatch((dir) -> {
			return !hasFace(state, dir);
		});
	}
}
