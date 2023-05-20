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
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.ICollisionReader;
import net.minecraft.world.World;

public abstract class MultifaceBlock extends Block {
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

	protected ImmutableMap<BlockState, VoxelShape> getShapeForEachState(Function<BlockState, VoxelShape> shapeFunc) {
		return this.stateDefinition.getPossibleStates().stream().collect(ImmutableMap.toImmutableMap(Function.identity(), shapeFunc));
	}

	public static Set<Direction> availableFaces(BlockState state) {
		if (!(state.getBlock() instanceof MultifaceBlock)) {
			return null;
		} else {
			Set<Direction> directions = EnumSet.noneOf(Direction.class);

			for(Direction direction : Direction.values()) {
				if (hasFace(state, direction)) {
					directions.add(direction);
				}
			}
			return directions;
		}
	}

	public static Set<Direction> unpack(byte stateb) {
		Set<Direction> directions = EnumSet.noneOf(Direction.class);

		for(Direction direction : Direction.values()) {
			if ((stateb & (byte)(1 << direction.ordinal())) > 0) {
				directions.add(direction);
			}
		}

		return directions;
	}

	public static byte pack(Collection<Direction> directions) {
		byte dirState = 0;

		for(Direction direction : directions) {
			dirState = (byte)(dirState | 1 << direction.ordinal());
		}

		return dirState;
	}

	protected boolean isFaceSupported(Direction direction) {
		return true;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
		for(Direction direction : DIRECTIONS) {
			if (this.isFaceSupported(direction)) {
				pBuilder.add(getFaceProperty(direction));
			}
		}

	}

	public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, World pWorld, BlockPos pCurrentPos, BlockPos pNeighborPos) {
		if (!hasAnyFace(pState)) {
			return Blocks.AIR.defaultBlockState();
		} else {
			return hasFace(pState, pDirection) && !canAttachTo(pWorld, pDirection, pNeighborPos, pNeighborState) ? removeFace(pState, getFaceProperty(pDirection)) : pState;
		}
	}

	public VoxelShape getShape(BlockState pState, World pWorld, BlockPos pPos, ICollisionReader pContext) {
		return this.shapesCache.get(pState);
	}

	public boolean canSurvive(BlockState pState, World pWorld, BlockPos pPos) {
		boolean canSurvive = false;

		for(Direction direction : DIRECTIONS) {
			if (hasFace(pState, direction)) {
				BlockPos blockpos = pPos.relative(direction);
				if (!canAttachTo(pWorld, direction, blockpos, pWorld.getBlockState(blockpos))) {
					return false;
				}
				canSurvive = true;
			}
		}
		return canSurvive;
	}

	@Override
	public boolean canBeReplaced(BlockState pState, BlockItemUseContext pUseContext) {
		return hasAnyVacantFace(pState);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext pContext) {
		World world = pContext.getLevel();
		BlockPos targetPos = pContext.getClickedPos();
		BlockState targetState = world.getBlockState(targetPos);
		return Arrays.stream(pContext.getNearestLookingDirections()).map((direction) -> {
			return this.getStateForPlacement(targetState, world, targetPos, direction);
		}).filter(Objects::nonNull).findFirst().orElse((BlockState)null);
	}

	public boolean isValidStateForPlacement(World world, BlockState state, BlockPos pos, Direction dir) {
		if (this.isFaceSupported(dir) && (!state.is(this) || !hasFace(state, dir))) {
			BlockPos relativePos = pos.relative(dir);
			return canAttachTo(world, dir, relativePos, world.getBlockState(relativePos));
		} else {
			return false;
		}
	}

	@Nullable
	public BlockState getStateForPlacement(BlockState pCurrentState, World pWorld, BlockPos pPos, Direction pLookingDirection) {
		if (!this.isValidStateForPlacement(pWorld, pCurrentState, pPos, pLookingDirection)) {
			return null;
		} else {
			BlockState targetState;
			if (pCurrentState.is(this)) {
				targetState = pCurrentState;
			} else if (this.isWaterloggable() && pCurrentState.getFluidState().is(FluidTags.WATER)) {
				targetState = this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true);
			} else {
				targetState = this.defaultBlockState();
			}
			return targetState.setValue(getFaceProperty(pLookingDirection), true);
		}
	}

	@Override
	public BlockState rotate(BlockState pState, Rotation pRotation) {
		return !this.canRotate ? pState : this.mapDirections(pState, pRotation::rotate);
	}

	@Override
	public BlockState mirror(BlockState pState, Mirror pMirror) {
		if (pMirror == Mirror.FRONT_BACK && !this.canMirrorX) {
			return pState;
		} else {
			return pMirror == Mirror.LEFT_RIGHT && !this.canMirrorZ ? pState : this.mapDirections(pState, pMirror::mirror);
		}
	}

	private BlockState mapDirections(BlockState state, Function<Direction, Direction> dirFunc) {
		BlockState targetState = state;

		for(Direction direction : DIRECTIONS) {
			if (this.isFaceSupported(direction)) {
				targetState = targetState.setValue(getFaceProperty(dirFunc.apply(direction)), state.getValue(getFaceProperty(direction)));
			}
		}

		return targetState;
	}

	public static boolean hasFace(BlockState state, Direction dir) {
		BooleanProperty faceProperty = getFaceProperty(dir);
		return state.hasProperty(faceProperty) && state.getValue(faceProperty);
	}

	public static boolean canAttachTo(World world, Direction dir, BlockPos pos, BlockState state) {
		return Block.isFaceFull(state.getBlockSupportShape(world, pos), dir.getOpposite()) || Block.isFaceFull(state.getCollisionShape(world, pos), dir.getOpposite());
	}

	private boolean isWaterloggable() {
		return this.stateDefinition.getProperties().contains(BlockStateProperties.WATERLOGGED);
	}

	private static BlockState removeFace(BlockState state, BooleanProperty boolProp) {
		BlockState targetState = state.setValue(boolProp, false);
		return hasAnyFace(targetState) ? targetState : Blocks.AIR.defaultBlockState();
	}

	public static BooleanProperty getFaceProperty(Direction dir) {
		return PROPERTY_BY_DIRECTION.get(dir);
	}

	public static int getFaceCount() {
		int i = 0;
		for (Direction direction : DIRECTIONS) {
/*			ChaosAwakens.LOGGER.debug("1: " + direction);
			ChaosAwakens.LOGGER.debug("2: " + getFaceProperty(direction));
			ChaosAwakens.LOGGER.debug("3: " + getFaceProperty(direction).getPossibleValues());
			ChaosAwakens.LOGGER.debug("4: " + getFaceProperty(direction).getAllValues());
			ChaosAwakens.LOGGER.debug("5: " + getFaceProperty(direction).getAllValues().findFirst());
			ChaosAwakens.LOGGER.debug("6: " + getFaceProperty(direction).getAllValues().findFirst().get());
			ChaosAwakens.LOGGER.debug("7: " + getFaceProperty(direction).getAllValues().findFirst().get().value()); */
			if (getFaceProperty(direction).getAllValues().findFirst().get().value()) {
				i++;
			}
		}
		return i;
	}

	private static BlockState getDefaultMultifaceState(StateContainer<Block, BlockState> container) {
		BlockState containerTargetState = container.any();

		for(BooleanProperty booleanproperty : PROPERTY_BY_DIRECTION.values()) {
			if (containerTargetState.hasProperty(booleanproperty)) {
				containerTargetState = containerTargetState.setValue(booleanproperty, false);
			}
		}
		return containerTargetState;
	}

	private static VoxelShape calculateMultifaceShape(BlockState state) {
		VoxelShape multiFaceShape = VoxelShapes.empty();

		for(Direction direction : DIRECTIONS) {
			if (hasFace(state, direction)) {
				multiFaceShape = VoxelShapes.or(multiFaceShape, SHAPE_BY_DIRECTION.get(direction));
			}
		}
		return multiFaceShape.isEmpty() ? VoxelShapes.block() : multiFaceShape;
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
