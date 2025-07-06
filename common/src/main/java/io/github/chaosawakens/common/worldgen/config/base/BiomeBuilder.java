package io.github.chaosawakens.common.worldgen.config.base;

import com.mojang.datafixers.util.Pair;
import io.github.chaosawakens.common.worldgen.config.mining_paradise.biome.MiningParadiseBiomeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;

import java.util.function.Consumer;

/**
 * Blueprint {@code interface} that provides boilerplate/shortcut methods and other conveniences for biome builder-type
 * classes.
 *
 * @see OverworldBiomeBuilder
 * @see MiningParadiseBiomeBuilder
 */
public interface BiomeBuilder {

    /**
     * Core method used by {@linkplain MultiNoiseBiomeSourceParameterList.Preset Biome Source Parameter List Presets} to
     * actually consume and map biomes to their respective noises/climate parameter points.
     *
     * @param biomeClimateParameterPointMapper The mapping {@linkplain Consumer} accepting different noise list-biome
     *                                         pairs for mapping and passing onto a {@linkplain MultiNoiseBiomeSourceParameterList}.
     */
    void mapBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomeClimateParameterPointMapper);

    /**
     * Specifies a biome to be placed on the surface in the climate parameters it's mapped to within this method.
     * <br></br>
     * All noises/"climate parameters" passed into this method are effectively horizontal 2D noise, only changing on both
     * the x and z axis. Depth is the only 3D noise, and it's primarily used for things like placing cave biomes at certain positions.
     * <br></br>
     * In this case, {@link #getLowerSurfaceDepth()} and {@link #getUpperSurfaceDepth()} are used to place biomes on or above
     * what is deemed to be the "surface" within an implementor biome builder type {@code class}.
     *
     * @param biomeMapper The {@link Consumer} to which the rest of the parameters will be passed in. Usually,
     *                    whenever you call this method (or its equivalents), you'll have access to this as a method
     *                    parameter (conventionally, you would access this in a biome builder type {@code class}).
     * @param temperature The temperature noise parameter the specified biome should be placed in. Usually used as a mental
     *                    model to differentiate between "cold" and "hot" biomes, conventionally preventing odd biome
     *                    placements (really up to you).
     * @param humidity The humidity noise parameter the specified biome should be placed in. Typically used as a mental
     *                 model for distinguishing between dry and "swampy" or otherwise humid biomes.
     * @param continentalness The continentalness noise parameter the specified biome should be placed in. Used as a mental
     *                        model to represent how far inland a biome is.
     * @param erosion The erosion noise parameter the specified biome should be placed in. Represents a mental model for
     *                how "eroded" (or flattened) a biome is.
     * @param weirdness The weirdness noise parameter the specified biome should be placed in. In Vanilla, this is used
     *                  to sort of allow for different biome variants (e.g. "Bamboo Jungle" VS "Normal Jungle"). This one's
     *                  a bit of a niche and often unused parameter, set to 0 most of the time.
     * @param offset The vertical offset the specified biome should be placed in account of.
     * @param actualBiomeToPlace The biome in question.
     */
    default void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomeMapper, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, float offset, ResourceKey<Biome> actualBiomeToPlace) {
        biomeMapper.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, getUpperSurfaceDepth(), weirdness, offset), actualBiomeToPlace));
        biomeMapper.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, getLowerSurfaceDepth(), weirdness, offset), actualBiomeToPlace));
    }

    /**
     * Specifies a biome to be placed underground in the climate parameters it's mapped to within this method.
     * <br></br>
     * All noises/"climate parameters" passed into this method are effectively horizontal 2D noise, only changing on both
     * the x and z axis. Depth is the only 3D noise, and it's primarily used for things like placing cave biomes at certain positions.
     * <br></br>
     * In this case, {@link #getUndergroundDepth()} is used to place biomes within what is deemed to be the "underground" section
     * within an implementor biome builder type {@code class}.
     *
     * @param biomeMapper The {@link Consumer} to which the rest of the parameters will be passed in. Usually,
     *                    whenever you call this method (or its equivalents), you'll have access to this as a method
     *                    parameter (conventionally, you would access this in a biome builder type {@code class}).
     * @param temperature The temperature noise parameter the specified biome should be placed in. Usually used as a mental
     *                    model to differentiate between "cold" and "hot" biomes, conventionally preventing odd biome
     *                    placements (really up to you).
     * @param humidity The humidity noise parameter the specified biome should be placed in. Typically used as a mental
     *                 model for distinguishing between dry and "swampy" or otherwise humid biomes.
     * @param continentalness The continentalness noise parameter the specified biome should be placed in. Used as a mental
     *                        model to represent how far inland a biome is.
     * @param erosion The erosion noise parameter the specified biome should be placed in. Represents a mental model for
     *                how "eroded" (or flattened) a biome is.
     * @param weirdness The weirdness noise parameter the specified biome should be placed in. In Vanilla, this is used
     *                  to sort of allow for different biome variants (e.g. "Bamboo Jungle" VS "Normal Jungle"). This one's
     *                  a bit of a niche and often unused parameter, set to 0 most of the time.
     * @param offset The vertical offset the specified biome should be placed in account of.
     * @param actualBiomeToPlace The biome in question.
     */
    default void addUndergroundBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomeMapper, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, float offset, ResourceKey<Biome> actualBiomeToPlace) {
        biomeMapper.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, getUndergroundDepth(), weirdness, offset), actualBiomeToPlace));
    }

    /**
     * Specifies a biome to be placed at the very bottom of a dimension in the climate parameters it's mapped to within this method.
     * <br></br>
     * All noises/"climate parameters" passed into this method are effectively horizontal 2D noise, only changing on both
     * the x and z axis. Depth is the only 3D noise, and it's primarily used for things like placing cave biomes at certain positions.
     * <br></br>
     * In this case, {@link #getBottomDepth()} is used to place biomes on what is deemed to be the "bottom" (lowest point)
     * within an implementor biome builder type {@code class}.
     *
     * @param biomeMapper The {@link Consumer} to which the rest of the parameters will be passed in. Usually,
     *                    whenever you call this method (or its equivalents), you'll have access to this as a method
     *                    parameter (conventionally, you would access this in a biome builder type {@code class}).
     * @param temperature The temperature noise parameter the specified biome should be placed in. Usually used as a mental
     *                    model to differentiate between "cold" and "hot" biomes, conventionally preventing odd biome
     *                    placements (really up to you).
     * @param humidity The humidity noise parameter the specified biome should be placed in. Typically used as a mental
     *                 model for distinguishing between dry and "swampy" or otherwise humid biomes.
     * @param continentalness The continentalness noise parameter the specified biome should be placed in. Used as a mental
     *                        model to represent how far inland a biome is.
     * @param erosion The erosion noise parameter the specified biome should be placed in. Represents a mental model for
     *                how "eroded" (or flattened) a biome is.
     * @param weirdness The weirdness noise parameter the specified biome should be placed in. In Vanilla, this is used
     *                  to sort of allow for different biome variants (e.g. "Bamboo Jungle" VS "Normal Jungle"). This one's
     *                  a bit of a niche and often unused parameter, set to 0 most of the time.
     * @param offset The vertical offset the specified biome should be placed in account of.
     * @param actualBiomeToPlace The biome in question.
     */
    default void addBottomBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomeMapper, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, float offset, ResourceKey<Biome> actualBiomeToPlace) {
        biomeMapper.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, getBottomDepth(), weirdness, offset), actualBiomeToPlace));
    }

    /**
     * The range (or point) determining the upper bound of the depth at which "surface" biomes are placed.
     *
     * @return The top depth of surface biomes. Default is {@code 0.0F}.
     */
    default Climate.Parameter getUpperSurfaceDepth() {
        return Climate.Parameter.point(0.0F);
    }

    /**
     * The range (or point) determining the lower bound of the depth at which "surface" biomes are placed.
     *
     * @return The bottom depth of surface biomes. Default is {@code 1.0F}.
     */
    default Climate.Parameter getLowerSurfaceDepth() {
        return Climate.Parameter.point(1.0F);
    }

    /**
     * The range (or point) determining the depth at which "underground" biomes are placed.
     *
     * @return The depth of underground/cave biomes. Default is ranged between {@code 0.2F - 0.9F}.
     */
    default Climate.Parameter getUndergroundDepth() {
        return Climate.Parameter.span(0.2F, 0.9F);
    }

    /**
     * The range (or point) determining the depth at which "bottom" biomes are placed.
     *
     * @return The depth of bottom biomes. Default is {@code 1.1F}.
     */
    default Climate.Parameter getBottomDepth() {
        return Climate.Parameter.point(1.1F);
    }
}
