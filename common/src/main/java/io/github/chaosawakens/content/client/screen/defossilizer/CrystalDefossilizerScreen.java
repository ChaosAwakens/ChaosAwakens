package io.github.chaosawakens.content.client.screen.defossilizer;

import io.github.chaosawakens.content.client.component.defossilizer.CrystalDefossilizerRecipeBookComponent;
import io.github.chaosawakens.content.client.menu.defossilizer.CrystalDefossilizerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class CrystalDefossilizerScreen extends AbstractDefossilizerScreen<CrystalDefossilizerMenu> {
    public static final Component TITLE = Component.translatable("container.chaosawakens.crystal_defossilizer");

    public CrystalDefossilizerScreen(CrystalDefossilizerMenu menu, Inventory playerInventory, Component title) {
        super(menu, new CrystalDefossilizerRecipeBookComponent(), playerInventory, title, DEFAULT_GUI_TEXTURE);
    }
}
