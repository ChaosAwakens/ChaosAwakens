package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableList;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.worldgen.ruleentry.TagRuleEntry;
import io.github.chaosawakens.common.worldgen.structureprocessor.TagRuleStructureProcessor;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.StructureProcessorList;

public class CAStructureProcessorLists {
    public static final StructureProcessorList REPLACE_WITH_GRAVEL_FOSSILS = new StructureProcessorList(ImmutableList.of(new TagRuleStructureProcessor(ImmutableList.of(
            new TagRuleEntry(new BlockMatchRuleTest(CABlocks.ROYAL_GUARDIAN_SCALE_BLOCK.get()), CATags.Blocks.FOSSILS_GRAVEL)
    ))));
    public static final StructureProcessorList REPLACE_WITH_STONE_FOSSILS = new StructureProcessorList(ImmutableList.of(new TagRuleStructureProcessor(ImmutableList.of(
            new TagRuleEntry(new BlockMatchRuleTest(CABlocks.ROYAL_GUARDIAN_SCALE_BLOCK.get()), CATags.Blocks.FOSSILS_STONE)
    ))));
    public static final StructureProcessorList REPLACE_WITH_FROZEN_FOSSILS = new StructureProcessorList(ImmutableList.of(new TagRuleStructureProcessor(ImmutableList.of(
            new TagRuleEntry(new BlockMatchRuleTest(CABlocks.ROYAL_GUARDIAN_SCALE_BLOCK.get()), CATags.Blocks.FOSSILS_ICE)
    ))));

    private static <SPL extends StructureProcessorList> void registerStructureProcessorList(String id, SPL entryValue) {
        WorldGenRegistries.register(WorldGenRegistries.PROCESSOR_LIST, ChaosAwakens.prefix(id), entryValue);
    }

    public static void registerStructureProcessorLists() {
        registerStructureProcessorList("replace_with_fossils", REPLACE_WITH_GRAVEL_FOSSILS);
        registerStructureProcessorList("replace_with_stone_fossils", REPLACE_WITH_STONE_FOSSILS);
        registerStructureProcessorList("replace_with_frozen_fossils", REPLACE_WITH_FROZEN_FOSSILS);
    }
}
