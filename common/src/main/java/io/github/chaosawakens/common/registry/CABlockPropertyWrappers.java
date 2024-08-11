package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.block.BlockPropertyWrapper;
import io.github.chaosawakens.util.LootUtil;
import io.github.chaosawakens.util.ModelUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.tags.BlockTags;

public final class CABlockPropertyWrappers {

    public static final BlockPropertyWrapper BASIC_BLOCK = BlockPropertyWrapper.createTemplate()
            .builder()
            .withLootTable(LootUtil::dropSelf)
            .withCustomModelDefinition(ModelUtil.cubeAll(CAConstants.prefix("base/template")))
            .withBlockStateDefinition(ModelUtil::simpleBlock)
            .build();

    public static final BlockPropertyWrapper BASIC_BLOCK_SWORD = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.SWORD_EFFICIENT))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_SWORD_STONE = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_SWORD)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_STONE_TOOL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_SWORD_IRON = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_SWORD)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_IRON_TOOL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_SWORD_DIAMOND = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_SWORD)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_DIAMOND_TOOL))
            .build();

    public static final BlockPropertyWrapper BASIC_BLOCK_PICKAXE = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.MINEABLE_WITH_PICKAXE))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_PICKAXE_STONE = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_PICKAXE)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_STONE_TOOL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_PICKAXE_IRON = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_PICKAXE)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_IRON_TOOL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_PICKAXE_DIAMOND = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_PICKAXE)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_DIAMOND_TOOL))
            .build();

    public static final BlockPropertyWrapper BASIC_BLOCK_AXE = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.MINEABLE_WITH_AXE))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_AXE_STONE = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_AXE)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_STONE_TOOL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_AXE_IRON = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_AXE)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_IRON_TOOL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_AXE_DIAMOND = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_AXE)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_DIAMOND_TOOL))
            .build();

    public static final BlockPropertyWrapper BASIC_BLOCK_SHOVEL = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.MINEABLE_WITH_SHOVEL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_SHOVEL_STONE = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_SHOVEL)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_STONE_TOOL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_SHOVEL_IRON = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_SHOVEL)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_IRON_TOOL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_SHOVEL_DIAMOND = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_SHOVEL)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_DIAMOND_TOOL))
            .build();

    public static final BlockPropertyWrapper BASIC_BLOCK_HOE = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.MINEABLE_WITH_HOE))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_HOE_STONE = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_SHOVEL)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_STONE_TOOL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_HOE_IRON = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_SHOVEL)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_IRON_TOOL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_HOE_DIAMOND = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_SHOVEL)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_DIAMOND_TOOL))
            .build();

    public static final BlockPropertyWrapper BASIC_WOODEN_SLAB = BlockPropertyWrapper.createTemplate()
            .builder()
            .withLootTable(LootUtil::dropSlab)
            .withTags(ObjectArrayList.of(BlockTags.WOODEN_SLABS, BlockTags.MINEABLE_WITH_AXE))
            .withCustomModelDefinitions(ModelUtil.slab(CAConstants.prefix("base/template")))
            .withBlockStateDefinition(ModelUtil::woodenSlab)
            .build();

    public static final BlockPropertyWrapper BASIC_WOODEN_DOOR = BlockPropertyWrapper.createTemplate()
            .builder()
            .withLootTable(LootUtil::dropDoor)
            .withTags(ObjectArrayList.of(BlockTags.WOODEN_DOORS, BlockTags.MINEABLE_WITH_AXE))
            .withCustomModelDefinitions(ModelUtil.door(CAConstants.prefix("wood/apple/apple_door_top"), CAConstants.prefix("wood/apple/apple_door_bottom")))
            .withBlockStateDefinition(ModelUtil::door)
            .build();
}
