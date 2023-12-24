package io.github.chaosawakens.common.blocks.tileentities;

import io.github.chaosawakens.common.registry.CAStats;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class RoboCrateBlock extends ContainerBlock {
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

	public RoboCrateBlock(Properties properties) {
		super(properties);
		registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.FALSE));
	}

	@Override
	public ActionResultType use(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult pHit) {
		if (pLevel.isClientSide) return ActionResultType.SUCCESS;
		else {
			TileEntity targetTileEntity = pLevel.getBlockEntity(pPos);
			
			if (targetTileEntity instanceof RoboCrateTileEntity) {
				pPlayer.openMenu((RoboCrateTileEntity)targetTileEntity);
				pPlayer.awardStat(CAStats.INTERACT_WITH_ROBO_CRATE);
				PiglinTasks.angerNearbyPiglins(pPlayer, true);
			}

			return ActionResultType.CONSUME;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState pState, World pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
		if (!pState.is(pNewState.getBlock())) {
			TileEntity targetTileEntity = pLevel.getBlockEntity(pPos);
			
			if (targetTileEntity instanceof IInventory) {
				InventoryHelper.dropContents(pLevel, pPos, (IInventory)targetTileEntity);
				pLevel.updateNeighbourForOutputSignal(pPos, this);
			}

			super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
		}
	}

	@Override
	public void tick(BlockState pState, ServerWorld pLevel, BlockPos pPos, Random pRand) {
		TileEntity targetTileEntity = pLevel.getBlockEntity(pPos);
		
		if (targetTileEntity instanceof RoboCrateTileEntity) ((RoboCrateTileEntity) targetTileEntity).recheckOpen();
	}

	@Nullable
	@Override
	public TileEntity newBlockEntity(IBlockReader world) {
		return new RoboCrateTileEntity();
	}

	@Override
	public BlockRenderType getRenderShape(BlockState pState) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void setPlacedBy(World pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
		if (pStack.hasCustomHoverName()) {
			TileEntity targetTileEntity = pLevel.getBlockEntity(pPos);
			
			if (targetTileEntity instanceof RoboCrateTileEntity) ((RoboCrateTileEntity) targetTileEntity).setCustomName(pStack.getHoverName());
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState pState) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState pBlockState, World pLevel, BlockPos pPos) {
		return Container.getRedstoneSignalFromBlockEntity(pLevel.getBlockEntity(pPos));
	}

	@Override
	public BlockState rotate(BlockState pState, Rotation pRotation) {
		return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState pState, Mirror pMirror) {
		return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(FACING, OPEN);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext pContext) {
		return defaultBlockState().setValue(FACING, pContext.getNearestLookingDirection().getOpposite());
	}
}
