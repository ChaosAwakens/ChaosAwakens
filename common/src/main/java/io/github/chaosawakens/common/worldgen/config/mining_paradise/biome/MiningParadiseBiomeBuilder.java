package io.github.chaosawakens.common.worldgen.config.mining_paradise.biome;

import com.mojang.datafixers.util.Pair;
import io.github.chaosawakens.common.registry.CABiomes;
import io.github.chaosawakens.common.worldgen.config.base.BiomeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import java.util.function.Consumer;

public class MiningParadiseBiomeBuilder implements BiomeBuilder {
    public static final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.1F, 1.55F);
    public static final Climate.Parameter LAYER_1 = Climate.Parameter.span(-0.2F, 0.15F);
    public static final Climate.Parameter LAYER_01 = Climate.Parameter.span(0.15F, 0.18F);
    public static final Climate.Parameter LAYER_12 = Climate.Parameter.span(0.18F, 0.25F);
    public static final Climate.Parameter LAYER_2 = Climate.Parameter.span(0.25F, 0.35F);
    public static final Climate.Parameter LAYER_02 = Climate.Parameter.span(0.35F, 0.4F);// .48
    public static final Climate.Parameter LAYER_23 = Climate.Parameter.span(0.4F, 0.57F);
    public static final Climate.Parameter LAYER_3 = Climate.Parameter.span(0.57F, 0.65F);
    public static final Climate.Parameter LAYER_03= Climate.Parameter.span(0.65F, 0.73F);
    public static final Climate.Parameter LAYER_4 = Climate.Parameter.span(0.73F, 0.83F);
    public static final Climate.Parameter LAYER_04 = Climate.Parameter.span(0.83F, 0.93F);
    public static final Climate.Parameter LAYER_4_CONT = Climate.Parameter.span(0.92F, 1.55F);


    public MiningParadiseBiomeBuilder() { // Same per-object pattern Vanilla uses, JIC

    }

    @Override
    public void mapBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomeClimateParameterPointMapper) {
        // Layer 3/4
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(-0.87F, 0.87F), Climate.Parameter.span(-0.41F, 0.41F), LAYER_3, FULL_RANGE, Climate.Parameter.point(0.0F), 0, CABiomes.DENSE_MOUNTAINS.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(-0.87F, 0.87F), Climate.Parameter.span(-0.41F, 0.41F), LAYER_4, FULL_RANGE, Climate.Parameter.point(0.0F), 0, CABiomes.DENSE_MOUNTAINS.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(-0.87F, 0.87F), Climate.Parameter.span(-0.41F, 0.41F), LAYER_4_CONT, FULL_RANGE, Climate.Parameter.point(0.0F), 0, CABiomes.DENSE_MOUNTAINS.get());

        // Layer 2
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(-1.1F, 0.0F), FULL_RANGE, LAYER_2, FULL_RANGE, Climate.Parameter.point(0.0F), 0, CABiomes.DENSE_PLAINS.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(-1.1F, 0.0F), FULL_RANGE, LAYER_23, FULL_RANGE, Climate.Parameter.point(0.0F), 0, CABiomes.DENSE_PLAINS.get());

        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(0.0F, 1.55F), FULL_RANGE, LAYER_2, FULL_RANGE, Climate.Parameter.point(0.0F), 0, CABiomes.DENSEWOOD_FOREST.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(0.0F, 1.55F), FULL_RANGE, LAYER_23, FULL_RANGE, Climate.Parameter.point(0.0F), 0, CABiomes.DENSEWOOD_FOREST.get());

        // Layer 1
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(-1.1F, 0.0F), FULL_RANGE, LAYER_1, FULL_RANGE, Climate.Parameter.point(0.0F), 0, CABiomes.MESOZOIC_JUNGLE.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(-1.1F, 0.0F), FULL_RANGE, LAYER_12, FULL_RANGE, Climate.Parameter.point(0.0F), 0, CABiomes.MESOZOIC_JUNGLE.get());

        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(0.0F, 1.55F), FULL_RANGE, LAYER_1, FULL_RANGE, Climate.Parameter.point(0.0F), 0, CABiomes.GINKGO_FOREST.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(0.0F, 1.55F), FULL_RANGE, LAYER_12, FULL_RANGE, Climate.Parameter.point(0.0F), 0, CABiomes.GINKGO_FOREST.get());

        // Layer 0
        addSurfaceBiome(biomeClimateParameterPointMapper, FULL_RANGE, FULL_RANGE, LAYER_01, FULL_RANGE, Climate.Parameter.point(0.0F), 0, CABiomes.STALAGMITE_VALLEY.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, FULL_RANGE, FULL_RANGE, LAYER_02, FULL_RANGE, Climate.Parameter.point(0.0F), 0, CABiomes.STALAGMITE_VALLEY.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, FULL_RANGE, FULL_RANGE, LAYER_03, FULL_RANGE, Climate.Parameter.point(0.0F), 0, CABiomes.STALAGMITE_VALLEY.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, FULL_RANGE, FULL_RANGE, LAYER_04, FULL_RANGE, Climate.Parameter.point(0.0F), 0, CABiomes.STALAGMITE_VALLEY.get());
    }
}
