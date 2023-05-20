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
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		if (state.is(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.COPPER.getId())).get())) {
			return new DefossilizerCopperTileEntity();
		}
		if (state.is(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.IRON.getId())).get())) {
			return new DefossilizerIronTileEntity();
		}
		return null;
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTrace) {
		if (world.isClientSide) return ActionResultType.SUCCESS;
		this.interactWith(world, pos, player);
		player.awardStat(CAStats.INTERACT_WITH_DEFOSSILIZER);
		return ActionResultType.CONSUME;
	}

	private void interactWith(World world, BlockPos pos, PlayerEntity player) {
		TileEntity tileEntity = world.getBlockEntity(pos);
		if (!(player instanceof ServerPlayerEntity)) return;
		if (world.getBlockState(pos).is(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.COPPER.getId())).get()) && tileEntity instanceof DefossilizerCopperTileEntity) {
			DefossilizerCopperTileEntity te = (DefossilizerCopperTileEntity) tileEntity;
			NetworkHooks.openGui((ServerPlayerEntity) player, te, te::encodeExtraData);
		}
		if (world.getBlockState(pos).is(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.IRON.getId())).get()) && tileEntity instanceof DefossilizerIronTileEntity) {
			DefossilizerIronTileEntity te = (DefossilizerIronTileEntity) tileEntity;
			NetworkHooks.openGui((ServerPlayerEntity) player, te, te::encodeExtraData);
		}
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			TileEntity tileEntity = world.getBlockEntity(pos);
			if (state.is(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.COPPER.getId())).get()) && tileEntity instanceof DefossilizerCopperTileEntity) {
				InventoryHelper.dropContents(world, pos, (IInventory) tileEntity);
				world.updateNeighbourForOutputSignal(pos, this);
			}
			if (state.is(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.IRON.getId())).get()) && tileEntity instanceof DefossilizerIronTileEntity) {
				InventoryHelper.dropContents(world, pos, (IInventory) tileEntity);
				world.updateNeighbourForOutputSignal(pos, this);
			}
			super.onRemove(state, world, pos, newState, isMoving);
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
			return this.id;
		}

		public static DefossilizerType byId(int id) {
			if(id < 0 || id >= VALUES.length) id = 0;
			return VALUES[id];
		}

		public static String getDefossilizerTypeByID(int id) {
			for(DefossilizerType defossilizerType : values()) {
				if(defossilizerType.getId() == id) return defossilizerType.getSerializedName();
			}
			return null;
		}

		public static boolean getBooleanDefossilizerTypeByID(int id) {
			for(DefossilizerType defossilizerType : values()) {
				if(defossilizerType.getId() == id) return true;
			}
			return false;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}
}
