package io.github.chaosawakens.content.client.menu.defossilizer;

import io.github.chaosawakens.content.registry.CAMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;

public class CrystalDefossilizerMenu extends AbstractDefossilizerMenu {

    public CrystalDefossilizerMenu(MenuType<?> menuType, int containerId, Inventory playerInventory, Container container, ContainerData data) {
        super(menuType, containerId, playerInventory, container, data);
    }

    public CrystalDefossilizerMenu(int containerId, Inventory playerInventory) {
        super(CAMenuTypes.CRYSTAL_DEFOSSILIZER.get(), containerId, playerInventory);
    }
}
