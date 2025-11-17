package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableList;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.asm.annotations.RegistrarEntry;
import io.github.chaosawakens.api.platform.CAServices;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.function.Supplier;

@RegistrarEntry
public class CANoiseParameters {
    private static final ObjectArrayList<Supplier<ResourceKey<NormalNoise.NoiseParameters>>> NOISE_PARAMETERS = new ObjectArrayList<>();

    // Mining Paradise
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> MINING_PARADISE_CONTINENTALNESS = registerNoiseParameter("mining_paradise/continentalness", () -> new NormalNoise.NoiseParameters(-10, 0.5D, 2.0D, 1.0D, 1.0D, 2.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> MINING_PARADISE_EROSION = registerNoiseParameter("mining_paradise/erosion", () -> new NormalNoise.NoiseParameters(-10, 1.0D, 1.0D, 0.0D, 1.0D, 1.0D));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> MINING_PARADISE_RIDGES = registerNoiseParameter("mining_paradise/ridges", () -> new NormalNoise.NoiseParameters(-11, 2.0D, 2.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D));


    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> AQUIFER_BARRIER = registerNoiseParameter("mining_paradise/aquifer_barrier", () -> new NormalNoise.NoiseParameters(-3, 1.0D));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> AQUIFER_FLUID_LEVEL_FLOODEDNESS = registerNoiseParameter("mining_paradise/aquifer_fluid_level_floodedness", () -> new NormalNoise.NoiseParameters(-7, 1.0D));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> AQUIFER_LAVA = registerNoiseParameter("mining_paradise/aquifer_lava", () -> new NormalNoise.NoiseParameters(-1, 1.0D));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> AQUIFER_FLUID_LEVEL_SPREAD = registerNoiseParameter("mining_paradise/aquifer_fluid_level_spread", () -> new NormalNoise.NoiseParameters(-5, 1.0D));

    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> CAVE = registerNoiseParameter("mining_paradise/cave", () -> new NormalNoise.NoiseParameters(-8, 1.0, 1.0, 1.0, 1.0));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> CAVE_LAYER = registerNoiseParameter("mining_paradise/cave_layer", () -> new NormalNoise.NoiseParameters(-8, 1.0D));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> CAVE_CHEESE = registerNoiseParameter("mining_paradise/cave_cheese", () -> new NormalNoise.NoiseParameters(-8, 1.0, 1.0, 2.0, 1.0, 2.0, 1.0, 0.0, 2.0, 1.0));

    private static Supplier<ResourceKey<NormalNoise.NoiseParameters>> registerNoiseParameter(ResourceLocation id, Supplier<NormalNoise.NoiseParameters> actualNoiseParamSup) {
        Supplier<ResourceKey<NormalNoise.NoiseParameters>> noiseParamSup = CAServices.REGISTRAR.registerDatapackObject(id, b -> actualNoiseParamSup, Registries.NOISE);
        NOISE_PARAMETERS.add(noiseParamSup);
        return noiseParamSup;
    }

    private static Supplier<ResourceKey<NormalNoise.NoiseParameters>> registerNoiseParameter(String id, Supplier<NormalNoise.NoiseParameters> actualNoiseParamSup) {
        return registerNoiseParameter(CAConstants.prefix(id), actualNoiseParamSup);
    }

    public static ImmutableList<Supplier<ResourceKey<NormalNoise.NoiseParameters>>> getNoiseParameters() {
        return ImmutableList.copyOf(NOISE_PARAMETERS);
    }
}
