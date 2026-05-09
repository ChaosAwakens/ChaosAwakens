package io.github.chaosawakens;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class CAConstants {
    public static final String MOD_ID = "chaosawakens";
    public static final String MOD_NAME = "Chaos Awakens";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static ResourceLocation prefix(String targetPath) {
        return new ResourceLocation(MOD_ID, targetPath.toLowerCase(Locale.ROOT));
    }
}