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
	public final DoubleValue ultimateCrossbowBoltBaseDamage;
	public final DoubleValue ultimateBowArrowDamageMultiplier;
	public final DoubleValue ultimateCrossbowBoltDamageMultiplier;
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
	public final IntValue crystalwoodSwordDamage;
	public final IntValue crystalwoodAxeDamage;
	public final IntValue crystalwoodPickaxeDamage;
	public final IntValue crystalwoodShovelDamage;
	public final IntValue crystalwoodHoeDamage;
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
	public final BooleanValue lenientAssetEnforcement;
	
	public CAServerConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Tools/Armor");
		builder.push("Ultimate Weapons/Tools");
		ultimateSwordDamage = builder.defineInRange("Damage of the Ultimate Sword", 40, 0, Integer.MAX_VALUE);
		ultimateAxeDamage = builder.defineInRange("Damage of the Ultimate Axe", 42, 0, Integer.MAX_VALUE);
		ultimatePickaxeDamage = builder.defineInRange("Damage of the Ultimate Pickaxe", 38, 0, Integer.MAX_VALUE);
		ultimateShovelDamage = builder.defineInRange("Damage of the Ultimate Shovel", 39, 0, Integer.MAX_VALUE);
		ultimateHoeDamage = builder.defineInRange("Damage of the Ultimate Hoe", 1, 0, Integer.MAX_VALUE);
		ultimateBowArrowBaseDamage = builder
				.comment("How much damage the Ultimate bow will add up to the base arrow damage")
				.defineInRange("Additional Damage of the Ultimate Bow Arrows", 5D, 0D, Integer.MAX_VALUE);
		ultimateCrossbowBoltBaseDamage = builder.defineInRange("Added Power Damage of the Ultimate Crossbow Bolts", 50D, 0D, Integer.MAX_VALUE);
		ultimateBowArrowDamageMultiplier = builder.defineInRange("Damage Multiplier of the Ultimate Bow's Power Enchantment", 0.5, 0, Integer.MAX_VALUE);
		ultimateCrossbowBoltDamageMultiplier = builder.defineInRange("Damage Multiplier of the Ultimate Crossbow's Power Enchantment", 1D, 0, Integer.MAX_VALUE);
		builder.pop();
		
		builder.push("Emerald Weapons/Tools");
		emeraldSwordDamage = builder.defineInRange("Damage of the Emerald Sword", 6, 0, Integer.MAX_VALUE);
		emeraldAxeDamage = builder.defineInRange("Damage of the Emerald Axe", 9, 0, Integer.MAX_VALUE);
		emeraldPickaxeDamage = builder.defineInRange("Damage of the Emerald Pickaxe", 4, 0, Integer.MAX_VALUE);
		emeraldShovelDamage = builder.defineInRange("Damage of the Emerald Shovel", 5, 0, Integer.MAX_VALUE);
		emeraldHoeDamage = builder.defineInRange("Damage of the Emerald Hoe", 1, 0, Integer.MAX_VALUE);
		builder.pop();
		
		builder.push("Ruby Weapons/Tools");
		rubySwordDamage = builder.defineInRange("Damage of the Ruby Sword", 20, 0, Integer.MAX_VALUE);
		rubyAxeDamage = builder.defineInRange("Damage of the Ruby Axe", 22, 0, Integer.MAX_VALUE);
		rubyPickaxeDamage = builder.defineInRange("Damage of the Ruby Pickaxe", 18, 0, Integer.MAX_VALUE);
		rubyShovelDamage = builder.defineInRange("Damage of the Ruby Shovel", 19, 0, Integer.MAX_VALUE);
		rubyHoeDamage = builder.defineInRange("Damage of the Ruby Hoe", 1, 0, Integer.MAX_VALUE);
		builder.pop();
		
		builder.push("Amethyst Weapons/Tools");
		amethystSwordDamage = builder.defineInRange("Damage of the Amethyst Sword", 15, 0, Integer.MAX_VALUE);
		amethystAxeDamage = builder.defineInRange("Damage of the Amethyst Axe", 17, 0, Integer.MAX_VALUE);
		amethystPickaxeDamage = builder.defineInRange("Damage of the Amethyst Pickaxe", 13, 0, Integer.MAX_VALUE);
		amethystShovelDamage = builder.defineInRange("Damage of the Amethyst Shovel", 14, 0, Integer.MAX_VALUE);
		amethystHoeDamage = builder.defineInRange("Damage of the Amethyst Hoe", 1, 0, Integer.MAX_VALUE);
		builder.pop();
		
		builder.push("Tiger's Eye Weapons/Tools");
		tigersEyeSwordDamage = builder.defineInRange("Damage of the Tiger's Eye Sword", 12, 0, Integer.MAX_VALUE);
		tigersEyeAxeDamage = builder.defineInRange("Damage of the Tiger's Eye Axe", 14, 0, Integer.MAX_VALUE);
		tigersEyePickaxeDamage = builder.defineInRange("Damage of the Tiger's Eye Pickaxe", 10, 0, Integer.MAX_VALUE);
		tigersEyeShovelDamage = builder.defineInRange("Damage of the Tiger's Eye Shovel", 11, 0, Integer.MAX_VALUE);
		tigersEyeHoeDamage = builder.defineInRange("Damage of the Tiger's Eye Hoe", 1, 0, Integer.MAX_VALUE);
		builder.pop();
		
		builder.push("Crystalwood Weapons/Tools");
		crystalwoodSwordDamage = builder.defineInRange("Damage of the Crystalwood Sword", 5, 0, Integer.MAX_VALUE);
		crystalwoodAxeDamage = builder.defineInRange("Damage of the Crystalwood Axe", 9, 0, Integer.MAX_VALUE);
		crystalwoodPickaxeDamage = builder.defineInRange("Damage of the Crystalwood Pickaxe", 3, 0, Integer.MAX_VALUE);
		crystalwoodShovelDamage = builder.defineInRange("Damage of the Crystalwood Shovel", 3, 0, Integer.MAX_VALUE);
		crystalwoodHoeDamage = builder.defineInRange("Damage of the Crystalwood Hoe", 1, 0, Integer.MAX_VALUE);
		builder.pop();
		
		builder.push("Kyanite Weapons/Tools");
		kyaniteSwordDamage = builder.defineInRange("Damage of the Kyanite Sword", 6, 0, Integer.MAX_VALUE);
		kyaniteAxeDamage = builder.defineInRange("Damage of the Kyanite Axe", 9, 0, Integer.MAX_VALUE);
		kyanitePickaxeDamage = builder.defineInRange("Damage of the Kyanite Pickaxe", 4, 0, Integer.MAX_VALUE);
		kyaniteShovelDamage = builder.defineInRange("Damage of the Kyanite Shovel", 4, 0, Integer.MAX_VALUE);
		kyaniteHoeDamage = builder.defineInRange("Damage of the Kyanite Hoe", 1, 0, Integer.MAX_VALUE);
		builder.pop();
		
		builder.push("Cat's Eye Weapons/Tools");
		catsEyeSwordDamage = builder.defineInRange("Damage of the Cat's Eye Sword", 12, 0, Integer.MAX_VALUE);
		catsEyeAxeDamage = builder.defineInRange("Damage of the Cat's Eye Axe", 14, 0, Integer.MAX_VALUE);
		catsEyePickaxeDamage = builder.defineInRange("Damage of the Cat's Eye Pickaxe", 10, 0, Integer.MAX_VALUE);
		catsEyeShovelDamage = builder.defineInRange("Damage of the Cat's Eye Shovel", 11, 0, Integer.MAX_VALUE);
		catsEyeHoeDamage = builder.defineInRange("Damage of the Cat's Eye Hoe", 1, 0, Integer.MAX_VALUE);
		builder.pop();
		
		builder.push("Pink Tourmaline Weapons/Tools");
		pinkTourmSwordDamage = builder.defineInRange("Damage of the Pink Tourmaline Sword", 11, 0, Integer.MAX_VALUE);
		pinkTourmAxeDamage = builder.defineInRange("Damage of the Pink Tourmaline Axe", 13, 0, Integer.MAX_VALUE);
		pinkTourmPickaxeDamage = builder.defineInRange("Damage of the Pink Tourmaline Pickaxe", 9, 0, Integer.MAX_VALUE);
		pinkTourmShovelDamage = builder.defineInRange("Damage of the Pink Tourmaline Shovel", 10, 0, Integer.MAX_VALUE);
		pinkTourmHoeDamage = builder.defineInRange("Damage of the Pink Tourmaline Hoe", 1, 0, Integer.MAX_VALUE);
		builder.pop();
		
		builder.push("Copper Weapons/Tools");
		copperSwordDamage = builder.defineInRange("Damage of the Copper Sword", 5, 0, Integer.MAX_VALUE);
		copperAxeDamage = builder.defineInRange("Damage of the Copper Axe", 9, 0, Integer.MAX_VALUE);
		copperPickaxeDamage = builder.defineInRange("Damage of the Copper Pickaxe", 3, 0, Integer.MAX_VALUE);
		copperShovelDamage = builder.defineInRange("Damage of the Copper Shovel", 4, 0, Integer.MAX_VALUE);
		copperHoeDamage = builder.defineInRange("Damage of the Copper Hoe", 1, 0, Integer.MAX_VALUE);
		builder.pop();
		
		builder.push("Tin Weapons/Tools");
		tinSwordDamage = builder.defineInRange("Damage of the Tin Sword", 6, 0, Integer.MAX_VALUE);
		tinAxeDamage = builder.defineInRange("Damage of the Tin Axe", 9, 0, Integer.MAX_VALUE);
		tinPickaxeDamage = builder.defineInRange("Damage of the Tin Pickaxe", 4, 0, Integer.MAX_VALUE);
		tinShovelDamage = builder.defineInRange("Damage of the Tin Shovel", 4, 0, Integer.MAX_VALUE);
		tinHoeDamage = builder.defineInRange("Damage of the Tin Hoe", 1, 0, Integer.MAX_VALUE);
		builder.pop();
		
		builder.push("Silver Weapons/Tools");
		silverSwordDamage = builder.defineInRange("Damage of the Silver Sword", 6, 0, Integer.MAX_VALUE);
		silverAxeDamage = builder.defineInRange("Damage of the Silver Axe", 9, 0, Integer.MAX_VALUE);
		silverPickaxeDamage = builder.defineInRange("Damage of the Silver Pickaxe", 4, 0, Integer.MAX_VALUE);
		silverShovelDamage = builder.defineInRange("Damage of the Silver Shovel", 5, 0, Integer.MAX_VALUE);
		silverHoeDamage = builder.defineInRange("Damage of the Silver Hoe", 1, 0, Integer.MAX_VALUE);
		builder.pop();
		
		builder.push("Platinum Weapons/Tools");
		platinumSwordDamage = builder.defineInRange("Damage of the Platinum Sword", 10, 0, Integer.MAX_VALUE);
		platinumAxeDamage = builder.defineInRange("Damage of the Platinum Axe", 12, 0, Integer.MAX_VALUE);
		platinumPickaxeDamage = builder.defineInRange("Damage of the Platinum Pickaxe", 8, 0, Integer.MAX_VALUE);
		platinumShovelDamage = builder.defineInRange("Damage of the Platinum Shovel", 9, 0, Integer.MAX_VALUE);
		platinumHoeDamage = builder.defineInRange("Damage of the Platinum Hoe", 1, 0, Integer.MAX_VALUE);
		builder.pop();
		
		builder.push("Misc Weapons/Tools");
		nightmareSwordDamage = builder.defineInRange("Damage of the Nightmare Sword", 30, 0, Integer.MAX_VALUE);
		basiliskSwordDamage = builder.defineInRange("Damage of the Basilisk Sword", 25, 0, Integer.MAX_VALUE);
		experienceSwordDamage = builder.defineInRange("Damage of the Experience Sword", 10, 0, Integer.MAX_VALUE);
		poisonSwordDamage = builder.defineInRange("Damage of the Poison Sword", 10, 0, Integer.MAX_VALUE);
		ratSwordDamage = builder.defineInRange("Damage of the Rat Sword", 10, 0, Integer.MAX_VALUE);
		fairySwordDamage = builder.defineInRange("Damage of the Fairy Sword", 10, 0, Integer.MAX_VALUE);
		mantisClawDamage = builder.defineInRange("Damage of the Mantis Claw", 10, 0, Integer.MAX_VALUE);
		bigHammerDamage = builder.defineInRange("Damage of the Big Hammer", 15, 0, Integer.MAX_VALUE);
		prismaticReaperDamage = builder.defineInRange("Damage of the Prismatic Reaper", 29, 0, Integer.MAX_VALUE);
		builder.pop();
		
		builder.push("Big Weapons/Tools");
		attitudeAdjusterDamage = builder.defineInRange("Damage of the Attitude Adjuster", 65, 0, Integer.MAX_VALUE);
		berthaDamage = builder.defineInRange("Damage of the Big Bertha", 500, 0, Integer.MAX_VALUE);
		battleAxeDamage = builder.defineInRange("Damage of the Battle Axe", 84, 0, Integer.MAX_VALUE);
		queenBattleAxeDamage = builder.defineInRange("Damage of the Queen Scale Battle Axe", 666, 0, Integer.MAX_VALUE);
		royalGuardianSwordDamage = builder.defineInRange("Damage of the Royal Guardian Sword", 750, 0, Integer.MAX_VALUE);
		slayerChainsawDamage = builder.defineInRange("Damage of the Slayer Chainsaw", 65, 0, Integer.MAX_VALUE);
		builder.pop(2);
		
		builder.push("Server Management/Logging");
		modServerPermission = builder.comment("Allow Chaos Awakens to manipulate server settings such as enabling flight.")
				.define("Chaos Awakens has OP server permissions", true);
		debugLogging = builder.comment("Debug CA events on the server while it's running.")
				.define("Debug logging", true);
		lenientAssetEnforcement = builder.comment("If set to true, dedicated servers will not throw an exception (crash) if there's a mismatch between metadata " +
						"(animation length, name, loop type). Tinker at your own risk! "
				+ "(Only applies to dedicated servers).")
				.define("Lenient Asset Enforcement", true);
		builder.pop();
	}
}
