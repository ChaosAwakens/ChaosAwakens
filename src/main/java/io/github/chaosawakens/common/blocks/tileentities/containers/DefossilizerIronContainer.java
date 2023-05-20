package io.github.chaosawakens.common.blocks.tileentities.containers;

import io.github.chaosawakens.common.blocks.tileentities.DefossilizerIronTileEntity;
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

public class DefossilizerIronContainer extends Container {
	private final IInventory inventory;
	private IIntArray fields;
	protected final World level;
	private final Slot fossilSlot;
	private final Slot bucketSlot;
	private final Slot powerchipSlot;
	private final Slot resultSlot;

	public DefossilizerIronContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		this(id, playerInventory, new DefossilizerIronTileEntity(), new IntArray(buffer.readByte()));
	}

	public DefossilizerIronContainer(int id, PlayerInventory playerInventory, IInventory inventory, IIntArray fields) {
		super(CAContainerTypes.IRON_DEFOSSILIZER.get(), id);
		this.level = playerInventory.player.level;
		this.inventory = inventory;
		this.fields = fields;

		this.fossilSlot = this.addSlot(new Slot(this.inventory, 0, 56, 17));
		this.bucketSlot = this.addSlot(new Slot(this.inventory, 1, 47, 53) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof BucketItem;
			}
		});
		this.powerchipSlot = this.addSlot(new Slot(this.inventory, 2, 65, 53) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof PowerChipItem;
			}
		});
		this.resultSlot = this.addSlot(new Slot(this.inventory, 3, 116, 35) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}
		});

		// Player backpack
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		// Player hotbar
		for (int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
		}

		this.addDataSlots(fields);
	}

	public int getProgressArrowScale() {
		int progress = fields.get(0);
		return progress != 0 ? progress * 24 / DefossilizerIronTileEntity.WORK_TIME : 0;
	}

	public int getLitProgress() {
		int progress = fields.get(0);
		return progress != 0 ? progress * 24 / DefossilizerIronTileEntity.WORK_TIME : 0;
	}

	@Override
	public boolean stillValid(PlayerEntity player) {
		return inventory.stillValid(player);
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (index == this.resultSlot.index) {
				if (!this.moveItemStackTo(itemstack1, 4, 40, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(itemstack1, itemstack);
			} else if (index != this.bucketSlot.index && index != this.fossilSlot.index && index != this.powerchipSlot.index) {
				if (!(itemstack1.getItem() instanceof BucketItem) && !(itemstack1.getItem() instanceof PowerChipItem)) {
					if (!this.moveItemStackTo(itemstack1, this.fossilSlot.index, this.fossilSlot.index + 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (itemstack1.getItem() instanceof BucketItem) {
					if (!this.moveItemStackTo(itemstack1, this.bucketSlot.index, this.bucketSlot.index + 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (itemstack1.getItem() instanceof PowerChipItem) {
					if (!this.moveItemStackTo(itemstack1, this.powerchipSlot.index, this.powerchipSlot.index + 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 4 && index < 31) {
					if (!this.moveItemStackTo(itemstack1, 31, 40, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 31 && index < 40 && !this.moveItemStackTo(itemstack1, 4, 31, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 4, 40, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, itemstack1);
		}

		return itemstack;
	}
}
