package io.github.chaosawakens.worldgen;

import io.github.chaosawakens.ChaosAwakens;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.BlockState;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class Initializer implements ModInitializer {
    /*
    private static final ConfiguredFeature<?, ?> ORE_RUBY_LAVA = registerCustom(io.github.chaosawakens.blocks.Blocks.RUBY_ORE.getDefaultState(), new BlockMatchRuleTest(net.minecraft.block.Blocks.LAVA), 8, 24, 24, 28);
    private static final ConfiguredFeature<?, ?> ORE_RUBY_NO_SURFACE = registerNoSurface(io.github.chaosawakens.blocks.Blocks.RUBY_ORE.getDefaultState(), 2, 12, 12, 1);
    private static final ConfiguredFeature<?, ?> ORE_AMETHYST = register(io.github.chaosawakens.blocks.Blocks.AMETHYST_ORE.getDefaultState(), 4, 24, 28, 4);
    private static final ConfiguredFeature<?, ?> ORE_URANIUM = register(io.github.chaosawakens.blocks.Blocks.URANIUM_ORE.getDefaultState(), 3, 12, 12, 2);
    private static final ConfiguredFeature<?, ?> ORE_TITANIUM = register(io.github.chaosawakens.blocks.Blocks.TITANIUM_ORE.getDefaultState(), 3, 12, 12, 2);
    private static final ConfiguredFeature<?, ?> ORE_TIGERS_EYE = register(io.github.chaosawakens.blocks.Blocks.TIGERS_EYE_ORE.getDefaultState(), 7, 24, 24, 5);
    private static final ConfiguredFeature<?, ?> ORE_ALUMINIUM = register(io.github.chaosawakens.blocks.Blocks.ALUMINIUM_ORE.getDefaultState(), 5, 28, 24, 8);
    private static final ConfiguredFeature<?, ?> ORE_SALT = register(io.github.chaosawakens.blocks.Blocks.SALT_ORE.getDefaultState(), 10, 64, 64, 28);
    private static final ConfiguredFeature<?, ?> FOSSILISED_ENT = register(io.github.chaosawakens.blocks.Blocks.FOSSILISED_ENT.getDefaultState(), 3, 64, 64, 14);
    private static final ConfiguredFeature<?, ?> FOSSILISED_HERCULES_BEETLE = register(io.github.chaosawakens.blocks.Blocks.FOSSILISED_HERCULES_BEETLE.getDefaultState(), 3, 64, 64, 14);
    private static final ConfiguredFeature<?, ?> FOSSILISED_RUBY_BUG = register(io.github.chaosawakens.blocks.Blocks.FOSSILISED_RUBY_BUG.getDefaultState(), 3, 64, 64, 14);
    private static final ConfiguredFeature<?, ?> FOSSILISED_EMERALD_GATOR = register(io.github.chaosawakens.blocks.Blocks.FOSSILISED_EMERALD_GATOR.getDefaultState(), 3, 64, 64, 14);
    private static final ConfiguredFeature<?, ?> RED_ANT_INFESTED_ORE = register(io.github.chaosawakens.blocks.Blocks.RED_ANT_INFESTED_ORE.getDefaultState(), 10, 16, 16, 6);
    private static final ConfiguredFeature<?, ?> TERMITE_INFESTED_ORE = register(io.github.chaosawakens.blocks.Blocks.TERMITE_INFESTED_ORE.getDefaultState(), 10, 16, 16, 2);

    private static final ConfiguredFeature<?, ?> register(BlockState blockstate, int size, int baseline, int spread, int count) {
        return Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, blockstate, size)).decorate(Decorator.DEPTH_AVERAGE.configure(new DepthAverageDecoratorConfig(baseline, spread))).spreadHorizontally().repeat(count);
    }
    private static final ConfiguredFeature<?, ?> registerNoSurface(BlockState blockstate, int size, int baseline, int spread, int count) {
        return Feature.NO_SURFACE_ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, blockstate, size)).decorate(Decorator.DEPTH_AVERAGE.configure(new DepthAverageDecoratorConfig(baseline, spread))).spreadHorizontally().repeat(count);
    }
    private static final ConfiguredFeature<?, ?> registerCustom(BlockState blockstate, RuleTest rule, int size, int baseline, int spread, int count) {
        return Feature.ORE.configure(new OreFeatureConfig(rule, blockstate, size)).decorate(Decorator.DEPTH_AVERAGE.configure(new DepthAverageDecoratorConfig(baseline, spread))).spreadHorizontally().repeat(count);
    }
    private static final ConfiguredFeature<?, ?> registerCustomNoSurface(BlockState blockstate, RuleTest rule, int size, int baseline, int spread, int count) {
        return Feature.NO_SURFACE_ORE.configure(new OreFeatureConfig(rule, blockstate, size)).decorate(Decorator.DEPTH_AVERAGE.configure(new DepthAverageDecoratorConfig(baseline, spread))).spreadHorizontally().repeat(count);
    }
    */

    @Override
    public void onInitialize() {
        /*
        initRegister(ORE_RUBY_LAVA, "ore_ruby_lava");
        initRegister(ORE_RUBY_NO_SURFACE, "ore_ruby_no_surface");
        initRegister(ORE_AMETHYST, "ore_amethyst");
        initRegister(ORE_URANIUM, "ore_uranium");
        initRegister(ORE_TITANIUM, "ore_titanium");
        initRegister(ORE_TIGERS_EYE, "ore_tigers_eye");
        initRegister(ORE_ALUMINIUM, "ore_aluminium");
        initRegister(ORE_SALT, "ore_salt");
        initRegister(FOSSILISED_ENT, "fossilised_ent");
        initRegister(FOSSILISED_HERCULES_BEETLE, "fossilised_hercules_beetle");
        initRegister(FOSSILISED_RUBY_BUG, "fossilised_ruby_bug");
        initRegister(FOSSILISED_EMERALD_GATOR, "fossilised_emerald_gator");
        initRegister(RED_ANT_INFESTED_ORE, "red_ant_infested_ore");
        initRegister(TERMITE_INFESTED_ORE, "termite_infested_ore");
        */
    }

    /*
    private final void initRegister(ConfiguredFeature feature, String path) {
        RegistryKey<ConfiguredFeature<?, ?>> configuredFeature = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, new Identifier(ChaosAwakens.modID, path));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, configuredFeature.getValue(), feature);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, configuredFeature);
    }
    */
}
