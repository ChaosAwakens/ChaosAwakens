package io.github.chaosawakens.api.block;

import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * A wrapper builder class primarily used to hold and group block model data for more convenient model definitions.
 */
public class BlockModelDefinition {
    private final ModelTemplate parentModel;
    @Nullable
    private BlockStateGenerator backingStateDefinition;
    private boolean ambientOcclusion = true;
    @Nullable
    private ItemTransform itemModelTransforms;
    @Nullable
    private ResourceLocation blockRenderType;
    @Nullable
    private TextureMapping blockModelTextureMapping;

    private BlockModelDefinition(ModelTemplate parentModel) {
        this.parentModel = parentModel;
    }

    /**
     * Creates a new {@link BlockModelDefinition} with the given {@link ModelTemplate} as its parent model. Properties can be
     * defined using {@link #withBackingStateDefinition(BlockStateGenerator)}.
     *
     * @param parentModel The parent {@link ModelTemplate} from which base model data will be serialized during datagen. Do keep in mind that depending
     *                    on the template you choose, you may need to define additional properties (E.G. Additional mapped {@linkplain TextureSlot TextureSlots} via
     *                    {@link #withTextureMapping(TextureMapping)}).
     *
     * @return A new {@link BlockModelDefinition} with the given {@link ModelTemplate} as its parent model.
     */
    public static BlockModelDefinition of(ModelTemplate parentModel) {
        return new BlockModelDefinition(parentModel);
    }

    public BlockModelDefinition withBackingStateDefinition(BlockStateGenerator backingStateDefinition) {
        this.backingStateDefinition = backingStateDefinition;
        return this;
    }

    public BlockModelDefinition withAmbientOcclusion(boolean ambientOcclusion) {
        this.ambientOcclusion = ambientOcclusion;
        return this;
    }

    public BlockModelDefinition withItemModelTransforms(ItemTransform itemModelTransforms) {
        this.itemModelTransforms = itemModelTransforms;
        return this;
    }

    public BlockModelDefinition withBlockRenderType(ResourceLocation blockRenderType) {
        this.blockRenderType = blockRenderType;
        return this;
    }

    public BlockModelDefinition withTextureMapping(TextureMapping blockModelTextureMapping) {
        this.blockModelTextureMapping = blockModelTextureMapping;
        return this;
    }

    public ModelTemplate getParentModel() {
        return parentModel;
    }

    @Nullable
    public BlockStateGenerator getBackingStateDefinition() {
        return backingStateDefinition;
    }

    public boolean hasAmbientOcclusion() {
        return ambientOcclusion;
    }

    @Nullable
    public ItemTransform getItemModelTransform() {
        return itemModelTransforms;
    }

    @Nullable
    public ResourceLocation getBlockModelRenderType() {
        return blockRenderType;
    }

    @Nullable
    public TextureMapping getTextureMapping() {
        return blockModelTextureMapping;
    }

    public ResourceLocation getParentModelLocation() {
        return parentModel.model.orElseGet(ModelTemplates.CUBE.model::get);
    }
}
