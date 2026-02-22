package io.github.chaosawakens.content.registry;

import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.platform.NexusServices;
import io.github.chaosawakens.content.client.screen.defossilizer.IronDefossilizerScreen;
import net.minecraft.client.gui.screens.MenuScreens;

@RegistrarEntry(priority = -1)
public final class CAMenuScreens {

    static {
        if (NexusServices.PLATFORM_MANAGER.getEnvironmentSide().isClient()) {
            MenuScreens.register(CAMenuTypes.IRON_DEFOSSILIZER.get(), IronDefossilizerScreen::new);
        }
    }
}
