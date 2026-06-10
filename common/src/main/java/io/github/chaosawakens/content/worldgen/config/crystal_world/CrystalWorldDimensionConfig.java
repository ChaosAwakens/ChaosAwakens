package io.github.chaosawakens.content.worldgen.config.crystal_world;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import io.github.chaosawakens.content.registry.*;
import io.github.chaosawakens.content.worldgen.chunk_gen.chunk.OptimizedNoiseBasedChunkGenerator;
import io.github.chaosawakens.content.worldgen.config.base.DimensionLevelStemConfig;
import io.github.chaosawakens.content.worldgen.config.crystal_world.biome.CrystalWorldBiomeBuilder;
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

public class CrystalWorldDimensionConfig implements DimensionLevelStemConfig {
    public static final NoiseSettings BASE_NOISE_SETTINGS = NoiseSettings.create(-96, 448, 4, 4);

    public CrystalWorldDimensionConfig() {
    }

    public static DimensionType createDimensionType() {
        return new DimensionType(OptionalLong.empty(), true, true, false, true, 1.0D, true, false, -96, 448, 352, BlockTags.INFINIBURN_OVERWORLD, BuiltinDimensionTypes.OVERWORLD_EFFECTS, 0.15F, new DimensionType.MonsterSettings(false, false, ConstantInt.of(4), 0));
    }

    public static Supplier<NoiseGeneratorSettings> createCrystalNoiseGenSettings(BootstapContext<NoiseGeneratorSettings> regCtx) {
        return () -> new NoiseGeneratorSettings(
                BASE_NOISE_SETTINGS,
                CABlocks.KYANITE.get().defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                createCrystalNoiseRouter(regCtx),
                createCrystalSurfaceRules(),
                createCrystalClimateSpawnConfiguration(regCtx),
                48,  // sea level
                false,  // disableMobGeneration
                false,   // aquifersEnabled
                false,   // oreVeinsEnabled
                false   // useLegacyRandomSource
        );
    }

    public static Supplier<MultiNoiseBiomeSourceParameterList> createCrystalNoiseBiomes(BootstapContext<MultiNoiseBiomeSourceParameterList> regCtx) {
        return () -> new MultiNoiseBiomeSourceParameterList(CAMultiNoiseBiomeSourceParameterLists.Presets.CRYSTAL_WORLD_BIOME_SOURCE_PRESET.get(), regCtx.lookup(Registries.BIOME));
    }

    public static <T> Climate.ParameterList<T> generateCrystalWorldBiomes(Function<ResourceKey<Biome>, T> biomeValueMapper) {
        ImmutableList.Builder<Pair<Climate.ParameterPoint, T>> mappedClimateParameterPointList = ImmutableList.builder();

        new CrystalWorldBiomeBuilder().mapBiomes(mappedBiome -> mappedClimateParameterPointList.add(mappedBiome.mapSecond(biomeValueMapper)));

        return new Climate.ParameterList<>(mappedClimateParameterPointList.build());
    }

    public static SurfaceRules.RuleSource createCrystalSurfaceRules() {
        // Order is important here: bottom to top
        return SurfaceRules.sequence(CASurfaceRules.ADD_BEDROCK_LAYER, CASurfaceRules.ADD_CRYSTAL_GRASS_BLOCK_TOP);
    }

    protected static NoiseRouter createCrystalNoiseRouter(BootstapContext<NoiseGeneratorSettings> regCtx) {
        DensityFunction shiftX = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.SHIFT_X);
        DensityFunction shiftZ = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.SHIFT_Z);
        DensityFunction shiftedTemperature = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.5D, regCtx.lookup(Registries.NOISE).getOrThrow(Noises.TEMPERATURE));
        DensityFunction shiftedVegetation = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.5D, regCtx.lookup(Registries.NOISE).getOrThrow(Noises.VEGETATION));
        DensityFunction landContinents = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.CRYSTAL_CONTINENTS);
        DensityFunction landErosion = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.CRYSTAL_EROSION);
        DensityFunction terrainWeirdness = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.CRYSTAL_RIDGES_FOLDED);
        DensityFunction terrainDepth = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.CRYSTAL_DEPTH);
        DensityFunction slopedCheese = CADensityFunctions.getWrappedDensityFunctionHolder(regCtx, CADensityFunctions.CRYSTAL_SLOPED_CHEESE);

        DensityFunction zero = DensityFunctions.zero();

        DensityFunction initialLandDensity =  DensityFunctions.add(
                DensityFunctions.constant(1.0F), DensityFunctions.mul(
                        DensityFunctions.yClampedGradient(
                                -96, 16, -1D, 1D),
                        DensityFunctions.add(
                                DensityFunctions.constant(-1.0F), DensityFunctions.mul(
                                        DensityFunctions.yClampedGradient(
                                                322, 352, 1D, 0D),
                                        DensityFunctions.add(
                                                DensityFunctions.constant(-1.0F), DensityFunctions.mul(
                                                        DensityFunctions.yClampedGradient(
                                                                0, 192, 0D, 1D),
                                                        DensityFunctions.add(
                                                                DensityFunctions.constant(1F), DensityFunctions.add(
                                                                        DensityFunctions.constant(-1F), DensityFunctions.mul(
                                                                                DensityFunctions.yClampedGradient(
                                                                                        96, 298, 1D, 0D),
                                                                                DensityFunctions.mul(
                                                                                        DensityFunctions.yClampedGradient(
                                                                                                132, 298, 1D, -1D),
                                                                                        DensityFunctions.add(
                                                                                                DensityFunctions.constant(1.75F), slopedCheese
                                                                                        ))
                                                                        ))
                                                        ))
                                        ))
                        ))
        );

        DensityFunction finalLandDensity = DensityFunctions.mul(
                DensityFunctions.constant(0.64),
                DensityFunctions.interpolated(DensityFunctions.blendDensity(initialLandDensity))
        ).squeeze();

        return new NoiseRouter(
                zero,
                zero,
                zero,
                zero,
                shiftedTemperature,
                shiftedVegetation,
                landContinents,
                landErosion,
                terrainDepth,
                terrainWeirdness,
                initialLandDensity,
                finalLandDensity,
                zero, // veininess
                zero, // vein a/b
                zero // ore gap
        );
    }

    protected static ObjectArrayList<Climate.ParameterPoint> createCrystalClimateSpawnConfiguration(BootstapContext<NoiseGeneratorSettings> regCtx) {
        return ObjectArrayList.of();
    }

    private static DensityFunction[] createOreDensityFunctions(DensityFunction yFunc, HolderGetter<NormalNoise.NoiseParameters> noiseParams) {
        return new DensityFunction[0];
    }

    @Override
    public @NotNull Supplier<ResourceKey<DimensionType>> getParentDimensionType() {
        return CADimensions.CRYSTAL_DIMENSION_TYPE;
    }

    @Override
    public @NotNull ChunkGenerator createLevelChunkGen(BootstapContext<LevelStem> regCtx) {
        HolderGetter<NoiseGeneratorSettings> noiseGenSettingsLookup = regCtx.lookup(Registries.NOISE_SETTINGS);
        HolderGetter<MultiNoiseBiomeSourceParameterList> biomeSrcParamListLookup = regCtx.lookup(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST);

        BiomeSource src = MultiNoiseBiomeSource.createFromPreset(biomeSrcParamListLookup.getOrThrow(CAMultiNoiseBiomeSourceParameterLists.CRYSTAL_WORLD_BIOME_LIST.get()));
        Holder.Reference<NoiseGeneratorSettings> settings = noiseGenSettingsLookup.getOrThrow(CANoiseGeneratorSettings.CRYSTAL_WORLD.get());

        return new OptimizedNoiseBasedChunkGenerator(src, settings);
    }
}