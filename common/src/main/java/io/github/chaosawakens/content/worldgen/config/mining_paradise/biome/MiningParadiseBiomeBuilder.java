package io.github.chaosawakens.content.worldgen.config.mining_paradise.biome;

import com.mojang.datafixers.util.Pair;
import io.github.chaosawakens.content.registry.CABiomes;
import io.github.chaosawakens.content.worldgen.config.base.BiomeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import java.util.function.Consumer;

public class MiningParadiseBiomeBuilder implements BiomeBuilder {
    public static final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.1F, 1.1F);
    public static final Climate.Parameter ZERO_POINT = Climate.Parameter.point(1.5F);
    public static final Climate.Parameter UPPER_POINT = Climate.Parameter.point(3.0F);
    public static final Climate.Parameter LAYER_1 = Climate.Parameter.span(-1.1F, 0.15F);
    public static final Climate.Parameter LAYER_2 = Climate.Parameter.span(0.16F, 0.5F);
    public static final Climate.Parameter LAYER_34 = Climate.Parameter.span(0.501F, 1.1F);

    public MiningParadiseBiomeBuilder() { // Same per-object pattern Vanilla uses, JIC

    }

    @Override
    public void mapBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomeClimateParameterPointMapper) {
        // Layer 3/4
        addSurfaceBiome(biomeClimateParameterPointMapper, FULL_RANGE, FULL_RANGE, LAYER_34, FULL_RANGE, UPPER_POINT, 0, CABiomes.DENSE_MOUNTAINS.get());

        // Layer 2
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(-1.1F, 0.1F), FULL_RANGE, LAYER_2, FULL_RANGE, UPPER_POINT, 0, CABiomes.DENSE_PLAINS.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(0.1F, 1.1F), FULL_RANGE, LAYER_2, FULL_RANGE, UPPER_POINT, 0, CABiomes.DENSEWOOD_FOREST.get());

        // Layer 1
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(-1.1F, 0.1F), FULL_RANGE, LAYER_1, FULL_RANGE, UPPER_POINT, 0, CABiomes.MESOZOIC_JUNGLE.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(0.1F, 1.1F), FULL_RANGE, LAYER_1, FULL_RANGE, UPPER_POINT, 0, CABiomes.GINKGO_FOREST.get());

        // Layer 0
        addSurfaceBiome(biomeClimateParameterPointMapper, FULL_RANGE, FULL_RANGE, Climate.Parameter.span(-0.05F, 1.1F), FULL_RANGE, ZERO_POINT, 0, CABiomes.STALAGMITE_VALLEY.get());
    }
}