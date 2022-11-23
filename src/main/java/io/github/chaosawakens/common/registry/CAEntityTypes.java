package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.entity.AbstractLavaEntity;
import io.github.chaosawakens.common.entity.AggressiveAntEntity;
import io.github.chaosawakens.common.entity.AntEntity;
import io.github.chaosawakens.common.entity.AppleCowEntity;
import io.github.chaosawakens.common.entity.BeaverEntity;
import io.github.chaosawakens.common.entity.BirdEntity;
import io.github.chaosawakens.common.entity.CABoatEntity;
import io.github.chaosawakens.common.entity.CarrotPigEntity;
import io.github.chaosawakens.common.entity.CrystalAppleCowEntity;
import io.github.chaosawakens.common.entity.CrystalCarrotPigEntity;
import io.github.chaosawakens.common.entity.CrystalGatorEntity;
import io.github.chaosawakens.common.entity.DimetrodonEntity;
import io.github.chaosawakens.common.entity.EmeraldGatorEntity;
import io.github.chaosawakens.common.entity.EnchantedGoldenAppleCowEntity;
import io.github.chaosawakens.common.entity.EnchantedGoldenCarrotPigEntity;
import io.github.chaosawakens.common.entity.EntEntity;
import io.github.chaosawakens.common.entity.GazelleEntity;
import io.github.chaosawakens.common.entity.GoldenAppleCowEntity;
import io.github.chaosawakens.common.entity.GoldenCarrotPigEntity;
import io.github.chaosawakens.common.entity.GreenFishEntity;
import io.github.chaosawakens.common.entity.HerculesBeetleEntity;
import io.github.chaosawakens.common.entity.LavaEelEntity;
import io.github.chaosawakens.common.entity.LeafyChickenEntity;
import io.github.chaosawakens.common.entity.RockFishEntity;
import io.github.chaosawakens.common.entity.RubyBugEntity;
import io.github.chaosawakens.common.entity.SparkFishEntity;
import io.github.chaosawakens.common.entity.StinkBugEntity;
import io.github.chaosawakens.common.entity.TreeFrogEntity;
import io.github.chaosawakens.common.entity.UltimateAppleCowEntity;
import io.github.chaosawakens.common.entity.WaspEntity;
import io.github.chaosawakens.common.entity.WhaleEntity;
import io.github.chaosawakens.common.entity.WoodFishEntity;
import io.github.chaosawakens.common.entity.nonliving.CAFallingBlockEntity;
import io.github.chaosawakens.common.entity.nonliving.CAScreenShakeEntity;
import io.github.chaosawakens.common.entity.projectile.AttackSquidProjectile;
import io.github.chaosawakens.common.entity.projectile.CALeafyChickenEggEntity;
import io.github.chaosawakens.common.entity.projectile.ExplosiveFireworkEntity;
import io.github.chaosawakens.common.entity.projectile.IrukandjiArrowEntity;
import io.github.chaosawakens.common.entity.projectile.RayGunProjectileEntity;
import io.github.chaosawakens.common.entity.projectile.RoboLaserEntity;
import io.github.chaosawakens.common.entity.projectile.ThunderStaffProjectileEntity;
import io.github.chaosawakens.common.entity.projectile.UltimateArrowEntity;
import io.github.chaosawakens.common.entity.projectile.UltimateFishingBobberEntity;
import io.github.chaosawakens.common.entity.robo.RoboEntity;
import io.github.chaosawakens.common.entity.robo.RoboPounderEntity;
import io.github.chaosawakens.common.entity.robo.RoboSniperEntity;
import io.github.chaosawakens.common.entity.robo.RoboWarriorEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.IFactory;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CAEntityTypes {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, ChaosAwakens.MODID);
	// Ents
	public static final RegistryObject<EntityType<EntEntity>> ACACIA_ENT = ENTITY_TYPES.register("acacia_ent",
			() -> EntityType.Builder.of((IFactory<EntEntity>) (type, world) -> new EntEntity(type, world, EntEntity.Types.ACACIA), EntityClassification.MONSTER)
					.sized(2.5f, 3.125f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "acacia_ent").toString()));
	public static final RegistryObject<EntityType<EntEntity>> APPLE_ENT = ENTITY_TYPES.register("apple_ent",
			() -> EntityType.Builder.of((IFactory<EntEntity>) (type, world) -> new EntEntity(type, world, EntEntity.Types.APPLE), EntityClassification.MONSTER)
					.sized(2.5f, 3.125f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "apple_ent").toString()));
	public static final RegistryObject<EntityType<EntEntity>> BIRCH_ENT = ENTITY_TYPES.register("birch_ent",
			() -> EntityType.Builder.of((IFactory<EntEntity>) (type, world) -> new EntEntity(type, world, EntEntity.Types.BIRCH), EntityClassification.MONSTER)
					.sized(2.5f, 3.125f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "birch_ent").toString()));
	public static final RegistryObject<EntityType<EntEntity>> CHERRY_ENT = ENTITY_TYPES.register("cherry_ent",
			() -> EntityType.Builder.of((IFactory<EntEntity>) (type, world) -> new EntEntity(type, world, EntEntity.Types.CHERRY), EntityClassification.MONSTER)
					.sized(2.5f, 3.125f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "cherry_ent").toString()));
	public static final RegistryObject<EntityType<EntEntity>> CRIMSON_ENT = ENTITY_TYPES.register("crimson_ent",
			() -> EntityType.Builder.of((IFactory<EntEntity>) (type, world) -> new EntEntity(type, world, EntEntity.Types.CRIMSON), EntityClassification.MONSTER)
					.sized(2.5f, 3.125f).fireImmune()
					.build(new ResourceLocation(ChaosAwakens.MODID, "crimson_ent").toString()));
	public static final RegistryObject<EntityType<EntEntity>> DARK_OAK_ENT = ENTITY_TYPES.register("dark_oak_ent",
			() -> EntityType.Builder.of((IFactory<EntEntity>) (type, world) -> new EntEntity(type, world, EntEntity.Types.DARK_OAK), EntityClassification.MONSTER)
					.sized(2.5f, 3.125f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "dark_oak_ent").toString()));
	public static final RegistryObject<EntityType<EntEntity>> JUNGLE_ENT = ENTITY_TYPES.register("jungle_ent",
			() -> EntityType.Builder.of((IFactory<EntEntity>) (type, world) -> new EntEntity(type, world, EntEntity.Types.JUNGLE), EntityClassification.MONSTER)
					.sized(2.5f, 3.125f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "jungle_ent").toString()));
	public static final RegistryObject<EntityType<EntEntity>> OAK_ENT = ENTITY_TYPES.register("oak_ent",
			() -> EntityType.Builder.of((IFactory<EntEntity>) (type, world) -> new EntEntity(type, world, EntEntity.Types.OAK), EntityClassification.MONSTER)
					.sized(2.5f, 3.125f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "oak_ent").toString()));
	public static final RegistryObject<EntityType<EntEntity>> PEACH_ENT = ENTITY_TYPES.register("peach_ent",
			() -> EntityType.Builder.of((IFactory<EntEntity>) (type, world) -> new EntEntity(type, world, EntEntity.Types.PEACH), EntityClassification.MONSTER)
					.sized(2.5f, 3.125f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "peach_ent").toString()));
	public static final RegistryObject<EntityType<EntEntity>> SKYWOOD_ENT = ENTITY_TYPES.register("skywood_ent",
			() -> EntityType.Builder.of((IFactory<EntEntity>) (type, world) -> new EntEntity(type, world, EntEntity.Types.SKYWOOD), EntityClassification.MONSTER)
					.sized(2.5f, 3.125f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "skywood_ent").toString()));
	public static final RegistryObject<EntityType<EntEntity>> SPRUCE_ENT = ENTITY_TYPES.register("spruce_ent",
			() -> EntityType.Builder.of((IFactory<EntEntity>) (type, world) -> new EntEntity(type, world, EntEntity.Types.SPRUCE), EntityClassification.MONSTER)
					.sized(2.5f, 3.125f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "spruce_ent").toString()));
	public static final RegistryObject<EntityType<EntEntity>> WARPED_ENT = ENTITY_TYPES.register("warped_ent",
			() -> EntityType.Builder.of((IFactory<EntEntity>) (type, world) -> new EntEntity(type, world, EntEntity.Types.WARPED), EntityClassification.MONSTER)
					.sized(2.5f, 3.125f).fireImmune()
					.build(new ResourceLocation(ChaosAwakens.MODID, "warped_ent").toString()));

	// Hercules Beetle
	public static final RegistryObject<EntityType<HerculesBeetleEntity>> HERCULES_BEETLE = ENTITY_TYPES.register("hercules_beetle",
			() -> EntityType.Builder.of((IFactory<HerculesBeetleEntity>) (type, world) -> new HerculesBeetleEntity(type, world, HerculesBeetleEntity.Type.MODERN), EntityClassification.MONSTER)
					.sized(4.5f, 1.925f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "hercules_beetle").toString()));
	public static final RegistryObject<EntityType<HerculesBeetleEntity>> THROWBACK_HERCULES_BEETLE = ENTITY_TYPES.register("throwback_hercules_beetle",
			() -> EntityType.Builder.of((IFactory<HerculesBeetleEntity>) (type, world) -> new HerculesBeetleEntity(type, world, HerculesBeetleEntity.Type.THROWBACK), EntityClassification.MONSTER)
					.sized(4.5f, 1.925f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "throwback_hercules_beetle").toString()));

	// Fish
	public static final RegistryObject<EntityType<GreenFishEntity>> GREEN_FISH = ENTITY_TYPES.register("green_fish",
			() -> EntityType.Builder.of(GreenFishEntity::new, EntityClassification.WATER_CREATURE)
					.sized(0.4f, 0.4f).clientTrackingRange(10)
					.build(new ResourceLocation(ChaosAwakens.MODID, "green_fish").toString()));
	public static final RegistryObject<EntityType<RockFishEntity>> ROCK_FISH = ENTITY_TYPES.register("rock_fish",
			() -> EntityType.Builder.of(RockFishEntity::new, EntityClassification.WATER_CREATURE)
					.sized(0.75f, 0.6f).clientTrackingRange(10)
					.build(new ResourceLocation(ChaosAwakens.MODID, "rock_fish").toString()));
	public static final RegistryObject<EntityType<SparkFishEntity>> SPARK_FISH = ENTITY_TYPES.register("spark_fish",
			() -> EntityType.Builder.of(SparkFishEntity::new, EntityClassification.WATER_CREATURE)
					.sized(0.4f, 0.15f).clientTrackingRange(10)
					.build(new ResourceLocation(ChaosAwakens.MODID, "spark_fish").toString()));
	public static final RegistryObject<EntityType<WoodFishEntity>> WOOD_FISH = ENTITY_TYPES.register("wood_fish",
			() -> EntityType.Builder.of(WoodFishEntity::new, EntityClassification.WATER_CREATURE)
					.sized(0.6F, 0.4F).clientTrackingRange(10)
					.build(new ResourceLocation(ChaosAwakens.MODID, "wood_fish").toString()));

	// Lava Eel
	public static final RegistryObject<EntityType<LavaEelEntity>> LAVA_EEL = ENTITY_TYPES.register("lava_eel",
			() -> EntityType.Builder.of(LavaEelEntity::new, EntityClassification.WATER_CREATURE)
					.sized(0.3f, 0.2f).fireImmune().clientTrackingRange(10)
					.build(new ResourceLocation(ChaosAwakens.MODID, "lava_eel").toString()));

	// Bird
	public static final RegistryObject<EntityType<BirdEntity>> BIRD = ENTITY_TYPES.register("bird",
			() -> EntityType.Builder.of(BirdEntity::new, EntityClassification.CREATURE)
					.sized(0.4f, 0.3f).clientTrackingRange(10)
					.build(new ResourceLocation(ChaosAwakens.MODID, "bird").toString()));

	// Whale
	public static final RegistryObject<EntityType<WhaleEntity>> WHALE = ENTITY_TYPES.register("whale",
			() -> EntityType.Builder.of(WhaleEntity::new, EntityClassification.WATER_CREATURE)
					.sized(9.0F, 4.3F).clientTrackingRange(10)
					.build(new ResourceLocation(ChaosAwakens.MODID, "whale").toString()));

	// Frog
	public static final RegistryObject<EntityType<TreeFrogEntity>> TREE_FROG = ENTITY_TYPES.register("tree_frog",
			() -> EntityType.Builder.of(TreeFrogEntity::new, EntityClassification.CREATURE)
					.sized(0.6F, 0.6F).clientTrackingRange(10)
					.build(new ResourceLocation(ChaosAwakens.MODID, "tree_frog").toString()));

	// Gazelle
	public static final RegistryObject<EntityType<GazelleEntity>> GAZELLE = ENTITY_TYPES.register("gazelle",
			() -> EntityType.Builder.of(GazelleEntity::new, EntityClassification.CREATURE)
					.sized(0.6F, 1.3F).clientTrackingRange(10)
					.build(new ResourceLocation(ChaosAwakens.MODID, "gazelle").toString()));

	// Apple Cows
	public static final RegistryObject<EntityType<AppleCowEntity>> APPLE_COW = ENTITY_TYPES.register("apple_cow",
			() -> EntityType.Builder.of(AppleCowEntity::new, EntityClassification.CREATURE)
					.sized(0.9F, 1.4F).clientTrackingRange(10)
					.build(new ResourceLocation(ChaosAwakens.MODID, "apple_cow").toString()));
	public static final RegistryObject<EntityType<GoldenAppleCowEntity>> GOLDEN_APPLE_COW = ENTITY_TYPES.register("golden_apple_cow",
			() -> EntityType.Builder.of(GoldenAppleCowEntity::new, EntityClassification.CREATURE)
					.sized(0.9F, 1.4F).clientTrackingRange(10)
					.build(new ResourceLocation(ChaosAwakens.MODID, "golden_apple_cow").toString()));
	public static final RegistryObject<EntityType<EnchantedGoldenAppleCowEntity>> ENCHANTED_GOLDEN_APPLE_COW = ENTITY_TYPES.register("enchanted_golden_apple_cow",
			() -> EntityType.Builder.of(EnchantedGoldenAppleCowEntity::new, EntityClassification.CREATURE)
					.sized(0.9F, 1.4F).clientTrackingRange(10)
					.build(new ResourceLocation(ChaosAwakens.MODID, "enchanted_golden_apple_cow").toString()));
	public static final RegistryObject<EntityType<CrystalAppleCowEntity>> CRYSTAL_APPLE_COW = ENTITY_TYPES.register("crystal_apple_cow",
			() -> EntityType.Builder.of(CrystalAppleCowEntity::new, EntityClassification.CREATURE)
					.sized(0.9F, 1.4F).clientTrackingRange(10)
					.build(new ResourceLocation(ChaosAwakens.MODID, "crystal_apple_cow").toString()));
	public static final RegistryObject<EntityType<UltimateAppleCowEntity>> ULTIMATE_APPLE_COW = ENTITY_TYPES.register("ultimate_apple_cow",
			() -> EntityType.Builder.of(UltimateAppleCowEntity::new, EntityClassification.CREATURE)
					.sized(0.9F, 1.4F).clientTrackingRange(10)
					.build(new ResourceLocation(ChaosAwakens.MODID, "ultimate_apple_cow").toString()));

	// Carrot Pigs
	public static final RegistryObject<EntityType<CarrotPigEntity>> CARROT_PIG = ENTITY_TYPES.register("carrot_pig",
			() -> EntityType.Builder.of(CarrotPigEntity::new, EntityClassification.CREATURE)
					.sized(0.9F, 0.9F).clientTrackingRange(10)
					.build(new ResourceLocation(ChaosAwakens.MODID, "carrot_pig").toString()));
	public static final RegistryObject<EntityType<GoldenCarrotPigEntity>> GOLDEN_CARROT_PIG = ENTITY_TYPES.register("golden_carrot_pig",
			() -> EntityType.Builder.of(GoldenCarrotPigEntity::new, EntityClassification.CREATURE)
					.sized(0.9F, 0.9F).clientTrackingRange(10)
					.build(new ResourceLocation(ChaosAwakens.MODID, "golden_carrot_pig").toString()));
	public static final RegistryObject<EntityType<EnchantedGoldenCarrotPigEntity>> ENCHANTED_GOLDEN_CARROT_PIG = ENTITY_TYPES.register("enchanted_golden_carrot_pig",
			() -> EntityType.Builder.of(EnchantedGoldenCarrotPigEntity::new, EntityClassification.CREATURE)
					.sized(0.9F, 0.9F).clientTrackingRange(10)
					.build(new ResourceLocation(ChaosAwakens.MODID, "enchanted_golden_carrot_pig").toString()));
	public static final RegistryObject<EntityType<CrystalCarrotPigEntity>> CRYSTAL_CARROT_PIG = ENTITY_TYPES.register("crystal_carrot_pig",
			() -> EntityType.Builder.of(CrystalCarrotPigEntity::new, EntityClassification.CREATURE)
					.sized(0.9F, 0.9F).clientTrackingRange(10)
					.build(new ResourceLocation(ChaosAwakens.MODID, "crystal_carrot_pig").toString()));
	
	//Leafy Chicken
	public static final RegistryObject<EntityType<LeafyChickenEntity>> LEAFY_CHICKEN = ENTITY_TYPES.register("leafy_chicken",
			() -> EntityType.Builder.of(LeafyChickenEntity::new, EntityClassification.CREATURE)
					.sized(0.4F, 0.7F).clientTrackingRange(10)
					.build(new ResourceLocation(ChaosAwakens.MODID, "leafy_chicken").toString()));

	// Ants
	public static final RegistryObject<EntityType<AntEntity>> BROWN_ANT = ENTITY_TYPES.register("brown_ant",
			() -> EntityType.Builder.of((IFactory<AntEntity>) (type, world) -> new AntEntity(type, world,
							CACommonConfig.COMMON.enableBrownAntTeleport, null), EntityClassification.CREATURE)
					.sized(0.25f, 0.25f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "brown_ant").toString()));
	public static final RegistryObject<EntityType<AntEntity>> RAINBOW_ANT = ENTITY_TYPES.register("rainbow_ant",
			() -> EntityType.Builder.of((IFactory<AntEntity>) (type, world) -> new AntEntity(type, world,
							CACommonConfig.COMMON.enableRainbowAntTeleport, CADimensions.VILLAGE_MANIA), EntityClassification.CREATURE)
					.sized(0.25f, 0.25f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "rainbow_ant").toString()));
	public static final RegistryObject<EntityType<AggressiveAntEntity>> RED_ANT = ENTITY_TYPES.register("red_ant",
			() -> EntityType.Builder.of((IFactory<AggressiveAntEntity>) (type, world) -> new AggressiveAntEntity(type, world,
							CACommonConfig.COMMON.enableRedAntTeleport, CADimensions.MINING_PARADISE), EntityClassification.MONSTER)
					.sized(0.25f, 0.25f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "red_ant").toString()));
	public static final RegistryObject<EntityType<AntEntity>> UNSTABLE_ANT = ENTITY_TYPES.register("unstable_ant",
			() -> EntityType.Builder.of((IFactory<AntEntity>) (type, world) -> new AntEntity(type, world,
							CACommonConfig.COMMON.enableUnstableAntTeleport, null), EntityClassification.CREATURE)
					.sized(0.25f, 0.25f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "unstable_ant").toString()));
	// Termite
	public static final RegistryObject<EntityType<AggressiveAntEntity>> TERMITE = ENTITY_TYPES.register("termite",
			() -> EntityType.Builder.of((IFactory<AggressiveAntEntity>) (type, world) -> new AggressiveAntEntity(type, world,
							CACommonConfig.COMMON.enableTermiteTeleport, CADimensions.CRYSTAL_WORLD), EntityClassification.CREATURE)
					.sized(0.5f, 0.25f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "termite").toString()));

	// Beaver
	public static final RegistryObject<EntityType<BeaverEntity>> BEAVER = ENTITY_TYPES.register("beaver",
			() -> EntityType.Builder.of(BeaverEntity::new, EntityClassification.CREATURE)
					.sized(0.75f, 0.7f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "beaver").toString()));

	// Dimetrodon
	public static final RegistryObject<EntityType<DimetrodonEntity>> DIMETRODON = ENTITY_TYPES.register("dimetrodon",
			() -> EntityType.Builder.of(DimetrodonEntity::new, EntityClassification.CREATURE)
					.sized(1.5f, 1.0f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "dimetrodon").toString()));

	// Emerald Gator
	public static final RegistryObject<EntityType<EmeraldGatorEntity>> EMERALD_GATOR = ENTITY_TYPES.register("emerald_gator",
			() -> EntityType.Builder.of(EmeraldGatorEntity::new, EntityClassification.CREATURE)
					.sized(1.5f, 1.0f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "emerald_gator").toString()));
	public static final RegistryObject<EntityType<CrystalGatorEntity>> CRYSTAL_GATOR = ENTITY_TYPES.register("crystal_gator",
			() -> EntityType.Builder.of(CrystalGatorEntity::new, EntityClassification.CREATURE)
					.sized(1.5f, 1.0f) // Hitbox Size ()
					.build(new ResourceLocation(ChaosAwakens.MODID, "crystal_gator").toString()));

	// Ruby Bug
	public static final RegistryObject<EntityType<RubyBugEntity>> RUBY_BUG = ENTITY_TYPES.register("ruby_bug",
			() -> EntityType.Builder.of(RubyBugEntity::new, EntityClassification.CREATURE)
					.sized(0.6f, 0.5f).fireImmune()
					.build(new ResourceLocation(ChaosAwakens.MODID, "ruby_bug").toString()));

	// Stink Bug
	public static final RegistryObject<EntityType<StinkBugEntity>> STINK_BUG = ENTITY_TYPES.register("stink_bug",
			() -> EntityType.Builder.of(StinkBugEntity::new, EntityClassification.CREATURE)
					.sized(0.5f, 0.5f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "stink_bug").toString()));

	// Robos
	public static final RegistryObject<EntityType<RoboSniperEntity>> ROBO_SNIPER = ENTITY_TYPES.register("robo_sniper",
			() -> EntityType.Builder.of(RoboSniperEntity::new, EntityClassification.MONSTER)
					.sized(1.0f, 1.5f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "robo_sniper").toString()));
	public static final RegistryObject<EntityType<RoboWarriorEntity>> ROBO_WARRIOR = ENTITY_TYPES.register("robo_warrior",
			() -> EntityType.Builder.of(RoboWarriorEntity::new, EntityClassification.MONSTER)
					.sized(2.0f, 4.0f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "robo_warrior").toString()));
	public static final RegistryObject<EntityType<RoboPounderEntity>> ROBO_POUNDER = ENTITY_TYPES.register("robo_pounder",
			() -> EntityType.Builder.of(RoboPounderEntity::new, EntityClassification.MONSTER)
					.sized(2.5F, 5.5F)
					.build(new ResourceLocation(ChaosAwakens.MODID, "robo_pounder").toString()));

	// Wasp
	public static final RegistryObject<EntityType<WaspEntity>> WASP = ENTITY_TYPES.register("wasp",
			() -> EntityType.Builder.of(WaspEntity::new, EntityClassification.MONSTER)
					.sized(1.5f, 2.0f)
					.build(new ResourceLocation(ChaosAwakens.MODID, "wasp").toString()));
	
	//Boat
	public static final RegistryObject<EntityType<CABoatEntity>> CA_BOAT = ENTITY_TYPES.register("ca_boat", 
			() -> EntityType.Builder.of((IFactory<CABoatEntity>) (type, world) -> new CABoatEntity(type, world), EntityClassification.MISC)
					.sized(1.375F, 0.5625F)
					.build(new ResourceLocation(ChaosAwakens.MODID, "ca_boat").toString()));

	// Projectiles
	public static final RegistryObject<EntityType<RoboLaserEntity>> ROBO_LASER = ENTITY_TYPES.register("robo_laser",
			() -> EntityType.Builder.<RoboLaserEntity>of(RoboLaserEntity::new, EntityClassification.MISC)
					.sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
					.build(new ResourceLocation(ChaosAwakens.MODID, "robo_laser").toString()));
	public static final RegistryObject<EntityType<UltimateArrowEntity>> ULTIMATE_ARROW = ENTITY_TYPES.register("ultimate_arrow",
			() -> EntityType.Builder.<UltimateArrowEntity>of(UltimateArrowEntity::new, EntityClassification.MISC)
					.sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
					.build(new ResourceLocation(ChaosAwakens.MODID, "ultimate_arrow").toString()));
	public static final RegistryObject<EntityType<IrukandjiArrowEntity>> IRUKANDJI_ARROW = ENTITY_TYPES.register("irukandji_arrow",
			() -> EntityType.Builder.<IrukandjiArrowEntity>of(IrukandjiArrowEntity::new, EntityClassification.MISC)
					.sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
					.build(new ResourceLocation(ChaosAwakens.MODID, "irukandji_arrow").toString()));
	public static final RegistryObject<EntityType<ThunderStaffProjectileEntity>> THUNDER_BALL = ENTITY_TYPES.register("thunder_ball",
			() -> EntityType.Builder.<ThunderStaffProjectileEntity>of(ThunderStaffProjectileEntity::new, EntityClassification.MISC)
					.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(20)
					.build(new ResourceLocation(ChaosAwakens.MODID, "thunder_ball").toString()));
	public static final RegistryObject<EntityType<RayGunProjectileEntity>> EXPLOSIVE_BALL = ENTITY_TYPES.register("explosive_ball",
			() -> EntityType.Builder.<RayGunProjectileEntity>of(RayGunProjectileEntity::new, EntityClassification.MISC)
					.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(20)
					.build(new ResourceLocation(ChaosAwakens.MODID, "explosive_ball").toString()));
	public static final RegistryObject<EntityType<AttackSquidProjectile>> ATTACK_SQUID_PROJECTILE = ENTITY_TYPES.register("attack_squid_projectile",
			() -> EntityType.Builder.<AttackSquidProjectile>of(AttackSquidProjectile::new, EntityClassification.MISC)
					.sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
					.build(new ResourceLocation(ChaosAwakens.MODID, "attack_squid_projectile").toString()));
	public static final RegistryObject<EntityType<ExplosiveFireworkEntity>> EXPLOSIVE_FIREWORKS = ENTITY_TYPES.register("explosive_fireworks",
			() -> EntityType.Builder.<ExplosiveFireworkEntity>of(ExplosiveFireworkEntity::new, EntityClassification.MISC)
			         .sized(0.55F, 0.55F).clientTrackingRange(4).updateInterval(20)
			         .build(new ResourceLocation(ChaosAwakens.MODID, "explosive_fireworks").toString()));
	public static final RegistryObject<EntityType<UltimateFishingBobberEntity>> ULTIMATE_FISHING_BOBBER = ENTITY_TYPES.register("ultimate_fishing_bobber",
			() -> EntityType.Builder.<UltimateFishingBobberEntity>createNothing(EntityClassification.MISC)
					.setCustomClientFactory(UltimateFishingBobberEntity::new)
					.noSave().noSummon().setShouldReceiveVelocityUpdates(true)
					.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(5).fireImmune()
					.build(new ResourceLocation(ChaosAwakens.MODID, "ultimate_fishing_bobber").toString()));
	
	// Misc
	public static final RegistryObject<EntityType<CAFallingBlockEntity>> FALLING_BLOCK = ENTITY_TYPES.register("falling_block",
			() -> EntityType.Builder.<CAFallingBlockEntity>of(CAFallingBlockEntity::new, EntityClassification.MISC)
					.sized(0.98F, 0.98F).clientTrackingRange(10).updateInterval(20)
					.build(new ResourceLocation(ChaosAwakens.MODID, "falling_block").toString()));
	public static final RegistryObject<EntityType<CAScreenShakeEntity>> SCREEN_SHAKE = ENTITY_TYPES.register("screen_shake", 
			() -> EntityType.Builder.<CAScreenShakeEntity>of(CAScreenShakeEntity::new, EntityClassification.MISC)
			        .build(new ResourceLocation(ChaosAwakens.MODID, "screen_shake").toString()));
	public static final RegistryObject<EntityType<CALeafyChickenEggEntity>> LEAFY_CHICKEN_EGG = ENTITY_TYPES.register("leafy_chicken_egg",
			() -> EntityType.Builder.<CALeafyChickenEggEntity>of(CALeafyChickenEggEntity::new, EntityClassification.MISC)
					.sized(0.25F, 0.25F).clientTrackingRange(10).updateInterval(20)
					.build(new ResourceLocation(ChaosAwakens.MODID, "leafy_chicken_egg").toString()));

	public static void registerSpawnPlacementTypes() {
		EntitySpawnPlacementRegistry.register(ACACIA_ENT.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
		EntitySpawnPlacementRegistry.register(BIRCH_ENT.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
		EntitySpawnPlacementRegistry.register(CRIMSON_ENT.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
		EntitySpawnPlacementRegistry.register(DARK_OAK_ENT.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
		EntitySpawnPlacementRegistry.register(JUNGLE_ENT.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
		EntitySpawnPlacementRegistry.register(OAK_ENT.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
		EntitySpawnPlacementRegistry.register(SPRUCE_ENT.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
		EntitySpawnPlacementRegistry.register(WARPED_ENT.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
		EntitySpawnPlacementRegistry.register(TREE_FROG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::checkMobSpawnRules);
		EntitySpawnPlacementRegistry.register(BIRD.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, BirdEntity::checkBirdSpawnRules);
		EntitySpawnPlacementRegistry.register(LAVA_EEL.get(), EntitySpawnPlacementRegistry.PlacementType.IN_LAVA, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractLavaEntity::checkLavaMobSpawnRules);
		EntitySpawnPlacementRegistry.register(HERCULES_BEETLE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
		EntitySpawnPlacementRegistry.register(THROWBACK_HERCULES_BEETLE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
		EntitySpawnPlacementRegistry.register(RUBY_BUG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
		EntitySpawnPlacementRegistry.register(EMERALD_GATOR.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
		EntitySpawnPlacementRegistry.register(CRYSTAL_GATOR.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CrystalGatorEntity::checkCrystalGatorSpawnRules);
		EntitySpawnPlacementRegistry.register(APPLE_COW.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
		EntitySpawnPlacementRegistry.register(GOLDEN_APPLE_COW.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
		EntitySpawnPlacementRegistry.register(ENCHANTED_GOLDEN_APPLE_COW.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
		EntitySpawnPlacementRegistry.register(CRYSTAL_APPLE_COW.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CrystalAppleCowEntity::checkCrystalAppleCowSpawnRules);
		EntitySpawnPlacementRegistry.register(CARROT_PIG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
		EntitySpawnPlacementRegistry.register(GOLDEN_CARROT_PIG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
		EntitySpawnPlacementRegistry.register(ENCHANTED_GOLDEN_CARROT_PIG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
		EntitySpawnPlacementRegistry.register(CRYSTAL_CARROT_PIG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CrystalCarrotPigEntity::checkCrystalCarrotPigSpawnRules);
		EntitySpawnPlacementRegistry.register(BEAVER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
		EntitySpawnPlacementRegistry.register(STINK_BUG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
		EntitySpawnPlacementRegistry.register(ROCK_FISH.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, RockFishEntity::checkRockFishSpawnRules);
		EntitySpawnPlacementRegistry.register(ROBO_POUNDER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, RoboEntity::checkRoboSpawnRules);
		EntitySpawnPlacementRegistry.register(ROBO_SNIPER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, RoboEntity::checkRoboSpawnRules);
		EntitySpawnPlacementRegistry.register(ROBO_WARRIOR.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, RoboEntity::checkRoboSpawnRules);
	}
}
