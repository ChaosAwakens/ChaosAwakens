package io.github.chaosawakens.common.blocks.tileentities;

import javax.annotation.Nullable;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.tileentities.containers.CrystalDefossilizerContainer;
import io.github.chaosawakens.common.crafting.recipe.AbstractDefossilizingRecipe;
import io.github.chaosawakens.common.crafting.recipe.DefossilizingRecipe;
import io.github.chaosawakens.common.items.PowerChipItem;
import io.github.chaosawakens.common.items.bucket.PinkTourmalineBucketItem;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CARecipeTypes;
import io.github.chaosawakens.common.registry.CATileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class CrystalDefossilizerTileEntity extends LockableTileEntity implements ISidedInventory, ITickableTileEntity, IItemHandler, ICapabilityProvider {
	public static final int WORK_TIME = 4 * AbstractDefossilizingRecipe.getDefossilizingTime();
	private static final TranslationTextComponent CONTAINER_NAME = new TranslationTextComponent("container." + ChaosAwakens.MODID + ".crystal_defossilizer");
	private static final int[] SLOTS_FOR_UP = new int[] { 0 };
	private static final int[] SLOTS_FOR_DOWN = new int[] { 3, 1 };
	private static final int[] SLOTS_FOR_BUCKET = new int[] { 1 };
	private static final int[] SLOTS_FOR_CHIP = new int[] { 2 };
	private NonNullList<ItemStack> items;
	private final LazyOptional<? extends IItemHandler>[] handlers;
	private int progress = 0;
	private final IIntArray fields = new IIntArray() {
		@Override
		public int get(int index) {
			switch (index) {
				case 0: return progress;
				default: return 0;
			}
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
				case 0:
					progress = value;
					break;
			}
		}

		@Override
		public int getCount() {
			return 1;
		}
	};
	
	public CrystalDefossilizerTileEntity() {
		super(CATileEntities.CRYSTAL_DEFOSSILIZER.get());
		this.handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
		this.items = NonNullList.withSize(4, ItemStack.EMPTY);
	}

	public void encodeExtraData(PacketBuffer buffer) {
		buffer.writeByte(fields.getCount());
	}

	@Override
	public void tick() {
		if (this.level == null || this.level.isClientSide) return;

		DefossilizingRecipe recipe = getRecipe();
		if (recipe != null) doWork(recipe);
		else stopWork();
	}

	@Nullable
	public DefossilizingRecipe getRecipe() {
		if (this.level == null || getItem(0).isEmpty() || getItem(1).isEmpty() || getItem(2).isEmpty()) return null;
		return level.getRecipeManager().getRecipeFor(CARecipeTypes.DEFOSSILIZING_RECIPE_TYPE, this, level).orElse(null);
	}

	private ItemStack getWorkOutput(@Nullable DefossilizingRecipe recipe) {
		if (recipe != null) return recipe.assemble(this);
		return ItemStack.EMPTY;
	}

	private void doWork(DefossilizingRecipe recipe) {
		if (!(recipe.getDefossilizerType().equals("iron") || recipe.getDefossilizerType().equals("crystal"))) return;
		assert this.level != null;

		ItemStack current = getItem(3);
		ItemStack output = getWorkOutput(recipe);

		if (!current.isEmpty()) {
			int newCount = current.getCount() + output.getCount();

			if (!ItemStack.isSame(current, output) || newCount > output.getMaxStackSize()) {
				stopWork();
				return;
			}
		}

		if (progress < WORK_TIME) ++progress;
		if (progress >= WORK_TIME && !level.isClientSide) finishWork(recipe, current);
	}

	private void stopWork() {
		progress = 0;
	}

	private void finishWork(DefossilizingRecipe recipe, ItemStack current) {
		ItemStack output = getWorkOutput(recipe);
		if (!current.isEmpty()) current.grow(output.getCount());
		else setItem(3, output);

		this.progress = 0;
		
		removeItem(0, 1);
		setItem(1, (getItem(1).getItem() instanceof PinkTourmalineBucketItem) ? CAItems.PINK_TOURMALINE_BUCKET.get().getDefaultInstance() : Items.BUCKET.getDefaultInstance());
		removeItem(2, 1);
	}

	@Override
	public int[] getSlotsForFace(Direction direction) {
		if (direction == Direction.DOWN) return SLOTS_FOR_DOWN;
		else if (direction == Direction.UP) return SLOTS_FOR_UP;
		else if (direction == Direction.EAST || direction == Direction.NORTH) return SLOTS_FOR_BUCKET;
		else return SLOTS_FOR_CHIP;
	}
	
	@Override
	public boolean canPlaceItem(int pIndex, ItemStack pStack) {
		if (pIndex == 0 && pStack.getItem() instanceof BlockItem) return true;
		else if(pIndex == 1 && pStack.getItem() instanceof BucketItem) return true;
		else if(pIndex == 2 && pStack.getItem() instanceof PowerChipItem) return true;
		return false;
	}

	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
		return this.canPlaceItem(index, stack);
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
		if(direction == Direction.DOWN) {
			if(index == 3 || (index == 1 && stack.getItem() == CAItems.PINK_TOURMALINE_BUCKET.get())) return true;
		}
		return false;
	}

	@Override
	protected ITextComponent getDefaultName() {
		return CONTAINER_NAME;
	}

	@Override
	protected Container createMenu(int id, PlayerInventory playerInventory) {
		return new CrystalDefossilizerContainer(id, playerInventory, this, this.fields);
	}

	@Override
	public int getContainerSize() {
		return 4;
	}

	@Override
	public boolean isEmpty() {
		return this.items.stream().allMatch(ItemStack::isEmpty);
	}

	@Override
	public ItemStack getItem(int index) {
		return items.get(index);
	}

	@Override
	public ItemStack removeItem(int index, int count) {
		return ItemStackHelper.removeItem(items, index, count);
	}

	@Override
	public ItemStack removeItemNoUpdate(int index) {
		return ItemStackHelper.takeItem(items, index);
	}

	@Override
	public void setItem(int index, ItemStack stack) {
		items.set(index, stack);
	}

	@Override
	public boolean stillValid(PlayerEntity player) {
		return this.level != null && this.level.getBlockEntity(this.worldPosition) == this && player.distanceToSqr(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ()) <= 64;
	}

	@Override
	public void clearContent() {
		items.clear();
	}

	@Override
	public void load(BlockState state, CompoundNBT tags) {
		super.load(state, tags);
		this.items = NonNullList.withSize(4, ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(tags, this.items);

		this.progress = tags.getInt("Progress");
	}

	@Override
	public CompoundNBT save(CompoundNBT tags) {
		super.save(tags);
		ItemStackHelper.saveAllItems(tags, this.items);
		tags.putInt("Progress", this.progress);
		return tags;
	}

	@Nullable
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT tags = this.getUpdateTag();
		ItemStackHelper.saveAllItems(tags, this.items);
		return new SUpdateTileEntityPacket(this.worldPosition, 1, tags);
	}

	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT tags = super.getUpdateTag();
		tags.putInt("Progress", this.progress);
		return tags;
	}

	@Nullable
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		if (!this.remove && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == Direction.UP) return handlers[0].cast();
			else if (facing == Direction.DOWN) return handlers[1].cast();
			else return handlers[2].cast();
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void setRemoved() {
		super.setRemoved();

		for (LazyOptional<? extends IItemHandler> handler : this.handlers) handler.invalidate();
	}

	@Override
	public int getSlots() {
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return null;
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		return null;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return null;
	}

	@Override
	public int getSlotLimit(int slot) {
		return slot;
	}

	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		switch (slot) {
			case 0:
				if (this.getStackInSlot(0) != null && this.getStackInSlot(0) != this.getStackInSlot(slot)) return false;
				return handlers[0].cast() != null;
			case 1:
				if (this.getStackInSlot(1) != null && this.getStackInSlot(1) != this.getStackInSlot(slot)) return false;
				return handlers[1].cast() != null;
			case 2:
				if (this.getStackInSlot(2) != null && this.getStackInSlot(2) != this.getStackInSlot(slot)) return false;
				return handlers[2].cast() != null;
			case 3:
				if (this.getStackInSlot(3) != null && this.getStackInSlot(3) != this.getStackInSlot(slot)) return false;
				return handlers[3].cast() != null;
		}
		return false;
	}
}
