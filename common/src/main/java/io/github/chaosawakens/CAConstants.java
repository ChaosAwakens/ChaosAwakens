package io.github.chaosawakens;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class CAConstants {
	public static final String MODID = "chaosawakens";
	public static final String MOD_NAME = "Chaos Awakens";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	public static ResourceLocation prefix(String path) {
		return new ResourceLocation(MODID, path.toLowerCase(Locale.ROOT));
	}
}