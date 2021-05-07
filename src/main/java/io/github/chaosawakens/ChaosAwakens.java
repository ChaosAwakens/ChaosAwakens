package io.github.chaosawakens;

import io.github.chaosawakens.config.Config;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import software.bernie.geckolib3.GeckoLib;

public class ChaosAwakens implements ModInitializer {
    public static final String modId = "chaosawakens";
    @Override
    public void onInitialize() {
        GeckoLib.initialize();
        AutoConfig.register(Config.class, JanksonConfigSerializer::new);
    }
}
