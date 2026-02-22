package io.github.chaosawakens.content.client.screen.defossilizer;

import io.github.chaosawakens.content.client.component.defossilizer.IronDefossilizerRecipeBookComponent;
import io.github.chaosawakens.content.client.menu.defossilizer.IronDefossilizerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class IronDefossilizerScreen extends AbstractDefossilizerScreen<IronDefossilizerMenu> {
    public static final Component TITLE = Component.translatable("container.chaosawakens.iron_defossilizer");

    public IronDefossilizerScreen(IronDefossilizerMenu menu, Inventory playerInventory, Component title) {
        super(menu, new IronDefossilizerRecipeBookComponent(), playerInventory, title, DEFAULT_GUI_TEXTURE);
    }
}
