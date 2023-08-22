package io.github.chaosawakens.common.blocks.tileentities.containers;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.util.IIntArray;

public class CrystalFurnaceContainer extends AbstractCrystalFurnaceContainer {
	
	public CrystalFurnaceContainer(int id, PlayerInventory inventory) {
		super(ContainerType.FURNACE, IRecipeType.SMELTING, RecipeBookCategory.FURNACE, id, inventory);
	}

	public CrystalFurnaceContainer(int id, PlayerInventory inventory, IInventory inv, IIntArray data) {
		super(ContainerType.FURNACE, IRecipeType.SMELTING, RecipeBookCategory.FURNACE, id, inventory, inv, data);
	}
}
