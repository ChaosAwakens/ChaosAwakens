package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.block.BlockPropertyWrapper;
import io.github.chaosawakens.util.LootUtil;
import io.github.chaosawakens.util.ModelUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.tags.BlockTags;

public final class CABlockPropertyWrappers {

    // Basic
    public static final BlockPropertyWrapper BASIC_BLOCK = BlockPropertyWrapper.createTemplate()
            .builder()
            .withLootTable(LootUtil::dropSelf)
            .withCustomModelDefinition(ModelUtil.cubeAll(CAConstants.prefix("base/template")))
            .withBlockStateDefinition(ModelUtil::simpleBlock)
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
}
