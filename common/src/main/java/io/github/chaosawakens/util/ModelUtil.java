package io.github.chaosawakens.util;

import com.google.common.collect.Sets;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.block.BlockModelDefinition;
import io.github.chaosawakens.api.block.BlockStateDefinition;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.SlabType;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;
import java.util.function.Supplier;

/**
 * Utility class containing helper methods (generally also found in datagen classes) aimed at reducing boilerplate code by natively providing
 * common {@link BlockModelDefinition} and {@link BlockStateDefinition} patterns.
 */
public final class ModelUtil {

    private ModelUtil() {
        throw new IllegalAccessError("Attempted to construct Utility Class!");
    }

    /**
     * Creates a {@link BlockModelDefinition} with the {@link ModelTemplates#CUBE_ALL} template.
     * <p>
     * <h3>Required Texture Slots</h3>
     * <ul>
     *  <li>{@link TextureSlot#ALL} -> {@code sixSideBlockTexture}</li>
     * </ul>
     *
     * @param sixSideBlockTexture The {@link ResourceLocation} representing the texture of every side of a standard cube block.
     *                            Points to the {@code "block/"} path by default (applies to all BMD methods in this class).
     *
     * @return A {@link BlockModelDefinition} with the {@link ModelTemplates#CUBE_ALL} template.
     *
     * @see #simpleBlock(Supplier)
     */
    public static BlockModelDefinition cubeAll(ResourceLocation sixSideBlockTexture) {
        return BlockModelDefinition.of(ModelTemplates.CUBE_ALL)
                .withTextureMapping(TextureMapping.cube(sixSideBlockTexture.withPrefix("block/")));
    }

    /**
     * Creates a {@link BlockStateDefinition}, using {@link MultiVariantGenerator} with the {@link VariantProperties#MODEL} property set to the supplied {@linkplain Block Block's}
     * default model location.
     * <p>
     * <h3>Variants</h3>
     * <ul>
     *  <li>{@link VariantProperties#MODEL} -> {@link ModelLocationUtils#getModelLocation(Block)}</li>
     * </ul>
     *
     * @param targetBlock The {@linkplain Block Block} to use as the base for the {@link BlockStateDefinition}.
     *
     * @return A new {@link BlockStateDefinition} with a {@code simpleBlock} template.
     *
     * @see #cubeAll(ResourceLocation)
     * @see #cross(ResourceLocation)
     * @see #crossCutout(ResourceLocation)
     */
    public static BlockStateDefinition simpleBlock(Supplier<Block> targetBlock) {
        return BlockStateDefinition.of(targetBlock)
                .withBlockStateSupplier(MultiVariantGenerator.multiVariant(targetBlock.get(), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(targetBlock.get()))));
    }

    /**
     * Creates a {@link BlockModelDefinition} with the {@link ModelTemplates#CUBE_BOTTOM_TOP} template.
     * <p>
     * <h3>Required Texture Slots</h3>
     * <ul>
     *  <li>{@link TextureSlot#SIDE} -> {@code sideTexture}</li>
     *  <li>{@link TextureSlot#BOTTOM} -> {@code bottomTexture}</li>
     *  <li>{@link TextureSlot#TOP} -> {@code topTexture}</li>
     * </ul>
     *
     * @param sideTexture The {@link ResourceLocation} representing the texture of the 4 horizontal faces of a standard cube block (N, S, E, W).
     * @param bottomTexture The {@link ResourceLocation} representing the texture of the bottom vertical face of a standard cube block (D).
     * @param topTexture The {@link ResourceLocation} representing the texture of the top vertical face of a standard cube block (U).
     *
     * @return A {@link BlockModelDefinition} with the {@link ModelTemplates#CUBE_BOTTOM_TOP} template.
     *
     * @see #simpleBlock(Supplier)
     * @see #cubeBottomTop(ResourceLocation)
     * @see #cubeBottomTop(ResourceLocation, boolean)
     */
    public static BlockModelDefinition cubeBottomTop(ResourceLocation sideTexture, ResourceLocation bottomTexture, ResourceLocation topTexture) {
        return BlockModelDefinition.of(ModelTemplates.CUBE_BOTTOM_TOP)
                .withTextureMapping(new TextureMapping()
                        .put(TextureSlot.SIDE, sideTexture.withPrefix("block/"))
                        .put(TextureSlot.BOTTOM, bottomTexture.withPrefix("block/"))
                        .put(TextureSlot.TOP, topTexture.withPrefix("block/")));
    }

    /**
     * Overloaded variant of {@link #cubeBottomTop(ResourceLocation, ResourceLocation, ResourceLocation)}. Creates a {@link BlockModelDefinition} with the {@link ModelTemplates#CUBE_BOTTOM_TOP} template, where
     * the {@code bottomTexture} and {@code topTexture} are suffixed appropriately based on the {@code baseTexture}.
     * <p>
     * <h3>Required Texture Slots</h3>
     * <ul>
     *  <li>{@link TextureSlot#SIDE} -> {@code baseTexture}</li>
     *  <li>{@link TextureSlot#BOTTOM} -> {@code baseTexture + "_bottom"}</li>
     *  <li>{@link TextureSlot#TOP} -> {@code baseTexture + "_top"}</li>
     * </ul>
     *
     * @param baseTexture The {@link ResourceLocation} representing the texture of the 4 horizontal faces of a standard cube block (N, S, E, W), additionally representing the 2 other vertical
     *                      faces of a standard cube block (D, U) via pre-determining the bottom and top textures with the appropriate suffixes.
     * @param useBaseTexture Whether the {@code baseTexture} should be used for every required {@link TextureSlot}.
     *
     * @return A {@link BlockModelDefinition} with the {@link ModelTemplates#CUBE_BOTTOM_TOP} template.
     *
     * @see #cubeBottomTop(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #cubeBottomTop(ResourceLocation)
     * @see #simpleBlock(Supplier)
     */
    public static BlockModelDefinition cubeBottomTop(ResourceLocation baseTexture, boolean useBaseTexture) {
        return cubeBottomTop(baseTexture, useBaseTexture ? baseTexture : baseTexture.withSuffix("_bottom"), useBaseTexture ? baseTexture : baseTexture.withSuffix("_top"));
    }

    /**
     * Overloaded variant of {@link #cubeBottomTop(ResourceLocation, boolean)}. Creates a {@link BlockModelDefinition} with the {@link ModelTemplates#CUBE_BOTTOM_TOP} template, where
     * the {@code bottomTexture} and {@code topTexture} are suffixed appropriately based on the {@code baseTexture}. Uses the provided {@code baseTexture} for every required {@link TextureSlot}.
     * <p>
     * <h3>Required Texture Slots</h3>
     * <ul>
     *  <li>{@link TextureSlot#SIDE} -> {@code baseTexture}</li>
     *  <li>{@link TextureSlot#BOTTOM} -> {@code baseTexture}</li>
     *  <li>{@link TextureSlot#TOP} -> {@code baseTexture}</li>
     * </ul>
     *
     * @param baseTexture The {@link ResourceLocation} representing the texture of the 4 horizontal faces of a standard cube block (N, S, E, W), additionally representing the 2 other vertical
     *                      faces of a standard cube block (D, U) via pre-determining the bottom and top textures with the appropriate suffixes.
     *
     * @return A {@link BlockModelDefinition} with the {@link ModelTemplates#CUBE_BOTTOM_TOP} template.
     *
     * @see #cubeBottomTop(ResourceLocation, boolean)
     * @see #cubeBottomTop(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #simpleBlock(Supplier)
     */
    public static BlockModelDefinition cubeBottomTop(ResourceLocation baseTexture) {
        return cubeBottomTop(baseTexture, true);
    }

    /**
     * Creates a {@link BlockModelDefinition} with the {@link ModelTemplates#SLAB_BOTTOM} template.
     * <p>
     * <h3>Required Texture Slots</h3>
     * <ul>
     *  <li>{@link TextureSlot#SIDE} -> {@code sideTexture}</li>
     *  <li>{@link TextureSlot#BOTTOM} -> {@code bottomTexture}</li>
     *  <li>{@link TextureSlot#TOP} -> {@code topTexture}</li>
     * </ul>
     *
     * @param sideTexture The {@link ResourceLocation} representing the texture of the 4 horizontal faces of a standard slab block (N, S, E, W).
     * @param bottomTexture The {@link ResourceLocation} representing the texture of the bottom vertical face of a standard slab block (D).
     * @param topTexture The {@link ResourceLocation} representing the texture of the top vertical face of a standard slab block (U).
     *
     * @return A {@link BlockModelDefinition} with the {@link ModelTemplates#SLAB_BOTTOM} template.
     *
     * @see #slabBottom(ResourceLocation, boolean)
     * @see #slabBottom(ResourceLocation)
     * @see #slabTop(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slabTop(ResourceLocation, boolean)
     * @see #slabTop(ResourceLocation)
     * @see #slab(ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(ResourceLocation)
     * @see #slab(Supplier, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(Supplier, ResourceLocation)
     * @see #woodSlab(Supplier)
     */
    public static BlockModelDefinition slabBottom(ResourceLocation sideTexture, ResourceLocation bottomTexture, ResourceLocation topTexture) {
        return BlockModelDefinition.of(ModelTemplates.SLAB_BOTTOM)
                .withTextureMapping(new TextureMapping()
                        .put(TextureSlot.SIDE, sideTexture.withPrefix("block/"))
                        .put(TextureSlot.BOTTOM, bottomTexture.withPrefix("block/"))
                        .put(TextureSlot.TOP, topTexture.withPrefix("block/")));
    }

    /**
     * Overloaded variant of {@link #slabBottom(ResourceLocation, ResourceLocation, ResourceLocation)}, with the top and bottom textures determined based on {@code useBaseTexture}.
     * <p>
     * <h3>Required Texture Slots</h3>
     * <ul>
     *  <li>{@link TextureSlot#SIDE} -> {@code baseTexture}</li>
     *  <li>{@link TextureSlot#BOTTOM} -> {@code useBaseTexture ? baseTexture : baseTexture + "_bottom"}</li>
     *  <li>{@link TextureSlot#TOP} -> {@code useBaseTexture ? baseTexture : baseTexture + "_top"}</li>
     * </ul>
     *
     * @param baseTexture The {@link ResourceLocation} representing the texture of the 4 horizontal faces of a standard slab block (N, S, E, W), or all sides if {@code useBaseTexture} is {@code false}.
     * @param useBaseTexture Whether the provided {@code baseTexture} should be used for every required {@link TextureSlot}. If set to {@code false}, the top and bottom textures will use the
     *                       provided {@code baseTexture} suffixed with {@code "_top"} and {@code "_bottom"} respectively.
     *
     * @return A {@link BlockModelDefinition} with the {@link ModelTemplates#SLAB_BOTTOM} template, with the top and bottom textures determined based on provided {@code baseTexture}.
     *
     * @see #slabBottom(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slabBottom(ResourceLocation)
     * @see #slabTop(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slabTop(ResourceLocation, boolean)
     * @see #slabTop(ResourceLocation)
     * @see #slab(ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(ResourceLocation)
     * @see #slab(Supplier, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(Supplier, ResourceLocation)
     * @see #woodSlab(Supplier)
     */
    public static BlockModelDefinition slabBottom(ResourceLocation baseTexture, boolean useBaseTexture) {
        return slabBottom(baseTexture, useBaseTexture ? baseTexture : baseTexture.withSuffix("_bottom"), useBaseTexture ? baseTexture : baseTexture.withSuffix("_top"));
    }

    /**
     * Overloaded variant of {@link #slabBottom(ResourceLocation, boolean)}, with the top and bottom textures set to use the provided {@code baseTexture}.
     * <p>
     * <h3>Required Texture Slots</h3>
     * <ul>
     *  <li>{@link TextureSlot#SIDE} -> {@code baseTexture}</li>
     *  <li>{@link TextureSlot#BOTTOM} -> {@code baseTexture}</li>
     *  <li>{@link TextureSlot#TOP} -> {@code baseTexture}</li>
     * </ul>
     *
     * @param baseTexture The {@link ResourceLocation} representing the texture of all sides of a standard slab block.
     *
     * @return A {@link BlockModelDefinition} with the {@link ModelTemplates#SLAB_BOTTOM} template, with the top and bottom textures set to the provided {@code baseTexture}.
     *
     * @see #slabBottom(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slabBottom(ResourceLocation, boolean)
     * @see #slabTop(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slabTop(ResourceLocation, boolean)
     * @see #slabTop(ResourceLocation)
     * @see #slab(ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(ResourceLocation)
     * @see #slab(Supplier, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(Supplier, ResourceLocation)
     * @see #woodSlab(Supplier)
     */
    public static BlockModelDefinition slabBottom(ResourceLocation baseTexture) {
        return slabBottom(baseTexture, true);
    }

    /**
     * Creates a {@link BlockModelDefinition} with the {@link ModelTemplates#SLAB_TOP} template.
     * <p>
     * <h3>Required Texture Slots</h3>
     * <ul>
     *  <li>{@link TextureSlot#SIDE} -> {@code sideTexture}</li>
     *  <li>{@link TextureSlot#BOTTOM} -> {@code bottomTexture}</li>
     *  <li>{@link TextureSlot#TOP} -> {@code topTexture}</li>
     * </ul>
     *
     * @param sideTexture The {@link ResourceLocation} representing the texture of the 4 horizontal faces of a standard slab block (N, S, E, W).
     * @param bottomTexture The {@link ResourceLocation} representing the texture of the bottom vertical face of a standard slab block (D).
     * @param topTexture The {@link ResourceLocation} representing the texture of the top vertical face of a standard slab block (U).
     *
     * @return A {@link BlockModelDefinition} with the {@link ModelTemplates#SLAB_TOP} template.
     *
     * @see #slabTop(ResourceLocation, boolean)
     * @see #slabTop(ResourceLocation)
     * @see #slabBottom(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slabBottom(ResourceLocation, boolean)
     * @see #slabBottom(ResourceLocation)
     * @see #slab(ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(ResourceLocation)
     * @see #slab(Supplier, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(Supplier, ResourceLocation)
     * @see #woodSlab(Supplier)
     */
    public static BlockModelDefinition slabTop(ResourceLocation sideTexture, ResourceLocation bottomTexture, ResourceLocation topTexture) {
        return BlockModelDefinition.of(ModelTemplates.SLAB_TOP)
                .withTextureMapping(new TextureMapping()
                        .put(TextureSlot.SIDE, sideTexture.withPrefix("block/"))
                        .put(TextureSlot.BOTTOM, bottomTexture.withPrefix("block/"))
                        .put(TextureSlot.TOP, topTexture.withPrefix("block/")));
    }

    /**
     * Overloaded variant of {@link #slabTop(ResourceLocation, ResourceLocation, ResourceLocation)}, with the top and bottom textures determined based on {@code useBaseTexture}.
     * <p>
     * <h3>Required Texture Slots</h3>
     * <ul>
     *  <li>{@link TextureSlot#SIDE} -> {@code baseTexture}</li>
     *  <li>{@link TextureSlot#BOTTOM} -> {@code useBaseTexture ? baseTexture : baseTexture + "_bottom"}</li>
     *  <li>{@link TextureSlot#TOP} -> {@code useBaseTexture ? baseTexture : baseTexture + "_top"}</li>
     * </ul>
     *
     * @param baseTexture The {@link ResourceLocation} representing the texture of the 4 horizontal faces of a standard slab block (N, S, E, W), or all sides if {@code useBaseTexture} is {@code false}.
     * @param useBaseTexture Whether the provided {@code baseTexture} should be used for every required {@link TextureSlot}. If set to {@code false}, the top and bottom textures will use the
     *                       provided {@code baseTexture} suffixed with {@code "_top"} and {@code "_bottom"} respectively.
     *
     * @return A {@link BlockModelDefinition} with the {@link ModelTemplates#SLAB_TOP} template, with the top and bottom textures determined based on provided {@code baseTexture}.
     *
     * @see #slabTop(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slabTop(ResourceLocation)
     * @see #slabBottom(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slabBottom(ResourceLocation, boolean)
     * @see #slabBottom(ResourceLocation)
     * @see #slab(ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(ResourceLocation)
     * @see #slab(Supplier, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(Supplier, ResourceLocation)
     * @see #woodSlab(Supplier)
     */
    public static BlockModelDefinition slabTop(ResourceLocation baseTexture, boolean useBaseTexture) {
        return slabTop(baseTexture, useBaseTexture ? baseTexture : baseTexture.withSuffix("_bottom"), useBaseTexture ? baseTexture : baseTexture.withSuffix("_top"));
    }

    /**
     * Overloaded variant of {@link #slabTop(ResourceLocation, boolean)}, with the top and bottom textures set to use the provided {@code baseTexture}.
     * <p>
     * <h3>Required Texture Slots</h3>
     * <ul>
     *  <li>{@link TextureSlot#SIDE} -> {@code baseTexture}</li>
     *  <li>{@link TextureSlot#BOTTOM} -> {@code baseTexture}</li>
     *  <li>{@link TextureSlot#TOP} -> {@code baseTexture}</li>
     * </ul>
     *
     * @param baseTexture The {@link ResourceLocation} representing the texture of all sides of a standard slab block.
     *
     * @return A {@link BlockModelDefinition} with the {@link ModelTemplates#SLAB_TOP} template, with the top and bottom textures set to the provided {@code baseTexture}.
     *
     * @see #slabTop(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slabTop(ResourceLocation, boolean)
     * @see #slabBottom(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slabBottom(ResourceLocation, boolean)
     * @see #slabBottom(ResourceLocation)
     * @see #slab(ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(ResourceLocation)
     * @see #slab(Supplier, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(Supplier, ResourceLocation)
     * @see #woodSlab(Supplier)
     */
    public static BlockModelDefinition slabTop(ResourceLocation baseTexture) {
        return slabTop(baseTexture, true);
    }

    /**
     * Creates an {@link ObjectArrayList} of {@linkplain BlockModelDefinition BlockModelDefinitions} with the {@link ModelTemplates#SLAB_BOTTOM} and {@link ModelTemplates#SLAB_TOP} templates, with all required
     * {@linkplain TextureSlot TextureSlots} mapped out to the provided {@link ResourceLocation} parameters.
     * <p>
     * <h3>Models</h3>
     * <ul>
     *  <li>{@link ModelTemplates#SLAB_BOTTOM} -> <ul>
     *      <li>{@link TextureSlot#BOTTOM} -> {@code bottomBottomTexture}</li>
     *      <li>{@link TextureSlot#TOP} -> {@code bottomTopTexture}</li>
     *      <li>{@link TextureSlot#SIDE} -> {@code bottomSideTexture}</li>
     *  </ul></li>
     *
     *  <li>{@link ModelTemplates#SLAB_TOP} -> <ul>
     *      <li>{@link TextureSlot#BOTTOM} -> {@code topBottomTexture}</li>
     *      <li>{@link TextureSlot#TOP} -> {@code topTopTexture}</li>
     *      <li>{@link TextureSlot#SIDE} -> {@code topSideTexture}</li>
     *  </ul></li>
     * </ul>
     *
     * @param bottomSideTexture The {@link ResourceLocation} representing the side texture of the bottom slab block model.
     * @param bottomBottomTexture The {@link ResourceLocation} representing the bottom texture of the bottom slab block model.
     * @param bottomTopTexture The {@link ResourceLocation} representing the top texture of the bottom slab block model.
     * @param topSideTexture The {@link ResourceLocation} representing the side texture of the top slab block model.
     * @param topBottomTexture The {@link ResourceLocation} representing the bottom texture of the top slab block model.
     * @param topTopTexture The {@link ResourceLocation} representing the top texture of the top slab block model.
     *
     * @return An {@link ObjectArrayList} of {@linkplain BlockModelDefinition BlockModelDefinitions} with the {@link ModelTemplates#SLAB_BOTTOM} and {@link ModelTemplates#SLAB_TOP} templates.
     *
     * @see #slab(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(ResourceLocation)
     * @see #slabBottom(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slabBottom(ResourceLocation, boolean)
     * @see #slabBottom(ResourceLocation)
     * @see #slabTop(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slabTop(ResourceLocation, boolean)
     * @see #slabTop(ResourceLocation)
     */
    public static ObjectArrayList<BlockModelDefinition> slab(ResourceLocation bottomSideTexture, ResourceLocation bottomBottomTexture, ResourceLocation bottomTopTexture, ResourceLocation topSideTexture, ResourceLocation topBottomTexture, ResourceLocation topTopTexture) {
        return ObjectArrayList.of(slabBottom(bottomSideTexture, bottomBottomTexture, bottomTopTexture), slabTop(topSideTexture, topBottomTexture, topTopTexture));
    }

    /**
     * Overloaded variant of {@link #slab(ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)}. Creates an {@link ObjectArrayList} of
     * {@linkplain BlockModelDefinition BlockModelDefinitions} with the {@link ModelTemplates#SLAB_BOTTOM} and {@link ModelTemplates#SLAB_TOP} templates, with all required {@linkplain TextureSlot TextureSlots}
     * mapped out to the provided {@link ResourceLocation} parameters. Maps both returned models with shared {@linkplain TextureSlot TextureSlots}.
     * <p>
     * <h3>Models</h3>
     * <ul>
     *  <li>{@link ModelTemplates#SLAB_BOTTOM} -> <ul>
     *      <li>{@link TextureSlot#BOTTOM} -> {@code bottomTexture}</li>
     *      <li>{@link TextureSlot#TOP} -> {@code topTexture}</li>
     *      <li>{@link TextureSlot#SIDE} -> {@code sideTexture}</li>
     *  </ul></li>
     *
     *  <li>{@link ModelTemplates#SLAB_TOP} -> <ul>
     *      <li>{@link TextureSlot#BOTTOM} -> {@code bottomTexture}</li>
     *      <li>{@link TextureSlot#TOP} -> {@code topTexture}</li>
     *      <li>{@link TextureSlot#SIDE} -> {@code sideTexture}</li>
     *  </ul></li>
     * </ul>
     *
     * @param sideTexture The {@link ResourceLocation} representing the side texture of the bottom & top slab block models.
     * @param bottomTexture The {@link ResourceLocation} representing the bottom texture of the bottom & top slab block models.
     * @param topTexture The {@link ResourceLocation} representing the top texture of the bottom & top slab block models.
     *
     * @return An {@link ObjectArrayList} of {@linkplain BlockModelDefinition BlockModelDefinitions} with the {@link ModelTemplates#SLAB_BOTTOM} and {@link ModelTemplates#SLAB_TOP} templates, with each template
     * utilising shared {@linkplain TextureSlot TextureSlots}.
     *
     * @see #slab(ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(ResourceLocation)
     * @see #slabBottom(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slabBottom(ResourceLocation, boolean)
     * @see #slabBottom(ResourceLocation)
     * @see #slabTop(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slabTop(ResourceLocation, boolean)
     * @see #slabTop(ResourceLocation)
     */
    public static ObjectArrayList<BlockModelDefinition> slab(ResourceLocation sideTexture, ResourceLocation bottomTexture, ResourceLocation topTexture) {
        return slab(sideTexture, bottomTexture, topTexture, sideTexture, bottomTexture, topTexture);
    }

    /**
     * Overloaded variant of {@link #slab(ResourceLocation, ResourceLocation, ResourceLocation)}. Creates an {@link ObjectArrayList} of {@linkplain BlockModelDefinition BlockModelDefinitions}
     * with the {@link ModelTemplates#SLAB_BOTTOM} and {@link ModelTemplates#SLAB_TOP} templates, with all required {@linkplain TextureSlot TextureSlots} mapped out to the provided {@link ResourceLocation}
     * parameters. Maps both returned models' {@linkplain TextureSlot TextureSlots} with the provided {@code baseTexture}.
     * <p>
     * <h3>Models</h3>
     * <ul>
     *  <li>{@link ModelTemplates#SLAB_BOTTOM} -> <ul>
     *      <li>{@link TextureSlot#BOTTOM} -> {@code baseTexture}</li>
     *      <li>{@link TextureSlot#TOP} -> {@code baseTexture}</li>
     *      <li>{@link TextureSlot#SIDE} -> {@code baseTexture}</li>
     *  </ul></li>
     *
     *  <li>{@link ModelTemplates#SLAB_TOP} -> <ul>
     *      <li>{@link TextureSlot#BOTTOM} -> {@code baseTexture}</li>
     *      <li>{@link TextureSlot#TOP} -> {@code baseTexture}</li>
     *      <li>{@link TextureSlot#SIDE} -> {@code baseTexture}</li>
     *  </ul></li>
     * </ul>
     *
     * @param baseTexture The {@link ResourceLocation} representing the base texture of every {@link TextureSlot} of the bottom & top slab block models.
     *
     * @return An {@link ObjectArrayList} of {@linkplain BlockModelDefinition BlockModelDefinitions} with the {@link ModelTemplates#SLAB_BOTTOM} and {@link ModelTemplates#SLAB_TOP} templates, with each template's
     * {@linkplain TextureSlot TextureSlots} sharing the same {@code baseTexture}.
     *
     * @see #slab(ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slabBottom(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slabBottom(ResourceLocation, boolean)
     * @see #slabBottom(ResourceLocation)
     * @see #slabTop(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slabTop(ResourceLocation, boolean)
     * @see #slabTop(ResourceLocation)
     */
    public static ObjectArrayList<BlockModelDefinition> slab(ResourceLocation baseTexture) {
        return slab(baseTexture, baseTexture, baseTexture);
    }

    /**
     * Creates a {@link BlockStateDefinition}, using {@link MultiVariantGenerator} with the {@link VariantProperties#MODEL} property set to the provided {@linkplain ResourceLocation ResourceLocations}
     * based on the {@link BlockStateProperties#SLAB_TYPE} property.
     * <p>
     * <h3>Variants / Properties</h3>
     * <ul>
     *  <li>{@link BlockStateProperties#SLAB_TYPE} -> <ul>
     *      <li>{@link SlabType#BOTTOM} -> <ul>
     *          <li>{@link VariantProperties#MODEL} -> {@code bottomModel}</li>
     *      </ul></li>
     *      <li>{@link SlabType#TOP} -> <ul>
     *          <li>{@link VariantProperties#MODEL} -> {@code topModel}</li>
     *      </ul></li>
     *      <li>{@link SlabType#DOUBLE} -> <ul>
     *          <li>{@link VariantProperties#MODEL} -> {@code doubleBlockModel}</li>
     *      </ul></li>
     *  </ul></li>
     * </ul>
     *
     * @param targetBlock The {@linkplain Block Block} to use as the base for the {@link BlockStateDefinition}.
     * @param bottomModel The {@link ResourceLocation} representing the model of the bottom half of a given standard slab.
     * @param topModel The {@link ResourceLocation} representing the model of the top half of a given standard slab.
     * @param doubleSlabModel The {@link ResourceLocation} representing the model of 2 slabs stacked on top of each other to create a standard cube block.
     *
     * @return A {@link BlockStateDefinition} with the {@link MultiVariantGenerator} with the {@link VariantProperties#MODEL} property set to the provided {@linkplain ResourceLocation ResourceLocations}
     * based on the {@link BlockStateProperties#SLAB_TYPE} property.
     *
     * @see #slab(Supplier, ResourceLocation)
     * @see SlabType
     */
    public static BlockStateDefinition slab(Supplier<Block> targetBlock, ResourceLocation bottomModel, ResourceLocation topModel, ResourceLocation doubleSlabModel) {
        return BlockStateDefinition.of(targetBlock)
                .withBlockStateSupplier(MultiVariantGenerator.multiVariant(targetBlock.get())
                        .with(PropertyDispatch.property(BlockStateProperties.SLAB_TYPE)
                                .select(SlabType.BOTTOM, Variant.variant()
                                        .with(VariantProperties.MODEL, bottomModel))
                                .select(SlabType.TOP, Variant.variant().with(VariantProperties.MODEL, topModel))
                                .select(SlabType.DOUBLE, Variant.variant().with(VariantProperties.MODEL, doubleSlabModel))));
    }

    /**
     * Overloaded variant of {@link #slab(Supplier, ResourceLocation, ResourceLocation, ResourceLocation)}, utilising the supplied {@link Block} as the base for deciding the
     * {@link ResourceLocation} of every required {@link TextureSlot} in a standard slab. The double-slab model variant is based on the provided {@code doubleSlabModel}.
     * <p>
     * <h3>Variants / Properties</h3>
     * <ul>
     *  <li>{@link BlockStateProperties#SLAB_TYPE} -> <ul>
     *      <li>{@link SlabType#BOTTOM} -> <ul>
     *          <li>{@link VariantProperties#MODEL} -> {@link ModelLocationUtils#getModelLocation(Block)}</li>
     *      </ul></li>
     *      <li>{@link SlabType#TOP} -> <ul>
     *          <li>{@link VariantProperties#MODEL} -> {@link ModelLocationUtils#getModelLocation(Block, String)} (Suffixed with {@code "_top"})</li>
     *      </ul></li>
     *      <li>{@link SlabType#DOUBLE} -> <ul>
     *          <li>{@link VariantProperties#MODEL} -> {@code doubleBlockModel}</li>
     *      </ul></li>
     *  </ul></li>
     * </ul>
     *
     * @param targetBlock The {@link Block} used as the base for determining the {@link ResourceLocation} of every required {@link TextureSlot} in a standard slab.
     * @param doubleBlockModel The {@link ResourceLocation} representing the model of 2 slabs stacked on top of each other to create a standard cube block.
     *
     * @return A {@link BlockStateDefinition} with the {@link MultiVariantGenerator} with the {@link VariantProperties#MODEL} property set to the required {@linkplain ResourceLocation ResourceLocations}
     * based on the required {@linkplain TextureSlot TextureSlots} in a standard slab, utilising the supplied {@link Block} as the base.
     *
     * @see #slab(Supplier, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(ResourceLocation)
     * @see #slabBottom(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slabBottom(ResourceLocation, boolean)
     * @see #slabBottom(ResourceLocation)
     * @see #slabTop(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slabTop(ResourceLocation, boolean)
     * @see #slabTop(ResourceLocation)
     * @see #woodSlab(Supplier)
     * @see SlabType
     */
    public static BlockStateDefinition slab(Supplier<Block> targetBlock, ResourceLocation doubleBlockModel) {
        return slab(targetBlock, ModelLocationUtils.getModelLocation(targetBlock.get()), ModelLocationUtils.getModelLocation(targetBlock.get(), "_top"), doubleBlockModel);
    }

    /**
     * Overloaded variant of {@link #slab(Supplier, ResourceLocation)}, utilising the supplied {@link Block} as the base for deciding the
     * {@link ResourceLocation} of every required {@link TextureSlot} in a standard slab. Also defaults the double-slab model to reference the plank variant of the supplied {@link Block},
     * assuming that it exists (Will likely throw an exception otherwise). Should typically be used for standard wood sets, particularly slabs (duh).
     * <p>
     * <h3>Variants / Properties</h3>
     * <ul>
     *  <li>{@link BlockStateProperties#SLAB_TYPE} -> <ul>
     *      <li>{@link SlabType#BOTTOM} -> <ul>
     *          <li>{@link VariantProperties#MODEL} -> {@link ModelLocationUtils#getModelLocation(Block)}</li>
     *      </ul></li>
     *      <li>{@link SlabType#TOP} -> <ul>
     *          <li>{@link VariantProperties#MODEL} -> {@link ModelLocationUtils#getModelLocation(Block, String)} (Suffixed with {@code "_top"})</li>
     *      </ul></li>
     *      <li>{@link SlabType#DOUBLE} -> <ul>
     *          <li>{@link VariantProperties#MODEL} -> {@link ModelLocationUtils#getModelLocation(Block)} + {@code replaceAll("_slab", "_planks")}</li>
     *      </ul></li>
     *  </ul></li>
     * </ul>
     *
     * @param targetBlock The {@link Block} used as the base for determining the {@link ResourceLocation} of every required {@link TextureSlot} in a standard slab.
     *
     * @return A {@link BlockStateDefinition} with the {@link MultiVariantGenerator} with the {@link VariantProperties#MODEL} property set to the required {@linkplain ResourceLocation ResourceLocations}
     * based on the required {@linkplain TextureSlot TextureSlots} in a standard slab, utilising the supplied {@link Block} as the base.
     *
     * @see #slab(Supplier, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(ResourceLocation)
     * @see #slabBottom(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slabBottom(ResourceLocation, boolean)
     * @see #slabBottom(ResourceLocation)
     * @see #slabTop(ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slabTop(ResourceLocation, boolean)
     * @see #slabTop(ResourceLocation)
     * @see #slab(Supplier, ResourceLocation)
     * @see SlabType
     */
    public static BlockStateDefinition woodSlab(Supplier<Block> targetBlock) {
        String targetDoubleBlockModel = ModelLocationUtils.getModelLocation(targetBlock.get()).getPath();
        return slab(targetBlock, CAConstants.prefix(!targetDoubleBlockModel.contains("_slab") ? targetDoubleBlockModel.concat("_planks") : targetDoubleBlockModel.replaceAll("_slab", "_planks")));
    }

    /**
     * Creates a {@link BlockModelDefinition} with the {@link ModelTemplates#CROSS} template. If you're doing crops and such, you may want to refer to {@link #crossCutout(ResourceLocation)}.
     * Handles item model.
     * <p>
     * <h3>Required Texture Slots</h3>
     * <ul>
     *  <li>{@link TextureSlot#CROSS} -> {@code baseCrossTexture}</li>
     * </ul>
     *
     * @param baseCrossTexture The {@link ResourceLocation} representing the base texture file of every face of a cross-block.
     *
     * @return A {@link BlockModelDefinition} with the {@link ModelTemplates#CROSS} template.
     *
     * @see #crossCutout(ResourceLocation)
     * @see #simpleBlock(Supplier)
     */
    public static BlockModelDefinition cross(ResourceLocation baseCrossTexture) {
        return BlockModelDefinition.of(ModelTemplates.CROSS)
                .withTextureMapping(TextureMapping.cross(baseCrossTexture.withPrefix("block/")))
                .withItemParentModelLoc(ModelTemplates.FLAT_ITEM)
                .withItemModelTextureMapping(TextureMapping.layer0(baseCrossTexture.withPrefix("block/")));
    }

    /**
     * Creates a {@link BlockModelDefinition} with the {@link ModelTemplates#CROSS} template. Calls {@link #cross(ResourceLocation)} and sets the
     * {@link BlockModelDefinition#withBlockRenderType(ResourceLocation)} to {@link RenderType#cutout()}. Handles item model.
     * <p>
     * <h3>Required Texture Slots</h3>
     * <ul>
     *  <li>{@link TextureSlot#CROSS} -> {@code baseCrossTexture}</li>
     * </ul>
     *
     * @param baseCrossTexture The {@link ResourceLocation} representing the base texture file of every face of a cross-block.
     *
     * @return A {@link BlockModelDefinition} with the {@link ModelTemplates#CROSS} template and the {@link RenderType#cutout()} render type.
     *
     * @see #cross(ResourceLocation)
     * @see #simpleBlock(Supplier)
     * @see RenderType
     */
    public static BlockModelDefinition crossCutout(ResourceLocation baseCrossTexture) {
        return cross(baseCrossTexture).withBlockRenderType(new ResourceLocation(RenderType.cutout().name));
    }

    /**
     * Creates a {@link BlockModelDefinition} with the {@link ModelTemplates#CUBE_COLUMN} template.
     * <p>
     * <h3>Required Texture Slots</h3>
     * <ul>
     *  <li>{@link TextureSlot#SIDE} -> {@code sideTexture}</li>
     *  <li>{@link TextureSlot#END} -> {@code endTexture}</li>
     * </ul>
     *
     * @param sideTexture The {@link ResourceLocation} representing the texture file of the side faces of a standard column block (I.E. horizontal faces, N, S, E, W).
     * @param endTexture The {@link ResourceLocation} representing the texture file of the end faces of a standard column block (I.E. vertical faces, U, D).
     *
     * @return A {@link BlockModelDefinition} with the {@link ModelTemplates#CUBE_COLUMN} template.
     *
     * @see #cubeColumn(ResourceLocation)
     * @see #cubeColumnHorizontal(ResourceLocation, ResourceLocation)
     * @see #cubeColumnHorizontal(ResourceLocation)
     * @see #rotatedPillarBlock(ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #rotatedPillarBlock(ResourceLocation, ResourceLocation)
     * @see #rotatedPillarBlock(Supplier, ResourceLocation, ResourceLocation)
     * @see #rotatedPillarBlock(Supplier, ResourceLocation)
     * @see #rotatedPillarBlock(Supplier)
     */
    public static BlockModelDefinition cubeColumn(ResourceLocation sideTexture, ResourceLocation endTexture) {
        return BlockModelDefinition.of(ModelTemplates.CUBE_COLUMN)
                .withTextureMapping(TextureMapping.column(sideTexture.withPrefix("block/"), endTexture.withPrefix("block/")));
    }

    /**
     * Overload variant of {@link #cubeColumn(ResourceLocation, ResourceLocation)}. Creates a {@link BlockModelDefinition} with the {@link ModelTemplates#CUBE_COLUMN} template, with both required
     * {@linkplain TextureSlot TextureSlots} mapped to the provided {@code baseTexture}.
     * <p>
     * <h3>Required Texture Slots</h3>
     * <ul>
     *  <li>{@link TextureSlot#SIDE} -> {@code baseTexture}</li>
     *  <li>{@link TextureSlot#END} -> {@code baseTexture}</li>
     * </ul>
     *
     * @param baseTexture The {@link ResourceLocation} representing the texture file of all faces of a standard column block (I.E. horizontal + vertical faces, N, S, E, W, U, D).
     *
     * @return A {@link BlockModelDefinition} with the {@link ModelTemplates#CUBE_COLUMN} template, with both required {@linkplain TextureSlot TextureSlots} set to the provided {@code baseTexture}.
     *
     * @see #cubeColumn(ResourceLocation, ResourceLocation)
     * @see #cubeColumnHorizontal(ResourceLocation, ResourceLocation)
     * @see #cubeColumnHorizontal(ResourceLocation)
     * @see #rotatedPillarBlock(ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #rotatedPillarBlock(ResourceLocation, ResourceLocation)
     * @see #rotatedPillarBlock(Supplier, ResourceLocation, ResourceLocation)
     * @see #rotatedPillarBlock(Supplier, ResourceLocation)
     * @see #rotatedPillarBlock(Supplier)
     */
    public static BlockModelDefinition cubeColumn(ResourceLocation baseTexture) {
        return cubeColumn(baseTexture, baseTexture);
    }

    /**
     * Creates a {@link BlockModelDefinition} with the {@link ModelTemplates#CUBE_COLUMN_HORIZONTAL} template.
     * <p>
     * <h3>Required Texture Slots</h3>
     * <ul>
     *  <li>{@link TextureSlot#SIDE} -> {@code sideTexture}</li>
     *  <li>{@link TextureSlot#END} -> {@code endTexture}</li>
     * </ul>
     *
     * @param sideTexture The {@link ResourceLocation} representing the texture file of the side faces of a standard column block (I.E. horizontal faces, N, S, E, W).
     * @param endTexture The {@link ResourceLocation} representing the texture file of the end faces of a standard column block (I.E. vertical faces, U, D).
     *
     * @return A {@link BlockModelDefinition} with the {@link ModelTemplates#CUBE_COLUMN_HORIZONTAL} template.
     *
     * @see #cubeColumnHorizontal(ResourceLocation)
     * @see #cubeColumn(ResourceLocation, ResourceLocation)
     * @see #cubeColumn(ResourceLocation)
     * @see #rotatedPillarBlock(ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #rotatedPillarBlock(ResourceLocation, ResourceLocation)
     * @see #rotatedPillarBlock(Supplier, ResourceLocation, ResourceLocation)
     * @see #rotatedPillarBlock(Supplier, ResourceLocation)
     * @see #rotatedPillarBlock(Supplier)
     */
    public static BlockModelDefinition cubeColumnHorizontal(ResourceLocation sideTexture, ResourceLocation endTexture) {
        return BlockModelDefinition.of(ModelTemplates.CUBE_COLUMN_HORIZONTAL)
                .withTextureMapping(TextureMapping.column(sideTexture.withPrefix("block/"), endTexture.withPrefix("block/")));
    }

    /**
     * Overload variant of {@link #cubeColumnHorizontal(ResourceLocation, ResourceLocation)}. Creates a {@link BlockModelDefinition} with the {@link ModelTemplates#CUBE_COLUMN_HORIZONTAL} template, with both required
     * {@linkplain TextureSlot TextureSlots} mapped to the provided {@code baseTexture}.
     * <p>
     * <h3>Required Texture Slots</h3>
     * <ul>
     *  <li>{@link TextureSlot#SIDE} -> {@code baseTexture}</li>
     *  <li>{@link TextureSlot#END} -> {@code baseTexture}</li>
     * </ul>
     *
     * @param baseTexture The {@link ResourceLocation} representing the texture file of all faces of a standard column block (I.E. horizontal + vertical faces, N, S, E, W, U, D).
     *
     * @return A {@link BlockModelDefinition} with the {@link ModelTemplates#CUBE_COLUMN_HORIZONTAL} template, with both required {@linkplain TextureSlot TextureSlots} set to the provided {@code baseTexture}.
     *
     * @see #cubeColumnHorizontal(ResourceLocation, ResourceLocation)
     * @see #cubeColumn(ResourceLocation, ResourceLocation)
     * @see #cubeColumn(ResourceLocation)
     * @see #rotatedPillarBlock(ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #rotatedPillarBlock(ResourceLocation, ResourceLocation)
     * @see #rotatedPillarBlock(Supplier, ResourceLocation, ResourceLocation)
     * @see #rotatedPillarBlock(Supplier, ResourceLocation)
     * @see #rotatedPillarBlock(Supplier)
     */
    public static BlockModelDefinition cubeColumnHorizontal(ResourceLocation baseTexture) {
        return cubeColumnHorizontal(baseTexture, baseTexture);
    }

    /**
     * Creates an {@link ObjectArrayList} of {@linkplain BlockModelDefinition BlockModelDefinitions} with the {@link ModelTemplates#CUBE_COLUMN} and {@link ModelTemplates#CUBE_COLUMN_HORIZONTAL} templates, in order
     * to constitute a standard rotated pillar block.
     * <p>
     * <h3>Models</h3>
     * <ul>
     *  <li>{@link ModelTemplates#CUBE_COLUMN} -> <ul>
     *      <li>{@link TextureSlot#SIDE} -> {@code ccSideTexture}</li>
     *      <li>{@link TextureSlot#END} -> {@code ccEndTexture}</li>
     *  </ul></li>
     *
     *  <li>{@link ModelTemplates#CUBE_COLUMN_HORIZONTAL} -> <ul>
     *      <li>{@link TextureSlot#SIDE} -> {@code cchSideTexture}</li>
     *      <li>{@link TextureSlot#END} -> {@code cchEndTexture}</li>
     *  </ul></li>
     * </ul>
     *
     * @param ccSideTexture The {@link ResourceLocation} representing the texture file of the side faces (horizontal, I.E. N, S, E, W) of the {@link ModelTemplates#CUBE_COLUMN} model.
     * @param ccEndTexture The {@link ResourceLocation} representing the texture file of the end (vertical, I.E. U, D) faces of the {@link ModelTemplates#CUBE_COLUMN} model.
     * @param cchSideTexture The {@link ResourceLocation} representing the texture file of the side faces (horizontal, I.E. N, S, E, W) of the {@link ModelTemplates#CUBE_COLUMN_HORIZONTAL} model.
     * @param cchEndTexture The {@link ResourceLocation} representing the texture file of the end (vertical, I.E. U, D) faces of the {@link ModelTemplates#CUBE_COLUMN_HORIZONTAL} model.
     *
     * @return An {@link ObjectArrayList} of {@linkplain BlockModelDefinition BlockModelDefinitions} with the {@link ModelTemplates#CUBE_COLUMN} and {@link ModelTemplates#CUBE_COLUMN_HORIZONTAL} templates, in order
     *         to constitute a standard rotated pillar block.
     *
     * @see #rotatedPillarBlock(ResourceLocation, ResourceLocation)
     * @see #rotatedPillarBlock(Supplier, ResourceLocation, ResourceLocation)
     * @see #rotatedPillarBlock(Supplier, ResourceLocation)
     * @see #rotatedPillarBlock(Supplier)
     * @see #cubeColumn(ResourceLocation, ResourceLocation)
     * @see #cubeColumn(ResourceLocation)
     * @see #cubeColumnHorizontal(ResourceLocation, ResourceLocation)
     * @see #cubeColumnHorizontal(ResourceLocation)
     */
    public static ObjectArrayList<BlockModelDefinition> rotatedPillarBlock(ResourceLocation ccSideTexture, ResourceLocation ccEndTexture, ResourceLocation cchSideTexture, ResourceLocation cchEndTexture) {
        return ObjectArrayList.of(cubeColumn(ccSideTexture, ccEndTexture), cubeColumnHorizontal(cchSideTexture, cchEndTexture));
    }

    /**
     * Overloaded variant of {@link #rotatedPillarBlock(ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)}. Creates an {@link ObjectArrayList} of
     * {@linkplain BlockModelDefinition BlockModelDefinitions} with the {@link ModelTemplates#CUBE_COLUMN} and {@link ModelTemplates#CUBE_COLUMN_HORIZONTAL} templates, in order
     * to constitute a standard rotated pillar block. Allows both models to share the same side and end textures.
     * <p>
     * <h3>Models</h3>
     * <ul>
     *  <li>{@link ModelTemplates#CUBE_COLUMN} -> <ul>
     *      <li>{@link TextureSlot#SIDE} -> {@code sideTexture}</li>
     *      <li>{@link TextureSlot#END} -> {@code endTexture}</li>
     *  </ul></li>
     *
     *  <li>{@link ModelTemplates#CUBE_COLUMN_HORIZONTAL} -> <ul>
     *      <li>{@link TextureSlot#SIDE} -> {@code sideTexture}</li>
     *      <li>{@link TextureSlot#END} -> {@code endTexture}</li>
     *  </ul></li>
     * </ul>
     *
     * @param sideTexture The {@link ResourceLocation} representing the texture file of the side faces (horizontal, I.E. N, S, E, W) of both {@link ModelTemplates#CUBE_COLUMN}
     *                   and {@link ModelTemplates#CUBE_COLUMN_HORIZONTAL} models.
     * @param endTexture The {@link ResourceLocation} representing the texture file of the end (vertical, I.E. U, D) faces of both {@link ModelTemplates#CUBE_COLUMN}
     *                   and {@link ModelTemplates#CUBE_COLUMN_HORIZONTAL} models.
     *
     * @return An {@link ObjectArrayList} of {@linkplain BlockModelDefinition BlockModelDefinitions} with the {@link ModelTemplates#CUBE_COLUMN} and {@link ModelTemplates#CUBE_COLUMN_HORIZONTAL} templates, in order
     *         to constitute a standard rotated pillar block, both models sharing the same side and end textures.
     *
     * @see #rotatedPillarBlock(ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #rotatedPillarBlock(Supplier, ResourceLocation, ResourceLocation)
     * @see #rotatedPillarBlock(Supplier, ResourceLocation)
     * @see #rotatedPillarBlock(Supplier)
     * @see #cubeColumn(ResourceLocation, ResourceLocation)
     * @see #cubeColumn(ResourceLocation)
     * @see #cubeColumnHorizontal(ResourceLocation, ResourceLocation)
     * @see #cubeColumnHorizontal(ResourceLocation)
     */
    public static ObjectArrayList<BlockModelDefinition> rotatedPillarBlock(ResourceLocation sideTexture, ResourceLocation endTexture) {
        return rotatedPillarBlock(sideTexture, endTexture, sideTexture, endTexture);
    }

    /**
     * Creates a {@link BlockStateDefinition}, using {@link MultiVariantGenerator} to update the supplied {@linkplain Block Block's} model based on its rotation across all 3 axis.
     * <p>
     * <h3>Variants / Properties</h3>
     * <ul>
     *  <li>{@link BlockStateProperties#AXIS} -> <ul>
     *      <li>{@link Direction.Axis#X} -> <ul>
     *          <li>{@link VariantProperties#MODEL} -> {@code horizontalModel}</li>
     *          <li>{@link VariantProperties#X_ROT} -> {@link VariantProperties.Rotation#R90}</li>
     *          <li>{@link VariantProperties#Y_ROT} -> {@link VariantProperties.Rotation#R90}</li>
     *      </ul></li>
     *      <li>{@link Direction.Axis#Y} -> <ul>
     *          <li>{@link VariantProperties#MODEL} -> {@code baseModel}</li>
     *      </ul></li>
     *      <li>{@link Direction.Axis#Z} -> <ul>
     *          <li>{@link VariantProperties#MODEL} -> {@code horizontalModel}</li>
     *          <li>{@link VariantProperties#X_ROT} -> {@link VariantProperties.Rotation#R90}</li>
     *      </ul></li>
     *  </ul></li>
     * </ul>
     *
     * @param targetBlock The {@linkplain Block Block} to use as the base for the {@link BlockStateDefinition}.
     * @param baseModel The {@link ResourceLocation} representing the block model of the non-horizontal variant of the supplied {@link Block}.
     * @param horizontalModel The {@link ResourceLocation} representing the block model of the horizontal (rotated) variant of the supplied {@link Block}.
     *
     * @return A {@link BlockStateDefinition}, using {@link MultiVariantGenerator} to update the supplied {@linkplain Block Block's} model based on its rotation across all 3 axis.
     *
     * @see #rotatedPillarBlock(Supplier, ResourceLocation)
     * @see #rotatedPillarBlock(Supplier)
     * @see #rotatedPillarBlock(ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #rotatedPillarBlock(ResourceLocation, ResourceLocation)
     * @see #cubeColumn(ResourceLocation, ResourceLocation)
     * @see #cubeColumn(ResourceLocation)
     * @see #cubeColumnHorizontal(ResourceLocation, ResourceLocation)
     * @see #cubeColumnHorizontal(ResourceLocation)
     */
    public static BlockStateDefinition rotatedPillarBlock(Supplier<Block> targetBlock, ResourceLocation baseModel, ResourceLocation horizontalModel) {
        return BlockStateDefinition.of(targetBlock).withBlockStateSupplier(MultiVariantGenerator.multiVariant(targetBlock.get())
                .with(PropertyDispatch.property(BlockStateProperties.AXIS)
                        .select(Direction.Axis.Y, Variant.variant()
                                .with(VariantProperties.MODEL, baseModel))
                        .select(Direction.Axis.Z, Variant.variant()
                                .with(VariantProperties.MODEL, horizontalModel)
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.Axis.X, Variant.variant()
                                .with(VariantProperties.MODEL, horizontalModel)
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))));
    }

    /**
     * Overloaded variant of {@link #rotatedPillarBlock(Supplier, ResourceLocation, ResourceLocation)}. Creates a {@link BlockStateDefinition}, using {@link MultiVariantGenerator}
     * to update the supplied {@linkplain Block Block's} model based on its rotation across all 3 axis. Defaults the {@code horizontalModel} to {@code baseModel + {@code "_horizontal"}}.
     * <p>
     * <h3>Variants / Properties</h3>
     * <ul>
     *  <li>{@link BlockStateProperties#AXIS} -> <ul>
     *      <li>{@link Direction.Axis#X} -> <ul>
     *          <li>{@link VariantProperties#MODEL} -> {@code baseModel + {@code "_horizontal"}}</li>
     *          <li>{@link VariantProperties#X_ROT} -> {@link VariantProperties.Rotation#R90}</li>
     *          <li>{@link VariantProperties#Y_ROT} -> {@link VariantProperties.Rotation#R90}</li>
     *      </ul></li>
     *      <li>{@link Direction.Axis#Y} -> <ul>
     *          <li>{@link VariantProperties#MODEL} -> {@code baseModel}</li>
     *      </ul></li>
     *      <li>{@link Direction.Axis#Z} -> <ul>
     *          <li>{@link VariantProperties#MODEL} -> {@code baseModel + {@code "_horizontal"}}</li>
     *          <li>{@link VariantProperties#X_ROT} -> {@link VariantProperties.Rotation#R90}</li>
     *      </ul></li>
     *  </ul></li>
     * </ul>
     *
     * @param targetBlock The {@linkplain Block Block} to use as the base for the {@link BlockStateDefinition}.
     * @param baseModel The {@link ResourceLocation} representing the block model of the non-horizontal variant of the supplied {@link Block}.
     *
     * @return A {@link BlockStateDefinition}, using {@link MultiVariantGenerator} to update the supplied {@linkplain Block Block's} model based on its rotation across all 3 axis,
     * and with the {@code horizontalModel} defaulted to {@code baseModel + {@code "_horizontal"}}.
     *
     * @see #rotatedPillarBlock(Supplier, ResourceLocation, ResourceLocation)
     * @see #rotatedPillarBlock(Supplier)
     * @see #rotatedPillarBlock(ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #rotatedPillarBlock(ResourceLocation, ResourceLocation)
     * @see #cubeColumn(ResourceLocation, ResourceLocation)
     * @see #cubeColumn(ResourceLocation)
     * @see #cubeColumnHorizontal(ResourceLocation, ResourceLocation)
     * @see #cubeColumnHorizontal(ResourceLocation)
     */
    public static BlockStateDefinition rotatedPillarBlock(Supplier<Block> targetBlock, ResourceLocation baseModel) {
        return rotatedPillarBlock(targetBlock, baseModel, baseModel.withSuffix("_horizontal"));
    }

    /**
     * Overloaded variant of {@link #rotatedPillarBlock(Supplier, ResourceLocation)}. Creates a {@link BlockStateDefinition}, using {@link MultiVariantGenerator}
     * to update the supplied {@linkplain Block Block's} model based on its rotation across all 3 axis. Defaults the {@code baseModel} to the default location of the supplied {@linkplain Block Block's}
     * model ({@link ModelLocationUtils#getModelLocation(Block)}).
     * <p>
     * <h3>Variants / Properties</h3>
     * <ul>
     *  <li>{@link BlockStateProperties#AXIS} -> <ul>
     *      <li>{@link Direction.Axis#X} -> <ul>
     *          <li>{@link VariantProperties#MODEL} -> {@link ModelLocationUtils#getModelLocation(Block)} (Suffixed with {@code "_horizontal"})</li>
     *          <li>{@link VariantProperties#X_ROT} -> {@link VariantProperties.Rotation#R90}</li>
     *          <li>{@link VariantProperties#Y_ROT} -> {@link VariantProperties.Rotation#R90}</li>
     *      </ul></li>
     *      <li>{@link Direction.Axis#Y} -> <ul>
     *          <li>{@link VariantProperties#MODEL} -> {@link ModelLocationUtils#getModelLocation(Block)}</li>
     *      </ul></li>
     *      <li>{@link Direction.Axis#Z} -> <ul>
     *          <li>{@link VariantProperties#MODEL} -> {@link ModelLocationUtils#getModelLocation(Block)} (Suffixed with {@code "_horizontal"})</li>
     *          <li>{@link VariantProperties#X_ROT} -> {@link VariantProperties.Rotation#R90}</li>
     *      </ul></li>
     *  </ul></li>
     * </ul>
     *
     * @param targetBlock The {@linkplain Block Block} to use as the base for the {@link BlockStateDefinition} and the {@link ResourceLocation} of its cube-column variant models.
     *
     * @return A {@link BlockStateDefinition}, using {@link MultiVariantGenerator} to update the supplied {@linkplain Block Block's} model based on its rotation across all 3 axis,
     * and with the {@code horizontalModel} defaulted to {@code baseModel + {@code "_horizontal"}}, where {@code baseModel} defaults to {@link ModelLocationUtils#getModelLocation(Block)}.
     *
     * @see #rotatedPillarBlock(Supplier, ResourceLocation, ResourceLocation)
     * @see #rotatedPillarBlock(Supplier, ResourceLocation)
     * @see #rotatedPillarBlock(ResourceLocation, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #rotatedPillarBlock(ResourceLocation, ResourceLocation)
     * @see #cubeColumn(ResourceLocation, ResourceLocation)
     * @see #cubeColumn(ResourceLocation)
     * @see #cubeColumnHorizontal(ResourceLocation, ResourceLocation)
     * @see #cubeColumnHorizontal(ResourceLocation)
     */
    public static BlockStateDefinition rotatedPillarBlock(Supplier<Block> targetBlock) {
        return rotatedPillarBlock(targetBlock, ModelLocationUtils.getModelLocation(targetBlock.get()));
    }

    public static BlockModelDefinition doorBottomLeft(ResourceLocation doorTopTexture, ResourceLocation doorBottomTexture, ResourceLocation doorItemTexture) {
        return BlockModelDefinition.of(ModelTemplates.DOOR_BOTTOM_LEFT)
                .withTextureMapping(TextureMapping.door(doorTopTexture.withPrefix("block/"), doorBottomTexture.withPrefix("block/")))
                .withBlockRenderType(new ResourceLocation(RenderType.cutout().name))
                .withItemParentModelLoc(ModelTemplates.FLAT_ITEM)
                .withItemModelTextureMapping(TextureMapping.layer0(doorItemTexture.withPrefix("item/")));
    }

    public static BlockModelDefinition doorBottomLeftOpen(ResourceLocation doorTopTexture, ResourceLocation doorBottomTexture, ResourceLocation doorItemTexture) {
        return BlockModelDefinition.of(ModelTemplates.DOOR_BOTTOM_LEFT_OPEN)
                .withTextureMapping(TextureMapping.door(doorTopTexture.withPrefix("block/"), doorBottomTexture.withPrefix("block/")))
                .withBlockRenderType(new ResourceLocation(RenderType.cutout().name))
                .withItemParentModelLoc(ModelTemplates.FLAT_ITEM)
                .withItemModelTextureMapping(TextureMapping.layer0(doorItemTexture.withPrefix("item/")));
    }

    public static BlockModelDefinition doorTopLeft(ResourceLocation doorTopTexture, ResourceLocation doorBottomTexture, ResourceLocation doorItemTexture) {
        return BlockModelDefinition.of(ModelTemplates.DOOR_TOP_LEFT)
                .withTextureMapping(TextureMapping.door(doorTopTexture.withPrefix("block/"), doorBottomTexture.withPrefix("block/")))
                .withBlockRenderType(new ResourceLocation(RenderType.cutout().name))
                .withItemParentModelLoc(ModelTemplates.FLAT_ITEM)
                .withItemModelTextureMapping(TextureMapping.layer0(doorItemTexture.withPrefix("item/")));
    }

    public static BlockModelDefinition doorTopLeftOpen(ResourceLocation doorTopTexture, ResourceLocation doorBottomTexture, ResourceLocation doorItemTexture) {
        return BlockModelDefinition.of(ModelTemplates.DOOR_TOP_LEFT_OPEN)
                .withTextureMapping(TextureMapping.door(doorTopTexture.withPrefix("block/"), doorBottomTexture.withPrefix("block/")))
                .withBlockRenderType(new ResourceLocation(RenderType.cutout().name))
                .withItemParentModelLoc(ModelTemplates.FLAT_ITEM)
                .withItemModelTextureMapping(TextureMapping.layer0(doorItemTexture.withPrefix("item/")));
    }

    public static BlockModelDefinition doorBottomRight(ResourceLocation doorTopTexture, ResourceLocation doorBottomTexture, ResourceLocation doorItemTexture) {
        return BlockModelDefinition.of(ModelTemplates.DOOR_BOTTOM_RIGHT)
                .withTextureMapping(TextureMapping.door(doorTopTexture.withPrefix("block/"), doorBottomTexture.withPrefix("block/")))
                .withBlockRenderType(new ResourceLocation(RenderType.cutout().name))
                .withItemParentModelLoc(ModelTemplates.FLAT_ITEM)
                .withItemModelTextureMapping(TextureMapping.layer0(doorItemTexture.withPrefix("item/")));
    }

    public static BlockModelDefinition doorBottomRightOpen(ResourceLocation doorTopTexture, ResourceLocation doorBottomTexture, ResourceLocation doorItemTexture) {
        return BlockModelDefinition.of(ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN)
                .withTextureMapping(TextureMapping.door(doorTopTexture.withPrefix("block/"), doorBottomTexture.withPrefix("block/")))
                .withBlockRenderType(new ResourceLocation(RenderType.cutout().name))
                .withItemParentModelLoc(ModelTemplates.FLAT_ITEM)
                .withItemModelTextureMapping(TextureMapping.layer0(doorItemTexture.withPrefix("item/")));
    }

    public static BlockModelDefinition doorTopRight(ResourceLocation doorTopTexture, ResourceLocation doorBottomTexture, ResourceLocation doorItemTexture) {
        return BlockModelDefinition.of(ModelTemplates.DOOR_TOP_RIGHT)
                .withTextureMapping(TextureMapping.door(doorTopTexture.withPrefix("block/"), doorBottomTexture.withPrefix("block/")))
                .withBlockRenderType(new ResourceLocation(RenderType.cutout().name))
                .withItemParentModelLoc(ModelTemplates.FLAT_ITEM)
                .withItemModelTextureMapping(TextureMapping.layer0(doorItemTexture.withPrefix("item/")));
    }

    public static BlockModelDefinition doorTopRightOpen(ResourceLocation doorTopTexture, ResourceLocation doorBottomTexture, ResourceLocation doorItemTexture) {
        return BlockModelDefinition.of(ModelTemplates.DOOR_TOP_RIGHT_OPEN)
                .withTextureMapping(TextureMapping.door(doorTopTexture.withPrefix("block/"), doorBottomTexture.withPrefix("block/")))
                .withBlockRenderType(new ResourceLocation(RenderType.cutout().name))
                .withItemParentModelLoc(ModelTemplates.FLAT_ITEM)
                .withItemModelTextureMapping(TextureMapping.layer0(doorItemTexture.withPrefix("item/")));
    }

    public static ObjectArrayList<BlockModelDefinition> doorLeft(ResourceLocation dblTopTexture, ResourceLocation dblBottomTexture, ResourceLocation dbloTopTexture, ResourceLocation dbloBottomTexture, ResourceLocation dtlTopTexture, ResourceLocation dtlBottomTexture, ResourceLocation dtloTopTexture, ResourceLocation dtloBottomTexture, ResourceLocation doorItemTexture) {
        return ObjectArrayList.of(doorBottomLeft(dblTopTexture, dblBottomTexture, doorItemTexture), doorBottomLeftOpen(dbloTopTexture, dbloBottomTexture, doorItemTexture), doorTopLeft(dtlTopTexture, dtlBottomTexture, doorItemTexture), doorTopLeftOpen(dtloTopTexture, dtloBottomTexture, doorItemTexture));
    }

    public static ObjectArrayList<BlockModelDefinition> doorLeft(ResourceLocation dblTopTexture, ResourceLocation dblBottomTexture, ResourceLocation dtlTopTexture, ResourceLocation dtlBottomTexture, ResourceLocation doorItemTexture) {
        return doorLeft(dblTopTexture, dblBottomTexture, dblTopTexture, dblBottomTexture, dtlTopTexture, dtlBottomTexture, dtlTopTexture, dtlBottomTexture, doorItemTexture);
    }

    public static ObjectArrayList<BlockModelDefinition> doorLeft(ResourceLocation doorTopTexture, ResourceLocation doorBottomTexture, ResourceLocation doorItemTexture) {
        return doorLeft(doorTopTexture, doorBottomTexture, doorTopTexture, doorBottomTexture, doorItemTexture);
    }

    public static ObjectArrayList<BlockModelDefinition> doorRight(ResourceLocation dbrTopTexture, ResourceLocation dbrBottomTexture, ResourceLocation dbroTopTexture, ResourceLocation dbroBottomTexture, ResourceLocation dtrTopTexture, ResourceLocation dtrBottomTexture, ResourceLocation dtroTopTexture, ResourceLocation dtroBottomTexture, ResourceLocation doorItemTexture) {
        return ObjectArrayList.of(doorBottomRight(dbrTopTexture, dbrBottomTexture, doorItemTexture), doorBottomRightOpen(dbroTopTexture, dbroBottomTexture, doorItemTexture), doorTopRight(dtrTopTexture, dtrBottomTexture, doorItemTexture), doorTopRightOpen(dtroTopTexture, dtroBottomTexture, doorItemTexture));
    }

    public static ObjectArrayList<BlockModelDefinition> doorRight(ResourceLocation dbrTopTexture, ResourceLocation dbrBottomTexture, ResourceLocation dtrTopTexture, ResourceLocation dtrBottomTexture, ResourceLocation doorItemTexture) {
        return doorRight(dbrTopTexture, dbrBottomTexture, dbrTopTexture, dbrBottomTexture, dtrTopTexture, dtrBottomTexture, dtrTopTexture, dtrBottomTexture, doorItemTexture);
    }

    public static ObjectArrayList<BlockModelDefinition> doorRight(ResourceLocation doorTopTexture, ResourceLocation doorBottomTexture, ResourceLocation doorItemTexture) {
        return doorRight(doorTopTexture, doorBottomTexture, doorTopTexture, doorBottomTexture, doorItemTexture);
    }

    public static ObjectArrayList<BlockModelDefinition> door(ResourceLocation dblTopTexture, ResourceLocation dblBottomTexture, ResourceLocation dbloTopTexture, ResourceLocation dbloBottomTexture, ResourceLocation dtlTopTexture, ResourceLocation dtlBottomTexture, ResourceLocation dtloTopTexture, ResourceLocation dtloBottomTexture, ResourceLocation dbrTopTexture, ResourceLocation dbrBottomTexture, ResourceLocation dbroTopTexture, ResourceLocation dbroBottomTexture, ResourceLocation dtrTopTexture, ResourceLocation dtrBottomTexture, ResourceLocation dtroTopTexture, ResourceLocation dtroBottomTexture, ResourceLocation doorItemTexture) {
        return ObjectArrayList.wrap(Sets.union(Set.copyOf(doorLeft(dblTopTexture, dblBottomTexture, dbloTopTexture, dbloBottomTexture, dtlTopTexture, dtlBottomTexture, dtloTopTexture, dtloBottomTexture, doorItemTexture)), Set.copyOf(doorRight(dbrTopTexture, dbrBottomTexture, dbroTopTexture, dbroBottomTexture, dtrTopTexture, dtrBottomTexture, dtroTopTexture, dtroBottomTexture, doorItemTexture))).toArray(BlockModelDefinition[]::new));
    }

    public static ObjectArrayList<BlockModelDefinition> door(ResourceLocation doorTopTexture, ResourceLocation doorBottomTexture, ResourceLocation doorOpenTopTexture, ResourceLocation doorOpenBottomTexture, ResourceLocation doorItemTexture) {
        return door(doorTopTexture, doorBottomTexture, doorOpenTopTexture, doorOpenBottomTexture, doorTopTexture, doorBottomTexture, doorOpenTopTexture, doorOpenBottomTexture, doorTopTexture, doorBottomTexture, doorOpenTopTexture, doorOpenBottomTexture, doorTopTexture, doorBottomTexture, doorOpenTopTexture, doorOpenBottomTexture, doorItemTexture);
    }

    public static ObjectArrayList<BlockModelDefinition> door(ResourceLocation doorTopTexture, ResourceLocation doorBottomTexture, ResourceLocation doorItemTexture) {
        return door(doorTopTexture, doorBottomTexture, doorTopTexture, doorBottomTexture, doorItemTexture);
    }

    public static ObjectArrayList<BlockModelDefinition> door(ResourceLocation doorTopTexture, ResourceLocation doorBottomTexture) {
        return door(doorTopTexture, doorBottomTexture, doorTopTexture, doorBottomTexture, new ResourceLocation(doorTopTexture.getNamespace(), "block_items/" +
                (doorTopTexture.getPath().contains("_top")
                        ? StringUtils.substringBefore(doorTopTexture.getPath(), "_top")
                        : doorTopTexture.getPath())));
    }

    public static BlockStateDefinition door(Supplier<Block> targetBlock, ResourceLocation topLeftDoorModel, ResourceLocation topLeftDoorOpenModel, ResourceLocation bottomLeftDoorModel, ResourceLocation bottomLeftDoorOpenModel, ResourceLocation topRightDoorModel, ResourceLocation topRightDoorOpenModel, ResourceLocation bottomRightDoorModel, ResourceLocation bottomRightDoorOpenModel) {
        return BlockStateDefinition.of(targetBlock).withBlockStateSupplier(MultiVariantGenerator.multiVariant(targetBlock.get()).with(
                PropertyDispatch
                        .properties(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.DOUBLE_BLOCK_HALF, BlockStateProperties.DOOR_HINGE, BlockStateProperties.OPEN)
                        .select(Direction.EAST, DoubleBlockHalf.LOWER, DoorHingeSide.LEFT, false, Variant.variant()
                                .with(VariantProperties.MODEL, bottomLeftDoorModel))
                        .select(Direction.SOUTH, DoubleBlockHalf.LOWER, DoorHingeSide.LEFT, false, Variant.variant()
                                .with(VariantProperties.MODEL, bottomLeftDoorModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.WEST, DoubleBlockHalf.LOWER, DoorHingeSide.LEFT, false, Variant.variant()
                                .with(VariantProperties.MODEL, bottomLeftDoorModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.NORTH, DoubleBlockHalf.LOWER, DoorHingeSide.LEFT, false, Variant.variant()
                                .with(VariantProperties.MODEL, bottomLeftDoorModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                        .select(Direction.EAST, DoubleBlockHalf.LOWER, DoorHingeSide.RIGHT, false, Variant.variant()
                                .with(VariantProperties.MODEL, bottomRightDoorModel))
                        .select(Direction.SOUTH, DoubleBlockHalf.LOWER, DoorHingeSide.RIGHT, false, Variant.variant()
                                .with(VariantProperties.MODEL, bottomRightDoorModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.WEST, DoubleBlockHalf.LOWER, DoorHingeSide.RIGHT, false, Variant.variant()
                                .with(VariantProperties.MODEL, bottomRightDoorModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.NORTH, DoubleBlockHalf.LOWER, DoorHingeSide.RIGHT, false, Variant.variant()
                                .with(VariantProperties.MODEL, bottomRightDoorModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                        .select(Direction.EAST, DoubleBlockHalf.LOWER, DoorHingeSide.LEFT, true, Variant.variant()
                                .with(VariantProperties.MODEL, bottomLeftDoorOpenModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.SOUTH, DoubleBlockHalf.LOWER, DoorHingeSide.LEFT, true, Variant.variant()
                                .with(VariantProperties.MODEL, bottomLeftDoorOpenModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.WEST, DoubleBlockHalf.LOWER, DoorHingeSide.LEFT, true, Variant.variant()
                                .with(VariantProperties.MODEL, bottomLeftDoorOpenModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                        .select(Direction.NORTH, DoubleBlockHalf.LOWER, DoorHingeSide.LEFT, true, Variant.variant()
                                .with(VariantProperties.MODEL, bottomLeftDoorOpenModel))
                        .select(Direction.EAST, DoubleBlockHalf.LOWER, DoorHingeSide.RIGHT, true, Variant.variant()
                                .with(VariantProperties.MODEL, bottomRightDoorOpenModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                        .select(Direction.SOUTH, DoubleBlockHalf.LOWER, DoorHingeSide.RIGHT, true, Variant.variant()
                                .with(VariantProperties.MODEL, bottomRightDoorOpenModel))
                        .select(Direction.WEST, DoubleBlockHalf.LOWER, DoorHingeSide.RIGHT, true, Variant.variant()
                                .with(VariantProperties.MODEL, bottomRightDoorOpenModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.NORTH, DoubleBlockHalf.LOWER, DoorHingeSide.RIGHT, true, Variant.variant()
                                .with(VariantProperties.MODEL, bottomRightDoorOpenModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.EAST, DoubleBlockHalf.UPPER, DoorHingeSide.LEFT, false, Variant.variant()
                                .with(VariantProperties.MODEL, topLeftDoorModel))
                        .select(Direction.SOUTH, DoubleBlockHalf.UPPER, DoorHingeSide.LEFT, false, Variant.variant()
                                .with(VariantProperties.MODEL, topLeftDoorModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.WEST, DoubleBlockHalf.UPPER, DoorHingeSide.LEFT, false, Variant.variant()
                                .with(VariantProperties.MODEL, topLeftDoorModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.NORTH, DoubleBlockHalf.UPPER, DoorHingeSide.LEFT, false, Variant.variant()
                                .with(VariantProperties.MODEL, topLeftDoorModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                        .select(Direction.EAST, DoubleBlockHalf.UPPER, DoorHingeSide.RIGHT, false, Variant.variant()
                                .with(VariantProperties.MODEL, topRightDoorModel))
                        .select(Direction.SOUTH, DoubleBlockHalf.UPPER, DoorHingeSide.RIGHT, false, Variant.variant()
                                .with(VariantProperties.MODEL, topRightDoorModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.WEST, DoubleBlockHalf.UPPER, DoorHingeSide.RIGHT, false, Variant.variant()
                                .with(VariantProperties.MODEL, topRightDoorModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.NORTH, DoubleBlockHalf.UPPER, DoorHingeSide.RIGHT, false, Variant.variant()
                                .with(VariantProperties.MODEL, topRightDoorModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                        .select(Direction.EAST, DoubleBlockHalf.UPPER, DoorHingeSide.LEFT, true, Variant.variant()
                                .with(VariantProperties.MODEL, topLeftDoorOpenModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.SOUTH, DoubleBlockHalf.UPPER, DoorHingeSide.LEFT, true, Variant.variant()
                                .with(VariantProperties.MODEL, topLeftDoorOpenModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.WEST, DoubleBlockHalf.UPPER, DoorHingeSide.LEFT, true, Variant.variant()
                                .with(VariantProperties.MODEL, topLeftDoorOpenModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                        .select(Direction.NORTH, DoubleBlockHalf.UPPER, DoorHingeSide.LEFT, true, Variant.variant()
                                .with(VariantProperties.MODEL, topLeftDoorOpenModel))
                        .select(Direction.EAST, DoubleBlockHalf.UPPER, DoorHingeSide.RIGHT, true, Variant.variant()
                                .with(VariantProperties.MODEL, topRightDoorOpenModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                        .select(Direction.SOUTH, DoubleBlockHalf.UPPER, DoorHingeSide.RIGHT, true, Variant.variant()
                                .with(VariantProperties.MODEL, topRightDoorOpenModel))
                        .select(Direction.WEST, DoubleBlockHalf.UPPER, DoorHingeSide.RIGHT, true, Variant.variant()
                                .with(VariantProperties.MODEL, topRightDoorOpenModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.NORTH, DoubleBlockHalf.UPPER, DoorHingeSide.RIGHT, true, Variant.variant()
                                .with(VariantProperties.MODEL, topRightDoorOpenModel)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
        ));
    }

    public static BlockStateDefinition door(Supplier<Block> targetBlock, ResourceLocation topDoorModel, ResourceLocation bottomDoorModel) {
        return door(targetBlock, topDoorModel.withSuffix("_left"), topDoorModel.withSuffix("_left_open"), bottomDoorModel.withSuffix("_left"), bottomDoorModel.withSuffix("_left_open"), topDoorModel.withSuffix("_right"), topDoorModel.withSuffix("_right_open"), bottomDoorModel.withSuffix("_right"), bottomDoorModel.withSuffix("_right_open"));
    }

    public static BlockStateDefinition door(Supplier<Block> targetBlock) {
        return door(targetBlock, ModelLocationUtils.getModelLocation(targetBlock.get(), "_top"), ModelLocationUtils.getModelLocation(targetBlock.get(), "_bottom"));
    }
}
