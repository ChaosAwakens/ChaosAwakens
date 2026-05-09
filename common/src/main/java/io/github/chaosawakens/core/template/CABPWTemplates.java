package io.github.chaosawakens.core.template;

import com.mememan.nexus.property_wrapper.def.block.BlockPropertyWrapper;
import com.mememan.nexus.property_wrapper.def.block.BlockPropertyWrapperBuilder;
import com.mememan.nexus.template.object.block.entity.sign.DefaultableCeilingHangingSignBlock;
import com.mememan.nexus.template.object.block.entity.sign.DefaultableStandingSignBlock;
import com.mememan.nexus.template.object.block.entity.sign.DefaultableWallHangingSignBlock;
import com.mememan.nexus.template.object.block.entity.sign.DefaultableWallSignBlock;
import com.mememan.nexus.template.object.block.misc.WoodenBlockGroup;
import com.mememan.nexus.template.object.block_entity.sign.DefaultableHangingSignBlockEntity;
import com.mememan.nexus.template.object.block_entity.sign.DefaultableSignBlockEntity;
import com.mememan.nexus.template.object.entity.misc.vehicle.DefaultableBoat;
import com.mememan.nexus.template.object.entity.misc.vehicle.DefaultableChestBoat;
import com.mememan.nexus.template.object.item.entity.boat.BoatType;
import com.mememan.nexus.template.object.item.entity.boat.DefaultableBoatItem;
import com.mememan.nexus.template.property_wrapper.BlockEntityTypePropertyWrapperTemplates;
import com.mememan.nexus.template.property_wrapper.BlockPropertyWrapperTemplates;
import com.mememan.nexus.template.property_wrapper.EntityTypePropertyWrapperTemplates;
import com.mememan.nexus.template.property_wrapper.ItemPropertyWrapperTemplates;
import com.mememan.nexus.util.LootUtil;
import com.mememan.nexus.util.ModelUtil;
import com.mememan.nexus.util.RegistryUtil;
import io.github.chaosawakens.content.registry.CACreativeModeTabs;
import io.github.chaosawakens.content.registry.CASoundTypes;
import io.github.chaosawakens.content.registry.CATags;
import io.github.chaosawakens.util.StringUtil;
import io.github.chaosawakens.util.VanillaUtil;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectObjectImmutablePair;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class CABPWTemplates {
    public static final BlockPropertyWrapper<Block> PATTERN_BLOCK = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.BASIC)
            .withRecipe(CARecipeTemplates::patternBlockRecipeFrom)
            .build();

    public static final BlockPropertyWrapper<Block> ORIENTABLE_BLOCK_STONE = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.BASIC_PICKAXE_STONE)
            .withModelDefinition(CAModelTemplates::orientableCube)
            .withBlockStateDefinition(CAModelTemplates::orientableCubeBlockState)
            .build();
    public static final BlockPropertyWrapper<Block> ORIENTABLE_BASIC_MACHINE_STONE = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(ORIENTABLE_BLOCK_STONE)
            .withModelDefinition(CAModelTemplates::orientableCubeLit)
            .build();

    public static final BlockPropertyWrapper<Block> FOSSIL = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.BASIC)
            .withParentTab(CACreativeModeTabs.FOSSILS)
            .literalTranslation()
            .withLocalization(StringUtil::formatFossilName)
            .withRecipe(CARecipeTemplates::spawnEggFromFossilWater)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_PICKAXE = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL)
            .withTag(() -> BlockTags.MINEABLE_WITH_PICKAXE)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_PICKAXE_STONE = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_PICKAXE)
            .withTag(() -> BlockTags.NEEDS_STONE_TOOL)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_DREDGESTONE = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_PICKAXE_STONE)
            .withTag(CATags.CABlockTags.DREDGESTONE_FOSSILS::get)
            .withAdditionalTag(CATags.CAItemTags.DREDGESTONE_FOSSILS::get)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_GLOOMSTONE = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_PICKAXE_STONE)
            .withTag(CATags.CABlockTags.GLOOMSTONE_FOSSILS::get)
            .withAdditionalTag(CATags.CAItemTags.GLOOMSTONE_FOSSILS::get)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_PICKAXE_IRON = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_PICKAXE)
            .withTag(() -> BlockTags.NEEDS_IRON_TOOL)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_DEEPSLATE = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_PICKAXE_IRON)
            .withTag(CATags.CABlockTags.DEEPSLATE_FOSSILS::get)
            .withAdditionalTag(CATags.CAItemTags.DEEPSLATE_FOSSILS::get)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_DEEPSLATE_ROTATED = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_DEEPSLATE)
            .withModelDefinition(ModelUtil::rotatedPillar)
            .withBlockStateDefinition(ModelUtil::rotatedPillarBlockState)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_BLACKSTOME = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_PICKAXE)
            .withTag(CATags.CABlockTags.BLACKSTONE_FOSSILS::get)
            .withAdditionalTag(CATags.CAItemTags.BLACKSTONE_FOSSILS::get)
            .withRecipe(CARecipeTemplates::spawnEggFromFossilLava)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_END_STONE = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_PICKAXE)
            .withTag(CATags.CABlockTags.END_STONE_FOSSILS::get)
            .withAdditionalTag(CATags.CAItemTags.END_STONE_FOSSILS::get)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_FROZEN = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_PICKAXE)
            .withTag(CATags.CABlockTags.FROZEN_FOSSILS::get)
            .withAdditionalTag(CATags.CAItemTags.FROZEN_FOSSILS::get)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_KYANITE = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_PICKAXE)
            .withTag(CATags.CABlockTags.KYANITE_FOSSILS::get)
            .withAdditionalTag(CATags.CAItemTags.KYANITE_FOSSILS::get)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_NETHERRACK = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_PICKAXE)
            .withTag(CATags.CABlockTags.NETHERRACK_FOSSILS::get)
            .withAdditionalTag(CATags.CAItemTags.NETHERRACK_FOSSILS::get)
            .withRecipe(CARecipeTemplates::spawnEggFromFossilLava)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_SANDSTONE = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_PICKAXE)
            .withModelDefinition(ModelUtil::cubeBottomTop)
            .withTag(CATags.CABlockTags.SANDSTONE_FOSSILS::get)
            .withAdditionalTag(CATags.CAItemTags.SANDSTONE_FOSSILS::get)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_STONE = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_PICKAXE)
            .withTag(CATags.CABlockTags.STONE_FOSSILS::get)
            .withAdditionalTag(CATags.CAItemTags.STONE_FOSSILS::get)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_SHOVEL = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL)
            .withTag(() -> BlockTags.MINEABLE_WITH_SHOVEL)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_GRAVEL = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_SHOVEL)
            .withTag(CATags.CABlockTags.GRAVEL_FOSSILS::get)
            .withAdditionalTag(CATags.CAItemTags.GRAVEL_FOSSILS::get)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_SAND = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_SHOVEL)
            .withTag(CATags.CABlockTags.SAND_FOSSILS::get)
            .withAdditionalTag(CATags.CAItemTags.SAND_FOSSILS::get)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_SOUL_SOIL = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_SHOVEL)
            .withTag(CATags.CABlockTags.SOUL_SOIL_FOSSILS::get)
            .withAdditionalTag(CATags.CAItemTags.SOUL_SOIL_FOSSILS::get)
            .withRecipe(CARecipeTemplates::spawnEggFromFossilLava)
            .build();
    public static final BlockPropertyWrapper<Block> GATE_BLOCK = new BlockPropertyWrapper<>()
            .builder()
            .literalTranslation()
            .withModelDefinition(parentBlock -> ModelUtil.cubeBottomTop(parentBlock, RegistryUtil.getTextureLocationOrDefault(parentBlock), RegistryUtil.getTextureLocationOrDefaultWithSuffix(parentBlock, "_top"), RegistryUtil.getTextureLocationOrDefaultWithSuffix(parentBlock, "_top")))
            .withBlockStateDefinition(ModelUtil::simpleBlockState)
            .build();

    public static final BlockPropertyWrapper<Block> FARMLAND = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.FARMLAND)
            .withTag(CATags.CABlockTags.FARMLAND_BLOCKS::get)
            .build();

    public static final BlockPropertyWrapper<Block> LEAF_CARPET = new BlockPropertyWrapper<>()
            .builder()
            .withModelDefinition(CAModelTemplates::leafCarpet)
            .withBlockStateDefinition(CAModelTemplates::leafCarpetBlockState)
            .withRecipe(CARecipeTemplates::leafCarpetRecipeFrom)
            .withTag(CATags.CABlockTags.LEAF_CARPETS::get)
            .withAdditionalTag(CATags.CAItemTags.LEAF_CARPETS::get)
            .withLootTable(LootUtil::dropMultiFace)
            .build();

    public static final BlockPropertyWrapper<Block> LEAF_CARPET_VANILLA = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(LEAF_CARPET)
            .withBlockColor(VanillaUtil::standardLeavesColor)
            .build();

    public static final BlockPropertyWrapper<Block> PETAL_BLOCK = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.BASIC_AXE)
            .withTag(CATags.CABlockTags.FLOWER_BLOCKS::get)
            .withAdditionalTag(CATags.CAItemTags.FLOWER_BLOCKS::get)
            .build();
    public static final BlockPropertyWrapper<Block> STEM_BLOCK = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.ROTATED_PILLAR_AXE)
            .withTag(CATags.CABlockTags.FLOWER_BLOCKS::get)
            .withAdditionalTag(CATags.CAItemTags.FLOWER_BLOCKS::get)
            .build();

    public static final BlockPropertyWrapper<Block> FRUITABLE_LEAVES = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.LEAVES)
            .withModelDefinition(CAModelTemplates::fruitableLeaves)
            .withBlockStateDefinition(CAModelTemplates::fruitableLeavesBlockState)
            .withLootTable(CALootTableTemplates::dropLeavesRipe)
            .build();

    public static final BlockPropertyWrapper<Block> CRYSTAL_GRASS_BLOCK = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.BASIC_PICKAXE)
            .withModelDefinition(CAModelTemplates::crystalGrassBlock)
            .withTag(CATags.CABlockTags.CRYSTAL_SOIL::get)
            .withLootTable(LootUtil::dropSilkTouchOnly)
            .literalTranslation()
            .build();

    public static final BlockPropertyWrapper<Block> CRYSTAL_LEAVES = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.LEAVES)
            .withBlockColor(null)
            .build();

    public static final BlockPropertyWrapper<Block> DENSE_PLANT = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.NO_TINT_PLANT)
            .withTag(CATags.CABlockTags.DENSE_VEGETATION::get)
            .build();
    public static final BlockPropertyWrapper<Block> TALL_DENSE_PLANT = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.NO_TINT_TALL_PLANT)
            .withTag(CATags.CABlockTags.DENSE_VEGETATION::get)
            .build();
    public static final BlockPropertyWrapper<Block> MULTI_LAYER_DENSE_PLANT = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.NO_TINT_MULTI_LAYER_PLANT)
            .withTag(CATags.CABlockTags.DENSE_VEGETATION::get)
            .build();

    public static final BlockPropertyWrapper<Block> DENSE_FLOWER = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.SMALL_FLOWER)
            .withTag(CATags.CABlockTags.DENSE_FLOWERS::get)
            .build();
    public static final BlockPropertyWrapper<Block> TALL_DENSE_FLOWER = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.TALL_FLOWER)
            .withTag(CATags.CABlockTags.DENSE_FLOWERS::get)
            .build();
    public static final BlockPropertyWrapper<Block> MULTI_LAYER_DENSE_FLOWER = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.MULTI_LAYER_FLOWER)
            .withTag(CATags.CABlockTags.DENSE_FLOWERS::get)
            .build();

    public static final BlockPropertyWrapper<Block> CRYSTAL_PLANT = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.NO_TINT_PLANT)
            .withTag(CATags.CABlockTags.CRYSTAL_VEGETATION::get)
            .build();
    public static final BlockPropertyWrapper<Block> TALL_CRYSTAL_PLANT = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.NO_TINT_TALL_PLANT)
            .withTag(CATags.CABlockTags.CRYSTAL_VEGETATION::get)
            .build();
    public static final BlockPropertyWrapper<Block> MULTI_LAYER_CRYSTAL_PLANT = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.NO_TINT_MULTI_LAYER_PLANT)
            .withTag(CATags.CABlockTags.CRYSTAL_VEGETATION::get)
            .build();

    public static final BlockPropertyWrapper<Block> CRYSTAL_FLOWER = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.SMALL_FLOWER)
            .withTag(CATags.CABlockTags.CRYSTAL_FLOWERS::get)
            .build();
    public static final BlockPropertyWrapper<Block> TALL_CRYSTAL_FLOWER = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.TALL_FLOWER)
            .withTag(CATags.CABlockTags.CRYSTAL_FLOWERS::get)
            .build();
    public static final BlockPropertyWrapper<Block> MULTI_LAYER_CRYSTAL_FLOWER = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.MULTI_LAYER_FLOWER)
            .withTag(CATags.CABlockTags.CRYSTAL_FLOWERS::get)
            .build();

    public static final BlockPropertyWrapper<Block> CRYSTAL_FAN = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.BASIC)
            .withModelDefinition(CAModelTemplates::coralFan)
            .withLootTable(LootUtil::dropSilkTouchOnly)
            .withTag(CATags.CABlockTags.CRYSTAL_VEGETATION::get)
            .build();
    public static final BlockPropertyWrapper<Block> CRYSTAL_WALL_FAN = new BlockPropertyWrapper<>()
            .builder()
            .withModelDefinition(CAModelTemplates::coralWallFan)
            .withBlockStateDefinition(CAModelTemplates::coralWallFanBlockState)
            .withLootTable(LootUtil::dropSilkTouchOnly)
            .withTag(CATags.CABlockTags.CRYSTAL_VEGETATION::get)
            .build();

    public static final BlockPropertyWrapper<Block> CROP = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.BASIC)
            .withModelDefinition(CAModelTemplates::crop)
            .withBlockStateDefinition(CAModelTemplates::cropBlockState)
            .withLootTable(CALootTableTemplates::dropCrop)
            .build();
    public static final BlockPropertyWrapper<Block> CROP_CROSS = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.BASIC)
            .withModelDefinition(CAModelTemplates::cropCross)
            .withBlockStateDefinition(CAModelTemplates::cropBlockState)
            .withLootTable(CALootTableTemplates::dropCrop)
            .build();

    public static final BlockPropertyWrapper<Block> CROP_HEAD_BLOCK = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.BASIC)
            .withModelDefinition(CAModelTemplates::cropHeadBlock)
            .withBlockStateDefinition(CAModelTemplates::cropHeadBlockBlockState)
            .withLootTable(CALootTableTemplates::dropCrop)
            .withLocalization(string -> string.replace("Block of ", "").replace("Head", "") + "Plant")
            .build();
    public static final BlockPropertyWrapper<Block> CROP_BODY_BLOCK = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.BASIC)
            .withModelDefinition(CAModelTemplates::cropBodyBlock)
            .withBlockStateDefinition(CAModelTemplates::cropBodyBlockBlockState)
            .withLootTable(CALootTableTemplates::dropCropBodyBlock)
            .withLocalization(string -> string.replace("Block of ", "").replace("Body", "") + "Plant")
            .build();

    public static final BlockPropertyWrapper<Block> MATERIAL_BLOCK_PICKAXE_IRON = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.MATERIAL_BLOCK_PICKAXE)
            .literalTranslation()
            .withTag(() -> BlockTags.NEEDS_IRON_TOOL)
            .build();
    public static final BlockPropertyWrapper<Block> MATERIAL_BLOCK_PICKAXE_DIAMOND = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.MATERIAL_BLOCK_PICKAXE)
            .literalTranslation()
            .withTag(() -> BlockTags.NEEDS_DIAMOND_TOOL)
            .build();

    public static final BlockPropertyWrapper<Block> MATERIAL_BLOCK_CRYSTAL = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(MATERIAL_BLOCK_PICKAXE_IRON)
            .withRecipe(CARecipeTemplates::crystalBlockRecipeFrom)
            .build();

    public static final BlockPropertyWrapper<Block> COMPONENT_BLOCK_PICKAXE_IRON = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.COMPONENT_BLOCK_PICKAXE)
            .withTag(() -> BlockTags.NEEDS_IRON_TOOL)
            .build();
    public static final BlockPropertyWrapper<Block> COMPONENT_BLOCK_PICKAXE_STONE = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.COMPONENT_BLOCK_PICKAXE)
            .withTag(() -> BlockTags.NEEDS_STONE_TOOL)
            .build();

    public static final BlockPropertyWrapper<Block> ROBO_BLOCK = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.BASIC_PICKAXE_DIAMOND)
            .withTag(CATags.CABlockTags.ROBO_BLOCKS::get)
            .withAdditionalTag(CATags.CAItemTags.ROBO_BLOCKS::get)
            .withLocalization(StringUtil::formatRoboBlockName)
            .build();
    public static final BlockPropertyWrapper<Block> ROBO_GATE_BLOCK = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(GATE_BLOCK)
            .withTag(CATags.CABlockTags.ROBO_BLOCKS::get)
            .withAdditionalTag(CATags.CAItemTags.ROBO_BLOCKS::get)
            .build();

    public static final BlockPropertyWrapper<Block> ROBO_GLASS = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.GLASS)
            .withTag(CATags.CABlockTags.ROBO_BLOCKS::get)
            .withAdditionalTag(CATags.CAItemTags.ROBO_BLOCKS::get)
            .build();
    public static final BlockPropertyWrapper<Block> ROBO_GLASS_PANE = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.GLASS_PANE)
            .withTag(CATags.CABlockTags.ROBO_BLOCKS::get)
            .withAdditionalTag(CATags.CAItemTags.ROBO_BLOCKS::get)
            .build();

    public static final BlockPropertyWrapper<Block> ROBO_BARS = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.BARS_DIAMOND)
            .withTag(CATags.CABlockTags.ROBO_BLOCKS::get)
            .withAdditionalTag(CATags.CAItemTags.ROBO_BLOCKS::get)
            .build();

    public static final BlockPropertyWrapper<Block> ROTATED_ROBO_BLOCK = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(ROBO_BLOCK)
            .withModelDefinition(ModelUtil::rotatedPillar)
            .withBlockStateDefinition(ModelUtil::rotatedPillarBlockState)
            .build();
    public static final BlockPropertyWrapper<Block> AXIS_ALIGNED_ROBO_BLOCK = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(ROBO_BLOCK)
            .withModelDefinition(ModelUtil::cubeColumn)
            .withBlockStateDefinition(ModelUtil::axisAlignedBlock)
            .literalTranslation()
            .build();

    public static final BlockPropertyWrapper<Block> ROBO_CONTAINER = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(ROBO_BLOCK)
            .withModelDefinition(CAModelTemplates::orientableCubeContainer)
            .withBlockStateDefinition(CAModelTemplates::orientableCubeContainerBlockState)
            .build();

    public static final BlockPropertyWrapper<Block> ROBO_SLAB = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.SLAB)
            .withModelDefinition(CAModelTemplates::roboSlab)
            .withBlockStateDefinition(CAModelTemplates::roboSlabBlockState)
            .withTags(() -> BlockTags.NEEDS_DIAMOND_TOOL, CATags.CABlockTags.ROBO_BLOCKS::get)
            .withAdditionalTag(CATags.CAItemTags.ROBO_BLOCKS::get)
            .withLocalization(StringUtil::formatRoboBlockName)
            .withRecipe(null)
            .build();
    public static final BlockPropertyWrapper<Block> ROBO_SLAB_TOP_SIDE = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(ROBO_SLAB)
            .withModelDefinition(CAModelTemplates::topSideRoboSlab)
            .build();
    public static final BlockPropertyWrapper<Block> ROBO_STAIRS = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.STAIRS)
            .withModelDefinition(CAModelTemplates::roboStairs)
            .withTags(() -> BlockTags.NEEDS_DIAMOND_TOOL, CATags.CABlockTags.ROBO_BLOCKS::get)
            .withAdditionalTag(CATags.CAItemTags.ROBO_BLOCKS::get)
            .withLocalization(StringUtil::formatRoboBlockName)
            .withRecipe(null)
            .build();
    public static final BlockPropertyWrapper<Block> ROBO_WALL = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.WALL)
            .withModelDefinition(CAModelTemplates::roboWall)
            .withTags(() -> BlockTags.NEEDS_DIAMOND_TOOL, CATags.CABlockTags.ROBO_BLOCKS::get)
            .withAdditionalTag(CATags.CAItemTags.ROBO_BLOCKS::get)
            .withLocalization(StringUtil::formatRoboBlockName)
            .withRecipe(null)
            .build();

    private CABPWTemplates() {
        throw new IllegalAccessError("Attempted to construct instance of template class! (CABPWTemplates)");
    }

    public static <B extends Block> BlockPropertyWrapperBuilder<B> registerWithItemAndChain(ResourceLocation blockId, Supplier<B> blockSup, Item.Properties itemProps, BlockPropertyWrapper<Block> templateBPW, @Nullable Collection<Supplier<Block>> blockSupCol, @Nullable Collection<Supplier<Item>> blockItemSupCol) {
        Supplier<B> registeredBlock = BlockPropertyWrapperTemplates.registerBlock(blockId, blockSup, blockSupCol);

        ItemPropertyWrapperTemplates.registerItem(blockId, () -> new BlockItem(registeredBlock.get(), itemProps), blockItemSupCol);

        return new BlockPropertyWrapper<>(registeredBlock, blockId.getNamespace())
                .builder()
                .copyFromType(templateBPW);
    }

    public static <B extends Block> Supplier<B> registerBlockWithItemFromTemplate(ResourceLocation blockId, Supplier<B> blockSup, Supplier<Item> blockItemSup, BlockPropertyWrapper<Block> templateBPW, @Nullable Collection<Supplier<Block>> blockSupCol, @Nullable Collection<Supplier<Item>> blockItemSupCol) {
        Supplier<B> registeredBlock = BlockPropertyWrapperTemplates.registerBlock(blockId, blockSup, blockSupCol);

        ItemPropertyWrapperTemplates.registerItem(blockId, blockItemSup, blockItemSupCol);

        return new BlockPropertyWrapper<>(registeredBlock, blockId.getNamespace())
                .builder()
                .copyFromType(templateBPW)
                .buildAndGet();
    }

    public static <B extends Block> BlockPropertyWrapperBuilder<B> registerWithItemAndChain(ResourceLocation blockId, Supplier<B> blockSup, Item.Properties itemProps, BlockPropertyWrapper<Block> templateBPW) {
        return registerWithItemAndChain(blockId, blockSup, itemProps, templateBPW, null, null);
    }

    public static <B extends Block> Supplier<B> registerBlockWithItemFromTemplate(ResourceLocation blockId, Supplier<B> blockSup, Item.Properties itemProps, BlockPropertyWrapper<Block> templateBPW, @Nullable Collection<Supplier<Block>> blockSupCol, @Nullable Collection<Supplier<Item>> blockItemSupCol) {
        return registerWithItemAndChain(blockId, blockSup, itemProps, templateBPW, blockSupCol, blockItemSupCol).buildAndGet();
    }

    public static <B extends Block> Supplier<B> registerBlockWithItemFromTemplate(ResourceLocation blockId, Supplier<B> blockSup, Item.Properties itemProps, BlockPropertyWrapper<Block> templateBPW) {
        return registerBlockWithItemFromTemplate(blockId, blockSup, itemProps, templateBPW, null, null);
    }

    public static Pair<List<Supplier<Item>>, List<Supplier<Item>>> splitBlockItems(Collection<Supplier<Item>> itemSupCol) {
        return ObjectObjectImmutablePair.of(
                itemSupCol.stream().filter(curItemSup -> curItemSup.get() instanceof BlockItem).collect(Collectors.toCollection(ObjectArrayList::new)),
                itemSupCol.stream().filter(curItemSup -> !(curItemSup.get() instanceof BlockItem)).collect(Collectors.toCollection(ObjectArrayList::new))
        );
    }

    public static List<Supplier<Item>> splitToBlockItems(Collection<Supplier<Item>> itemSupCol) {
        return splitBlockItems(itemSupCol).first();
    }

    public static List<Supplier<Item>> splitToNonBlockItems(Collection<Supplier<Item>> itemSupCol) {
        return splitBlockItems(itemSupCol).second();
    }

    public static WoodenBlockGroup registerCrystalwoodWoodFamily(ResourceLocation familyId, Supplier<TagKey<Item>> logTag, @Nullable Collection<Supplier<Item>> itemSupCol, @Nullable Collection<Supplier<Block>> blockSupCol, @Nullable Collection<Supplier<BlockEntityType<BlockEntity>>> blockEntityTypeSupCol, @Nullable Collection<Supplier<EntityType<Entity>>> entityTypeSupCol) {
        return Util.make(() -> {
            Map<ResourceLocation, Supplier<? extends Block>> woodBlockFamilyMap = new Object2ObjectOpenHashMap<>();
            Map<ResourceLocation, Supplier<? extends Item>> woodItemFamilyMap = new Object2ObjectOpenHashMap<>();
            Map<ResourceLocation, Supplier<? extends BlockEntityType<? extends BlockEntity>>> woodBlockEntityFamilyMap = new Object2ObjectOpenHashMap<>();
            Map<ResourceLocation, Supplier<? extends EntityType<? extends Entity>>> woodEntityTypeFamilyMap = new Object2ObjectOpenHashMap<>();
            BlockSetType woodBlockSetType = RegistryUtil.getOrCreateBlockSetType(new BlockSetType(familyId.toString()));
            WoodType woodBlockType = RegistryUtil.getOrCreateWoodType(familyId.toString(), woodBlockSetType);

            ResourceLocation woodenLogId = familyId.withSuffix("_log");
            ResourceLocation woodBlockId = familyId.getPath().endsWith("wood") ? familyId : familyId.withSuffix("_wood");
            ResourceLocation woodenStrippedLogId = familyId.withPrefix("stripped_").withSuffix("_log");

            Supplier<RotatedPillarBlock> woodenLog = BlockPropertyWrapperTemplates.registerWithItemAndReflectAndChain(woodenLogId, () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(CASoundTypes.CRYSTALWOOD)), BlockPropertyWrapperTemplates.WOODEN_LOG, blockSupCol, itemSupCol)
                    .withAdditionalTag(logTag::get)
                    .buildAndGet();
            Supplier<RotatedPillarBlock> woodBlock = BlockPropertyWrapperTemplates.registerWithItemAndChain(woodBlockId, () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).sound(CASoundTypes.CRYSTALWOOD)), BlockPropertyWrapperTemplates.WOOD, blockSupCol, itemSupCol)
                    .withAdditionalTag(logTag::get)
                    .buildAndGet();
            Supplier<RotatedPillarBlock> woodenStrippedLog = BlockPropertyWrapperTemplates.registerWithItemAndChain(woodenStrippedLogId, () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).sound(CASoundTypes.CRYSTALWOOD)), BlockPropertyWrapperTemplates.STRIPPED_WOODEN_LOG, blockSupCol, itemSupCol)
                    .withAdditionalTag(logTag::get)
                    .buildAndGet();

            ResourceLocation woodenPlanksId = familyId.withSuffix("_planks");
            ResourceLocation woodenSlabId = familyId.withSuffix("_slab");
            ResourceLocation woodenStairsId = familyId.withSuffix("_stairs");

            ResourceLocation woodenFenceId = familyId.withSuffix("_fence");
            ResourceLocation woodenFenceGateId = familyId.withSuffix("_fence_gate");
            ResourceLocation woodenDoorId = familyId.withSuffix("_door");
            ResourceLocation woodenTrapdoorId = familyId.withSuffix("_trapdoor");

            ResourceLocation woodenPressurePlateId = familyId.withSuffix("_pressure_plate");
            ResourceLocation woodenButtonId = familyId.withSuffix("_button");

            ResourceLocation woodenSignId = familyId.withSuffix("_sign");
            ResourceLocation woodenWallSignId = familyId.withSuffix("_wall_sign");

            ResourceLocation woodenHangingSignId = familyId.withSuffix("_hanging_sign");
            ResourceLocation woodenWallHangingSignId = familyId.withSuffix("_wall_hanging_sign");

            Supplier<Block> woodenPlanks = BlockPropertyWrapperTemplates.registerBlockWithItemFromTemplate(woodenPlanksId, () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(CASoundTypes.CRYSTALWOOD)), BlockPropertyWrapperTemplates.WOODEN_PLANKS, blockSupCol, itemSupCol);
            Supplier<Block> woodenSlab = BlockPropertyWrapperTemplates.registerBlockWithItemFromTemplate(woodenSlabId, () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB).sound(CASoundTypes.CRYSTALWOOD)), BlockPropertyWrapperTemplates.WOODEN_SLAB, blockSupCol, itemSupCol);
            Supplier<Block> woodenStairs = BlockPropertyWrapperTemplates.registerBlockWithItemFromTemplate(woodenStairsId, () -> new StairBlock(woodenPlanks.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS).sound(CASoundTypes.CRYSTALWOOD)), BlockPropertyWrapperTemplates.WOODEN_STAIRS, blockSupCol, itemSupCol);

            Supplier<Block> woodenFence = BlockPropertyWrapperTemplates.registerBlockWithItemFromTemplate(woodenFenceId, () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).sound(CASoundTypes.CRYSTALWOOD)), BlockPropertyWrapperTemplates.WOODEN_FENCE, blockSupCol, itemSupCol);
            Supplier<Block> woodenFenceGate = BlockPropertyWrapperTemplates.registerBlockWithItemFromTemplate(woodenFenceGateId, () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).sound(CASoundTypes.CRYSTALWOOD), woodBlockType), BlockPropertyWrapperTemplates.WOODEN_FENCE_GATE, blockSupCol, itemSupCol);

            Supplier<Block> woodenDoor = BlockPropertyWrapperTemplates.registerBlockWithItemFromTemplate(woodenDoorId, () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).sound(CASoundTypes.CRYSTALWOOD), woodBlockSetType), BlockPropertyWrapperTemplates.WOODEN_DOOR, blockSupCol, itemSupCol);
            Supplier<Block> woodenTrapdoor = BlockPropertyWrapperTemplates.registerBlockWithItemFromTemplate(woodenTrapdoorId, () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).sound(CASoundTypes.CRYSTALWOOD), woodBlockSetType), BlockPropertyWrapperTemplates.WOODEN_TRAPDOOR, blockSupCol, itemSupCol);

            Supplier<Block> woodenPressurePlate = BlockPropertyWrapperTemplates.registerBlockWithItemFromTemplate(woodenPressurePlateId, () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE).sound(CASoundTypes.CRYSTALWOOD), woodBlockSetType), BlockPropertyWrapperTemplates.WOODEN_PRESSURE_PLATE, blockSupCol, itemSupCol);
            Supplier<Block> woodenButton = BlockPropertyWrapperTemplates.registerBlockWithItemFromTemplate(woodenButtonId, () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).sound(CASoundTypes.CRYSTALWOOD), woodBlockSetType, 30, true), BlockPropertyWrapperTemplates.WOODEN_BUTTON, blockSupCol, itemSupCol);

            Supplier<Block> woodenStandingSign = BlockPropertyWrapperTemplates.registerBlockFromTemplateAndReflect(woodenSignId, () -> new DefaultableStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN).sound(CASoundTypes.CRYSTALWOOD), woodBlockType), BlockPropertyWrapperTemplates.WOODEN_STANDING_SIGN, blockSupCol);
            Supplier<Block> woodenWallSign = BlockPropertyWrapperTemplates.registerBlockFromTemplateAndReflect(woodenWallSignId, () -> new DefaultableWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN).sound(CASoundTypes.CRYSTALWOOD).dropsLike(woodenStandingSign.get()), woodBlockType), BlockPropertyWrapperTemplates.WOODEN_WALL_SIGN, blockSupCol);

            Supplier<Block> woodenCeilingHangingSign = BlockPropertyWrapperTemplates.registerBlockFromTemplateAndReflect(woodenHangingSignId, () -> new DefaultableCeilingHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN).sound(CASoundTypes.CRYSTALWOOD), woodBlockType), BlockPropertyWrapperTemplates.WOODEN_CEILING_HANGING_SIGN, blockSupCol);
            Supplier<Block> woodenWallHangingSign = BlockPropertyWrapperTemplates.registerBlockFromTemplateAndReflect(woodenWallHangingSignId, () -> new DefaultableWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN).sound(CASoundTypes.CRYSTALWOOD).dropsLike(woodenCeilingHangingSign.get()), woodBlockType), BlockPropertyWrapperTemplates.WOODEN_WALL_HANGING_SIGN, blockSupCol);

            Supplier<SignItem> woodenSignItem = ItemPropertyWrapperTemplates.registerItem(woodenSignId, () -> new SignItem(new Item.Properties().stacksTo(16), woodenStandingSign.get(), woodenWallSign.get()), itemSupCol);
            Supplier<HangingSignItem> woodenHangingSignItem = ItemPropertyWrapperTemplates.registerItem(woodenHangingSignId, () -> new HangingSignItem(woodenCeilingHangingSign.get(), woodenWallHangingSign.get(), new Item.Properties().stacksTo(16)), itemSupCol);

            ResourceLocation woodenBoatId = familyId.withSuffix("_boat");
            ResourceLocation woodenChestBoatId = familyId.withSuffix("_chest_boat");

            Supplier<Item> woodenBoatItem = ItemPropertyWrapperTemplates.registerItemFromTemplate(woodenBoatId, () -> new DefaultableBoatItem(false, BoatType.register(familyId.toString().replace(':', '-'), woodenPlanks), new Item.Properties().stacksTo(1)), ItemPropertyWrapperTemplates.BOAT, itemSupCol);
            Supplier<Item> woodenChestBoatItem = ItemPropertyWrapperTemplates.registerItemFromTemplate(woodenChestBoatId, () -> new DefaultableBoatItem(true, BoatType.register(familyId.toString().replace(':', '-'), woodenPlanks), new Item.Properties().stacksTo(1)), ItemPropertyWrapperTemplates.CHEST_BOAT, itemSupCol);

            ResourceLocation signBlockEntityId = familyId.withPath("sign");
            ResourceLocation hangingSignBlockEntityId = familyId.withPath("hanging_sign");

            ResourceLocation boatEntityId = familyId.withPath("boat");
            ResourceLocation chestBoatId = familyId.withPath("chest_boat");

            BuiltInRegistries.BLOCK_ENTITY_TYPE.getOptional(signBlockEntityId)
                    .ifPresentOrElse(alreadyRegisteredBaseSignBlockEntity -> {
                        Supplier<BlockEntityType<DefaultableSignBlockEntity>> mappedSignBlockEntitySup = () -> (BlockEntityType<DefaultableSignBlockEntity>) alreadyRegisteredBaseSignBlockEntity;
                        BlockEntityType<DefaultableSignBlockEntity> mappedSignBlockEntity = mappedSignBlockEntitySup.get();

                        mappedSignBlockEntity.validBlocks = Util.make(new ObjectOpenHashSet<>(mappedSignBlockEntity.validBlocks), existingValidBlocks -> {
                            existingValidBlocks.add(woodenStandingSign.get());
                            existingValidBlocks.add(woodenWallSign.get());
                        });

                        woodBlockEntityFamilyMap.put(signBlockEntityId, mappedSignBlockEntitySup);
                    }, () -> {
                        Supplier<BlockEntityType<DefaultableSignBlockEntity>> woodenSignBlockEntityType = BlockEntityTypePropertyWrapperTemplates.registerBlockEntityTypeFromTemplateAndReflect(signBlockEntityId, () -> BlockEntityType.Builder.of(
                                (targetPos, targetState) -> new DefaultableSignBlockEntity(() -> BuiltInRegistries.BLOCK_ENTITY_TYPE.getOptional(signBlockEntityId).orElseThrow(), targetPos, targetState),
                                woodenStandingSign.get(), woodenWallSign.get()
                        ).build(Util.fetchChoiceType(References.BLOCK_ENTITY, woodenSignId.getPath())), BlockEntityTypePropertyWrapperTemplates.SIGN, blockEntityTypeSupCol);

                        woodBlockEntityFamilyMap.put(signBlockEntityId, woodenSignBlockEntityType);
                    });
            BuiltInRegistries.BLOCK_ENTITY_TYPE.getOptional(hangingSignBlockEntityId)
                    .ifPresentOrElse(alreadyRegisteredBaseHangingSignBlockEntity -> {
                        Supplier<BlockEntityType<DefaultableHangingSignBlockEntity>> mappedHangingSignBlockEntitySup = () -> (BlockEntityType<DefaultableHangingSignBlockEntity>) alreadyRegisteredBaseHangingSignBlockEntity;
                        BlockEntityType<DefaultableHangingSignBlockEntity> mappedHangingSignBlockEntity = mappedHangingSignBlockEntitySup.get();

                        mappedHangingSignBlockEntity.validBlocks = Util.make(new ObjectOpenHashSet<>(mappedHangingSignBlockEntity.validBlocks), existingValidBlocks -> {
                            existingValidBlocks.add(woodenCeilingHangingSign.get());
                            existingValidBlocks.add(woodenWallHangingSign.get());
                        });

                        woodBlockEntityFamilyMap.put(hangingSignBlockEntityId, mappedHangingSignBlockEntitySup);
                    }, () -> {
                        Supplier<BlockEntityType<DefaultableHangingSignBlockEntity>> woodenHangingSignBlockEntityType = BlockEntityTypePropertyWrapperTemplates.registerBlockEntityTypeFromTemplateAndReflect(hangingSignBlockEntityId, () -> BlockEntityType.Builder.of(
                                (targetPos, targetState) -> new DefaultableHangingSignBlockEntity(() -> BuiltInRegistries.BLOCK_ENTITY_TYPE.getOptional(hangingSignBlockEntityId).orElseThrow(), targetPos, targetState),
                                woodenCeilingHangingSign.get(), woodenWallHangingSign.get()
                        ).build(Util.fetchChoiceType(References.BLOCK_ENTITY, woodenHangingSignId.getPath())), BlockEntityTypePropertyWrapperTemplates.HANGING_SIGN, blockEntityTypeSupCol);

                        woodBlockEntityFamilyMap.put(hangingSignBlockEntityId, woodenHangingSignBlockEntityType);
                    });

            BuiltInRegistries.ENTITY_TYPE.getOptional(boatEntityId)
                    .ifPresentOrElse(alreadyRegisteredBaseBoatEntity -> {
                        woodEntityTypeFamilyMap.put(boatEntityId, () -> alreadyRegisteredBaseBoatEntity);
                    }, () -> {
                        Supplier<EntityType<DefaultableBoat>> woodenBoatEntityType = EntityTypePropertyWrapperTemplates.registerEntityTypeFromTemplateAndReflect(boatEntityId,
                                () -> EntityType.Builder.<DefaultableBoat>of(DefaultableBoat::new, MobCategory.MISC)
                                        .sized(1.375F, 0.5625F)
                                        .clientTrackingRange(10)
                                        .build(boatEntityId.toString()), EntityTypePropertyWrapperTemplates.BOAT, entityTypeSupCol);

                        woodEntityTypeFamilyMap.put(boatEntityId, woodenBoatEntityType);
                    });
            BuiltInRegistries.ENTITY_TYPE.getOptional(chestBoatId)
                    .ifPresentOrElse(alreadyRegisteredBaseChestBoatEntity -> {
                        woodEntityTypeFamilyMap.put(chestBoatId, () -> alreadyRegisteredBaseChestBoatEntity);
                    }, () -> {
                        Supplier<EntityType<DefaultableChestBoat>> woodenChestBoatEntityType = EntityTypePropertyWrapperTemplates.registerEntityTypeFromTemplateAndReflect(chestBoatId,
                                () -> EntityType.Builder.<DefaultableChestBoat>of(DefaultableChestBoat::new, MobCategory.MISC)
                                        .sized(1.375F, 0.5625F)
                                        .clientTrackingRange(10)
                                        .build(chestBoatId.toString()), EntityTypePropertyWrapperTemplates.CHEST_BOAT, entityTypeSupCol);

                        woodEntityTypeFamilyMap.put(chestBoatId, woodenChestBoatEntityType);
                    });

            woodBlockFamilyMap.put(woodenLogId, woodenLog);
            woodBlockFamilyMap.put(woodBlockId, woodBlock);
            woodBlockFamilyMap.put(woodenStrippedLogId, woodenStrippedLog);

            woodBlockFamilyMap.put(woodenPlanksId, woodenPlanks);
            woodBlockFamilyMap.put(woodenSlabId, woodenSlab);
            woodBlockFamilyMap.put(woodenStairsId, woodenStairs);

            woodBlockFamilyMap.put(woodenFenceId, woodenFence);
            woodBlockFamilyMap.put(woodenFenceGateId, woodenFenceGate);

            woodBlockFamilyMap.put(woodenDoorId, woodenDoor);
            woodBlockFamilyMap.put(woodenTrapdoorId, woodenTrapdoor);

            woodBlockFamilyMap.put(woodenPressurePlateId, woodenPressurePlate);
            woodBlockFamilyMap.put(woodenButtonId, woodenButton);

            woodBlockFamilyMap.put(woodenSignId, woodenStandingSign);
            woodBlockFamilyMap.put(woodenWallSignId, woodenWallSign);

            woodBlockFamilyMap.put(woodenHangingSignId, woodenCeilingHangingSign);
            woodBlockFamilyMap.put(woodenWallHangingSignId, woodenWallHangingSign);

            woodItemFamilyMap.put(woodenSignId, woodenSignItem);
            woodItemFamilyMap.put(woodenHangingSignId, woodenHangingSignItem);

            woodItemFamilyMap.put(woodenBoatId, woodenBoatItem);
            woodItemFamilyMap.put(woodenChestBoatId, woodenChestBoatItem);

            return new WoodenBlockGroup(woodBlockSetType, woodBlockType, woodBlockFamilyMap, woodItemFamilyMap, woodBlockEntityFamilyMap, woodEntityTypeFamilyMap);
        });
    }
}
