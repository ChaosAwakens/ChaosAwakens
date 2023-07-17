package io.github.chaosawakens.common.blocks.tileentities;

import java.util.Arrays;
import java.util.Comparator;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAStats;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class DefossilizerBlock extends Block {
	public static final DirectionProperty FACING = HorizontalBlock.FACING;

	public DefossilizerBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState targetState, IBlockReader world) {
		if (targetState.is(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.COPPER.getId())).get())) return new CopperDefossilizerTileEntity();
		if (targetState.is(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.IRON.getId())).get())) return new IronDefossilizerTileEntity();
		if (targetState.is(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.CRYSTAL.getId())).get())) return new CrystalDefossilizerTileEntity();
		return null;
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTrace) {
		if (world.isClientSide) return ActionResultType.SUCCESS;
		
		interactWith(world, pos, player);
		player.awardStat(CAStats.INTERACT_WITH_DEFOSSILIZER);
		
		return ActionResultType.CONSUME;
	}

	private void interactWith(World curWorld, BlockPos targetPos, PlayerEntity interactingPlayer) { //TODO Un-hardcode (if we still maintain this MC version by then)
		TileEntity curTileEntity = curWorld.getBlockEntity(targetPos);
		
		if (!(interactingPlayer instanceof ServerPlayerEntity)) return;
		
		if (curWorld.getBlockState(targetPos).is(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.COPPER.getId())).get()) && curTileEntity instanceof CopperDefossilizerTileEntity) {
			CopperDefossilizerTileEntity copperDefossilizerTileEntity = (CopperDefossilizerTileEntity) curTileEntity;
			NetworkHooks.openGui((ServerPlayerEntity) interactingPlayer, copperDefossilizerTileEntity, copperDefossilizerTileEntity::encodeExtraData);
		}
		
		if (curWorld.getBlockState(targetPos).is(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.IRON.getId())).get()) && curTileEntity instanceof IronDefossilizerTileEntity) {
			IronDefossilizerTileEntity ironDefossilizerTileEntity = (IronDefossilizerTileEntity) curTileEntity;
			NetworkHooks.openGui((ServerPlayerEntity) interactingPlayer, ironDefossilizerTileEntity, ironDefossilizerTileEntity::encodeExtraData);
		}
		
		if (curWorld.getBlockState(targetPos).is(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.CRYSTAL.getId())).get()) && curTileEntity instanceof CrystalDefossilizerTileEntity) {
			CrystalDefossilizerTileEntity crystalDefossilizerTileEntity = (CrystalDefossilizerTileEntity) curTileEntity;
			NetworkHooks.openGui((ServerPlayerEntity) interactingPlayer, crystalDefossilizerTileEntity, crystalDefossilizerTileEntity::encodeExtraData);
		}
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState curState, World curWorld, BlockPos targetPos, BlockState newState, boolean isMoving) {
		if (!curState.is(newState.getBlock())) {
			TileEntity curTileEntity = curWorld.getBlockEntity(targetPos);
			
			if (curState.is(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.COPPER.getId())).get()) && curTileEntity instanceof CopperDefossilizerTileEntity) {
				InventoryHelper.dropContents(curWorld, targetPos, (IInventory) curTileEntity);
				curWorld.updateNeighbourForOutputSignal(targetPos, this);
			}
			
			if (curState.is(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.IRON.getId())).get()) && curTileEntity instanceof IronDefossilizerTileEntity) {
				InventoryHelper.dropContents(curWorld, targetPos, (IInventory) curTileEntity);
				curWorld.updateNeighbourForOutputSignal(targetPos, this);
			}
			
			if (curState.is(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.CRYSTAL.getId())).get()) && curTileEntity instanceof CrystalDefossilizerTileEntity) {
				InventoryHelper.dropContents(curWorld, targetPos, (IInventory) curTileEntity);
				curWorld.updateNeighbourForOutputSignal(targetPos, this);
			}
			
			super.onRemove(curState, curWorld, targetPos, newState, isMoving);
		}
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
	public enum DefossilizerType implements IStringSerializable {
		COPPER(0, "copper"),
		IRON(1, "iron"),
		CRYSTAL(2, "crystal");

		private static final DefossilizerType[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(DefossilizerType::getId)).toArray(DefossilizerType[]::new);
		private final int id;
		private final String name;

		DefossilizerType(int idIn, String name) {
			this.id = idIn;
			this.name = name;
		}

		public int getId() {
			return id;
		}
		
		public String getName() {
			return name;
		}

		public static DefossilizerType byId(int id) {
			if (id < 0 || id >= VALUES.length) id = 0;
			return VALUES[id];
		}

		public static String getDefossilizerTypeByID(int id) {
			for (DefossilizerType defossilizerType : values()) {
				if (defossilizerType.getId() == id) return defossilizerType.getSerializedName();
			}
			return null;
		}

		public static boolean getBooleanDefossilizerTypeByID(int id) {
			for (DefossilizerType defossilizerType : values()) return defossilizerType.getId() == id;
			return false;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}
}
