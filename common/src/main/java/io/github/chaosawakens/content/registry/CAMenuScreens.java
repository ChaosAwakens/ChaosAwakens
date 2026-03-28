package io.github.chaosawakens.content.registry;

import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.loader.ModSide;
import io.github.chaosawakens.content.client.screen.defossilizer.CrystalDefossilizerScreen;
import io.github.chaosawakens.content.client.screen.defossilizer.IronDefossilizerScreen;
import net.minecraft.client.gui.screens.MenuScreens;

@RegistrarEntry(priority = -1, initSide = ModSide.CLIENT)
public final class CAMenuScreens {

    static {
        MenuScreens.register(CAMenuTypes.IRON_DEFOSSILIZER.get(), IronDefossilizerScreen::new);
        MenuScreens.register(CAMenuTypes.CRYSTAL_DEFOSSILIZER.get(), CrystalDefossilizerScreen::new);
    }
}
