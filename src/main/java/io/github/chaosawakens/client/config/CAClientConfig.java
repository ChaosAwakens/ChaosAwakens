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
		public final ConfigValue<Boolean> enableDamageTooltips;
		public final ConfigValue<Boolean> enableCritterCageTooltips;
		public final ConfigValue<Boolean> enableCritterCageMobIDToolTip;
		public final ConfigValue<Boolean> enableCritterCageMobNameToolTip;
		public final ConfigValue<Boolean> enableCritterCageMobHealthToolTip;
		public final ConfigValue<Boolean> enableCritterCageVillagerProfessionToolTip;
		public final ConfigValue<Boolean> enableCritterCageVillagerTradingLevelToolTip;
		public final ConfigValue<Boolean> enableCritterCageMobOwnerToolTip;
		public final ConfigValue<Boolean> enableCritterCageMobCollarColorToolTip;
		public final ConfigValue<TextFormatting> toolTipColor;
		public final ConfigValue<Boolean> enableCrystalDimensionFog;
		public final ConfigValue<Float> crystalDimensionFogDensity;
		public final ConfigValue<Boolean> enableCrystalDimensionParticles;
		public final ConfigValue<Integer> crystalDimensionParticleDensity;
		
		public final ConfigValue<Float> lavaEelSetLavaFogDensity;
		public final ConfigValue<Double> lavaEelSetFireStackTranslation;
		
		public final ConfigValue<Boolean> enableCameraShake;
		
		public Client(ForgeConfigSpec.Builder builder) {
			builder.push("ToolTips");
			enableTooltips = builder.define("Enable tooltips", true);
			toolTipColor = builder.comment("(ToolTip color applies to both default tooltip description text and when shift-clicking) \n"
					+ "Colors include: \n"
					+ "AQUA, BLACK, DARK_AQUA, DARK_BLUE, DARK_GRAY, DARK_GREEN, DARK_PURPLE, DARK_RED, \n"
					+ "GREEN, GOLD, LIGHT_PURPLE, RED, WHITE, YELLOW").define("ToolTip Color", TextFormatting.AQUA);
			enableDamageTooltips = builder.define("Enable damage tooltips", true);
			builder.push("Critter Cage");
			enableCritterCageTooltips = builder.define("Display mob info on a filled critter cage", true);
			enableCritterCageMobIDToolTip = builder.define("Display the entity's registry name, alongside its modid", true);
			enableCritterCageMobNameToolTip = builder.define("Display the entity's display name", true);
			enableCritterCageMobHealthToolTip = builder.define("Display the entity's health", true);
			enableCritterCageVillagerProfessionToolTip = builder.define("Display the villager's profession", true);
			enableCritterCageVillagerTradingLevelToolTip = builder.define("Display the villager's trading level", true);
			enableCritterCageMobOwnerToolTip = builder.define("Display the tamed entity's owner's name", true);
			enableCritterCageMobCollarColorToolTip = builder.define("Display the tamed entity's collar color", true);
			builder.pop();
			builder.pop();
			builder.push("Dimensions");
			builder.push("Crystal Dimension");
			enableCrystalDimensionFog = builder.define("Enable fog in the Crystal Dimension", true);
			enableCrystalDimensionParticles = builder.define("Enable particles in the Crystal Dimension", true);
			crystalDimensionFogDensity = builder.define("Crystal Dimension fog density", 0.05F);
			crystalDimensionParticleDensity = builder.define("Crystal Dimension particle density", 2);
			builder.pop();
			builder.pop();
			builder.push("Functionality");
			builder.push("Camera");
			enableCameraShake = builder.define("Enable camera shake", true);
			builder.pop();
			builder.push("Armor");
			builder.push("Lava Eel Armor");
			lavaEelSetLavaFogDensity = builder
					.comment("A static fog value for when you have the full Lava Eel set equipped and you're inside lava.")
					.define("Lava fog density", 0.02F);
			lavaEelSetFireStackTranslation = builder
					.comment("The position of fire on your screen when you're inside lava. 0 is the default. Positive movs it up, and negative moves it down.")
					.define("Fire (y) Position on screen", -0.50);
			builder.pop();
			builder.pop();
			builder.pop();
		}
	}
}
