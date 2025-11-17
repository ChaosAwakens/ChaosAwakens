package io.github.chaosawakens.common.worldgen.config.mining_paradise.biome;

import com.mojang.datafixers.util.Pair;
import io.github.chaosawakens.common.registry.CABiomes;
import io.github.chaosawakens.common.worldgen.config.base.BiomeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import java.util.function.Consumer;

public class MiningParadiseBiomeBuilder implements BiomeBuilder {
    public static final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-2.0F, 2.0F);
    public static final Climate.Parameter POINT = Climate.Parameter.point(0.0F);
    public static final Climate.Parameter LAYER_1 = Climate.Parameter.span(-0.3F, -0.071F);
    public static final Climate.Parameter LAYER_01 = Climate.Parameter.span(-0.07F, -0.03F);
    public static final Climate.Parameter LAYER_12 = Climate.Parameter.span(-0.031F, 0.15F);
    public static final Climate.Parameter LAYER_2 = Climate.Parameter.span(0.16F, 0.359F);
    public static final Climate.Parameter LAYER_02 = Climate.Parameter.span(0.36F, 0.42F);// .48
    public static final Climate.Parameter LAYER_23 = Climate.Parameter.span(0.421F, 0.5F);
    public static final Climate.Parameter LAYER_3 = Climate.Parameter.span(0.501F, 0.658F);
    public static final Climate.Parameter LAYER_03= Climate.Parameter.span(0.659F, 0.73F);
    public static final Climate.Parameter LAYER_4 = Climate.Parameter.span(0.731F, 0.839F);
    public static final Climate.Parameter LAYER_04 = Climate.Parameter.span(0.84F, 0.91F);
    public static final Climate.Parameter LAYER_4_CONT = Climate.Parameter.span(0.911F, 2.0F);


    public MiningParadiseBiomeBuilder() { // Same per-object pattern Vanilla uses, JIC

    }

    @Override
    public void mapBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomeClimateParameterPointMapper) {
        // Layer 3/4
        addSurfaceBiome(biomeClimateParameterPointMapper, FULL_RANGE, FULL_RANGE, LAYER_3, FULL_RANGE, POINT, 0, CABiomes.DENSE_MOUNTAINS.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, FULL_RANGE, FULL_RANGE, LAYER_4, FULL_RANGE, POINT, 0.001F, CABiomes.DENSE_MOUNTAINS.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, FULL_RANGE, FULL_RANGE, LAYER_4_CONT, FULL_RANGE, POINT, 0.002F, CABiomes.DENSE_MOUNTAINS.get());

        // Layer 2
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(-1.1F, 0.1F), FULL_RANGE, LAYER_2, FULL_RANGE, POINT, 0.004F, CABiomes.DENSE_PLAINS.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(-1.1F, 0.1F), FULL_RANGE, LAYER_23, FULL_RANGE, POINT, 0.006F, CABiomes.DENSE_PLAINS.get());

        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(0.1F, 1.1F), FULL_RANGE, LAYER_2, FULL_RANGE, POINT, 0.004F, CABiomes.DENSEWOOD_FOREST.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(0.1F, 1.1F), FULL_RANGE, LAYER_23, FULL_RANGE, POINT, 0.006F, CABiomes.DENSEWOOD_FOREST.get());

        // Layer 1
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(-1.1F, 0.1F), FULL_RANGE, LAYER_1, FULL_RANGE, POINT, 0, CABiomes.MESOZOIC_JUNGLE.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(-1.1F, 0.1F), FULL_RANGE, LAYER_12, FULL_RANGE, POINT, 0.002F, CABiomes.MESOZOIC_JUNGLE.get());

        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(0.1F, 1.1F), FULL_RANGE, LAYER_1, FULL_RANGE, POINT, 0, CABiomes.GINKGO_FOREST.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(0.1F, 1.1F), FULL_RANGE, LAYER_12, FULL_RANGE, POINT, 0.002F, CABiomes.GINKGO_FOREST.get());

        // Layer 0
        addSurfaceBiome(biomeClimateParameterPointMapper, FULL_RANGE, FULL_RANGE, LAYER_01, FULL_RANGE, POINT, 0.001F, CABiomes.STALAGMITE_VALLEY.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, FULL_RANGE, FULL_RANGE, LAYER_02, FULL_RANGE, POINT, 0.003F, CABiomes.STALAGMITE_VALLEY.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, FULL_RANGE, FULL_RANGE, LAYER_03, FULL_RANGE, POINT, 0.005F, CABiomes.STALAGMITE_VALLEY.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, FULL_RANGE, FULL_RANGE, LAYER_04, FULL_RANGE, POINT, 0.006F, CABiomes.STALAGMITE_VALLEY.get());
    }
}
