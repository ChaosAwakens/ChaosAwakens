package io.github.chaosawakens.core.template;

import com.mememan.nexus.client.block.BlockStateDefinition;
import com.mememan.nexus.client.model.block.BlockModelDefinition;
import com.mememan.nexus.client.model.item.ItemModelDefinition;
import com.mememan.nexus.property_wrapper.base.generic.DataGenPropertyWrapper;
import com.mememan.nexus.util.ModelUtil;
import com.mememan.nexus.util.RegistryUtil;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.content.block.vegetation.FruitableLeavesBlock;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Direction;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Optional;
import java.util.function.Supplier;

public final class CAModelTemplates {
    public static final ModelTemplate LEAF_CARPET = new ModelTemplate(Optional.of(CAConstants.prefix("block/leaf_carpet")), Optional.empty(), TextureSlot.TEXTURE);
    public static final ModelTemplate LEAF_CARPET_INVENTORY = new ModelTemplate(Optional.of(CAConstants.prefix("block/leaf_carpet_inventory")), Optional.of("_inventory"), TextureSlot.TEXTURE);
    public static final ModelTemplate HANDHELD_LONG = new ModelTemplate(Optional.of(CAConstants.prefix("item/handheld_long")), Optional.empty(), TextureSlot.LAYER0);

    private CAModelTemplates() {
        throw new IllegalAccessError("Attempted to construct instance of template class! (CAModelTemplates)");
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
                                .term(PipeBlock.NORTH, true), Variant.variant() // Gotta use PipeBlock properties or it just won't work :skull:
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
        return orientableCube(RegistryUtil.getTextureLocationOrDefault(targetBlock), RegistryUtil.getTextureLocationWithSuffixOrDefault(targetBlock, "_side"), RegistryUtil.getTextureLocationWithSuffixOrDefault(targetBlock, "_top"))
                .withOrdinalModelDefinition(new ItemModelDefinition(ModelUtil.fromLocation(ModelLocationUtils.getModelLocation(targetBlock.get()))));
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
}
