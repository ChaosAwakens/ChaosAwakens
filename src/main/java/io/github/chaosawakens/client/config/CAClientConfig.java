package io.github.chaosawakens.client.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class CAClientConfig {
	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;

	static {
		final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = clientSpecPair.getRight();
		CLIENT = clientSpecPair.getLeft();
	}
	
	public static class Client {
		public final ConfigValue<Boolean> enableTooltips;
		public final ConfigValue<TextFormatting> toolTipColor;
		public final ConfigValue<Boolean> enableCrystalDimensionFog;
		public final ConfigValue<Float> crystalDimensionFogDensity;
		public final ConfigValue<Boolean> enableCrystalDimensionParticles;
		public final ConfigValue<Integer> crystalDimensionParticleDensity;
		
		public Client(ForgeConfigSpec.Builder builder) {
			builder.push("ToolTips");
			enableTooltips = builder.comment().define("Enable tooltips", true);
			toolTipColor = builder.comment("(ToolTip color applies to both default tooltip description text and when shift-clicking) \n"
					+ "Colors include: \n"
					+ "AQUA, BLACK, DARK_AQUA, DARK_BLUE, DARK_GRAY, DARK_GREEN, DARK_PURPLE, DARK_RED, \n"
					+ "GREEN, GOLD, LIGHT_PURPLE, RED, WHITE, YELLOW").define("ToolTip Color", TextFormatting.AQUA);
			builder.pop();
			builder.push("Dimensions");
			builder.push("Crystal Dimension");
			enableCrystalDimensionFog = builder.define("Enable fog in the Crystal Dimension", true);
			enableCrystalDimensionParticles = builder.define("Enable particles in the Crystal Dimension", true);
			crystalDimensionFogDensity = builder.define("Crystal Dimension fog density", 0.05F);
			crystalDimensionParticleDensity = builder.define("Crystal Dimension particle density", 2);
			builder.pop();
			builder.pop();
			
		}
	}
}
