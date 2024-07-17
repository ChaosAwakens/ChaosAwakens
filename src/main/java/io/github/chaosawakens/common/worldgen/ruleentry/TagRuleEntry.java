package io.github.chaosawakens.common.worldgen.ruleentry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.template.AlwaysTrueRuleTest;
import net.minecraft.world.gen.feature.template.AlwaysTrueTest;
import net.minecraft.world.gen.feature.template.PosRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;

import java.util.Optional;
import java.util.Random;

public class TagRuleEntry {
    public static final Codec<TagRuleEntry> CODEC = RecordCodecBuilder.create((codecInstance) -> codecInstance.group(
                    RuleTest.CODEC.fieldOf("input_predicate").forGetter((treInstance) -> treInstance.inputPredicate),
                    RuleTest.CODEC.fieldOf("location_predicate").forGetter((treInstance) -> treInstance.locPredicate),
                    PosRuleTest.CODEC.optionalFieldOf("position_predicate", AlwaysTrueTest.INSTANCE).forGetter((treInstance) -> treInstance.posPredicate),
                    ITag.codec(() -> TagCollectionManager.getInstance().getBlocks()).optionalFieldOf("target_result_block_tag").forGetter(treInstance -> treInstance.targetResultBlockTag))
            .apply(codecInstance, TagRuleEntry::new));
    private final RuleTest inputPredicate;
    private final RuleTest locPredicate;
    private final PosRuleTest posPredicate;
    private final Optional<ITag<Block>> targetResultBlockTag;

    public TagRuleEntry(RuleTest pInputPredicate, RuleTest pLocPredicate, PosRuleTest pPosPredicate, Optional<ITag<Block>> targetResultBlockTag) {
        this.inputPredicate = pInputPredicate;
        this.locPredicate = pLocPredicate;
        this.posPredicate = pPosPredicate;
        this.targetResultBlockTag = targetResultBlockTag;
    }

    public TagRuleEntry(RuleTest pInputPredicate, RuleTest pLocPredicate, ITag<Block> targetResultBlockTag) {
        this(pInputPredicate, pLocPredicate, AlwaysTrueTest.INSTANCE, Optional.ofNullable(targetResultBlockTag));
    }

    public TagRuleEntry(RuleTest pInputPredicate, ITag<Block> targetResultBlockTag) {
        this(pInputPredicate, AlwaysTrueRuleTest.INSTANCE, AlwaysTrueTest.INSTANCE, Optional.ofNullable(targetResultBlockTag));
    }

    public boolean test(BlockState inputState, BlockState locState, BlockPos posPredicatePos, BlockPos initialPosPredicatePos, BlockPos targetPosPredicatePos, Random pRandom) {
        return this.inputPredicate.test(inputState, pRandom) && this.locPredicate.test(locState, pRandom) && this.posPredicate.test(posPredicatePos, initialPosPredicatePos, targetPosPredicatePos, pRandom);
    }

    public Optional<ITag<Block>> getTargetResultBlockTag() {
        return this.targetResultBlockTag;
    }
}
