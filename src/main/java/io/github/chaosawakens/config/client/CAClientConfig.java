package io.github.chaosawakens.config.client;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class CAClientConfig {	
	public final BooleanValue enableTooltips;
	public final BooleanValue enableDamageTooltips;
	public final BooleanValue enableCritterCageTooltips;
	public final BooleanValue enableCritterCageMobIDToolTip;
	public final BooleanValue enableCritterCageMobNameToolTip;
	public final BooleanValue enableCritterCageMobHealthToolTip;
	public final BooleanValue enableCritterCageVillagerProfessionToolTip;
	public final BooleanValue enableCritterCageVillagerTradingLevelToolTip;
	public final BooleanValue enableCritterCageMobOwnerToolTip;
	public final BooleanValue enableCritterCageMobCollarColorToolTip;
	public final EnumValue<TextFormatting> toolTipColor;
	public final BooleanValue enableCrystalDimensionFog;
	public final DoubleValue crystalDimensionFogDensity;
	public final BooleanValue enableCrystalDimensionParticles;
	public final IntValue crystalDimensionParticleDensity;
	
	public final DoubleValue lavaEelSetLavaFogDensity;
	public final DoubleValue lavaEelSetFireStackTranslation;

	public final BooleanValue enableVFXEffects;
	public final BooleanValue enableCameraShake;
	public final BooleanValue enableCameraZoom;
	
	public CAClientConfig(ForgeConfigSpec.Builder builder) {
		builder.push("ToolTips");
		enableTooltips = builder.define("Enable tooltips", true);
		toolTipColor = builder.comment("(ToolTip color applies to both default tooltip description text and when shift-clicking).")
				.defineEnum("ToolTip Color", TextFormatting.AQUA);
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
		builder.pop(2);
		
		builder.push("Dimensions");
		builder.push("Crystal Dimension");
		enableCrystalDimensionFog = builder.define("Enable fog in the Crystal Dimension", true);
		enableCrystalDimensionParticles = builder.define("Enable particles in the Crystal Dimension", true);
		crystalDimensionFogDensity = builder.defineInRange("Crystal Dimension fog density", 0.03D, 0, 1.0D);
		crystalDimensionParticleDensity = builder.defineInRange("Crystal Dimension particle density", 2, 0, 100);
		builder.pop(2);
		
		builder.push("Functionality");
		
		builder.push("VFX Effects");
		enableVFXEffects = builder.comment("Enable/Disable all VFX effects under the 'VFX Effects' section.")
				.define("Enable VFX effects", true);
		builder.push("Camera");
		enableCameraShake = builder.define("Enable camera shake", true);
		enableCameraZoom = builder.define("Enable camera zoom", true);
		builder.pop(2);
		
		builder.push("Armor");
		builder.push("Lava Eel Armor");
		lavaEelSetLavaFogDensity = builder
				.comment("A static fog value for when you have the full Lava Eel set equipped and you're inside lava.")
				.defineInRange("Lava fog density", 0.02F, 0, 1F);
		lavaEelSetFireStackTranslation = builder
				.comment("The position of fire on your screen when you're inside lava. 0 is the default. Positive moves it up, and negative moves it down.")
				.defineInRange("Fire (y) Position on screen", -0.50, -1.0D, 1.0D);
		builder.pop();
		builder.pop(2);
	}
}
