package io.github.chaosawakens.datagen;

import com.google.common.base.Preconditions;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.datagen.block.BlockModelDefinition;
import io.github.chaosawakens.api.block.standard.BlockPropertyWrapper;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class CABlockModelProvider extends BlockModelProvider {
    public static final ResourceLocation ITEM_GENERATED = new ResourceLocation("item/generated");
    protected final Object2ObjectLinkedOpenHashMap<ResourceLocation, ItemModelBuilder> generatedBlockItemModels = new Object2ObjectLinkedOpenHashMap<>();
    protected final Function<ResourceLocation, ItemModelBuilder> itemModelFactory = targetLoc -> new ItemModelBuilder(targetLoc, existingFileHelper);

    public CABlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CAConstants.MODID, existingFileHelper);
    }

    @NotNull
    @Override
    public String getName() {
        return CAConstants.MOD_NAME.concat(": Block & Block Item Models");
    }

    @Override
    protected void registerModels() {
        if (!BlockPropertyWrapper.getMappedBwps().isEmpty()) {
            BlockPropertyWrapper.getMappedBwps().forEach((blockSupEntry, mappedBpw) -> {
                List<BlockModelDefinition> curModelDefs = mappedBpw.getModelDefinitions();

                if (!curModelDefs.isEmpty()) {
                    CAConstants.LOGGER.debug("[Setting Block & Item Model]: " + blockSupEntry.get().getDescriptionId());

                    curModelDefs.forEach(curModelDef -> {
                        ResourceLocation parentModelLoc = curModelDef.getParentModelLocation();
                        Block curBlock = blockSupEntry.get();
                        String formattedBlockRegName = ModelLocationUtils.getModelLocation(curBlock, curModelDef.getParentModel().suffix.orElse("")).getPath();
                        formattedBlockRegName = formattedBlockRegName.substring(formattedBlockRegName.lastIndexOf("/") + 1);
                        String customModelName = curModelDef.getCustomModelName();

                        if (curModelDef != null && parentModelLoc != null) { //TODO Add GuiLight & ElementBuilder support
                            ResourceLocation curBlockModelRenderTypeLoc = curModelDef.getBlockModelRenderType();
                            ResourceLocation itemParentModelLoc = curModelDef.getItemParentModelLocation();
                            ModelTemplate itemModelTextureMapping = curModelDef.getItemParentModel();
                            boolean curBMDHasAO = curModelDef.hasAmbientOcclusion();
                            Map<ItemDisplayContext, ItemTransform> itemTransforms = curModelDef.getItemModelTransforms();
                            String modelFileName = customModelName != null ? customModelName.toLowerCase(Locale.ROOT).trim().replaceAll("\\s+", "_") : formattedBlockRegName;
                            BlockModelBuilder resultBuilder = withExistingParent(modelFileName, parentModelLoc)
                                    .ao(curBMDHasAO); // Can't utilise ModelTemplate#create cuz then I'd also have to re-invent vanilla datagen here. No thanks. Much more customizable anyway :l

                            curModelDef.getParentModel().requiredSlots.forEach(requiredTexSlot -> {
                                String requiredTexSlotId = requiredTexSlot.getId();
                                ResourceLocation mappedTextureLocation = curModelDef.getTextureMapping().get(requiredTexSlot); // Explicit get() call for exception handling + readability

                                resultBuilder.texture(requiredTexSlotId, mappedTextureLocation);
                            });

                            if (curBlockModelRenderTypeLoc != null) resultBuilder.renderType(curBlockModelRenderTypeLoc);
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

                            if (!BlockModelDefinition.getCachedModelDefinitions().containsKey(blockSupEntry)) {
                                BlockModelDefinition.getCachedModelDefinitions().putIfAbsent(blockSupEntry, curModelDef); // JIC

                                String itemModelFileName = curBlock.getDescriptionId().substring(curBlock.getDescriptionId().lastIndexOf(".") + 1);
                                ResourceLocation customBlockModelItemParent = curModelDef.getCustomItemModelParent();
                                ItemModelBuilder resultItemBuilder = withExistingItemParent(itemModelFileName, itemModelTextureMapping == null ? customBlockModelItemParent != null ? customBlockModelItemParent : CAConstants.prefix(BLOCK_FOLDER + "/" + itemModelFileName) : itemParentModelLoc);
                                TextureMapping textureLayerDefinitionMap = curModelDef.getItemModelTextureMapping();
                                Map<Map<ResourceLocation, Float>, ResourceLocation> textureOverrides = curModelDef.getItemModelTextureOverrides();

                                if (itemModelTextureMapping != null && !itemParentModelLoc.equals(CAConstants.prefix(BLOCK_FOLDER + "/" + itemModelFileName))) {
                                    itemModelTextureMapping.requiredSlots.forEach(requiredTexSlot -> {
                                        String requiredTexSlotId = requiredTexSlot.getId();
                                        ResourceLocation mappedTextureLocation = textureLayerDefinitionMap.get(requiredTexSlot); // Explicit get() call for exception handling + readability

                                        resultItemBuilder.texture(requiredTexSlotId, mappedTextureLocation);
                                    });
                                }
                                if (!textureOverrides.isEmpty()) {
                                    textureOverrides.forEach((modelPredicates, resultingDelegateModel) -> {
                                        ItemModelBuilder.OverrideBuilder itemModelOverrides = resultItemBuilder.override();

                                        if (!modelPredicates.isEmpty()) modelPredicates.forEach(itemModelOverrides::predicate);
                                        itemModelOverrides.model(getExistingFile(resultingDelegateModel));

                                        itemModelOverrides.end();
                                    });
                                }
                            }
                        }
                    });
                }
            });
        }

    /*    ObjectArrayList<Supplier<Block>> distinctBlocks = Stream.concat(CABlocks.getBlocks().stream(), BlockModelDefinition.getCachedModelDefinitions().keySet().stream()) // At this point, BMDs have been mapped/cached
                .distinct()
                .collect(Collectors.toCollection(ObjectArrayList::new));

        distinctBlocks.forEach(blockSupEntry -> {
            Block blockEntry = blockSupEntry.get();
            String formattedBlockRegName = blockEntry.getDescriptionId().substring(blockEntry.getDescriptionId().lastIndexOf(".") + 1);

            if (blockEntry instanceof SlabBlock slabBlockEntry) {

            }

            if (blockEntry instanceof StairBlock stairBlockEntry) {

            }

            if (blockEntry instanceof RotatedPillarBlock rotatedPillarBlockEntry) {

            }

            if (blockEntry instanceof WallBlock wallBlockEntry) {

            }
        }); */ //TODO Figure out how to map texture locations such that we can get an actually consistent and useful algorithm instead of this yanderedev looking ahh code block
    }

    @Override
    protected CompletableFuture<?> generateAll(CachedOutput cache) {
        CompletableFuture<?>[] futures = new CompletableFuture<?>[this.generatedModels.size() + this.generatedBlockItemModels.size()];
        int i = 0;

        for (BlockModelBuilder model : this.generatedModels.values()) {
            Path target = getPath(model);
            futures[i++] = DataProvider.saveStable(cache, model.toJson(), target);
        }

        for (ItemModelBuilder model : this.generatedBlockItemModels.values()) {
            ResourceLocation loc = model.getLocation();
            Path target = this.output.getOutputFolder(PackOutput.Target.RESOURCE_PACK).resolve(loc.getNamespace()).resolve("models").resolve(loc.getPath() + ".json");
            futures[i++] = DataProvider.saveStable(cache, model.toJson(), target);
        }

        return CompletableFuture.allOf(futures);
    }

    @Override
    protected void clear() {
        super.clear();
        generatedBlockItemModels.clear();
    }

    protected ItemModelBuilder withExistingItemParent(String name, ResourceLocation parent) {
        return getItemBuilder(name).parent(getExistingFile(parent));
    }

    protected ItemModelBuilder getItemBuilder(String path) {
        Preconditions.checkNotNull(path, "Path must not be null");
        ResourceLocation outputLoc = extendWithFolder(path.contains(":") ? new ResourceLocation(path) : new ResourceLocation(modid, path));
        this.existingFileHelper.trackGenerated(outputLoc, MODEL);
        return generatedBlockItemModels.computeIfAbsent(outputLoc, itemModelFactory);
    }

    protected ResourceLocation extendWithFolder(ResourceLocation targetRl) {
        return targetRl.getPath().contains("/") ? targetRl : new ResourceLocation(targetRl.getNamespace(), ITEM_FOLDER + "/" + targetRl.getPath());
    }

    protected ResourceLocation chaosRL(String texture) {
        return CAConstants.prefix(BLOCK_FOLDER + "/" + texture);
    }

    protected ResourceLocation mcRL(String texture) {
        return new ResourceLocation(BLOCK_FOLDER + "/" + texture);
    }

    public void trapDoor(String name) {
        singleTexture(name + "_bottom", mcRL("template_orientable_trapdoor_bottom"), chaosRL(name));
        singleTexture(name + "_open", mcRL("template_orientable_trapdoor_open"), chaosRL(name));
        singleTexture(name + "_top", mcRL("template_orientable_trapdoor_top"), chaosRL(name));
    }

    public void leafCarpet(String name, ResourceLocation texture) {
        singleTexture(name, chaosRL("leaf_carpet"), "texture", texture);
    }

    public void leafCarpetInventory(String name, ResourceLocation texture) {
        singleTexture(name, chaosRL("leaf_carpet_inventory"), "texture", texture);
    }

    public BlockModelBuilder wired2SidedLeftBlock(String name, ResourceLocation targetTexture, ResourceLocation sideTexture) {
        return orientableWithBottom(name, sideTexture, targetTexture, chaosRL(targetTexture.getPath() + "_lb"), chaosRL(targetTexture.getPath() + "_lt"));
    }

    public BlockModelBuilder wired2SidedRightBlock(String name, ResourceLocation targetTexture, ResourceLocation sideTexture) {
        return orientableWithBottom(name, sideTexture, targetTexture, chaosRL(targetTexture.getPath() + "_rb"), chaosRL(targetTexture.getPath() + "_rt"));
    }

    public BlockModelBuilder wired3SidedLeftBlock(String name, ResourceLocation targetTexture, ResourceLocation sideTexture) {
        return orientableWithBottom(name, sideTexture, targetTexture, chaosRL(targetTexture.getPath() + "_bottom"), chaosRL(targetTexture.getPath() + "_top"))
                .texture("east", chaosRL(targetTexture.getPath() + "_right"))
                .texture("west", chaosRL(targetTexture.getPath() + "_left"));
    }

    public void gateBlock(String name, ResourceLocation top) {
        withExistingParent(name, BLOCK_FOLDER).texture("side", chaosRL(name)).texture("top", top).texture("bottom", top);
    }

    public void plant(String name, String texture) {
        cross(name, chaosRL(texture));
    }

    public void doublePlant(String name, String texture) {
        cross(name + "_top", chaosRL(texture + "_top"));
        cross(name + "_bottom", chaosRL(texture + "_bottom"));
    }

    public BlockModelBuilder farmland(String name, ResourceLocation dirt, ResourceLocation top) {
        return withExistingParent(name, BLOCK_FOLDER + "/template_farmland")
                .texture("dirt", dirt)
                .texture("top", top);
    }

    public BlockModelBuilder grassBlock(String name, ResourceLocation particle, ResourceLocation bottom, ResourceLocation top, ResourceLocation side, ResourceLocation overlay) {
        return withExistingParent(name, BLOCK_FOLDER + "/grass_block")
                .texture("particle", particle)
                .texture("bottom", bottom)
                .texture("top", top)
                .texture("side", side)
                .texture("overlay", overlay);
    }

    public void standardPaneBlock(String normalPaneName, ResourceLocation paneTexture, ResourceLocation edgeTexture) {
        panePost(normalPaneName + "_post", paneTexture, edgeTexture);
        paneNoSide(normalPaneName + "_noside", paneTexture);
        paneSide(normalPaneName + "_side", paneTexture, edgeTexture);
        paneNoSideAlt(normalPaneName + "_noside_alt", paneTexture);
        paneSideAlt(normalPaneName + "_side_alt", paneTexture, edgeTexture);
    }

    public void standardBarBlock(String normalBarName, ResourceLocation barTexture) {
        barCap(normalBarName + "_cap", barTexture);
        barCapAlt(normalBarName + "_cap_alt", barTexture);
        barPost(normalBarName + "_post", barTexture);
        barPostEnds(normalBarName + "_post_ends", barTexture);
        barSide(normalBarName + "_side", barTexture);
        barSideAlt(normalBarName + "_side_alt", barTexture);
    }

    public BlockModelBuilder barCap(String name, ResourceLocation barTexture) {
        return withExistingParent(name, BLOCK_FOLDER + "/iron_bars_cap")
                .texture("particle", barTexture)
                .texture("bars", barTexture)
                .texture("edge", barTexture);
    }

    public BlockModelBuilder barCapAlt(String name, ResourceLocation barTexture) {
        return withExistingParent(name, BLOCK_FOLDER + "/iron_bars_cap_alt")
                .texture("particle", barTexture)
                .texture("bars", barTexture)
                .texture("edge", barTexture);
    }

    public BlockModelBuilder barPost(String name, ResourceLocation barTexture) {
        return withExistingParent(name, BLOCK_FOLDER + "/iron_bars_post")
                .texture("particle", barTexture)
                .texture("bars", barTexture);
    }

    public BlockModelBuilder barPostEnds(String name, ResourceLocation barTexture) {
        return withExistingParent(name, BLOCK_FOLDER + "/iron_bars_post_ends")
                .texture("particle", barTexture)
                .texture("bars", barTexture)
                .texture("edge", barTexture);
    }

    public BlockModelBuilder barSide(String name, ResourceLocation barTexture) {
        return withExistingParent(name, BLOCK_FOLDER + "/iron_bars_side")
                .texture("particle", barTexture)
                .texture("bars", barTexture)
                .texture("edge", barTexture);
    }

    public BlockModelBuilder barSideAlt(String name, ResourceLocation barTexture) {
        return withExistingParent(name, BLOCK_FOLDER + "/iron_bars_side_alt")
                .texture("particle", barTexture)
                .texture("bars", barTexture)
                .texture("edge", barTexture);
    }

    public BlockModelBuilder tintedCross(String name, ResourceLocation cross) {
        return withExistingParent(name, BLOCK_FOLDER + "/tinted_cross")
                .texture("cross", cross);
    }

    public void doubleTintedCross(String name, String texture) {
        tintedCross(name + "_top", chaosRL(texture + "_top"));
        tintedCross(name + "_bottom", chaosRL(texture + "_bottom"));
    }

    public BlockModelBuilder buttonInventory(String name, ResourceLocation texture) {
        return withExistingParent(name, BLOCK_FOLDER + "/button_inventory")
                .texture("texture", texture);
    }

    public BlockModelBuilder pottedCross(String name, ResourceLocation texture) {
        return withExistingParent(name, BLOCK_FOLDER + "/flower_pot_cross")
                .texture("plant", texture);
    }

    @Override
    public BlockModelBuilder cubeColumn(String name, ResourceLocation side, ResourceLocation end) {
        return withExistingParent(name, BLOCK_FOLDER).texture("side", side).texture("end", end);
    }

    @Override
    public BlockModelBuilder cubeColumnHorizontal(String name, ResourceLocation side, ResourceLocation end) {
        return withExistingParent(name, BLOCK_FOLDER).texture("side", side).texture("end", end);
    }
}
