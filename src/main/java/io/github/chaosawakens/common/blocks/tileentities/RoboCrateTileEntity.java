package io.github.chaosawakens.common.blocks.tileentities;

import javax.annotation.Nullable;

import io.github.chaosawakens.ChaosAwakens;
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
	private static final TranslationTextComponent CONTAINER_NAME = new TranslationTextComponent("container." + ChaosAwakens.MODID + ".robo_crate");
	private NonNullList<ItemStack> items = NonNullList.withSize(36, ItemStack.EMPTY);
	private int openCount;

	public RoboCrateTileEntity() {
		super(CATileEntities.ROBO_CRATE.get());
	}

	@Override
	public CompoundNBT save(CompoundNBT pCompound) {
		super.save(pCompound);
		
		if (!this.trySaveLootTable(pCompound)) ItemStackHelper.saveAllItems(pCompound, items);

		return pCompound;
	}

	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		
		if (!this.tryLoadLootTable(nbt)) ItemStackHelper.loadAllItems(nbt, items);
	}

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
		return CONTAINER_NAME;
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
			if (this.openCount < 0) this.openCount = 0;

			++this.openCount;
			
			BlockState curState = getBlockState();
			boolean isOpen = curState.getValue(RoboCrateBlock.OPEN);
			
			if (!isOpen) {
				playSound(curState, SoundEvents.BARREL_OPEN);
				setOpen(curState, true);
				tick();
			}

			scheduleRecheck();
		}
	}

	public void tick() {
		World curWorld = getLevel();
		BlockPos curPos = worldPosition;
		
		if (curWorld.isClientSide) {
			double xOffset = (double) curPos.getX() + curWorld.random.nextDouble();
			double yOffset = (double) curPos.getY() + curWorld.random.nextDouble();
			double zOffset = (double) curPos.getZ() + curWorld.random.nextDouble();
			
			curWorld.addParticle(CAParticleTypes.ROBO_SPARK.get(), xOffset, yOffset, zOffset, level.random.nextBoolean() ? 0.01D : -0.01D, 0.02D, level.random.nextBoolean() ? 0.01D : -0.01D);
		}
	}
	
	@Nullable
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {	 
		return new SUpdateTileEntityPacket(worldPosition, 1, getUpdateTag());  
	}

	private void scheduleRecheck() {
		level.getBlockTicks().scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 5);
	}

	public void recheckOpen() {
		int curX = worldPosition.getX();
		int curY = worldPosition.getY();
		int curZ = worldPosition.getZ();
		this.openCount = ChestTileEntity.getOpenCount(level, this, curX, curY, curZ);
		
		if (openCount > 0) scheduleRecheck();
		else {
			BlockState curState = getBlockState();
			
			if (!curState.is(CABlocks.ROBO_CRATE.get())) {
				this.setRemoved();
				return;
			}

			boolean isOpen = curState.getValue(RoboCrateBlock.OPEN);
			
			if (isOpen) {
				playSound(curState, SoundEvents.BARREL_CLOSE);
				setOpen(curState, false);
			}
		}
	}

	@Override
	public void stopOpen(PlayerEntity pPlayer) {
		if (!pPlayer.isSpectator()) --openCount;
	}

	private void setOpen(BlockState pState, boolean pOpen) {
		level.setBlock(getBlockPos(), pState.setValue(RoboCrateBlock.OPEN, pOpen), 3);
	}

	private void playSound(BlockState pState, SoundEvent pSound) {
		Vector3i curFacingVec = pState.getValue(RoboCrateBlock.FACING).getNormal();
		double angledX = (double) worldPosition.getX() + 0.5D + (double) curFacingVec.getX() / 2.0D;
		double angledY = (double) worldPosition.getY() + 0.5D + (double) curFacingVec.getY() / 2.0D;
		double angledZ = (double) worldPosition.getZ() + 0.5D + (double) curFacingVec.getZ() / 2.0D;
		
		level.playSound(null, angledX, angledY, angledZ, pSound, SoundCategory.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
	}
}
