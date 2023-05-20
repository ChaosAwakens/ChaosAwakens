package io.github.chaosawakens.common.blocks.tileentities;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAParticleTypes;
import io.github.chaosawakens.common.registry.CATileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class RoboCrateTileEntity extends LockableLootTileEntity {
	private NonNullList<ItemStack> items = NonNullList.withSize(36, ItemStack.EMPTY);
	private int openCount;

	public RoboCrateTileEntity() {
		super(CATileEntities.ROBO_CRATE.get());
	}

	@Override
	public CompoundNBT save(CompoundNBT pCompound) {
		super.save(pCompound);
		if (!this.trySaveLootTable(pCompound)) {
			ItemStackHelper.saveAllItems(pCompound, this.items);
		}

		return pCompound;
	}

	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		if (!this.tryLoadLootTable(nbt)) {
			ItemStackHelper.loadAllItems(nbt, this.items);
		}

	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getContainerSize() {
		return 36;
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.items;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> pItems) {
		this.items = pItems;
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.chaosawakens.robo_crate");
	}

	@Override
	protected Container createMenu(int pId, PlayerInventory pPlayer) {
		return fourRows(pId, pPlayer, this);
	}

	public static ChestContainer fourRows(int pId, PlayerInventory pPlayer, IInventory pBlockEntity) {
		return new ChestContainer(ContainerType.GENERIC_9x4, pId, pPlayer, pBlockEntity, 4);
	}

	@Override
	public void startOpen(PlayerEntity pPlayer) {
		if (!pPlayer.isSpectator()) {
			if (this.openCount < 0) {
				this.openCount = 0;
			}

			++this.openCount;
			BlockState blockstate = this.getBlockState();
			boolean flag = blockstate.getValue(RoboCrateBlock.OPEN);
			if (!flag) {
				this.playSound(blockstate, SoundEvents.BARREL_OPEN);
				this.updateBlockState(blockstate, true);
				tick();
			}

			this.scheduleRecheck();
		}

	}

	public void tick() {
		World world = this.getLevel();
		BlockPos blockpos = this.worldPosition;
		if (world.isClientSide) {
			double xo = (double)blockpos.getX() + world.random.nextDouble();
			double yo = (double)blockpos.getY() + world.random.nextDouble();
			double zo = (double)blockpos.getZ() + world.random.nextDouble();
			world.addParticle(CAParticleTypes.ROBO_SPARK.get(), xo, yo, zo, level.random.nextBoolean() ? 0.01D : -0.01D, 0.02D, level.random.nextBoolean() ? 0.01D : -0.01D);
		}
	}
	
	@Nullable	
	public SUpdateTileEntityPacket getUpdatePacket() {	 
		return new SUpdateTileEntityPacket(this.worldPosition, 1, this.getUpdateTag());  
	}

	private void scheduleRecheck() {
		this.level.getBlockTicks().scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 5);
	}

	public void recheckOpen() {
		int i = this.worldPosition.getX();
		int j = this.worldPosition.getY();
		int k = this.worldPosition.getZ();
		this.openCount = ChestTileEntity.getOpenCount(this.level, this, i, j, k);
		if (this.openCount > 0) {
			this.scheduleRecheck();
		} else {
			BlockState blockstate = this.getBlockState();
			if (!blockstate.is(CABlocks.ROBO_CRATE.get())) {
				this.setRemoved();
				return;
			}

			boolean flag = blockstate.getValue(RoboCrateBlock.OPEN);
			if (flag) {
				this.playSound(blockstate, SoundEvents.BARREL_CLOSE);
				this.updateBlockState(blockstate, false);
			}
		}

	}

	public void stopOpen(PlayerEntity pPlayer) {
		if (!pPlayer.isSpectator()) {
			--this.openCount;
		}

	}

	private void updateBlockState(BlockState pState, boolean pOpen) {
		this.level.setBlock(this.getBlockPos(), pState.setValue(RoboCrateBlock.OPEN, pOpen), 3);
	}

	private void playSound(BlockState pState, SoundEvent pSound) {
		Vector3i vector3i = pState.getValue(RoboCrateBlock.FACING).getNormal();
		double d0 = (double)this.worldPosition.getX() + 0.5D + (double)vector3i.getX() / 2.0D;
		double d1 = (double)this.worldPosition.getY() + 0.5D + (double)vector3i.getY() / 2.0D;
		double d2 = (double)this.worldPosition.getZ() + 0.5D + (double)vector3i.getZ() / 2.0D;
		this.level.playSound(null, d0, d1, d2, pSound, SoundCategory.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
	}
}
