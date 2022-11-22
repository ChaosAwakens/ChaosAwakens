package io.github.chaosawakens.common.blocks.tileentities;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.util.IIntArray;

public class CrystalFurnaceContainer extends AbstractCrystalFurnaceContainer {
	public CrystalFurnaceContainer(int p_i50082_1_, PlayerInventory inventory) {
		super(ContainerType.FURNACE, IRecipeType.SMELTING, RecipeBookCategory.FURNACE, p_i50082_1_, inventory);
	}

	public CrystalFurnaceContainer(int p_i50083_1_, PlayerInventory inventory, IInventory inv, IIntArray p_i50083_4_) {
		super(ContainerType.FURNACE, IRecipeType.SMELTING, RecipeBookCategory.FURNACE, p_i50083_1_, inventory, inv, p_i50083_4_);
	}
	
	
	
}
