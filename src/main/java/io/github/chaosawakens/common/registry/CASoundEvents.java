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

	// Emerald Gator
	public static final RegistryObject<SoundEvent> EMERALD_GATOR_WALK = SOUND_EVENTS.register("entity.emerald_gator.walk", () -> new SoundEvent(ChaosAwakens.prefix("entity.emerald_gator.walk")));
	public static final RegistryObject<SoundEvent> EMERALD_GATOR_ATTACK = SOUND_EVENTS.register("entity.emerald_gator.attack", () -> new SoundEvent(ChaosAwakens.prefix("entity.emerald_gator.attack")));
	public static final RegistryObject<SoundEvent> EMERALD_GATOR_HURT = SOUND_EVENTS.register("entity.emerald_gator.hurt", () -> new SoundEvent(ChaosAwakens.prefix("entity.emerald_gator.hurt")));
	public static final RegistryObject<SoundEvent> EMERALD_GATOR_DEATH = SOUND_EVENTS.register("entity.emerald_gator.death", () -> new SoundEvent(ChaosAwakens.prefix("entity.emerald_gator.death")));

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
