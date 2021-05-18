package io.github.chaosawakens.common.registry;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;

public class CAFeatures {
    public static final ConfiguredFeature<?, ?> ORE_RUBY_LAVA = register("ore_ruby", Feature.ORE.withConfiguration(new OreFeatureConfig(new BlockMatchRuleTest(Blocks.LAVA), CABlocks.RUBY_ORE.get().getDefaultState(), 8)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(24, 24))).square().count(28));
    public static final ConfiguredFeature<?, ?> ORE_RUBY_NO_SURFACE = register("ore_ruby", Feature.NO_SURFACE_ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.RUBY_ORE, 2)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(12, 12))).square().count(1));
    public static final ConfiguredFeature<?, ?> ORE_AMETHYST = register("ore_amethyst", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.AMETHYST_ORE, 4)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(28, 24))).square().count(4));
    public static final ConfiguredFeature<?, ?> ORE_URANIUM = register("ore_uranium", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.URANIUM_ORE, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(12, 12))).square().count(2));
    public static final ConfiguredFeature<?, ?> ORE_TITANIUM = register("ore_titanium", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.TITANIUM_ORE, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(12, 12))).square().count(2));
    public static final ConfiguredFeature<?, ?> ORE_TIGERS_EYE_ORE = register("ore_tigers_eye_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.TIGERS_EYE_ORE, 7)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(24, 24))).square().count(5));
    public static final ConfiguredFeature<?, ?> ORE_ALUMINUM = register("ore_aluminum", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.ALUMINIUM_ORE, 5)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(28, 24))).square().count(8));
    public static final ConfiguredFeature<?, ?> ORE_COPPER = register("ore_copper", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.COPPER_ORE, 5)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(24, 12))).square().count(6));
    public static final ConfiguredFeature<?, ?> ORE_TIN = register("ore_tin", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.TIN_ORE, 4)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(24, 12))).square().count(5));
    public static final ConfiguredFeature<?, ?> ORE_SILVER = register("ore_silver", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.SILVER_ORE, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(18, 12))).square().count(4));
    public static final ConfiguredFeature<?, ?> ORE_PLATINUM = register("ore_platinum", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.PLATINUM_ORE, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(12, 12))).square().count(3));
    public static final ConfiguredFeature<?, ?> ORE_SALT = register("ore_salt", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.SALT_ORE, 8)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(48, 48))).square().count(18));
    public static final ConfiguredFeature<?, ?> FOSSILISED_ENT = register("ore_fossilised_ent", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.FOSSILISED_ENT, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(48, 48))).square().count(10));
    public static final ConfiguredFeature<?, ?> FOSSILISED_HERCULES_BEETLE = register("ore_fossilised_hercules_beetle", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.FOSSILISED_HERCULES_BEETLE, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(48, 48))).square().count(10));
    public static final ConfiguredFeature<?, ?> FOSSILISED_RUBY_BUG = register("ore_fossilised_ruby_bug", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.FOSSILISED_RUBY_BUG, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(48, 48))).square().count(10));
    public static final ConfiguredFeature<?, ?> FOSSILISED_EMERALD_GATOR = register("ore_fossilised_emerald_gator", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.FOSSILISED_EMERALD_GATOR, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(48, 48))).square().count(10));
    public static final ConfiguredFeature<?, ?> RED_ANT_INFESTED_ORE = register("ore_red_ant_infested_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.RED_ANT_INFESTED_ORE, 8)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(12, 12))).square().count(5));
    public static final ConfiguredFeature<?, ?> TERMITE_INFESTED_ORE = register("ore_termite_infested_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.TERMITE_INFESTED_ORE, 8)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(12, 12))).square().count(2));

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
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
        protected static final BlockState SALT_ORE = CABlocks.SALT_ORE.get().getDefaultState();
        protected static final BlockState FOSSILISED_ENT = CABlocks.FOSSILISED_ENT.get().getDefaultState();
        protected static final BlockState FOSSILISED_HERCULES_BEETLE = CABlocks.FOSSILISED_HERCULES_BEETLE.get().getDefaultState();
        protected static final BlockState FOSSILISED_RUBY_BUG = CABlocks.FOSSILISED_RUBY_BUG.get().getDefaultState();
        protected static final BlockState FOSSILISED_EMERALD_GATOR = CABlocks.FOSSILISED_EMERALD_GATOR.get().getDefaultState();
        protected static final BlockState RED_ANT_INFESTED_ORE = CABlocks.RED_ANT_INFESTED_ORE.get().getDefaultState();
        protected static final BlockState TERMITE_INFESTED_ORE = CABlocks.TERMITE_INFESTED_ORE.get().getDefaultState();
    }
}
