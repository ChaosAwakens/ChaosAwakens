package io.github.chaosawakens.common.worldgen.config.mining_paradise.biome;

import com.mojang.datafixers.util.Pair;
import io.github.chaosawakens.common.registry.CABiomes;
import io.github.chaosawakens.common.worldgen.config.base.BiomeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import java.util.function.Consumer;

public class MiningParadiseBiomeBuilder implements BiomeBuilder {

    public MiningParadiseBiomeBuilder() { // Same per-object pattern Vanilla uses, JIC

    }

    @Override
    public void mapBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomeClimateParameterPointMapper) {
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(0.2F, 1.2F), Climate.Parameter.span(0.11F, 0.5F), Climate.Parameter.span(0.2F, 0.85F), Climate.Parameter.span(0.1F, 0.9F), Climate.Parameter.point(0.0F), 0, CABiomes.DENSE_PLAINS.get());
    }
}
