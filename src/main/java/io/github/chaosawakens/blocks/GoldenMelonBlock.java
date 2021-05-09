package io.github.chaosawakens.blocks;

import io.github.chaosawakens.registry.ModBlocks;
import net.minecraft.block.*;

public class GoldenMelonBlock extends StemGrownBlock {
    public GoldenMelonBlock(AbstractBlock.Properties builder) {
        super(builder);
    }

    public StemBlock getStem() {
        return (StemBlock) ModBlocks.GOLDEN_MELON_STEM.get();
    }

    public AttachedStemBlock getAttachedStem() {
        return (AttachedStemBlock)ModBlocks.ATTACHED_GOLDEN_MELON_STEM.get();
    }
}