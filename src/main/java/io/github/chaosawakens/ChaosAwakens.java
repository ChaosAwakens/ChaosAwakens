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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

public class ChaosAwakens implements ModInitializer, ClientModInitializer {
        public static final String modID = "chaosawakens";
        public static Config config;

        public static final Logger LOGGER = LogManager.getLogger();

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

        public static <D> void debug(String domain, D message) {
                LOGGER.debug("[" + domain + "]: " + (message == null ? "null" : message.toString()));
        }
        public static <I> void info(String domain, I message) {
                LOGGER.info("[" + domain + "]: " + (message == null ? "null" : message.toString()));
        }

        public static <W> void warn(String domain, W message) {
                LOGGER.warn("[" + domain + "]: " + (message == null ? "null" : message.toString()));
        }

        public static <E> void error(String domain, E message) {
                LOGGER.error("[" + domain + "]: " + (message == null ? "null" : message.toString()));
        }

}
