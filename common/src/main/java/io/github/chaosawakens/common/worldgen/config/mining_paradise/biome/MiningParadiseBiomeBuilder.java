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
    public static final Climate.Parameter LAYER_1 = Climate.Parameter.span(-0.1F, 0.25F);
    public static final Climate.Parameter LAYER_2 = Climate.Parameter.span(0.25F, 0.57F); // .48
    public static final Climate.Parameter LAYER_4 = Climate.Parameter.span(0.57F, 1.55F);

    public MiningParadiseBiomeBuilder() { // Same per-object pattern Vanilla uses, JIC

    }

    @Override
    public void mapBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomeClimateParameterPointMapper) {
        // Layer 4
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(-0.87F, 0.87F), Climate.Parameter.span(-0.41F, 0.41F), LAYER_4, FULL_RANGE, Climate.Parameter.point(0.0F), 0, CABiomes.DENSE_MOUNTAINS.get());

        // Layer 2
        addSurfaceBiome(biomeClimateParameterPointMapper, FULL_RANGE, FULL_RANGE, LAYER_2, FULL_RANGE, Climate.Parameter.point(0.0F), 0, CABiomes.DENSE_PLAINS.get());

        // Layer 1
        addSurfaceBiome(biomeClimateParameterPointMapper, FULL_RANGE, FULL_RANGE, LAYER_1, FULL_RANGE, Climate.Parameter.point(0.0F), 0, CABiomes.MESOZOIC_JUNGLE.get());
    }
}
