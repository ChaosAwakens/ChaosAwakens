package io.github.chaosawakens.datagen;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.datagen.item.ItemModelDefinition;
import io.github.chaosawakens.api.item.ItemPropertyWrapper;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CAItemModelProvider extends ItemModelProvider {

    public CAItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CAConstants.MODID, existingFileHelper);
    }

    @NotNull
    @Override
    public String getName() {
        return CAConstants.MOD_NAME.concat(": Item Models");
    }

    @Override
    protected void registerModels() {
        registerItemModels();
    }

    protected void registerItemModels() {
        if (!ItemPropertyWrapper.getMappedIpws().isEmpty()) {
            ItemPropertyWrapper.getMappedIpws().forEach((itemSupEntry, mappedIpw) -> {
                List<ItemModelDefinition> curModelDefs = mappedIpw.getItemModelDefinitions();

                if (!curModelDefs.isEmpty()) {
                    CAConstants.LOGGER.debug("[Setting Item Model]: " + itemSupEntry.get().getDescriptionId());

                    curModelDefs.forEach(curModelDef -> {
                        ResourceLocation parentModelLoc = curModelDef.getParentModelLocation();
                        Item curItem = itemSupEntry.get();
                        String formattedItemRegName = ModelLocationUtils.getModelLocation(curItem, curModelDef.getParentModel().suffix.orElse("")).getPath();
                        formattedItemRegName = formattedItemRegName.substring(formattedItemRegName.lastIndexOf("/") + 1); // Avoid stacking calls to avoid dumb concurrency issues with string concat/building (how)
                        String customModelName = curModelDef.getCustomModelName();

                        if (curModelDef != null && parentModelLoc != null) {
                            ModelTemplate parentModel = curModelDef.getParentModel();
                            boolean curBMDHasAO = curModelDef.hasAmbientOcclusion();
                            Map<ItemDisplayContext, ItemTransform> itemTransforms = curModelDef.getItemModelTransforms();
                            Map<Map<ResourceLocation, Float>, ResourceLocation> textureOverrides = curModelDef.getItemModelTextureOverrides();
                            BlockModel.GuiLight targetGuiLight = curModelDef.getItemModelGuiLight();
                            String modelFileName = customModelName != null ? customModelName.toLowerCase(Locale.ROOT).trim().replaceAll("\\s+", "_") : formattedItemRegName;
                            ItemModelBuilder resultBuilder = withExistingParent(modelFileName, parentModelLoc)
                                    .ao(curBMDHasAO);

                            if (parentModel.requiredSlots != null && !parentModel.requiredSlots.isEmpty()) { // JIC
                                parentModel.requiredSlots.forEach(requiredTexSlot -> {
                                    String requiredTexSlotId = requiredTexSlot.getId();
                                    ResourceLocation mappedTextureLocation = curModelDef.getTextureMapping().get(requiredTexSlot); // Explicit get() call for exception handling + readability

                                    resultBuilder.texture(requiredTexSlotId, mappedTextureLocation);
                                });
                            }

                            if (targetGuiLight != null) resultBuilder.guiLight(targetGuiLight);
                            if (!itemTransforms.isEmpty()) {
                                itemTransforms.forEach((curDisplayCtx, curItemTransform) -> resultBuilder.transforms()
                                        .transform(curDisplayCtx)
                                        .leftRotation(curItemTransform.rotation.x, curItemTransform.rotation.y, curItemTransform.rotation.z)
                                        .rightRotation(curItemTransform.rightRotation.x, curItemTransform.rightRotation.y, curItemTransform.rightRotation.z)
                                        .scale(curItemTransform.scale.x, curItemTransform.scale.y, curItemTransform.scale.z)
                                        .translation(curItemTransform.translation.x, curItemTransform.translation.y, curItemTransform.translation.z)
                                        .end()
                                        .end());
                            }

                            if (!textureOverrides.isEmpty()) {
                                textureOverrides.forEach((modelPredicates, resultingDelegateModel) -> {
                                    ItemModelBuilder.OverrideBuilder itemModelOverrides = resultBuilder.override();

                                    if (!modelPredicates.isEmpty()) modelPredicates.forEach(itemModelOverrides::predicate);
                                    itemModelOverrides.model(getExistingFile(resultingDelegateModel));

                                    itemModelOverrides.end();
                                });
                            }
                        }
                    });
                }
            });
        }
    }
}
