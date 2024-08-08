package io.github.chaosawakens.util;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.block.BlockModelDefinition;
import io.github.chaosawakens.api.block.BlockStateDefinition;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;

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
     *
     * <h3>Required Texture Slots</h3>
     * <ul>
     *  <li>{@link TextureSlot#ALL} -> {@code sixSideBlockTexture}</li>
     * </ul>
     *
     * @param sixSideBlockTexture The {@link ResourceLocation} representing the texture of every side of a standard cube block.
     *                            Points to the {@code "block/"} path by default.
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
     *
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
     */
    public static BlockStateDefinition simpleBlock(Supplier<Block> targetBlock) {
        return BlockStateDefinition.of(targetBlock)
                .withBlockStateSupplier(MultiVariantGenerator.multiVariant(targetBlock.get(), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(targetBlock.get()))));
    }

    /**
     * Creates a {@link BlockModelDefinition} with the {@link ModelTemplates#CUBE_BOTTOM_TOP} template.
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
     * <h3>Variants / Properties</h3>
     * <ul>
     *  <li>{@link BlockStateProperties#SLAB_TYPE} -> <ul>
     *      <li>{@link SlabType#BOTTOM} -> {@link VariantProperties#MODEL} -> {@code bottomModel}</li>
     *      <li>{@link SlabType#TOP} -> {@link VariantProperties#MODEL} -> {@code topModel}</li>
     *      <li>{@link SlabType#DOUBLE} -> {@link VariantProperties#MODEL} -> {@code doubleSlabModel}</li>
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
     * {@link ResourceLocation} of every required {@link TextureSlot} in a standard slab. The double-slab model variant is based on the provided {@code doubleSlabModel}
     *
     * <h3>Variants / Properties</h3>
     * <ul>
     *  <li>{@link BlockStateProperties#SLAB_TYPE} -> <ul>
     *      <li>{@link SlabType#BOTTOM} -> {@link VariantProperties#MODEL} -> {@link ModelLocationUtils#getModelLocation(Block)}</li>
     *      <li>{@link SlabType#TOP} -> {@link VariantProperties#MODEL} -> {@link ModelLocationUtils#getModelLocation(Block, String)} (Suffixed with {@code "_top"})</li>
     *      <li>{@link SlabType#DOUBLE} -> {@link VariantProperties#MODEL} -> {@code doubleBlockModel}</li>
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
     * @see #woodSlab(Supplier)
     * @see SlabType
     */
    public static BlockStateDefinition slab(Supplier<Block> targetBlock, ResourceLocation doubleBlockModel) {
        return slab(targetBlock, ModelLocationUtils.getModelLocation(targetBlock.get(), "_top"), ModelLocationUtils.getModelLocation(targetBlock.get()), doubleBlockModel);
    }

    /**
     * Overloaded variant of {@link #slab(Supplier, ResourceLocation)}, utilising the supplied {@link Block} as the base for deciding the
     * {@link ResourceLocation} of every required {@link TextureSlot} in a standard slab. Also defaults the double-slab model to reference the plank variant of the supplied {@link Block},
     * assuming that it exists (Will likely throw an exception otherwise). Should typically be used for standard wood sets, particularly slabs (duh).
     *
     * <h3>Variants / Properties</h3>
     * <ul>
     *  <li>{@link BlockStateProperties#SLAB_TYPE} -> <ul>
     *      <li>{@link SlabType#BOTTOM} -> {@link VariantProperties#MODEL} -> {@link ModelLocationUtils#getModelLocation(Block)}</li>
     *      <li>{@link SlabType#TOP} -> {@link VariantProperties#MODEL} -> {@link ModelLocationUtils#getModelLocation(Block, String)} (Suffixed with {@code "_top"})</li>
     *      <li>{@link SlabType#DOUBLE} -> {@link VariantProperties#MODEL} -> {@link ModelLocationUtils#getModelLocation(Block)} + {@code replaceAll("_slab", "_planks")}</li>
     *  </ul></li>
     * </ul>
     *
     * @param targetBlock The {@link Block} used as the base for determining the {@link ResourceLocation} of every required {@link TextureSlot} in a standard slab.
     *
     * @return A {@link BlockStateDefinition} with the {@link MultiVariantGenerator} with the {@link VariantProperties#MODEL} property set to the required {@linkplain ResourceLocation ResourceLocations}
     * based on the required {@linkplain TextureSlot TextureSlots} in a standard slab, utilising the supplied {@link Block} as the base.
     *
     * @see #slab(Supplier, ResourceLocation, ResourceLocation, ResourceLocation)
     * @see #slab(Supplier, ResourceLocation)
     * @see SlabType
     */
    public static BlockStateDefinition woodSlab(Supplier<Block> targetBlock) {
        return slab(targetBlock, CAConstants.prefix(ModelLocationUtils.getModelLocation(targetBlock.get()).getPath().replaceAll("_slab", "_planks")));
    }
}
