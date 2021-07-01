package io.github.chaosawakens;

import io.github.chaosawakens.config.Config;
import io.github.chaosawakens.entities.Entities;
import io.github.chaosawakens.entities.entities.ent.EntEntityRenderer;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import software.bernie.geckolib3.GeckoLib;

public class ChaosAwakens implements ModInitializer, ClientModInitializer {
        public static final String modID = "chaosawakens";
        public static Config config;

        @Environment(EnvType.CLIENT)
        @Override
        public void onInitializeClient() {
                EntityRendererRegistry.INSTANCE.register(Entities.ENT, EntEntityRenderer::new);
        }

        @Override
        public void onInitialize() {
                GeckoLib.initialize();
                AutoConfig.register(Config.class, JanksonConfigSerializer::new);
                config = AutoConfig.getConfigHolder(Config.class).getConfig();
        }
}
