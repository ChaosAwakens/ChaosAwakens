package io.github.chaosawakens.common.worldgen.config.mining_paradise;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import io.github.chaosawakens.common.registry.*;
import io.github.chaosawakens.common.worldgen.chunk_gen.OptimizedChunkGenerator;
import io.github.chaosawakens.common.worldgen.config.base.DimensionLevelStemConfig;
import io.github.chaosawakens.common.worldgen.config.mining_paradise.biome.MiningParadiseBiomeBuilder;
import io.github.chaosawakens.util.NoiseRouterUtil;
import io.github.chaosawakens.util.WorldGenUtil;
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
    public static final NoiseSettings BASE_NOISE_SETTINGS = NoiseSettings.create(-256, 736, 1, 4);

    public MiningParadiseDimensionConfig() {
    }

    @Override
    public @NotNull Supplier<ResourceKey<DimensionType>> getParentDimensionType() {
        return CADimensions.MINING_PARADISE_DIMENSION_TYPE;
    }

    @Override
    public @NotNull ChunkGenerator createLevelChunkGen(BootstapContext<LevelStem> regCtx) {
        HolderGetter<Biome> biomeLookup = regCtx.lookup(Registries.BIOME);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettingsLookup = regCtx.lookup(Registries.NOISE_SETTINGS);
        HolderGetter<MultiNoiseBiomeSourceParameterList> biomeSrcParamListLookup = regCtx.lookup(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST);

        BiomeSource src = MultiNoiseBiomeSource.createFromPreset(biomeSrcParamListLookup.getOrThrow(CAMultiNoiseBiomeSourceParameterLists.MINING_PARADISE_BIOME_LIST.get()));
        Holder.Reference<NoiseGeneratorSettings> settings = noiseGenSettingsLookup.getOrThrow(CANoiseGeneratorSettings.MINING_PARADISE.get());

        return new OptimizedChunkGenerator(src, settings);
    }

    public static DimensionType createDimensionType() {
        return new DimensionType(OptionalLong.empty(), true, false, false, true, 1.0D, true, false, -256, 736, 480, BlockTags.INFINIBURN_OVERWORLD, BuiltinDimensionTypes.OVERWORLD_EFFECTS, 0.0F, new DimensionType.MonsterSettings(false, false, ConstantInt.of(4), 0));
    }

    public static Supplier<NoiseGeneratorSettings> createMiningParadiseNoiseGenSettings(BootstapContext<NoiseGeneratorSettings> regCtx) {
        return () -> new NoiseGeneratorSettings(BASE_NOISE_SETTINGS, CABlocks.DREDGESTONE.get().defaultBlockState(), Blocks.WATER.defaultBlockState(), createMiningParadiseNoiseRouter(regCtx), createMiningParadiseSurfaceRules(), createMiningParadiseClimateSpawnConfiguration(regCtx), 121, false, true, true, false);
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
        SurfaceRules.RuleSource defaultSurfaceRuleSource = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.abovePreliminarySurface(),
                        SurfaceRules.ifTrue(
                                SurfaceRules.ON_FLOOR,
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(
                                                CASurfaceRules.CAConditionSources.AT_ABOVE_WATER_LEVEL,
                                                CASurfaceRules.CAStateRules.DENSE_GRASS_BLOCK
                                        ),
                                        CASurfaceRules.CAStateRules.DENSE_DIRT
                                )
                        )
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.UNDER_FLOOR,
                        SurfaceRules.ifTrue(
                                SurfaceRules.not(SurfaceRules.hole()),
                                CASurfaceRules.CAStateRules.DENSE_DIRT
                        )
                )
        );
        SurfaceRules.RuleSource denseMountainsSurfaceRuleSource = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.abovePreliminarySurface(),
                        SurfaceRules.ifTrue(
                                SurfaceRules.ON_FLOOR,
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(
                                                CASurfaceRules.CAConditionSources.AT_ABOVE_WATER_LEVEL,
                                                CASurfaceRules.CAStateRules.DENSE_DIRT
                                        ),
                                        CASurfaceRules.CAStateRules.DENSE_GRASS_BLOCK
                                )
                        )
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.UNDER_FLOOR,
                        SurfaceRules.ifTrue(
                                SurfaceRules.not(SurfaceRules.hole()),
                                CASurfaceRules.CAStateRules.DENSE_DIRT
                        )
                )
        );
        SurfaceRules.RuleSource mesozoicJungleSurfaceRuleSource = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.abovePreliminarySurface(),
                        SurfaceRules.ifTrue(
                                SurfaceRules.ON_FLOOR,
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(
                                                CASurfaceRules.CAConditionSources.AT_ABOVE_WATER_LEVEL,
                                                CASurfaceRules.CAStateRules.DENSE_GRASS_BLOCK
                                        ),
                                        CASurfaceRules.CAStateRules.DENSE_DIRT
                                )
                        )
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.UNDER_FLOOR,
                        SurfaceRules.ifTrue(
                                SurfaceRules.not(SurfaceRules.hole()),
                                CASurfaceRules.CAStateRules.DENSE_DIRT
                        )
                )
        );
        SurfaceRules.RuleSource densePlainsRuleSource = SurfaceRules.ifTrue(SurfaceRules.isBiome(CABiomes.DENSE_PLAINS.get()), defaultSurfaceRuleSource);
        SurfaceRules.RuleSource denseMountainsRuleSource = SurfaceRules.ifTrue(SurfaceRules.isBiome(CABiomes.DENSE_MOUNTAINS.get()), denseMountainsSurfaceRuleSource);
        SurfaceRules.RuleSource mesozoicJungleRuleSource = SurfaceRules.ifTrue(SurfaceRules.isBiome(CABiomes.MESOZOIC_JUNGLE.get()), mesozoicJungleSurfaceRuleSource);
        SurfaceRules.RuleSource bedrockFloorRuleSource = SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), CASurfaceRules.CAStateRules.BEDROCK);

        return SurfaceRules.sequence(densePlainsRuleSource, denseMountainsRuleSource, mesozoicJungleRuleSource, bedrockFloorRuleSource);
    }

    protected static NoiseRouter createMiningParadiseNoiseRouter(BootstapContext<NoiseGeneratorSettings> regCtx) {
        DensityFunction aquiferBarrier = DensityFunctions.noise(regCtx.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.AQUIFER_BARRIER.get()), (double)0.5F);
        DensityFunction fluidFloodedness = DensityFunctions.noise(regCtx.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.AQUIFER_FLUID_LEVEL_FLOODEDNESS.get()), 0.67);
        DensityFunction fluidSpread = DensityFunctions.noise(regCtx.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.AQUIFER_FLUID_LEVEL_SPREAD.get()), 0.715);
        DensityFunction aquiferLava = DensityFunctions.noise(regCtx.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.AQUIFER_LAVA.get()), 0.5);
        DensityFunction shiftX = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.SHIFT_X);
        DensityFunction shiftZ = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.SHIFT_Z);
        DensityFunction shiftedTemperature = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.5D, regCtx.lookup(Registries.NOISE).getOrThrow(Noises.TEMPERATURE));
        DensityFunction shiftedVegetation = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.5D, regCtx.lookup(Registries.NOISE).getOrThrow(Noises.VEGETATION));
        DensityFunction y = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.Y);
        DensityFunction[] oreFunctions = createOreDensityFunctions(y, regCtx.lookup(Registries.NOISE));
        DensityFunction zero = DensityFunctions.zero();
        DensityFunction landContinents = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.MINING_PARADISE_CONTINENTS);
        DensityFunction landErosion = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.MINING_PARADISE_EROSION);
        DensityFunction terrainJaggedness = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.MINING_PARADISE_JAGGEDNESS);
        DensityFunction terrainFactor = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.MINING_PARADISE_FACTOR);
        DensityFunction terrainDepth = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.MINING_PARADISE_DEPTH);
        DensityFunction continentRidges = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.MINING_PARADISE_RIDGES);
        DensityFunction initialLandDensity = NoiseRouterUtil.createTerrainSlide(
                DensityFunctions.add(WorldGenUtil.noiseGradientDensity(DensityFunctions.cache2d(terrainFactor), terrainDepth, 5.0F), DensityFunctions.constant(9.5D)).clamp(-66.0D, 64.0D),
                -256, 736, 20, 20, -1.0F, 4, 44, 1.5F);
        DensityFunction finalLandDensity = DensityFunctions.mul(DensityFunctions.interpolated(DensityFunctions.blendDensity(initialLandDensity)), DensityFunctions.constant(1.0D)).squeeze();

        return new NoiseRouter(
                aquiferBarrier,
                fluidFloodedness,
                fluidSpread,
                aquiferLava,
                shiftedTemperature,
                shiftedVegetation,
                landContinents,
                landErosion,
                terrainDepth,
                continentRidges,
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
    private static DensityFunction[] createOreDensityFunctions(DensityFunction y, HolderGetter<NormalNoise.NoiseParameters> noiseParams) {

        int minY = Stream.of(OreVeinifier.VeinType.values())
                .mapToInt(v -> v.minY)
                .min().orElse(-DimensionType.MIN_Y * 2);

        int maxY = Stream.of(OreVeinifier.VeinType.values())
                .mapToInt(v -> v.maxY)
                .max().orElse(-DimensionType.MIN_Y * 2);

        DensityFunction veininess = yLimitedInterpolatable(y, DensityFunctions.noise(noiseParams.getOrThrow(Noises.ORE_VEININESS), 1.5D, 1.5D), minY, maxY, 0);

        DensityFunction veinA = yLimitedInterpolatable(y, DensityFunctions.noise(noiseParams.getOrThrow(Noises.ORE_VEIN_A), 4.0D, 4.0D), minY, maxY, 0).abs();
        DensityFunction veinB = yLimitedInterpolatable(y, DensityFunctions.noise(noiseParams.getOrThrow(Noises.ORE_VEIN_B), 4.0D, 4.0D), minY, maxY, 0).abs();
        DensityFunction veinAB = DensityFunctions.add(DensityFunctions.constant(-0.08), DensityFunctions.max(veinA, veinB));

        DensityFunction oreGap = DensityFunctions.noise(noiseParams.getOrThrow(Noises.ORE_GAP));

        return new DensityFunction[]{veininess, veinAB, oreGap};
    }
}