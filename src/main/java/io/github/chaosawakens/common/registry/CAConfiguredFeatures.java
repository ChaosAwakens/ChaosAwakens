package io.github.chaosawakens.common.registry;

import java.util.Optional;
import java.util.OptionalInt;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import io.github.chaosawakens.api.wrapper.FeatureWrapper;
import io.github.chaosawakens.common.events.CACommonSetupEvents;
import io.github.chaosawakens.common.worldgen.feature.CrystalBranchConfig;
import io.github.chaosawakens.common.worldgen.feature.GeodeFeatureConfig;
import io.github.chaosawakens.common.worldgen.feature.StalagmiteFeatureConfig;
import io.github.chaosawakens.common.worldgen.foliageplacer.ConiferousFoliagePlacer;
import io.github.chaosawakens.common.worldgen.foliageplacer.CubicSkipFoliagePlacer;
import io.github.chaosawakens.common.worldgen.foliageplacer.SpheroidFoliagePlacer;
import io.github.chaosawakens.common.worldgen.placement.DoubleCrystalPlantBlockPlacer;
import io.github.chaosawakens.common.worldgen.placement.DoubleDensePlantBlockPlacer;
import io.github.chaosawakens.common.worldgen.placement.OceanBedPlacement;
import io.github.chaosawakens.common.worldgen.treedecorator.LeafCarpetTreeDecorator;
import io.github.chaosawakens.common.worldgen.treedecorator.VinesBelowLeavesTreeDecorator;
import io.github.chaosawakens.common.worldgen.trunkplacer.CrystalTrunkPlacer;
import io.github.chaosawakens.common.worldgen.trunkplacer.DirtlessGiantTrunkPlacer;
import io.github.chaosawakens.common.worldgen.trunkplacer.GiantConiferTrunkPlacer;
import io.github.chaosawakens.common.worldgen.trunkplacer.ThinGiantTrunkPlacer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.ColumnBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.common.Tags;

public class CAConfiguredFeatures {
	// ORES
	// GENERIC
	public static final ConfiguredFeature<?, ?> ORE_RUBY_LAVA = registerFeature("ore_ruby", Feature.NO_SURFACE_ORE.configured(new OreFeatureConfig(RuleTests.BASE_LAVA, States.RUBY_ORE, 8)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(6, 12))).squared().count(3));
	public static final ConfiguredFeature<?, ?> GEODE_AMETHYST = registerFeature("geode_amethyst", CAFeatures.GEODE.get().configured( new GeodeFeatureConfig(States.AMETHYST_ORE, States.MARBLE, FeatureSpread.of(6, 4), FeatureSpread.fixed(10))).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(16, 8))).chance(20));
	public static final ConfiguredFeature<?, ?> ORE_URANIUM = registerFeature("ore_uranium", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.URANIUM_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(1, 12))).squared().count(3));
	public static final ConfiguredFeature<?, ?> ORE_TITANIUM = registerFeature("ore_titanium", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.TITANIUM_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(1, 12))).squared().count(3));
	public static final ConfiguredFeature<?, ?> ORE_TIGERS_EYE = registerFeature("ore_tigers_eye", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.TIGERS_EYE_ORE, 7)).decorated(Placement.RANGE_BIASED.configured(new TopSolidRangeConfig(1, 24, 48))).squared().count(5));
	public static final ConfiguredFeature<?, ?> ORE_SALT = registerFeature("ore_salt", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.SALT_ORE, 8)).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(32, 64, 128))).squared().count(14));
	public static final ConfiguredFeature<?, ?> ORE_ALUMINUM = registerFeature("ore_aluminum", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.ALUMINUM_ORE, 15)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(50, 30))).squared().count(2));

	public static final ConfiguredFeature<?, ?> ORE_COPPER = registerFeature("ore_copper", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.COPPER_ORE, 5)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(72, 32))).squared().count(6));
	public static final ConfiguredFeature<?, ?> ORE_TIN = registerFeature("ore_tin", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.TIN_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(56, 24))).squared().count(5));
	public static final ConfiguredFeature<?, ?> ORE_SILVER = registerFeature("ore_silver", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.SILVER_ORE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(32, 20))).squared().count(4));
	public static final ConfiguredFeature<?, ?> ORE_PLATINUM = registerFeature("ore_platinum", Feature.NO_SURFACE_ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.PLATINUM_ORE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(8, 16))).squared().count(3));
	public static final ConfiguredFeature<?, ?> ORE_SUNSTONE = registerFeature("ore_sunstone", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.SUNSTONE_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(8));
	public static final ConfiguredFeature<?, ?> ORE_BLOODSTONE = registerFeature("ore_bloodstone", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.BLOODSTONE_ORE, 5)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(8));

	public static final ConfiguredFeature<?, ?> FOSSILISED_ACACIA_ENT = registerFeature("ore_fossilised_acacia_ent", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_ACACIA_ENT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_BIRCH_ENT = registerFeature("ore_fossilised_birch_ent", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_BIRCH_ENT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_DARK_OAK_ENT = registerFeature("ore_fossilised_dark_oak_ent", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_DARK_OAK_ENT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_JUNGLE_ENT = registerFeature("ore_fossilised_jungle_ent", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_JUNGLE_ENT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_OAK_ENT = registerFeature("ore_fossilised_oak_ent", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_OAK_ENT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_SPRUCE_ENT = registerFeature("ore_fossilised_spruce_ent", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SPRUCE_ENT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_HERCULES_BEETLE = registerFeature("ore_fossilised_hercules_beetle", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_HERCULES_BEETLE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_BEAVER = registerFeature("ore_fossilised_beaver", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_BEAVER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_RUBY_BUG = registerFeature("ore_fossilised_ruby_bug", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_RUBY_BUG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_EMERALD_GATOR = registerFeature("ore_fossilised_emerald_gator", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_EMERALD_GATOR, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_GREEN_FISH = registerFeature("ore_fossilised_green_fish", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_GREEN_FISH, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_ROCK_FISH = registerFeature("ore_fossilised_rock_fish", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_ROCK_FISH, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_SPARK_FISH = registerFeature("ore_fossilised_spark_fish", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_SPARK_FISH, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_WOOD_FISH = registerFeature("ore_fossilised_wood_fish", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_WOOD_FISH, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_WHALE = registerFeature("ore_fossilised_whale", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_WHALE, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_WTF = registerFeature("ore_fossilised_wtf", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WTF, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_SCORPION = registerFeature("ore_fossilised_scorpion", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SCORPION, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_WASP = registerFeature("ore_fossilised_wasp", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WASP, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_PIRAPORU = registerFeature("ore_fossilised_piraporu", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_PIRAPORU, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_APPLE_COW = registerFeature("ore_fossilised_apple_cow", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_APPLE_COW, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_GOLDEN_APPLE_COW = registerFeature("ore_fossilised_golden_apple_cow", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_GOLDEN_APPLE_COW, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_CARROT_PIG = registerFeature("ore_fossilised_carrot_pig", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_CARROT_PIG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_GOLDEN_CARROT_PIG = registerFeature("ore_fossilised_golden_carrot_pig", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_GOLDEN_CARROT_PIG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_LETTUCE_CHICKEN = registerFeature("ore_fossilised_lettuce_chicken", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_LETTUCE_CHICKEN, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_BIRD = registerFeature("ore_fossilised_bird", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_BIRD, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_TREE_FROG = registerFeature("ore_fossilised_tree_frog", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_TREE_FROG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));

	public static final ConfiguredFeature<?, ?> FOSSILISED_BAT = registerFeature("ore_fossilised_bat", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_BAT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_BEE = registerFeature("ore_fossilised_bee", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_BEE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_CAVE_SPIDER = registerFeature("ore_fossilised_cave_spider", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_CAVE_SPIDER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_CHICKEN = registerFeature("ore_fossilised_chicken", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_CHICKEN, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_COD = registerFeature("ore_fossilised_cod", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_COD, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_COW = registerFeature("ore_fossilised_cow", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_COW, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_CREEPER = registerFeature("ore_fossilised_creeper", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_CREEPER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_DOLPHIN = registerFeature("ore_fossilised_dolphin", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_DOLPHIN, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_DONKEY = registerFeature("ore_fossilised_donkey", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_DONKEY, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_DROWNED = registerFeature("ore_fossilised_drowned", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_DROWNED, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_ENDERMAN = registerFeature("ore_fossilised_enderman", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_ENDERMAN, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_EVOKER = registerFeature("ore_fossilised_evoker", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_EVOKER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_FOX = registerFeature("ore_fossilised_fox", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_FOX, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_GUARDIAN = registerFeature("ore_fossilised_guardian", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_GUARDIAN, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_HORSE = registerFeature("ore_fossilised_horse", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_HORSE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_HUSK = registerFeature("ore_fossilised_husk", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_HUSK, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_HUSK_SANDSTONE = registerFeature("ore_fossilised_husk_sandstone", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_DESERT, States.FOSSILISED_HUSK_SANDSTONE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(72, 30))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_ILLUSIONER = registerFeature("ore_fossilised_illusioner", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_ILLUSIONER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_IRON_GOLEM = registerFeature("ore_fossilised_iron_golem", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_IRON_GOLEM, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_LLAMA = registerFeature("ore_fossilised_llama", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_LLAMA, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_MOOSHROOM = registerFeature("ore_fossilised_mooshroom", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_MOOSHROOM, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_OCELOT = registerFeature("ore_fossilised_ocelot", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_OCELOT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_PANDA = registerFeature("ore_fossilised_panda", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_PANDA, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_PHANTOM = registerFeature("ore_fossilised_phantom", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_PHANTOM, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(92, 30))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_PIG = registerFeature("ore_fossilised_pig", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_PIG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_PILLAGER = registerFeature("ore_fossilised_pillager", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_PILLAGER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FROZEN_POLAR_BEAR = registerFeature("ore_frozen_polar_bear", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_PACKED_ICE, States.FROZEN_POLAR_BEAR, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(70, 30))).squared().count(9));
	public static final ConfiguredFeature<?, ?> FOSSILISED_PUFFERFISH = registerFeature("ore_fossilised_pufferfish", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_PUFFERFISH, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_RABBIT = registerFeature("ore_fossilised_rabbit", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_RABBIT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_RAVAGER = registerFeature("ore_fossilised_ravager", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_RAVAGER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_SALMON = registerFeature("ore_fossilised_salmon", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_SALMON, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_SHEEP = registerFeature("ore_fossilised_sheep", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SHEEP, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_SKELETON = registerFeature("ore_fossilised_skeleton", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SKELETON, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_SKELETON_HORSE = registerFeature("ore_fossilised_skeleton_horse", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SKELETON_HORSE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_SLIME = registerFeature("ore_fossilised_slime", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SLIME, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FROZEN_SNOW_GOLEM = registerFeature("ore_frozen_snow_golem", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_PACKED_ICE, States.FROZEN_SNOW_GOLEM, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(70, 30))).squared().count(9));
	public static final ConfiguredFeature<?, ?> FOSSILISED_SPIDER = registerFeature("ore_fossilised_spider", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SPIDER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_SQUID = registerFeature("ore_fossilised_squid", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_SQUID, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FROZEN_STRAY = registerFeature("ore_frozen_stray", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_PACKED_ICE, States.FROZEN_STRAY, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(70, 30))).squared().count(9));
	public static final ConfiguredFeature<?, ?> FOSSILISED_TROPICAL_FISH = registerFeature("ore_fossilised_tropical_fish", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_TROPICAL_FISH, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_TURTLE = registerFeature("ore_fossilised_turtle", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_OCEAN_FLOOR, States.FOSSILISED_TURTLE, 3)).decorated(OceanBedPlacement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 32))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_VILLAGER = registerFeature("ore_fossilised_villager", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_VILLAGER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_VINDICATOR = registerFeature("ore_fossilised_vindicator", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_VINDICATOR, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_WANDERING_TRADER = registerFeature("ore_fossilised_wandering_trader", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WANDERING_TRADER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_WITCH = registerFeature("ore_fossilised_witch", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WITCH, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_WOLF = registerFeature("ore_fossilised_wolf", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WOLF, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_ZOMBIE = registerFeature("ore_fossilised_zombie", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_ZOMBIE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));
	public static final ConfiguredFeature<?, ?> FOSSILISED_ZOMBIE_HORSE = registerFeature("ore_fossilised_zombie_horse", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_ZOMBIE_HORSE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(4));

	public static final ConfiguredFeature<?, ?> RED_ANT_INFESTED = registerFeature("ore_red_ant_infested", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.RED_ANT_INFESTED_ORE, 8)).range(16).squared());
	public static final ConfiguredFeature<?, ?> TERMITE_INFESTED = registerFeature("ore_termite_infested", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.STONE, States.TERMITE_INFESTED_ORE)).decorated(Placement.EMERALD_ORE.configured(IPlacementConfig.NONE)));

	// NETHER
	public static final ConfiguredFeature<?, ?> NETHERRACK_ORE_RUBY_LAVA = registerFeature("netherrack_ore_ruby_lava", Feature.NO_SURFACE_ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.NETHERRACK_RUBY_ORE, 6)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(26, 8))).squared().chance(2));
	public static final ConfiguredFeature<?, ?> NETHERRACK_ORE_RUBY = registerFeature("netherrack_ore_ruby", Feature.NO_SURFACE_ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.NETHERRACK_RUBY_ORE, 8)).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(100, 0, 22))).squared().count(1));
	public static final ConfiguredFeature<?, ?> BLACKSTONE_ORE_RUBY = registerFeature("blackstone_ore_ruby", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_BLACKSTONE, States.BLACKSTONE_RUBY_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));

	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_CRIMSON_ENT = registerFeature("nether_ore_fossilised_crimson_ent", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_CRIMSON_ENT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_WARPED_ENT = registerFeature("nether_ore_fossilised_warped_ent", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_WARPED_ENT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_LAVA_EEL = registerFeature("nether_ore_fossilised_lava_eel", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_LAVA_EEL, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));

	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_BLAZE = registerFeature("nether_ore_fossilised_blaze", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_BLAZE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_GHAST = registerFeature("nether_ore_fossilised_ghast", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_GHAST, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_HOGLIN = registerFeature("nether_ore_fossilised_hoglin", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_HOGLIN, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_ENDERMAN_NETHERRACK = registerFeature("nether_ore_fossilised_enderman_netherrack", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_ENDERMAN_NETHERRACK, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_MAGMA_CUBE_NETHERRACK = registerFeature("nether_ore_fossilised_magma_cube_netherrack", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_MAGMA_CUBE_NETHERRACK, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_MAGMA_CUBE_BLACKSTONE = registerFeature("nether_ore_fossilised_magma_cube_blackstone", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_BLACKSTONE, States.FOSSILISED_MAGMA_CUBE_BLACKSTONE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_PIGLIN = registerFeature("nether_ore_fossilised_piglin", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_PIGLIN, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_SKELETON_SOUL_SOIL = registerFeature("nether_ore_fossilised_skeleton_soul_soil", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_SOUL_SOIL, States.FOSSILISED_SKELETON_SOUL_SOIL, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_STRIDER = registerFeature("nether_ore_fossilised_strider", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_STRIDER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_WITHER_SKELETON = registerFeature("nether_ore_fossilised_wither_skeleton", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_WITHER_SKELETON, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));
	public static final ConfiguredFeature<?, ?> NETHER_FOSSILISED_ZOMBIFIED_PIGLIN = registerFeature("nether_ore_fossilised_zombified_piglin", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, States.FOSSILISED_ZOMBIFIED_PIGLIN, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 64))).squared().count(6));

	// END
	public static final ConfiguredFeature<?, ?> END_FOSSILISED_ENDERMAN_END_STONE = registerFeature("end_ore_fossilised_enderman_end_stone", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_END_STONE, States.FOSSILISED_ENDERMAN_END_STONE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(37, 27))).squared().count(8));
	public static final ConfiguredFeature<?, ?> END_FOSSILISED_ENDERMITE = registerFeature("end_ore_fossilised_endermite", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_END_STONE, States.FOSSILISED_ENDERMITE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(37, 27))).squared().count(8));
	public static final ConfiguredFeature<?, ?> END_FOSSILISED_SHULKER = registerFeature("end_ore_fossilised_shulker", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_END_STONE, States.FOSSILISED_SHULKER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(37, 27))).squared().count(8));

	// MINING PARADISE
	public static final ConfiguredFeature<?, ?> STALAGMITE_COMMON = registerFeature("stalagmite_common", CAFeatures.STALAGMITE
			.get().configured(new StalagmiteFeatureConfig(Blocks.STONE.defaultBlockState(), 4, 1.8F, 2, 
					Optional.of(CATags.Blocks.STALAGMITE_ORE_COMMON), 0.025f)).decorated(Features.Placements.HEIGHTMAP_SQUARE)
			.chance(2));
	public static final ConfiguredFeature<?, ?> STALAGMITE_COMMON_SHORT = registerFeature("stalagmite_common_short", CAFeatures
			.STALAGMITE.get().configured(new StalagmiteFeatureConfig(Blocks.STONE.defaultBlockState(), 4, 1.25F, 2,
					Optional.of(CATags.Blocks.STALAGMITE_ORE_COMMON), 0.025f)).decorated(Features.Placements.HEIGHTMAP_SQUARE)
			.chance(2));
	public static final ConfiguredFeature<?, ?> STALAGMITE_SKYSCRAPER = registerFeature("stalagmite_skyscraper", CAFeatures.
			STALAGMITE.get().configured(new StalagmiteFeatureConfig(Blocks.STONE.defaultBlockState(), 7, 2.25F, 0.5f,
					Optional.of(CATags.Blocks.STALAGMITE_ORE_COMMON), 0.05f)).decorated(Features.Placements.HEIGHTMAP_SQUARE)
			.chance(16));
	public static final ConfiguredFeature<?, ?> STALAGMITE_CONE = registerFeature("stalagmite_cone", CAFeatures.STALAGMITE.get()
			.configured(new StalagmiteFeatureConfig(Blocks.STONE.defaultBlockState(), 7, 0.15F, 1,
					Optional.of(CATags.Blocks.STALAGMITE_ORE_COMMON), 0.025f)).decorated(Features.Placements.HEIGHTMAP_SQUARE)
			.chance(3));
	
	public static final ConfiguredFeature<?, ?> STALAGMITE_LIMESTONE = registerFeature("stalagmite_limestone",
			CAFeatures.STALAGMITE.get().configured(new StalagmiteFeatureConfig(CABlocks.LIMESTONE.get()
					.defaultBlockState(), 12, 1.35F, 0.5f, Optional.of(CATags.Blocks.STALAGMITE_ORE_RARE), 0.05f))
			.decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(192));
	
	public static final ConfiguredFeature<?, ?> MINING_ORE_RUBY_LAVA = registerFeature("mining_ore_ruby", Feature.NO_SURFACE_ORE.configured(new OreFeatureConfig(RuleTests.BASE_LAVA, States.RUBY_ORE, 8)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(6, 12))).squared().count(4));
	public static final ConfiguredFeature<?, ?> MINING_ORE_AMETHYST = registerFeature("mining_ore_amethyst", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.AMETHYST_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(40, 16))).squared().count(4));
	public static final ConfiguredFeature<?, ?> MINING_ORE_URANIUM = registerFeature("mining_ore_uranium", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.URANIUM_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(1, 18))).squared().count(3));
	public static final ConfiguredFeature<?, ?> MINING_ORE_TITANIUM = registerFeature("mining_ore_titanium", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.TITANIUM_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(1, 18))).squared().count(3));
	public static final ConfiguredFeature<?, ?> MINING_ORE_TIGERS_EYE = registerFeature("mining_ore_tigers_eye", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.TIGERS_EYE_ORE, 7)).decorated(Placement.RANGE_BIASED.configured(new TopSolidRangeConfig(1, 32, 60))).squared().count(5));
	public static final ConfiguredFeature<?, ?> MINING_ORE_SALT = registerFeature("mining_ore_salt", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.SALT_ORE, 8)).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(32, 24, 144))).squared().count(12));
	public static final ConfiguredFeature<?, ?> MINING_ORE_ALUMINUM = registerFeature("mining_ore_aluminum", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.ALUMINUM_ORE, 15)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(80, 40))).squared().count(6));

	public static final ConfiguredFeature<?, ?> MINING_ORE_COPPER = registerFeature("mining_ore_copper", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.COPPER_ORE, 5)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 30))).squared().count(6));
	public static final ConfiguredFeature<?, ?> MINING_ORE_TIN = registerFeature("mining_ore_tin", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.TIN_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 30))).squared().count(5));
	public static final ConfiguredFeature<?, ?> MINING_ORE_SILVER = registerFeature("mining_ore_silver", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.SILVER_ORE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(32, 24))).squared().count(4));
	public static final ConfiguredFeature<?, ?> MINING_ORE_PLATINUM = registerFeature("mining_ore_platinum", Feature.NO_SURFACE_ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.PLATINUM_ORE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(8, 20))).squared().count(3));
	public static final ConfiguredFeature<?, ?> MINING_ORE_SUNSTONE = registerFeature("mining_ore_sunstone", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.SUNSTONE_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(72, 64))).squared().count(8));
	public static final ConfiguredFeature<?, ?> MINING_ORE_BLOODSTONE = registerFeature("mining_ore_bloodstone", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.BLOODSTONE_ORE, 5)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(72, 64))).squared().count(8));

	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_HERCULES_BEETLE = registerFeature("mining_ore_fossilised_hercules_beetle", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_HERCULES_BEETLE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_BEAVER = registerFeature("mining_ore_fossilised_beaver", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_BEAVER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_RUBY_BUG = registerFeature("mining_ore_fossilised_ruby_bug", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_RUBY_BUG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_EMERALD_GATOR = registerFeature("mining_ore_fossilised_emerald_gator", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_EMERALD_GATOR, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_WTF = registerFeature("mining_ore_fossilised_wtf", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WTF, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_SCORPION = registerFeature("mining_ore_fossilised_scorpion", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SCORPION, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_WASP = registerFeature("mining_ore_fossilised_wasp", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WASP, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_PIRAPORU = registerFeature("mining_ore_fossilised_piraporu", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_PIRAPORU, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_APPLE_COW = registerFeature("mining_ore_fossilised_apple_cow", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_APPLE_COW, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_GOLDEN_APPLE_COW = registerFeature("mining_ore_fossilised_golden_apple_cow", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_GOLDEN_APPLE_COW, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_CARROT_PIG = registerFeature("mining_ore_fossilised_carrot_pig", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_CARROT_PIG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_GOLDEN_CARROT_PIG = registerFeature("mining_ore_fossilised_golden_carrot_pig", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_GOLDEN_CARROT_PIG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_LETTUCE_CHICKEN = registerFeature("mining_ore_fossilised_lettuce_chicken", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_LETTUCE_CHICKEN, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_BIRD = registerFeature("mining_ore_fossilised_bird", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_BIRD, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_DIMETRODON = registerFeature("mining_ore_fossilised_dimetrodon", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_DIMETRODON, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_TREE_FROG = registerFeature("mining_ore_fossilised_tree_frog", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_TREE_FROG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));

	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_BAT = registerFeature("mining_ore_fossilised_bat", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_BAT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_BEE = registerFeature("mining_ore_fossilised_bee", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_BEE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_CAVE_SPIDER = registerFeature("mining_ore_fossilised_cave_spider", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_CAVE_SPIDER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_CHICKEN = registerFeature("mining_ore_fossilised_chicken", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_CHICKEN, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_COW = registerFeature("mining_ore_fossilised_cow", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_COW, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_CREEPER = registerFeature("mining_ore_fossilised_creeper", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_CREEPER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_DONKEY = registerFeature("mining_ore_fossilised_donkey", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_DONKEY, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_ENDERMAN = registerFeature("mining_ore_fossilised_enderman", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_ENDERMAN, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_EVOKER = registerFeature("mining_ore_fossilised_evoker", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_EVOKER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_FOX = registerFeature("mining_ore_fossilised_fox", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_FOX, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_GIANT = registerFeature("mining_ore_fossilised_giant", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_GIANT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_ILLUSIONER = registerFeature("mining_ore_fossilised_illusioner", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_ILLUSIONER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_IRON_GOLEM = registerFeature("mining_ore_fossilised_iron_golem", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_IRON_GOLEM, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_LLAMA = registerFeature("mining_ore_fossilised_llama", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_LLAMA, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_MOOSHROOM = registerFeature("mining_ore_fossilised_mooshroom", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_MOOSHROOM, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_OCELOT = registerFeature("mining_ore_fossilised_ocelot", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_OCELOT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_PANDA = registerFeature("mining_ore_fossilised_panda", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_PANDA, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_PIG = registerFeature("mining_ore_fossilised_pig", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_PIG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_PILLAGER = registerFeature("mining_ore_fossilised_pillager", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_PILLAGER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_RABBIT = registerFeature("mining_ore_fossilised_rabbit", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_RABBIT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_RAVAGER = registerFeature("mining_ore_fossilised_ravager", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_RAVAGER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_SHEEP = registerFeature("mining_ore_fossilised_sheep", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SHEEP, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_SKELETON = registerFeature("mining_ore_fossilised_skeleton", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SKELETON, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_SKELETON_HORSE = registerFeature("mining_ore_fossilised_skeleton_horse", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SKELETON_HORSE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_SLIME = registerFeature("mining_ore_fossilised_slime", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SLIME, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_SPIDER = registerFeature("mining_ore_fossilised_spider", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SPIDER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_VILLAGER = registerFeature("mining_ore_fossilised_villager", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_VILLAGER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_VINDICATOR = registerFeature("mining_ore_fossilised_vindicator", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_VINDICATOR, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_WANDERING_TRADER = registerFeature("mining_ore_fossilised_wandering_trader", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WANDERING_TRADER, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_WITCH = registerFeature("mining_ore_fossilised_witch", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WITCH, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_WOLF = registerFeature("mining_ore_fossilised_wolf", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WOLF, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_ZOMBIE = registerFeature("mining_ore_fossilised_zombie", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_ZOMBIE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_ZOMBIE_HORSE = registerFeature("mining_ore_fossilised_zombie_horse", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_ZOMBIE_HORSE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 60))).squared().count(2));

	public static final ConfiguredFeature<?, ?> MINING_RED_ANT_INFESTED = registerFeature("mining_ore_red_ant_infested", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.RED_ANT_INFESTED_ORE, 8)).range(16).squared());
	public static final ConfiguredFeature<?, ?> MINING_TERMITE_INFESTED = registerFeature("mining_ore_termite_infested", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.STONE, States.TERMITE_INFESTED_ORE)).decorated(Placement.EMERALD_ORE.configured(IPlacementConfig.NONE)));

	public static final ConfiguredFeature<?, ?> MINING_MARBLE_CAVE_PATCH = registerFeature("mining_marble_cave_patch", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.MARBLE, 40)).squared().range(128).count(6));
	public static final ConfiguredFeature<?, ?> MINING_LIMESTONE_CAVE_PATCH = registerFeature("mining_limestone_cave_patch", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.LIMESTONE, 36)).squared().range(116).count(3));
	public static final ConfiguredFeature<?, ?> MINING_RHINESTONE_CAVE_PATCH = registerFeature("mining_rhinestone_cave_patch", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.RHINESTONE, 38)).squared().range(114).count(4));
	
	public static final ConfiguredFeature<?, ?> BLOB_TERRA_PRETA = registerFeature("blob_terra_preta", Feature.ORE.configured( new OreFeatureConfig(RuleTests.BASE_DIRT, States.TERRA_PRETA, 24)).decorated( Placement.RANGE.configured( new TopSolidRangeConfig(75, 0, 30))).squared().chance(3));
	public static final ConfiguredFeature<?, ?> BLOB_LATOSOL = registerFeature("blob_latosol", Feature.ORE.configured( new OreFeatureConfig(RuleTests.BASE_DIRT, States.LATOSOL, 24)).decorated( Placement.RANGE.configured( new TopSolidRangeConfig(75, 0, 30))).squared());
	public static final ConfiguredFeature<?, ?> BLOB_TAR = registerFeature("blob_tar", Feature.ORE.configured( new OreFeatureConfig(RuleTests.BASE_DIRT, States.TAR, 20)).decorated( Placement.RANGE.configured( new TopSolidRangeConfig(75, 0, 30))).squared());
	
	public static final ConfiguredFeature<?, ?> MOUNTAINS_STALAGMITE = registerFeature("mountains_stalagmite", CAFeatures.
			STALAGMITE.get().configured(new StalagmiteFeatureConfig(Blocks.STONE.defaultBlockState(), 4, 0.35F, 1, 
					Optional.of(CATags.Blocks.STALAGMITE_ORE_RARE), 0.0F))
			.decorated(Features.Placements.HEIGHTMAP_SQUARE).chance(6));
	
	// CRYSTAL WORLD
	public static final ConfiguredFeature<?, ?> CRYSTAL_ENERGIZED_KYANITE = registerFeature("crystal_energized_kyanite", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_STONE_CRYSTAL, States.ENERGIZED_KYANITE, 16)).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(16, 0, 80))).squared().count(3));
	public static final ConfiguredFeature<?, ?> GEODE_PINK_TOURMALINE = registerFeature("geode_pink_tourmaline", CAFeatures.GEODE.get().configured(new GeodeFeatureConfig(States.PINK_TOURMALINE, States.KYANITE, FeatureSpread.of(4, 6), FeatureSpread.fixed(4))).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(28, 0, 24))).chance(12));
	public static final ConfiguredFeature<?, ?> GEODE_CATS_EYE = registerFeature("geode_cats_eye", CAFeatures.GEODE.get().configured(new GeodeFeatureConfig(States.CATS_EYE, States.KYANITE, FeatureSpread.of(4, 3), FeatureSpread.fixed(4))).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(6, 0, 24))).chance(18));

	public static final ConfiguredFeature<?, ?> CRYSTAL_FOSSILISED_CRYSTAL_APPLE_COW = registerFeature("crystal_ore_fossilised_crystal_apple_cow", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_STONE_CRYSTAL, States.CRYSTALISED_CRYSTAL_APPLE_COW, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(37, 27))).squared().count(3));
	public static final ConfiguredFeature<?, ?> CRYSTAL_FOSSILISED_CRYSTAL_CARROT_PIG = registerFeature("crystal_ore_fossilised_crystal_carrot_pig", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_STONE_CRYSTAL, States.CRYSTALISED_CRYSTAL_CARROT_PIG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(39, 23))).squared().count(2));
	public static final ConfiguredFeature<?, ?> CRYSTAL_FOSSILISED_CRYSTAL_GATOR = registerFeature("crystal_ore_fossilised_crystal_gator", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_STONE_CRYSTAL, States.CRYSTALISED_CRYSTAL_GATOR, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(38, 25))).squared().count(2));

	// GRASS & FLOWERS
	public static final ConfiguredFeature<?, ?> CHAOS_FLOWER_DEFAULT = registerFeature("chaos_flower_default", Feature.FLOWER.configured(Configs.CHAOS_FLOWER_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(2));
	public static final ConfiguredFeature<?, ?> PATCH_CRYSTAL_GRASS = registerFeature("patch_crystal_grass", Feature.RANDOM_PATCH.configured(Configs.CRYSTAL_GRASS_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 5, 10))));
	public static final ConfiguredFeature<?, ?> PATCH_TALL_CRYSTAL_GRASS = registerFeature("patch_tall_crystal_grass", Feature.RANDOM_PATCH.configured(Configs.TALL_CRYSTAL_GRASS_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(7));
	public static final ConfiguredFeature<?, ?> CRYSTAL_FLOWER_DEFAULT = registerFeature("crystal_flower_default", Feature.FLOWER.configured(Configs.CRYSTAL_FLOWER_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(2));
	public static final ConfiguredFeature<?, ?> CRYSTAL_GROWTH_DEFAULT = registerFeature("crystal_growth_default", Feature.FLOWER.configured(Configs.CRYSTAL_GROWTH_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(2));
	public static final ConfiguredFeature<?, ?> PATCH_DENSE_GRASS = registerFeature("patch_dense_grass", Feature.RANDOM_PATCH.configured(Configs.DENSE_GRASS_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 5, 10))));
	public static final ConfiguredFeature<?, ?> PATCH_TALL_DENSE_GRASS = registerFeature("patch_tall_dense_grass", Feature.RANDOM_PATCH.configured(Configs.TALL_DENSE_GRASS_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(7));
	public static final ConfiguredFeature<?, ?> PATCH_THORNY_SUN = registerFeature("patch_thorny_sun", Feature.RANDOM_PATCH.configured(Configs.THORNY_SUN_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(7));
	public static final ConfiguredFeature<?, ?> PATCH_ALSTROEMERIAT = registerFeature("patch_alstroemeriat", Feature.RANDOM_PATCH.configured(Configs.ALSTROEMERIAT_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(7));
	public static final ConfiguredFeature<?, ?> DENSE_BULB_DEFAULT = registerFeature("dense_bulb_default", Feature.FLOWER.configured(Configs.DENSE_BULB_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(2));
	public static final ConfiguredFeature<?, ?> PATCH_MESO_PLANTS = registerFeature("patch_meso_plants", Feature.FLOWER.configured(Configs.MESOZOIC_PLANT_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(5));
	public static final ConfiguredFeature<?, ?> PATCH_MESO_TALL_BUSH = registerFeature("patch_meso_plants", Feature.RANDOM_PATCH.configured(Configs.MESOZOIC_TALL_BUSH_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(4));
	public static final ConfiguredFeature<?, ?> PATCH_DENSE_FLOWER = registerFeature("patch_dense_flower", Feature.FLOWER.configured(Configs.DENSE_FLOWER_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(4));
	
	// CROPS
	public static final ConfiguredFeature<?, ?> PATCH_STRAWBERRY_BUSH = registerFeature("patch_strawberry_bush", Feature.RANDOM_PATCH.configured(Configs.STRAWBERRY_BUSH_CONFIG));
	public static final ConfiguredFeature<?, ?> PATCH_STRAWBERRY_SPARSE = registerFeature("patch_strawberry_sparse", PATCH_STRAWBERRY_BUSH.decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE));
	public static final ConfiguredFeature<?, ?> PATCH_STRAWBERRY_DECORATED = registerFeature("patch_strawberry_decorated", PATCH_STRAWBERRY_BUSH.decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).chance(12));
	public static final ConfiguredFeature<?, ?> CORN_PATCH = registerFeature("patch_corn", Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.CORN), new ColumnBlockPlacer(1, 4))).tries(7).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).xspread(16).yspread(0).zspread(16).noProjection().build()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE));
	public static final ConfiguredFeature<?, ?> TOMATO_PATCH = registerFeature("patch_tomato", Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.TOMATO), new ColumnBlockPlacer(1, 3))).tries(7).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).xspread(16).yspread(0).zspread(16).noProjection().build()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE));

	// TREES
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> GREEN_CRYSTAL_TREE = registerFeature("green_crystal_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.CRYSTAL_LOG), new SimpleBlockStateProvider(States.GREEN_CRYSTAL_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(3), FeatureSpread.fixed(2), 2), new CrystalTrunkPlacer(3, 2, 0, 0, new CrystalBranchConfig()), new TwoLayerFeature(2, 0, 2)).ignoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> RED_CRYSTAL_TREE = registerFeature("red_crystal_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.CRYSTAL_LOG), new SimpleBlockStateProvider(States.RED_CRYSTAL_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(3), FeatureSpread.fixed(2), 2), new DirtlessGiantTrunkPlacer(5, 2, 0, States.KYANITE), new TwoLayerFeature(2, 0, 2)).ignoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> YELLOW_CRYSTAL_TREE = registerFeature("yellow_crystal_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.CRYSTAL_LOG), new SimpleBlockStateProvider(States.YELLOW_CRYSTAL_LEAVES), new CubicSkipFoliagePlacer(FeatureSpread.of(1, 1), FeatureSpread.of(1, 2), FeatureSpread.of(0, 1)), new CrystalTrunkPlacer(12, 4, 0, 0, new CrystalBranchConfig()), new TwoLayerFeature(2, 0, 2)).ignoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> PINK_CRYSTAL_TREE = registerFeature("pink_crystal_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.CRYSTAL_LOG), new SimpleBlockStateProvider(States.PINK_CRYSTAL_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(4), FeatureSpread.fixed(3), 3), new CrystalTrunkPlacer(6, 2, 0, 0, new CrystalBranchConfig()), new TwoLayerFeature(2, 0, 2)).ignoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> BLUE_CRYSTAL_TREE = registerFeature("blue_crystal_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.CRYSTAL_LOG), new SimpleBlockStateProvider(States.BLUE_CRYSTAL_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(4), FeatureSpread.fixed(2), 2), new CrystalTrunkPlacer(7, 2, 0, 0, new CrystalBranchConfig()), new TwoLayerFeature(2, 0, 2)).ignoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> ORANGE_CRYSTAL_TREE = registerFeature("orange_crystal_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.CRYSTAL_LOG), new SimpleBlockStateProvider(States.ORANGE_CRYSTAL_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(5), FeatureSpread.fixed(4), 4), new CrystalTrunkPlacer(5, 3, 0, 0, new CrystalBranchConfig()), new TwoLayerFeature(2, 0, 2)).ignoreVines().build()));
	
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> APPLE_TREE = registerFeature("apple_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.APPLE_LOG), new SimpleBlockStateProvider(States.APPLE_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 2), new StraightTrunkPlacer(4, 3, 0), new TwoLayerFeature(2, 0, 2)).ignoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> APPLE_TREE_BEES_005 = registerFeature("apple_tree_bees_005", Feature.TREE.configured(APPLE_TREE.config.withDecorators(ImmutableList.of(Placements.BEEHIVE_005))));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_APPLE_TREE = registerFeature("fancy_apple_tree", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.APPLE_LOG), new SimpleBlockStateProvider(States.APPLE_LEAVES), new FancyFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(1), 3), new FancyTrunkPlacer(8, 4, 0), new TwoLayerFeature(2, 0, 2, OptionalInt.of(4)))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_APPLE_TREE_BEES_005 = registerFeature("fancy_apple_tree_bees_005", Feature.TREE.configured(FANCY_APPLE_TREE.config.withDecorators(ImmutableList.of(Placements.BEEHIVE_005))));

	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> CHERRY_TREE = registerFeature("cherry_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.CHERRY_LOG), new SimpleBlockStateProvider(States.CHERRY_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 2), new StraightTrunkPlacer(4, 3, 0), new TwoLayerFeature(2, 0, 2)).ignoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> CHERRY_TREE_BEES_005 = registerFeature("cherry_tree_bees_005", Feature.TREE.configured(CHERRY_TREE.config.withDecorators(ImmutableList.of(Placements.BEEHIVE_005))));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_CHERRY_TREE = registerFeature("fancy_cherry_tree", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.CHERRY_LOG), new SimpleBlockStateProvider(States.CHERRY_LEAVES), new FancyFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(1), 3), new FancyTrunkPlacer(8, 3, 0), new TwoLayerFeature(2, 0, 2, OptionalInt.of(4)))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_CHERRY_TREE_BEES_005 = registerFeature("fancy_cherry_tree_bees_005", Feature.TREE.configured(FANCY_CHERRY_TREE.config.withDecorators(ImmutableList.of(Placements.BEEHIVE_005))));

	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> DUPLICATION_TREE = registerFeature("duplication_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.DUPLICATION_LOG), new SimpleBlockStateProvider(States.DUPLICATION_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(1), FeatureSpread.fixed(0), 1), new StraightTrunkPlacer(3, 2, 4), new TwoLayerFeature(1, 0, 1)).ignoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> DEAD_DUPLICATION_TREE = registerFeature("dead_duplication_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.DEAD_DUPLICATION_LOG), new SimpleBlockStateProvider(States.DUPLICATION_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(1), FeatureSpread.fixed(0), 1), new StraightTrunkPlacer(1, 0, 0), new TwoLayerFeature(1, 0, 1)).ignoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_DUPLICATION_TREE = registerFeature("fancy_duplication_tree", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.DUPLICATION_LOG), new SimpleBlockStateProvider(States.DUPLICATION_LEAVES), new FancyFoliagePlacer(FeatureSpread.fixed(1), FeatureSpread.fixed(0), 1), new FancyTrunkPlacer(3, 2, 4), new TwoLayerFeature(1, 0, 1))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_DEAD_DUPLICATION_TREE = registerFeature("fancy_dead_duplication_tree", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.DEAD_DUPLICATION_LOG), new SimpleBlockStateProvider(States.DUPLICATION_LEAVES), new FancyFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(1), 2), new FancyTrunkPlacer(1, 0, 0), new TwoLayerFeature(0, 0, 0, OptionalInt.of(3)))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()));

	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> PEACH_TREE = registerFeature("peach_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.PEACH_LOG), new SimpleBlockStateProvider(States.PEACH_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 2), new StraightTrunkPlacer(5, 3, 0), new TwoLayerFeature(2, 0, 2)).ignoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> PEACH_TREE_BEES_005 = registerFeature("peach_tree_bees_005", Feature.TREE.configured(PEACH_TREE.config.withDecorators(ImmutableList.of(Placements.BEEHIVE_005))));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_PEACH_TREE = registerFeature("fancy_peach_tree", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.PEACH_LOG), new SimpleBlockStateProvider(States.PEACH_LEAVES), new FancyFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(1), 4), new FancyTrunkPlacer(8, 5, 0), new TwoLayerFeature(2, 0, 2, OptionalInt.of(4)))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_PEACH_TREE_BEES_005 = registerFeature("fancy_peach_tree_bees_005", Feature.TREE.configured(FANCY_PEACH_TREE.config.withDecorators(ImmutableList.of(Placements.BEEHIVE_005))));

	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> GINKGO_TREE = registerFeature("ginkgo_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.GINKGO_LOG), new SimpleBlockStateProvider(States.GINKGO_LEAVES), new SpheroidFoliagePlacer(FeatureSpread.of(2, 1), FeatureSpread.fixed(0), FeatureSpread.of(5, 2)), new StraightTrunkPlacer(7, 2, 2), new TwoLayerFeature(2, 0, 2)).decorators(ImmutableList.of(new LeafCarpetTreeDecorator(States.GINKGO_LEAF_CARPET))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> GINKGO_TREE_FANCY = registerFeature("ginkgo_tree_fancy", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.GINKGO_LOG), new SimpleBlockStateProvider(States.GINKGO_LEAVES), new FancyFoliagePlacer(FeatureSpread.of(2, 1), FeatureSpread.fixed(0), 4), new StraightTrunkPlacer(5, 3, 0), new TwoLayerFeature(2, 0, 2)).decorators(ImmutableList.of(new LeafCarpetTreeDecorator(States.GINKGO_LEAF_CARPET))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> GINKGO_TREE_MEGA = registerFeature("ginkgo_tree_mega", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.GINKGO_LOG), new SimpleBlockStateProvider(States.GINKGO_LEAVES), new SpheroidFoliagePlacer(FeatureSpread.fixed(4), FeatureSpread.fixed(6), FeatureSpread.fixed(10)), new DirtlessGiantTrunkPlacer(12, 2, 4, States.DENSE_DIRT), new TwoLayerFeature(2, 0, 2)).decorators(ImmutableList.of(new LeafCarpetTreeDecorator(States.GINKGO_LEAF_CARPET))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()));
	
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> DENSEWOOD_TREE = registerFeature("densewood_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.DENSEWOOD_LOG), new SimpleBlockStateProvider(States.DENSEWOOD_LEAVES), new ConiferousFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.of(8, 3)), new StraightTrunkPlacer(12, 2, 2), new TwoLayerFeature(2, 1, 3)).decorators(ImmutableList.of(Features.Placements.BEEHIVE_002, new LeafCarpetTreeDecorator(States.DENSEWOOD_LEAF_CARPET))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> DENSEWOOD_TREE_FANCY = registerFeature("densewood_tree_fancy", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.DENSEWOOD_LOG), new SimpleBlockStateProvider(States.DENSEWOOD_LEAVES), new SpheroidFoliagePlacer(FeatureSpread.of(2, 2), FeatureSpread.fixed(0), FeatureSpread.of(5, 3)), new StraightTrunkPlacer(8, 2, 2), new TwoLayerFeature(1, 0, 2)).decorators(ImmutableList.of(Features.Placements.BEEHIVE_005, new LeafCarpetTreeDecorator(States.DENSEWOOD_LEAF_CARPET))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()));
	
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> MESOZOIC_TREE = registerFeature("mesozoic_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.MESOZOIC_LOG), new SimpleBlockStateProvider(States.MESOZOIC_LEAVES), new SpheroidFoliagePlacer(FeatureSpread.fixed(4), FeatureSpread.fixed(1), FeatureSpread.of(3, 1)), new GiantConiferTrunkPlacer(20, 2, 2, States.DENSE_DIRT), new TwoLayerFeature(1, 0, 2)).decorators(ImmutableList.of(new VinesBelowLeavesTreeDecorator(States.MESOZOIC_VINES, States.MESOZOIC_VINES_PLANT))).heightmap(Heightmap.Type.MOTION_BLOCKING).build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> MESOZOIC_TREE_THIN = registerFeature("mesozoic_tree_thin", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.MESOZOIC_LOG), new SimpleBlockStateProvider(States.MESOZOIC_LEAVES), new SpheroidFoliagePlacer(FeatureSpread.of(4, 0), FeatureSpread.fixed(0), FeatureSpread.fixed(4)), new ThinGiantTrunkPlacer(24, 3, 3, States.DENSE_DIRT), new TwoLayerFeature(1, 0, 2)).decorators(ImmutableList.of(new VinesBelowLeavesTreeDecorator(States.MESOZOIC_VINES, States.MESOZOIC_VINES_PLANT))).heightmap(Heightmap.Type.MOTION_BLOCKING).build()));
	
	public static final ConfiguredFeature<?, ?> TREES_CRYSTAL_PLAINS = registerFeature("trees_crystal_dimension", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(GREEN_CRYSTAL_TREE.weighted(0.4F), RED_CRYSTAL_TREE.weighted(0.3F), YELLOW_CRYSTAL_TREE.weighted(0.1F), PINK_CRYSTAL_TREE.weighted(0.045F), BLUE_CRYSTAL_TREE.weighted(0.2F), ORANGE_CRYSTAL_TREE.weighted(0.035F)), GREEN_CRYSTAL_TREE)).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(2, 0.1F, 1))));
	public static final ConfiguredFeature<?, ?> TREES_APPLE = registerFeature("trees_apple", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(APPLE_TREE.weighted(0.1F), APPLE_TREE_BEES_005.weighted(0.04F), FANCY_APPLE_TREE.weighted(0.09F), FANCY_APPLE_TREE_BEES_005.weighted(0.02F)), Feature.NO_OP.configured(new NoFeatureConfig()))).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(1).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.01F, 1))));
	public static final ConfiguredFeature<?, ?> TREES_CHERRY = registerFeature("trees_cherry", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(CHERRY_TREE.weighted(0.1F), CHERRY_TREE_BEES_005.weighted(0.01F), FANCY_CHERRY_TREE.weighted(0.09F), FANCY_CHERRY_TREE_BEES_005.weighted(0.02F)), Feature.NO_OP.configured(new NoFeatureConfig()))).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(1).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.01F, 1))));
	public static final ConfiguredFeature<?, ?> TREES_PEACH = registerFeature("trees_peach", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(PEACH_TREE.weighted(0.1F), PEACH_TREE_BEES_005.weighted(0.01F), FANCY_PEACH_TREE.weighted(0.07F), FANCY_PEACH_TREE_BEES_005.weighted(0.01F)), Feature.NO_OP.configured(new NoFeatureConfig()))).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(1).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.01F, 1))));
	public static final ConfiguredFeature<?, ?> TREES_DENSE_PLAINS = registerFeature("trees_dense_plains", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(DENSEWOOD_TREE.weighted(0.02F), GINKGO_TREE_FANCY.weighted(0.01F)), Feature.NO_OP.configured(new NoFeatureConfig()))).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(1).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.1F, 1))));
	public static final ConfiguredFeature<?, ?> TREES_DENSE_FOREST = registerFeature("trees_dense_forest", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(DENSEWOOD_TREE.weighted(0.9F), DENSEWOOD_TREE_FANCY.weighted(0.1F), GINKGO_TREE_FANCY.weighted(0.4F)), Feature.NO_OP.configured(new NoFeatureConfig()))).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(2).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(2, 0.6F, 2))));
	public static final ConfiguredFeature<?, ?> TREES_DENSE_GINKGO = registerFeature("trees_dense_ginkgo", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(GINKGO_TREE.weighted(0.9F), GINKGO_TREE_FANCY.weighted(0.3F), GINKGO_TREE_MEGA.weighted(0.05F)), Feature.NO_OP.configured(new NoFeatureConfig()))).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(2).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(2, 0.6F, 3))));
	public static final ConfiguredFeature<?, ?> TREES_MESOZOIC = registerFeature("trees_mesozoic", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(MESOZOIC_TREE.weighted(0.9F), MESOZOIC_TREE_THIN.weighted(0.5F), GINKGO_TREE_MEGA.weighted(0.005F)), Feature.NO_OP.configured(new NoFeatureConfig()))).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(2).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(2, 0.5F, 4))));
	
	// NESTS
	public static final ConfiguredFeature<?, ?> BROWN_ANT_NEST = registerFeature("nest_brown_ant", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.GRASS_BLOCK, States.BROWN_ANT_NEST)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(80, 50))));
	public static final ConfiguredFeature<?, ?> RAINBOW_ANT_NEST = registerFeature("nest_rainbow_ant", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.GRASS_BLOCK, States.RAINBOW_ANT_NEST)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(80, 50))));
	public static final ConfiguredFeature<?, ?> RED_ANT_NEST = registerFeature("nest_red_ant", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.GRASS_BLOCK, States.RED_ANT_NEST)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(80, 50))));
	public static final ConfiguredFeature<?, ?> UNSTABLE_ANT_NEST = registerFeature("nest_unstable_ant", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.GRASS_BLOCK, States.UNSTABLE_ANT_NEST)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(80, 50))));
	public static final ConfiguredFeature<?, ?> TERMITE_NEST = registerFeature("nest_termite", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.GRASS_BLOCK, States.TERMITE_NEST)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(80, 50))));

	public static final ConfiguredFeature<?, ?> CRYSTAL_TERMITE_NEST = registerFeature("nest_crystal_termite", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.CRYSTAL_GRASS_BLOCK, States.CRYSTAL_TERMITE_NEST)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(80, 50))).count(6));
	public static final ConfiguredFeature<?, ?> DENSE_RED_ANT_NEST = registerFeature("nest_dense_red_ant", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.DENSE_GRASS_BLOCK, States.DENSE_RED_ANT_NEST)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(140, 40))).count(6));

	private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> registerFeature(String key, ConfiguredFeature<FC, ?> configuredFeature) {
		CACommonSetupEvents.CONFIG_FEATURES.add(new FeatureWrapper(key, configuredFeature));
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
	
		private static final BlockState DENSE_DIRT = CABlocks.DENSE_DIRT.get().defaultBlockState();
		private static final BlockState KYANITE = CABlocks.KYANITE.get().defaultBlockState();

		private static final BlockState MARBLE = CABlocks.MARBLE.get().defaultBlockState();
		private static final BlockState LIMESTONE = CABlocks.LIMESTONE.get().defaultBlockState();
		private static final BlockState RHINESTONE = CABlocks.RHINESTONE.get().defaultBlockState();

		private static final BlockState APPLE_LOG = CABlocks.APPLE_LOG.get().defaultBlockState();
		private static final BlockState CHERRY_LOG = CABlocks.CHERRY_LOG.get().defaultBlockState();
		private static final BlockState DUPLICATION_LOG = CABlocks.DUPLICATION_LOG.get().defaultBlockState();
		private static final BlockState DEAD_DUPLICATION_LOG = CABlocks.DEAD_DUPLICATION_LOG.get().defaultBlockState();
		private static final BlockState PEACH_LOG = CABlocks.PEACH_LOG.get().defaultBlockState();
		
		private static final BlockState GINKGO_LOG = CABlocks.GINKGO_LOG.get().defaultBlockState();
		private static final BlockState MESOZOIC_LOG = CABlocks.MESOZOIC_LOG.get().defaultBlockState();
		private static final BlockState DENSEWOOD_LOG = CABlocks.DENSEWOOD_LOG.get().defaultBlockState();
		
		private static final BlockState APPLE_LEAVES = CABlocks.APPLE_LEAVES.get().defaultBlockState();
		private static final BlockState CHERRY_LEAVES = CABlocks.CHERRY_LEAVES.get().defaultBlockState();
		private static final BlockState DUPLICATION_LEAVES = CABlocks.DUPLICATION_LEAVES.get().defaultBlockState();
		private static final BlockState PEACH_LEAVES = CABlocks.PEACH_LEAVES.get().defaultBlockState();
		
		private static final BlockState GINKGO_LEAVES = CABlocks.GINKGO_LEAVES.get().defaultBlockState();
		private static final BlockState GINKGO_LEAF_CARPET = CABlocks.GINKGO_LEAF_CARPET.get().defaultBlockState();
		private static final BlockState MESOZOIC_LEAVES = CABlocks.MESOZOIC_LEAVES.get().defaultBlockState();
		private static final BlockState DENSEWOOD_LEAVES = CABlocks.DENSEWOOD_LEAVES.get().defaultBlockState();
		private static final BlockState DENSEWOOD_LEAF_CARPET = CABlocks.DENSEWOOD_LEAF_CARPET.get().defaultBlockState();

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
		private static final BlockState FOSSILISED_BEAVER = CABlocks.FOSSILISED_BEAVER.get().defaultBlockState();
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
		private static final BlockState FOSSILISED_LETTUCE_CHICKEN = CABlocks.FOSSILISED_LETTUCE_CHICKEN.get().defaultBlockState();
		private static final BlockState FOSSILISED_BIRD = CABlocks.FOSSILISED_BIRD.get().defaultBlockState();
		private static final BlockState FOSSILISED_TREE_FROG = CABlocks.FOSSILISED_TREE_FROG.get().defaultBlockState();

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

		private static final BlockState CRYSTALISED_CRYSTAL_APPLE_COW = CABlocks.CRYSTALISED_CRYSTAL_APPLE_COW.get().defaultBlockState();
		private static final BlockState CRYSTALISED_CRYSTAL_CARROT_PIG = CABlocks.CRYSTALISED_CRYSTAL_CARROT_PIG.get().defaultBlockState();
		private static final BlockState CRYSTALISED_CRYSTAL_GATOR = CABlocks.CRYSTALISED_CRYSTAL_GATOR.get().defaultBlockState();

		private static final BlockState FOSSILISED_DIMETRODON = CABlocks.FOSSILISED_DIMETRODON.get().defaultBlockState();

		private static final BlockState RED_ANT_INFESTED_ORE = CABlocks.RED_ANT_INFESTED_ORE.get().defaultBlockState();
		private static final BlockState TERMITE_INFESTED_ORE = CABlocks.TERMITE_INFESTED_ORE.get().defaultBlockState();
		private static final BlockState ENERGIZED_KYANITE = CABlocks.ENERGIZED_KYANITE.get().defaultBlockState();
		private static final BlockState PINK_TOURMALINE = CABlocks.BUDDING_PINK_TOURMALINE.get().defaultBlockState();
		private static final BlockState CATS_EYE = CABlocks.BUDDING_CATS_EYE.get().defaultBlockState();
		private static final BlockState CRYSTAL_LOG = CABlocks.CRYSTALWOOD_LOG.get().defaultBlockState();
		private static final BlockState GREEN_CRYSTAL_LEAVES = CABlocks.GREEN_CRYSTAL_LEAVES.get().defaultBlockState();
		private static final BlockState RED_CRYSTAL_LEAVES = CABlocks.RED_CRYSTAL_LEAVES.get().defaultBlockState();
		private static final BlockState YELLOW_CRYSTAL_LEAVES = CABlocks.YELLOW_CRYSTAL_LEAVES.get().defaultBlockState();
		private static final BlockState PINK_CRYSTAL_LEAVES = CABlocks.PINK_CRYSTAL_LEAVES.get().defaultBlockState();
		private static final BlockState BLUE_CRYSTAL_LEAVES = CABlocks.BLUE_CRYSTAL_LEAVES.get().defaultBlockState();
		private static final BlockState ORANGE_CRYSTAL_LEAVES = CABlocks.ORANGE_CRYSTAL_LEAVES.get().defaultBlockState();

		private static final BlockState CYAN_ROSE = CABlocks.CYAN_ROSE.get().defaultBlockState();
		private static final BlockState RED_ROSE = CABlocks.RED_ROSE.get().defaultBlockState();
		private static final BlockState PAEONIA = CABlocks.PAEONIA.get().defaultBlockState();

		private static final BlockState RED_CRYSTAL_FLOWER = CABlocks.RED_CRYSTAL_FLOWER.get().defaultBlockState();
		private static final BlockState BLUE_CRYSTAL_FLOWER = CABlocks.BLUE_CRYSTAL_FLOWER.get().defaultBlockState();
		private static final BlockState GREEN_CRYSTAL_FLOWER = CABlocks.GREEN_CRYSTAL_FLOWER.get().defaultBlockState();
		private static final BlockState YELLOW_CRYSTAL_FLOWER = CABlocks.YELLOW_CRYSTAL_FLOWER.get().defaultBlockState();
		private static final BlockState PINK_CRYSTAL_FLOWER = CABlocks.PINK_CRYSTAL_FLOWER.get().defaultBlockState();
		private static final BlockState ORANGE_CRYSTAL_FLOWER = CABlocks.ORANGE_CRYSTAL_FLOWER.get().defaultBlockState();
		private static final BlockState RED_CRYSTAL_GROWTH = CABlocks.RED_CRYSTAL_GROWTH.get().defaultBlockState();
		private static final BlockState BLUE_CRYSTAL_GROWTH = CABlocks.BLUE_CRYSTAL_GROWTH.get().defaultBlockState();
		private static final BlockState GREEN_CRYSTAL_GROWTH = CABlocks.GREEN_CRYSTAL_GROWTH.get().defaultBlockState();
		private static final BlockState YELLOW_CRYSTAL_GROWTH = CABlocks.YELLOW_CRYSTAL_GROWTH.get().defaultBlockState();
		private static final BlockState ORANGE_CRYSTAL_GROWTH = CABlocks.ORANGE_CRYSTAL_GROWTH.get().defaultBlockState();
		private static final BlockState PINK_CRYSTAL_GROWTH = CABlocks.PINK_CRYSTAL_GROWTH.get().defaultBlockState();
		private static final BlockState CRYSTAL_ROSE = CABlocks.CRYSTAL_ROSE.get().defaultBlockState();

		private static final BlockState THORNY_SUN = CABlocks.THORNY_SUN.get().defaultBlockState();
		private static final BlockState BLUE_BULB = CABlocks.BLUE_BULB.get().defaultBlockState();
		private static final BlockState PINK_BULB = CABlocks.PINK_BULB.get().defaultBlockState();
		private static final BlockState PURPLE_BULB = CABlocks.PURPLE_BULB.get().defaultBlockState();
		
		private static final BlockState TERRA_PRETA = CABlocks.TERRA_PRETA.get().defaultBlockState();
		private static final BlockState LATOSOL = CABlocks.LATOSOL.get().defaultBlockState();
		private static final BlockState TAR = CABlocks.TAR.get().defaultBlockState();
		
		private static final BlockState SWAMP_MILKWEED = CABlocks.SWAMP_MILKWEED.get().defaultBlockState();
		private static final BlockState PRIMROSE = CABlocks.PRIMROSE.get().defaultBlockState();
		private static final BlockState DAISY = CABlocks.DAISY.get().defaultBlockState();
		
		private static final BlockState DENSE_ORCHID = CABlocks.DENSE_ORCHID.get().defaultBlockState();
		private static final BlockState ALSTROEMERIAT = CABlocks.ALSTROEMERIAT.get().defaultBlockState();
		private static final BlockState SMALL_BUSH = CABlocks.SMALL_BUSH.get().defaultBlockState();
		private static final BlockState TALL_BUSH = CABlocks.TALL_BUSH.get().defaultBlockState();
		private static final BlockState SMALL_CARNIVOROUS_PLANT = CABlocks.SMALL_CARNIVOROUS_PLANT.get().defaultBlockState();
		private static final BlockState BIG_CARNIVOROUS_PLANT = CABlocks.BIG_CARNIVOROUS_PLANT.get().defaultBlockState();
		private static final BlockState MESOZOIC_VINES = CABlocks.MESOZOIC_VINES.get().defaultBlockState();
		private static final BlockState MESOZOIC_VINES_PLANT = CABlocks.MESOZOIC_VINES_PLANT.get().defaultBlockState();
		
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
		public static final BlockClusterFeatureConfig MESOZOIC_PLANT_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).add(States.SMALL_BUSH, 2).add(States.SMALL_CARNIVOROUS_PLANT, 2).add(States.BIG_CARNIVOROUS_PLANT, 2), SimpleBlockPlacer.INSTANCE)).tries(32).build();
		public static final BlockClusterFeatureConfig MESOZOIC_TALL_BUSH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.TALL_BUSH), new DoubleDensePlantBlockPlacer())).tries(32).noProjection().build();
		public static final BlockClusterFeatureConfig ALSTROEMERIAT_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.ALSTROEMERIAT), new DoubleDensePlantBlockPlacer())).tries(32).noProjection().build();
		public static final BlockClusterFeatureConfig DENSE_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).add(States.DENSE_ORCHID, 2).add(States.PRIMROSE, 2).add(States.SWAMP_MILKWEED, 2).add(States.DAISY, 2), SimpleBlockPlacer.INSTANCE)).tries(32).build();
		public static final BlockClusterFeatureConfig STRAWBERRY_BUSH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.STRAWBERRY_BUSH), SimpleBlockPlacer.INSTANCE)).tries(64).whitelist(ImmutableSet.of(States.GRASS_BLOCK.getBlock())).noProjection().build();
		public static final BlockClusterFeatureConfig CRYSTAL_GRASS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.CRYSTAL_GRASS), SimpleBlockPlacer.INSTANCE)).tries(32).build();
		public static final BlockClusterFeatureConfig TALL_CRYSTAL_GRASS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.TALL_CRYSTAL_GRASS), new DoubleCrystalPlantBlockPlacer())).tries(64).noProjection().build();
		public static final BlockClusterFeatureConfig CRYSTAL_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).add(States.RED_CRYSTAL_FLOWER, 2).add(States.BLUE_CRYSTAL_FLOWER, 2).add(States.GREEN_CRYSTAL_FLOWER, 2).add(States.YELLOW_CRYSTAL_FLOWER, 2).add(States.PINK_CRYSTAL_FLOWER, 2).add(States.ORANGE_CRYSTAL_FLOWER, 2).add(States.CRYSTAL_ROSE, 3), SimpleBlockPlacer.INSTANCE)).tries(32).build();
		public static final BlockClusterFeatureConfig CRYSTAL_GROWTH_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).add(States.RED_CRYSTAL_GROWTH, 2).add(States.BLUE_CRYSTAL_GROWTH, 2).add(States.GREEN_CRYSTAL_GROWTH, 2).add(States.YELLOW_CRYSTAL_GROWTH, 2).add(States.PINK_CRYSTAL_GROWTH, 2).add(States.ORANGE_CRYSTAL_GROWTH, 2), SimpleBlockPlacer.INSTANCE)).tries(32).build();
	}

	public static final class Placements {
		public static final BeehiveTreeDecorator BEEHIVE_005 = new BeehiveTreeDecorator(0.05f);
	}

	public static final class RuleTests {
		public static final RuleTest BASE_DIRT = new TagMatchRuleTest(Tags.Blocks.DIRT);
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
