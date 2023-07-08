package io.github.chaosawakens.config.server;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class CAServerConfig {
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
	
	public final BooleanValue modServerPermission;
	public final BooleanValue debugLogging;
	
	public CAServerConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Tools/Armor");
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
		
		builder.push("Crystalwood Weapons/Tools");
		crystalWoodSwordDamage = builder.defineInRange("Damage of the Crystalwood Sword", 5, 0, 10000);
		crystalWoodAxeDamage = builder.defineInRange("Damage of the Crystalwood Axe", 9, 0, 10000);
		crystalWoodPickaxeDamage = builder.defineInRange("Damage of the Crystalwood Pickaxe", 3, 0, 10000);
		crystalWoodShovelDamage = builder.defineInRange("Damage of the Crystalwood Shovel", 3, 0, 10000);
		crystalWoodHoeDamage = builder.defineInRange("Damage of the Crystalwood Hoe", 1, 0, 10000);
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
		
		builder.push("Server Management/Logging");
		modServerPermission = builder.comment("Allow Chaos Awakens to manipulate server settings such as enabling flight.")
				.define("Chaos Awakens has OP server permissions", true);
		debugLogging = builder.comment("Debug CA events on the server while it's running.")
				.define("Debug logging", true);
		builder.pop();
	}
}
