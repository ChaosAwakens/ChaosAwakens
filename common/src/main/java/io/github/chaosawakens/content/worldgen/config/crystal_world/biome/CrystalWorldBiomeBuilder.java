package io.github.chaosawakens.content.worldgen.config.crystal_world.biome;

import com.mojang.datafixers.util.Pair;
import io.github.chaosawakens.content.registry.CABiomes;
import io.github.chaosawakens.content.worldgen.config.base.BiomeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import java.util.function.Consumer;

public class CrystalWorldBiomeBuilder implements BiomeBuilder {
    public static final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.1F, 1.1F);
    public static final Climate.Parameter UPPER_POINT = Climate.Parameter.point(3.0F);
    public static final Climate.Parameter LAYER_1 = Climate.Parameter.span(-1.1F, 0.16F);
    public static final Climate.Parameter LAYER_2 = Climate.Parameter.span(0.17F, 0.5F);
    public static final Climate.Parameter LAYER_3 = Climate.Parameter.span(0.51F, 1.1F);

    public CrystalWorldBiomeBuilder() { // Same per-object pattern Vanilla uses, JIC

    }

    @Override
    public void mapBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomeClimateParameterPointMapper) {
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(-0.4F, 0.4F), Climate.Parameter.span(-0.1F, 0.9F), LAYER_1,  Climate.Parameter.span(-0.4F, 0.4F), UPPER_POINT, 0, CABiomes.CRYSTALLINE_REEF.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, Climate.Parameter.span(-0.8F, 0.8F), Climate.Parameter.span(-0.9F, 0.4F), LAYER_2,  Climate.Parameter.span(-0.8F, 0.8F), UPPER_POINT, 0, CABiomes.CRYSTALWOOD_FOREST.get());
        addSurfaceBiome(biomeClimateParameterPointMapper, FULL_RANGE, Climate.Parameter.span(-0.4F, 0.4F), LAYER_3,  Climate.Parameter.span(-0.4F, 0.4F), UPPER_POINT, 0, CABiomes.KYANITE_PLAINS.get());
    }
}