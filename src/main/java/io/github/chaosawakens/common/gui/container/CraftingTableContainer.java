package io.github.chaosawakens.common.gui.container;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;
import java.util.Optional;

public class CraftingTableContainer extends RecipeBookContainer<CraftingInventory> {

	private final CraftingInventory craftMatrix = new CraftingInventory(this, 3, 3);
	private final CraftResultInventory craftResult = new CraftResultInventory();
	private final IWorldPosCallable worldPosCallable;
	private final PlayerEntity player;
	private final Block craftingTable;

	public CraftingTableContainer(int id, PlayerInventory playerInventory, Block craftingTable) {
		this(id, playerInventory, IWorldPosCallable.NULL, craftingTable);
	}

	public CraftingTableContainer(int id, PlayerInventory playerInventory, IWorldPosCallable p_i50090_3_, Block craftingTable) {
		super(ContainerType.CRAFTING, id);
		this.worldPosCallable = p_i50090_3_;
		this.player = playerInventory.player;
		this.craftingTable = craftingTable;
		this.addSlot(new CraftingResultSlot(playerInventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));

		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j)
				this.addSlot(new Slot(this.craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));

		for (int k = 0; k < 3; ++k)
			for (int i1 = 0; i1 < 9; ++i1)
				this.addSlot(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));

		for (int l = 0; l < 9; ++l)
			this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 142));
	}

	protected static void updateCraftingResult(int id, World world, PlayerEntity player, CraftingInventory inventory,
			CraftResultInventory inventoryResult) {
		if (!world.isClientSide) {
			ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) player;
			ItemStack itemstack = ItemStack.EMPTY;
			Optional<ICraftingRecipe> optional = Objects.requireNonNull(world.getServer()).getRecipeManager().getRecipeFor(IRecipeType.CRAFTING, inventory, world);
			if (optional.isPresent()) {
				ICraftingRecipe icraftingrecipe = optional.get();
				if (inventoryResult.setRecipeUsed(world, serverplayerentity, icraftingrecipe)) itemstack = icraftingrecipe.assemble(inventory);
			}

			inventoryResult.setItem(0, itemstack);
			serverplayerentity.connection.send(new SSetSlotPacket(id, 0, itemstack));
		}
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	public void slotsChanged(IInventory inventoryIn) {
		this.worldPosCallable.execute((p_217069_1_, p_217069_2_) -> updateCraftingResult(this.containerId, p_217069_1_, this.player, this.craftMatrix, this.craftResult));
	}

	public void fillCraftSlotsStackedContents(RecipeItemHelper itemHelperIn) {
		this.craftMatrix.fillStackedContents(itemHelperIn);
	}

	public void clearCraftingContent() {
		this.craftMatrix.clearContent();
		this.craftResult.clearContent();
	}

	public boolean recipeMatches(IRecipe<? super CraftingInventory> recipeIn) {
		return recipeIn.matches(this.craftMatrix, this.player.level);
	}

	/**
	 * Called when the container is closed.
	 */
	public void removed(PlayerEntity playerIn) {
		super.removed(playerIn);
		this.worldPosCallable.execute((p_217068_2_, p_217068_3_) -> this.clearContainer(playerIn, p_217068_2_, this.craftMatrix));
	}

	/**
	 * Determines whether supplied player can use this container
	 */
	public boolean stillValid(PlayerEntity playerIn) {
		return stillValid(this.worldPosCallable, playerIn, craftingTable);
	}

	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this
	 * moves the stack between the player inventory and the other inventory(s).
	 */
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (index == 0) {
				this.worldPosCallable.execute((p_217067_2_, p_217067_3_) -> itemstack1.getItem().onCraftedBy(itemstack1, p_217067_2_, playerIn));
				if (!this.moveItemStackTo(itemstack1, 10, 46, true)) return ItemStack.EMPTY;
				slot.onQuickCraft(itemstack1, itemstack);
			} else if (index >= 10 && index < 46) {
				if (!this.moveItemStackTo(itemstack1, 1, 10, false)) {
					if (index < 37) if (!this.moveItemStackTo(itemstack1, 37, 46, false)) return ItemStack.EMPTY;
					else if (!this.moveItemStackTo(itemstack1, 10, 37, false)) return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 10, 46, false)) return ItemStack.EMPTY;

			if (itemstack1.isEmpty()) slot.set(ItemStack.EMPTY);
			else slot.setChanged();

			if (itemstack1.getCount() == itemstack.getCount()) return ItemStack.EMPTY;

			ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);
			if (index == 0) playerIn.drop(itemstack2, false);
		}
		return itemstack;
	}

	/**
	 * Called to determine if the current slot is valid for the stack merging
	 * (double-click) code. The stack passed in is null for the initial slot that
	 * was double-clicked.
	 */
	public boolean canTakeItemForPickAll(ItemStack stack, Slot slotIn) {
		return slotIn.container != this.craftResult && super.canTakeItemForPickAll(stack, slotIn);
	}

	public int getResultSlotIndex() {
		return 0;
	}

	public int getGridWidth() {
		return this.craftMatrix.getWidth();
	}

	public int getGridHeight() {
		return this.craftMatrix.getHeight();
	}

	@OnlyIn(Dist.CLIENT)
	public int getSize() {
		return 10;
	}

	@OnlyIn(Dist.CLIENT)
	public RecipeBookCategory getRecipeBookType() {
		return RecipeBookCategory.CRAFTING;
	}
}
