package io.github.chaosawakens.content.registry;

import com.google.common.collect.ImmutableList;
import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.platform.NexusServices;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.content.worldgen.config.crystal_world.CrystalWorldDimensionConfig;
import io.github.chaosawakens.content.worldgen.config.mining_paradise.MiningParadiseDimensionConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.function.Function;
import java.util.function.Supplier;

@RegistrarEntry
public class CANoiseGeneratorSettings {
    private static final ObjectArrayList<Supplier<ResourceKey<NoiseGeneratorSettings>>> NOISE_GENERATOR_SETTINGS = new ObjectArrayList<>();

    public static final Supplier<ResourceKey<NoiseGeneratorSettings>> CRYSTAL_WORLD = registerNoiseGeneratorSetting("crystal_world", CrystalWorldDimensionConfig::createCrystalNoiseGenSettings);
    public static final Supplier<ResourceKey<NoiseGeneratorSettings>> MINING_PARADISE = registerNoiseGeneratorSetting("mining_paradise", MiningParadiseDimensionConfig::createMiningParadiseNoiseGenSettings);

    private static Supplier<ResourceKey<NoiseGeneratorSettings>> registerNoiseGeneratorSetting(ResourceLocation id, Function<BootstapContext<NoiseGeneratorSettings>, Supplier<NoiseGeneratorSettings>> ngsConfigFunc) {
        Supplier<ResourceKey<NoiseGeneratorSettings>> ngsSup = NexusServices.REGISTRAR.registerDatapackObject(id, ngsConfigFunc, Registries.NOISE_SETTINGS);
        NOISE_GENERATOR_SETTINGS.add(ngsSup);
        return ngsSup;
    }

    private static Supplier<ResourceKey<NoiseGeneratorSettings>> registerNoiseGeneratorSetting(String id, Function<BootstapContext<NoiseGeneratorSettings>, Supplier<NoiseGeneratorSettings>> ngsConfigFunc) {
        return registerNoiseGeneratorSetting(CAConstants.prefix(id), ngsConfigFunc);
    }

    public static ImmutableList<Supplier<ResourceKey<NoiseGeneratorSettings>>> getNoiseGeneratorSettings() {
        return ImmutableList.copyOf(NOISE_GENERATOR_SETTINGS);
    }
}