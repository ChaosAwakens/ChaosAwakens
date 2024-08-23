package io.github.chaosawakens.api.datagen.item;

import io.github.chaosawakens.api.datagen.block.BlockModelDefinition;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * A wrapper builder class primarily used to hold and group item model data for more convenient model definitions.
 */
public class ItemModelDefinition {
    @Nullable
    private final ModelTemplate parentModel;
    @Nullable
    private TextureMapping modelTextureMapping;
    private final Map<ItemDisplayContext, ItemTransform> itemModelTransforms = new Object2ObjectLinkedOpenHashMap<>();
    private final Map<Map<ResourceLocation, Float>, ResourceLocation> modelTextureOverrides = new Object2ObjectLinkedOpenHashMap<>();
    @Nullable
    private String customModelName;
    private boolean ambientOcclusion = true;
    @Nullable
    private BlockModel.GuiLight itemModelGuiLight;

    private ItemModelDefinition(@NotNull ModelTemplate parentModel) {
        this.parentModel = parentModel;
    }

    /**
     * Creates a new {@link ItemModelDefinition} with the given {@link ModelTemplate} as its parent model.
     *
     * @param parentModel The parent {@link ModelTemplate} from which base model data will be serialized during datagen. Do keep in mind that depending
     *                    on the template you choose, you may need to define additional properties (E.G. Additional mapped {@linkplain TextureSlot TextureSlots} via
     *                    {@link #withTextureMapping(TextureMapping)}).
     *
     * @return A new {@link BlockModelDefinition} with the given {@link ModelTemplate} as its parent model.
     *
     * @see #of()
     */
    public static ItemModelDefinition of(ModelTemplate parentModel) {
        return new ItemModelDefinition(parentModel);
    }

    /**
     * Overloaded variant of {@link #of(ModelTemplate)}. Creates a new {@link ItemModelDefinition} with no parent model. Useful for stuff like standard block models, which do not require parent model templates
     * most of the time.
     *
     * @return A new {@link BlockModelDefinition} with no model parent.
     *
     * @see #of(ModelTemplate)
     */
    public static ItemModelDefinition of() {
        return of(null);
    }

    /**
     * Sets the texture map, conforming to the parent {@linkplain ModelTemplate ModelTemplate's} required {@linkplain TextureSlot TextureSlots}.
     * Any additional slots that have nothing to do with the parent {@linkplain ModelTemplate ModelTemplate's} required {@linkplain TextureSlot TextureSlots}
     * will be ignored. However, any missing slot mappings (as per the parent {@linkplain ModelTemplate ModelTemplate's} required {@linkplain TextureSlot TextureSlots})
     * will end up throwing an {@link IllegalStateException}.
     *
     * @param itemModelTextureMapping The object representation of texture {@linkplain ResourceLocation ResourceLocations} to the required {@linkplain TextureSlot TextureSlots}.
     *
     * @return {@code this} (builder method)
     *
     * @see ModelTemplate#requiredSlots
     * @see TexturedModel
     * @see ModelTemplates
     * @see TextureSlot
     */
    public ItemModelDefinition withTextureMapping(TextureMapping itemModelTextureMapping) {
        this.modelTextureMapping = itemModelTextureMapping;
        return this;
    }

    /**
     * A mapped representation of model transformations applied to the item model based on its key {@link ItemDisplayContext}.
     *
     * @param itemModelTransforms The mapped item model transformations.
     *
     * @return {@code this} (builder method)
     */
    public ItemModelDefinition withItemModelTransforms(Map<ItemDisplayContext, ItemTransform> itemModelTransforms) {
        this.itemModelTransforms.putAll(itemModelTransforms);
        return this;
    }

    /**
     * Defines a custom {@link Map} of texture overrides for the item model. The key represents a {@link Map} of predicates (fulfilled based on the criteria of the value's... well, value)
     * per {@link ResourceLocation ResourceLocation} representing the item model to delegate to if said predicate(s) is/are fulfilled.
     *
     * @param textureOverrides The custom map of texture overrides for the item model.
     *
     * @return {@code this} (builder method)
     */
    public ItemModelDefinition withItemModelTextureOverrides(Map<Map<ResourceLocation, Float>, ResourceLocation> textureOverrides) {
        this.modelTextureOverrides.putAll(textureOverrides);
        return this;
    }

    /**
     * Defines a custom {@link BlockModel.GuiLight} to render this item model with. Primarily used for perspective aware/block models. Contrary to the name of the superclass in which GuiLight is nested,
     * this is applicable to item models, too.
     *
     * @param itemModelGuiLight The {@link BlockModel.GuiLight} to render this item model with. May be {@code null}.
     *
     * @return {@code this} (builder method)
     */
    public ItemModelDefinition withGuiLight(BlockModel.GuiLight itemModelGuiLight) {
        this.itemModelGuiLight = itemModelGuiLight;
        return this;
    }

    /**
     * Defines a custom model name with which the generated item model file based on this definition will be named. By default, model files generated through IMDs are named based on
     * the parent object's registry name (Whether it be {@link Block}, {@link Item}, etc.).
     *
     * @param customModelName The custom model name.
     *
     * @return {@code this} (builder method)
     */
    public ItemModelDefinition withCustomModelName(String customModelName) {
        this.customModelName = customModelName;
        return this;
    }

    /**
     * Whether the resulting item model should exude ambient occlusion.
     *
     * @param ambientOcclusion Whether the resulting item model should exude ambient occlusion.
     *
     * @return {@code this} (builder method)
     */
    public ItemModelDefinition withAmbientOcclusion(boolean ambientOcclusion) {
        this.ambientOcclusion = ambientOcclusion;
        return this;
    }

    /**
     * Gets the parent {@link ModelTemplate} from which base model data will be serialized during datagen.
     *
     * @return The parent {@link ModelTemplate}.
     *
     * @see ModelTemplates
     */
    public ModelTemplate getParentModel() {
        return parentModel;
    }

    /**
     * Gets this definition's {@link TextureMapping}. Should conform to the parent {@linkplain ModelTemplate ModelTemplate's} required {@linkplain TextureSlot TextureSlots}.
     *
     * @return This definition's {@link TextureMapping}.
     *
     * @see ModelTemplate#requiredSlots
     * @see TexturedModel
     * @see ModelTemplates
     * @see TextureSlot
     */
    @Nullable
    public TextureMapping getTextureMapping() {
        return modelTextureMapping;
    }

    /**
     * A mapped representation of model transformations applied to the item model based on its key {@link ItemDisplayContext}. May be empty.
     *
     * @return The mapped item model transformations.
     *
     * @see ItemTransforms
     */
    public Map<ItemDisplayContext, ItemTransform> getItemModelTransforms() {
        return itemModelTransforms;
    }

    /**
     * Gets the custom map of texture overrides for the item model. Texture overrides are applied based on the {@link Map} of predicate values they share.
     *
     * @return The texture overrides {@link Map}. Empty if left undefined.
     *
     * @see #withItemModelTextureOverrides(Map)
     */
    public Map<Map<ResourceLocation, Float>, ResourceLocation> getItemModelTextureOverrides() {
        return modelTextureOverrides;
    }

    /**
     * Returns the custom model name with which the generated item model file based on this definition will be named. May be {@code null} if left undefined.
     *
     * @return The custom model name, or {@code null} if left undefined.
     */
    @Nullable
    public String getCustomModelName() {
        return customModelName;
    }

    /**
     * Gets the {@link ResourceLocation} of the parent model/{@link ModelTemplate}, or {@link ModelTemplates#FLAT_ITEM} if the parent model is not explicitly defined. May be {@code null}.
     *
     * @return The {@link ResourceLocation} of the parent model/{@link ModelTemplate} || {@link ModelTemplates#FLAT_ITEM} if the parent model is not explicitly defined, or {@code null} if left undefined.
     */
    @Nullable
    public ResourceLocation getParentModelLocation() {
        return parentModel == null ? null : parentModel.model.orElseGet(ModelTemplates.FLAT_ITEM.model::get);
    }

    /**
     * Whether the resulting item model exudes ambient occlusion.
     *
     * @return Whether the resulting item model exudes ambient occlusion.
     */
    public boolean hasAmbientOcclusion() {
        return ambientOcclusion;
    }

    /**
     * Gets the {@link BlockModel.GuiLight} with which this item model is rendered. May be {@code null}.
     *
     * @return The stored {@link BlockModel.GuiLight}. May be {@code null}.
     */
    @Nullable
    public BlockModel.GuiLight getItemModelGuiLight() {
        return itemModelGuiLight;
    }
}
