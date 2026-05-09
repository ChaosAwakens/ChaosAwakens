package io.github.chaosawakens.content.registry;

import com.google.common.collect.ImmutableList;
import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.platform.NexusServices;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.util.WorldGenUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraft.world.level.levelgen.NoiseRouterData.splineWithBlending;

@RegistrarEntry
public class CADensityFunctions {
    private static final ObjectArrayList<Supplier<ResourceKey<DensityFunction>>> DENSITY_FUNCTIONS = new ObjectArrayList<>();

    // Vanilla
    public static final Supplier<ResourceKey<DensityFunction>> ZERO = registerDensityFunction("vanilla/zero", b -> DensityFunctions::zero);
    public static final Supplier<ResourceKey<DensityFunction>> Y = registerDensityFunction("vanilla/y", b -> () -> DensityFunctions.yClampedGradient(DimensionType.MIN_Y * 2, DimensionType.MAX_Y * 2, DimensionType.MIN_Y * 2, DimensionType.MAX_Y * 2));
    public static final Supplier<ResourceKey<DensityFunction>> MINING_PARADISE_NOODLE = registerDensityFunction("mining_paradise/land/noodle", b -> () ->
            DensityFunctions.rangeChoice(
                    DensityFunctions.rangeChoice(
                            getWrappedDensityFunctionHolder(b, Y),
                            -120,
                            120,
                            DensityFunctions.noise(
                                    b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.NOODLE.get()), 1.0D, 0.25D),
                            DensityFunctions.constant(-1)),
                    -1.0D,
                    0.0D,
                    DensityFunctions.constant(92.0D),
                    DensityFunctions.add(
                            DensityFunctions.rangeChoice(
                                    getWrappedDensityFunctionHolder(b, Y),
                                    -120,
                                    120,
                                    DensityFunctions.noise(
                                            b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.NOODLE_THICKNESS.get()), 1.0D, 1.00D),
                                    DensityFunctions.constant(1)),
                            DensityFunctions.mul(
                                    DensityFunctions.constant(1.5D),
                                    DensityFunctions.max(
                                            DensityFunctions.rangeChoice(
                                                    getWrappedDensityFunctionHolder(b, Y),
                                                    -120,
                                                    120,
                                                    DensityFunctions.noise(
                                                            b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.NOODLE_RIDGE_A.get()), 2.0D, 2.0D),
                                                    DensityFunctions.constant(0)
                                            ).abs(),
                                            DensityFunctions.rangeChoice(
                                                    getWrappedDensityFunctionHolder(b, Y),
                                                    -120,
                                                    120,
                                                    DensityFunctions.noise(
                                                            b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.NOODLE_RIDGE_B.get()), 0.5D, 0.75D),
                                                    DensityFunctions.constant(0)
                                            ).abs()
                                    ))
                    ))
    );
    public static final Supplier<ResourceKey<DensityFunction>> SHIFT_X = registerDensityFunction("vanilla/shift_x", b -> () -> DensityFunctions.flatCache(DensityFunctions.cache2d(DensityFunctions.shiftA(b.lookup(Registries.NOISE).getOrThrow(Noises.SHIFT)))));
    public static final Supplier<ResourceKey<DensityFunction>> SHIFT_Z = registerDensityFunction("vanilla/shift_z", b -> () -> DensityFunctions.flatCache(DensityFunctions.cache2d(DensityFunctions.shiftB(b.lookup(Registries.NOISE).getOrThrow(Noises.SHIFT)))));
    public static final Supplier<ResourceKey<DensityFunction>> CONTINENTS = registerDensityFunction("vanilla/land/continents", b -> () -> DensityFunctions.flatCache(DensityFunctions.shiftedNoise2d(getWrappedDensityFunctionHolder(b, SHIFT_X), getWrappedDensityFunctionHolder(b, SHIFT_Z), 0.25D, b.lookup(Registries.NOISE).getOrThrow(Noises.CONTINENTALNESS))));
    public static final Supplier<ResourceKey<DensityFunction>> EROSION = registerDensityFunction("vanilla/land/erosion", b -> () -> DensityFunctions.flatCache(DensityFunctions.shiftedNoise2d(getWrappedDensityFunctionHolder(b, SHIFT_X), getWrappedDensityFunctionHolder(b, SHIFT_Z), 0.25D, b.lookup(Registries.NOISE).getOrThrow(Noises.EROSION))));
    public static final Supplier<ResourceKey<DensityFunction>> RIDGES = registerDensityFunction("vanilla/land/ridges", b -> () -> DensityFunctions.flatCache(DensityFunctions.shiftedNoise2d(getWrappedDensityFunctionHolder(b, SHIFT_X), getWrappedDensityFunctionHolder(b, SHIFT_Z), 0.25D, b.lookup(Registries.NOISE).getOrThrow(Noises.RIDGE))));
    public static final Supplier<ResourceKey<DensityFunction>> RIDGES_FOLDED = registerDensityFunction("vanilla/land/ridges_folded", b -> () -> DensityFunctions.mul(DensityFunctions.add(DensityFunctions.add(getWrappedDensityFunctionHolder(b, RIDGES).abs(), DensityFunctions.constant(-2.0D / 3.0D)).abs(), DensityFunctions.constant(-1.0D / 3.0D)), DensityFunctions.constant(-3.0D)));
    // Mining Paradise
    public static final Supplier<ResourceKey<DensityFunction>> MINING_PARADISE_CONTINENTS = registerDensityFunction("mining_paradise/land/continents", b -> () ->
            DensityFunctions.min(DensityFunctions.constant(1.0D),
                    DensityFunctions.max(DensityFunctions.constant(-1.0D),
                            DensityFunctions.mul(DensityFunctions.constant(1.1D),
                                    DensityFunctions.add(DensityFunctions.constant(0.1D),
                                            DensityFunctions.flatCache(DensityFunctions.shiftedNoise2d(getWrappedDensityFunctionHolder(b, SHIFT_X),
                                                    getWrappedDensityFunctionHolder(b, SHIFT_Z), 0.3D, b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.MINING_PARADISE_CONTINENTALNESS.get()))))))));
    public static final Supplier<ResourceKey<DensityFunction>> MINING_PARADISE_RIDGES_FOLDED = registerDensityFunction("mining_paradise/land/ridges_folded", b -> () ->
            DensityFunctions.max(DensityFunctions.constant(1.5D),
                    DensityFunctions.min(DensityFunctions.constant(3.0D),
                            DensityFunctions.mul(DensityFunctions.mul(
                                            DensityFunctions.constant(3.0D),
                                            DensityFunctions.add(DensityFunctions.constant(1.0D),
                                                    getWrappedDensityFunctionHolder(b, MINING_PARADISE_CONTINENTS))),
                                    DensityFunctions.mul(DensityFunctions.constant(2),
                                            DensityFunctions.min(DensityFunctions.constant(0.5D),
                                                    DensityFunctions.max(DensityFunctions.constant(0.0D),
                                                            DensityFunctions.add(DensityFunctions.constant(1D),
                                                                    DensityFunctions.add(DensityFunctions.mul(DensityFunctions.constant(-1.0D),
                                                                                    getWrappedDensityFunctionHolder(b, MINING_PARADISE_CONTINENTS)),
                                                                            DensityFunctions.add(DensityFunctions.constant(-3.0D),
                                                                                    DensityFunctions.mul(DensityFunctions.constant(40.0D),
                                                                                            DensityFunctions.shiftedNoise2d(getWrappedDensityFunctionHolder(b, SHIFT_X),
                                                                                                    getWrappedDensityFunctionHolder(b, SHIFT_Z), 0.2D, b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.MINING_PARADISE_RIDGES.get()))).abs()))))))))));
    public static final Supplier<ResourceKey<DensityFunction>> MINING_PARADISE_EROSION = registerDensityFunction("mining_paradise/land/erosion", b -> () ->
            DensityFunctions.min(DensityFunctions.constant(0.25D),
                    DensityFunctions.flatCache(DensityFunctions.mul(DensityFunctions.constant(0.125D),
                            DensityFunctions.add(DensityFunctions.constant(1.0),
                                    DensityFunctions.shiftedNoise2d(getWrappedDensityFunctionHolder(b, SHIFT_X),
                                            getWrappedDensityFunctionHolder(b, SHIFT_Z), 0.65D, b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.MINING_PARADISE_EROSION.get())))))));
    public static final Supplier<ResourceKey<DensityFunction>> MINING_PARADISE_OFFSET = registerDensityFunction("mining_paradise/land/offset", b -> () -> DensityFunctions.flatCache(
            DensityFunctions.cache2d(DensityFunctions.lerp(DensityFunctions.blendAlpha(),
                    DensityFunctions.blendOffset(),
                    DensityFunctions.mul(DensityFunctions.constant(15.0D),
                            DensityFunctions.add(DensityFunctions.constant(-0.00365D),
                                    DensityFunctions.spline(WorldGenUtil.miningParadiseOffset(new DensityFunctions.Spline.Coordinate(getWrappedDensityFunction(b, MINING_PARADISE_CONTINENTS)), new DensityFunctions.Spline.Coordinate(getWrappedDensityFunction(b, MINING_PARADISE_EROSION)),
                                            new DensityFunctions.Spline.Coordinate(getWrappedDensityFunction(b, MINING_PARADISE_RIDGES_FOLDED))
                                    ))))))));
    public static final Supplier<ResourceKey<DensityFunction>> MINING_PARADISE_DEPTH = registerDensityFunction("mining_paradise/land/depth", b -> () -> DensityFunctions.add(
            DensityFunctions.yClampedGradient(-128, 384, 0.05D, -1D),
            getWrappedDensityFunctionHolder(b, MINING_PARADISE_OFFSET)));
    public static final Supplier<ResourceKey<DensityFunction>> MINING_PARADISE_RIDGES = registerDensityFunction("mining_paradise/land/ridges", b -> () ->
            DensityFunctions.mul(DensityFunctions.constant(0.75D),
                    DensityFunctions.flatCache(DensityFunctions.shiftedNoise2d(getWrappedDensityFunctionHolder(b, SHIFT_X),
                            getWrappedDensityFunctionHolder(b, SHIFT_Z), 0.5D, b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.MINING_PARADISE_RIDGES.get())))));
    public static final Supplier<ResourceKey<DensityFunction>> MINING_PARADISE_FACTOR = registerDensityFunction("mining_paradise/land/factor", b -> () -> splineWithBlending(
            DensityFunctions.spline(WorldGenUtil.miningParadiseFactor(
                    new DensityFunctions.Spline.Coordinate(getWrappedDensityFunction(b, MINING_PARADISE_CONTINENTS)),
                    new DensityFunctions.Spline.Coordinate(getWrappedDensityFunction(b, MINING_PARADISE_EROSION)),
                    new DensityFunctions.Spline.Coordinate(getWrappedDensityFunction(b, MINING_PARADISE_RIDGES)),
                    new DensityFunctions.Spline.Coordinate(getWrappedDensityFunction(b, MINING_PARADISE_RIDGES_FOLDED)))),
            DensityFunctions.constant(1.0D)));
    public static final Supplier<ResourceKey<DensityFunction>> MINING_PARADISE_JAGGEDNESS = registerDensityFunction("mining_paradise/land/jaggedness", b -> () ->
            DensityFunctions.mul(
                    DensityFunctions.constant(0.35), DensityFunctions.mul(
                            DensityFunctions.constant(0.4348), DensityFunctions.add(
                                    DensityFunctions.constant(1.3), DensityFunctions.noise(
                                            b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.MINING_PARADISE_JAGGEDNESS.get()),
                                            30.0D, 0.0D)
                            ))));

    //public static final Supplier<ResourceKey<DensityFunction>> MINING_PARADISE_PEAKS_VALLEYS = registerDensityFunction(RIDGES_FOLDED, peaksAndValleys($$9));
    public static final Supplier<ResourceKey<DensityFunction>> MINING_PARADISE_SLOPED_CHEESE = registerDensityFunction("mining_paradise/land/sloped_cheese", b -> () ->
            DensityFunctions.mul(
                    DensityFunctions.constant(4.0D),
                    DensityFunctions.mul(
                            getWrappedDensityFunctionHolder(b, MINING_PARADISE_FACTOR),
                            DensityFunctions.add(
                                    getWrappedDensityFunctionHolder(b, MINING_PARADISE_DEPTH),
                                    DensityFunctions.mul(
                                            getWrappedDensityFunctionHolder(b, MINING_PARADISE_JAGGEDNESS),
                                            getWrappedDensityFunctionHolder(b, MINING_PARADISE_JAGGEDNESS).halfNegative()
                                    )))));
    public static final Supplier<ResourceKey<DensityFunction>> MINING_PARADISE_SPAGHETTI_ROUGHNESS_MODULATOR = registerDensityFunction("mining_paradise/land/spaghetti_roughness_modulator", b -> () ->
            DensityFunctions.mappedNoise(
                    b.lookup(Registries.NOISE).getOrThrow(
                            CANoiseParameters.SPAGHETTI_ROUGHNESS_MODULATOR.get()),
                    -0.006D, 0.005D));
    public static final Supplier<ResourceKey<DensityFunction>> MINING_PARADISE_SPAGHETTI_ROUGHNESS = registerDensityFunction("mining_paradise/land/spaghetti_roughness", b -> () ->
            DensityFunctions.noise(b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.SPAGHETTI_ROUGHNESS.get())).abs());
    public static final Supplier<ResourceKey<DensityFunction>> MINING_PARADISE_SPAGHETTI_ROUGHNESS_FUNCTION = registerDensityFunction("mining_paradise/land/spaghetti_roughness_function", b -> () ->
            DensityFunctions.cacheOnce(
                    DensityFunctions.mul(
                            DensityFunctions.add(
                                    DensityFunctions.constant(-0.05),
                                    DensityFunctions.mul(
                                            DensityFunctions.constant(-0.05),
                                            getWrappedDensityFunctionHolder(
                                                    b, MINING_PARADISE_SPAGHETTI_ROUGHNESS_MODULATOR))),
                            DensityFunctions.add(
                                    DensityFunctions.constant(-0.4),
                                    getWrappedDensityFunctionHolder(
                                            b, MINING_PARADISE_SPAGHETTI_ROUGHNESS)
                            ))));
    public static final Supplier<ResourceKey<DensityFunction>> MINING_PARADISE_ENTRANCES = registerDensityFunction("mining_paradise/land/entrances", b -> () ->
            DensityFunctions.cacheOnce(DensityFunctions.min(
                    DensityFunctions.add(DensityFunctions.add(
                                    DensityFunctions.noise(b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.CAVE_ENTRANCE.get()), 0.75D, 0.5D),
                                    DensityFunctions.constant(0.37D)),
                            DensityFunctions.yClampedGradient(-10, 30, 0.3D, 0.0D)),
                    DensityFunctions.add(getWrappedDensityFunctionHolder(b, MINING_PARADISE_SPAGHETTI_ROUGHNESS_FUNCTION),
                            DensityFunctions.add(DensityFunctions.max(
                                                    DensityFunctions.weirdScaledSampler(DensityFunctions.cacheOnce(
                                                                    DensityFunctions.noise(b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.SPAGHETTI_3D_RARITY.get()), 2.0D, 1.0D)),
                                                            b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.SPAGHETTI_3D_1.get()), DensityFunctions.WeirdScaledSampler.RarityValueMapper.TYPE1),
                                                    DensityFunctions.weirdScaledSampler(DensityFunctions.cacheOnce(
                                                                    DensityFunctions.noise(b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.SPAGHETTI_3D_RARITY.get()), 2.0D, 1.0D)),
                                                            b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.SPAGHETTI_3D_2.get()), DensityFunctions.WeirdScaledSampler.RarityValueMapper.TYPE1)),
                                            DensityFunctions.mappedNoise(
                                                    b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.SPAGHETTI_3D_THICKNESS.get()), -0.1D, -0.06D))
                                    .clamp(-1.0D, 1.0D)
                    ))));
    public static final Supplier<ResourceKey<DensityFunction>> MINING_PARADISE_SPAGHETTI_2D_THICKNESS = registerDensityFunction("mining_paradise/land/spaghetti_2d_thickness", b -> () ->
            DensityFunctions.cacheOnce(DensityFunctions.mappedNoise(b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.SPAGHETTI_2D_THICKNESS.get()), 2.0D, 1.0D, 0.35D, -0.95D)));
    public static final Supplier<ResourceKey<DensityFunction>> MINING_PARADISE_SPAGHETTI_2D = registerDensityFunction("mining_paradise/land/spaghetti_2d", b -> () ->
            DensityFunctions.max(
                            DensityFunctions.add(
                                    DensityFunctions.weirdScaledSampler(
                                            DensityFunctions.mappedNoise(
                                                    b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.SPAGHETTI_ROUGHNESS_MODULATOR.get()), 1.0D, 1.0D),
                                            b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.SPAGHETTI_2D.get()),
                                            DensityFunctions.WeirdScaledSampler.RarityValueMapper.TYPE2),
                                    DensityFunctions.mul(
                                            DensityFunctions.constant(1.0D),
                                            getWrappedDensityFunctionHolder(b, MINING_PARADISE_SPAGHETTI_2D_THICKNESS))),
                            DensityFunctions.add(
                                    DensityFunctions.add(
                                            DensityFunctions.add(
                                                    DensityFunctions.constant(-20),
                                                    DensityFunctions.mul(
                                                            DensityFunctions.constant(6),
                                                            DensityFunctions.mappedNoise(
                                                                    b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.SPAGHETTI_2D_ELEVATION.get()), 2.0D, 1.0D))),
                                            DensityFunctions.yClampedGradient(-128, 384, 17.0D, -15.0D)).abs(),
                                    getWrappedDensityFunctionHolder(b, MINING_PARADISE_SPAGHETTI_2D_THICKNESS)).cube())
                    .clamp(-1.0D, 1.0D));
    public static final Supplier<ResourceKey<DensityFunction>> MINING_PARADISE_PILLARS = registerDensityFunction("mining_paradise/land/pillars", b -> () ->
            DensityFunctions.cacheOnce(DensityFunctions.mul(
                    DensityFunctions.add(
                            DensityFunctions.mul(DensityFunctions.constant(1.75D),
                                    DensityFunctions.noise(
                                            b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.PILLAR.get()),
                                            5.0D, 0.1D)
                            ),
                            DensityFunctions.mappedNoise(
                                    b.lookup(Registries.NOISE).getOrThrow(CANoiseParameters.PILLAR_RARENESS.get()),
                                    -1.0D, -1.0D)
                    ),
                    DensityFunctions.add(DensityFunctions.mul(DensityFunctions.constant(0.7D).square(), DensityFunctions.constant(0.85D)), DensityFunctions.constant(0.75D)).cube()
            )));

    private static Supplier<ResourceKey<DensityFunction>> registerDensityFunction(ResourceLocation id, Function<BootstapContext<DensityFunction>, Supplier<DensityFunction>> dfFunc) {
        Supplier<ResourceKey<DensityFunction>> densityFunctionSup = NexusServices.REGISTRAR.registerDatapackObject(id, dfFunc, Registries.DENSITY_FUNCTION);
        DENSITY_FUNCTIONS.add(densityFunctionSup);
        return densityFunctionSup;
    }

    private static Supplier<ResourceKey<DensityFunction>> registerDensityFunction(String id, Function<BootstapContext<DensityFunction>, Supplier<DensityFunction>> dfFunc) {
        return registerDensityFunction(CAConstants.prefix(id), dfFunc);
    }

    public static DensityFunctions.HolderHolder getWrappedDensityFunctionHolder(BootstapContext<?> regCtx, Supplier<ResourceKey<DensityFunction>> targetDensityFunction) {
        return new DensityFunctions.HolderHolder(regCtx.lookup(Registries.DENSITY_FUNCTION).getOrThrow(targetDensityFunction.get()));
    }

    public static Holder<DensityFunction> getWrappedDensityFunction(BootstapContext<?> regCtx, Supplier<ResourceKey<DensityFunction>> targetDensityFunction) {
        return getWrappedDensityFunctionHolder(regCtx, targetDensityFunction).function();
    }

    public static DensityFunction noise(Holder<NormalNoise.NoiseParameters> noiseData) {
        return noise(noiseData, 1.0F, 1.0F);
    }

    public static DensityFunction noise(Holder<NormalNoise.NoiseParameters> noiseData, double xzScale, double yScale) {
        return new DensityFunctions.Noise(new DensityFunction.NoiseHolder(noiseData), xzScale, yScale);
    }

    public static DensityFunction noise(Holder<NormalNoise.NoiseParameters> noiseData, double yScale) {
        return noise(noiseData, 1.0F, yScale);
    }

    public static ImmutableList<Supplier<ResourceKey<DensityFunction>>> getDensityFunctions() {
        return ImmutableList.copyOf(DENSITY_FUNCTIONS);
    }
}