package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.github.chaosawakens.api.FeatureWrapper;
import io.github.chaosawakens.common.events.CommonSetupEvent;
import io.github.chaosawakens.common.worldgen.feature.GeodeFeatureConfig;
import io.github.chaosawakens.common.worldgen.placement.DoubleCrystalPlantBlockPlacer;
import io.github.chaosawakens.common.worldgen.placement.DoubleDensePlantBlockPlacer;
import io.github.chaosawakens.common.worldgen.placement.OceanBedPlacement;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.*;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.placement.*;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

import java.util.OptionalInt;

public class CAConfiguredFeatures {
	// ORES
	// GENERIC
	public static final ConfiguredFeature<?, ?> ORE_RUBY_LAVA = register("ore_ruby", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_LAVA, States.RUBY_ORE, 8)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(6, 12))).squared().count(3));
	public static final ConfiguredFeature<?, ?> ORE_AMETHYST = register("ore_amethyst", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.AMETHYST_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(18, 16))).squared().count(4));
	public static final ConfiguredFeature<?, ?> ORE_URANIUM = register("ore_uranium", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.URANIUM_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(1, 12))).squared().count(3));
	public static final ConfiguredFeature<?, ?> ORE_TITANIUM = register("ore_titanium", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.TITANIUM_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(1, 12))).squared().count(3));
	public static final ConfiguredFeature<?, ?> ORE_TIGERS_EYE = register("ore_tigers_eye", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.TIGERS_EYE_ORE, 7)).decorated(Placement.RANGE_BIASED.configured(new TopSolidRangeConfig(1, 24, 48))).squared().count(5));
	public static final ConfiguredFeature<?, ?> ORE_SALT = register("ore_salt", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.SALT_ORE, 8)).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(32, 64, 128))).squared().count(14));

	public static final ConfiguredFeature<?, ?> ORE_COPPER = register("ore_copper", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.COPPER_ORE, 5)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(72, 32))).squared().count(6));
	public static final ConfiguredFeature<?, ?> ORE_TIN = register("ore_tin", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.TIN_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(56, 24))).squared().count(5));
	public static final ConfiguredFeature<?, ?> ORE_SILVER = register("ore_silver", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.SILVER_ORE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(32, 20))).squared().count(4));
	public static final ConfiguredFeature<?, ?> ORE_PLATINUM = register("ore_platinum", Feature.NO_SURFACE_ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.PLATINUM_ORE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(8, 16))).squared().count(3));
	public static final ConfiguredFeature<?, ?> ORE_SUNSTONE = register("ore_sunstone", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.SUNSTONE_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(8));
	public static final ConfiguredFeature<?, ?> ORE_BLOODSTONE = register("ore_bloodstone", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.BLOODSTONE_ORE, 5)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(8));

	public static final ConfiguredFeature<?, ?> FOSSILISED_ACACIA_ENT = register("ore_fossilised_acacia_ent", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_ACACIA_ENT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_BIRCH_ENT = register("ore_fossilised_birch_ent", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_BIRCH_ENT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_DARK_OAK_ENT = register("ore_fossilised_dark_oak_ent", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_DARK_OAK_ENT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_JUNGLE_ENT = register("ore_fossilised_jungle_ent", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_JUNGLE_ENT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_OAK_ENT = register("ore_fossilised_oak_ent", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_OAK_ENT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_SPRUCE_ENT = register("ore_fossilised_spruce_ent", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SPRUCE_ENT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_HERCULES_BEETLE = register("ore_fossilised_hercules_beetle", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_HERCULES_BEETLE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_RUBY_BUG = register("ore_fossilised_ruby_bug", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_RUBY_BUG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_EMERALD_GATOR = register("ore_fossilised_emerald_gator", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_EMERALD_GATOR, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_GREEN_FISH = register("ore_fossilised_green_fish", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_GREEN_FISH, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_ROCK_FISH = register("ore_fossilised_rock_fish", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_ROCK_FISH, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_SPARK_FISH = register("ore_fossilised_spark_fish", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_SPARK_FISH, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_WOOD_FISH = register("ore_fossilised_wood_fish", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_WOOD_FISH, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_WHALE = register("ore_fossilised_whale", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_WHALE, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_WTF = register("ore_fossilised_wtf", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WTF, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_SCORPION = register("ore_fossilised_scorpion", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SCORPION, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_WASP = register("ore_fossilised_wasp", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WASP, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_PIRAPORU = register("ore_fossilised_piraporu", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_PIRAPORU, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_APPLE_COW = register("ore_fossilised_apple_cow", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_APPLE_COW, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_GOLDEN_APPLE_COW = register("ore_fossilised_golden_apple_cow", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_GOLDEN_APPLE_COW, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_CARROT_PIG = register("ore_fossilised_carrot_pig", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_CARROT_PIG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_GOLDEN_CARROT_PIG = register("ore_fossilised_golden_carrot_pig", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_GOLDEN_CARROT_PIG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_BIRD = register("ore_fossilised_bird", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_BIRD, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_FROG = register("ore_fossilised_frog", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_FROG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));

	public static final ConfiguredFeature<?, ?> FOSSILISED_BAT = register("ore_fossilised_bat", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_BAT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_BEE = register("ore_fossilised_bee", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_BEE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_CAVE_SPIDER = register("ore_fossilised_cave_spider", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_CAVE_SPIDER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_CHICKEN = register("ore_fossilised_chicken", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_CHICKEN, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_COD = register("ore_fossilised_cod", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_COD, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_COW = register("ore_fossilised_cow", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_COW, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_CREEPER = register("ore_fossilised_creeper", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_CREEPER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_DOLPHIN = register("ore_fossilised_dolphin", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_DOLPHIN, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_DONKEY = register("ore_fossilised_donkey", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_DONKEY, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_DROWNED = register("ore_fossilised_drowned", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_DROWNED, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_ENDERMAN = register("ore_fossilised_enderman", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_ENDERMAN, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_EVOKER = register("ore_fossilised_evoker", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_EVOKER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_FOX = register("ore_fossilised_fox", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_FOX, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_GUARDIAN = register("ore_fossilised_guardian", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_GUARDIAN, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_HORSE = register("ore_fossilised_horse", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_HORSE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_HUSK = register("ore_fossilised_husk", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_HUSK, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_HUSK_SANDSTONE = register("ore_fossilised_husk_sandstone", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_DESERT, States.FOSSILISED_HUSK_SANDSTONE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(72, 30))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_ILLUSIONER = register("ore_fossilised_illusioner", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_ILLUSIONER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_IRON_GOLEM = register("ore_fossilised_iron_golem", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_IRON_GOLEM, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_LLAMA = register("ore_fossilised_llama", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_LLAMA, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_MOOSHROOM = register("ore_fossilised_mooshroom", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_MOOSHROOM, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_OCELOT = register("ore_fossilised_ocelot", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_OCELOT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_PANDA = register("ore_fossilised_panda", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_PANDA, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_PIG = register("ore_fossilised_pig", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_PIG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_PHANTOM = register("ore_fossilised_phantom", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_PHANTOM, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(92, 30))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_PILLAGER = register("ore_fossilised_pillager", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_PILLAGER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FROZEN_POLAR_BEAR = register("ore_frozen_polar_bear", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_PACKED_ICE, States.FROZEN_POLAR_BEAR, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(70, 30))).squared().count(9));
	public static final ConfiguredFeature<?, ?> FOSSILISED_PUFFERFISH = register("ore_fossilised_pufferfish", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_PUFFERFISH, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_RABBIT = register("ore_fossilised_rabbit", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_RABBIT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_RAVAGER = register("ore_fossilised_ravager", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_RAVAGER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_SALMON = register("ore_fossilised_salmon", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_SALMON, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_SHEEP = register("ore_fossilised_sheep", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SHEEP, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_SKELETON = register("ore_fossilised_skeleton", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SKELETON, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_SKELETON_HORSE = register("ore_fossilised_skeleton_horse", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SKELETON_HORSE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_SLIME = register("ore_fossilised_slime", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SLIME, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FROZEN_SNOW_GOLEM = register("ore_frozen_snow_golem", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_PACKED_ICE, States.FROZEN_SNOW_GOLEM, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(70, 30))).squared().count(9));
	public static final ConfiguredFeature<?, ?> FOSSILISED_SPIDER = register("ore_fossilised_spider", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SPIDER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_SQUID = register("ore_fossilised_squid", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_SQUID, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FROZEN_STRAY = register("ore_frozen_stray", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_PACKED_ICE, States.FROZEN_STRAY, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(70, 30))).squared().count(9));
	public static final ConfiguredFeature<?, ?> FOSSILISED_TROPICAL_FISH = register("ore_fossilised_tropical_fish", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_TROPICAL_FISH, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_TURTLE = register("ore_fossilised_turtle", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_TURTLE, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_VILLAGER = register("ore_fossilised_villager", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_VILLAGER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_VINDICATOR = register("ore_fossilised_vindicator", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_VINDICATOR, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_WANDERING_TRADER = register("ore_fossilised_wandering_trader", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WANDERING_TRADER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_WITCH = register("ore_fossilised_witch", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WITCH, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_WOLF = register("ore_fossilised_wolf", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WOLF, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_ZOMBIE = register("ore_fossilised_zombie", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_ZOMBIE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_ZOMBIE_HORSE = register("ore_fossilised_zombie_horse", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_ZOMBIE_HORSE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));

	public static final ConfiguredFeature<?, ?> RED_ANT_INFESTED = register("ore_red_ant_infested", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.RED_ANT_INFESTED_ORE, 8)).range(16).squared());
	public static final ConfiguredFeature<?, ?> TERMITE_INFESTED = register("ore_termite_infested", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.STONE, States.TERMITE_INFESTED_ORE)).decorated(Placement.EMERALD_ORE.configured(IPlacementConfig.NONE)));

	// NETHER
	public static final ConfiguredFeature<?, ?> NETHERRACK_ORE_RUBY_LAVA = register("netherrack_ore_ruby_lava", Feature.NO_SURFACE_ORE.configured(new OreFeatureConfig(RuleTests.BASE_LAVA, States.NETHERRACK_RUBY_ORE, 12)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(12, 12))).squared().count(5));
	public static final ConfiguredFeature<?, ?> NETHERRACK_ORE_RUBY = register("netherrack_ore_ruby", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.NETHERRACK_RUBY_ORE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(4));
	public static final ConfiguredFeature<?, ?> BLACKSTONE_ORE_RUBY = register("blackstone_ore_ruby", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_BLACKSTONE, States.BLACKSTONE_RUBY_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));

	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_CRIMSON_ENT = register("nether_ore_fossilised_crimson_ent", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_CRIMSON_ENT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_WARPED_ENT = register("nether_ore_fossilised_warped_ent", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_WARPED_ENT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_LAVA_EEL = register("nether_ore_fossilised_lava_eel", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_LAVA_EEL, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));

	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_BLAZE = register("nether_ore_fossilised_blaze", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_BLAZE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_GHAST = register("nether_ore_fossilised_ghast", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_GHAST, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_HOGLIN = register("nether_ore_fossilised_hoglin", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_HOGLIN, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_ENDERMAN_NETHERRACK = register("nether_ore_fossilised_enderman_netherrack", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_ENDERMAN_NETHERRACK, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_MAGMA_CUBE_NETHERRACK = register("nether_ore_fossilised_magma_cube_netherrack", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_MAGMA_CUBE_NETHERRACK, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_MAGMA_CUBE_BLACKSTONE = register("nether_ore_fossilised_magma_cube_blackstone", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_BLACKSTONE, States.FOSSILISED_MAGMA_CUBE_BLACKSTONE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_PIGLIN = register("nether_ore_fossilised_piglin", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_PIGLIN, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_SKELETON_SOUL_SOIL = register("nether_ore_fossilised_skeleton_soul_soil", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_SOUL_SOIL, States.FOSSILISED_SKELETON_SOUL_SOIL, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_STRIDER = register("nether_ore_fossilised_strider", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_STRIDER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_WITHER_SKELETON = register("nether_ore_fossilised_wither_skeleton", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_WITHER_SKELETON, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_ZOMBIFIED_PIGLIN = register("nether_ore_fossilised_zombified_piglin", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_ZOMBIFIED_PIGLIN, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));

	// END
	public static final ConfiguredFeature<?, ?> END_FOSSILISED_ENDERMAN_END_STONE = register("end_ore_fossilised_enderman_end_stone", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_END_STONE, States.FOSSILISED_ENDERMAN_END_STONE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(37, 27))).squared().count(8));
	public static final ConfiguredFeature<?, ?> END_FOSSILISED_ENDERMITE = register("end_ore_fossilised_endermite", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_END_STONE, States.FOSSILISED_ENDERMITE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(37, 27))).squared().count(8));
	public static final ConfiguredFeature<?, ?> END_FOSSILISED_SHULKER = register("end_ore_fossilised_shulker", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_END_STONE, States.FOSSILISED_SHULKER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(37, 27))).squared().count(8));

	// MINING PARADISE
	public static final ConfiguredFeature<?, ?> MINING_ORE_RUBY_LAVA = register("mining_ore_ruby", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_LAVA, States.RUBY_ORE, 8)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(6, 12))).squared().count(4));
	public static final ConfiguredFeature<?, ?> MINING_ORE_AMETHYST = register("mining_ore_amethyst", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.AMETHYST_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(40, 16))).squared().count(4));
	public static final ConfiguredFeature<?, ?> MINING_ORE_URANIUM = register("mining_ore_uranium", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.URANIUM_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(1, 18))).squared().count(3));
	public static final ConfiguredFeature<?, ?> MINING_ORE_TITANIUM = register("mining_ore_titanium", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.TITANIUM_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(1, 18))).squared().count(3));
	public static final ConfiguredFeature<?, ?> MINING_ORE_ALUMINUM = register("mining_ore_aluminum", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.ALUMINUM_ORE, 5)).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(8, 64, 80))).squared().count(8));
	public static final ConfiguredFeature<?, ?> MINING_ORE_COPPER = register("mining_ore_copper", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.COPPER_ORE, 5)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(72, 38))).squared().count(6));
	public static final ConfiguredFeature<?, ?> MINING_ORE_TIN = register("mining_ore_tin", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.TIN_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(56, 30))).squared().count(5));
	public static final ConfiguredFeature<?, ?> MINING_ORE_SILVER = register("mining_ore_silver", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.SILVER_ORE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(32, 24))).squared().count(4));
	public static final ConfiguredFeature<?, ?> MINING_ORE_PLATINUM = register("mining_ore_platinum", Feature.NO_SURFACE_ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.PLATINUM_ORE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(8, 20))).squared().count(3));
	public static final ConfiguredFeature<?, ?> MINING_ORE_TIGERS_EYE = register("mining_ore_tigers_eye", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.TIGERS_EYE_ORE, 7)).decorated(Placement.RANGE_BIASED.configured(new TopSolidRangeConfig(1, 32, 60))).squared().count(5));
	public static final ConfiguredFeature<?, ?> MINING_ORE_SALT = register("mining_ore_salt", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.SALT_ORE, 8)).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(32, 64, 144))).squared().count(14));
	public static final ConfiguredFeature<?, ?> MINING_ORE_SUNSTONE = register("mining_ore_sunstone", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.SUNSTONE_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 72))).squared().count(8));
	public static final ConfiguredFeature<?, ?> MINING_ORE_BLOODSTONE = register("mining_ore_bloodstone", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.BLOODSTONE_ORE, 5)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 72))).squared().count(8));
	public static final ConfiguredFeature<?, ?> MINING_RED_ANT_INFESTED = register("mining_ore_red_ant_infested", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.RED_ANT_INFESTED_ORE, 8)).range(16).squared());
	public static final ConfiguredFeature<?, ?> MINING_TERMITE_INFESTED = register("mining_ore_termite_infested", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.STONE, States.TERMITE_INFESTED_ORE)).decorated(Placement.EMERALD_ORE.configured(IPlacementConfig.NONE)));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_HERCULES_BEETLE = register("mining_ore_fossilised_hercules_beetle", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_HERCULES_BEETLE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_RUBY_BUG = register("mining_ore_fossilised_ruby_bug", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_RUBY_BUG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_EMERALD_GATOR = register("mining_ore_fossilised_emerald_gator", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_EMERALD_GATOR, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_WTF = register("mining_ore_fossilised_wtf", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WTF, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_SCORPION = register("mining_ore_fossilised_scorpion", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SCORPION, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_WASP = register("mining_ore_fossilised_wasp", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WASP, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_PIRAPORU = register("mining_ore_fossilised_piraporu", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_PIRAPORU, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_APPLE_COW = register("mining_ore_fossilised_apple_cow", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_APPLE_COW, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_GOLDEN_APPLE_COW = register("mining_ore_fossilised_golden_apple_cow", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_GOLDEN_APPLE_COW, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_CARROT_PIG = register("mining_ore_fossilised_carrot_pig", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_CARROT_PIG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_GOLDEN_CARROT_PIG = register("mining_ore_fossilised_golden_carrot_pig", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_GOLDEN_CARROT_PIG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_BIRD = register("mining_ore_fossilised_bird", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_BIRD, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_DIMETRODON = register("mining_ore_fossilised_dimetrodon", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_DIMETRODON, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_FROG = register("mining_ore_fossilised_frog", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_FROG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));

	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_BAT = register("mining_ore_fossilised_bat", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_BAT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_BEE = register("mining_ore_fossilised_bee", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_BEE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_CAVE_SPIDER = register("mining_ore_fossilised_cave_spider", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_CAVE_SPIDER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_CHICKEN = register("mining_ore_fossilised_chicken", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_CHICKEN, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_COW = register("mining_ore_fossilised_cow", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_COW, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_CREEPER = register("mining_ore_fossilised_creeper", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_CREEPER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_DONKEY = register("mining_ore_fossilised_donkey", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_DONKEY, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_ENDERMAN = register("mining_ore_fossilised_enderman", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_ENDERMAN, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_EVOKER = register("mining_ore_fossilised_evoker", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_EVOKER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_FOX = register("mining_ore_fossilised_fox", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_FOX, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_GIANT = register("mining_ore_fossilised_giant", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_GIANT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_ILLUSIONER = register("mining_ore_fossilised_illusioner", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_ILLUSIONER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_IRON_GOLEM = register("mining_ore_fossilised_iron_golem", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_IRON_GOLEM, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_LLAMA = register("mining_ore_fossilised_llama", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_LLAMA, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_MOOSHROOM = register("mining_ore_fossilised_mooshroom", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_MOOSHROOM, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_OCELOT = register("mining_ore_fossilised_ocelot", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_OCELOT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_PANDA = register("mining_ore_fossilised_panda", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_PANDA, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_PIG = register("mining_ore_fossilised_pig", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_PIG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_PILLAGER = register("mining_ore_fossilised_pillager", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_PILLAGER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_RABBIT = register("mining_ore_fossilised_rabbit", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_RABBIT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_RAVAGER = register("mining_ore_fossilised_ravager", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_RAVAGER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_SHEEP = register("mining_ore_fossilised_sheep", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SHEEP, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_SKELETON = register("mining_ore_fossilised_skeleton", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SKELETON, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_SKELETON_HORSE = register("mining_ore_fossilised_skeleton_horse", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SKELETON_HORSE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_SLIME = register("mining_ore_fossilised_slime", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SLIME, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_SPIDER = register("mining_ore_fossilised_spider", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SPIDER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_VILLAGER = register("mining_ore_fossilised_villager", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_VILLAGER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_VINDICATOR = register("mining_ore_fossilised_vindicator", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_VINDICATOR, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_WANDERING_TRADER = register("mining_ore_fossilised_wandering_trader", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WANDERING_TRADER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_WITCH = register("mining_ore_fossilised_witch", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WITCH, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_WOLF = register("mining_ore_fossilised_wolf", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WOLF, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_ZOMBIE = register("mining_ore_fossilised_zombie", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_ZOMBIE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_ZOMBIE_HORSE = register("mining_ore_fossilised_zombie_horse", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_ZOMBIE_HORSE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));

	public static final ConfiguredFeature<?, ?> MINING_MARBLE_CAVE_PATCH = register("mining_marble_cave_patch", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.MARBLE, 40)).squared().range(128).count(6));
	public static final ConfiguredFeature<?, ?> MINING_LIMESTONE_CAVE_PATCH = register("mining_limestone_cave_patch", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.LIMESTONE, 36)).squared().range(116).count(3));

	// CRYSTAL WORLD
	public static final ConfiguredFeature<?, ?> CRYSTAL_ORE_ENERGY = register("crystal_ore_energy", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_STONE_CRYSTAL, States.CRYSTAL_ENERGY, 5)).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(16, 64, 80))).squared().count(5));
	public static final ConfiguredFeature<?, ?> GEODE_PINK_TOURMALINE = register("geode_pink_tourmaline", CAFeatures.GEODE.get().configured(new GeodeFeatureConfig(RuleTests.BASE_STONE_CRYSTAL, States.PINK_TOURMALINE, States.CLUSTER_PINK_TOURMALINE, 28, 48, 40)));
	public static final ConfiguredFeature<?, ?> GEODE_CATS_EYE = register("geode_cats_eye", CAFeatures.GEODE.get().configured(new GeodeFeatureConfig(RuleTests.BASE_STONE_CRYSTAL, States.CATS_EYE, States.CLUSTER_CATS_EYE, 5, 28, 15)));

	public static final ConfiguredFeature<?, ?> CRYSTAL_FOSSILISED_CRYSTAL_APPLE_COW = register("crystal_ore_fossilised_crystal_apple_cow", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_STONE_CRYSTAL, States.CRYSTALISED_CRYSTAL_APPLE_COW, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(37, 27))).squared().count(3));

	// GRASS / FLOWERS
	public static final ConfiguredFeature<?, ?> CHAOS_FLOWER_DEFAULT = register("chaos_flower_default", Feature.FLOWER.configured(Configs.CHAOS_FLOWER_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(2));
	public static final ConfiguredFeature<?, ?> PATCH_CRYSTAL_GRASS = register("patch_crystal_grass", Feature.RANDOM_PATCH.configured(Configs.CRYSTAL_GRASS_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 5, 10))));
	public static final ConfiguredFeature<?, ?> PATCH_TALL_CRYSTAL_GRASS = register("patch_tall_crystal_grass", Feature.RANDOM_PATCH.configured(Configs.TALL_CRYSTAL_GRASS_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(7));
	public static final ConfiguredFeature<?, ?> CRYSTAL_FLOWER_DEFAULT = register("crystal_flower_default", Feature.FLOWER.configured(Configs.CRYSTAL_FLOWER_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(2));
	public static final ConfiguredFeature<?, ?> CRYSTAL_GROWTH_DEFAULT = register("crystal_growth_default", Feature.FLOWER.configured(Configs.CRYSTAL_GROWTH_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(2));
	public static final ConfiguredFeature<?, ?> PATCH_DENSE_GRASS = register("patch_dense_grass", Feature.RANDOM_PATCH.configured(Configs.DENSE_GRASS_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 5, 10))));
	public static final ConfiguredFeature<?, ?> PATCH_TALL_DENSE_GRASS = register("patch_tall_dense_grass", Feature.RANDOM_PATCH.configured(Configs.TALL_DENSE_GRASS_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(7));
	public static final ConfiguredFeature<?, ?> PATCH_THORNY_SUN = register("patch_thorny_sun", Feature.RANDOM_PATCH.configured(Configs.THORNY_SUN_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(7));
	public static final ConfiguredFeature<?, ?> DENSE_BULB_DEFAULT = register("dense_bulb_default", Feature.FLOWER.configured(Configs.DENSE_BULB_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(2));

	// CROPS
	public static final ConfiguredFeature<?, ?> PATCH_STRAWBERRY_BUSH = register("patch_strawberry_bush", Feature.RANDOM_PATCH.configured(Configs.STRAWBERRY_BUSH_CONFIG));
	public static final ConfiguredFeature<?, ?> PATCH_STRAWBERRY_SPARSE = register("patch_strawberry_sparse", PATCH_STRAWBERRY_BUSH.decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE));
	public static final ConfiguredFeature<?, ?> PATCH_STRAWBERRY_DECORATED = register("patch_strawberry_decorated", PATCH_STRAWBERRY_BUSH.decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).chance(12));
	public static final ConfiguredFeature<?, ?> CORN_PATCH = register("patch_corn", Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.CORN), new ColumnBlockPlacer(3, 8))).tries(8).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).xspread(8).yspread(0).zspread(8).noProjection().build()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE));
	public static final ConfiguredFeature<?, ?> TOMATO_PATCH = register("patch_tomato", Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.TOMATO), new ColumnBlockPlacer(2, 6))).tries(8).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).xspread(8).yspread(0).zspread(8).noProjection().build()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE));

	// TREES
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> GREEN_CRYSTAL_TREE = register("green_crystal_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.CRYSTAL_LOG), new SimpleBlockStateProvider(States.GREEN_CRYSTAL_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 2), new StraightTrunkPlacer(3, 2, 0), new TwoLayerFeature(2, 0, 2)).ignoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> RED_CRYSTAL_TREE = register("red_crystal_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.CRYSTAL_LOG), new SimpleBlockStateProvider(States.RED_CRYSTAL_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(3), FeatureSpread.fixed(2), 2), new StraightTrunkPlacer(5, 2, 0), new TwoLayerFeature(2, 0, 2)).ignoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> YELLOW_CRYSTAL_TREE = register("yellow_crystal_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.CRYSTAL_LOG), new SimpleBlockStateProvider(States.YELLOW_CRYSTAL_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(4), FeatureSpread.fixed(3), 3), new StraightTrunkPlacer(7, 2, 0), new TwoLayerFeature(2, 0, 2)).ignoreVines().build()));

	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> APPLE_TREE = register("apple_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.APPLE_LOG), new SimpleBlockStateProvider(States.APPLE_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 2), new StraightTrunkPlacer(4, 3, 0), new TwoLayerFeature(2, 0, 2)).ignoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> APPLE_TREE_BEES_005 = register("apple_tree_bees_005", Feature.TREE.configured(APPLE_TREE.config.withDecorators(ImmutableList.of(Placements.BEEHIVE_005))));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_APPLE_TREE = register("fancy_apple_tree", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.APPLE_LOG), new SimpleBlockStateProvider(States.APPLE_LEAVES), new FancyFoliagePlacer(FeatureSpread.fixed(3), FeatureSpread.fixed(2), 4), new FancyTrunkPlacer(6, 4, 0), new TwoLayerFeature(2, 0, 2, OptionalInt.of(4)))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_APPLE_TREE_BEES_005 = register("fancy_apple_tree_bees_005", Feature.TREE.configured(FANCY_APPLE_TREE.config.withDecorators(ImmutableList.of(Placements.BEEHIVE_005))));

	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> CHERRY_TREE = register("cherry_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.CHERRY_LOG), new SimpleBlockStateProvider(States.CHERRY_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 2), new StraightTrunkPlacer(4, 3, 0), new TwoLayerFeature(2, 0, 2)).ignoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> CHERRY_TREE_BEES_005 = register("cherry_tree_bees_005", Feature.TREE.configured(CHERRY_TREE.config.withDecorators(ImmutableList.of(Placements.BEEHIVE_005))));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_CHERRY_TREE = register("fancy_cherry_tree", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.CHERRY_LOG), new SimpleBlockStateProvider(States.CHERRY_LEAVES), new FancyFoliagePlacer(FeatureSpread.fixed(3), FeatureSpread.fixed(2), 4), new FancyTrunkPlacer(5, 3, 0), new TwoLayerFeature(2, 0, 2, OptionalInt.of(4)))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_CHERRY_TREE_BEES_005 = register("fancy_cherry_tree_bees_005", Feature.TREE.configured(FANCY_CHERRY_TREE.config.withDecorators(ImmutableList.of(Placements.BEEHIVE_005))));

	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> DUPLICATION_TREE = register("duplication_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.DUPLICATION_LOG), new SimpleBlockStateProvider(States.DUPLICATION_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(1), FeatureSpread.fixed(0), 1), new StraightTrunkPlacer(1, 0, 0), new TwoLayerFeature(1, 0, 1)).ignoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> DEAD_DUPLICATION_TREE = register("dead_duplication_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.DEAD_DUPLICATION_LOG), new SimpleBlockStateProvider(States.DUPLICATION_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(1), FeatureSpread.fixed(0), 1), new StraightTrunkPlacer(1, 0, 0), new TwoLayerFeature(1, 0, 1)).ignoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_DUPLICATION_TREE = register("fancy_duplication_tree", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.DUPLICATION_LOG), new SimpleBlockStateProvider(States.DUPLICATION_LEAVES), new FancyFoliagePlacer(FeatureSpread.fixed(1), FeatureSpread.fixed(0), 1), new FancyTrunkPlacer(1, 0, 0), new TwoLayerFeature(0, 0, 0, OptionalInt.of(1)))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_DEAD_DUPLICATION_TREE = register("fancy_dead_duplication_tree", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.DEAD_DUPLICATION_LOG), new SimpleBlockStateProvider(States.DUPLICATION_LEAVES), new FancyFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(1), 2), new FancyTrunkPlacer(1, 0, 0), new TwoLayerFeature(0, 0, 0, OptionalInt.of(3)))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()));

	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> PEACH_TREE = register("peach_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.PEACH_LOG), new SimpleBlockStateProvider(States.PEACH_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 2), new StraightTrunkPlacer(5, 3, 0), new TwoLayerFeature(2, 0, 2)).ignoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> PEACH_TREE_BEES_005 = register("peach_tree_bees_005", Feature.TREE.configured(PEACH_TREE.config.withDecorators(ImmutableList.of(Placements.BEEHIVE_005))));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_PEACH_TREE = register("fancy_peach_tree", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.PEACH_LOG), new SimpleBlockStateProvider(States.PEACH_LEAVES), new FancyFoliagePlacer(FeatureSpread.fixed(3), FeatureSpread.fixed(2), 4), new FancyTrunkPlacer(4, 3, 0), new TwoLayerFeature(2, 0, 2, OptionalInt.of(4)))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_PEACH_TREE_BEES_005 = register("fancy_peach_tree_bees_005", Feature.TREE.configured(FANCY_PEACH_TREE.config.withDecorators(ImmutableList.of(Placements.BEEHIVE_005))));

	public static final ConfiguredFeature<?, ?> TREES_CRYSTAL_PLAINS = register("trees_crystal_dimension", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(GREEN_CRYSTAL_TREE.weighted(0.4F), RED_CRYSTAL_TREE.weighted(0.3F), YELLOW_CRYSTAL_TREE.weighted(0.1F)), GREEN_CRYSTAL_TREE)).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(2, 0.1F, 1))));
	public static final ConfiguredFeature<?, ?> TREES_APPLE = register("trees_apple", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(APPLE_TREE.weighted(0.1F), APPLE_TREE_BEES_005.weighted(0.01F)), Feature.NO_OP.configured(new NoFeatureConfig()))).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(1).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.01F, 1))));
	public static final ConfiguredFeature<?, ?> TREES_CHERRY = register("trees_cherry", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(CHERRY_TREE.weighted(0.1F), CHERRY_TREE_BEES_005.weighted(0.01F)), Feature.NO_OP.configured(new NoFeatureConfig()))).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(1).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.01F, 1))));
	public static final ConfiguredFeature<?, ?> TREES_PEACH = register("trees_peach", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(PEACH_TREE.weighted(0.1F), PEACH_TREE_BEES_005.weighted(0.01F)), Feature.NO_OP.configured(new NoFeatureConfig()))).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(1).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.01F, 1))));

	// NESTS
	public static final ConfiguredFeature<?, ?> BROWN_ANT_NEST = register("nest_brown_ant", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.GRASS_BLOCK, States.BROWN_ANT_NEST)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(80, 50))));
	public static final ConfiguredFeature<?, ?> RAINBOW_ANT_NEST = register("nest_rainbow_ant", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.GRASS_BLOCK, States.RAINBOW_ANT_NEST)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(80, 50))));
	public static final ConfiguredFeature<?, ?> RED_ANT_NEST = register("nest_red_ant", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.GRASS_BLOCK, States.RED_ANT_NEST)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(80, 50))));
	public static final ConfiguredFeature<?, ?> UNSTABLE_ANT_NEST = register("nest_unstable_ant", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.GRASS_BLOCK, States.UNSTABLE_ANT_NEST)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(80, 50))));
	public static final ConfiguredFeature<?, ?> TERMITE_NEST = register("nest_termite", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.GRASS_BLOCK, States.TERMITE_NEST)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(80, 50))));

	public static final ConfiguredFeature<?, ?> CRYSTAL_TERMITE_NEST = register("nest_crystal_termite", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.CRYSTAL_GRASS_BLOCK, States.CRYSTAL_TERMITE_NEST)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(80, 50))).count(6));
	public static final ConfiguredFeature<?, ?> DENSE_RED_ANT_NEST = register("nest_dense_red_ant", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.DENSE_GRASS_BLOCK, States.DENSE_RED_ANT_NEST)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(140, 40))).count(6));

	private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
		CommonSetupEvent.configFeatures.add(new FeatureWrapper(key, configuredFeature));
		return configuredFeature;
	}

	public static final class States {
		private static final BlockState GRASS_BLOCK = Blocks.GRASS_BLOCK.defaultBlockState();
		private static final BlockState CRYSTAL_GRASS_BLOCK = CABlocks.CRYSTAL_GRASS_BLOCK.get().defaultBlockState();
		private static final BlockState CRYSTAL_GRASS = CABlocks.CRYSTAL_GRASS.get().defaultBlockState();
		private static final BlockState TALL_CRYSTAL_GRASS = CABlocks.TALL_CRYSTAL_GRASS.get().defaultBlockState();
		private static final BlockState DENSE_GRASS_BLOCK = CABlocks.DENSE_GRASS_BLOCK.get().defaultBlockState();
		private static final BlockState DENSE_GRASS = CABlocks.DENSE_GRASS.get().defaultBlockState();
		private static final BlockState TALL_DENSE_GRASS = CABlocks.TALL_DENSE_GRASS.get().defaultBlockState();

		private static final BlockState MARBLE = CABlocks.MARBLE.get().defaultBlockState();
		private static final BlockState LIMESTONE = CABlocks.LIMESTONE.get().defaultBlockState();

		private static final BlockState APPLE_LOG = CABlocks.APPLE_LOG.get().defaultBlockState();
		private static final BlockState CHERRY_LOG = CABlocks.CHERRY_LOG.get().defaultBlockState();
		private static final BlockState DUPLICATION_LOG = CABlocks.DUPLICATION_LOG.get().defaultBlockState();
		private static final BlockState DEAD_DUPLICATION_LOG = CABlocks.DEAD_DUPLICATION_LOG.get().defaultBlockState();
		private static final BlockState PEACH_LOG = CABlocks.PEACH_LOG.get().defaultBlockState();
		private static final BlockState GINKGO_LOG = CABlocks.GINKGO_LOG.get().defaultBlockState();
		private static final BlockState APPLE_LEAVES = CABlocks.APPLE_LEAVES.get().defaultBlockState();
		private static final BlockState CHERRY_LEAVES = CABlocks.CHERRY_LEAVES.get().defaultBlockState();
		private static final BlockState DUPLICATION_LEAVES = CABlocks.DUPLICATION_LEAVES.get().defaultBlockState();
		private static final BlockState PEACH_LEAVES = CABlocks.PEACH_LEAVES.get().defaultBlockState();
		private static final BlockState GINKGO_LEAVES = CABlocks.GINKGO_LEAVES.get().defaultBlockState();

		private static final BlockState STONE = Blocks.STONE.defaultBlockState();
		private static final BlockState RUBY_ORE = CABlocks.RUBY_ORE.get().defaultBlockState();
		private static final BlockState NETHERRACK_RUBY_ORE = CABlocks.NETHERRACK_RUBY_ORE.get().defaultBlockState();
		private static final BlockState BLACKSTONE_RUBY_ORE = CABlocks.BLACKSTONE_RUBY_ORE.get().defaultBlockState();
		private static final BlockState AMETHYST_ORE = CABlocks.AMETHYST_ORE.get().defaultBlockState();
		private static final BlockState URANIUM_ORE = CABlocks.URANIUM_ORE.get().defaultBlockState();
		private static final BlockState TITANIUM_ORE = CABlocks.TITANIUM_ORE.get().defaultBlockState();
		private static final BlockState TIGERS_EYE_ORE = CABlocks.TIGERS_EYE_ORE.get().defaultBlockState();
		private static final BlockState ALUMINUM_ORE = CABlocks.ALUMINUM_ORE.get().defaultBlockState();
		private static final BlockState COPPER_ORE = CABlocks.COPPER_ORE.get().defaultBlockState();
		private static final BlockState TIN_ORE = CABlocks.TIN_ORE.get().defaultBlockState();
		private static final BlockState SILVER_ORE = CABlocks.SILVER_ORE.get().defaultBlockState();
		private static final BlockState PLATINUM_ORE = CABlocks.PLATINUM_ORE.get().defaultBlockState();
		private static final BlockState SUNSTONE_ORE = CABlocks.SUNSTONE_ORE.get().defaultBlockState();
		private static final BlockState BLOODSTONE_ORE = CABlocks.BLOODSTONE_ORE.get().defaultBlockState();
		private static final BlockState SALT_ORE = CABlocks.SALT_ORE.get().defaultBlockState();

		private static final BlockState FOSSILISED_ACACIA_ENT = CABlocks.FOSSILISED_ACACIA_ENT.get().defaultBlockState();
		private static final BlockState FOSSILISED_BIRCH_ENT = CABlocks.FOSSILISED_BIRCH_ENT.get().defaultBlockState();
		private static final BlockState FOSSILISED_DARK_OAK_ENT = CABlocks.FOSSILISED_DARK_OAK_ENT.get().defaultBlockState();
		private static final BlockState FOSSILISED_JUNGLE_ENT = CABlocks.FOSSILISED_JUNGLE_ENT.get().defaultBlockState();
		private static final BlockState FOSSILISED_OAK_ENT = CABlocks.FOSSILISED_OAK_ENT.get().defaultBlockState();
		private static final BlockState FOSSILISED_SPRUCE_ENT = CABlocks.FOSSILISED_SPRUCE_ENT.get().defaultBlockState();
		private static final BlockState FOSSILISED_HERCULES_BEETLE = CABlocks.FOSSILISED_HERCULES_BEETLE.get().defaultBlockState();
		private static final BlockState FOSSILISED_RUBY_BUG = CABlocks.FOSSILISED_RUBY_BUG.get().defaultBlockState();
		private static final BlockState FOSSILISED_EMERALD_GATOR = CABlocks.FOSSILISED_EMERALD_GATOR.get().defaultBlockState();
		private static final BlockState FOSSILISED_GREEN_FISH = CABlocks.FOSSILISED_GREEN_FISH.get().defaultBlockState();
		private static final BlockState FOSSILISED_ROCK_FISH = CABlocks.FOSSILISED_ROCK_FISH.get().defaultBlockState();
		private static final BlockState FOSSILISED_SPARK_FISH = CABlocks.FOSSILISED_SPARK_FISH.get().defaultBlockState();
		private static final BlockState FOSSILISED_WOOD_FISH = CABlocks.FOSSILISED_WOOD_FISH.get().defaultBlockState();
		private static final BlockState FOSSILISED_WHALE = CABlocks.FOSSILISED_WHALE.get().defaultBlockState();
		private static final BlockState FOSSILISED_WTF = CABlocks.FOSSILISED_WTF.get().defaultBlockState();
		private static final BlockState FOSSILISED_SCORPION = CABlocks.FOSSILISED_SCORPION.get().defaultBlockState();
		private static final BlockState FOSSILISED_WASP = CABlocks.FOSSILISED_WASP.get().defaultBlockState();
		private static final BlockState FOSSILISED_PIRAPORU = CABlocks.FOSSILISED_PIRAPORU.get().defaultBlockState();
		private static final BlockState FOSSILISED_APPLE_COW = CABlocks.FOSSILISED_APPLE_COW.get().defaultBlockState();
		private static final BlockState FOSSILISED_GOLDEN_APPLE_COW = CABlocks.FOSSILISED_GOLDEN_APPLE_COW.get().defaultBlockState();
		private static final BlockState FOSSILISED_CARROT_PIG = CABlocks.FOSSILISED_CARROT_PIG.get().defaultBlockState();
		private static final BlockState FOSSILISED_GOLDEN_CARROT_PIG = CABlocks.FOSSILISED_GOLDEN_CARROT_PIG.get().defaultBlockState();
		private static final BlockState FOSSILISED_BIRD = CABlocks.FOSSILISED_BIRD.get().defaultBlockState();
		private static final BlockState FOSSILISED_FROG = CABlocks.FOSSILISED_TREE_FROG.get().defaultBlockState();

		private static final BlockState FOSSILISED_BAT = CABlocks.FOSSILISED_BAT.get().defaultBlockState();
		private static final BlockState FOSSILISED_BEE = CABlocks.FOSSILISED_BEE.get().defaultBlockState();
		private static final BlockState FOSSILISED_CAVE_SPIDER = CABlocks.FOSSILISED_CAVE_SPIDER.get().defaultBlockState();
		private static final BlockState FOSSILISED_CHICKEN = CABlocks.FOSSILISED_CHICKEN.get().defaultBlockState();
		private static final BlockState FOSSILISED_COD = CABlocks.FOSSILISED_COD.get().defaultBlockState();
		private static final BlockState FOSSILISED_COW = CABlocks.FOSSILISED_COW.get().defaultBlockState();
		private static final BlockState FOSSILISED_CREEPER = CABlocks.FOSSILISED_CREEPER.get().defaultBlockState();
		private static final BlockState FOSSILISED_DOLPHIN = CABlocks.FOSSILISED_DOLPHIN.get().defaultBlockState();
		private static final BlockState FOSSILISED_DONKEY = CABlocks.FOSSILISED_DONKEY.get().defaultBlockState();
		private static final BlockState FOSSILISED_DROWNED = CABlocks.FOSSILISED_DROWNED.get().defaultBlockState();
		private static final BlockState FOSSILISED_ENDERMAN = CABlocks.FOSSILISED_ENDERMAN.get().defaultBlockState();
		private static final BlockState FOSSILISED_EVOKER = CABlocks.FOSSILISED_EVOKER.get().defaultBlockState();
		private static final BlockState FOSSILISED_FOX = CABlocks.FOSSILISED_FOX.get().defaultBlockState();
		private static final BlockState FOSSILISED_GIANT = CABlocks.FOSSILISED_GIANT.get().defaultBlockState();
		private static final BlockState FOSSILISED_GUARDIAN = CABlocks.FOSSILISED_GUARDIAN.get().defaultBlockState();
		private static final BlockState FOSSILISED_HORSE = CABlocks.FOSSILISED_HORSE.get().defaultBlockState();
		private static final BlockState FOSSILISED_HUSK = CABlocks.FOSSILISED_HUSK.get().defaultBlockState();
		private static final BlockState FOSSILISED_HUSK_SANDSTONE = CABlocks.FOSSILISED_HUSK_SANDSTONE.get().defaultBlockState();
		private static final BlockState FOSSILISED_ILLUSIONER = CABlocks.FOSSILISED_ILLUSIONER.get().defaultBlockState();
		private static final BlockState FOSSILISED_IRON_GOLEM = CABlocks.FOSSILISED_IRON_GOLEM.get().defaultBlockState();
		private static final BlockState FOSSILISED_LLAMA = CABlocks.FOSSILISED_LLAMA.get().defaultBlockState();
		private static final BlockState FOSSILISED_MOOSHROOM = CABlocks.FOSSILISED_MOOSHROOM.get().defaultBlockState();
		private static final BlockState FOSSILISED_OCELOT = CABlocks.FOSSILISED_OCELOT.get().defaultBlockState();
		private static final BlockState FOSSILISED_PANDA = CABlocks.FOSSILISED_PANDA.get().defaultBlockState();
		private static final BlockState FOSSILISED_PIG = CABlocks.FOSSILISED_PIG.get().defaultBlockState();
		private static final BlockState FOSSILISED_PHANTOM = CABlocks.FOSSILISED_PHANTOM.get().defaultBlockState();
		private static final BlockState FOSSILISED_PILLAGER = CABlocks.FOSSILISED_PILLAGER.get().defaultBlockState();
		private static final BlockState FROZEN_POLAR_BEAR = CABlocks.FROZEN_POLAR_BEAR.get().defaultBlockState();
		private static final BlockState FOSSILISED_PUFFERFISH = CABlocks.FOSSILISED_PUFFERFISH.get().defaultBlockState();
		private static final BlockState FOSSILISED_RABBIT = CABlocks.FOSSILISED_RABBIT.get().defaultBlockState();
		private static final BlockState FOSSILISED_RAVAGER = CABlocks.FOSSILISED_RAVAGER.get().defaultBlockState();
		private static final BlockState FOSSILISED_SALMON = CABlocks.FOSSILISED_SALMON.get().defaultBlockState();
		private static final BlockState FOSSILISED_SHEEP = CABlocks.FOSSILISED_SHEEP.get().defaultBlockState();
		private static final BlockState FOSSILISED_SKELETON = CABlocks.FOSSILISED_SKELETON.get().defaultBlockState();
		private static final BlockState FOSSILISED_SKELETON_HORSE = CABlocks.FOSSILISED_SKELETON_HORSE.get().defaultBlockState();
		private static final BlockState FOSSILISED_SLIME = CABlocks.FOSSILISED_SLIME.get().defaultBlockState();
		private static final BlockState FROZEN_SNOW_GOLEM = CABlocks.FROZEN_SNOW_GOLEM.get().defaultBlockState();
		private static final BlockState FOSSILISED_SPIDER = CABlocks.FOSSILISED_SPIDER.get().defaultBlockState();
		private static final BlockState FOSSILISED_SQUID = CABlocks.FOSSILISED_SQUID.get().defaultBlockState();
		private static final BlockState FROZEN_STRAY = CABlocks.FROZEN_STRAY.get().defaultBlockState();
		private static final BlockState FOSSILISED_TROPICAL_FISH = CABlocks.FOSSILISED_TROPICAL_FISH.get().defaultBlockState();
		private static final BlockState FOSSILISED_TURTLE = CABlocks.FOSSILISED_TURTLE.get().defaultBlockState();
		private static final BlockState FOSSILISED_VILLAGER = CABlocks.FOSSILISED_VILLAGER.get().defaultBlockState();
		private static final BlockState FOSSILISED_VINDICATOR = CABlocks.FOSSILISED_VINDICATOR.get().defaultBlockState();
		private static final BlockState FOSSILISED_WANDERING_TRADER = CABlocks.FOSSILISED_WANDERING_TRADER.get().defaultBlockState();
		private static final BlockState FOSSILISED_WITCH = CABlocks.FOSSILISED_WITCH.get().defaultBlockState();
		private static final BlockState FOSSILISED_WOLF = CABlocks.FOSSILISED_WOLF.get().defaultBlockState();
		private static final BlockState FOSSILISED_ZOMBIE = CABlocks.FOSSILISED_ZOMBIE.get().defaultBlockState();
		private static final BlockState FOSSILISED_ZOMBIE_HORSE = CABlocks.FOSSILISED_ZOMBIE_HORSE.get().defaultBlockState();

		private static final BlockState FOSSILISED_CRIMSON_ENT = CABlocks.FOSSILISED_CRIMSON_ENT.get().defaultBlockState();
		private static final BlockState FOSSILISED_WARPED_ENT = CABlocks.FOSSILISED_WARPED_ENT.get().defaultBlockState();
		private static final BlockState FOSSILISED_LAVA_EEL = CABlocks.FOSSILISED_LAVA_EEL.get().defaultBlockState();

		private static final BlockState FOSSILISED_BLAZE = CABlocks.FOSSILISED_BLAZE.get().defaultBlockState();
		private static final BlockState FOSSILISED_GHAST = CABlocks.FOSSILISED_GHAST.get().defaultBlockState();
		private static final BlockState FOSSILISED_HOGLIN = CABlocks.FOSSILISED_HOGLIN.get().defaultBlockState();
		private static final BlockState FOSSILISED_ENDERMAN_NETHERRACK = CABlocks.FOSSILISED_ENDERMAN_NETHERRACK.get().defaultBlockState();
		private static final BlockState FOSSILISED_MAGMA_CUBE_NETHERRACK = CABlocks.FOSSILISED_MAGMA_CUBE_NETHERRACK.get().defaultBlockState();
		private static final BlockState FOSSILISED_MAGMA_CUBE_BLACKSTONE = CABlocks.FOSSILISED_MAGMA_CUBE_BLACKSTONE.get().defaultBlockState();
		private static final BlockState FOSSILISED_PIGLIN = CABlocks.FOSSILISED_PIGLIN.get().defaultBlockState();
		private static final BlockState FOSSILISED_SKELETON_SOUL_SOIL = CABlocks.FOSSILISED_SKELETON_SOUL_SOIL.get().defaultBlockState();
		private static final BlockState FOSSILISED_STRIDER = CABlocks.FOSSILISED_STRIDER.get().defaultBlockState();
		private static final BlockState FOSSILISED_WITHER_SKELETON = CABlocks.FOSSILISED_WITHER_SKELETON.get().defaultBlockState();
		private static final BlockState FOSSILISED_ZOMBIFIED_PIGLIN = CABlocks.FOSSILISED_ZOMBIFIED_PIGLIN.get().defaultBlockState();

		private static final BlockState FOSSILISED_ENDERMAN_END_STONE = CABlocks.FOSSILISED_ENDERMAN_END_STONE.get().defaultBlockState();
		private static final BlockState FOSSILISED_ENDERMITE = CABlocks.FOSSILISED_ENDERMITE.get().defaultBlockState();
		private static final BlockState FOSSILISED_SHULKER = CABlocks.FOSSILISED_SHULKER.get().defaultBlockState();

		private static final BlockState CRYSTALISED_CRYSTAL_APPLE_COW = CABlocks.CRYSTALLISED_CRYSTAL_APPLE_COW.get().defaultBlockState();

		private static final BlockState FOSSILISED_DIMETRODON = CABlocks.FOSSILISED_DIMETRODON.get().defaultBlockState();

		private static final BlockState RED_ANT_INFESTED_ORE = CABlocks.RED_ANT_INFESTED_ORE.get().defaultBlockState();
		private static final BlockState TERMITE_INFESTED_ORE = CABlocks.TERMITE_INFESTED_ORE.get().defaultBlockState();
		private static final BlockState CRYSTAL_ENERGY = CABlocks.CRYSTAL_ENERGY.get().defaultBlockState();
		private static final BlockState PINK_TOURMALINE = CABlocks.BUDDING_PINK_TOURMALINE.get().defaultBlockState();
		private static final BlockState CATS_EYE = CABlocks.BUDDING_CATS_EYE.get().defaultBlockState();
		private static final BlockState CLUSTER_PINK_TOURMALINE = CABlocks.PINK_TOURMALINE_CLUSTER.get().defaultBlockState();
		private static final BlockState CLUSTER_CATS_EYE = CABlocks.CATS_EYE_CLUSTER.get().defaultBlockState();
		private static final BlockState CRYSTAL_LOG = CABlocks.CRYSTAL_LOG.get().defaultBlockState();
		private static final BlockState GREEN_CRYSTAL_LEAVES = CABlocks.GREEN_CRYSTAL_LEAVES.get().defaultBlockState();
		private static final BlockState RED_CRYSTAL_LEAVES = CABlocks.RED_CRYSTAL_LEAVES.get().defaultBlockState();
		private static final BlockState YELLOW_CRYSTAL_LEAVES = CABlocks.YELLOW_CRYSTAL_LEAVES.get().defaultBlockState();

		private static final BlockState CYAN_ROSE = CABlocks.CYAN_ROSE.get().defaultBlockState();
		private static final BlockState RED_ROSE = CABlocks.RED_ROSE.get().defaultBlockState();
		private static final BlockState PAEONIA = CABlocks.PAEONIA.get().defaultBlockState();

		private static final BlockState RED_CRYSTAL_FLOWER = CABlocks.RED_CRYSTAL_FLOWER.get().defaultBlockState();
		private static final BlockState BLUE_CRYSTAL_FLOWER = CABlocks.BLUE_CRYSTAL_FLOWER.get().defaultBlockState();
		private static final BlockState GREEN_CRYSTAL_FLOWER = CABlocks.GREEN_CRYSTAL_FLOWER.get().defaultBlockState();
		private static final BlockState YELLOW_CRYSTAL_FLOWER = CABlocks.YELLOW_CRYSTAL_FLOWER.get().defaultBlockState();
		private static final BlockState RED_CRYSTAL_GROWTH = CABlocks.RED_CRYSTAL_GROWTH.get().defaultBlockState();
		private static final BlockState BLUE_CRYSTAL_GROWTH = CABlocks.BLUE_CRYSTAL_GROWTH.get().defaultBlockState();
		private static final BlockState GREEN_CRYSTAL_GROWTH = CABlocks.GREEN_CRYSTAL_GROWTH.get().defaultBlockState();
		private static final BlockState YELLOW_CRYSTAL_GROWTH = CABlocks.YELLOW_CRYSTAL_GROWTH.get().defaultBlockState();
		private static final BlockState ORANGE_CRYSTAL_GROWTH = CABlocks.ORANGE_CRYSTAL_GROWTH.get().defaultBlockState();
		private static final BlockState PINK_CRYSTAL_GROWTH = CABlocks.PINK_CRYSTAL_GROWTH.get().defaultBlockState();

		private static final BlockState THORNY_SUN = CABlocks.THORNY_SUN.get().defaultBlockState();
		private static final BlockState BLUE_BULB = CABlocks.BLUE_BULB.get().defaultBlockState();
		private static final BlockState PINK_BULB = CABlocks.PINK_BULB.get().defaultBlockState();
		private static final BlockState PURPLE_BULB = CABlocks.PURPLE_BULB.get().defaultBlockState();

		private static final BlockState STRAWBERRY_BUSH = CABlocks.STRAWBERRY_BUSH.get().defaultBlockState().setValue(SweetBerryBushBlock.AGE, 3);
		private static final BlockState CORN = CABlocks.CORN_BODY_BLOCK.get().defaultBlockState();
		private static final BlockState TOMATO = CABlocks.TOMATO_BODY_BLOCK.get().defaultBlockState();

		private static final BlockState BROWN_ANT_NEST = CABlocks.BROWN_ANT_NEST.get().defaultBlockState();
		private static final BlockState RAINBOW_ANT_NEST = CABlocks.RAINBOW_ANT_NEST.get().defaultBlockState();
		private static final BlockState RED_ANT_NEST = CABlocks.RED_ANT_NEST.get().defaultBlockState();
		private static final BlockState DENSE_RED_ANT_NEST = CABlocks.DENSE_RED_ANT_NEST.get().defaultBlockState();
		private static final BlockState UNSTABLE_ANT_NEST = CABlocks.UNSTABLE_ANT_NEST.get().defaultBlockState();
		private static final BlockState TERMITE_NEST = CABlocks.TERMITE_NEST.get().defaultBlockState();
		private static final BlockState CRYSTAL_TERMITE_NEST = CABlocks.CRYSTAL_TERMITE_NEST.get().defaultBlockState();
	}

	public static final class Configs {
		public static final BlockClusterFeatureConfig CHAOS_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).add(States.CYAN_ROSE, 2).add(States.RED_ROSE, 2).add(States.PAEONIA, 2), SimpleBlockPlacer.INSTANCE)).tries(32).build();
		public static final BlockClusterFeatureConfig DENSE_GRASS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.DENSE_GRASS), SimpleBlockPlacer.INSTANCE)).tries(32).build();
		public static final BlockClusterFeatureConfig TALL_DENSE_GRASS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.TALL_DENSE_GRASS), new DoubleDensePlantBlockPlacer())).tries(64).noProjection().build();
		public static final BlockClusterFeatureConfig THORNY_SUN_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.THORNY_SUN), new DoubleDensePlantBlockPlacer())).tries(32).noProjection().build();
		public static final BlockClusterFeatureConfig DENSE_BULB_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).add(States.BLUE_BULB, 2).add(States.PINK_BULB, 2).add(States.PURPLE_BULB, 2), SimpleBlockPlacer.INSTANCE)).tries(32).build();
		public static final BlockClusterFeatureConfig STRAWBERRY_BUSH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.STRAWBERRY_BUSH), SimpleBlockPlacer.INSTANCE)).tries(64).whitelist(ImmutableSet.of(States.GRASS_BLOCK.getBlock())).noProjection().build();
		public static final BlockClusterFeatureConfig CRYSTAL_GRASS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.CRYSTAL_GRASS), SimpleBlockPlacer.INSTANCE)).tries(32).build();
		public static final BlockClusterFeatureConfig TALL_CRYSTAL_GRASS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.TALL_CRYSTAL_GRASS), new DoubleCrystalPlantBlockPlacer())).tries(64).noProjection().build();
		public static final BlockClusterFeatureConfig CRYSTAL_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).add(States.RED_CRYSTAL_FLOWER, 2).add(States.BLUE_CRYSTAL_FLOWER, 2).add(States.GREEN_CRYSTAL_FLOWER, 2).add(States.YELLOW_CRYSTAL_FLOWER, 2), SimpleBlockPlacer.INSTANCE)).tries(32).build();
		public static final BlockClusterFeatureConfig CRYSTAL_GROWTH_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).add(States.RED_CRYSTAL_GROWTH, 2).add(States.BLUE_CRYSTAL_GROWTH, 2).add(States.GREEN_CRYSTAL_GROWTH, 2).add(States.YELLOW_CRYSTAL_GROWTH, 2).add(States.ORANGE_CRYSTAL_GROWTH, 2).add(States.PINK_CRYSTAL_GROWTH, 2), SimpleBlockPlacer.INSTANCE)).tries(32).build();
	}

	public static final class Placements {
		public static final BeehiveTreeDecorator BEEHIVE_005 = new BeehiveTreeDecorator(0.05f);
	}

	public static final class RuleTests {
		public static final RuleTest BASE_STONE_CRYSTAL = new TagMatchRuleTest(CATags.Blocks.BASE_STONE_CRYSTAL);
		public static final RuleTest BASE_OCEAN_FLOOR = new BlockMatchRuleTest(Blocks.GRAVEL);
		public static final RuleTest BASE_DESERT = new BlockMatchRuleTest(Blocks.SANDSTONE);
		public static final RuleTest BASE_BLACKSTONE = new BlockMatchRuleTest(Blocks.BLACKSTONE);
		public static final RuleTest BASE_SOUL_SOIL = new BlockMatchRuleTest(Blocks.SOUL_SOIL);
		public static final RuleTest BASE_LAVA = new BlockMatchRuleTest(Blocks.LAVA);
		public static final RuleTest BASE_END_STONE = new BlockMatchRuleTest(Blocks.END_STONE);
		public static final RuleTest BASE_PACKED_ICE = new BlockMatchRuleTest(Blocks.PACKED_ICE);
	}
}
