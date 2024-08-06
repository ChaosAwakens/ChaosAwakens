package io.github.chaosawakens.util;

import io.github.chaosawakens.api.block.BlockModelDefinition;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

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
     */
    public static BlockModelDefinition cubeAll(ResourceLocation sixSideBlockTexture) {
        return BlockModelDefinition.of(ModelTemplates.CUBE_ALL)
                .withTextureMapping(TextureMapping.cube(sixSideBlockTexture.withPrefix("block/")));
    }
}
