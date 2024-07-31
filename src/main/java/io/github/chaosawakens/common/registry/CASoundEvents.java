package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CASoundEvents {
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ChaosAwakens.MODID);

	public static final RegistryObject<SoundEvent> ROBO_SHOOT = SOUND_EVENTS.register("entity.robo.shoot", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo.shoot")));
	public static final RegistryObject<SoundEvent> ROBO_HURT = SOUND_EVENTS.register("entity.robo.hurt", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo.hurt")));
	public static final RegistryObject<SoundEvent> ROBO_DEATH = SOUND_EVENTS.register("entity.robo.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo.death")));

	// Robo Jeffery
	public static final RegistryObject<SoundEvent> ROBO_JEFFERY_IDLE = SOUND_EVENTS.register("entity.robo_jeffery.idle", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_jeffery.idle")));
	public static final RegistryObject<SoundEvent> ROBO_JEFFERY_WALK = SOUND_EVENTS.register("entity.robo_jeffery.walk", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_jeffery.walk")));
	public static final RegistryObject<SoundEvent> ROBO_JEFFERY_JEFFERY_PUNCH = SOUND_EVENTS.register("entity.robo_jeffery.jeffery_punch", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_jeffery.jeffery_punch")));
	public static final RegistryObject<SoundEvent> ROBO_JEFFERY_SEISMIC_SLAM = SOUND_EVENTS.register("entity.robo_jeffery.seismic_slam", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_jeffery.seismic_slam")));
	public static final RegistryObject<SoundEvent> ROBO_JEFFERY_PISTON_LEAP_LAUNCH = SOUND_EVENTS.register("entity.robo_jeffery.piston_leap_launch", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_jeffery.piston_leap_launch")));
	public static final RegistryObject<SoundEvent> ROBO_JEFFERY_PISTON_LEAP_LAND = SOUND_EVENTS.register("entity.robo_jeffery.piston_leap_land", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_jeffery.piston_leap_land")));
	public static final RegistryObject<SoundEvent> ROBO_JEFFERY_DEATH = SOUND_EVENTS.register("entity.robo_jeffery.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_jeffery.death")));
	public static final RegistryObject<SoundEvent> ROBO_JEFFERY_DAMAGE_V1 = SOUND_EVENTS.register("entity.robo_jeffery.damage_v1", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_jeffery.damage_v1")));
	public static final RegistryObject<SoundEvent> ROBO_JEFFERY_DAMAGE_V2 = SOUND_EVENTS.register("entity.robo_jeffery.damage_v2", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_jeffery.damage_v2")));
	public static final RegistryObject<SoundEvent> ROBO_JEFFERY_DAMAGE_V3 = SOUND_EVENTS.register("entity.robo_jeffery.damage_v3", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_jeffery.damage_v3")));
	public static final RegistryObject<SoundEvent> ROBO_JEFFERY_DAMAGE_V4 = SOUND_EVENTS.register("entity.robo_jeffery.damage_v4", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_jeffery.damage_v4")));

	// Robo Pounder
	public static final RegistryObject<SoundEvent> ROBO_POUNDER_IDLE = SOUND_EVENTS.register("entity.robo_pounder.idle", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_pounder.idle")));
	public static final RegistryObject<SoundEvent> ROBO_POUNDER_WALK = SOUND_EVENTS.register("entity.robo_pounder.walk", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_pounder.walk")));
	public static final RegistryObject<SoundEvent> ROBO_POUNDER_TAUNT = SOUND_EVENTS.register("entity.robo_pounder.taunt", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_pounder.taunt")));
	public static final RegistryObject<SoundEvent> ROBO_POUNDER_PISTON_PUNCH = SOUND_EVENTS.register("entity.robo_pounder.piston_punch", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_pounder.piston_punch")));
	public static final RegistryObject<SoundEvent> ROBO_POUNDER_SIDE_SWEEP = SOUND_EVENTS.register("entity.robo_pounder.side_sweep", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_pounder.side_sweep")));
	public static final RegistryObject<SoundEvent> ROBO_POUNDER_DYSON_DASH = SOUND_EVENTS.register("entity.robo_pounder.dyson_dash", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_pounder.dyson_dash")));
	public static final RegistryObject<SoundEvent> ROBO_POUNDER_GROUND_SLAM = SOUND_EVENTS.register("entity.robo_pounder.ground_slam", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_pounder.ground_slam")));
	public static final RegistryObject<SoundEvent> ROBO_POUNDER_DOME_STOMP = SOUND_EVENTS.register("entity.robo_pounder.dome_stomp", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_pounder.dome_stomp")));
	public static final RegistryObject<SoundEvent> ROBO_POUNDER_RAGE_RUN_WINDUP = SOUND_EVENTS.register("entity.robo_pounder.rage_run_windup", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_pounder.rage_run_windup")));
	public static final RegistryObject<SoundEvent> ROBO_POUNDER_RAGE_RUN = SOUND_EVENTS.register("entity.robo_pounder.rage_run", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_pounder.rage_run")));
	public static final RegistryObject<SoundEvent> ROBO_POUNDER_RAGE_RUN_COOLDOWN = SOUND_EVENTS.register("entity.robo_pounder.rage_run_cooldown", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_pounder.rage_run_cooldown")));
	public static final RegistryObject<SoundEvent> ROBO_POUNDER_RAGE_RUN_RESTART = SOUND_EVENTS.register("entity.robo_pounder.rage_run_restart", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_pounder.rage_run_restart")));
	public static final RegistryObject<SoundEvent> ROBO_POUNDER_RAGE_RUN_CRASH = SOUND_EVENTS.register("entity.robo_pounder.rage_run_crash", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_pounder.rage_run_restart")));
	public static final RegistryObject<SoundEvent> ROBO_POUNDER_RAGE_RUN_CRASH_RESTART = SOUND_EVENTS.register("entity.robo_pounder.rage_run_crash_restart", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_pounder.rage_run_restart")));
	public static final RegistryObject<SoundEvent> ROBO_POUNDER_DEATH = SOUND_EVENTS.register("entity.robo_pounder.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_pounder.death")));
	public static final RegistryObject<SoundEvent> ROBO_POUNDER_CRITICAL_DAMAGE = SOUND_EVENTS.register("entity.robo_pounder.critical_damage", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_pounder.critical_damage")));
	public static final RegistryObject<SoundEvent> ROBO_POUNDER_CRITICAL_DAMAGE_RADIO = SOUND_EVENTS.register("entity.robo_pounder.critical_damage_radio", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_pounder.critical_damage_radio")));
	public static final RegistryObject<SoundEvent> ROBO_POUNDER_DAMAGE_V1 = SOUND_EVENTS.register("entity.robo_pounder.damage_v1", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_pounder.damage_v1")));
	public static final RegistryObject<SoundEvent> ROBO_POUNDER_DAMAGE_V2 = SOUND_EVENTS.register("entity.robo_pounder.damage_v2", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_pounder.damage_v2")));
	public static final RegistryObject<SoundEvent> ROBO_POUNDER_DAMAGE_V3 = SOUND_EVENTS.register("entity.robo_pounder.damage_v3", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_pounder.damage_v3")));
	public static final RegistryObject<SoundEvent> ROBO_POUNDER_DAMAGE_V4 = SOUND_EVENTS.register("entity.robo_pounder.damage_v4", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_pounder.damage_v4")));

	// Robo Warrior
	public static final RegistryObject<SoundEvent> ROBO_WARRIOR_IDLE = SOUND_EVENTS.register("entity.robo_warrior.idle", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_warrior.idle")));
	public static final RegistryObject<SoundEvent> ROBO_WARRIOR_WALK = SOUND_EVENTS.register("entity.robo_warrior.walk", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_warrior.walk")));
	public static final RegistryObject<SoundEvent> ROBO_WARRIOR_DEATH = SOUND_EVENTS.register("entity.robo_warrior.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_warrior.death")));
	public static final RegistryObject<SoundEvent> ROBO_WARRIOR_DAMAGE = SOUND_EVENTS.register("entity.robo_warrior.damage", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_warrior.damage")));
	public static final RegistryObject<SoundEvent> ROBO_WARRIOR_UPPERCUT = SOUND_EVENTS.register("entity.robo_warrior.uppercut", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_warrior.uppercut")));
	public static final RegistryObject<SoundEvent> ROBO_WARRIOR_LASER_BURST = SOUND_EVENTS.register("entity.robo_warrior.laser_burst", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_warrior.laser_burst")));
	public static final RegistryObject<SoundEvent> ROBO_WARRIOR_CHARGED_SHOT = SOUND_EVENTS.register("entity.robo_warrior.charged_shot", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_warrior.charged_shot")));
	public static final RegistryObject<SoundEvent> ROBO_WARRIOR_SHIELD_ACTIVATION = SOUND_EVENTS.register("entity.robo_warrior.shield_activation", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_warrior.shield_activation")));
	public static final RegistryObject<SoundEvent> ROBO_WARRIOR_SHIELD_ACTIVATED = SOUND_EVENTS.register("entity.robo_warrior.shield_activated", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_warrior.shield_activated")));
	public static final RegistryObject<SoundEvent> ROBO_WARRIOR_SHIELD_DEACTIVATION = SOUND_EVENTS.register("entity.robo_warrior.shield_deactivation", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_warrior.shield_deactivation")));
	public static final RegistryObject<SoundEvent> ROBO_WARRIOR_SHIELD_DESTROYED = SOUND_EVENTS.register("entity.robo_warrior.shield_destroyed", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_warrior.shield_destroyed")));

	// Robo Laser
	public static final RegistryObject<SoundEvent> ROBO_LASER_TRAVEL = SOUND_EVENTS.register("entity.robo_laser.travel", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_laser.travel")));
	public static final RegistryObject<SoundEvent> ROBO_LASER_HIT = SOUND_EVENTS.register("entity.robo_laser.hit", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_laser.hit")));

	// Robo Ray
	public static final RegistryObject<SoundEvent> ROBO_RAY_TRAVEL = SOUND_EVENTS.register("entity.robo_ray.travel", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_ray.travel")));
	public static final RegistryObject<SoundEvent> ROBO_RAY_HIT = SOUND_EVENTS.register("entity.robo_ray.hit", () -> new SoundEvent(ChaosAwakens.prefix("entity.robo_ray.hit")));

	// Emerald Gator
	public static final RegistryObject<SoundEvent> EMERALD_GATOR_WALK = SOUND_EVENTS.register("entity.emerald_gator.walk", () -> new SoundEvent(ChaosAwakens.prefix("entity.emerald_gator.walk")));
	public static final RegistryObject<SoundEvent> EMERALD_GATOR_ATTACK = SOUND_EVENTS.register("entity.emerald_gator.attack", () -> new SoundEvent(ChaosAwakens.prefix("entity.emerald_gator.attack")));
	public static final RegistryObject<SoundEvent> EMERALD_GATOR_HURT = SOUND_EVENTS.register("entity.emerald_gator.hurt", () -> new SoundEvent(ChaosAwakens.prefix("entity.emerald_gator.hurt")));
	public static final RegistryObject<SoundEvent> EMERALD_GATOR_DEATH = SOUND_EVENTS.register("entity.emerald_gator.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.emerald_gator.death")));

	// Crystal Gator
	public static final RegistryObject<SoundEvent> CRYSTAL_GATOR_WALK = SOUND_EVENTS.register("entity.crystal_gator.walk", () -> new SoundEvent(ChaosAwakens.prefix("entity.crystal_gator.walk")));
	public static final RegistryObject<SoundEvent> CRYSTAL_GATOR_ATTACK = SOUND_EVENTS.register("entity.crystal_gator.attack", () -> new SoundEvent(ChaosAwakens.prefix("entity.crystal_gator.attack")));
	public static final RegistryObject<SoundEvent> CRYSTAL_GATOR_HURT = SOUND_EVENTS.register("entity.crystal_gator.hurt", () -> new SoundEvent(ChaosAwakens.prefix("entity.crystal_gator.hurt")));
	public static final RegistryObject<SoundEvent> CRYSTAL_GATOR_DEATH = SOUND_EVENTS.register("entity.crystal_gator.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.crystal_gator.death")));

	// Hercules Beetle
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_DOCILE = SOUND_EVENTS.register("entity.hercules_beetle.docile", () -> new SoundEvent(ChaosAwakens.prefix("entity.hercules_beetle.docile")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_AWAKEN = SOUND_EVENTS.register("entity.hercules_beetle.awaken", () -> new SoundEvent(ChaosAwakens.prefix("entity.hercules_beetle.awaken")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_IDLE = SOUND_EVENTS.register("entity.hercules_beetle.idle", () -> new SoundEvent(ChaosAwakens.prefix("entity.hercules_beetle.idle")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_WALK = SOUND_EVENTS.register("entity.hercules_beetle.walk", () -> new SoundEvent(ChaosAwakens.prefix("entity.hercules_beetle.walk")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_FLY = SOUND_EVENTS.register("entity.hercules_beetle.fly", () -> new SoundEvent(ChaosAwakens.prefix("entity.hercules_beetle.fly")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_DEATH = SOUND_EVENTS.register("entity.hercules_beetle.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.hercules_beetle.death")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_DEATH_MIDAIR = SOUND_EVENTS.register("entity.hercules_beetle.death_midair", () -> new SoundEvent(ChaosAwakens.prefix("entity.hercules_beetle.death_midair")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_DEATH_MIDAIR_FALLING = SOUND_EVENTS.register("entity.hercules_beetle.death_midair_falling", () -> new SoundEvent(ChaosAwakens.prefix("entity.hercules_beetle.death_midair_falling")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_DEATH_MIDAIR_FALLEN = SOUND_EVENTS.register("entity.hercules_beetle.death_midair_fallen", () -> new SoundEvent(ChaosAwakens.prefix("entity.hercules_beetle.death_midair_fallen")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_BEETLE_RAM = SOUND_EVENTS.register("entity.hercules_beetle.ram_attack", () -> new SoundEvent(ChaosAwakens.prefix("entity.hercules_beetle.ram_attack")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_GRAB = SOUND_EVENTS.register("entity.hercules_beetle.grab", () -> new SoundEvent(ChaosAwakens.prefix("entity.hercules_beetle.grab")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_MAD_MUNCH = SOUND_EVENTS.register("entity.hercules_beetle.munch_attack", () -> new SoundEvent(ChaosAwakens.prefix("entity.hercules_beetle.munch_attack")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_LEAP = SOUND_EVENTS.register("entity.hercules_beetle.leap", () -> new SoundEvent(ChaosAwakens.prefix("entity.hercules_beetle.leap")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_GROUND_SLAM = SOUND_EVENTS.register("entity.hercules_beetle.ground_slam", () -> new SoundEvent(ChaosAwakens.prefix("entity.hercules_beetle.ground_slam")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_GROUND_SLAM_HIT = SOUND_EVENTS.register("entity.hercules_beetle.ground_slam_hit", () -> new SoundEvent(ChaosAwakens.prefix("entity.hercules_beetle.ground_slam_hit")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_ROAR = SOUND_EVENTS.register("entity.hercules_beetle.roar", () -> new SoundEvent(ChaosAwakens.prefix("entity.hercules_beetle.roar")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_DAMAGE_CRITICAL = SOUND_EVENTS.register("entity.hercules_beetle.damage_critical", () -> new SoundEvent(ChaosAwakens.prefix("entity.hercules_beetle.damage_critical")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_DAMAGE_DEFENSE = SOUND_EVENTS.register("entity.hercules_beetle.damage_defense", () -> new SoundEvent(ChaosAwakens.prefix("entity.hercules_beetle.damage_defense")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_DAMAGE_EVASIVE = SOUND_EVENTS.register("entity.hercules_beetle.damage_evasive", () -> new SoundEvent(ChaosAwakens.prefix("entity.hercules_beetle.damage_evasive")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_DAMAGE_OFFENSE = SOUND_EVENTS.register("entity.hercules_beetle.damage_offense", () -> new SoundEvent(ChaosAwakens.prefix("entity.hercules_beetle.damage_offense")));

	// Ent Sounds
	public static final RegistryObject<SoundEvent> ENT_IDLE = SOUND_EVENTS.register("entity.ent.idle", () -> new SoundEvent(ChaosAwakens.prefix("entity.ent.idle")));
	public static final RegistryObject<SoundEvent> ENT_WALK = SOUND_EVENTS.register("entity.ent.walk", () -> new SoundEvent(ChaosAwakens.prefix("entity.ent.walk")));
	public static final RegistryObject<SoundEvent> ENT_TREE_PUNCH = SOUND_EVENTS.register("entity.ent.tree_punch", () -> new SoundEvent(ChaosAwakens.prefix("entity.ent.tree_punch")));
	public static final RegistryObject<SoundEvent> ENT_ENT_SMASH = SOUND_EVENTS.register("entity.ent.ent_smash", () -> new SoundEvent(ChaosAwakens.prefix("entity.ent.ent_smash")));
	public static final RegistryObject<SoundEvent> ENT_DEATH = SOUND_EVENTS.register("entity.ent.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.ent.death")));
	public static final RegistryObject<SoundEvent> ENT_DAMAGE = SOUND_EVENTS.register("entity.ent.damage", () -> new SoundEvent(ChaosAwakens.prefix("entity.ent.damage")));
	public static final RegistryObject<SoundEvent> ACACIA_ENT_IDLE = SOUND_EVENTS.register("entity.acacia_ent.idle", () -> new SoundEvent(ChaosAwakens.prefix("entity.acacia_ent.idle")));
	public static final RegistryObject<SoundEvent> ACACIA_ENT_WALK = SOUND_EVENTS.register("entity.acacia_ent.walk", () -> new SoundEvent(ChaosAwakens.prefix("entity.acacia_ent.walk")));
	public static final RegistryObject<SoundEvent> ACACIA_ENT_TREE_PUNCH = SOUND_EVENTS.register("entity.acacia_ent.tree_punch", () -> new SoundEvent(ChaosAwakens.prefix("entity.acacia_ent.tree_punch")));
	public static final RegistryObject<SoundEvent> ACACIA_ENT_ENT_SMASH = SOUND_EVENTS.register("entity.acacia_ent.ent_smash", () -> new SoundEvent(ChaosAwakens.prefix("entity.acacia_ent.ent_smash")));
	public static final RegistryObject<SoundEvent> ACACIA_ENT_DEATH = SOUND_EVENTS.register("entity.acacia_ent.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.acacia_ent.death")));
	public static final RegistryObject<SoundEvent> ACACIA_ENT_DAMAGE = SOUND_EVENTS.register("entity.acacia_ent.damage", () -> new SoundEvent(ChaosAwakens.prefix("entity.acacia_ent.damage")));
	public static final RegistryObject<SoundEvent> APPLE_ENT_IDLE = SOUND_EVENTS.register("entity.apple_ent.idle", () -> new SoundEvent(ChaosAwakens.prefix("entity.apple_ent.idle")));
	public static final RegistryObject<SoundEvent> APPLE_ENT_WALK = SOUND_EVENTS.register("entity.apple_ent.walk", () -> new SoundEvent(ChaosAwakens.prefix("entity.apple_ent.walk")));
	public static final RegistryObject<SoundEvent> APPLE_ENT_TREE_PUNCH = SOUND_EVENTS.register("entity.apple_ent.tree_punch", () -> new SoundEvent(ChaosAwakens.prefix("entity.apple_ent.tree_punch")));
	public static final RegistryObject<SoundEvent> APPLE_ENT_ENT_SMASH = SOUND_EVENTS.register("entity.apple_ent.ent_smash", () -> new SoundEvent(ChaosAwakens.prefix("entity.apple_ent.ent_smash")));
	public static final RegistryObject<SoundEvent> APPLE_ENT_DEATH = SOUND_EVENTS.register("entity.apple_ent.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.apple_ent.death")));
	public static final RegistryObject<SoundEvent> APPLE_ENT_DAMAGE = SOUND_EVENTS.register("entity.apple_ent.damage", () -> new SoundEvent(ChaosAwakens.prefix("entity.apple_ent.damage")));
	public static final RegistryObject<SoundEvent> BIRCH_ENT_IDLE = SOUND_EVENTS.register("entity.birch_ent.idle", () -> new SoundEvent(ChaosAwakens.prefix("entity.birch_ent.idle")));
	public static final RegistryObject<SoundEvent> BIRCH_ENT_WALK = SOUND_EVENTS.register("entity.birch_ent.walk", () -> new SoundEvent(ChaosAwakens.prefix("entity.birch_ent.walk")));
	public static final RegistryObject<SoundEvent> BIRCH_ENT_TREE_PUNCH = SOUND_EVENTS.register("entity.birch_ent.tree_punch", () -> new SoundEvent(ChaosAwakens.prefix("entity.birch_ent.tree_punch")));
	public static final RegistryObject<SoundEvent> BIRCH_ENT_ENT_SMASH = SOUND_EVENTS.register("entity.birch_ent.ent_smash", () -> new SoundEvent(ChaosAwakens.prefix("entity.birch_ent.ent_smash")));
	public static final RegistryObject<SoundEvent> BIRCH_ENT_DEATH = SOUND_EVENTS.register("entity.birch_ent.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.birch_ent.death")));
	public static final RegistryObject<SoundEvent> BIRCH_ENT_DAMAGE = SOUND_EVENTS.register("entity.birch_ent.damage", () -> new SoundEvent(ChaosAwakens.prefix("entity.birch_ent.damage")));
	public static final RegistryObject<SoundEvent> CHERRY_ENT_IDLE = SOUND_EVENTS.register("entity.cherry_ent.idle", () -> new SoundEvent(ChaosAwakens.prefix("entity.cherry_ent.idle")));
	public static final RegistryObject<SoundEvent> CHERRY_ENT_WALK = SOUND_EVENTS.register("entity.cherry_ent.walk", () -> new SoundEvent(ChaosAwakens.prefix("entity.cherry_ent.walk")));
	public static final RegistryObject<SoundEvent> CHERRY_ENT_TREE_PUNCH = SOUND_EVENTS.register("entity.cherry_ent.tree_punch", () -> new SoundEvent(ChaosAwakens.prefix("entity.cherry_ent.tree_punch")));
	public static final RegistryObject<SoundEvent> CHERRY_ENT_ENT_SMASH = SOUND_EVENTS.register("entity.cherry_ent.ent_smash", () -> new SoundEvent(ChaosAwakens.prefix("entity.cherry_ent.ent_smash")));
	public static final RegistryObject<SoundEvent> CHERRY_ENT_DEATH = SOUND_EVENTS.register("entity.cherry_ent.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.cherry_ent.death")));
	public static final RegistryObject<SoundEvent> CHERRY_ENT_DAMAGE = SOUND_EVENTS.register("entity.cherry_ent.damage", () -> new SoundEvent(ChaosAwakens.prefix("entity.cherry_ent.damage")));
	public static final RegistryObject<SoundEvent> CRIMSON_ENT_IDLE = SOUND_EVENTS.register("entity.crimson_ent.idle", () -> new SoundEvent(ChaosAwakens.prefix("entity.crimson_ent.idle")));
	public static final RegistryObject<SoundEvent> CRIMSON_ENT_WALK = SOUND_EVENTS.register("entity.crimson_ent.walk", () -> new SoundEvent(ChaosAwakens.prefix("entity.crimson_ent.walk")));
	public static final RegistryObject<SoundEvent> CRIMSON_ENT_TREE_PUNCH = SOUND_EVENTS.register("entity.crimson_ent.tree_punch", () -> new SoundEvent(ChaosAwakens.prefix("entity.crimson_ent.tree_punch")));
	public static final RegistryObject<SoundEvent> CRIMSON_ENT_ENT_SMASH = SOUND_EVENTS.register("entity.crimson_ent.ent_smash", () -> new SoundEvent(ChaosAwakens.prefix("entity.crimson_ent.ent_smash")));
	public static final RegistryObject<SoundEvent> CRIMSON_ENT_DEATH = SOUND_EVENTS.register("entity.crimson_ent.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.crimson_ent.death")));
	public static final RegistryObject<SoundEvent> CRIMSON_ENT_DAMAGE = SOUND_EVENTS.register("entity.crimson_ent.damage", () -> new SoundEvent(ChaosAwakens.prefix("entity.crimson_ent.damage")));
	public static final RegistryObject<SoundEvent> DARK_OAK_ENT_IDLE = SOUND_EVENTS.register("entity.dark_oak_ent.idle", () -> new SoundEvent(ChaosAwakens.prefix("entity.dark_oak_ent.idle")));
	public static final RegistryObject<SoundEvent> DARK_OAK_ENT_WALK = SOUND_EVENTS.register("entity.dark_oak_ent.walk", () -> new SoundEvent(ChaosAwakens.prefix("entity.dark_oak_ent.walk")));
	public static final RegistryObject<SoundEvent> DARK_OAK_ENT_TREE_PUNCH = SOUND_EVENTS.register("entity.dark_oak_ent.tree_punch", () -> new SoundEvent(ChaosAwakens.prefix("entity.dark_oak_ent.tree_punch")));
	public static final RegistryObject<SoundEvent> DARK_OAK_ENT_ENT_SMASH = SOUND_EVENTS.register("entity.dark_oak_ent.ent_smash", () -> new SoundEvent(ChaosAwakens.prefix("entity.dark_oak_ent.ent_smash")));
	public static final RegistryObject<SoundEvent> DARK_OAK_ENT_DEATH = SOUND_EVENTS.register("entity.dark_oak_ent.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.dark_oak_ent.death")));
	public static final RegistryObject<SoundEvent> DARK_OAK_ENT_DAMAGE = SOUND_EVENTS.register("entity.dark_oak_ent.damage", () -> new SoundEvent(ChaosAwakens.prefix("entity.dark_oak_ent.damage")));
	public static final RegistryObject<SoundEvent> JUNGLE_ENT_IDLE = SOUND_EVENTS.register("entity.jungle_ent.idle", () -> new SoundEvent(ChaosAwakens.prefix("entity.jungle_ent.idle")));
	public static final RegistryObject<SoundEvent> JUNGLE_ENT_WALK = SOUND_EVENTS.register("entity.jungle_ent.walk", () -> new SoundEvent(ChaosAwakens.prefix("entity.jungle_ent.walk")));
	public static final RegistryObject<SoundEvent> JUNGLE_ENT_TREE_PUNCH = SOUND_EVENTS.register("entity.jungle_ent.tree_punch", () -> new SoundEvent(ChaosAwakens.prefix("entity.jungle_ent.tree_punch")));
	public static final RegistryObject<SoundEvent> JUNGLE_ENT_ENT_SMASH = SOUND_EVENTS.register("entity.jungle_ent.ent_smash", () -> new SoundEvent(ChaosAwakens.prefix("entity.jungle_ent.ent_smash")));
	public static final RegistryObject<SoundEvent> JUNGLE_ENT_DEATH = SOUND_EVENTS.register("entity.jungle_ent.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.jungle_ent.death")));
	public static final RegistryObject<SoundEvent> JUNGLE_ENT_DAMAGE = SOUND_EVENTS.register("entity.jungle_ent.damage", () -> new SoundEvent(ChaosAwakens.prefix("entity.jungle_ent.damage")));
	public static final RegistryObject<SoundEvent> OAK_ENT_IDLE = SOUND_EVENTS.register("entity.oak_ent.idle", () -> new SoundEvent(ChaosAwakens.prefix("entity.oak_ent.idle")));
	public static final RegistryObject<SoundEvent> OAK_ENT_WALK = SOUND_EVENTS.register("entity.oak_ent.walk", () -> new SoundEvent(ChaosAwakens.prefix("entity.oak_ent.walk")));
	public static final RegistryObject<SoundEvent> OAK_ENT_TREE_PUNCH = SOUND_EVENTS.register("entity.oak_ent.tree_punch", () -> new SoundEvent(ChaosAwakens.prefix("entity.oak_ent.tree_punch")));
	public static final RegistryObject<SoundEvent> OAK_ENT_ENT_SMASH = SOUND_EVENTS.register("entity.oak_ent.ent_smash", () -> new SoundEvent(ChaosAwakens.prefix("entity.oak_ent.ent_smash")));
	public static final RegistryObject<SoundEvent> OAK_ENT_DEATH = SOUND_EVENTS.register("entity.oak_ent.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.oak_ent.death")));
	public static final RegistryObject<SoundEvent> OAK_ENT_DAMAGE = SOUND_EVENTS.register("entity.oak_ent.damage", () -> new SoundEvent(ChaosAwakens.prefix("entity.oak_ent.damage")));
	public static final RegistryObject<SoundEvent> PEACH_ENT_IDLE = SOUND_EVENTS.register("entity.peach_ent.idle", () -> new SoundEvent(ChaosAwakens.prefix("entity.peach_ent.idle")));
	public static final RegistryObject<SoundEvent> PEACH_ENT_WALK = SOUND_EVENTS.register("entity.peach_ent.walk", () -> new SoundEvent(ChaosAwakens.prefix("entity.peach_ent.walk")));
	public static final RegistryObject<SoundEvent> PEACH_ENT_TREE_PUNCH = SOUND_EVENTS.register("entity.peach_ent.tree_punch", () -> new SoundEvent(ChaosAwakens.prefix("entity.peach_ent.tree_punch")));
	public static final RegistryObject<SoundEvent> PEACH_ENT_ENT_SMASH = SOUND_EVENTS.register("entity.peach_ent.ent_smash", () -> new SoundEvent(ChaosAwakens.prefix("entity.peach_ent.ent_smash")));
	public static final RegistryObject<SoundEvent> PEACH_ENT_DEATH = SOUND_EVENTS.register("entity.peach_ent.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.peach_ent.death")));
	public static final RegistryObject<SoundEvent> PEACH_ENT_DAMAGE = SOUND_EVENTS.register("entity.peach_ent.damage", () -> new SoundEvent(ChaosAwakens.prefix("entity.peach_ent.damage")));
	public static final RegistryObject<SoundEvent> SKYWOOD_ENT_IDLE = SOUND_EVENTS.register("entity.skywood_ent.idle", () -> new SoundEvent(ChaosAwakens.prefix("entity.skywood_ent.idle")));
	public static final RegistryObject<SoundEvent> SKYWOOD_ENT_WALK = SOUND_EVENTS.register("entity.skywood_ent.walk", () -> new SoundEvent(ChaosAwakens.prefix("entity.skywood_ent.walk")));
	public static final RegistryObject<SoundEvent> SKYWOOD_ENT_TREE_PUNCH = SOUND_EVENTS.register("entity.skywood_ent.tree_punch", () -> new SoundEvent(ChaosAwakens.prefix("entity.skywood_ent.tree_punch")));
	public static final RegistryObject<SoundEvent> SKYWOOD_ENT_ENT_SMASH = SOUND_EVENTS.register("entity.skywood_ent.ent_smash", () -> new SoundEvent(ChaosAwakens.prefix("entity.skywood_ent.ent_smash")));
	public static final RegistryObject<SoundEvent> SKYWOOD_ENT_DEATH = SOUND_EVENTS.register("entity.skywood_ent.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.skywood_ent.death")));
	public static final RegistryObject<SoundEvent> SKYWOOD_ENT_DAMAGE = SOUND_EVENTS.register("entity.skywood_ent.damage", () -> new SoundEvent(ChaosAwakens.prefix("entity.skywood_ent.damage")));
	public static final RegistryObject<SoundEvent> SPRUCE_ENT_IDLE = SOUND_EVENTS.register("entity.spruce_ent.idle", () -> new SoundEvent(ChaosAwakens.prefix("entity.spruce_ent.idle")));
	public static final RegistryObject<SoundEvent> SPRUCE_ENT_WALK = SOUND_EVENTS.register("entity.spruce_ent.walk", () -> new SoundEvent(ChaosAwakens.prefix("entity.spruce_ent.walk")));
	public static final RegistryObject<SoundEvent> SPRUCE_ENT_TREE_PUNCH = SOUND_EVENTS.register("entity.spruce_ent.tree_punch", () -> new SoundEvent(ChaosAwakens.prefix("entity.spruce_ent.tree_punch")));
	public static final RegistryObject<SoundEvent> SPRUCE_ENT_ENT_SMASH = SOUND_EVENTS.register("entity.spruce_ent.ent_smash", () -> new SoundEvent(ChaosAwakens.prefix("entity.spruce_ent.ent_smash")));
	public static final RegistryObject<SoundEvent> SPRUCE_ENT_DEATH = SOUND_EVENTS.register("entity.spruce_ent.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.spruce_ent.death")));
	public static final RegistryObject<SoundEvent> SPRUCE_ENT_DAMAGE = SOUND_EVENTS.register("entity.spruce_ent.damage", () -> new SoundEvent(ChaosAwakens.prefix("entity.spruce_ent.damage")));
	public static final RegistryObject<SoundEvent> WARPED_ENT_IDLE = SOUND_EVENTS.register("entity.warped_ent.idle", () -> new SoundEvent(ChaosAwakens.prefix("entity.warped_ent.idle")));
	public static final RegistryObject<SoundEvent> WARPED_ENT_WALK = SOUND_EVENTS.register("entity.warped_ent.walk", () -> new SoundEvent(ChaosAwakens.prefix("entity.warped_ent.walk")));
	public static final RegistryObject<SoundEvent> WARPED_ENT_TREE_PUNCH = SOUND_EVENTS.register("entity.warped_ent.tree_punch", () -> new SoundEvent(ChaosAwakens.prefix("entity.warped_ent.tree_punch")));
	public static final RegistryObject<SoundEvent> WARPED_ENT_ENT_SMASH = SOUND_EVENTS.register("entity.warped_ent.ent_smash", () -> new SoundEvent(ChaosAwakens.prefix("entity.warped_ent.ent_smash")));
	public static final RegistryObject<SoundEvent> WARPED_ENT_DEATH = SOUND_EVENTS.register("entity.warped_ent.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.warped_ent.death")));
	public static final RegistryObject<SoundEvent> WARPED_ENT_DAMAGE = SOUND_EVENTS.register("entity.warped_ent.damage", () -> new SoundEvent(ChaosAwakens.prefix("entity.warped_ent.damage")));
	public static final RegistryObject<SoundEvent> GINKGO_ENT_IDLE = SOUND_EVENTS.register("entity.ginkgo_ent.idle", () -> new SoundEvent(ChaosAwakens.prefix("entity.ginkgo_ent.idle")));
	public static final RegistryObject<SoundEvent> GINKGO_ENT_WALK = SOUND_EVENTS.register("entity.ginkgo_ent.walk", () -> new SoundEvent(ChaosAwakens.prefix("entity.ginkgo_ent.walk")));
	public static final RegistryObject<SoundEvent> GINKGO_ENT_TREE_PUNCH = SOUND_EVENTS.register("entity.ginkgo_ent.tree_punch", () -> new SoundEvent(ChaosAwakens.prefix("entity.ginkgo_ent.tree_punch")));
	public static final RegistryObject<SoundEvent> GINKGO_ENT_ENT_SMASH = SOUND_EVENTS.register("entity.ginkgo_ent.ent_smash", () -> new SoundEvent(ChaosAwakens.prefix("entity.ginkgo_ent.ent_smash")));
	public static final RegistryObject<SoundEvent> GINKGO_ENT_DEATH = SOUND_EVENTS.register("entity.ginkgo_ent.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.ginkgo_ent.death")));
	public static final RegistryObject<SoundEvent> GINKGO_ENT_DAMAGE = SOUND_EVENTS.register("entity.ginkgo_ent.damage", () -> new SoundEvent(ChaosAwakens.prefix("entity.ginkgo_ent.damage")));

	// Whale Sounds
	public static final RegistryObject<SoundEvent> WHALE_AMBIENT = SOUND_EVENTS.register("entity.whale.ambient", () -> new SoundEvent(ChaosAwakens.prefix("entity.whale.ambient")));
	public static final RegistryObject<SoundEvent> WHALE_HURT = SOUND_EVENTS.register("entity.whale.hurt", () -> new SoundEvent(ChaosAwakens.prefix("entity.whale.hurt")));
	public static final RegistryObject<SoundEvent> WHALE_DEATH = SOUND_EVENTS.register("entity.whale.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.whale.death")));

	public static final RegistryObject<SoundEvent> WASP_AMBIENT = SOUND_EVENTS.register("entity.wasp.ambient", () -> new SoundEvent(ChaosAwakens.prefix("entity.wasp.ambient")));

	// Sky Moss Carpets
	public static final RegistryObject<SoundEvent> SKY_MOSS_CARPET_BREAK = SOUND_EVENTS.register("block.sky_moss_carpet.break", () -> new SoundEvent(ChaosAwakens.prefix("block.sky_moss_carpet.break")));
	public static final RegistryObject<SoundEvent> SKY_MOSS_CARPET_FALL = SOUND_EVENTS.register("block.sky_moss_carpet.fall", () -> new SoundEvent(ChaosAwakens.prefix("block.sky_moss_carpet.fall")));
	public static final RegistryObject<SoundEvent> SKY_MOSS_CARPET_HIT = SOUND_EVENTS.register("block.sky_moss_carpet.hit", () -> new SoundEvent(ChaosAwakens.prefix("block.sky_moss_carpet.hit")));
	public static final RegistryObject<SoundEvent> SKY_MOSS_CARPET_PLACE = SOUND_EVENTS.register("block.sky_moss_carpet.place", () -> new SoundEvent(ChaosAwakens.prefix("block.sky_moss_carpet.place")));
	public static final RegistryObject<SoundEvent> SKY_MOSS_CARPET_STEP = SOUND_EVENTS.register("block.sky_moss_carpet.step", () -> new SoundEvent(ChaosAwakens.prefix("block.sky_moss_carpet.step")));
	public static final RegistryObject<SoundEvent> SKY_MOSS_BREAK = SOUND_EVENTS.register("block.sky_moss.break", () -> new SoundEvent(ChaosAwakens.prefix("block.sky_moss.break")));
	public static final RegistryObject<SoundEvent> SKY_MOSS_FALL = SOUND_EVENTS.register("block.sky_moss.fall", () -> new SoundEvent(ChaosAwakens.prefix("block.sky_moss.fall")));
	public static final RegistryObject<SoundEvent> SKY_MOSS_HIT = SOUND_EVENTS.register("block.sky_moss.hit", () -> new SoundEvent(ChaosAwakens.prefix("block.sky_moss.hit")));
	public static final RegistryObject<SoundEvent> SKY_MOSS_PLACE = SOUND_EVENTS.register("block.sky_moss.place", () -> new SoundEvent(ChaosAwakens.prefix("block.sky_moss.place")));
	public static final RegistryObject<SoundEvent> SKY_MOSS_STEP = SOUND_EVENTS.register("block.sky_moss.step", () -> new SoundEvent(ChaosAwakens.prefix("block.sky_moss.step")));
}
