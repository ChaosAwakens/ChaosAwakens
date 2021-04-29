package io.github.chaosawakens;

import net.fabricmc.api.ModInitializer;
import software.bernie.geckolib3.GeckoLib;

public class ChaosAwakens implements ModInitializer {
    public static final String modId = "chaosawakens";
    @Override
    public void onInitialize() {
        GeckoLib.initialize();
    }
}
