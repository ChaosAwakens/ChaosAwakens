package io.github.chaosawakens.blocks;

import io.github.chaosawakens.registry.CABlocks;
import net.minecraft.block.*;

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