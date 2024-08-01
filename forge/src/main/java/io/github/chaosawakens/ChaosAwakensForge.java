package io.github.chaosawakens;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(CAConstants.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChaosAwakensForge {
    
    public ChaosAwakensForge() {
        ChaosAwakens.setup();
    }

    @SubscribeEvent
    public static void onFMLCommonSetupEvent(FMLCommonSetupEvent event) {
        ChaosAwakens.deferredSetup();
    }
}