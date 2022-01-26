package io.github.chaosawakens.common.blocks.tileentities;

import com.google.common.collect.Maps;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CATileEntities;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.FurnaceContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Map;

public class CrystalFurnaceTileEntity extends AbstractFurnaceTileEntity {
    public CrystalFurnaceTileEntity() {
        super(CATileEntities.CRYSTAL_FURNACE.get(), IRecipeType.SMELTING);
    }

    public static Map<Item, Integer> getBurnTimes() {
        Map<Item, Integer> map = Maps.newLinkedHashMap();
        addItemBurnTime(map, CABlocks.CRYSTAL_ENERGY.get(), 2200);
        return map;
    }

    private static void addItemBurnTime(Map<Item, Integer> map, IItemProvider itemProvider, int burnTimeIn) {
        Item item = itemProvider.asItem();
        map.put(item, burnTimeIn);
    }

    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + ChaosAwakens.MODID + ".crystal_furnace");
    }

    protected int getBurnDuration(ItemStack fuel) {
        return super.getBurnDuration(fuel) / 2 - 5;
    }

    protected Container createMenu(int id, PlayerInventory player) {
        return new FurnaceContainer(id, player, this, this.dataAccess);
    }
}
