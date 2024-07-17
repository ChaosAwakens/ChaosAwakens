package io.github.chaosawakens.common.worldgen.structureprocessor;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import io.github.chaosawakens.common.registry.CAStructureProcessorTypes;
import io.github.chaosawakens.common.worldgen.ruleentry.TagRuleEntry;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class TagRuleStructureProcessor extends StructureProcessor {
    public static final Codec<TagRuleStructureProcessor> CODEC = TagRuleEntry.CODEC.listOf().fieldOf("tag_rules").xmap(TagRuleStructureProcessor::new, (trspInstance) -> trspInstance.tagRules).codec();
    private final ImmutableList<TagRuleEntry> tagRules;

    public TagRuleStructureProcessor(List<? extends TagRuleEntry> tagRules) {
        this.tagRules = ImmutableList.copyOf(tagRules);
    }

    @Nullable
    @Override
    public Template.BlockInfo process(IWorldReader pLevel, BlockPos p_230386_2_, BlockPos p_230386_3_, Template.BlockInfo initialBlockInfoTemplate, Template.BlockInfo resultBlockInfoTemplate, PlacementSettings pSettings, @Nullable Template template) {
        Random random = new Random(MathHelper.getSeed(resultBlockInfoTemplate.pos));
        BlockState targetState = pLevel.getBlockState(resultBlockInfoTemplate.pos);

        for (TagRuleEntry tagRuleEntry : tagRules) {
            if (tagRuleEntry.test(resultBlockInfoTemplate.state, targetState, initialBlockInfoTemplate.pos, resultBlockInfoTemplate.pos, p_230386_3_, random)) {
                return new Template.BlockInfo(resultBlockInfoTemplate.pos, tagRuleEntry.getTargetResultBlockTag().get().getRandomElement(random).defaultBlockState(), null);
            }
        }

        return resultBlockInfoTemplate;
    }

    @Override
    protected IStructureProcessorType<TagRuleStructureProcessor> getType() {
        return CAStructureProcessorTypes.TAG_RULE;
    }
}
