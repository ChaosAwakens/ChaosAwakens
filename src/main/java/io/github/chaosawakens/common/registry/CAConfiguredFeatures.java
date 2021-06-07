package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableList;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.FeatureWrapper;
import io.github.chaosawakens.common.worldgen.feature.GeodeFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class CAConfiguredFeatures {
	public static final ConfiguredFeature<?, ?> ORE_RUBY_LAVA = register("ore_ruby", Feature.ORE.withConfiguration(new OreFeatureConfig(new BlockMatchRuleTest(Blocks.LAVA), CABlocks.RUBY_ORE.get().getDefaultState(), 8)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(24, 24))).square().count(28));
	public static final ConfiguredFeature<?, ?> ORE_AMETHYST = register("ore_amethyst", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.AMETHYST_ORE, 4)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(28, 24))).square().count(4));
	public static final ConfiguredFeature<?, ?> ORE_URANIUM = register("ore_uranium", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.URANIUM_ORE, 4)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(12, 12))).square().count(3));
	public static final ConfiguredFeature<?, ?> ORE_TITANIUM = register("ore_titanium", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.TITANIUM_ORE, 4)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(12, 12))).square().count(3));
	public static final ConfiguredFeature<?, ?> ORE_TIGERS_EYE = register("ore_tigers_eye", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.TIGERS_EYE_ORE, 7)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(24, 24))).square().count(5));
	public static final ConfiguredFeature<?, ?> ORE_SALT = register("ore_salt", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.SALT_ORE, 8)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(40, 32))).square().count(14));
	public static final ConfiguredFeature<?, ?> FOSSILISED_ENT = register("ore_fossilised_ent", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.FOSSILISED_ENT, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(48, 48))).square().count(10));
	public static final ConfiguredFeature<?, ?> FOSSILISED_HERCULES_BEETLE = register("ore_fossilised_hercules_beetle", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.FOSSILISED_HERCULES_BEETLE, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(48, 48))).square().count(10));
	public static final ConfiguredFeature<?, ?> FOSSILISED_RUBY_BUG = register("ore_fossilised_ruby_bug", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.FOSSILISED_RUBY_BUG, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(48, 48))).square().count(10));
	public static final ConfiguredFeature<?, ?> FOSSILISED_EMERALD_GATOR = register("ore_fossilised_emerald_gator", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.FOSSILISED_EMERALD_GATOR, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(48, 48))).square().count(10));
	public static final ConfiguredFeature<?, ?> RED_ANT_INFESTED = register("ore_red_ant_infested", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.RED_ANT_INFESTED_ORE, 8)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(12, 12))).square().count(5));
	public static final ConfiguredFeature<?, ?> TERMITE_INFESTED = register("ore_termite_infested", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.TERMITE_INFESTED_ORE, 8)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(12, 12))).square().count(2));

	public static final ConfiguredFeature<?, ?> ORE_COPPER = register("ore_copper", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.COPPER_ORE, 5)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(72, 12))).square().count(16));
	public static final ConfiguredFeature<?, ?> ORE_TIN = register("ore_tin", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.TIN_ORE, 4)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(56, 12))).square().count(12));
	public static final ConfiguredFeature<?, ?> ORE_SILVER = register("ore_silver", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.SILVER_ORE, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(32, 12))).square().count(6));
	public static final ConfiguredFeature<?, ?> ORE_PLATINUM = register("ore_platinum", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.PLATINUM_ORE, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(16, 12))).square().count(3));
	public static final ConfiguredFeature<?, ?> ORE_SUNSTONE = register("ore_sunstone", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.SUNSTONE_ORE, 4)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(24, 8))).square().count(8));
	public static final ConfiguredFeature<?, ?> ORE_BLOODSTONE = register("ore_bloodstone", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.BLOODSTONE_ORE, 5)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(24, 6))).square().count(8));

	public static final ConfiguredFeature<?, ?> MINING_ORE_RUBY_LAVA = register("mining_ore_ruby", Feature.ORE.withConfiguration(new OreFeatureConfig(new BlockMatchRuleTest(Blocks.LAVA), CABlocks.RUBY_ORE.get().getDefaultState(), 8)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(36, 24))).square().count(28));
	public static final ConfiguredFeature<?, ?> MINING_ORE_RUBY_NO_SURFACE = register("mining_ore_ruby_surfaceless", Feature.NO_SURFACE_ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.RUBY_ORE, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(24, 24))).square().count(1));
	public static final ConfiguredFeature<?, ?> MINING_ORE_AMETHYST = register("mining_ore_amethyst", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.AMETHYST_ORE, 4)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(40, 24))).square().count(4));
	public static final ConfiguredFeature<?, ?> MINING_ORE_URANIUM = register("mining_ore_uranium", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.URANIUM_ORE, 4)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(24, 24))).square().count(3));
	public static final ConfiguredFeature<?, ?> MINING_ORE_TITANIUM = register("mining_ore_titanium", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.TITANIUM_ORE, 4)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(24, 24))).square().count(3));
	public static final ConfiguredFeature<?, ?> MINING_ORE_ALUMINUM = register("mining_ore_aluminum", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.ALUMINIUM_ORE, 5)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(40, 24))).square().count(8));
	public static final ConfiguredFeature<?, ?> MINING_ORE_COPPER = register("mining_ore_copper", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.COPPER_ORE, 5)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(60, 24))).square().count(6));
	public static final ConfiguredFeature<?, ?> MINING_ORE_TIN = register("mining_ore_tin", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.TIN_ORE, 4)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(50, 18))).square().count(5));
	public static final ConfiguredFeature<?, ?> MINING_ORE_SILVER = register("mining_ore_silver", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.SILVER_ORE, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(40, 12))).square().count(4));
	public static final ConfiguredFeature<?, ?> MINING_ORE_PLATINUM = register("mining_ore_platinum", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.PLATINUM_ORE, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(30, 8))).square().count(3));
	public static final ConfiguredFeature<?, ?> MINING_ORE_SALT = register("mining_ore_salt", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.SALT_ORE, 8)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(40, 32))).square().count(14));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_ENT = register("mining_ore_fossilised_ent", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.FOSSILISED_ENT, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(60, 48))).square().count(10));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_HERCULES_BEETLE = register("mining_ore_fossilised_hercules_beetle", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.FOSSILISED_HERCULES_BEETLE, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(60, 48))).square().count(10));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_RUBY_BUG = register("mining_ore_fossilised_ruby_bug", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.FOSSILISED_RUBY_BUG, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(60, 48))).square().count(10));
	public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_EMERALD_GATOR = register("mining_ore_fossilised_emerald_gator", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.FOSSILISED_EMERALD_GATOR, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(48, 48))).square().count(10));
	public static final ConfiguredFeature<?, ?> MINING_ORE_TIGERS_EYE = register("mining_ore_tigers_eye", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.TIGERS_EYE_ORE, 7)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(36, 24))).square().count(5));
	public static final ConfiguredFeature<?, ?> MINING_ORE_SUNSTONE = register("mining_ore_sunstone", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.SUNSTONE_ORE, 4)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(28, 8))).square().count(8));
	public static final ConfiguredFeature<?, ?> MINING_ORE_BLOODSTONE = register("mining_ore_bloodstone", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.BLOODSTONE_ORE, 5)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(28, 6))).square().count(8));
	public static final ConfiguredFeature<?, ?> MINING_RED_ANT_INFESTED = register("mining_ore_red_ant_infested", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.RED_ANT_INFESTED_ORE, 8)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(24, 12))).square().count(5));
	public static final ConfiguredFeature<?, ?> MINING_TERMITE_INFESTED = register("mining_ore_termite_infested", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.TERMITE_INFESTED_ORE, 8)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(24, 12))).square().count(2));

	public static final ConfiguredFeature<?, ?> CRYSTAL_ORE_ENERGY = register("crystal_ore_energy", Feature.ORE.withConfiguration(new OreFeatureConfig(RuleTests.BASE_STONE_CRYSTAL, States.CRYSTAL_ENERGY, 5)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(60, 7))).square().count(5));
	/* 
	 * Why are those two even here?
	 * public static final ConfiguredFeature<?, ?> CRYSTAL_ORE_PINK_BUDDING = register("crystal_ore_pink_budding", Feature.ORE.withConfiguration(new OreFeatureConfig(RuleTests.BASE_STONE_CRYSTAL, States.PINK_TOURMALINE, 2)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(36, 5))).square().count(5));
	 * public static final ConfiguredFeature<?, ?> CRYSTAL_ORE_CATS_EYE_BUDDING = register("crystal_ore_cats_eye_budding", Feature.ORE.withConfiguration(new OreFeatureConfig(RuleTests.BASE_STONE_CRYSTAL, States.CATS_EYE, 1)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(12, 3))).square().count(5));
	 */
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> GREEN_CRYSTAL_TREE = register("green_crystal_tree", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(CABlocks.CRYSTAL_LOG.get().getDefaultState()), new SimpleBlockStateProvider(CABlocks.GREEN_CRYSTAL_LEAVES.get().getDefaultState()), new BlobFoliagePlacer(FeatureSpread.create(2), FeatureSpread.create(0), 2), new StraightTrunkPlacer(3, 2, 0), new TwoLayerFeature(2, 0, 2)).setIgnoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> RED_CRYSTAL_TREE = register("red_crystal_tree", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(CABlocks.CRYSTAL_LOG.get().getDefaultState()), new SimpleBlockStateProvider(CABlocks.RED_CRYSTAL_LEAVES.get().getDefaultState()), new BlobFoliagePlacer(FeatureSpread.create(3), FeatureSpread.create(2), 2), new StraightTrunkPlacer(5, 2, 0), new TwoLayerFeature(2, 0, 2)).setIgnoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> YELLOW_CRYSTAL_TREE = register("yellow_crystal_tree", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(CABlocks.CRYSTAL_LOG.get().getDefaultState()), new SimpleBlockStateProvider(CABlocks.YELLOW_CRYSTAL_LEAVES.get().getDefaultState()), new BlobFoliagePlacer(FeatureSpread.create(4), FeatureSpread.create(3), 3), new StraightTrunkPlacer(7, 2, 0), new TwoLayerFeature(2, 0, 2)).setIgnoreVines().build()));
	
	public static final ConfiguredFeature<?, ?> TREES_CRYSTAL_PLAINS = register("trees_crystal_dimension", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(GREEN_CRYSTAL_TREE.withChance(0.4F), RED_CRYSTAL_TREE.withChance(0.3F), YELLOW_CRYSTAL_TREE.withChance(0.1F)), GREEN_CRYSTAL_TREE)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(2, 0.1F, 1))));
	
	public static final ConfiguredFeature<?, ?> GEODE_PINK_TOURMALINE = register("geode_pink_tourmaline", CAFeatures.GEODE.get().withConfiguration( new GeodeFeatureConfig(RuleTests.BASE_STONE_CRYSTAL, States.PINK_TOURMALINE, States.CLUSTER_PINK_TOURMALINE, 28, 48, 40)));
	public static final ConfiguredFeature<?, ?> GEODE_CATS_EYE = register("geode_cats_eye", CAFeatures.GEODE.get().withConfiguration( new GeodeFeatureConfig(RuleTests.BASE_STONE_CRYSTAL, States.CATS_EYE, States.CLUSTER_CATS_EYE, 5, 28, 15)));
	
	private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
		ChaosAwakens.configFeatures.add( new FeatureWrapper(key, configuredFeature));
		return configuredFeature;
	}
	
	public static final class States {
		protected static final BlockState RUBY_ORE = CABlocks.RUBY_ORE.get().getDefaultState();
		protected static final BlockState AMETHYST_ORE = CABlocks.AMETHYST_ORE.get().getDefaultState();
		protected static final BlockState URANIUM_ORE = CABlocks.URANIUM_ORE.get().getDefaultState();
		protected static final BlockState TITANIUM_ORE = CABlocks.TITANIUM_ORE.get().getDefaultState();
		protected static final BlockState TIGERS_EYE_ORE = CABlocks.TIGERS_EYE_ORE.get().getDefaultState();
		protected static final BlockState ALUMINIUM_ORE = CABlocks.ALUMINUM_ORE.get().getDefaultState();
		protected static final BlockState COPPER_ORE = CABlocks.COPPER_ORE.get().getDefaultState();
		protected static final BlockState TIN_ORE = CABlocks.TIN_ORE.get().getDefaultState();
		protected static final BlockState SILVER_ORE = CABlocks.SILVER_ORE.get().getDefaultState();
		protected static final BlockState PLATINUM_ORE = CABlocks.PLATINUM_ORE.get().getDefaultState();
		protected static final BlockState SUNSTONE_ORE = CABlocks.SUNSTONE_ORE.get().getDefaultState();
		protected static final BlockState BLOODSTONE_ORE = CABlocks.BLOODSTONE_ORE.get().getDefaultState();
		protected static final BlockState SALT_ORE = CABlocks.SALT_ORE.get().getDefaultState();
		protected static final BlockState FOSSILISED_ENT = CABlocks.FOSSILISED_ENT.get().getDefaultState();
		protected static final BlockState FOSSILISED_HERCULES_BEETLE = CABlocks.FOSSILISED_HERCULES_BEETLE.get().getDefaultState();
		protected static final BlockState FOSSILISED_RUBY_BUG = CABlocks.FOSSILISED_RUBY_BUG.get().getDefaultState();
		protected static final BlockState FOSSILISED_EMERALD_GATOR = CABlocks.FOSSILISED_EMERALD_GATOR.get().getDefaultState();
		protected static final BlockState RED_ANT_INFESTED_ORE = CABlocks.RED_ANT_INFESTED_ORE.get().getDefaultState();
		protected static final BlockState TERMITE_INFESTED_ORE = CABlocks.TERMITE_INFESTED_ORE.get().getDefaultState();
		protected static final BlockState CRYSTAL_ENERGY = CABlocks.CRYSTAL_ENERGY.get().getDefaultState();
		protected static final BlockState PINK_TOURMALINE = CABlocks.BUDDING_PINK_TOURMALINE.get().getDefaultState();
		protected static final BlockState CATS_EYE = CABlocks.BUDDING_CATS_EYE.get().getDefaultState();
		protected static final BlockState CLUSTER_PINK_TOURMALINE = CABlocks.PINK_TOURMALINE_CLUSTER.get().getDefaultState();
		protected static final BlockState CLUSTER_CATS_EYE = CABlocks.CATS_EYE_CLUSTER.get().getDefaultState();
	}
	
	public static final class RuleTests {
		//public static final RuleTest BASE_STONE_MINING = new TagMatchRuleTest(BlockTags.getCollection().getTagByID(new ResourceLocation(ChaosAwakens.MODID + "base_stone_mining")));
		public static final RuleTest BASE_STONE_CRYSTAL = new TagMatchRuleTest(CATags.BASE_STONE_CRYSTAL);
	}
}