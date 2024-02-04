package io.github.chaosawakens.common.blocks.tileentities.containers;

import io.github.chaosawakens.common.blocks.tileentities.CrystalDefossilizerTileEntity;
import io.github.chaosawakens.common.items.PowerChipItem;
import io.github.chaosawakens.common.registry.CAContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;

public class CrystalDefossilizerContainer extends Container {
	private final IInventory inventory;
	private IIntArray fields;
	protected final World level;
	private final Slot fossilSlot;
	private final Slot bucketSlot;
	private final Slot powerchipSlot;
	private final Slot resultSlot;

	public CrystalDefossilizerContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		this(id, playerInventory, new CrystalDefossilizerTileEntity(), new IntArray(buffer.readByte()));
	}

	public CrystalDefossilizerContainer(int id, PlayerInventory playerInventory, IInventory inventory, IIntArray fields) {
		super(CAContainerTypes.CRYSTAL_DEFOSSILIZER.get(), id);
		this.level = playerInventory.player.level;
		this.inventory = inventory;
		this.fields = fields;

		this.fossilSlot = addSlot(new Slot(inventory, 0, 56, 17));
		this.bucketSlot = addSlot(new Slot(inventory, 1, 47, 53) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof BucketItem;
			}
		});
		this.powerchipSlot = addSlot(new Slot(inventory, 2, 65, 53) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof PowerChipItem;
			}
		});
		this.resultSlot = addSlot(new Slot(inventory, 3, 116, 35) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}
		});

		// Player backpack
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
		}

		// Player hotbar
		for (int k = 0; k < 9; ++k) {
			addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
		}

		addDataSlots(fields);
	}

	public int getProgressArrowScale() {
		int progress = fields.get(0);
		return progress != 0 ? progress * 24 / CrystalDefossilizerTileEntity.WORK_TIME : 0;
	}

	public int getLitProgress() {
		int progress = fields.get(0);
		return progress != 0 ? progress * 24 / CrystalDefossilizerTileEntity.WORK_TIME : 0;
	}

	@Override
	public boolean stillValid(PlayerEntity player) {
		return inventory.stillValid(player);
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity player, int index) {
		ItemStack curStack = ItemStack.EMPTY;
		Slot curSlot = slots.get(index);
		
		if (curSlot != null && curSlot.hasItem()) {
			ItemStack curFilledStack = curSlot.getItem();
			curStack = curFilledStack.copy();
			
			if (index == resultSlot.index) {
				if (!moveItemStackTo(curFilledStack, 4, 40, true)) return ItemStack.EMPTY;

				curSlot.onQuickCraft(curFilledStack, curStack);
			} else if (index != bucketSlot.index && index != fossilSlot.index && index != powerchipSlot.index) {
				if (!(curFilledStack.getItem() instanceof BucketItem) && !(curFilledStack.getItem() instanceof PowerChipItem)) {
					if (!moveItemStackTo(curFilledStack, fossilSlot.index, fossilSlot.index + 1, false)) return ItemStack.EMPTY;
				} else if (curFilledStack.getItem() instanceof BucketItem) {
					if (!moveItemStackTo(curFilledStack, bucketSlot.index, bucketSlot.index + 1, false)) return ItemStack.EMPTY;
				} else if (curFilledStack.getItem() instanceof PowerChipItem) {
					if (!moveItemStackTo(curFilledStack, powerchipSlot.index, powerchipSlot.index + 1, false)) return ItemStack.EMPTY;
				} else if (index >= 4 && index < 31) {
					if (!moveItemStackTo(curFilledStack, 31, 40, false)) return ItemStack.EMPTY;
				} else if (index >= 31 && index < 40 && !moveItemStackTo(curFilledStack, 4, 31, false)) return ItemStack.EMPTY;
			} else if (!moveItemStackTo(curFilledStack, 4, 40, false)) return ItemStack.EMPTY;

			if (curFilledStack.isEmpty()) curSlot.set(ItemStack.EMPTY);
			else curSlot.setChanged();

			if (curFilledStack.getCount() == curStack.getCount()) return ItemStack.EMPTY;

			curSlot.onTake(player, curFilledStack);
		}

		return curStack;
	}
}
