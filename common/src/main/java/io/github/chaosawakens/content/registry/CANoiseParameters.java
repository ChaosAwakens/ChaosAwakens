package io.github.chaosawakens.content.registry;

import com.google.common.collect.ImmutableList;
import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.platform.NexusServices;
import io.github.chaosawakens.CAConstants;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.function.Supplier;

@RegistrarEntry
public class CANoiseParameters {
    private static final ObjectArrayList<Supplier<ResourceKey<NormalNoise.NoiseParameters>>> NOISE_PARAMETERS = new ObjectArrayList<>();

    // Mining Paradise
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> MINING_PARADISE_CONTINENTALNESS = registerNoiseParameter("mining_paradise/continentalness", () -> new NormalNoise.NoiseParameters(-10, 1.0D, 1.0D, 2.0D, 2.0D, 2.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> MINING_PARADISE_EROSION = registerNoiseParameter("mining_paradise/erosion", () -> new NormalNoise.NoiseParameters(-8, 1.0D, 0.0D, 1.0D, 1.0D));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> MINING_PARADISE_RIDGES = registerNoiseParameter("mining_paradise/ridges", () -> new NormalNoise.NoiseParameters(-8, 1.5D, 1.0D, 0.5D, 0.5D, 0.5D, 0.5D, 0.5D));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> MINING_PARADISE_JAGGEDNESS = registerNoiseParameter("mining_paradise/jaggedness", () -> new NormalNoise.NoiseParameters(-12,     1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0));

    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> AQUIFER_BARRIER = registerNoiseParameter("mining_paradise/aquifer_barrier", () -> new NormalNoise.NoiseParameters(-3, 1.0D));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> AQUIFER_FLUID_LEVEL_FLOODEDNESS = registerNoiseParameter("mining_paradise/aquifer_fluid_level_floodedness", () -> new NormalNoise.NoiseParameters(-4, 1.0D));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> AQUIFER_LAVA = registerNoiseParameter("mining_paradise/aquifer_lava", () -> new NormalNoise.NoiseParameters(-1, 1.0D));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> AQUIFER_FLUID_LEVEL_SPREAD = registerNoiseParameter("mining_paradise/aquifer_fluid_level_spread", () -> new NormalNoise.NoiseParameters(-3, 1.0D));

    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> CAVE_ENTRANCE = registerNoiseParameter("mining_paradise/cave", () -> new NormalNoise.NoiseParameters(-7, 0.4, 0.5, 1.0));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> CAVE_LAYER = registerNoiseParameter("mining_paradise/cave_layer", () -> new NormalNoise.NoiseParameters(-8, 1.0D));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> CAVE_CHEESE = registerNoiseParameter("mining_paradise/cave_cheese", () -> new NormalNoise.NoiseParameters(-8, 1.0, 1.0, 2.0, 1.0, 2.0, 1.0, 0.0, 2.0, 1.0));

    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> NOODLE = registerNoiseParameter("mining_paradise/noodle", () -> new NormalNoise.NoiseParameters(-8, 1.0));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> NOODLE_RIDGE_A = registerNoiseParameter("mining_paradise/noodle_ridge_a", () -> new NormalNoise.NoiseParameters(-7, 1.0));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> NOODLE_RIDGE_B = registerNoiseParameter("mining_paradise/noodle_ridge_b", () -> new NormalNoise.NoiseParameters(-7, 1.0));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> NOODLE_THICKNESS = registerNoiseParameter("mining_paradise/noodle_thickness", () -> new NormalNoise.NoiseParameters(-8, 1.0));

    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> PILLAR = registerNoiseParameter("mining_paradise/pillar", () -> new NormalNoise.NoiseParameters(-6, 1.0, 1.0));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> PILLAR_RARENESS = registerNoiseParameter("mining_paradise/pillar_rareness", () -> new NormalNoise.NoiseParameters(-7, 1.0));

    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> SPAGHETTI_2D = registerNoiseParameter("mining_paradise/spaghetti_2d", () -> new NormalNoise.NoiseParameters(-7, 1.0));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> SPAGHETTI_2D_ELEVATION = registerNoiseParameter("mining_paradise/spaghetti_2d_elevation", () -> new NormalNoise.NoiseParameters(-8, 1.0));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> SPAGHETTI_2D_MODULATOR = registerNoiseParameter("mining_paradise/spaghetti_2d_modulator", () -> new NormalNoise.NoiseParameters(-11, 1.0));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> SPAGHETTI_2D_THICKNESS = registerNoiseParameter("mining_paradise/spaghetti_2d_thickness", () -> new NormalNoise.NoiseParameters(-11, 1.0));

    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> SPAGHETTI_3D_1 = registerNoiseParameter("mining_paradise/spaghetti_3d_1", () -> new NormalNoise.NoiseParameters(-7, 1.0));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> SPAGHETTI_3D_2 = registerNoiseParameter("mining_paradise/spaghetti_3d_2", () -> new NormalNoise.NoiseParameters(-7, 1.0));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> SPAGHETTI_3D_RARITY = registerNoiseParameter("mining_paradise/spaghetti_3d_rarity", () -> new NormalNoise.NoiseParameters(-11, 1.0));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> SPAGHETTI_3D_THICKNESS= registerNoiseParameter("mining_paradise/spaghetti_3d_thickness", () -> new NormalNoise.NoiseParameters(-8, 1.0));

    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> SPAGHETTI_ROUGHNESS = registerNoiseParameter("mining_paradise/spaghetti_roughness", () -> new NormalNoise.NoiseParameters(-5, 1.0));
    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> SPAGHETTI_ROUGHNESS_MODULATOR = registerNoiseParameter("mining_paradise/spaghetti_roughness_modulator", () -> new NormalNoise.NoiseParameters(-8, 1.0));

    public static final Supplier<ResourceKey<NormalNoise.NoiseParameters>> SURFACE = registerNoiseParameter("mining_paradise/surface", () -> new NormalNoise.NoiseParameters(-6, 1.0, 1.0, 1.0));

    private static Supplier<ResourceKey<NormalNoise.NoiseParameters>> registerNoiseParameter(ResourceLocation id, Supplier<NormalNoise.NoiseParameters> actualNoiseParamSup) {
        Supplier<ResourceKey<NormalNoise.NoiseParameters>> noiseParamSup = NexusServices.REGISTRAR.registerDatapackObject(id, b -> actualNoiseParamSup, Registries.NOISE);
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