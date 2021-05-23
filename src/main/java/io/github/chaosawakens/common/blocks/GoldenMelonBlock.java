package io.github.chaosawakens.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.StemBlock;
import net.minecraft.block.StemGrownBlock;

public class GoldenMelonBlock extends StemGrownBlock {
    public GoldenMelonBlock(AbstractBlock.Properties builder) {
        super(builder);
    }

    public StemBlock getStem() {
        return (StemBlock) Blocks.MELON_STEM;
    }

    public AttachedStemBlock getAttachedStem() {
        return (AttachedStemBlock) Blocks.ATTACHED_MELON_STEM;
    }
}