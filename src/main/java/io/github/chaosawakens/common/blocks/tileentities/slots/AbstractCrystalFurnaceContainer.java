package io.github.chaosawakens.common.blocks.tileentities.slots;

import io.github.chaosawakens.common.registry.CATags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.item.crafting.ServerRecipePlacerFurnace;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;

public abstract class AbstractCrystalFurnaceContainer extends RecipeBookContainer<IInventory> {
	private final IInventory container;
	private final IIntArray data;
	protected final World level;
	private final IRecipeType<? extends AbstractCookingRecipe> recipeType;
	private final RecipeBookCategory recipeBookType;

	protected AbstractCrystalFurnaceContainer(ContainerType<?> containerType, IRecipeType<? extends AbstractCookingRecipe> recipeType, RecipeBookCategory recipeBookCategory, int id, PlayerInventory targetPlayerInv) {
		this(containerType, recipeType, recipeBookCategory, id, targetPlayerInv, new Inventory(3), new IntArray(4));
	}

	protected AbstractCrystalFurnaceContainer(ContainerType<?> containerType, IRecipeType<? extends AbstractCookingRecipe> recipeType, RecipeBookCategory recipeBookCategory, int id, PlayerInventory targetPlayerInv, IInventory inv, IIntArray data) {
		super(containerType, id);
		this.recipeType = recipeType;
		this.recipeBookType = recipeBookCategory;
		checkContainerSize(inv, 3);
		checkContainerDataCount(data, 4);
		this.container = inv;
		this.data = data;
		this.level = targetPlayerInv.player.level;
		this.addSlot(new Slot(inv, 0, 56, 17));
		this.addSlot(new CrystalFurnaceFuelSlot(inv, 1, 56, 53));
		this.addSlot(new FurnaceResultSlot(targetPlayerInv.player, inv, 2, 116, 35));

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlot(new Slot(targetPlayerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			addSlot(new Slot(targetPlayerInv, k, 8 + k * 18, 142));
		}

		addDataSlots(data);
	}

	@Override
	public void fillCraftSlotsStackedContents(RecipeItemHelper recipeHelper) {
		if (this.container instanceof IRecipeHelperPopulator) ((IRecipeHelperPopulator) this.container).fillStackedContents(recipeHelper);
	}

	@Override
	public void clearCraftingContent() {
		this.container.clearContent();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handlePlacement(boolean shouldPlaceAll, IRecipe<?> recipe, ServerPlayerEntity interactingPlayer) {
		(new ServerRecipePlacerFurnace<>(this)).recipeClicked(interactingPlayer, (IRecipe<IInventory>) recipe, shouldPlaceAll);
	}

	@Override
	public boolean recipeMatches(IRecipe<? super IInventory> recipe) {
		return recipe.matches(this.container, this.level);
	}

	@Override
	public int getResultSlotIndex() {
		return 2;
	}

	@Override
	public int getGridWidth() {
		return 1;
	}

	@Override
	public int getGridHeight() {
		return 1;
	}

	@Override
	public int getSize() {
		return 3;
	}

	@Override
	public boolean stillValid(PlayerEntity interactingPlayer) {
		return this.container.stillValid(interactingPlayer);
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity interactingPlayer, int slotIndex) {
		ItemStack emptyStack = ItemStack.EMPTY;
		Slot targetSlot = this.slots.get(slotIndex);
		
		if (targetSlot != null && targetSlot.hasItem()) {
			ItemStack targetStack = targetSlot.getItem();
			emptyStack = targetStack.copy();
			
			if (slotIndex == 2) {
				if (!moveItemStackTo(targetStack, 3, 39, true)) return ItemStack.EMPTY;
				
				targetSlot.onQuickCraft(targetStack, emptyStack);
			} else if (slotIndex != 1 && slotIndex != 0) {
				if (canSmelt(targetStack)) {
					if (!moveItemStackTo(targetStack, 0, 1, false)) return ItemStack.EMPTY;
				} else if (isFuel(targetStack)) {
					if (!moveItemStackTo(targetStack, 1, 2, false)) return ItemStack.EMPTY;
				} else if (slotIndex >= 3 && slotIndex < 30) {
					if (!moveItemStackTo(targetStack, 30, 39, false)) return ItemStack.EMPTY;
				} else if (slotIndex >= 30 && slotIndex < 39 && !moveItemStackTo(targetStack, 3, 30, false)) return ItemStack.EMPTY;
			} else if (!moveItemStackTo(targetStack, 3, 39, false)) return ItemStack.EMPTY;

			if (targetStack.isEmpty()) targetSlot.set(ItemStack.EMPTY);
			else targetSlot.setChanged();

			if (targetStack.getCount() == emptyStack.getCount()) return ItemStack.EMPTY;

			targetSlot.onTake(interactingPlayer, targetStack);
		}

		return emptyStack;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected boolean canSmelt(ItemStack targetStack) {
		return this.level.getRecipeManager().getRecipeFor((IRecipeType) this.recipeType, new Inventory(targetStack), this.level).isPresent();
	}

	protected boolean isFuel(ItemStack stack) {
		return stack.getItem().is(CATags.Items.CRYSTAL_FURNACE_FUEL);
	}

	public int getBurnProgress() {
		int burnProgress = this.data.get(2);
		int smeltProgress = this.data.get(3);
		return smeltProgress != 0 && burnProgress != 0 ? burnProgress * 24 / smeltProgress : 0;
	}

	public int getLitProgress() {
		int litProg = this.data.get(1);
		
		if (litProg == 0) litProg = 200;

		return this.data.get(0) * 13 / litProg;
	}

	public boolean isLit() {
		return this.data.get(0) > 0;
	}

	@Override
	public RecipeBookCategory getRecipeBookType() {
		return this.recipeBookType;
	}
}
