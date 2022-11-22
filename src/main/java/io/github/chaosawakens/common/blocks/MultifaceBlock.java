package io.github.chaosawakens.common.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.ICollisionReader;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeBlock;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

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

	public MultifaceBlock(Properties p_153822_) {
		super(p_153822_);
		this.registerDefaultState(getDefaultMultifaceState(this.stateDefinition));
		this.shapesCache = this.getShapeForEachState(MultifaceBlock::calculateMultifaceShape);
		this.canRotate = Direction.Plane.HORIZONTAL.stream().allMatch(this::isFaceSupported);
		this.canMirrorX = Direction.Plane.HORIZONTAL.stream().filter(Direction.Axis.X).filter(this::isFaceSupported).count() % 2L == 0L;
		this.canMirrorZ = Direction.Plane.HORIZONTAL.stream().filter(Direction.Axis.Z).filter(this::isFaceSupported).count() % 2L == 0L;
	}

	protected ImmutableMap<BlockState, VoxelShape> getShapeForEachState(Function<BlockState, VoxelShape> func) {
		return this.stateDefinition.getPossibleStates().stream().collect(ImmutableMap.toImmutableMap(Function.identity(), func));
	}

	public static Set<Direction> availableFaces(BlockState state) {
		if (!(state.getBlock() instanceof MultifaceBlock)) {
			return null;
		} else {
			Set<Direction> set = EnumSet.noneOf(Direction.class);

			for(Direction direction : Direction.values()) {
				if (hasFace(state, direction)) {
					set.add(direction);
				}
			}

			return set;
		}
	}

	public static Set<Direction> unpack(byte stateb) {
		Set<Direction> set = EnumSet.noneOf(Direction.class);

		for(Direction direction : Direction.values()) {
			if ((stateb & (byte)(1 << direction.ordinal())) > 0) {
				set.add(direction);
			}
		}

		return set;
	}

	public static byte pack(Collection<Direction> directions) {
		byte b0 = 0;

		for(Direction direction : directions) {
			b0 = (byte)(b0 | 1 << direction.ordinal());
		}

		return b0;
	}

	protected boolean isFaceSupported(Direction p_153921_) {
		return true;
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
		for(Direction direction : DIRECTIONS) {
			if (this.isFaceSupported(direction)) {
				pBuilder.add(getFaceProperty(direction));
			}
		}

	}

	/**
	 * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
	 * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
	 * returns its solidified counterpart.
	 * Note that this method should ideally consider only the specific direction passed in.
	 */
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
		boolean flag = false;

		for(Direction direction : DIRECTIONS) {
			if (hasFace(pState, direction)) {
				BlockPos blockpos = pPos.relative(direction);
				if (!canAttachTo(pWorld, direction, blockpos, pWorld.getBlockState(blockpos))) {
					return false;
				}

				flag = true;
			}
		}

		return flag;
	}

	public boolean canBeReplaced(BlockState pState, BlockItemUseContext pUseContext) {
		return hasAnyVacantFace(pState);
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext pContext) {
		World world = pContext.getLevel();
		BlockPos blockpos = pContext.getClickedPos();
		BlockState blockstate = world.getBlockState(blockpos);
		return Arrays.stream(pContext.getNearestLookingDirections()).map((p_153865_) -> {
			return this.getStateForPlacement(blockstate, world, blockpos, p_153865_);
		}).filter(Objects::nonNull).findFirst().orElse((BlockState)null);
	}

	public boolean isValidStateForPlacement(World world, BlockState state, BlockPos pos, Direction dir) {
		if (this.isFaceSupported(dir) && (!state.is(this) || !hasFace(state, dir))) {
			BlockPos blockpos = pos.relative(dir);
			return canAttachTo(world, dir, blockpos, world.getBlockState(blockpos));
		} else {
			return false;
		}
	}

	@Nullable
	public BlockState getStateForPlacement(BlockState pCurrentState, World pWorld, BlockPos pPos, Direction pLookingDirection) {
		if (!this.isValidStateForPlacement(pWorld, pCurrentState, pPos, pLookingDirection)) {
			return null;
		} else {
			BlockState blockstate;
			if (pCurrentState.is(this)) {
				blockstate = pCurrentState;
			} else if (this.isWaterloggable() && pCurrentState.getFluidState().is(FluidTags.WATER)) {
				blockstate = this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true);
			} else {
				blockstate = this.defaultBlockState();
			}

			return blockstate.setValue(getFaceProperty(pLookingDirection), true);
		}
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 * @deprecated call via {@link net.minecraft.block.AbstractBlock.AbstractBlockState#rotate} whenever
	 * possible. Implementing/overriding is fine.
	 */
	public BlockState rotate(BlockState pState, Rotation pRotation) {
		return !this.canRotate ? pState : this.mapDirections(pState, pRotation::rotate);
	}

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 * @deprecated call via {@link net.minecraft.block.AbstractBlock.AbstractBlockState#mirror} whenever
	 * possible. Implementing/overriding is fine.
	 */
	public BlockState mirror(BlockState pState, Mirror pMirror) {
		if (pMirror == Mirror.FRONT_BACK && !this.canMirrorX) {
			return pState;
		} else {
			return pMirror == Mirror.LEFT_RIGHT && !this.canMirrorZ ? pState : this.mapDirections(pState, pMirror::mirror);
		}
	}

	private BlockState mapDirections(BlockState state, Function<Direction, Direction> dirFunc) {
		BlockState blockstate = state;

		for(Direction direction : DIRECTIONS) {
			if (this.isFaceSupported(direction)) {
				blockstate = blockstate.setValue(getFaceProperty(dirFunc.apply(direction)), state.getValue(getFaceProperty(direction)));
			}
		}

		return blockstate;
	}

	public static boolean hasFace(BlockState state, Direction dir) {
		BooleanProperty booleanproperty = getFaceProperty(dir);
		return state.hasProperty(booleanproperty) && state.getValue(booleanproperty);
	}

	public static boolean canAttachTo(World world, Direction dir, BlockPos pos, BlockState state) {
		return Block.isFaceFull(state.getBlockSupportShape(world, pos), dir.getOpposite()) || Block.isFaceFull(state.getCollisionShape(world, pos), dir.getOpposite());
	}

	private boolean isWaterloggable() {
		return this.stateDefinition.getProperties().contains(BlockStateProperties.WATERLOGGED);
	}

	private static BlockState removeFace(BlockState state, BooleanProperty boolProp) {
		BlockState blockstate = state.setValue(boolProp, false);
		return hasAnyFace(blockstate) ? blockstate : Blocks.AIR.defaultBlockState();
	}

	public static BooleanProperty getFaceProperty(Direction dir) {
		return PROPERTY_BY_DIRECTION.get(dir);
	}

	public static int getFaceCount() {
		int i = 0;
		for (Direction direction : DIRECTIONS) {
			ChaosAwakens.LOGGER.debug("1: " + direction);
			ChaosAwakens.LOGGER.debug("2: " + getFaceProperty(direction));
			ChaosAwakens.LOGGER.debug("3: " + getFaceProperty(direction).getPossibleValues());
			ChaosAwakens.LOGGER.debug("4: " + getFaceProperty(direction).getAllValues());
			ChaosAwakens.LOGGER.debug("5: " + getFaceProperty(direction).getAllValues().findFirst());
			ChaosAwakens.LOGGER.debug("6: " + getFaceProperty(direction).getAllValues().findFirst().get());
			ChaosAwakens.LOGGER.debug("7: " + getFaceProperty(direction).getAllValues().findFirst().get().value());
			if (getFaceProperty(direction).getAllValues().findFirst().get().value()) {
				i++;
			}
		}
		return i;
	}

	private static BlockState getDefaultMultifaceState(StateContainer<Block, BlockState> container) {
		BlockState blockstate = container.any();

		for(BooleanProperty booleanproperty : PROPERTY_BY_DIRECTION.values()) {
			if (blockstate.hasProperty(booleanproperty)) {
				blockstate = blockstate.setValue(booleanproperty, false);
			}
		}

		return blockstate;
	}

	private static VoxelShape calculateMultifaceShape(BlockState state) {
		VoxelShape voxelshape = VoxelShapes.empty();

		for(Direction direction : DIRECTIONS) {
			if (hasFace(state, direction)) {
				voxelshape = VoxelShapes.or(voxelshape, SHAPE_BY_DIRECTION.get(direction));
			}
		}

		return voxelshape.isEmpty() ? VoxelShapes.block() : voxelshape;
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
