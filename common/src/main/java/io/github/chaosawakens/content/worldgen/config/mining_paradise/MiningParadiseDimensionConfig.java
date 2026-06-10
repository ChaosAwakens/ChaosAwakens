package io.github.chaosawakens.content.worldgen.config.mining_paradise;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.content.registry.*;
import io.github.chaosawakens.content.worldgen.chunk_gen.chunk.OptimizedNoiseBasedChunkGenerator;
import io.github.chaosawakens.content.worldgen.config.base.DimensionLevelStemConfig;
import io.github.chaosawakens.content.worldgen.config.mining_paradise.biome.MiningParadiseBiomeBuilder;
import io.github.chaosawakens.content.worldgen.surface_rule.CASurfaceRules;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.NotNull;

import java.util.OptionalLong;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static net.minecraft.world.level.levelgen.NoiseRouterData.yLimitedInterpolatable;

public class MiningParadiseDimensionConfig implements DimensionLevelStemConfig {
    public static final NoiseSettings BASE_NOISE_SETTINGS = NoiseSettings.create(-128, 512, 1, 4);

    public MiningParadiseDimensionConfig() {
    }

    @Override
    public @NotNull Supplier<ResourceKey<DimensionType>> getParentDimensionType() {
        return CADimensions.MINING_PARADISE_DIMENSION_TYPE;
    }

    @Override
    public @NotNull ChunkGenerator createLevelChunkGen(BootstapContext<LevelStem> regCtx) {
        HolderGetter<NoiseGeneratorSettings> noiseGenSettingsLookup = regCtx.lookup(Registries.NOISE_SETTINGS);
        HolderGetter<MultiNoiseBiomeSourceParameterList> biomeSrcParamListLookup = regCtx.lookup(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST);

        BiomeSource src = MultiNoiseBiomeSource.createFromPreset(biomeSrcParamListLookup.getOrThrow(CAMultiNoiseBiomeSourceParameterLists.MINING_PARADISE_BIOME_LIST.get()));
        Holder.Reference<NoiseGeneratorSettings> settings = noiseGenSettingsLookup.getOrThrow(CANoiseGeneratorSettings.MINING_PARADISE.get());

        return new OptimizedNoiseBasedChunkGenerator(src, settings);
    }

    public static DimensionType createDimensionType() {
        return new DimensionType(OptionalLong.empty(), true, false, false, true, 1.0D, true, false, -128, 512, 384, BlockTags.INFINIBURN_OVERWORLD, BuiltinDimensionTypes.OVERWORLD_EFFECTS, 0.0F, new DimensionType.MonsterSettings(false, false, ConstantInt.of(4), 0));
    }

    public static Supplier<NoiseGeneratorSettings> createMiningParadiseNoiseGenSettings(BootstapContext<NoiseGeneratorSettings> regCtx) {
        return () -> new NoiseGeneratorSettings(
                BASE_NOISE_SETTINGS,
                CABlocks.DREDGESTONE.stoneBlockFamily().get(CAConstants.prefix("dredgestone")).get().defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                createMiningParadiseNoiseRouter(regCtx),
                createMiningParadiseSurfaceRules(),
                createMiningParadiseClimateSpawnConfiguration(regCtx),
                92,  // sea level
                false,  // disableMobGeneration
                true,   // aquifersEnabled
                false,   // oreVeinsEnabled
                false   // useLegacyRandomSource
        );
    }

    public static Supplier<MultiNoiseBiomeSourceParameterList> createMiningParadiseNoiseBiomes(BootstapContext<MultiNoiseBiomeSourceParameterList> regCtx) {
        return () -> new MultiNoiseBiomeSourceParameterList(CAMultiNoiseBiomeSourceParameterLists.Presets.MINING_PARADISE_BIOME_SOURCE_PRESET.get(), regCtx.lookup(Registries.BIOME));
    }

    public static <T> Climate.ParameterList<T> generateMiningParadiseBiomes(Function<ResourceKey<Biome>, T> biomeValueMapper) {
        ImmutableList.Builder<Pair<Climate.ParameterPoint, T>> mappedClimateParameterPointList = ImmutableList.builder();

        new MiningParadiseBiomeBuilder().mapBiomes(mappedBiome -> mappedClimateParameterPointList.add(mappedBiome.mapSecond(biomeValueMapper)));

        return new Climate.ParameterList<>(mappedClimateParameterPointList.build());
    }

    public static SurfaceRules.RuleSource createMiningParadiseSurfaceRules() {
        // Order is important here: bottom to top

        SurfaceRules.RuleSource defaultSurfaceRuleSource = SurfaceRules.sequence(CASurfaceRules.ADD_GLOOMSTONE_LAYER, CASurfaceRules.ADD_DENSE_GRASS_BLOCK_TOP);
        SurfaceRules.RuleSource stalagmiteValleySurfaceRuleSource = CASurfaceRules.ADD_GLOOMSTONE_LAYER;

        SurfaceRules.RuleSource stalagmiteValleyRuleSource = SurfaceRules.ifTrue(SurfaceRules.isBiome(CABiomes.STALAGMITE_VALLEY.get()), stalagmiteValleySurfaceRuleSource);
        SurfaceRules.RuleSource denseMountainsRuleSource = SurfaceRules.ifTrue(SurfaceRules.isBiome(CABiomes.DENSE_MOUNTAINS.get()), defaultSurfaceRuleSource);
        SurfaceRules.RuleSource densePlainsRuleSource = SurfaceRules.ifTrue(SurfaceRules.isBiome(CABiomes.DENSE_PLAINS.get()), defaultSurfaceRuleSource);
        SurfaceRules.RuleSource densewoodForestRuleSource = SurfaceRules.ifTrue(SurfaceRules.isBiome(CABiomes.DENSEWOOD_FOREST.get()), defaultSurfaceRuleSource);
        SurfaceRules.RuleSource mesozoicJungleRuleSource = SurfaceRules.ifTrue(SurfaceRules.isBiome(CABiomes.MESOZOIC_JUNGLE.get()), defaultSurfaceRuleSource);
        SurfaceRules.RuleSource ginkgoForestRuleSource = SurfaceRules.ifTrue(SurfaceRules.isBiome(CABiomes.GINKGO_FOREST.get()), defaultSurfaceRuleSource);

        return SurfaceRules.sequence(CASurfaceRules.ADD_BEDROCK_LAYER, densewoodForestRuleSource, densePlainsRuleSource, mesozoicJungleRuleSource, ginkgoForestRuleSource, denseMountainsRuleSource, stalagmiteValleyRuleSource);
    }

    protected static NoiseRouter createMiningParadiseNoiseRouter(BootstapContext<NoiseGeneratorSettings> regCtx) {
        DensityFunction aquiferBarrier = DensityFunctions.mul(DensityFunctions.constant(10), DensityFunctions.add(DensityFunctions.constant(0.75), DensityFunctions.noise(regCtx.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.MINING_AQUIFER_BARRIER.get()), 0.5D, 0.5D)));
        DensityFunction fluidFloodedness = DensityFunctions.noise(regCtx.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.MINING_AQUIFER_FLUID_LEVEL_FLOODEDNESS.get()), 0.5D, 2.0D);
        DensityFunction fluidSpread = DensityFunctions.noise(regCtx.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.MINING_AQUIFER_FLUID_LEVEL_SPREAD.get()), 2.0D, 2.0D);
        DensityFunction aquiferLava = DensityFunctions.noise(regCtx.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.MINING_AQUIFER_LAVA.get()), 1.0D);
        DensityFunction shiftX = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.SHIFT_X);
        DensityFunction shiftZ = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.SHIFT_Z);
        DensityFunction shiftedTemperature = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.5D, regCtx.lookup(Registries.NOISE).getOrThrow(Noises.TEMPERATURE));
        DensityFunction shiftedVegetation = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.5D, regCtx.lookup(Registries.NOISE).getOrThrow(Noises.VEGETATION));
        DensityFunction y = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.Y);
        DensityFunction[] oreFunctions = createOreDensityFunctions(y, regCtx.lookup(Registries.NOISE));
        DensityFunction landContinents = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.MINING_PARADISE_CONTINENTS);
        DensityFunction landErosion = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.MINING_PARADISE_EROSION);
        DensityFunction terrainWeirdness = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.MINING_PARADISE_RIDGES_FOLDED);
        DensityFunction terrainDepth = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.MINING_PARADISE_DEPTH);
        DensityFunction slopedCheese = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.MINING_PARADISE_SLOPED_CHEESE);
        DensityFunction entrances = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.MINING_PARADISE_ENTRANCES);
        DensityFunction spaghetti = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.MINING_PARADISE_SPAGHETTI_2D);
        DensityFunction spaghettiRoughness = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.MINING_PARADISE_SPAGHETTI_ROUGHNESS);
        DensityFunction pillars = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.MINING_PARADISE_PILLARS);
        DensityFunction noodle = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.MINING_PARADISE_NOODLE);


        DensityFunction initialLandDensity =
                DensityFunctions.add(
                        DensityFunctions.constant(0.1171875F),
                        DensityFunctions.mul(
                                DensityFunctions.yClampedGradient(-128, -104, 0D, 1D),
                                DensityFunctions.add(
                                        DensityFunctions.constant(-0.1171875F),
                                        DensityFunctions.add(
                                                DensityFunctions.constant(-0.078125F),
                                                DensityFunctions.mul(
                                                        DensityFunctions.yClampedGradient(338, 364, 1D, 0D),
                                                        DensityFunctions.add(
                                                                DensityFunctions.constant(0.078125F),
                                                                slopedCheese)
                                                ).clamp(-128, 92)
                                        ))
                        ));
        DensityFunction finalLandDensity = DensityFunctions.min(
                DensityFunctions.mul(
                        DensityFunctions.constant(0.64),
                        DensityFunctions.interpolated(
                                DensityFunctions.blendDensity(
                                        DensityFunctions.add(DensityFunctions.constant(0.1171875F),
                                                DensityFunctions.mul( DensityFunctions.yClampedGradient(-128, -104, 0D, 1D),
                                                        DensityFunctions.add(DensityFunctions.constant(-0.1171875F),
                                                                DensityFunctions.add(DensityFunctions.constant(-0.078125F),
                                                                        DensityFunctions.mul(DensityFunctions.yClampedGradient(338, 364, 1D, 0D),
                                                                                DensityFunctions.add(DensityFunctions.constant(0.078125F),
                                                                                        DensityFunctions.rangeChoice(
                                                                                                slopedCheese, -3, 0.6,
                                                                                                DensityFunctions.min(
                                                                                                        slopedCheese, DensityFunctions.mul(
                                                                                                                DensityFunctions.constant(5), entrances)), DensityFunctions.max(
                                                                                                        DensityFunctions.min(
                                                                                                                DensityFunctions.min(
                                                                                                                        DensityFunctions.add(
                                                                                                                                DensityFunctions.mul(
                                                                                                                                        DensityFunctions.constant(4),
                                                                                                                                        DensityFunctions.noise(
                                                                                                                                                regCtx.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.MINING_CAVE_LAYER.get()), 1, 8)),
                                                                                                                                DensityFunctions.add(
                                                                                                                                        DensityFunctions.add(
                                                                                                                                                DensityFunctions.constant(0.27),
                                                                                                                                                DensityFunctions.noise(regCtx.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.MINING_CAVE_CHEESE.get()), 1, 0.666)
                                                                                                                                        ).clamp(-0.25,0.75),
                                                                                                                                        DensityFunctions.add(
                                                                                                                                                        DensityFunctions.constant(1.5),
                                                                                                                                                        DensityFunctions.mul(
                                                                                                                                                                DensityFunctions.constant(-0.64),
                                                                                                                                                                slopedCheese))
                                                                                                                                                .clamp(-0.05, 0)
                                                                                                                                )),
                                                                                                                        entrances),
                                                                                                                DensityFunctions.add(
                                                                                                                        spaghetti,
                                                                                                                        spaghettiRoughness)),
                                                                                                        DensityFunctions.rangeChoice(
                                                                                                                pillars,
                                                                                                                0,
                                                                                                                1,
                                                                                                                DensityFunctions.constant(1),
                                                                                                                pillars)
                                                                                                ))
                                                                                ))
                                                                ))
                                                ))
                                ))
                ).squeeze(),
                noodle
        );

        return new NoiseRouter(
                aquiferBarrier,
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                aquiferLava,
                shiftedTemperature,
                shiftedVegetation,
                landContinents,
                landErosion,
                terrainDepth,
                terrainWeirdness,
                initialLandDensity,
                finalLandDensity,
                oreFunctions[0], // veininess
                oreFunctions[1], // vein a/b
                oreFunctions[2]  // ore gap
        );
    }

    protected static ObjectArrayList<Climate.ParameterPoint> createMiningParadiseClimateSpawnConfiguration(BootstapContext<NoiseGeneratorSettings> regCtx) {
        return ObjectArrayList.of();
    }

    private static DensityFunction[] createOreDensityFunctions(DensityFunction yFunc, HolderGetter<NormalNoise.NoiseParameters> noiseParams) {
        int minY = Stream.of(OreVeinifier.VeinType.values())
                .mapToInt(v -> v.minY)
                .min().orElse(-DimensionType.MIN_Y * 2);

        int maxY = Stream.of(OreVeinifier.VeinType.values())
                .mapToInt(v -> v.maxY)
                .max().orElse(-DimensionType.MIN_Y * 2);

        DensityFunction veininess = yLimitedInterpolatable(yFunc, DensityFunctions.noise(noiseParams.getOrThrow(Noises.ORE_VEININESS), 1.5D, 1.5D), minY, maxY, 0);

        DensityFunction veinA = yLimitedInterpolatable(yFunc, DensityFunctions.noise(noiseParams.getOrThrow(Noises.ORE_VEIN_A), 4.0D, 4.0D), minY, maxY, 0).abs();
        DensityFunction veinB = yLimitedInterpolatable(yFunc, DensityFunctions.noise(noiseParams.getOrThrow(Noises.ORE_VEIN_B), 4.0D, 4.0D), minY, maxY, 0).abs();
        DensityFunction veinAB = DensityFunctions.add(DensityFunctions.constant(-0.08), DensityFunctions.max(veinA, veinB));

        DensityFunction oreGap = DensityFunctions.noise(noiseParams.getOrThrow(Noises.ORE_GAP));

        return new DensityFunction[]{veininess, veinAB, oreGap};
    }
}