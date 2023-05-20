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
	public final IntValue ultimateSwordDamage;
	public final IntValue ultimateAxeDamage;
	public final IntValue ultimatePickaxeDamage;
	public final IntValue ultimateShovelDamage;
	public final IntValue ultimateHoeDamage;
	public final DoubleValue ultimateBowArrowBaseDamage;
	public final DoubleValue ultimateBowArrowDamageMultiplier;
	public final IntValue emeraldSwordDamage;
	public final IntValue emeraldAxeDamage;
	public final IntValue emeraldPickaxeDamage;
	public final IntValue emeraldShovelDamage;
	public final IntValue emeraldHoeDamage;
	public final IntValue rubySwordDamage;
	public final IntValue rubyAxeDamage;
	public final IntValue rubyPickaxeDamage;
	public final IntValue rubyShovelDamage;
	public final IntValue rubyHoeDamage;
	public final IntValue amethystSwordDamage;
	public final IntValue amethystAxeDamage;
	public final IntValue amethystPickaxeDamage;
	public final IntValue amethystShovelDamage;
	public final IntValue amethystHoeDamage;
	public final IntValue tigersEyeSwordDamage;
	public final IntValue tigersEyeAxeDamage;
	public final IntValue tigersEyePickaxeDamage;
	public final IntValue tigersEyeShovelDamage;
	public final IntValue tigersEyeHoeDamage;
	public final IntValue copperSwordDamage;
	public final IntValue copperAxeDamage;
	public final IntValue copperPickaxeDamage;
	public final IntValue copperShovelDamage;
	public final IntValue copperHoeDamage;
	public final IntValue tinSwordDamage;
	public final IntValue tinAxeDamage;
	public final IntValue tinPickaxeDamage;
	public final IntValue tinShovelDamage;
	public final IntValue tinHoeDamage;
	public final IntValue silverSwordDamage;
	public final IntValue silverAxeDamage;
	public final IntValue silverPickaxeDamage;
	public final IntValue silverShovelDamage;
	public final IntValue silverHoeDamage;
	public final IntValue platinumSwordDamage;
	public final IntValue platinumAxeDamage;
	public final IntValue platinumPickaxeDamage;
	public final IntValue platinumShovelDamage;
	public final IntValue platinumHoeDamage;
	public final IntValue crystalWoodSwordDamage;
	public final IntValue crystalWoodAxeDamage;
	public final IntValue crystalWoodPickaxeDamage;
	public final IntValue crystalWoodShovelDamage;
	public final IntValue crystalWoodHoeDamage;
	public final IntValue kyaniteSwordDamage;
	public final IntValue kyaniteAxeDamage;
	public final IntValue kyanitePickaxeDamage;
	public final IntValue kyaniteShovelDamage;
	public final IntValue kyaniteHoeDamage;
	public final IntValue catsEyeSwordDamage;
	public final IntValue catsEyeAxeDamage;
	public final IntValue catsEyePickaxeDamage;
	public final IntValue catsEyeShovelDamage;
	public final IntValue catsEyeHoeDamage;
	public final IntValue pinkTourmSwordDamage;
	public final IntValue pinkTourmAxeDamage;
	public final IntValue pinkTourmPickaxeDamage;
	public final IntValue pinkTourmShovelDamage;
	public final IntValue pinkTourmHoeDamage;
	public final IntValue nightmareSwordDamage;
	public final IntValue basiliskSwordDamage;
	public final IntValue experienceSwordDamage;
	public final IntValue poisonSwordDamage;
	public final IntValue ratSwordDamage;
	public final IntValue fairySwordDamage;
	public final IntValue mantisClawDamage;
	public final IntValue bigHammerDamage;
	public final IntValue prismaticReaperDamage;

	public final IntValue attitudeAdjusterDamage;
	public final IntValue berthaDamage;
	public final IntValue battleAxeDamage;
	public final IntValue queenBattleAxeDamage;
	public final IntValue royalGuardianSwordDamage;
	public final IntValue slayerChainsawDamage;

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

	public CACommonConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Log messages");
		terraforgedCheckMsg = builder.define("Terraforged check message active", true);
		builder.pop();
		
		builder.push("Attack Damage");
		//			builder.comment("Set damages are of the Sword \n Axe is +2 damage, Shovel is -1 damage and Pickaxe is -2 damage \n Hoes are always 1 damage");
		builder.push("Ultimate Weapons/Tools");
		ultimateSwordDamage = builder.defineInRange("Damage of the Ultimate Sword", 40, 0, 10000);
		ultimateAxeDamage = builder.defineInRange("Damage of the Ultimate Axe", 42, 0, 10000);
		ultimatePickaxeDamage = builder.defineInRange("Damage of the Ultimate Pickaxe", 38, 0, 10000);
		ultimateShovelDamage = builder.defineInRange("Damage of the Ultimate Shovel", 39, 0, 10000);
		ultimateHoeDamage = builder.defineInRange("Damage of the Ultimate Hoe", 1, 0, 10000);
		ultimateBowArrowBaseDamage = builder
				.comment("How much damage the Ultimate bow will add up to the base arrow damage")
				.defineInRange("Additional Damage of the Ultimate Bow Arrows", 5D, 0D, 10000D);
		ultimateBowArrowDamageMultiplier = builder.defineInRange("Damage Multiplier of the Ultimate Bow's Power Enchantment", 0.5, 0, 10000D);
		builder.pop();
		
		builder.push("Emerald Weapons/Tools");
		emeraldSwordDamage = builder.defineInRange("Damage of the Emerald Sword", 6, 0, 10000);
		emeraldAxeDamage = builder.defineInRange("Damage of the Emerald Axe", 9, 0, 10000);
		emeraldPickaxeDamage = builder.defineInRange("Damage of the Emerald Pickaxe", 4, 0, 10000);
		emeraldShovelDamage = builder.defineInRange("Damage of the Emerald Shovel", 5, 0, 10000);
		emeraldHoeDamage = builder.defineInRange("Damage of the Emerald Hoe", 1, 0, 10000);
		builder.pop();
		
		builder.push("Ruby Weapons/Tools");
		rubySwordDamage = builder.defineInRange("Damage of the Ruby Sword", 20, 0, 10000);
		rubyAxeDamage = builder.defineInRange("Damage of the Ruby Axe", 22, 0, 10000);
		rubyPickaxeDamage = builder.defineInRange("Damage of the Ruby Pickaxe", 18, 0, 10000);
		rubyShovelDamage = builder.defineInRange("Damage of the Ruby Shovel", 19, 0, 10000);
		rubyHoeDamage = builder.defineInRange("Damage of the Ruby Hoe", 1, 0, 10000);
		builder.pop();
		
		builder.push("Amethyst Weapons/Tools");
		amethystSwordDamage = builder.defineInRange("Damage of the Amethyst Sword", 15, 0, 10000);
		amethystAxeDamage = builder.defineInRange("Damage of the Amethyst Axe", 17, 0, 10000);
		amethystPickaxeDamage = builder.defineInRange("Damage of the Amethyst Pickaxe", 13, 0, 10000);
		amethystShovelDamage = builder.defineInRange("Damage of the Amethyst Shovel", 14, 0, 10000);
		amethystHoeDamage = builder.defineInRange("Damage of the Amethyst Hoe", 1, 0, 10000);
		builder.pop();
		
		builder.push("Tiger's Eye Weapons/Tools");
		tigersEyeSwordDamage = builder.defineInRange("Damage of the Tiger's Eye Sword", 12, 0, 10000);
		tigersEyeAxeDamage = builder.defineInRange("Damage of the Tiger's Eye Axe", 14, 0, 10000);
		tigersEyePickaxeDamage = builder.defineInRange("Damage of the Tiger's Eye Pickaxe", 10, 0, 10000);
		tigersEyeShovelDamage = builder.defineInRange("Damage of the Tiger's Eye Shovel", 11, 0, 10000);
		tigersEyeHoeDamage = builder.defineInRange("Damage of the Tiger's Eye Hoe", 1, 0, 10000);
		builder.pop();
		
		builder.push("Crystal Wood Weapons/Tools");
		crystalWoodSwordDamage = builder.defineInRange("Damage of the Crystal Wood Sword", 5, 0, 10000);
		crystalWoodAxeDamage = builder.defineInRange("Damage of the Crystal Wood Axe", 9, 0, 10000);
		crystalWoodPickaxeDamage = builder.defineInRange("Damage of the Crystal Wood Pickaxe", 3, 0, 10000);
		crystalWoodShovelDamage = builder.defineInRange("Damage of the Crystal Wood Shovel", 3, 0, 10000);
		crystalWoodHoeDamage = builder.defineInRange("Damage of the Crystal Wood Hoe", 1, 0, 10000);
		builder.pop();
		
		builder.push("Kyanite Weapons/Tools");
		kyaniteSwordDamage = builder.defineInRange("Damage of the Kyanite Sword", 6, 0, 10000);
		kyaniteAxeDamage = builder.defineInRange("Damage of the Kyanite Axe", 9, 0, 10000);
		kyanitePickaxeDamage = builder.defineInRange("Damage of the Kyanite Pickaxe", 4, 0, 10000);
		kyaniteShovelDamage = builder.defineInRange("Damage of the Kyanite Shovel", 4, 0, 10000);
		kyaniteHoeDamage = builder.defineInRange("Damage of the Kyanite Hoe", 1, 0, 10000);
		builder.pop();
		
		builder.push("Cat's Eye Weapons/Tools");
		catsEyeSwordDamage = builder.defineInRange("Damage of the Cat's Eye Sword", 12, 0, 10000);
		catsEyeAxeDamage = builder.defineInRange("Damage of the Cat's Eye Axe", 14, 0, 10000);
		catsEyePickaxeDamage = builder.defineInRange("Damage of the Cat's Eye Pickaxe", 10, 0, 10000);
		catsEyeShovelDamage = builder.defineInRange("Damage of the Cat's Eye Shovel", 11, 0, 10000);
		catsEyeHoeDamage = builder.defineInRange("Damage of the Cat's Eye Hoe", 1, 0, 10000);
		builder.pop();
		
		builder.push("Pink Tourmaline Weapons/Tools");
		pinkTourmSwordDamage = builder.defineInRange("Damage of the Pink Tourmaline Sword", 11, 0, 10000);
		pinkTourmAxeDamage = builder.defineInRange("Damage of the Pink Tourmaline Axe", 13, 0, 10000);
		pinkTourmPickaxeDamage = builder.defineInRange("Damage of the Pink Tourmaline Pickaxe", 9, 0, 10000);
		pinkTourmShovelDamage = builder.defineInRange("Damage of the Pink Tourmaline Shovel", 10, 0, 10000);
		pinkTourmHoeDamage = builder.defineInRange("Damage of the Pink Tourmaline Hoe", 1, 0, 10000);
		builder.pop();
		
		builder.push("Copper Weapons/Tools");
		copperSwordDamage = builder.defineInRange("Damage of the Copper Sword", 5, 0, 10000);
		copperAxeDamage = builder.defineInRange("Damage of the Copper Axe", 9, 0, 10000);
		copperPickaxeDamage = builder.defineInRange("Damage of the Copper Pickaxe", 3, 0, 10000);
		copperShovelDamage = builder.defineInRange("Damage of the Copper Shovel", 4, 0, 10000);
		copperHoeDamage = builder.defineInRange("Damage of the Copper Hoe", 1, 0, 10000);
		builder.pop();
		
		builder.push("Tin Weapons/Tools");
		tinSwordDamage = builder.defineInRange("Damage of the Tin Sword", 6, 0, 10000);
		tinAxeDamage = builder.defineInRange("Damage of the Tin Axe", 9, 0, 10000);
		tinPickaxeDamage = builder.defineInRange("Damage of the Tin Pickaxe", 4, 0, 10000);
		tinShovelDamage = builder.defineInRange("Damage of the Tin Shovel", 4, 0, 10000);
		tinHoeDamage = builder.defineInRange("Damage of the Tin Hoe", 1, 0, 10000);
		builder.pop();
		
		builder.push("Silver Weapons/Tools");
		silverSwordDamage = builder.defineInRange("Damage of the Silver Sword", 6, 0, 10000);
		silverAxeDamage = builder.defineInRange("Damage of the Silver Axe", 9, 0, 10000);
		silverPickaxeDamage = builder.defineInRange("Damage of the Silver Pickaxe", 4, 0, 10000);
		silverShovelDamage = builder.defineInRange("Damage of the Silver Shovel", 5, 0, 10000);
		silverHoeDamage = builder.defineInRange("Damage of the Silver Hoe", 1, 0, 10000);
		builder.pop();
		
		builder.push("Platinum Weapons/Tools");
		platinumSwordDamage = builder.defineInRange("Damage of the Platinum Sword", 10, 0, 10000);
		platinumAxeDamage = builder.defineInRange("Damage of the Platinum Axe", 12, 0, 10000);
		platinumPickaxeDamage = builder.defineInRange("Damage of the Platinum Pickaxe", 8, 0, 10000);
		platinumShovelDamage = builder.defineInRange("Damage of the Platinum Shovel", 9, 0, 10000);
		platinumHoeDamage = builder.defineInRange("Damage of the Platinum Hoe", 1, 0, 10000);
		builder.pop();
		
		builder.push("Misc Weapons/Tools");
		nightmareSwordDamage = builder.defineInRange("Damage of the Nightmare Sword", 30, 0, 10000);
		basiliskSwordDamage = builder.defineInRange("Damage of the Basilisk Sword", 25, 0, 10000);
		experienceSwordDamage = builder.defineInRange("Damage of the Experience Sword", 10, 0, 10000);
		poisonSwordDamage = builder.defineInRange("Damage of the Poison Sword", 10, 0, 10000);
		ratSwordDamage = builder.defineInRange("Damage of the Rat Sword", 10, 0, 10000);
		fairySwordDamage = builder.defineInRange("Damage of the Fairy Sword", 10, 0, 10000);
		mantisClawDamage = builder.defineInRange("Damage of the Mantis Claw", 10, 0, 10000);
		bigHammerDamage = builder.defineInRange("Damage of the Big Hammer", 15, 0, 10000);
		prismaticReaperDamage = builder.defineInRange("Damage of the Prismatic Reaper", 29, 0, 10000);
		builder.pop();
		
		builder.push("Big Weapons/Tools");
		attitudeAdjusterDamage = builder.defineInRange("Damage of the Attitude Adjuster", 65, 0, 10000);
		berthaDamage = builder.defineInRange("Damage of the Big Bertha", 500, 0, 10000);
		battleAxeDamage = builder.defineInRange("Damage of the Battle Axe", 84, 0, 10000);
		queenBattleAxeDamage = builder.defineInRange("Damage of the Queen Scale Battle Axe", 666, 0, 10000);
		royalGuardianSwordDamage = builder.defineInRange("Damage of the Royal Guardian Sword", 750, 0, 10000);
		slayerChainsawDamage = builder.defineInRange("Damage of the Slayer Chainsaw", 65, 0, 10000);
		builder.pop(2);
		
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
		
		enableAutoEnchanting = builder
				.comment("If disabled, auto-enchanted items will be able to be enchanted manually.")
				.define("Auto-enchant specific tools and weapons", true);
		builder.pop();
		
		builder.push("Breeding");
		enableEnchantedAnimalBreeding = builder
				.comment("Will Enchanted Animals be Breedable?")
				.define("Enchanted Animal Breeding", false);
		builder.pop();
		
		builder.push("Items");
		builder.pop();
		
		builder.push("Textures");
		holidayTextures = builder
				.comment("Will holiday special textures be obtainable?")
				.define("Obtainable Holiday Textures", true);
		builder.pop();
		
		builder.push("Ender Dragon");
		enableDragonEggRespawns = builder
				.comment("Will the Ender Dragon Egg respawn after the first death?")
				.define("Ender Dragon Egg Respawn", true);
		enderDragonHeadDrop = builder
				.comment("Will the Ender Dragon drop her head?")
				.define("Ender Dragon Head Drop", true);
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
				.comment("Enable/Disable ores generating on the stalagmites in Stalagmite Valley. Note that this may require a more powerful computer, \n"
						+ "until further optimization is made.")
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
		
		builder.push("Entity");
		
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
		builder.pop(2);
		
	}
}
