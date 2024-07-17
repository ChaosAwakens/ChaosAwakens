package io.github.chaosawakens.common.registry;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.worldgen.structureprocessor.TagRuleStructureProcessor;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.StructureProcessor;

public class CAStructureProcessorTypes {
    public static final IStructureProcessorType<TagRuleStructureProcessor> TAG_RULE = () -> TagRuleStructureProcessor.CODEC;

    private static <SP extends StructureProcessor> void registerStructureProcessorType(String id, IStructureProcessorType<SP> entryValue) {
        Registry.register(Registry.STRUCTURE_PROCESSOR, ChaosAwakens.prefix(id), entryValue);
    }

    public static void registerStructureProcessorTypes() {
        registerStructureProcessorType("tag_rule", TAG_RULE);
    }
}
