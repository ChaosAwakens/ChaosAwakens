package io.github.chaosawakens.common.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.ICollisionReader;
import net.minecraft.world.World;

import java.util.Map;

@SuppressWarnings("all")
public class PipeBlock extends Block {
	private static final Direction[] DIRECTIONS = Direction.values();
	public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
	public static final BooleanProperty EAST = BlockStateProperties.EAST;
	public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
	public static final BooleanProperty WEST = BlockStateProperties.WEST;
	public static final BooleanProperty UP = BlockStateProperties.UP;
	public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
	public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = ImmutableMap.copyOf(Util.make(Maps.newEnumMap(Direction.class), (p_55164_) -> {
		p_55164_.put(Direction.NORTH, NORTH);
		p_55164_.put(Direction.EAST, EAST);
		p_55164_.put(Direction.SOUTH, SOUTH);
		p_55164_.put(Direction.WEST, WEST);
		p_55164_.put(Direction.UP, UP);
		p_55164_.put(Direction.DOWN, DOWN);
	}));
	protected final VoxelShape[] shapeByIndex;

	public PipeBlock(float p_55159_, Properties p_55160_) {
		super(p_55160_);
		this.shapeByIndex = this.makeShapes(p_55159_);
	}

	private VoxelShape[] makeShapes(float pApothem) {
		float f = 0.5F - pApothem;
		float f1 = 0.5F + pApothem;
		VoxelShape voxelshape = Block.box((f * 16.0F), (f * 16.0F), (f * 16.0F), (f1 * 16.0F), (f1 * 16.0F), (f1 * 16.0F));
		VoxelShape[] avoxelshape = new VoxelShape[DIRECTIONS.length];

		for(int i = 0; i < DIRECTIONS.length; ++i) {
			Direction direction = DIRECTIONS[i];
			avoxelshape[i] = VoxelShapes.box(0.5D + Math.min((-pApothem), (double)direction.getStepX() * 0.5D), 0.5D + Math.min((-pApothem), (double)direction.getStepY() * 0.5D), 0.5D + Math.min((-pApothem), (double)direction.getStepZ() * 0.5D), 0.5D + Math.max(pApothem, (double)direction.getStepX() * 0.5D), 0.5D + Math.max(pApothem, (double)direction.getStepY() * 0.5D), 0.5D + Math.max(pApothem, (double)direction.getStepZ() * 0.5D));
		}

		VoxelShape[] avoxelshape1 = new VoxelShape[64];

		for(int k = 0; k < 64; ++k) {
			VoxelShape voxelshape1 = voxelshape;

			for(int j = 0; j < DIRECTIONS.length; ++j) {
				if ((k & 1 << j) != 0) {
					voxelshape1 = VoxelShapes.or(voxelshape1, avoxelshape[j]);
				}
			}

			avoxelshape1[k] = voxelshape1;
		}

		return avoxelshape1;
	}

	public boolean propagatesSkylightDown(BlockState pState, World pReader, BlockPos pPos) {
		return false;
	}

	public VoxelShape getShape(BlockState pState, World pWorld, BlockPos pPos, ICollisionReader pContext) {
		return this.shapeByIndex[this.getAABBIndex(pState)];
	}

	protected int getAABBIndex(BlockState pState) {
		int i = 0;

		for(int j = 0; j < DIRECTIONS.length; ++j) {
			if (pState.getValue(PROPERTY_BY_DIRECTION.get(DIRECTIONS[j]))) {
				i |= 1 << j;
			}
		}

		return i;
	}
}
