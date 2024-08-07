package io.github.chaosawakens.util;

import io.github.chaosawakens.api.block.BlockModelDefinition;
import io.github.chaosawakens.api.block.BlockStateDefinition;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

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
     *  <li>{@link TextureSlot#ALL}</li>
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
}
