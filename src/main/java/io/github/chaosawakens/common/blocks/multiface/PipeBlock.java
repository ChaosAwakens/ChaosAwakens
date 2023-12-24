package io.github.chaosawakens.common.blocks.multiface;

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

public class PipeBlock extends Block {
	private static final Direction[] DIRECTIONS = Direction.values();
	public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
	public static final BooleanProperty EAST = BlockStateProperties.EAST;
	public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
	public static final BooleanProperty WEST = BlockStateProperties.WEST;
	public static final BooleanProperty UP = BlockStateProperties.UP;
	public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
	public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = ImmutableMap.copyOf(Util.make(Maps.newEnumMap(Direction.class), (dirProperties) -> {
		dirProperties.put(Direction.NORTH, NORTH);
		dirProperties.put(Direction.EAST, EAST);
		dirProperties.put(Direction.SOUTH, SOUTH);
		dirProperties.put(Direction.WEST, WEST);
		dirProperties.put(Direction.UP, UP);
		dirProperties.put(Direction.DOWN, DOWN);
	}));
	protected final VoxelShape[] shapeByIndex;

	public PipeBlock(float shapeByIndex, Properties properties) {
		super(properties);
		this.shapeByIndex = makeShapes(shapeByIndex);
	}

	private VoxelShape[] makeShapes(float apothem) {
		float apothemOffsetN = 0.5F - apothem;
		float apothemOffsetP = 0.5F + apothem;
		VoxelShape blockShape = Block.box((apothemOffsetN * 16.0F), (apothemOffsetN * 16.0F), (apothemOffsetN * 16.0F), (apothemOffsetP * 16.0F), (apothemOffsetP * 16.0F), (apothemOffsetP * 16.0F));
		VoxelShape[] shapeByDirection = new VoxelShape[DIRECTIONS.length];

		for (int i = 0; i < DIRECTIONS.length; ++i) {
			Direction curDirection = DIRECTIONS[i];
			shapeByDirection[i] = VoxelShapes.box(0.5D + Math.min((-apothem), (double)curDirection.getStepX() * 0.5D), 0.5D + Math.min((-apothem), (double)curDirection.getStepY() * 0.5D), 0.5D + Math.min((-apothem), (double)curDirection.getStepZ() * 0.5D), 0.5D + Math.max(apothem, (double)curDirection.getStepX() * 0.5D), 0.5D + Math.max(apothem, (double)curDirection.getStepY() * 0.5D), 0.5D + Math.max(apothem, (double)curDirection.getStepZ() * 0.5D));
		}

		VoxelShape[] blockShapes = new VoxelShape[64];

		for (int k = 0; k < 64; ++k) {
			VoxelShape blockShapeByDirection = blockShape;

			for (int curDir = 0; curDir < DIRECTIONS.length; ++curDir) {
				if ((k & 1 << curDir) != 0) {
					blockShapeByDirection = VoxelShapes.or(blockShapeByDirection, shapeByDirection[curDir]);
				}
			}

			blockShapes[k] = blockShapeByDirection;
		}

		return blockShapes;
	}

	public boolean propagatesSkylightDown(BlockState pState, World pReader, BlockPos pPos) {
		return false;
	}

	public VoxelShape getShape(BlockState pState, World pWorld, BlockPos pPos, ICollisionReader pContext) {
		return this.shapeByIndex[getAABBIndex(pState)];
	}

	protected int getAABBIndex(BlockState pState) {
		int aabbIndex = 0;

		for(int shapeIndex = 0; shapeIndex < DIRECTIONS.length; ++shapeIndex) {
			if (pState.getValue(PROPERTY_BY_DIRECTION.get(DIRECTIONS[shapeIndex]))) {
				aabbIndex |= 1 << shapeIndex;
			}
		}

		return aabbIndex;
	}
}
