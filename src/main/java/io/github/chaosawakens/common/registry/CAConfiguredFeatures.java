package io.github.chaosawakens.common.registry;

import java.util.OptionalInt;

import com.google.common.collect.ImmutableList;
import io.github.chaosawakens.api.FeatureWrapper;
import io.github.chaosawakens.common.dupetree.DuplicationTree;
import io.github.chaosawakens.common.events.CommonSetupEvent;
import io.github.chaosawakens.common.worldgen.feature.GeodeFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.ColumnBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class CAConfiguredFeatures {
    public static final ConfiguredFeature<?, ?> ORE_RUBY_LAVA = register("ore_ruby", Feature.ORE.configured(new OreFeatureConfig(new BlockMatchRuleTest(Blocks.LAVA), CABlocks.RUBY_ORE.get().defaultBlockState(), 8)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(24, 24))).squared().count(16));
    public static final ConfiguredFeature<?, ?> ORE_AMETHYST = register("ore_amethyst", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.AMETHYST_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(28, 24))).squared().count(4));
    public static final ConfiguredFeature<?, ?> ORE_URANIUM = register("ore_uranium", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.URANIUM_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(12, 12))).squared().count(3));
    public static final ConfiguredFeature<?, ?> ORE_TITANIUM = register("ore_titanium", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.TITANIUM_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(12, 12))).squared().count(3));
    public static final ConfiguredFeature<?, ?> ORE_TIGERS_EYE = register("ore_tigers_eye", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.TIGERS_EYE_ORE, 7)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(24, 24))).squared().count(5));
    public static final ConfiguredFeature<?, ?> ORE_SALT = register("ore_salt", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.SALT_ORE, 8)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(40, 32))).squared().count(14));
    public static final ConfiguredFeature<?, ?> FOSSILISED_ENT = register("ore_fossilised_ent", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_ENT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(10));
    public static final ConfiguredFeature<?, ?> FOSSILISED_HERCULES_BEETLE = register("ore_fossilised_hercules_beetle", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_HERCULES_BEETLE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(10));
    public static final ConfiguredFeature<?, ?> FOSSILISED_RUBY_BUG = register("ore_fossilised_ruby_bug", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_RUBY_BUG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(10));
    public static final ConfiguredFeature<?, ?> FOSSILISED_EMERALD_GATOR = register("ore_fossilised_emerald_gator", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_EMERALD_GATOR, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(10));
    public static final ConfiguredFeature<?, ?> FOSSILISED_WTF = register("ore_fossilised_wtf", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WTF, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(10));
    public static final ConfiguredFeature<?, ?> FOSSILISED_SCORPION = register("ore_fossilised_scorpion", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SCORPION, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(10));
    public static final ConfiguredFeature<?, ?> FOSSILISED_WASP = register("ore_fossilised_wasp", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WASP, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(10));
    public static final ConfiguredFeature<?, ?> FOSSILISED_PIRAPORU = register("ore_fossilised_piraporu", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_PIRAPORU, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(48, 48))).squared().count(10));

    public static final ConfiguredFeature<?, ?> RED_ANT_INFESTED = register("ore_red_ant_infested", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.RED_ANT_INFESTED_ORE, 8)).range(16).squared());
    public static final ConfiguredFeature<?, ?> TERMITE_INFESTED = register("ore_termite_infested", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.STONE, States.TERMITE_INFESTED_ORE)).decorated(Placement.EMERALD_ORE.configured(IPlacementConfig.NONE)));

    public static final ConfiguredFeature<?, ?> ORE_COPPER = register("ore_copper", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.COPPER_ORE, 5)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(72, 12))).squared().count(20));
    public static final ConfiguredFeature<?, ?> ORE_TIN = register("ore_tin", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.TIN_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(56, 12))).squared().count(16));
    public static final ConfiguredFeature<?, ?> ORE_SILVER = register("ore_silver", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.SILVER_ORE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(32, 12))).squared().count(6));
    public static final ConfiguredFeature<?, ?> ORE_PLATINUM = register("ore_platinum", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.PLATINUM_ORE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(16, 12))).squared().count(3));
    public static final ConfiguredFeature<?, ?> ORE_SUNSTONE = register("ore_sunstone", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.SUNSTONE_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(24, 8))).squared().count(8));
    public static final ConfiguredFeature<?, ?> ORE_BLOODSTONE = register("ore_bloodstone", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.BLOODSTONE_ORE, 5)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(24, 6))).squared().count(8));

    public static final ConfiguredFeature<?, ?> NETHER_ORE_RUBY_LAVA = register("nether_ore_ruby", Feature.NO_SURFACE_ORE.configured(new OreFeatureConfig(new BlockMatchRuleTest(Blocks.LAVA), States.NETHER_RUBY_ORE, 8)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(12, 12))).squared().count(16));

    public static final ConfiguredFeature<?, ?> MINING_ORE_RUBY_LAVA = register("mining_ore_ruby", Feature.ORE.configured(new OreFeatureConfig(new BlockMatchRuleTest(Blocks.LAVA), CABlocks.RUBY_ORE.get().defaultBlockState(), 8)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(36, 24))).squared().count(28));
    public static final ConfiguredFeature<?, ?> MINING_ORE_RUBY_NO_SURFACE = register("mining_ore_ruby_surfaceless", Feature.NO_SURFACE_ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.RUBY_ORE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(24, 24))).squared().count(1));
    public static final ConfiguredFeature<?, ?> MINING_ORE_AMETHYST = register("mining_ore_amethyst", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.AMETHYST_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(40, 24))).squared().count(4));
    public static final ConfiguredFeature<?, ?> MINING_ORE_URANIUM = register("mining_ore_uranium", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.URANIUM_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(24, 24))).squared().count(3));
    public static final ConfiguredFeature<?, ?> MINING_ORE_TITANIUM = register("mining_ore_titanium", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.TITANIUM_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(24, 24))).squared().count(3));
    public static final ConfiguredFeature<?, ?> MINING_ORE_ALUMINUM = register("mining_ore_aluminum", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.ALUMINIUM_ORE, 5)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(40, 24))).squared().count(8));
    public static final ConfiguredFeature<?, ?> MINING_ORE_COPPER = register("mining_ore_copper", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.COPPER_ORE, 5)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(60, 24))).squared().count(6));
    public static final ConfiguredFeature<?, ?> MINING_ORE_TIN = register("mining_ore_tin", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.TIN_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(50, 18))).squared().count(5));
    public static final ConfiguredFeature<?, ?> MINING_ORE_SILVER = register("mining_ore_silver", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.SILVER_ORE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(40, 12))).squared().count(4));
    public static final ConfiguredFeature<?, ?> MINING_ORE_PLATINUM = register("mining_ore_platinum", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.PLATINUM_ORE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(30, 8))).squared().count(3));
    public static final ConfiguredFeature<?, ?> MINING_ORE_TIGERS_EYE = register("mining_ore_tigers_eye", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.TIGERS_EYE_ORE, 7)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(36, 24))).squared().count(5));
    public static final ConfiguredFeature<?, ?> MINING_ORE_SALT = register("mining_ore_salt", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.SALT_ORE, 8)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(40, 32))).squared().count(14));
    public static final ConfiguredFeature<?, ?> MINING_ORE_SUNSTONE = register("mining_ore_sunstone", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.SUNSTONE_ORE, 4)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(28, 8))).squared().count(8));
    public static final ConfiguredFeature<?, ?> MINING_ORE_BLOODSTONE = register("mining_ore_bloodstone", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.BLOODSTONE_ORE, 5)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(28, 6))).squared().count(8));
    public static final ConfiguredFeature<?, ?> MINING_RED_ANT_INFESTED = register("mining_ore_red_ant_infested", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.RED_ANT_INFESTED_ORE, 8)).range(16).squared());
    public static final ConfiguredFeature<?, ?> MINING_TERMITE_INFESTED = register("mining_ore_termite_infested", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.STONE, States.TERMITE_INFESTED_ORE)).decorated(Placement.EMERALD_ORE.configured(IPlacementConfig.NONE)));
    public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_ENT = register("mining_ore_fossilised_ent", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_ENT, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(60, 48))).squared().count(10));
    public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_HERCULES_BEETLE = register("mining_ore_fossilised_hercules_beetle", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_HERCULES_BEETLE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(60, 48))).squared().count(10));
    public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_RUBY_BUG = register("mining_ore_fossilised_ruby_bug", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_RUBY_BUG, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(60, 48))).squared().count(10));
    public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_EMERALD_GATOR = register("mining_ore_fossilised_emerald_gator", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_EMERALD_GATOR, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(60, 48))).squared().count(10));
    public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_WTF = register("mining_ore_fossilised_wtf", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WTF, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(60, 48))).squared().count(10));
    public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_SCORPION = register("mining_ore_fossilised_scorpion", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_SCORPION, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(60, 48))).squared().count(10));
    public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_WASP = register("mining_ore_fossilised_wasp", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_WASP, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(60, 48))).squared().count(10));
    public static final ConfiguredFeature<?, ?> MINING_FOSSILISED_PIRAPORU = register("mining_ore_fossilised_piraporu", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.FOSSILISED_PIRAPORU, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(60, 48))).squared().count(10));

    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> GREEN_CRYSTAL_TREE = register("green_crystal_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(CABlocks.CRYSTAL_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(CABlocks.GREEN_CRYSTAL_LEAVES.get().defaultBlockState()), new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 2), new StraightTrunkPlacer(3, 2, 0), new TwoLayerFeature(2, 0, 2)).ignoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> RED_CRYSTAL_TREE = register("red_crystal_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(CABlocks.CRYSTAL_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(CABlocks.RED_CRYSTAL_LEAVES.get().defaultBlockState()), new BlobFoliagePlacer(FeatureSpread.fixed(3), FeatureSpread.fixed(2), 2), new StraightTrunkPlacer(5, 2, 0), new TwoLayerFeature(2, 0, 2)).ignoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> YELLOW_CRYSTAL_TREE = register("yellow_crystal_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(CABlocks.CRYSTAL_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(CABlocks.YELLOW_CRYSTAL_LEAVES.get().defaultBlockState()), new BlobFoliagePlacer(FeatureSpread.fixed(4), FeatureSpread.fixed(3), 3), new StraightTrunkPlacer(7, 2, 0), new TwoLayerFeature(2, 0, 2)).ignoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> PEACH_TREE = register("peach_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(CABlocks.PEACH_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(CABlocks.PEACH_LEAVES.get().defaultBlockState()), new BlobFoliagePlacer(FeatureSpread.fixed(3), FeatureSpread.fixed(0), 2), new StraightTrunkPlacer(4, 3, 0), new TwoLayerFeature(2, 0, 2)).ignoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> PEACH_TREE_BEES_005 = register("peach_tree_bees_005", Feature.TREE.configured(PEACH_TREE.config.withDecorators(ImmutableList.of(CAConfiguredFeatures.Placements.BEEHIVE_005))));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> DUPLICATION_TREE = register("duplication_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(CABlocks.DUPLICATION_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(CABlocks.DUPLICATION_LEAVES.get().defaultBlockState()), new BlobFoliagePlacer(FeatureSpread.fixed(1), FeatureSpread.fixed(0), 1), new StraightTrunkPlacer(1, 0, 0), new TwoLayerFeature(1, 0, 1)).ignoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> DEAD_DUPLICATION_TREE = register("dead_duplication_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(CABlocks.DEAD_DUPLICATION_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(CABlocks.DUPLICATION_LEAVES.get().defaultBlockState()), new BlobFoliagePlacer(FeatureSpread.fixed(1), FeatureSpread.fixed(0), 1), new StraightTrunkPlacer(1, 0, 0), new TwoLayerFeature(1, 0, 1)).ignoreVines().build()));
    
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_DUPLICATION_TREE = register("fancy_duplication_tree", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(CAConfiguredFeatures.States.DUPLICATION_LOG), new SimpleBlockStateProvider(CAConfiguredFeatures.States.DUPLICATION_LEAVES), new FancyFoliagePlacer(FeatureSpread.fixed(1), FeatureSpread.fixed(0), 1), new FancyTrunkPlacer(1, 0, 0), new TwoLayerFeature(0, 0, 0, OptionalInt.of(1)))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_DEAD_DUPLICATION_TREE = register("fancy_dead_duplication_tree", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(CAConfiguredFeatures.States.DEAD_DUPLICATION_LOG), new SimpleBlockStateProvider(CAConfiguredFeatures.States.DUPLICATION_LEAVES), new FancyFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(1), 2), new FancyTrunkPlacer(1, 0, 0), new TwoLayerFeature(0, 0, 0, OptionalInt.of(3)))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_PEACH_TREE = register("fancy_peach_tree", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(CAConfiguredFeatures.States.PEACH_LOG), new SimpleBlockStateProvider(CAConfiguredFeatures.States.PEACH_LEAVES), new FancyFoliagePlacer(FeatureSpread.fixed(4), FeatureSpread.fixed(4), 4), new FancyTrunkPlacer(4, 3, 0), new TwoLayerFeature(2, 0, 2, OptionalInt.of(4)))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_PEACH_TREE_BEES_005 = register("fancy_peach_tree_bees_005", Feature.TREE.configured(FANCY_PEACH_TREE.config.withDecorators(ImmutableList.of(CAConfiguredFeatures.Placements.BEEHIVE_005))));
	
    public static final ConfiguredFeature<?, ?> TREES_CRYSTAL_PLAINS = register("trees_crystal_dimension", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(GREEN_CRYSTAL_TREE.weighted(0.4F), RED_CRYSTAL_TREE.weighted(0.3F), YELLOW_CRYSTAL_TREE.weighted(0.1F)), GREEN_CRYSTAL_TREE)).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(2, 0.1F, 1))));
    public static final ConfiguredFeature<?, ?> TREES_PEACH = register("trees_peach", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(PEACH_TREE.weighted(0.0001F), PEACH_TREE_BEES_005.weighted(0.0001F)), PEACH_TREE)).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(3).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.1F, 1))));
   // public static final ConfiguredFeature<?, ?> TREES_DUPLICATION = register("trees_duplication", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(DUPLICATION_TREE.weighted(0.0001F)), DUPLICATION_TREE)).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(1).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.000001F, 1))));
   // public static final ConfiguredFeature<?, ?> TREES_DEAD_DUPLICATION = register("trees_dead_duplication", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(DEAD_DUPLICATION_TREE.weighted(0.0001F)), DEAD_DUPLICATION_TREE)).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(1).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.000001F, 1))));

    
    public static final ConfiguredFeature<?, ?> CRYSTAL_ORE_ENERGY = register("crystal_ore_energy", Feature.ORE.configured(new OreFeatureConfig(RuleTests.BASE_STONE_CRYSTAL, States.CRYSTAL_ENERGY, 5)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(60, 7))).squared().count(5));
    public static final ConfiguredFeature<?, ?> GEODE_PINK_TOURMALINE = register("geode_pink_tourmaline", CAFeatures.GEODE.get().configured(new GeodeFeatureConfig(RuleTests.BASE_STONE_CRYSTAL, States.PINK_TOURMALINE, States.CLUSTER_PINK_TOURMALINE, 28, 48, 40)));
    public static final ConfiguredFeature<?, ?> GEODE_CATS_EYE = register("geode_cats_eye", CAFeatures.GEODE.get().configured(new GeodeFeatureConfig(RuleTests.BASE_STONE_CRYSTAL, States.CATS_EYE, States.CLUSTER_CATS_EYE, 5, 28, 15)));

    public static final ConfiguredFeature<?, ?> CORN_PATCH = register("plants_corn", Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.CORN_GROWN), new ColumnBlockPlacer(2, 4))).tries(20).xspread(4).yspread(0).zspread(4).noProjection().build()));
    public static final ConfiguredFeature<?, ?> TOMATO_PATCH = register("plants_tomatoes", Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.TOMATO_GROWN), new ColumnBlockPlacer(2, 4))).tries(20).xspread(4).yspread(0).zspread(4).noProjection().build()));

    public static final ConfiguredFeature<?, ?> BROWN_ANT_NEST = register("nest_brown_ant", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.GRASS_BLOCK, States.BROWN_ANT_NEST)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(80, 50))));
    public static final ConfiguredFeature<?, ?> RAINBOW_ANT_NEST = register("nest_rainbow_ant", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.GRASS_BLOCK, States.RAINBOW_ANT_NEST)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(80, 50))));
    public static final ConfiguredFeature<?, ?> RED_ANT_NEST = register("nest_red_ant", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.GRASS_BLOCK, States.RED_ANT_NEST)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(80, 50))));
    public static final ConfiguredFeature<?, ?> UNSTABLE_ANT_NEST = register("nest_unstable_ant", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.GRASS_BLOCK, States.UNSTABLE_ANT_NEST)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(80, 50))));
    public static final ConfiguredFeature<?, ?> TERMITE_NEST = register("nest_termite", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.GRASS_BLOCK, States.TERMITE_NEST)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(80, 50))));

    public static final ConfiguredFeature<?, ?> CRYSTAL_TERMITE_NEST = register("nest_crystal_termite", Feature.EMERALD_ORE.configured(new ReplaceBlockConfig(States.CRYSTAL_GRASS_BLOCK, States.CRYSTAL_TERMITE_NEST)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(80, 50))));

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        CommonSetupEvent.configFeatures.add(new FeatureWrapper(key, configuredFeature));
        return configuredFeature;
    }

    public static final class States {
        protected static final BlockState GRASS_BLOCK = Blocks.GRASS_BLOCK.defaultBlockState();
        protected static final BlockState PEACH_LOG = CABlocks.PEACH_LOG.get().defaultBlockState();
        protected static final BlockState DUPLICATION_LOG = CABlocks.DUPLICATION_LOG.get().defaultBlockState();
        protected static final BlockState DEAD_DUPLICATION_LOG = CABlocks.DEAD_DUPLICATION_LOG.get().defaultBlockState();
        protected static final BlockState DUPLICATION_LEAVES = CABlocks.DUPLICATION_LEAVES.get().defaultBlockState();
        protected static final BlockState PEACH_LEAVES = CABlocks.PEACH_LEAVES.get().defaultBlockState();
        protected static final BlockState STONE = Blocks.STONE.defaultBlockState();
        protected static final BlockState RUBY_ORE = CABlocks.RUBY_ORE.get().defaultBlockState();
        protected static final BlockState NETHER_RUBY_ORE = CABlocks.NETHER_RUBY_ORE.get().defaultBlockState();
        protected static final BlockState AMETHYST_ORE = CABlocks.AMETHYST_ORE.get().defaultBlockState();
        protected static final BlockState URANIUM_ORE = CABlocks.URANIUM_ORE.get().defaultBlockState();
        protected static final BlockState TITANIUM_ORE = CABlocks.TITANIUM_ORE.get().defaultBlockState();
        protected static final BlockState TIGERS_EYE_ORE = CABlocks.TIGERS_EYE_ORE.get().defaultBlockState();
        protected static final BlockState ALUMINIUM_ORE = CABlocks.ALUMINUM_ORE.get().defaultBlockState();
        protected static final BlockState COPPER_ORE = CABlocks.COPPER_ORE.get().defaultBlockState();
        protected static final BlockState TIN_ORE = CABlocks.TIN_ORE.get().defaultBlockState();
        protected static final BlockState SILVER_ORE = CABlocks.SILVER_ORE.get().defaultBlockState();
        protected static final BlockState PLATINUM_ORE = CABlocks.PLATINUM_ORE.get().defaultBlockState();
        protected static final BlockState SUNSTONE_ORE = CABlocks.SUNSTONE_ORE.get().defaultBlockState();
        protected static final BlockState BLOODSTONE_ORE = CABlocks.BLOODSTONE_ORE.get().defaultBlockState();
        protected static final BlockState SALT_ORE = CABlocks.SALT_ORE.get().defaultBlockState();
        protected static final BlockState FOSSILISED_ENT = CABlocks.FOSSILISED_ENT.get().defaultBlockState();
        protected static final BlockState FOSSILISED_HERCULES_BEETLE = CABlocks.FOSSILISED_HERCULES_BEETLE.get().defaultBlockState();
        protected static final BlockState FOSSILISED_RUBY_BUG = CABlocks.FOSSILISED_RUBY_BUG.get().defaultBlockState();
        protected static final BlockState FOSSILISED_EMERALD_GATOR = CABlocks.FOSSILISED_EMERALD_GATOR.get().defaultBlockState();
        protected static final BlockState FOSSILISED_WTF = CABlocks.FOSSILISED_WTF.get().defaultBlockState();
        protected static final BlockState FOSSILISED_SCORPION = CABlocks.FOSSILISED_SCORPION.get().defaultBlockState();
        protected static final BlockState FOSSILISED_WASP = CABlocks.FOSSILISED_WASP.get().defaultBlockState();
        protected static final BlockState FOSSILISED_PIRAPORU = CABlocks.FOSSILISED_PIRAPORU.get().defaultBlockState();
        protected static final BlockState RED_ANT_INFESTED_ORE = CABlocks.RED_ANT_INFESTED_ORE.get().defaultBlockState();
        protected static final BlockState TERMITE_INFESTED_ORE = CABlocks.TERMITE_INFESTED_ORE.get().defaultBlockState();
        protected static final BlockState CRYSTAL_ENERGY = CABlocks.CRYSTAL_ENERGY.get().defaultBlockState();
        protected static final BlockState PINK_TOURMALINE = CABlocks.BUDDING_PINK_TOURMALINE.get().defaultBlockState();
        protected static final BlockState CATS_EYE = CABlocks.BUDDING_CATS_EYE.get().defaultBlockState();
        protected static final BlockState CLUSTER_PINK_TOURMALINE = CABlocks.PINK_TOURMALINE_CLUSTER.get().defaultBlockState();
        protected static final BlockState CLUSTER_CATS_EYE = CABlocks.CATS_EYE_CLUSTER.get().defaultBlockState();
        protected static final BlockState CORN_GROWN = CABlocks.CORN_PLANT.get().defaultBlockState();
        protected static final BlockState TOMATO_GROWN = CABlocks.TOMATO_PLANT.get().defaultBlockState();
        protected static final BlockState BROWN_ANT_NEST = CABlocks.BROWN_ANT_NEST.get().defaultBlockState();
        protected static final BlockState RAINBOW_ANT_NEST = CABlocks.RAINBOW_ANT_NEST.get().defaultBlockState();
        protected static final BlockState RED_ANT_NEST = CABlocks.RED_ANT_NEST.get().defaultBlockState();
        protected static final BlockState UNSTABLE_ANT_NEST = CABlocks.UNSTABLE_ANT_NEST.get().defaultBlockState();
        protected static final BlockState TERMITE_NEST = CABlocks.TERMITE_NEST.get().defaultBlockState();
        protected static final BlockState CRYSTAL_GRASS_BLOCK = CABlocks.CRYSTAL_GRASS_BLOCK.get().defaultBlockState();
        protected static final BlockState CRYSTAL_TERMITE_NEST = CABlocks.CRYSTAL_TERMITE_NEST.get().defaultBlockState();
    }
    
    public static final class Placements {
    	public static final BeehiveTreeDecorator BEEHIVE_005 = new BeehiveTreeDecorator(0.05f);
    }

    public static final class RuleTests {
        //public static final RuleTest BASE_STONE_MINING = new TagMatchRuleTest(BlockTags.getCollection().getTagByID(new ResourceLocation(ChaosAwakens.MODID + "base_stone_mining")));
        public static final RuleTest BASE_STONE_CRYSTAL = new TagMatchRuleTest(CATags.BASE_STONE_CRYSTAL);
    }
}