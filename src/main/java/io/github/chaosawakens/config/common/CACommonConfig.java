package io.github.chaosawakens.config.common;

import io.github.chaosawakens.common.util.EnumUtil;
import io.github.chaosawakens.common.util.EnumUtil.ElytraDamageType;
import io.github.chaosawakens.common.util.EnumUtil.ExplosionType;
import io.github.chaosawakens.common.util.EnumUtil.SurvivalSpawnerManipulationType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class CACommonConfig {	
	public final IntValue attitudeAdjusterExplosionSize;
	public final EnumValue<ExplosionType> attitudeAdjusterExplosionType;
	public final BooleanValue attitudeAdjusterExplosionFire;
	public final IntValue rayGunExplosionSize;
	public final EnumValue<ExplosionType> rayGunExplosionType;
	public final BooleanValue rayGunExplosionFire;
	public final IntValue thunderStaffExplosionSize;
	public final EnumValue<ExplosionType> thunderStaffExplosionType;
	public final BooleanValue thunderStaffExplosionFire;

	public final BooleanValue enableAutoEnchanting;

	public final IntValue experienceSwordXPMultiplier;
	public final BooleanValue enableExperienceSwordBonus;

	public final EnumValue<ElytraDamageType> enderDragonScaleArmorElytraDamageType;

	public final IntValue experienceArmorSetRandomBonusXPSpawnTime;
	public final IntValue experienceArmorSetXPMultiplier;

	public final DoubleValue emeraldArmorDiscountMultiplier;

	public final IntValue lapisArmorSetBookshelfPowerModifier;
	
	public final BooleanValue enableUltimatePickaxeBonus;

	public final BooleanValue crystalWorldRequiresEmptyInventory;
	public final BooleanValue enableBrownAntTeleport;
	public final BooleanValue enableRainbowAntTeleport;
	public final BooleanValue enableRedAntTeleport;
	public final BooleanValue enableUnstableAntTeleport;
	public final BooleanValue enableTermiteTeleport;
	public final BooleanValue enableButterflyTeleport;

	public final BooleanValue wanderingTraderSellsUraniumAndTitanium;
	public final BooleanValue wanderingTraderSellsTriffidGoo;
	public final IntValue roboWarriorExplosionSize;
	public final EnumValue<ExplosionType> roboWarriorExplosionType;
	public final BooleanValue roboWarriorExplosionFire;

	public final BooleanValue enableOreGen;
	public final BooleanValue enableFossilGen;
	public final BooleanValue enableTrollOreGen;
	public final BooleanValue enableDzMineralOreGen;
	public final BooleanValue spawnDzOresInOverworld;
	public final BooleanValue enableNestGen;

	public final BooleanValue enableMarbleGen;
	public final BooleanValue enableLimestoneGen;
	public final BooleanValue enableRhinestoneGen;

	public final BooleanValue enableMarbleGenInOverworld;
	public final BooleanValue enableLimestoneGenInOverworld;
	public final BooleanValue enableRhinestoneGenInOverworld;

	public final BooleanValue enableOreRubyGen;
	public final BooleanValue enableOreTigersEyeGen;
	public final BooleanValue enableOreAmethystGen;
	public final BooleanValue enableOreTitaniumGen;
	public final BooleanValue enableOreUraniumGen;
	public final BooleanValue enableOreSaltGen;
	public final BooleanValue enableOreAluminumGen;
	public final BooleanValue enableOreCopperGen;
	public final BooleanValue enableOreTinGen;
	public final BooleanValue enableOreSilverGen;
	public final BooleanValue enableOrePlatinumGen;
	public final BooleanValue enableOreSunstoneGen;
	public final BooleanValue enableOreBloodstoneGen;

	public final BooleanValue enableStalagmiteOreGen;

	public final BooleanValue enableEnchantedAnimalBreeding;

	public final BooleanValue generateAcaciaEntTree;
	public final BooleanValue generateBirchEntTree;
	public final BooleanValue generateCrimsonEntTree;
	public final BooleanValue generateDarkOakEntTree;
	public final BooleanValue generateJungleEntTree;
	public final BooleanValue generateOakEntTree;
	public final BooleanValue generateSpruceEntTree;
	public final BooleanValue generateWarpedEntTree;
	public final BooleanValue generateWaspNest;

	public final BooleanValue holidayTextures;

	public final BooleanValue enableDragonEggRespawns;
	public final BooleanValue enderDragonHeadDrop;

	public final BooleanValue enableEnderScaleDragonArmorSetBonus;
	public final BooleanValue enableExperienceArmorSetBonus;
	public final BooleanValue enableEmeraldArmorSetBonus;
	public final BooleanValue enableLavaEelArmorSetBonus;
	public final BooleanValue enableLapisArmorSetBonus;

	public final BooleanValue terraforgedCheckMsg;

	public final EnumValue<SurvivalSpawnerManipulationType> spawnEggsSpawnersSurvival;

	public final BooleanValue showUpdateMessage;

	//TODO Remove damage config things
	public CACommonConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Log messages");
		terraforgedCheckMsg = builder.define("Terraforged check message active", true);
		builder.pop();
		
		builder.push("Functionality");
		
		builder.push("Armor");
		builder.push("Ender Dragon Scale Armor");
		enderDragonScaleArmorElytraDamageType = builder
				.comment("How the elytra on the Ender Dragon Scale Armor will get damaged," + "\n"
						+ "where ARMOR means per hit, and ELYTRA means that it'll get damage every 20 ticks or so as you fly (basically the same as a normal elytra). \n"
						+ "This has yet to be functional in a future update")
				.defineEnum("Ender Dragon Scale Armor elytra damage type", EnumUtil.ElytraDamageType.ELYTRA);
		enableEnderScaleDragonArmorSetBonus = builder.define("Enable the Dragon Wings set bonus", true);
		builder.pop();
		
		builder.push("Emerald Armor");
		enableEmeraldArmorSetBonus = builder.define("Enable the Emerald set bonus", true);
		emeraldArmorDiscountMultiplier = builder
				.comment("The multiplier by which villagers will discount their prices when a player is wearing a full emerald set. \n"
						+ " This multiplier stacks per each player in the area. Formula: 1 = 12.5% Discount, 2 = 25% Discount, etc.")
				.defineInRange("Emerald Armor Set Discount Multiplier", 4.0D, 0D, 8.0D);
		builder.pop();
		
		builder.push("Experience Armor");
		enableExperienceArmorSetBonus = builder.define("Enable the Experience set bonus", true);
		experienceArmorSetRandomBonusXPSpawnTime = builder
				.comment("The number you input here will affect how frequently XP orbs randomly spawn on you, should you be wearing the full Experience Armor set. \n"
						+ " The larger the number, the less frequent XP orbs will spawn on you.")
				.defineInRange("The max amount of time (in ticks) between each XP orb spawn", 5500, 0, 100000);
		experienceArmorSetXPMultiplier = builder
				.comment("The multiplier for XP dropped, either by killing mobs or by breaking blocks. This doesn't work on randomly spawning XP orbs from \n"
						+ " the armor itself. Note that when killing a mob with the Experience Sword alongside the armor, the Experience Sword's multiplier \n"
						+ " will be added onto the armor's multiplier.")
				.defineInRange("Experience Armor Set XP Multiplier", 3, 0, 10000);
		builder.pop();
		
		builder.push("Lapis Armor");
		enableLapisArmorSetBonus = builder.define("Enable the Lapis set bonus", true);
/*		lapisArmorSetLevelRequirementReductionModifier = builder
				.comment("Modify/Substract from the enchantment level requirements for each enchantment row in the enchantment table. Note that if you attempt to expand or reduce \n"
						+ " the size of the contents within the list, or define a number higher than the price listed in the enchantment table, the prices will automatically default to vanilla values. If you attempt \n"
						+ " to define a NAN (not a number) value the game will crash. The first value is the modifier for the first row, the second value if the modifier for the \n"
						+ "second row, and the third value is the modifier for the third row.")
				.defineList("Lapis Set Enchantment Level Requirement Modifiers", LAPISMODLIST, cost -> cost instanceof Integer);*/
		lapisArmorSetBookshelfPowerModifier = builder
				.comment("The power of each bookshelf around the enchantment table when a player with the full Lapis Armor Set is present. This effect stacks with each present player.")
				.defineInRange("Lapis Armor Set Bookshelf Power Moifier", 2, 0, 15);
		builder.pop();
		
		builder.push("Lava Eel Armor");
		enableLavaEelArmorSetBonus = builder.define("Enable the Lava Eel set bonus", true);
		builder.pop(2);
		
		builder.push("Tools/Weapons");
		
		builder.push("Explosions");
		builder.push("Attitude Adjuster");
		attitudeAdjusterExplosionSize = builder.defineInRange("Attitude Adjuster explosion size", 4, 0, 500);
		attitudeAdjusterExplosionType = builder
				.comment("NONE - The Attitude Adjuster will not affect the terrain." + "\n"
						+ "BREAK - The Attitude Adjuster will drop some blocks that it breaks." + "\n"
						+ "DESTROY - The Attitude Adjuster will destroy blocks and never drop them.")
				.defineEnum("Attitude Adjuster explosion type", ExplosionType.BREAK);
		attitudeAdjusterExplosionFire = builder.define("Fire from Attitude Adjuster explosion", false);
		builder.pop();
		
		builder.push("Ray Gun");
		rayGunExplosionSize = builder.defineInRange("Ray Gun explosion size", 6, 0, 500);
		rayGunExplosionType = builder
				.comment("NONE - The Ray Gun will not affect the terrain." + "\n"
						+ "BREAK - The Ray Gun will drop some blocks that it breaks." + "\n"
						+ "DESTROY - The Ray Gun will destroy blocks and never drop them.")
				.defineEnum("Ray Gun explosion type", ExplosionType.BREAK);
		rayGunExplosionFire = builder.define("Fire from Ray Gun explosion", false);
		builder.pop();
		
		builder.push("Thunder Staff");
		thunderStaffExplosionSize = builder.defineInRange("Thunder Staff explosion size", 4, 0, 500);
		thunderStaffExplosionType = builder
				.comment("NONE - The Thunder Staff will not affect the terrain." + "\n"
						+ "BREAK - The Thunder Staff will drop some blocks that it breaks. (May not work due to lightning)" + "\n"
						+ "DESTROY - The Thunder Staff will destroy blocks and never drop them.")
				.defineEnum("Thunder Staff explosion type", ExplosionType.DESTROY);
		thunderStaffExplosionFire = builder.define("Fire from Thunder Staff explosion", true);
		builder.pop(2);
		
		builder.push("Swords");
		builder.push("Experience Sword");
		experienceSwordXPMultiplier = builder.defineInRange("Multiplier for XP dropped by killing mobs", 2, 0, 1000);
		enableExperienceSwordBonus = builder.define("Enable Experience Sword Weapon Bonus", true);
		builder.pop(2);
		
		builder.push("Pickaxes");
		builder.push("Ultimate Pickaxe");
		enableUltimatePickaxeBonus = builder.define("Enable Ultimate Pickaxe Tool Bonus", true);
		builder.pop(2);
		
		enableAutoEnchanting = builder
				.comment("If disabled, auto-enchanted items will be able to be enchanted manually.")
				.define("Auto-enchant specific tools and weapons", true);
		builder.pop();
		
		builder.push("Items");
		builder.pop();
		
		builder.push("Entity");
		
		builder.push("Breeding");
		enableEnchantedAnimalBreeding = builder
				.comment("Will Enchanted Animals be Breedable?")
				.define("Enchanted Animal Breeding", false);
		builder.pop();
		
		builder.push("Specific Entities");
		
		builder.push("Ender Dragon");
		enableDragonEggRespawns = builder
				.comment("Will the Ender Dragon Egg respawn after the first death?")
				.define("Ender Dragon Egg Respawn", true);
		enderDragonHeadDrop = builder
				.comment("Will the Ender Dragon drop her head?")
				.define("Ender Dragon Head Drop", true);
		builder.pop();
		
		builder.push("Wandering Trader");
		wanderingTraderSellsTriffidGoo = builder
				.comment("Can the Wandering Trader sell Triffid Goo?")
				.define("Triffid Goo from Wandering Trader?", true);
		wanderingTraderSellsUraniumAndTitanium = builder
				.comment("Can the Wandering Trader sell Uranium and Titanium?")
				.define("Uranium and Titanium from Wandering Trader?", false);
		builder.pop();
		
		builder.push("Robo Warrior");
		roboWarriorExplosionSize = builder.defineInRange("Robo Warrior explosion size", 6, 0, 500);
		roboWarriorExplosionType = builder
				.comment("NONE - The Robo Warrior will not affect the terrain." + "\n"
						+ "BREAK - The Robo Warrior will drop some blocks that it breaks." + "\n"
						+ "DESTROY - The Robo Warrior will destroy blocks and never drop them.")
				.defineEnum("Ray Gun explosion type", ExplosionType.BREAK);
		roboWarriorExplosionFire = builder.define("Fire from Robo Warrior explosion", false);
		builder.pop(3);
		
		builder.push("Textures");
		holidayTextures = builder
				.comment("Will holiday special textures be obtainable?")
				.define("Obtainable Holiday Textures", true);
		builder.pop();
		
		builder.push("Update Checker");
		showUpdateMessage = builder
				.comment("Send messages when there is a new update?")
				.define("Show Update Messages", true);
		builder.pop();
		
		builder.push("Spawners");
		spawnEggsSpawnersSurvival = builder
				.comment("NO_BLOCKING - All Spawn Eggs can be used on a Spawner in Survival." + "\n"
						+ "BLOCK_ALL - All Spawn Eggs will be blocked from being used on a Spawner in Survival." + "\n"
						+ "BLOCK_CHAOS_AWAKENS - Only Spawn Eggs from Chaos Awakens will be blocked from being used on a Spawner in Survival."
						+ "TAG_BLACKLISTED - Only Spawn Eggs which aren't tagged with the 'SPAWNER_SPAWN_EGG' tag can be used on a Spawner in Survival."
						+ "TAG_WHITELISTED - Only Spawn Eggs which are tagged with the 'SPAWNER_SPAWN_EGG' tag can be used on a Spawner in Survival.")
				.defineEnum("Spawn Eggs on Spawners in Survival?", SurvivalSpawnerManipulationType.BLOCK_ALL);
		builder.pop(2);
		
		builder.push("World Generation");
		enableOreGen = builder.define("Enable ore generation", true);
		
		builder.push("Specific Ore Spawning");
		enableOreSaltGen = builder.define("Salt ore generation", true);
		enableOreAmethystGen = builder.define("Amethyst ore generation", true);
		enableOreRubyGen = builder.define("Ruby ore generation", true);
		enableOreTigersEyeGen = builder.define("Tiger's Eye ore generation", true);
		enableOreTitaniumGen = builder.define("Titanium ore generation", true);
		enableOreUraniumGen = builder.define("Uranium ore generation", true);
		enableOreCopperGen = builder.define("Copper ore generation", true);
		enableOreTinGen = builder.define("Tin ore generation", true);
		enableOreSilverGen = builder.define("Silver ore generation", true);
		enableOrePlatinumGen = builder.define("Platinum ore generation", true);
		enableOreSunstoneGen = builder.define("Sunstone ore generation", true);
		enableOreBloodstoneGen = builder.define("Bloodstone ore generation", true);
		enableOreAluminumGen = builder.define("Aluminum ore generation", true);
		builder.pop();
		
		enableFossilGen = builder.define("Enable fossil generation", true);
		enableMarbleGen = builder.define("Enable marble generation in the mining dimension", true);
		enableLimestoneGen = builder.define("Enable limestone generation in the mining dimension", true);
		enableRhinestoneGen = builder.define("Enable rhinestone generation in the mining dimension", true);
		enableMarbleGenInOverworld = builder.define("Enable marble generation in the overworld", false);
		enableLimestoneGenInOverworld = builder.define("Enable limestone generation in the overworld", false);
		enableRhinestoneGenInOverworld = builder.define("Enable rhinestone generation in the overworld", false);
		enableTrollOreGen = builder.define("Enable infested ore generation", true);
		enableDzMineralOreGen = builder.define("Enable DZ ore generation", true);
		spawnDzOresInOverworld = builder
				.comment("Will DZ ores spawn in the Overworld?")
				.define("Overworld DZ Ores", false);
		enableNestGen = builder
				.comment("Will Nests spawn in the Overworld or the Ant Dimensions?")
				.define("Ant Nest generation", true);
		
		builder.push("Structures");			
		generateAcaciaEntTree = builder
				.comment("Will Acacia Ent Trees be generated?")
				.define("Generate Acacia Ent Trees", true);
		generateBirchEntTree = builder
				.comment("Will Birch Ent Trees be generated?")
				.define("Generate Birch Ent Trees", true);
		generateCrimsonEntTree = builder
				.comment("Will Crimson Ent Trees be generated?")
				.define("Generate Crimson Ent Trees", true);
		generateDarkOakEntTree = builder
				.comment("Will Dark Oak Ent Trees be generated?")
				.define("Generate Dark Oak Ent Trees", true);
		generateJungleEntTree = builder
				.comment("Will Jungle Ent Trees be generated?")
				.define("Generate Jungle Ent Trees", true);
		generateOakEntTree = builder
				.comment("Will Oak Ent Trees be generated?")
				.define("Generate Oak Ent Trees", true);
		generateSpruceEntTree = builder
				.comment("Will Spruce Ent Trees be generated?")
				.define("Generate Spruce Ent Trees", true);
		generateWarpedEntTree = builder
				.comment("Will Warped Ent Trees be generated?")
				.define("Generate Warped Ent Trees", true);
		generateWaspNest = builder
				.comment("Will Wasp Nests be generated?")
				.define("Generate Wasp Nests", true);
		builder.pop();
		
		builder.push("Dimensioms");
		
		builder.push("Mining Paradise");
		builder.push("Biomes");
		builder.push("Stalagmite Valley");
		enableStalagmiteOreGen = builder
				.comment("Enable/Disable ores generating on the stalagmites in the Stalagmite Valley.")
				.define("Enable Stalamite Ore Gen", true);
		builder.pop(3);
		
		builder.pop(2);
		
		builder.push("Dimensions");
		crystalWorldRequiresEmptyInventory = builder
				.comment("Disable the requirement of needing an empty inventory to enter the Crystal World (Termite Dimension)?")
				.define("Crystal World Requires Empty Inventory", true);
		enableBrownAntTeleport = builder
				.comment("Will the Brown Ant teleport you to its Dimension?")
				.define("Brown Ant Teleport", true);
		enableRainbowAntTeleport = builder
				.comment("Will the Rainbow Ant teleport you to its Dimension?")
				.define("Rainbow Ant Teleport", true);
		enableRedAntTeleport = builder
				.comment("Will the Red Ant teleport you to its Dimension?")
				.define("Red Ant Teleport", true);
		enableUnstableAntTeleport = builder
				.comment("Will the Unstable Ant teleport you to its Dimension?")
				.define("Unstable Ant Teleport", true);
		enableTermiteTeleport = builder
				.comment("Will the Termite teleport you to its Dimension?")
				.define("Termite Teleport", true);
		enableButterflyTeleport = builder
				.comment("Will the Butterfly teleport you to its Dimension?")
				.define("Butterfly Teleport", true);
		builder.pop();
	}
}
