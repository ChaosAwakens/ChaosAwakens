package io.github.chaosawakens.registry;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;

public class CAFeatures {
    public static final ConfiguredFeature<?, ?> ORE_RUBY_LAVA = register("ore_ruby", Feature.ORE.withConfiguration(new OreFeatureConfig(new BlockMatchRuleTest(Blocks.LAVA), ModBlocks.RUBY_ORE.get().getDefaultState(), 8)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(24, 24))).square().count(28));
    public static final ConfiguredFeature<?, ?> ORE_RUBY_NO_SURFACE = register("ore_ruby", Feature.NO_SURFACE_ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.RUBY_ORE, 2)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(12, 12))).square().count(1));
    public static final ConfiguredFeature<?, ?> ORE_AMETHYST = register("ore_amethyst", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.AMETHYST_ORE, 4)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(28, 24))).square().count(4));
    public static final ConfiguredFeature<?, ?> ORE_URANIUM = register("ore_uranium", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.URANIUM_ORE, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(12, 12))).square().count(2));
    public static final ConfiguredFeature<?, ?> ORE_TITANIUM = register("ore_titanium", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.TITANIUM_ORE, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(12, 12))).square().count(2));
    public static final ConfiguredFeature<?, ?> ORE_TIGERS_EYE_ORE = register("ore_tigers_eye_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.TIGERS_EYE_ORE, 7)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(24, 24))).square().count(5));
    public static final ConfiguredFeature<?, ?> ORE_ALUMINIUM = register("ore_aluminium", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.ALUMINIUM_ORE, 5)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(28, 24))).square().count(8));
    public static final ConfiguredFeature<?, ?> ORE_SALT = register("ore_salt", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.SALT_ORE, 10)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(64, 64))).square().count(28));
    public static final ConfiguredFeature<?, ?> FOSSILISED_ENT = register("ore_fossilised_ent", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.FOSSILISED_ENT, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(64, 64))).square().count(14));
    public static final ConfiguredFeature<?, ?> FOSSILISED_HERCULES_BEETLE = register("ore_fossilised_hercules_beetle", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.FOSSILISED_HERCULES_BEETLE, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(64, 64))).square().count(14));
    public static final ConfiguredFeature<?, ?> FOSSILISED_RUBY_BUG = register("ore_fossilised_ruby_bug", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.FOSSILISED_RUBY_BUG, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(64, 64))).square().count(14));
    public static final ConfiguredFeature<?, ?> FOSSILISED_EMERALD_GATOR = register("ore_fossilised_emerald_gator", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.FOSSILISED_EMERALD_GATOR, 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(64, 64))).square().count(14));
    public static final ConfiguredFeature<?, ?> RED_ANT_INFESTED_ORE = register("ore_red_ant_infested_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.RED_ANT_INFESTED_ORE, 10)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(16, 16))).square().count(6));
    public static final ConfiguredFeature<?, ?> TERMITE_INFESTED_ORE = register("ore_termite_infested_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.TERMITE_INFESTED_ORE, 10)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(16, 16))).square().count(2));

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }

    public static final class States {
        protected static final BlockState STONE = Blocks.STONE.getDefaultState();
        protected static final BlockState RUBY_ORE = ModBlocks.RUBY_ORE.get().getDefaultState();
        protected static final BlockState AMETHYST_ORE = ModBlocks.AMETHYST_ORE.get().getDefaultState();
        protected static final BlockState URANIUM_ORE = ModBlocks.URANIUM_ORE.get().getDefaultState();
        protected static final BlockState TITANIUM_ORE = ModBlocks.TITANIUM_ORE.get().getDefaultState();
        protected static final BlockState TIGERS_EYE_ORE = ModBlocks.TIGERS_EYE_ORE.get().getDefaultState();
        protected static final BlockState ALUMINIUM_ORE = ModBlocks.ALUMINIUM_ORE.get().getDefaultState();
        protected static final BlockState SALT_ORE = ModBlocks.SALT_ORE.get().getDefaultState();
        protected static final BlockState FOSSILISED_ENT = ModBlocks.FOSSILISED_ENT.get().getDefaultState();
        protected static final BlockState FOSSILISED_HERCULES_BEETLE = ModBlocks.FOSSILISED_HERCULES_BEETLE.get().getDefaultState();
        protected static final BlockState FOSSILISED_RUBY_BUG = ModBlocks.FOSSILISED_RUBY_BUG.get().getDefaultState();
        protected static final BlockState FOSSILISED_EMERALD_GATOR = ModBlocks.FOSSILISED_EMERALD_GATOR.get().getDefaultState();
        protected static final BlockState RED_ANT_INFESTED_ORE = ModBlocks.RED_ANT_INFESTED_ORE.get().getDefaultState();
        protected static final BlockState TERMITE_INFESTED_ORE = ModBlocks.TERMITE_INFESTED_ORE.get().getDefaultState();
    }
}
