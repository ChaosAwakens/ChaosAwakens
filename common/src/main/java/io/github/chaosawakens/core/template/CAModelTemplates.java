package io.github.chaosawakens.core.template;

import com.mememan.nexus.client.block.BlockStateDefinition;
import com.mememan.nexus.client.model.block.BlockModelDefinition;
import com.mememan.nexus.client.model.item.ItemModelDefinition;
import com.mememan.nexus.property_wrapper.base.generic.DataGenPropertyWrapper;
import com.mememan.nexus.util.ModelUtil;
import com.mememan.nexus.util.RegistryUtil;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.content.block.vegetation.FruitableLeavesBlock;
import io.github.chaosawakens.content.block.vegetation.generic.CropInstance;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Direction;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import javax.print.DocFlavor;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CAModelTemplates {
    public static final ModelTemplate LEAF_CARPET = new ModelTemplate(Optional.of(CAConstants.prefix("block/leaf_carpet")), Optional.empty(), TextureSlot.TEXTURE);
    public static final ModelTemplate LEAF_CARPET_INVENTORY = new ModelTemplate(Optional.of(CAConstants.prefix("block/leaf_carpet_inventory")), Optional.of("_inventory"), TextureSlot.TEXTURE);
    public static final ModelTemplate HANDHELD_LONG = new ModelTemplate(Optional.of(CAConstants.prefix("item/handheld_long")), Optional.empty(), TextureSlot.LAYER0);
    public static final ModelTemplate ULTIMATE_CROSSBOW = new ModelTemplate(Optional.of(CAConstants.prefix("item/ultimate_crossbow")), Optional.empty(), TextureSlot.LAYER0);

    private CAModelTemplates() {
        throw new IllegalAccessError("Attempted to construct instance of template class! (CAModelTemplates)");
    }

    public static ItemModelDefinition crossbow(ResourceLocation crossbowid) {
        String prefix = "item/equipment/ultimate/";
        return new ItemModelDefinition(ULTIMATE_CROSSBOW)
                .withTextureMapping(TextureMapping.layer0(crossbowid.withPrefix(prefix).withSuffix("_standby")))
                .setItemModelTextureOverrides(Map.of(
                        Map.of(new ResourceLocation("pulling"), 1.0F),
                        crossbowid.withPrefix(prefix).withSuffix("_pulling_0"),

                        Map.of(new ResourceLocation("pulling"), 1.0F, new ResourceLocation("pull"), 0.58F),
                        crossbowid.withPrefix(prefix).withSuffix("_pulling_1"),

                        Map.of(new ResourceLocation("pulling"), 1.0F, new ResourceLocation("pull"), 1.0F),
                        crossbowid.withPrefix(prefix).withSuffix("_pulling_2"),

                        Map.of(new ResourceLocation("charged"), 1.0F),
                        crossbowid.withPrefix(prefix).withSuffix("_arrow"),

                        Map.of(new ResourceLocation("charged"), 1.0F,new  ResourceLocation("firework"), 1.0F),
                        crossbowid.withPrefix(prefix).withSuffix("_firework")
                ));
    }

    public static ItemModelDefinition crossbow(Supplier<Item> parentItem) {
        return crossbow(DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(parentItem.get()));
    }

    public static BlockModelDefinition leafCarpetDefault(ResourceLocation leafCarpetTextureLoc) {
        return new BlockModelDefinition(LEAF_CARPET)
                .withRenderType(ModelUtil.CUTOUT_MIPPED_RENDER_TYPE)
                .withTextureMapping(TextureMapping.defaultTexture(RegistryUtil.pickBlockPrefix(leafCarpetTextureLoc)));
    }

    public static BlockModelDefinition leafCarpetDefault(Supplier<Block> targetBlock) {
        return leafCarpetDefault(RegistryUtil.getTextureLocationOrDefault(
                targetBlock,
                RegistryUtil.getTextureLocationOrDefault(DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get())
                        .withPath(curPath -> curPath.replace("_leaf_carpet", "_leaves")))));
    }

    public static BlockModelDefinition leafCarpetInventory(Supplier<Block> targetBlock, ResourceLocation leafCarpetItemTextureLoc) {
        return new BlockModelDefinition(LEAF_CARPET_INVENTORY)
                .withRenderType(ModelUtil.CUTOUT_MIPPED_RENDER_TYPE)
                .withTextureMapping(TextureMapping.defaultTexture(RegistryUtil.pickBlockPrefix(leafCarpetItemTextureLoc)))
                .withOrdinalModelDefinition(new ItemModelDefinition(ModelUtil.fromLocation(ModelLocationUtils.getModelLocation(targetBlock.get(), "_inventory"))));
    }

    public static BlockModelDefinition leafCarpetInventoryDefault(Supplier<Block> targetBlock) {
        return leafCarpetInventory(targetBlock, RegistryUtil.getTextureLocationOrDefault(
                targetBlock,
                RegistryUtil.getTextureLocationOrDefault(DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get())
                        .withPath(curPath -> curPath.replace("_leaf_carpet", "_leaves")))));
    }

    public static BlockModelDefinition leafCarpet(Supplier<Block> targetBlock, ResourceLocation leafCarpetTextureLoc, ResourceLocation leafCarpetItemTextureLoc) {
        return leafCarpetDefault(leafCarpetTextureLoc)
                .withOrdinalModelDefinition(leafCarpetInventory(targetBlock, leafCarpetItemTextureLoc));
    }

    public static BlockModelDefinition leafCarpet(Supplier<Block> targetBlock, ResourceLocation leafCarpetTextureLoc) {
        return leafCarpet(targetBlock, leafCarpetTextureLoc, leafCarpetTextureLoc);
    }

    public static BlockModelDefinition leafCarpet(Supplier<Block> targetBlock) {
        return leafCarpet(targetBlock, RegistryUtil.getTextureLocationOrDefault(
                targetBlock,
                RegistryUtil.getTextureLocationOrDefault(DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get())
                        .withPath(curPath -> curPath.replace("_leaf_carpet", "_leaves")),
                        "block",
                        RegistryUtil.getTextureLocationOrDefault(DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get())
                                .withPath(curPath -> curPath.replace("_carpet", "")), "block", RegistryUtil.getTextureLocationOrDefault(
                                        new ResourceLocation(DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get()).getPath().replace("_leaf_carpet", "_leaves")),
                                "block", RegistryUtil.getTextureLocationOrDefault(
                                        new ResourceLocation(DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get()).getPath().replace("_carpet", "")),
                                        "block"
                                )
                        ))
                )));
    }

    public static BlockStateDefinition leafCarpetBlockState(Supplier<Block> targetBlock) {
        ResourceLocation targetModelLoc = ModelLocationUtils.getModelLocation(targetBlock.get());
        return new BlockStateDefinition(targetBlock)
                .withBlockStateSupplier(MultiPartGenerator.multiPart(targetBlock.get())
                        .with(Condition.condition()
                                .term(PipeBlock.NORTH, true), Variant.variant()
                                .with(VariantProperties.MODEL, targetModelLoc))
                        .with(Condition.condition()
                                .term(PipeBlock.DOWN, false)
                                .term(PipeBlock.NORTH, false)
                                .term(PipeBlock.EAST, false)
                                .term(PipeBlock.SOUTH, false)
                                .term(PipeBlock.UP, false)
                                .term(PipeBlock.WEST, false), Variant.variant()
                                .with(VariantProperties.MODEL, targetModelLoc))
                        .with(Condition.condition()
                                .term(PipeBlock.EAST, true), Variant.variant()
                                .with(VariantProperties.MODEL, targetModelLoc)
                                .with(VariantProperties.UV_LOCK, true)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .with(Condition.condition()
                                .term(PipeBlock.DOWN, false)
                                .term(PipeBlock.NORTH, false)
                                .term(PipeBlock.EAST, false)
                                .term(PipeBlock.SOUTH, false)
                                .term(PipeBlock.UP, false)
                                .term(PipeBlock.WEST, false), Variant.variant()
                                .with(VariantProperties.MODEL, targetModelLoc)
                                .with(VariantProperties.UV_LOCK, true)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .with(Condition.condition()
                                .term(PipeBlock.SOUTH, true), Variant.variant()
                                .with(VariantProperties.MODEL, targetModelLoc)
                                .with(VariantProperties.UV_LOCK, true)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .with(Condition.condition()
                                .term(PipeBlock.DOWN, false)
                                .term(PipeBlock.NORTH, false)
                                .term(PipeBlock.EAST, false)
                                .term(PipeBlock.SOUTH, false)
                                .term(PipeBlock.UP, false)
                                .term(PipeBlock.WEST, false), Variant.variant()
                                .with(VariantProperties.MODEL, targetModelLoc)
                                .with(VariantProperties.UV_LOCK, true)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .with(Condition.condition()
                                .term(PipeBlock.WEST, true), Variant.variant()
                                .with(VariantProperties.MODEL, targetModelLoc)
                                .with(VariantProperties.UV_LOCK, true)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                        .with(Condition.condition()
                                .term(PipeBlock.DOWN, false)
                                .term(PipeBlock.NORTH, false)
                                .term(PipeBlock.EAST, false)
                                .term(PipeBlock.SOUTH, false)
                                .term(PipeBlock.UP, false)
                                .term(PipeBlock.WEST, false), Variant.variant()
                                .with(VariantProperties.MODEL, targetModelLoc)
                                .with(VariantProperties.UV_LOCK, true)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                        .with(Condition.condition()
                                .term(PipeBlock.UP, true), Variant.variant()
                                .with(VariantProperties.MODEL, targetModelLoc)
                                .with(VariantProperties.UV_LOCK, true)
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270))
                        .with(Condition.condition()
                                .term(PipeBlock.DOWN, false)
                                .term(PipeBlock.NORTH, false)
                                .term(PipeBlock.EAST, false)
                                .term(PipeBlock.SOUTH, false)
                                .term(PipeBlock.UP, false)
                                .term(PipeBlock.WEST, false), Variant.variant()
                                .with(VariantProperties.MODEL, targetModelLoc)
                                .with(VariantProperties.UV_LOCK, true)
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270))
                        .with(Condition.condition()
                                .term(PipeBlock.DOWN, true), Variant.variant()
                                .with(VariantProperties.MODEL, targetModelLoc)
                                .with(VariantProperties.UV_LOCK, true)
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                        .with(Condition.condition()
                                .term(PipeBlock.DOWN, false)
                                .term(PipeBlock.NORTH, false)
                                .term(PipeBlock.EAST, false)
                                .term(PipeBlock.SOUTH, false)
                                .term(PipeBlock.UP, false)
                                .term(PipeBlock.WEST, false), Variant.variant()
                                .with(VariantProperties.MODEL, targetModelLoc)
                                .with(VariantProperties.UV_LOCK, true)
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                );
    }

    public static BlockModelDefinition fruitableLeaves(Supplier<Block> targetBlock, ResourceLocation baseLeavesTextureLoc, ResourceLocation ripeLeavesTextureLoc) {
        return ModelUtil.leaves(targetBlock, baseLeavesTextureLoc)
                .withOrdinalModelDefinition(ModelUtil.leaves(targetBlock, ripeLeavesTextureLoc)
                        .withCustomName(DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get()).withSuffix("_ripe").getPath())
                        .setOrdinalModelDefinitions(ObjectArrayList.of()));
    }

    public static BlockModelDefinition fruitableLeaves(Supplier<Block> targetBlock) {
        return fruitableLeaves(targetBlock, RegistryUtil.getTextureLocationOrDefault(targetBlock),
                RegistryUtil.getTextureLocationOrDefault(
                        DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get())
                                .withSuffix("_ripe")
                )
        );
    }

    public static BlockStateDefinition fruitableLeavesBlockState(Supplier<Block> targetBlock, ResourceLocation unripeLeavesModel, ResourceLocation ripeLeavesModel) {
        return new BlockStateDefinition(targetBlock)
                .withBlockStateSupplier(MultiVariantGenerator.multiVariant(targetBlock.get())
                        .with(PropertyDispatch
                                .property(FruitableLeavesBlock.RIPE)
                                .select(true, Variant.variant()
                                        .with(VariantProperties.MODEL, ripeLeavesModel))
                                .select(false, Variant.variant()
                                        .with(VariantProperties.MODEL, unripeLeavesModel))));
    }

    public static BlockStateDefinition fruitableLeavesBlockState(Supplier<Block> targetBlock) {
        return fruitableLeavesBlockState(targetBlock, ModelLocationUtils.getModelLocation(targetBlock.get()), ModelLocationUtils.getModelLocation(targetBlock.get()).withSuffix("_ripe"));
    }

    public static BlockModelDefinition roboSlab(Supplier<Block> targetBlock) {
        ResourceLocation defaultedTexLoc = RegistryUtil.getTextureLocationOrDefault(RegistryUtil.pickBlockId(targetBlock), RegistryUtil.getTextureLocationOrDefault(DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get()).withPath(curPath -> curPath.replace("_slab", "_block"))));

        return ModelUtil.slab(targetBlock, defaultedTexLoc, defaultedTexLoc, defaultedTexLoc);
    }

    public static BlockStateDefinition roboSlabBlockState(Supplier<Block> targetBlock) {
        ResourceLocation defaultedTexLoc = RegistryUtil.getTextureLocationOrDefault(RegistryUtil.pickBlockId(targetBlock), RegistryUtil.getTextureLocationOrDefault(DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get()).withPath(curPath -> curPath.replace("_slab", "_block"))));

        if (defaultedTexLoc.getPath().contains("/")) defaultedTexLoc = defaultedTexLoc.withPath(curPath -> curPath.substring(curPath.lastIndexOf('/') + 1)).withPrefix("block/");

        return ModelUtil.slabBlockState(targetBlock, defaultedTexLoc);
    }

    public static BlockModelDefinition topSideRoboSlab(Supplier<Block> targetBlock) {
        ResourceLocation defaultedSideTexLoc = RegistryUtil.getTextureLocationOrDefault(RegistryUtil.pickBlockId(targetBlock), RegistryUtil.getTextureLocationOrDefault(DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get()).withPath(curPath -> curPath.replace("_slab", "_block"))));
        ResourceLocation defaultedTopBottomTexLoc = RegistryUtil.getTextureLocationOrDefault(RegistryUtil.pickBlockId(targetBlock), RegistryUtil.getTextureLocationOrDefault(DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get()).withPath(curPath -> curPath.replace("_slab", "_block")).withSuffix("_top")));

        return ModelUtil.slab(targetBlock, defaultedTopBottomTexLoc, defaultedTopBottomTexLoc, defaultedSideTexLoc);
    }

    public static BlockModelDefinition roboStairs(Supplier<Block> targetBlock) {
        ResourceLocation defaultedTexLoc = RegistryUtil.getTextureLocationOrDefault(RegistryUtil.pickBlockId(targetBlock), RegistryUtil.getTextureLocationOrDefault(DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get()).withPath(curPath -> curPath.replace("_stairs", "_block"))));

        return ModelUtil.stairs(targetBlock, defaultedTexLoc, defaultedTexLoc, defaultedTexLoc);
    }

    public static BlockModelDefinition roboWall(Supplier<Block> targetBlock) {
        ResourceLocation defaultedTexLoc = RegistryUtil.getTextureLocationOrDefault(RegistryUtil.pickBlockId(targetBlock), RegistryUtil.getTextureLocationOrDefault(DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get()).withPath(curPath -> curPath.replace("_wall", "_block"))));

        return ModelUtil.wall(targetBlock, defaultedTexLoc);
    }

    public static BlockModelDefinition orientableCube(ResourceLocation frontTexture, ResourceLocation sideTexture, ResourceLocation topTexture) {
        return new BlockModelDefinition(ModelTemplates.CUBE_ORIENTABLE)
                .withTextureMapping(new TextureMapping()
                        .put(TextureSlot.FRONT, frontTexture)
                        .put(TextureSlot.SIDE, sideTexture)
                        .put(TextureSlot.TOP, topTexture));
    }

    public static BlockModelDefinition orientableCube(Supplier<Block> targetBlock) {
        return orientableCube(RegistryUtil.getTextureLocationOrDefault(targetBlock, "block"), RegistryUtil.getTextureLocationWithSuffixOrDefault(targetBlock, "_side", "block"), RegistryUtil.getTextureLocationWithSuffixOrDefault(targetBlock, "_top", "block"))
                .withOrdinalModelDefinition(new ItemModelDefinition(ModelUtil.fromLocation(ModelLocationUtils.getModelLocation(targetBlock.get()))));
    }

    public static BlockModelDefinition orientableCubeLit(Supplier<Block> targetBlock) {
        return orientableCube(targetBlock)
                .withOrdinalModelDefinition(orientableCube(RegistryUtil.getTextureLocationWithSuffixOrDefault(targetBlock, "_lit", "block"), RegistryUtil.getTextureLocationWithSuffixOrDefault(targetBlock, "_side", "block"), RegistryUtil.getTextureLocationWithSuffixOrDefault(targetBlock, "_top", "block"))
                        .withCustomName(DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get()).withSuffix("_lit").getPath()));
    }

    public static BlockStateDefinition orientableCubeBlockState(Supplier<Block> targetBlock) {
        return new BlockStateDefinition(targetBlock)
                .withBlockStateSupplier(MultiVariantGenerator.multiVariant(targetBlock.get())
                        .with(PropertyDispatch.property(BlockStateProperties.LIT)
                                .select(true, Variant.variant()
                                        .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(targetBlock.get(), "_lit")))
                                .select(false, Variant.variant()
                                        .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(targetBlock.get()))))
                        .with(PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                                .select(Direction.EAST, Variant.variant()
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                                .select(Direction.SOUTH, Variant.variant()
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                                .select(Direction.WEST, Variant.variant()
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                                .select(Direction.NORTH, Variant.variant())));
    }

    public static BlockModelDefinition crystalGrassBlock(Supplier<Block> targetBlock) {
        ResourceLocation targetBlockId = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get());
        ResourceLocation sideTextureLoc = RegistryUtil.getTextureLocationOrDefault(targetBlockId.withSuffix("_side"), "block");
        ResourceLocation bottomTextureLoc = RegistryUtil.getTextureLocationOrDefault(targetBlockId.withSuffix("_bottom"), "block");
        ResourceLocation topTextureLoc = RegistryUtil.getTextureLocationOrDefault(targetBlockId.withSuffix("_top"), "block");

        return ModelUtil.cubeBottomTop(targetBlock, sideTextureLoc, bottomTextureLoc, topTextureLoc);
    }

    public static BlockModelDefinition orientableCubeContainer(Supplier<Block> targetBlock, ResourceLocation sideTextureLoc, ResourceLocation bottomTextureLoc, ResourceLocation topTextureLoc, ResourceLocation openContainerTexture) {
        return ModelUtil.cubeBottomTop(targetBlock, sideTextureLoc, bottomTextureLoc, topTextureLoc)
                .withOrdinalModelDefinition(ModelUtil.cubeBottomTop(targetBlock, sideTextureLoc, bottomTextureLoc, topTextureLoc)
                        .withCustomName(DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get()).withSuffix("_open").getPath())
                        .setOrdinalModelDefinitions(ObjectArrayList.of()));
    }

    public static BlockModelDefinition orientableCubeContainer(Supplier<Block> targetBlock, ResourceLocation sideTextureLoc, ResourceLocation bottomTextureLoc, ResourceLocation topTextureLoc) {
        return orientableCubeContainer(targetBlock, sideTextureLoc, bottomTextureLoc, topTextureLoc, topTextureLoc);
    }

    public static BlockModelDefinition orientableCubeContainer(Supplier<Block> targetBlock, ResourceLocation sideTextureLoc, ResourceLocation endTextureLoc) {
        return orientableCubeContainer(targetBlock, sideTextureLoc, endTextureLoc, endTextureLoc);
    }

    public static BlockModelDefinition orientableCubeContainer(Supplier<Block> targetBlock) {
        return orientableCubeContainer(targetBlock, RegistryUtil.getTextureLocationOrDefault(targetBlock), RegistryUtil.getTextureLocationOrDefault(targetBlock), RegistryUtil.getTextureLocationWithSuffixOrDefault(targetBlock, "_top"), RegistryUtil.getTextureLocationOrDefault(DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get()).withSuffix("_open"), "block", RegistryUtil.getTextureLocationWithSuffixOrDefault(targetBlock, "_top")));
    }

    public static BlockStateDefinition orientableFacingBlockState(Supplier<Block> targetBlock) {
        return new BlockStateDefinition(targetBlock)
                .withBlockStateSupplier(MultiVariantGenerator.multiVariant(targetBlock.get())
                        .with(PropertyDispatch.property(BlockStateProperties.FACING)
                                .select(Direction.DOWN, Variant.variant()
                                        .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
                                .select(Direction.UP, Variant.variant())
                                .select(Direction.NORTH, Variant.variant()
                                        .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                                .select(Direction.SOUTH, Variant.variant()
                                        .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                                .select(Direction.WEST, Variant.variant()
                                        .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                                .select(Direction.EAST, Variant.variant()
                                        .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))));
    }

    public static BlockStateDefinition orientableCubeContainerBlockState(Supplier<Block> targetBlock, ResourceLocation openModelLoc, ResourceLocation closedModelLoc) {
        BlockStateDefinition orientableCubeBase = orientableFacingBlockState(targetBlock);
        MultiVariantGenerator multiVariantGenerator = (MultiVariantGenerator) orientableCubeBase.getBlockStateSupplier();

        return new BlockStateDefinition(targetBlock)
                .withBlockStateSupplier(multiVariantGenerator
                        .with(PropertyDispatch.property(BlockStateProperties.OPEN)
                                .select(false, Variant.variant()
                                        .with(VariantProperties.MODEL, closedModelLoc))
                                .select(true, Variant.variant()
                                        .with(VariantProperties.MODEL, openModelLoc))));
    }

    public static BlockStateDefinition orientableCubeContainerBlockState(Supplier<Block> targetBlock) {
        return orientableCubeContainerBlockState(targetBlock, ModelLocationUtils.getModelLocation(targetBlock.get()), ModelLocationUtils.getModelLocation(targetBlock.get(), "_open"));
    }

    public static BlockModelDefinition coralFan(ResourceLocation fanTextureLoc) {
        return new BlockModelDefinition(ModelTemplates.CORAL_FAN)
                .withTextureMapping(new TextureMapping().put(TextureSlot.FAN, fanTextureLoc))
                .withRenderType(ModelUtil.CUTOUT_RENDER_TYPE)
                .withOrdinalModelDefinition(ModelUtil.generatedBlock(fanTextureLoc));
    }

    public static BlockModelDefinition coralFan(Supplier<Block> targetBlock) {
        return coralFan(RegistryUtil.getTextureLocationOrDefault(targetBlock, "block"));
    }

    public static BlockModelDefinition coralWallFan(ResourceLocation fanTextureLoc) {
        return new BlockModelDefinition(ModelTemplates.CORAL_WALL_FAN)
                .withTextureMapping(new TextureMapping().put(TextureSlot.FAN, fanTextureLoc))
                .withRenderType(ModelUtil.CUTOUT_RENDER_TYPE);
    }

    public static BlockModelDefinition coralWallFan(Supplier<Block> targetBlock) {
        return coralWallFan(RegistryUtil.getTextureLocationOrDefault(targetBlock, "block", RegistryUtil.getTextureLocationOrDefault(DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get()).withPath(path -> path.replace("_wall", "")))));
    }

    public static BlockStateDefinition coralWallFanBlockState(Supplier<Block> targetBlock) {
        return new BlockStateDefinition(targetBlock)
                .withBlockStateSupplier(MultiVariantGenerator.multiVariant(targetBlock.get(), Variant.variant()
                                .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(targetBlock.get())))
                        .with(PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                                .select(Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                                .select(Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                                .select(Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                                .select(Direction.NORTH, Variant.variant())));
    }

    public static BlockModelDefinition crop(Supplier<Block> targetBlock) {
        if (targetBlock.get() instanceof CropInstance crop) {
            ResourceLocation targetBlockId = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get());
            BlockModelDefinition stage0 = new BlockModelDefinition(ModelTemplates.CROP)
                    .withRenderType(ModelUtil.CUTOUT_RENDER_TYPE)
                    .withCustomName(targetBlockId.withSuffix("_stage_0").getPath())
                    .withTextureMapping(TextureMapping.crop(RegistryUtil.getTextureLocationWithSuffixOrDefault(new ResourceLocation(targetBlockId.getNamespace(), targetBlockId.getPath()), "_0", "block/vegetation/crop")));
            Stream<BlockModelDefinition> ordinals = crop.getAgeProperty().getPossibleValues().stream().filter(age -> age != 0)
                    .map((age) -> new BlockModelDefinition(ModelTemplates.CROP)
                            .withRenderType(ModelUtil.CUTOUT_RENDER_TYPE)
                            .withCustomName(targetBlockId.withSuffix("_stage_" + age).getPath())
                            .withTextureMapping(TextureMapping.crop(RegistryUtil.getTextureLocationWithSuffixOrDefault(new ResourceLocation(targetBlockId.getNamespace(), targetBlockId.getPath()), "_" + age, "block/vegetation/crop"))));
            return stage0.setOrdinalModelDefinitions(ordinals.collect(Collectors.toUnmodifiableList()));
        } else {
            throw new IllegalArgumentException("Target block must be instance of CropInstance");
        }
    }

    public static BlockModelDefinition cropCross(Supplier<Block> targetBlock) {
        if (targetBlock.get() instanceof CropInstance crop) {
            ResourceLocation targetBlockId = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get());
            BlockModelDefinition stage0 = new BlockModelDefinition(ModelTemplates.CROSS)
                    .withRenderType(ModelUtil.CUTOUT_RENDER_TYPE)
                    .withCustomName(targetBlockId.withSuffix("_stage_0").getPath())
                    .withTextureMapping(TextureMapping.cross(RegistryUtil.getTextureLocationWithSuffixOrDefault(new ResourceLocation(targetBlockId.getNamespace(), targetBlockId.getPath()), "_0", "block/vegetation/crop")));
            Stream<BlockModelDefinition> ordinals = crop.getAgeProperty().getPossibleValues().stream().filter(age -> age != 0)
                    .map((age) -> new BlockModelDefinition(ModelTemplates.CROSS)
                            .withRenderType(ModelUtil.CUTOUT_RENDER_TYPE)
                            .withCustomName(targetBlockId.withSuffix("_stage_" + age).getPath())
                            .withTextureMapping(TextureMapping.cross(RegistryUtil.getTextureLocationWithSuffixOrDefault(new ResourceLocation(targetBlockId.getNamespace(), targetBlockId.getPath()), "_" + age, "block/vegetation/crop"))));
            return stage0.setOrdinalModelDefinitions(ordinals.collect(Collectors.toUnmodifiableList()));
        } else {
            throw new IllegalArgumentException("Target block must be instance of CropInstance");
        }
    }

    public static BlockStateDefinition cropBlockState(Supplier<Block> targetBlock) {
        if (targetBlock.get() instanceof CropInstance crop) {
            Int2ObjectMap<ResourceLocation> int2objectmap = new Int2ObjectOpenHashMap<>();
            PropertyDispatch dispatch = PropertyDispatch.property(crop.getAgeProperty()).generate((age) -> {
                ResourceLocation stageModelLoc = int2objectmap.computeIfAbsent(age, arg ->
                        ModelLocationUtils.getModelLocation(targetBlock.get(), "_stage_" + age)
                );
                return Variant.variant().with(VariantProperties.MODEL, stageModelLoc);
            });
            return new BlockStateDefinition(targetBlock)
                    .withBlockStateSupplier(MultiVariantGenerator.multiVariant(targetBlock.get()).with(dispatch));
        } else {
            throw new IllegalArgumentException("Target block must be instance of CropInstance");
        }
    }

    public static BlockModelDefinition cropHeadBlock(Supplier<Block> targetBlock) {
        if (targetBlock.get() instanceof CropInstance crop) {
            ResourceLocation targetBlockId = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get());
            BlockModelDefinition stage0 = new BlockModelDefinition(ModelTemplates.CROSS)
                    .withRenderType(ModelUtil.CUTOUT_RENDER_TYPE)
                    .withCustomName(targetBlockId.withSuffix("_stage_0").getPath().replace("_head_block", ""))
                    .withTextureMapping(TextureMapping.cross(RegistryUtil.getTextureLocationWithSuffixOrDefault(new ResourceLocation(targetBlockId.getNamespace(), targetBlockId.getPath().replace("_head_block", "")), "_0", "block/vegetation/crop")));
            Stream<BlockModelDefinition> ordinals = crop.getAgeProperty().getPossibleValues().stream().filter(age -> age != 0 && age < crop.getMaxAge())
                    .map((age) -> new BlockModelDefinition(ModelTemplates.CROSS)
                            .withRenderType(ModelUtil.CUTOUT_RENDER_TYPE)
                            .withCustomName(targetBlockId.withSuffix("_stage_" + age).getPath().replace("_head_block", ""))
                            .withTextureMapping(TextureMapping.cross(RegistryUtil.getTextureLocationWithSuffixOrDefault(new ResourceLocation(targetBlockId.getNamespace(), targetBlockId.getPath().replace("_head_block", "")), "_" + age, "block/vegetation/crop"))));
            return stage0.setOrdinalModelDefinitions(ordinals.collect(Collectors.toUnmodifiableList()));
        } else {
            throw new IllegalArgumentException("Target block must be instance of CropInstance");
        }
    }

    public static BlockModelDefinition cropBodyBlock(Supplier<Block> targetBlock) {
        if (targetBlock.get() instanceof CropInstance crop) {
            ResourceLocation targetBlockId = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(targetBlock.get());
            int maxAge = crop.getMaxAge();

            return new BlockModelDefinition(ModelTemplates.CROSS)
                    .withRenderType(ModelUtil.CUTOUT_RENDER_TYPE)
                    .withCustomName(targetBlockId.withSuffix("_stage_" + maxAge).getPath().replace("_body_block", ""))
                    .withTextureMapping(TextureMapping.cross(RegistryUtil.getTextureLocationWithSuffixOrDefault(new ResourceLocation(targetBlockId.getNamespace(), targetBlockId.getPath().replace("_body_block", "")), "_" + maxAge, "block/vegetation/crop")));
        } else throw new IllegalArgumentException("Target block must be instance of CropInstance");
    }

    public static BlockStateDefinition cropHeadBlockBlockState(Supplier<Block> targetBlock) {
        if (targetBlock.get() instanceof CropInstance crop) {
            Int2ObjectMap<ResourceLocation> int2objectmap = new Int2ObjectOpenHashMap<>();
            PropertyDispatch dispatch = PropertyDispatch.property(crop.getAgeProperty()).generate((age) -> {
                ResourceLocation stageModelLoc = int2objectmap.computeIfAbsent(age, arg ->
                        CAConstants.prefix(ModelLocationUtils.getModelLocation(targetBlock.get(), "_stage_" + age).getPath().replace("_head_block", ""))
                );
                return Variant.variant().with(VariantProperties.MODEL, stageModelLoc);
            });
            return new BlockStateDefinition(targetBlock).withBlockStateSupplier(MultiVariantGenerator.multiVariant(targetBlock.get()).with(dispatch));
        } else {
            throw new IllegalArgumentException("Target block must be instance of CropInstance");
        }
    }

    public static BlockStateDefinition cropBodyBlockBlockState(Supplier<Block> targetBlock) {
        if (targetBlock.get() instanceof CropInstance crop) {
            int maxAge = crop.getMaxAge();
            return new BlockStateDefinition(targetBlock)
                    .withBlockStateSupplier(MultiVariantGenerator
                            .multiVariant(targetBlock.get(), Variant.variant()
                                    .with(VariantProperties.MODEL, CAConstants.prefix(ModelLocationUtils.getModelLocation(targetBlock.get(), "_stage_" + maxAge).getPath().replace("_body_block", "")))));
        } else {
            throw new IllegalArgumentException("Target block must be instance of CropInstance");
        }
    }
}
