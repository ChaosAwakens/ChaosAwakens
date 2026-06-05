package io.github.chaosawakens.content.registry;

import com.google.common.collect.ImmutableList;
import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.platform.NexusServices;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.content.worldgen.config.base.BiomeConfig;
import io.github.chaosawakens.content.worldgen.config.crystal_world.biome.crystalwood_forest.CrystalwoodForestConfig;
import io.github.chaosawakens.content.worldgen.config.crystal_world.biome.crystalline_reef.CrystallineReefConfig;
import io.github.chaosawakens.content.worldgen.config.crystal_world.biome.kyanite_plains.KyanitePlainsConfig;
import io.github.chaosawakens.content.worldgen.config.mining_paradise.biome.layer_0.StalagmiteValleyConfig;
import io.github.chaosawakens.content.worldgen.config.mining_paradise.biome.layer_1.GinkgoForestConfig;
import io.github.chaosawakens.content.worldgen.config.mining_paradise.biome.layer_1.MesozoicJungleConfig;
import io.github.chaosawakens.content.worldgen.config.mining_paradise.biome.layer_2.DensePlainsConfig;
import io.github.chaosawakens.content.worldgen.config.mining_paradise.biome.layer_2.DensewoodForestConfig;
import io.github.chaosawakens.content.worldgen.config.mining_paradise.biome.layer_4.DenseMountainsConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.function.Supplier;

@RegistrarEntry
public class CABiomes {
    private static final ObjectArrayList<Supplier<ResourceKey<Biome>>> BIOMES = new ObjectArrayList<>();

    // Crystal World
    public static final Supplier<ResourceKey<Biome>> CRYSTALLINE_REEF = registerBiome("crystalline_reef", CrystallineReefConfig::new);
    public static final Supplier<ResourceKey<Biome>> CRYSTALWOOD_FOREST  = registerBiome("crystalwood_forest", CrystalwoodForestConfig::new);
    public static final Supplier<ResourceKey<Biome>> KYANITE_PLAINS = registerBiome("kyanite_plains", KyanitePlainsConfig::new);

    // Mining Paradise
    public static final Supplier<ResourceKey<Biome>> DENSE_MOUNTAINS = registerBiome("dense_mountains", DenseMountainsConfig::new);
    public static final Supplier<ResourceKey<Biome>> DENSE_PLAINS = registerBiome("dense_plains", DensePlainsConfig::new);

    public static final Supplier<ResourceKey<Biome>> MESOZOIC_JUNGLE = registerBiome("mesozoic_jungle", MesozoicJungleConfig::new);
    public static final Supplier<ResourceKey<Biome>> STALAGMITE_VALLEY = registerBiome("stalagmite_valley", StalagmiteValleyConfig::new);

    public static final Supplier<ResourceKey<Biome>> DENSEWOOD_FOREST = registerBiome("densewood_forest", DensewoodForestConfig::new);
    public static final Supplier<ResourceKey<Biome>> GINKGO_FOREST = registerBiome("ginkgo_forest", GinkgoForestConfig::new);

    private static Supplier<ResourceKey<Biome>> registerBiome(ResourceLocation id, Supplier<BiomeConfig> biomeConfigSup) {
        Supplier<ResourceKey<Biome>> biomeSup = NexusServices.REGISTRAR.registerDatapackObject(id, b -> () -> biomeConfigSup.get().createBiome(b), Registries.BIOME);
        BIOMES.add(biomeSup);
        return biomeSup;
    }

    private static Supplier<ResourceKey<Biome>> registerBiome(String id, Supplier<BiomeConfig> biomeConfigSup) {
        return registerBiome(CAConstants.prefix(id), biomeConfigSup);
    }

    public static ImmutableList<Supplier<ResourceKey<Biome>>> getBiomes() {
        return ImmutableList.copyOf(BIOMES);
    }
}