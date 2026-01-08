package io.github.chaosawakens.core.template;

import com.mememan.nexus.property_wrapper.def.block.BlockPropertyWrapper;
import com.mememan.nexus.property_wrapper.def.block.BlockPropertyWrapperBuilder;
import com.mememan.nexus.template.property_wrapper.BlockPropertyWrapperTemplates;
import com.mememan.nexus.template.property_wrapper.ItemPropertyWrapperTemplates;
import com.mememan.nexus.util.LootUtil;
import com.mememan.nexus.util.ModelUtil;
import com.mememan.nexus.util.RegistryUtil;
import io.github.chaosawakens.content.registry.CACreativeModeTabs;
import io.github.chaosawakens.content.registry.CATags;
import io.github.chaosawakens.util.StringUtil;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectObjectImmutablePair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class CABPWTemplates {
    public static final BlockPropertyWrapper<Block> PATTERN_BLOCK = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.BASIC)
            .withRecipe(CARecipeTemplates::patternBlockRecipeFrom)
            .build();

    public static final BlockPropertyWrapper<Block> FOSSIL = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.BASIC)
            .withParentTab(CACreativeModeTabs.FOSSILS)
            .literalTranslation()
            .withLocalization(StringUtil::formatFossilName)
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
    public static final BlockPropertyWrapper<Block> FOSSIL_PICKAXE_IRON = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_PICKAXE)
            .withTag(() -> BlockTags.NEEDS_IRON_TOOL)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_SHOVEL = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL)
            .withTag(() -> BlockTags.MINEABLE_WITH_SHOVEL)
            .build();

    public static final BlockPropertyWrapper<Block> FOSSIL_BLACKSTOME = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_PICKAXE)
            .withTag(CATags.CABlockTags.BLACKSTONE_FOSSILS::get)
            .withAdditionalTag(CATags.CAItemTags.BLACKSTONE_FOSSILS::get)
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
    public static final BlockPropertyWrapper<Block> FOSSIL_DREDGESTONE = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_PICKAXE_STONE)
            .withTag(CATags.CABlockTags.DREDGESTONE_FOSSILS::get)
            .withAdditionalTag(CATags.CAItemTags.DREDGESTONE_FOSSILS::get)
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
    public static final BlockPropertyWrapper<Block> FOSSIL_GLOOMSTONE = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_PICKAXE_STONE)
            .withTag(CATags.CABlockTags.GLOOMSTONE_FOSSILS::get)
            .withAdditionalTag(CATags.CAItemTags.GLOOMSTONE_FOSSILS::get)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_GRAVEL = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_SHOVEL)
            .withTag(CATags.CABlockTags.GRAVEL_FOSSILS::get)
            .withAdditionalTag(CATags.CAItemTags.GRAVEL_FOSSILS::get)
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
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_SAND = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_SHOVEL)
            .withTag(CATags.CABlockTags.SAND_FOSSILS::get)
            .withAdditionalTag(CATags.CAItemTags.SAND_FOSSILS::get)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_SANDSTONE = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_PICKAXE)
            .withModelDefinition(ModelUtil::cubeBottomTop)
            .withTag(CATags.CABlockTags.SANDSTONE_FOSSILS::get)
            .withAdditionalTag(CATags.CAItemTags.SANDSTONE_FOSSILS::get)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_SOUL_SOIL = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_SHOVEL)
            .withTag(CATags.CABlockTags.SOUL_SOIL_FOSSILS::get)
            .withAdditionalTag(CATags.CAItemTags.SOUL_SOIL_FOSSILS::get)
            .build();
    public static final BlockPropertyWrapper<Block> FOSSIL_STONE = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(FOSSIL_PICKAXE)
            .withTag(CATags.CABlockTags.STONE_FOSSILS::get)
            .withAdditionalTag(CATags.CAItemTags.STONE_FOSSILS::get)
            .build();

    public static final BlockPropertyWrapper<Block> GATE_BLOCK = new BlockPropertyWrapper<>()
            .builder()
            .literalTranslation()
            .withModelDefinition(parentBlock -> ModelUtil.cubeBottomTop(parentBlock, RegistryUtil.getTextureLocationOrDefault(parentBlock), RegistryUtil.getTextureLocationOrDefaultWithSuffix(parentBlock, "_top"), RegistryUtil.getTextureLocationOrDefaultWithSuffix(parentBlock, "_top")))
            .withBlockStateDefinition(ModelUtil::simpleBlockState)
            .build();

    public static final BlockPropertyWrapper<Block> LEAF_CARPET = new BlockPropertyWrapper<>()
            .builder()
            .withModelDefinition(CAModelTemplates::leafCarpet)
            .withBlockStateDefinition(CAModelTemplates::leafCarpetBlockState)
            .withTag(CATags.CABlockTags.LEAF_CARPETS::get)
            .withAdditionalTag(CATags.CAItemTags.LEAF_CARPETS::get)
            .withLootTable(LootUtil::dropMultiFace)
            .build();

    public static final BlockPropertyWrapper<Block> FRUITABLE_LEAVES = new BlockPropertyWrapper<>()
            .builder()
            .copyFrom(BlockPropertyWrapperTemplates.LEAVES)
            .withModelDefinition(CAModelTemplates::fruitableLeaves)
            .withBlockStateDefinition(CAModelTemplates::fruitableLeavesBlockState)
            .withLootTable(CALootTableTemplates::dropLeavesRipe)
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

    public static <B extends Block> BlockPropertyWrapperBuilder<B> registerWithItemAndChain(ResourceLocation blockId, Supplier<B> blockSup, Item.Properties itemProps, BlockPropertyWrapper<Block> templateBPW, @Nullable Collection<Supplier<Block>> blockSupCol, @Nullable Collection<Supplier<Item>> blockItemSupCol) {
        Supplier<B> registeredBlock = BlockPropertyWrapperTemplates.registerBlock(blockId, blockSup, blockSupCol);

        ItemPropertyWrapperTemplates.registerItem(blockId, () -> new BlockItem(registeredBlock.get(), itemProps), blockItemSupCol);

        return new BlockPropertyWrapper<>(registeredBlock, blockId.getNamespace())
                .builder()
                .copyFromType(templateBPW);
    }

    private CABPWTemplates() {
        throw new IllegalAccessError("Attempted to construct instance of template class! (CABPWTemplates)");
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
}
